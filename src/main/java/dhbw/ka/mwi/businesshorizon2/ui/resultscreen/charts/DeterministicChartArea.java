/*******************************************************************************
 * BusinessHorizon2
 *
 * Copyright (C) 
 * 2012-2013 Christian Gahlert, Florian Stier, Kai Westerholz,
 * Timo Belz, Daniel Dengler, Katharina Huber, Christian Scherer, Julius Hacker
 * 2013-2014 Marcel Rosenberger, Mirko Göpfrich, Annika Weis, Katharina Narlock, 
 * Volker Meier
 * 
 *
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package dhbw.ka.mwi.businesshorizon2.ui.resultscreen.charts;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import org.apache.log4j.Logger;

import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import dhbw.ka.mwi.businesshorizon2.models.DeterministicResultContainer;
import dhbw.ka.mwi.businesshorizon2.models.Szenario;
import dhbw.ka.mwi.businesshorizon2.models.Period.CashFlowPeriod;
import dhbw.ka.mwi.businesshorizon2.models.Period.Period;

/**
 * Die DeterministicChartArea komponiert die Ausgabe der Ergebnisse des
 * deterministischen Verfahrens. Es können mehrere Diagramme sowie Labels
 * hinzugefügt und im Gridlayout angeordnet werden.
 * 
 * @author Florian Stier, Marcel Rosenberger, Annika Weis, Mirko Göpfrich
 * 
 */
public class DeterministicChartArea extends HorizontalLayout {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger
			.getLogger("DeterministicChartArea.class");
	private Label headline;

	public Label getHeadline() {
		return headline;
	}

	// APV Ausgabe
	@SuppressWarnings("unchecked")
	public DeterministicChartArea(double uwsteuerfrei, double steuervorteile,
			double unternehmenswert, double fremdkapital, String name,
			DeterministicResultContainer drContainer, Szenario scenario) {

		DecimalFormat df = new DecimalFormat("#0.00");
		headline = new Label("<h2>Deterministisches Verfahren - APV</h2>");
		headline.setContentMode(Label.CONTENT_XHTML);

		// Chart zur Aufschlüsselung des Unternehmenswert, Rundung auf zwei
		// Nachkommastellen
		List<String> cvKeyColumns = new ArrayList<String>();
		cvKeyColumns.add("Unverschuldetes Unternehmen");
		cvKeyColumns.add("Steuervorteil");
		cvKeyColumns.add("Unternehmenswert");
		cvKeyColumns.add("Fremdkapital");

		Map<String, double[]> cvKeyValues = new LinkedHashMap<String, double[]>();
		cvKeyValues.put(
				"Unverschuldetes Unternehmen und Steuervorteil",
				new double[] { Math.round(100.0 * uwsteuerfrei) / 100.0,
						Math.round(100.0 * steuervorteile) / 100.0, 0, 0 });
		cvKeyValues.put(
				"Unternehmenswert und Fremdkapital",
				new double[] { 0, 0,
						Math.round(100.0 * unternehmenswert) / 100.0,
						Math.round(100.0 * fremdkapital) / 100.0 });

		StackedColumnChart cvKeyChart = new StackedColumnChart("Chart",
				cvKeyColumns);
		cvKeyChart.addValues(cvKeyValues);
		cvKeyChart.setOption("is3D", true);
		cvKeyChart.setHeight("300px");
		cvKeyChart.setWidth("410px");
		cvKeyChart.addStyleName("chart1");
		
		VerticalLayout vl = new VerticalLayout();
		Label hl = new Label("Unternehmenswertaufschlüsselung");
		vl.addComponent(hl);
		vl.addComponent(cvKeyChart);
		
		this.addComponent(vl);
		// Platzhalter

		// Chart zur Anzeige des Cashflow Verlaufs
		TreeSet<CashFlowPeriod> periods = new TreeSet<CashFlowPeriod>();

		// Das CashFlowPeriod TreeSet befüllen, um ClassCastException bei GKV
		// oder UKV vorzubeugen
		for (Period p : drContainer.getPeriodContainers().first().getPeriods()) {
			CashFlowPeriod cfp = new CashFlowPeriod(p.getYear());
			cfp.setCapitalStock(p.getCapitalStock());
			cfp.setFreeCashFlow(p.getFreeCashFlow());
			periods.add(cfp);
		}


		if (periods != null) {
			List<String> cfChartLines = new ArrayList<String>();
			cfChartLines.add("Cashflows");
			cfChartLines.add("Fremdkapital");


			Map<String, double[]> cfChartValues = new LinkedHashMap<String, double[]>();

			BasicLineChart cfChart = new BasicLineChart("Zukunftswerte",
					cfChartLines);

			// Werte hinzufügen und auf zwei Nachkommastellen runden
			for (CashFlowPeriod period : periods) {
				cfChartValues
						.put(Integer.toString(period.getYear()),
								new double[] {
										Double.parseDouble((df.format(period
												.getFreeCashFlow())).replace(
												",", ".")),
										Double.parseDouble((df.format(period
												.getCapitalStock())).replace(
												",", ".")) });

			}
			
	
			cfChart.addValues(cfChartValues);
			cfChart.setHeight("300px");
			cfChart.setWidth("510px");
			cfChart.addStyleName("chart2");
			
			VerticalLayout vl2 = new VerticalLayout();
			Label hl2 = new Label("Free-Cashflow und Fremdkapital");
			vl2.addComponent(hl2);
			vl2.addComponent(cfChart);
			
			this.addComponent(vl2);
			
		}

		// Planungsprämissen des Szenarios hinzufügen
		ScenarioTable st = new ScenarioTable(scenario);
		st.setHeight("100px");
		
		VerticalLayout vl3 = new VerticalLayout();
		Label hl3 = new Label("Planungsprämissen");
		vl3.addComponent(hl3);
		vl3.addComponent(st);
		
		this.addComponent(vl3);

	}

