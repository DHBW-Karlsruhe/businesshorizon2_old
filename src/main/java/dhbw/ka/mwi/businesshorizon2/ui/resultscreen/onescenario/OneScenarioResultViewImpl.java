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

package dhbw.ka.mwi.businesshorizon2.ui.resultscreen.onescenario;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.vaadinvisualizations.ColumnChart;
import org.vaadin.vaadinvisualizations.LineChart;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

/**
 * Diese Klasse implementiert das GUI fuer den Prozessschritt "Ausgabe" in
 * Vaadin.
 * 
 * @author Florian Stier, Mirko Göpfrich
 * 
 */
public class OneScenarioResultViewImpl extends VerticalLayout implements OneScenarioResultViewInterface {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The Constant logger. */
	private static final Logger logger = Logger
			.getLogger("OneScenarioResultViewImpl.class");

	/** The presenter. */
	@Autowired
	private OneScenarioResultPresenter presenter;

	/** The planning label. */
	private Label planningLabel;

	/** The company value label. */
	private Label companyValueLabel;

	/** The planning layout. */
	private HorizontalLayout planningLayout;

	/** The company value. */
	private Label companyValue;

	/** The scenario layout. */
	private GridLayout scenarioLayout;

	/** The rendite ek label. */
	private Label renditeEKLabel;

	/** The rendite fk label. */
	private Label renditeFKLabel;

	/** The gewerbe st label. */
	private Label gewerbeStLabel;

	/** The koerper st label. */
	private Label koerperStLabel;

	/** The company value layout. */
	private HorizontalLayout companyValueLayout;

	/** The rendite ek. */
	private Label renditeEK;

	/** The rendite fk. */
	private Label renditeFK;

	/** The gewerbe st. */
	private Label gewerbeSt;

	/** The koerper st. */
	private Label koerperSt;

	/** The gap. */
	private Label gap;

	/** The gap2. */
	private Label gap2;

	/** The expanding gap. */
	private Label expandingGap;

	/** The gap3. */
	private Label gap3;

	/** The expanding gap2. */
	private Label expandingGap2;

	/** The expanding gap3. */
	private Label expandingGap3;

	/** The capital chart layout. */
	private VerticalLayout capitalChartLayout;

	/** The cashflow chart layout. */
	private VerticalLayout cashflowChartLayout;

	/** The gap4. */
	private Label gap4;

	/** The chart area. */
	private HorizontalLayout chartArea;

	/**
	 * Dies ist der Konstruktor, der von Spring nach der Initialierung der
	 * Dependencies aufgerufen wird. Er registriert sich selbst beim Presenter
	 * und initialisiert die View-Komponenten.
	 * 
	 * @author Florian Stier
	 */
	@PostConstruct
	public void init() {
		presenter.setView(this);
		logger.debug("init beendet");
		generateUi();
	}

