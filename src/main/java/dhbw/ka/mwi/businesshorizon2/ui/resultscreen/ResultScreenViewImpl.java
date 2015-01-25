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

package dhbw.ka.mwi.businesshorizon2.ui.resultscreen;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.mvplite.view.View;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
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
public class ResultScreenViewImpl extends VerticalLayout implements ResultScreenViewInterface {
	private static final long serialVersionUID = 1L;
	
	private static final Logger logger = Logger.getLogger("ResultScreenViewImpl.class");

	@Autowired
	private ResultScreenPresenter presenter;
	
	private VerticalLayout vl = new VerticalLayout();
	
	private ProgressIndicator progressIndicator;

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

		progressIndicator = new ProgressIndicator();
		progressIndicator.setIndeterminate(true);
		progressIndicator.setEnabled(true);
		progressIndicator.setStyleName("bar");
		progressIndicator.setCaption("Berechne..");
		
		
		addComponent(progressIndicator);
		setComponentAlignment(progressIndicator, Alignment.MIDDLE_CENTER);
//		addComponent(vl);
		setStyleName("borderless light");
		setWidth(95, UNITS_PERCENTAGE);
		setHeight(100, UNITS_PERCENTAGE);
		setStyleName("projectDetailsLayout");

	}
	
	/**
	 * Diese Methode entfernt den Style projectDetailsLayout, damit die volle Breite genutzt werden kann.
	 * @author Tobias Lindner
	 */
	public void removeStyle () {
		removeStyleName("projectDetailsLayout");
	}
	
//	public void createLayout() {
//		
//		grid = new GridLayout(2, 4);
//		planningLabel = new Label("Planungsprämissen:");
//		companyValueLabel = new Label("Unternehmenswert:");
//		planningLayout = new GridLayout();
//		companyValue = new Label("30.000.000€");
//		
//		grid.setSizeFull();
//		grid.setColumnExpandRatio(1, 5);
//		
//		grid.addComponent(planningLabel, 0, 0);
//		grid.addComponent(planningLayout, 1, 0);
//		grid.addComponent(companyValueLabel, 0, 1);
//		grid.addComponent(companyValue, 1, 1);
//		
//		addComponent(grid);
//		
//	}

	@Override

	public void showOutputView() {
		this.removeAllComponents();
		generateUi();
		
	}
	
	public void showView(View view){
		addComponent((Component)view);
		logger.debug ("ResultScreenview gesetzt: " + view.toString());
	}

	public void addHeadline(Label head) {
		vl.addComponent(head);
	}
	
	
	public void addSubline(Label head) {
		vl.addComponent(head);
	}
	
	public void addSubline(Label head, Label abw) {
		vl.addComponent(head);
		vl.addComponent(abw);
	}
	
	public void addStochasticChartArea(StochasticChartArea chartArea, int number) {
		this.addSubline(new Label("Szenario " + number), chartArea.getModulAbweichung());
		
		HorizontalLayout outputArea = new HorizontalLayout();
		outputArea.addComponent(chartArea);
		vl.addComponent(outputArea);
	}


	@Override
	public void addDeterministicChartArea(DeterministicChartArea chartArea, int number) {
		this.addSubline(new Label("Szenario " + number));
		
		HorizontalLayout outputArea = new HorizontalLayout();
		outputArea.addComponent(chartArea);
		vl.addComponent(outputArea);
	}
	
	
	

	
	@Override
	public void showErrorMessge(String message) {
		getWindow().showNotification((String) "Berechnung fehlgeschlagen", message, Notification.TYPE_ERROR_MESSAGE);

	}

	@Override
	public void changeProgress(float progress) {
		if (progress == 1) {
			progressIndicator.setEnabled(false);
			removeComponent(progressIndicator);
		} else {
			progressIndicator.setEnabled(true);
			
		}

	}
	
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
