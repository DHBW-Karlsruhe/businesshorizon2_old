package dhbw.ka.mwi.businesshorizon2.ui.process.output.charts;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.TreeSet;

import com.vaadin.ui.GridLayout;

import dhbw.ka.mwi.businesshorizon2.models.CompanyValue.CompanyValueStochastic.Couple;
import dhbw.ka.mwi.businesshorizon2.models.Period.CashFlowPeriod;

/**
 * Die StochasticChartArea komponiert die Ausgabe der Ergebnisse des
 * stochastischen Verfahrens. Es können mehrere Diagramme sowie Labels
 * hinzugefügt und im Gridlayout angeordnet werden.
 * 
 * @author Florian Stier
 * 
 */
public class StochasticChartArea extends GridLayout {

	private static final long serialVersionUID = 1L;

	public StochasticChartArea(TreeSet<CashFlowPeriod> periods, TreeMap<Double, Couple> companyValues) {

		super(3, 2);

		// Chart zur Anzeige der Unternehmenswerte
		List<String> cvChartColumns = new ArrayList<String>();
		cvChartColumns.add("Häufigkeit des Unternehmenswert");

		Map<String, double[]> cvChartValues = new LinkedHashMap<String, double[]>();

		BasicColumnChart cvChart = new BasicColumnChart("Unternehmenswert", cvChartColumns);

		for (Entry<Double, Couple> companyValue : companyValues.entrySet()) {
			cvChartValues.put(Double.toString(companyValue.getKey()),
					new double[] { companyValue.getValue().getCount() });
		}

		cvChart.addValues(cvChartValues);

		this.addComponent(cvChart, 0, 0);

		// Chart zur Anzeige des Cashflow Verlaufs
		if (periods != null) {
			List<String> cfChartLines = new ArrayList<String>();
			cfChartLines.add("Erwartete Cashflows");

			Map<String, double[]> cfChartValues = new LinkedHashMap<String, double[]>();

			BasicLineChart cfChart = new BasicLineChart(cfChartLines);

			for (CashFlowPeriod period : periods) {
				cfChartValues.put(Integer.toString(period.getYear()), new double[] { period.getFreeCashFlow() });

			}

			cfChart.addValues(cfChartValues);
			this.addComponent(cfChart, 0, 1);
		}

		this.setHeight("600px");
		this.setWidth("2000px");
	}
}