	/**
	 * Erstelle das GUI zum Prozessschritt "Ausgabe".
	 *
	 * @author Florian Stier
	 */
	private void generateUi() {

		setSizeFull();
		planningLayout = new HorizontalLayout();
		planningLabel = new Label("Planungsprämissen:");
		scenarioLayout = new GridLayout(2, 4);
		renditeEKLabel = new Label("Renditeforderung EK:");
		renditeFKLabel = new Label("Renditeforderung FK:");
		gewerbeStLabel = new Label("Gewerbesteuer:");
		koerperStLabel = new Label("Körperschaftssteuer inkl. Solidaritätszuschlag:");
		renditeEK = new Label();
		renditeFK = new Label();
		gewerbeSt = new Label();
		koerperSt = new Label();
		companyValueLayout = new HorizontalLayout();
		companyValueLabel = new Label("Unternehmenswert:");
		companyValue = new Label();
		gap = new Label();
		gap2 = new Label();
		gap3 = new Label();
		gap4 = new Label();
		expandingGap = new Label();
		expandingGap2 = new Label();
		expandingGap3 = new Label();
		capitalChartLayout = new VerticalLayout();
		cashflowChartLayout = new VerticalLayout();
		chartArea = new HorizontalLayout();

		gap.setWidth("20px");
		gap2.setWidth("20px");
		gap3.setHeight("20px");
		gap4.setHeight("20px");
		expandingGap.setSizeFull();

		planningLayout.setWidth(100, UNITS_PERCENTAGE);
		companyValueLayout.setHeight(60, UNITS_PIXELS);
		companyValueLayout.setWidth(100, UNITS_PERCENTAGE);
		scenarioLayout.setWidth(100, UNITS_PERCENTAGE);
		planningLabel.setWidth(SIZE_UNDEFINED, 0);
		companyValue.setWidth(SIZE_UNDEFINED, 0);
		companyValueLabel.setWidth(SIZE_UNDEFINED, 0);
		capitalChartLayout.setWidth(250, UNITS_PIXELS);
		capitalChartLayout.setHeight(250, UNITS_PIXELS);
		cashflowChartLayout.setWidth(350, UNITS_PIXELS);
		cashflowChartLayout.setHeight(250, UNITS_PIXELS);
		chartArea.setWidth(100, UNITS_PERCENTAGE);
		chartArea.setHeight(SIZE_UNDEFINED, 0);

		planningLabel.setStyleName("font12bold");
		renditeEKLabel.setStyleName("font12bold");
		renditeFKLabel.setStyleName("font12bold");
		gewerbeStLabel.setStyleName("font12bold");
		koerperStLabel.setStyleName("font12bold");
		renditeEK.setStyleName("font12bold");
		renditeFK.setStyleName("font12bold");
		gewerbeSt.setStyleName("font12bold");
		koerperSt.setStyleName("font12bold");
		companyValueLabel.setStyleName("font14bold");
		companyValue.setStyleName("font14bold");

		scenarioLayout.setStyleName("resultScenarioLayout");
		companyValueLayout.setStyleName("companyValueLayout");

		planningLayout.addComponent(planningLabel);
		planningLayout.addComponent(gap2);
		planningLayout.addComponent(scenarioLayout);
		scenarioLayout.addComponent(renditeEKLabel, 0, 0);
		scenarioLayout.addComponent(renditeEK, 1, 0);
		scenarioLayout.addComponent(renditeFKLabel, 0, 1);
		scenarioLayout.addComponent(renditeFK, 1, 1);
		scenarioLayout.addComponent(gewerbeStLabel, 0, 2);
		scenarioLayout.addComponent(gewerbeSt, 1, 2);
		scenarioLayout.addComponent(koerperStLabel, 0, 3);
		scenarioLayout.addComponent(koerperSt, 1, 3);
		companyValueLayout.addComponent(expandingGap2);
		companyValueLayout.addComponent(companyValueLabel);
		companyValueLayout.addComponent(gap);
		companyValueLayout.addComponent(companyValue);
		companyValueLayout.addComponent(expandingGap3);

		planningLayout.setComponentAlignment(planningLabel, Alignment.MIDDLE_LEFT);
		planningLayout.setExpandRatio(scenarioLayout, 1.0f);
		companyValueLayout.setComponentAlignment(companyValueLabel, Alignment.MIDDLE_CENTER);
		companyValueLayout.setComponentAlignment(companyValue, Alignment.MIDDLE_CENTER);
		companyValueLayout.setExpandRatio(expandingGap2, 1.0f);
		companyValueLayout.setExpandRatio(expandingGap3, 1.0f);
		//		scenarioLayout.setColumnExpandRatio(1, 1.0f);
		//		scenarioLayout.setComponentAlignment(renditeEK, Alignment.MIDDLE_RIGHT);
		//		scenarioLayout.setComponentAlignment(renditeFK, Alignment.MIDDLE_RIGHT);
		//		scenarioLayout.setComponentAlignment(gewerbeSt, Alignment.MIDDLE_RIGHT);
		scenarioLayout.setComponentAlignment(koerperSt, Alignment.BOTTOM_CENTER);
		
		chartArea.addComponent(capitalChartLayout);
		chartArea.addComponent(cashflowChartLayout);

		addComponent(planningLayout);
		addComponent(gap3);
		addComponent(companyValueLayout);
		addComponent(gap4);
		addComponent(chartArea);
		addComponent(expandingGap);

		setExpandRatio(expandingGap, 1.0f);
	}

	/* (non-Javadoc)
	 * @see dhbw.ka.mwi.businesshorizon2.ui.resultscreen.onescenario.OneScenarioResultViewInterface#setScenarioValue(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	public void setScenarioValue(String renditeEK, String renditeFK, String gewerbeSt, String koerperSt){
		this.renditeEK.setValue(renditeEK);
		this.renditeFK.setValue(renditeFK);
		this.gewerbeSt.setValue(gewerbeSt);
		this.koerperSt.setValue(koerperSt);		
		logger.debug("Planungsprämissen im UI gesetzt");
	}

	/* (non-Javadoc)
	 * @see dhbw.ka.mwi.businesshorizon2.ui.resultscreen.onescenario.OneScenarioResultViewInterface#setCompanyValue(java.lang.String)
	 */
	public void setCompanyValue(String companyValue){
		this.companyValue.setValue(companyValue);
		logger.debug("Unternehmenswert im UI gesetzt");
	}

	/* (non-Javadoc)
	 * @see dhbw.ka.mwi.businesshorizon2.ui.resultscreen.onescenario.OneScenarioResultViewInterface#setCapitalChart(org.vaadin.vaadinvisualizations.ColumnChart)
	 */
	@Override
	public void setCapitalChart(ColumnChart chart) {
		chart.setSizeFull();
		capitalChartLayout.removeAllComponents();
		capitalChartLayout.addComponent(chart);

	}
	
	/* (non-Javadoc)
	 * @see dhbw.ka.mwi.businesshorizon2.ui.resultscreen.onescenario.OneScenarioResultViewInterface#setCashFlowChart(org.vaadin.vaadinvisualizations.LineChart)
	 */
	public void setCashFlowChart(LineChart chart) {
		chart.setSizeFull();
		cashflowChartLayout.removeAllComponents();
		cashflowChartLayout.addComponent(chart);
	}

}