	// FTE Ausgabe
	@SuppressWarnings("unchecked")
	public DeterministicChartArea(double unternehmenswert, String name,
			DeterministicResultContainer drContainer, Szenario scenario) {
		DecimalFormat df = new DecimalFormat("#0.00");

		headline = new Label("<h2>Deterministisches Verfahren - FTE</h2>");
		headline.setContentMode(Label.CONTENT_XHTML);

		// Chart zur Aufschlüsselung des Unternehmenswert
		List<String> cvKeyColumns = new ArrayList<String>();
		cvKeyColumns.add("Unternehmenswert");

		Map<String, double[]> cvKeyValues = new LinkedHashMap<String, double[]>();
		cvKeyValues.put("Unternehmenswert",
				new double[] { Math.round(100.0 * unternehmenswert) / 100.0 });

		StackedColumnChart cvKeyChart = new StackedColumnChart("Chart",
				cvKeyColumns);
		cvKeyChart.addValues(cvKeyValues);
		cvKeyChart.setOption("is3D", true);
		cvKeyChart.setHeight("300px");
		cvKeyChart.setWidth("410px");
		cvKeyChart.setStyleName("chart1");
		
		VerticalLayout vl = new VerticalLayout();
		Label hl = new Label("Unternehmenswertaufschlüsselung");
		vl.addComponent(hl);
		vl.addComponent(cvKeyChart);
		
		this.addComponent(vl);
		// Platzhalter

		// Chart zur Anzeige des Cashflow Verlaufs
		TreeSet<CashFlowPeriod> periods = new TreeSet<CashFlowPeriod>();

		// Das CashFlowPeriod TreeSet befüllen, um ClassCastException bei GKV
		// oder UKV vorzubeugen
		for (Period p : drContainer.getPeriodContainers().first().getPeriods()) {
			CashFlowPeriod cfp = new CashFlowPeriod(p.getYear());
			cfp.setCapitalStock(p.getCapitalStock());
			cfp.setFreeCashFlow(p.getFreeCashFlow());
			periods.add(cfp);
		}
		if (periods != null) {
			List<String> cfChartLines = new ArrayList<String>();
			cfChartLines.add("Cashflows");
			cfChartLines.add("Fremdkapital");


			Map<String, double[]> cfChartValues = new LinkedHashMap<String, double[]>();

			BasicLineChart cfChart = new BasicLineChart("Zukunftswerte",
					cfChartLines);

			// Werte hinzufügen und auf zwei Nachkommastellen runden
			for (CashFlowPeriod period : periods) {
				cfChartValues
						.put(Integer.toString(period.getYear()),
								new double[] {
										Double.parseDouble((df.format(period
												.getFreeCashFlow())).replace(
												",", ".")),
										Double.parseDouble((df.format(period
												.getCapitalStock())).replace(
												",", ".")) });

			}

			cfChart.addValues(cfChartValues);
			cfChart.setHeight("300px");
			cfChart.setWidth("510px");
			cfChart.addStyleName("chart2");
			
			VerticalLayout vl2 = new VerticalLayout();
			Label hl2 = new Label("Free-Cashflow und Fremdkapital");
			vl2.addComponent(hl2);
			vl2.addComponent(cfChart);
			
			this.addComponent(vl2);
		}


		// Planungsprämissen des Szenarios hinzufügen
		ScenarioTable st = new ScenarioTable(scenario);
		st.setHeight("100px");
		st.setStyleName("chart3");
		
		VerticalLayout vl3 = new VerticalLayout();
		Label hl3 = new Label("Planungsprämissen");
		vl3.addComponent(hl3);
		vl3.addComponent(st);
		
		this.addComponent(vl3);
	}

}
