package dhbw.ka.mwi.businesshorizon2.ui.process.output.charts;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.TreeSet;

import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

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
public class StochasticChartArea extends VerticalLayout {

	private static final long serialVersionUID = 1L;

	public StochasticChartArea(String methodName, TreeSet<CashFlowPeriod> periods, TreeMap<Double, Couple> companyValues) {

		// Überschrift anzeigen
		Label title = new Label("<h1>Stochastic Calculation - " + methodName + "</h1>");
		title.setContentMode(Label.CONTENT_XHTML);

		this.addComponent(title);

		// Chart zur Anzeige der Unternehmenswerte
		List<String> cvChartColumns = new ArrayList<String>();
		cvChartColumns.add("Häufigkeit des Unternehmenswert");
		cvChartColumns.add("Erwartungswert");

		Map<String, double[]> cvChartValues = new LinkedHashMap<String, double[]>();

		BasicColumnChart cvChart = new BasicColumnChart("Unternehmenswert", cvChartColumns);

		String expectedCompanyValue = "";
		double expectedCompanyValueFreq = 0;

		for (Entry<Double, Couple> companyValue : companyValues.entrySet()) {

			cvChartValues.put(Double.toString(companyValue.getKey()),
					new double[] { companyValue.getValue().getCount() });

			// Erwartungswert der Unternehmenswerte bestimmen (Wert mit größter
			// Häufigkeit)
			if (companyValue.getValue().getCount() >= expectedCompanyValueFreq) {
				expectedCompanyValue = Double.toString(companyValue.getKey());
				expectedCompanyValueFreq = companyValue.getValue().getCount();
			}

		}

		cvChartValues.put(expectedCompanyValue, new double[] { 0, expectedCompanyValueFreq });

		cvChart.addValues(cvChartValues);

		this.addComponent(cvChart);

		// Chart zur Anzeige des Cashflow Verlaufs
		if (periods != null) {
			List<String> cfChartLines = new ArrayList<String>();
			cfChartLines.add("Erwartete Cashflows");

			Map<String, double[]> cfChartValues = new LinkedHashMap<String, double[]>();

			BasicLineChart cfChart = new BasicLineChart("Cashflows", cfChartLines);

			for (CashFlowPeriod period : periods) {
				cfChartValues.put(Integer.toString(period.getYear()), new double[] { period.getFreeCashFlow() });

			}

			cfChart.addValues(cfChartValues);
			this.addComponent(cfChart);
		}
		this.setHeight("1000px");
		this.setWidth("1024px");

	}
}
