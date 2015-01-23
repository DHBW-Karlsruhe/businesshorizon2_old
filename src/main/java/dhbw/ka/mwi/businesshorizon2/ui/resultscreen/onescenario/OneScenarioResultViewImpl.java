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

import com.vaadin.ui.Alignment;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.ProgressIndicator;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window.Notification;

import dhbw.ka.mwi.businesshorizon2.ui.process.output.charts.BasicLineChart;
import dhbw.ka.mwi.businesshorizon2.ui.process.output.charts.DeterministicChartArea;
import dhbw.ka.mwi.businesshorizon2.ui.process.output.charts.StochasticChartArea;

/**
 * Diese Klasse implementiert das GUI fuer den Prozessschritt "Ausgabe" in
 * Vaadin.
 * 
 * @author Florian Stier, Mirko Göpfrich
 * 
 */
public class OneScenarioResultViewImpl extends VerticalLayout implements OneScenarioResultViewInterface {
	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger
			.getLogger("OneScenarioResultViewImpl.class");
	
	@Autowired
	private OneScenarioResultPresenter presenter;

	private Label planningLabel;

	private Label companyValueLabel;

	private HorizontalLayout planningLayout;

	private Label companyValue;

	private GridLayout scenarioLayout;

	private Label renditeEKLabel;

	private Label renditeFKLabel;

	private Label gewerbeStLabel;

	private Label koerperStLabel;

	private HorizontalLayout companyValueLayout;

	private Label renditeEK;

	private Label renditeFK;

	private Label gewerbeSt;

	private Label koerperSt;

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
		generateUi();
	}

	/**
	 * Erstelle das GUI zum Prozessschritt "Ausgabe"
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
		
		planningLabel.setStyleName("font12bold");
		renditeEKLabel.setStyleName("font12bold");
		renditeFKLabel.setStyleName("font12bold");
		gewerbeStLabel.setStyleName("font12bold");
		koerperStLabel.setStyleName("font12bold");
		renditeEK.setStyleName("font12bold");
		renditeFK.setStyleName("font12bold");
		gewerbeSt.setStyleName("font12bold");
		koerperSt.setStyleName("font12bold");
		companyValueLabel.setStyleName("font12bold");
		companyValue.setStyleName("font12bold");
		
		planningLayout.addComponent(planningLabel);
		planningLayout.addComponent(scenarioLayout);
		scenarioLayout.addComponent(renditeEKLabel, 0, 0);
		scenarioLayout.addComponent(renditeEK, 1, 0);
		scenarioLayout.addComponent(renditeFKLabel, 0, 1);
		scenarioLayout.addComponent(renditeFK, 1, 1);
		scenarioLayout.addComponent(gewerbeStLabel, 0, 2);
		scenarioLayout.addComponent(gewerbeSt, 1, 2);
		scenarioLayout.addComponent(koerperStLabel, 0, 3);
		scenarioLayout.addComponent(koerperSt, 1, 3);
		companyValueLayout.addComponent(companyValueLabel);
		companyValueLayout.addComponent(companyValue);
		
		addComponent(planningLayout);
		addComponent(companyValueLayout);
	}
	
	public void addStochasticChartArea(StochasticChartArea chartArea, int number) {
//		this.addSubline(new Label("Szenario " + number), chartArea.getModulAbweichung());
//		
//		HorizontalLayout outputArea = new HorizontalLayout();
//		outputArea.addComponent(chartArea);
//		vl.addComponent(outputArea);
	}


	@Override
	public void addDeterministicChartArea(DeterministicChartArea chartArea, int number) {
//		this.addSubline(new Label("Szenario " + number));
//		
//		HorizontalLayout outputArea = new HorizontalLayout();
//		outputArea.addComponent(chartArea);
//		vl.addComponent(outputArea);
	}
	
	public void setScenarioValue(String renditeEK, String renditeFK, String gewerbeSt, String koerperSt){
		this.renditeEK.setValue(renditeEK);
		this.renditeFK.setValue(renditeFK);
		this.gewerbeSt.setValue(gewerbeSt);
		this.koerperSt.setValue(koerperSt);		
		logger.debug("Planungsprämissen im UI gesetzt");
	}
	
	public void setCompanyValue(String companyValue){
		this.companyValue.setValue(companyValue);
		logger.debug("Unternehmenswert im UI gesetzt");
	}
	
//	@Override
//	public void showErrorMessge(String message) {
//		getWindow().showNotification((String) "Berechnung fehlgeschlagen", message, Notification.TYPE_ERROR_MESSAGE);
//
//	}

	
	/**
	 * @author Annika Weis
	 * @param Label
	 * @return	void
	 * 
	 * Gibt das angegebene Label aus
	 */
	public void addLabel(Label label){
		addComponent(label);		
	}

}
