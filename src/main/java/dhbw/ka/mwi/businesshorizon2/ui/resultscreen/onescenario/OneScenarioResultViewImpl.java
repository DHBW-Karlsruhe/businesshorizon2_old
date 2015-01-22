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

	@Autowired
	private OneScenarioResultPresenter presenter;

	private GridLayout grid;

	private Label planningLabel;

	private Label companyValueLabel;

	private GridLayout planningLayout;

	private Label companyValue;

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
	}

	/**
	 * Erstelle das GUI zum Prozessschritt "Ausgabe"
	 * 
	 * @author Florian Stier
	 */
	private void generateUi() {

		setSizeFull();

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
