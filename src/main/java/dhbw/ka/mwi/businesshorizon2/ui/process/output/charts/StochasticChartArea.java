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

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.TreeSet;

import org.apache.log4j.Logger;


import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import dhbw.ka.mwi.businesshorizon2.methods.discountedCashflow.APV_2;
import dhbw.ka.mwi.businesshorizon2.models.Szenario;
import dhbw.ka.mwi.businesshorizon2.models.CompanyValue.CompanyValueStochastic.Couple;
import dhbw.ka.mwi.businesshorizon2.models.Period.CashFlowPeriod;

/**
 * Die StochasticChartArea komponiert die Ausgabe der Ergebnisse des
 * stochastischen Verfahrens. Es können mehrere Diagramme sowie Labels
 * hinzugefügt und im Gridlayout angeordnet werden.
 * 
 * @author Florian Stier, Marcel Rosenberger
 * 
 */
public class StochasticChartArea extends VerticalLayout {

	private static final long serialVersionUID = 1L;
	
	private static final Logger logger = Logger
			.getLogger("StochasticChartArea.class");
	
	

	public StochasticChartArea(String methodName, TreeSet<CashFlowPeriod> periods, TreeMap<Double, Couple> companyValues, double validierung, Szenario scenario) {
		
		DecimalFormat df = new DecimalFormat("#.00");
		
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

		logger.debug("Erwartungswert ermitteln");
		APV_2 apv = new APV_2();
		double[] cashflow = null;
		double[] fremdkapital = null;
		int i;

		// für jede Periode wird die Schleife je einmal durchlaufen 
		for (CashFlowPeriod period : periods) {			
			// ein Iterator zum durchlaufen des TreeSet wird erstellt.
			Iterator<CashFlowPeriod> periodenIterator = periods
					.iterator();
			// Zähler, Cashflow- und Fremdkapital-Arrays werden
			// initialisiert
			cashflow = new double[periods.size()];
			fremdkapital = new double[periods.size()];
			i = 0;
			// pro Periode sollen nun die Werte ausgelesen werden
			while (periodenIterator.hasNext()) {
				period = periodenIterator.next();
				cashflow[i] = period.getFreeCashFlow();
				fremdkapital[i] = period.getCapitalStock();
				i++;
			}
		}
			
		double erwartungswert = apv.calculateValues(cashflow, fremdkapital,
				scenario);
		double keydrueber = 0;
		int keydrueberfreq = 0 ; 
		double keydrunter = 0;
		int keydrunterfreq = 0;
		
		
		logger.debug("Eigentlicher Erwartungswert: " + erwartungswert);
        for (Entry<Double, Couple> companyValue : companyValues.entrySet()) {

                cvChartValues.put(df.format(companyValue.getKey()),
                                new double[] { companyValue.getValue().getCount() });
                
                if (companyValue.getKey() < erwartungswert){
                	keydrunter = companyValue.getKey();
                	keydrunterfreq = companyValue.getValue().getCount();
                	logger.debug("Neuer Key drunter: " + keydrunter);
                } 
                
                if ((companyValue.getKey() > erwartungswert) && (keydrueber != 0)){
                	keydrueber = companyValue.getKey();
                	keydrueberfreq = companyValue.getValue().getCount();
                	logger.debug("Neuer Key drueber: " + keydrueber);
                }
                
                /*
                 * Alte Erwartungswert Ermittlung
                 * 
                 * 
                // Erwartungswert der Unternehmenswerte bestimmen (Wert mit größter
                // Häufigkeit)
                if (companyValue.getValue().getCount() >= expectedCompanyValueFreq) {
                        expectedCompanyValue = Double.toString(companyValue.getKey());
                        expectedCompanyValueFreq = companyValue.getValue().getCount();
                        logger.debug("Neuer Erwartungswert: " + expectedCompanyValue);
                }*/

        }
		
        if(Math.abs((keydrunter-erwartungswert)) < Math.abs((keydrueber-erwartungswert))){
        	cvChartValues.put(df.format(keydrunter), new double[] { 0, keydrunterfreq });
        } else{
        	cvChartValues.put(df.format(keydrueber), new double[] { 0, keydrueberfreq });
        }

		

		cvChart.addValues(cvChartValues);
		cvChart.setHeight("200px");
		cvChart.setWidth("1024px");
		

		this.addComponent(cvChart);

		// Chart zur Anzeige des Cashflow Verlaufs
		if (periods != null) {
			List<String> cfChartLines = new ArrayList<String>();
			cfChartLines.add("Erwartete Cashflows");
			cfChartLines.add("Erwartetes Fremdkapital");

			Map<String, double[]> cfChartValues = new LinkedHashMap<String, double[]>();

			BasicLineChart cfChart = new BasicLineChart("Erwartete Werte", cfChartLines);

			for (CashFlowPeriod period : periods) {
				cfChartValues.put(Integer.toString(period.getYear()), new double[] { Math.round(period.getFreeCashFlow()), Math.round(period.getCapitalStock()) });

			}

			cfChart.addValues(cfChartValues);
			cfChart.setHeight("200px");
			this.addComponent(cfChart);
		}
		
		//Modellabweichung hinzufügen		
		
		this.addComponent(new Label("Die Modellabweichung beträgt " + df.format(validierung) + "%"));
		
		//Planungsprämissen des Szenarios hinzufügen
		ScenarioTable st = new ScenarioTable(scenario);
		st.setHeight("200px");
		this.addComponent(st);
		
		this.setHeight("900px");
		this.setWidth("1024px");

	}
}
