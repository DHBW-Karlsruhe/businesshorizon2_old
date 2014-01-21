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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import dhbw.ka.mwi.businesshorizon2.models.DeterministicResultContainer;
import dhbw.ka.mwi.businesshorizon2.models.Szenario;

/**
 * Die DeterministicChartArea komponiert die Ausgabe der Ergebnisse des
 * deterministischen Verfahrens. Es können mehrere Diagramme sowie Labels
 * hinzugefügt und im Gridlayout angeordnet werden.
 * 
 * @author Florian Stier, Marcel Rosenberger, Annika Weis
 * 
 */
public class DeterministicChartArea extends VerticalLayout {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger
			.getLogger("DeterministicChartArea.class");

	//APV Ausgabe
	public DeterministicChartArea(double uwsteuerfrei, double steuervorteile,
			double unternehmenswert, double fremdkapital, String name,
			DeterministicResultContainer drContainer, Szenario scenario) {

		Label headline = new Label("<h2>Deterministisches Verfahren - APV</h2>");
		headline.setContentMode(Label.CONTENT_XHTML);
		this.addComponent(headline);

		// Chart zur Aufschlüsselung des Unternehmenswert
		List<String> cvKeyColumns = new ArrayList<String>();
		cvKeyColumns.add("Unverschuldetes Unternehmen");
		cvKeyColumns.add("Steuervorteil");
		cvKeyColumns.add("Unternehmenswert");
		cvKeyColumns.add("Fremdkapital");

		Map<String, double[]> cvKeyValues = new LinkedHashMap<String, double[]>();
		cvKeyValues.put("Unverschuldetes Unternehmen und Steuervorteil",
				new double[] { uwsteuerfrei, steuervorteile, 0, 0 });
		cvKeyValues.put("Unternehmenswert und Fremdkapital", new double[] { 0,
				0, unternehmenswert, fremdkapital });

		StackedColumnChart cvKeyChart = new StackedColumnChart("Chart",
				cvKeyColumns);
		cvKeyChart.addValues(cvKeyValues);
		cvKeyChart.setOption("is3D", true);
		cvKeyChart.setHeight("300px");
		cvKeyChart.setWidth("500px");
		this.addComponent(cvKeyChart);
		// Platzhalter
		this.addComponent(new Label(""));

		// Chart zu Fremdkapital und Cashflow Verlauf
		// Beschriftung der Linie hinzufügen
		List<String> cfKeyColumns = new ArrayList<String>();
		cfKeyColumns.add("Cashflows");
		cfKeyColumns.add("Fremdkapital");

		double[] cashflows = new double[drContainer.getJahre().length];
		double[] fremdkapitalwerte = new double[drContainer.getJahre().length];
		int[] jahre = new int[drContainer.getJahre().length];
		cashflows = drContainer.getCashflows();
		fremdkapitalwerte = drContainer.getFremdkapitl();
		jahre = drContainer.getJahre();
		int anzahlWerte = drContainer.getJahre().length;

		// Werte hinzufügen
		Map<String, double[]> cfKeyValues = new LinkedHashMap<String, double[]>();
		for (int i = 0; i < anzahlWerte; i++) {
			cfKeyValues.put(jahre[i] + "", new double[] { cashflows[i],
					fremdkapitalwerte[i] });
			logger.debug("DeterministicLineChart: " + cashflows[i] + " &  "
					+ fremdkapitalwerte[i]);
		}

		BasicLineChart cfKeyChart = new BasicLineChart("Chart", cfKeyColumns);
		cfKeyChart.addValues(cfKeyValues);
		cfKeyChart.setHeight("300px");
		cfKeyChart.setWidth("500px");
		this.addComponent(cfKeyChart);


		// Planungsprämissen des Szenarios hinzufügen
		ScenarioTable st = new ScenarioTable(scenario);
		st.setHeight("200px");
		this.addComponent(st);
		this.setHeight("800px");

	}
	
	//FTE Ausgabe
	public DeterministicChartArea(double unternehmenswert, String name,
			DeterministicResultContainer drContainer, Szenario scenario) {
		Label headline = new Label("<h2>Deterministisches Verfahren - FTE</h2>");
		headline.setContentMode(Label.CONTENT_XHTML);
		this.addComponent(headline);

		// Chart zur Aufschlüsselung des Unternehmenswert
		List<String> cvKeyColumns = new ArrayList<String>();
		cvKeyColumns.add("Unternehmenswert");
		

		Map<String, double[]> cvKeyValues = new LinkedHashMap<String, double[]>();
		cvKeyValues.put("Unternehmenswert", new double[] {unternehmenswert});

		StackedColumnChart cvKeyChart = new StackedColumnChart("Chart",
				cvKeyColumns);
		cvKeyChart.addValues(cvKeyValues);
		cvKeyChart.setOption("is3D", true);
		cvKeyChart.setHeight("300px");
		cvKeyChart.setWidth("500px");
		this.addComponent(cvKeyChart);
		// Platzhalter
		this.addComponent(new Label(""));

		// Chart zu Cashflow Verlauf
		// Beschriftung der Linie hinzufügen
		List<String> cfKeyColumns = new ArrayList<String>();
		cfKeyColumns.add("Cashflows");

		double[] cashflows = new double[drContainer.getJahre().length];
		
		int[] jahre = new int[drContainer.getJahre().length];
		cashflows = drContainer.getCashflows();
		
		jahre = drContainer.getJahre();
		int anzahlWerte = drContainer.getJahre().length;

		// Werte hinzufügen
		Map<String, double[]> cfKeyValues = new LinkedHashMap<String, double[]>();
		for (int i = 0; i < anzahlWerte; i++) {
			cfKeyValues.put(jahre[i] + "", new double[] { cashflows[i]});
			logger.debug("DeterministicLineChart: " + cashflows[i]);
		}

		BasicLineChart cfKeyChart = new BasicLineChart("Chart", cfKeyColumns);
		cfKeyChart.addValues(cfKeyValues);
		cfKeyChart.setHeight("300px");
		cfKeyChart.setWidth("500px");
		this.addComponent(cfKeyChart);


		// Planungsprämissen des Szenarios hinzufügen
		ScenarioTable st = new ScenarioTable(scenario);
		st.setHeight("200px");
		this.addComponent(st);
		this.setHeight("800px");
	}

}
