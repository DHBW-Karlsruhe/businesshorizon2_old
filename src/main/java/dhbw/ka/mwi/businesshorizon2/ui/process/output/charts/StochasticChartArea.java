/*******************************************************************************
 * BusinessHorizon2
 * 
 *     Copyright (C) 2012-2013  Christian Gahlert, Florian Stier, Kai Westerholz,
 *     Timo Belz, Daniel Dengler, Katharina Huber, Christian Scherer, Julius Hacker
 * 
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Affero General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 * 
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Affero General Public License for more details.
 * 
 *     You should have received a copy of the GNU Affero General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package dhbw.ka.mwi.businesshorizon2.ui.process.output.charts;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.TreeSet;

import org.apache.log4j.Logger;

import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import dhbw.ka.mwi.businesshorizon2.models.CompanyValue.CompanyValueStochastic.Couple;
import dhbw.ka.mwi.businesshorizon2.models.Period.CashFlowPeriod;

/**
 * Die StochasticChartArea komponiert die Ausgabe der Ergebnisse des
 * stochastischen Verfahrens. Es können mehrere Diagramme sowie Labels
 * hinzugefügt und im Gridlayout angeordnet werden.
 * 
 * @author Marcel Rosenberger
 * 
 */
public class StochasticChartArea extends VerticalLayout {

	private static final long serialVersionUID = 1L;
	
	private static final Logger logger = Logger
			.getLogger("StochasticChartArea.class");

	public StochasticChartArea(String methodName, TreeSet<CashFlowPeriod> periods, TreeMap<Double, Couple> companyValues) {

		// Überschrift anzeigen
		Label title = new Label("<h2>Stochastic Calculation - " + methodName + "<h2>");
		title.setContentMode(Label.CONTENT_XHTML);
		title.setHeight("50px");

		this.addComponent(title);

		// Chart zur Anzeige der Unternehmenswerte
		List<String> cvChartColumns = new ArrayList<String>();
		cvChartColumns.add("Häufigkeit des Unternehmenswert");
		cvChartColumns.add("Erwartungswert");

		Map<String, double[]> cvChartValues = new LinkedHashMap<String, double[]>();

		BasicColumnChart cvChart = new BasicColumnChart("Unternehmenswert", cvChartColumns);

		String expectedCompanyValue = "";
		double expectedCompanyValueFreq = 0;

		logger.debug("Erwartungswert ermitteln:");
		for (Entry<Double, Couple> companyValue : companyValues.entrySet()) {

			cvChartValues.put(Double.toString(companyValue.getKey()),
					new double[] { companyValue.getValue().getCount() });
			
			
			// Erwartungswert der Unternehmenswerte bestimmen (Wert mit größter
			// Häufigkeit)
			if (companyValue.getValue().getCount() > expectedCompanyValueFreq) {
				expectedCompanyValue = Double.toString(companyValue.getKey());
				expectedCompanyValueFreq = companyValue.getValue().getCount();
				logger.debug("Neuer Erwartungswert: " + companyValue.getValue().getCompanyValue());
			}

		}

		cvChartValues.put(expectedCompanyValue, new double[] { 0, expectedCompanyValueFreq });

		cvChart.addValues(cvChartValues);
		cvChart.setHeight("200px");
		

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
			cfChart.setHeight("200px");
			this.addComponent(cfChart);
		}
		this.setHeight("590px");
		this.setWidth("1024px");

	}
}
