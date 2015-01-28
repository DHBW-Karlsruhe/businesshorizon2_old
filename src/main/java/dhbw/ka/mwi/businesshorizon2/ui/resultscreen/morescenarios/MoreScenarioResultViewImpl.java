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

package dhbw.ka.mwi.businesshorizon2.ui.resultscreen.morescenarios;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.vaadinvisualizations.ColumnChart;

import com.vaadin.data.Item;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.ProgressIndicator;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window.Notification;

import dhbw.ka.mwi.businesshorizon2.ui.process.output.charts.BasicLineChart;
import dhbw.ka.mwi.businesshorizon2.ui.process.output.charts.DeterministicChartArea;
import dhbw.ka.mwi.businesshorizon2.ui.process.output.charts.StochasticChartArea;

/**
 * Diese Klasse implementiert das GUI fuer den Prozessschritt "Ausgabe" in
 * Vaadin.
 * 
 * @author Florian Stier, Mirko Göpfrich, Tobias Lindner
 * 
 */
public class MoreScenarioResultViewImpl extends VerticalLayout implements MoreScenarioResultViewInterface {
	private static final long serialVersionUID = 1L;
	
	private static final Logger logger = Logger.getLogger("MoreScenarioResultViewImpl.class");

	@Autowired
	private MoreScenarioResultPresenter presenter;
	
	private VerticalLayout vl = new VerticalLayout();
	
	private ProgressIndicator progressIndicator;

	private GridLayout grid;

	private Label planningLabel;
	
	private Label renditeEKLabel1;
	private Label renditeFKLabel1;
	private Label gewerbeStLabel1;
	private Label koerperStLabel1;
	private Label renditeEK1;
	private Label renditeFK1;
	private Label gewerbeSt1;
	private Label koerperSt1;
	private Label companyValue1;
	
	private Label renditeEKLabel2;
	private Label renditeFKLabel2;
	private Label gewerbeStLabel2;
	private Label koerperStLabel2;
	private Label renditeEK2;
	private Label renditeFK2;
	private Label gewerbeSt2;
	private Label koerperSt2;
	private Label companyValue2;
	
	private Label renditeEKLabel3;
	private Label renditeFKLabel3;
	private Label gewerbeStLabel3;
	private Label koerperStLabel3;
	private Label renditeEK3;
	private Label renditeFK3;
	private Label gewerbeSt3;
	private Label koerperSt3;
	private Label companyValue3;

	private Label companyValueLabel;

	private GridLayout planningLayout;
	private GridLayout planningGridScenario1;
	private GridLayout planningGridScenario2;
	private GridLayout planningGridScenario3;
	
	private GridLayout companyValueLayoutScenario1;
	private GridLayout companyValueLayoutScenario2;
	private GridLayout companyValueLayoutScenario3;
	
	private GridLayout capitalStructureChartScenario1;
	private GridLayout capitalStructureChartScenario2;
	private GridLayout capitalStructureChartScenario3;
	
	private Label companyValue;
	
	private Table planningTable;
	
	private Item row1;
	private Item row2;
	private Item row3;
	
	private Object itemId1;
	private Object itemId2;
	private Object itemId3;

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
	 * @author Tobias Lindner
	 */
	private void generateUi() {
		setSizeFull();
		
		planningLayout = new GridLayout (4, 3);
		
		planningTable = new Table();
		
		planningTable.setWidth(100, UNITS_PERCENTAGE);
		planningTable.setPageLength(4);
		
		planningTable.addContainerProperty("first", String.class, null);
		planningTable.setColumnHeader("first", "");
		
		planningTable.addContainerProperty("scenario1", GridLayout.class, null);
		planningTable.setColumnHeader("scenario1", "Szenario 1");
		
		planningTable.addContainerProperty("scenario2", GridLayout.class, null);
		planningTable.setColumnHeader("scenario2", "Szenario 2");
		
		itemId1 = planningTable.addItem();
		row1 = planningTable.getItem(itemId1);
		row1.getItemProperty("first").setValue("Planungsprämissen");
		itemId2 = planningTable.addItem();
		row2 = planningTable.getItem(itemId2);
		row2.getItemProperty("first").setValue("Unternehmenswert");
		itemId3 = planningTable.addItem();
		row3 = planningTable.getItem(itemId3);
		row3.getItemProperty("first").setValue("Kapitalstruktur");
		
		planningGridScenario1 = new GridLayout(2,4);
		planningGridScenario2 = new GridLayout(2,4);
		planningGridScenario3 = new GridLayout(2,4);
		
		companyValueLayoutScenario1 = new GridLayout();
		companyValueLayoutScenario2 = new GridLayout();
		companyValueLayoutScenario3 = new GridLayout();
		
		capitalStructureChartScenario1 = new GridLayout(1, 1);
//		capitalStructureChartScenario1.setSizeFull();
		capitalStructureChartScenario2 = new GridLayout();
		capitalStructureChartScenario3 = new GridLayout();
		
		row1.getItemProperty("scenario1").setValue(planningGridScenario1);
		row1.getItemProperty("scenario2").setValue(planningGridScenario2);
		
		row2.getItemProperty("scenario1").setValue(companyValueLayoutScenario1);
		row2.getItemProperty("scenario2").setValue(companyValueLayoutScenario2);
		
		row3.getItemProperty("scenario1").setValue(capitalStructureChartScenario1);
		row3.getItemProperty("scenario2").setValue(capitalStructureChartScenario2);
		
		createScenario1Layout();
		createScenario2Layout();
		createScenario3Layout();
		
		addComponent(planningTable);
		

		planningTable.setCellStyleGenerator(new Table.CellStyleGenerator() {
			private static final long serialVersionUID = 1L;

			@Override
			public String getStyle(Object itemId, Object propertyId) {
				if (propertyId == null) {
					if (itemId == itemId2) {
						return "yello-row";
					}
					
					if (itemId == itemId1) {
						return "table-line";
					}
					
					if (itemId == itemId3) {
						return "table-capital";
					}
						return null;	
				}
				
				else {
					return null;
				}
			}
		});
		
		planningTable.addStyleName("scenarioTable");

	}
	
	/**
	 * Diese Methode fügt die 3. Spalte der Tabelle mit den Daten zum Szenario 3 hinzu.
	 * 
	 * @author Tobias Lindner
	 */
	public void addScenario3ToLayout () {
		planningTable.addContainerProperty("scenario3", GridLayout.class, null);
		planningTable.setColumnHeader("scenario3", "Szenario 3");
		
		row1.getItemProperty("scenario3").setValue(planningGridScenario3);
		row2.getItemProperty("scenario3").setValue(companyValueLayoutScenario3);
		row3.getItemProperty("scenario3").setValue(capitalStructureChartScenario3);
		
	}
	
	
	/**
	 * Diese Methode erzeugt die Anzeige der Planungsprämissen für Szenario 1
	 * 
	 * @author Tobias Lindner
	 */
	public void createScenario1Layout() {
		renditeEKLabel1 = new Label("Renditeforderung EK:");
		renditeFKLabel1 = new Label("Renditeforderung FK:");
		gewerbeStLabel1 = new Label("Gewerbesteuer:");
		koerperStLabel1 = new Label("Körperschaftssteuer <br> inkl. Solidaritätszuschlag:  ");
		koerperStLabel1.setContentMode(Label.CONTENT_XHTML);
		renditeEK1 = new Label();
		renditeFK1 = new Label();
		gewerbeSt1 = new Label();
		koerperSt1 = new Label();
		
		companyValue1 = new Label();
		
		planningGridScenario1.addComponent(renditeEKLabel1, 0, 0);
		planningGridScenario1.addComponent(renditeEK1, 1, 0);
		planningGridScenario1.addComponent(renditeFKLabel1, 0, 1);
		planningGridScenario1.addComponent(renditeFK1, 1, 1);
		planningGridScenario1.addComponent(gewerbeStLabel1, 0, 2);
		planningGridScenario1.addComponent(gewerbeSt1, 1, 2);
		planningGridScenario1.addComponent(koerperStLabel1, 0, 3);
		planningGridScenario1.addComponent(koerperSt1, 1, 3);
		planningGridScenario1.setComponentAlignment(koerperSt1, Alignment.MIDDLE_LEFT);
		
		companyValueLayoutScenario1.addComponent(companyValue1);
		
	}
	
	/**
	 * Diese Methode erzeugt die Anzeige der Planungsprämissen für Szenario 2
	 * 
	 * @author Tobias Lindner
	 */
	public void createScenario2Layout() {
		renditeEKLabel2 = new Label("Renditeforderung EK:");
		renditeFKLabel2 = new Label("Renditeforderung FK:");
		gewerbeStLabel2 = new Label("Gewerbesteuer:");
		koerperStLabel2 = new Label("Körperschaftssteuer <br> inkl. Solidaritätszuschlag:  ");
		koerperStLabel2.setContentMode(Label.CONTENT_XHTML);
		renditeEK2 = new Label();
		renditeFK2 = new Label();
		gewerbeSt2 = new Label();
		koerperSt2 = new Label();
		
		companyValue2 = new Label();
		
		planningGridScenario2.addComponent(renditeEKLabel2, 0, 0);
		planningGridScenario2.addComponent(renditeEK2, 1, 0);
		planningGridScenario2.addComponent(renditeFKLabel2, 0, 1);
		planningGridScenario2.addComponent(renditeFK2, 1, 1);
		planningGridScenario2.addComponent(gewerbeStLabel2, 0, 2);
		planningGridScenario2.addComponent(gewerbeSt2, 1, 2);
		planningGridScenario2.addComponent(koerperStLabel2, 0, 3);
		planningGridScenario2.addComponent(koerperSt2, 1, 3);
		planningGridScenario2.setComponentAlignment(koerperSt2, Alignment.MIDDLE_LEFT);
		
		companyValueLayoutScenario2.addComponent(companyValue2);
		
	}
	
	/**
	 * Diese Methode erzeugt die Anzeige der Planungsprämissen für Szenario 3
	 * 
	 * @author Tobias Lindner
	 */
	public void createScenario3Layout() {
		renditeEKLabel3 = new Label("Renditeforderung EK:");
		renditeFKLabel3 = new Label("Renditeforderung FK:");
		gewerbeStLabel3 = new Label("Gewerbesteuer:");
		koerperStLabel3 = new Label("Körperschaftssteuer <br> inkl. Solidaritätszuschlag:  ");
		koerperStLabel3.setContentMode(Label.CONTENT_XHTML);
		renditeEK3 = new Label();
		renditeFK3 = new Label();
		gewerbeSt3 = new Label();
		koerperSt3 = new Label();
		
		companyValue3 = new Label();
		
		planningGridScenario3.addComponent(renditeEKLabel3, 0, 0);
		planningGridScenario3.addComponent(renditeEK3, 1, 0);
		planningGridScenario3.addComponent(renditeFKLabel3, 0, 1);
		planningGridScenario3.addComponent(renditeFK3, 1, 1);
		planningGridScenario3.addComponent(gewerbeStLabel3, 0, 2);
		planningGridScenario3.addComponent(gewerbeSt3, 1, 2);
		planningGridScenario3.addComponent(koerperStLabel3, 0, 3);
		planningGridScenario3.addComponent(koerperSt3, 1, 3);
		planningGridScenario3.setComponentAlignment(koerperSt3, Alignment.MIDDLE_LEFT);
		
		companyValueLayoutScenario3.addComponent(companyValue3);
		
	}
	
	public void addChartScenario(int numScenario, ColumnChart chart) {
		switch (numScenario) {
		case 0:
			chart.setSizeFull();
			capitalStructureChartScenario1.removeAllComponents();
			capitalStructureChartScenario1.addComponent(chart);
			capitalStructureChartScenario1.setHeight(240, UNITS_PIXELS);
			capitalStructureChartScenario1.setWidth(200, UNITS_PIXELS);
			capitalStructureChartScenario1.setComponentAlignment(chart, Alignment.TOP_CENTER);
			break;
			
		case 1:
			chart.setSizeFull();
			capitalStructureChartScenario2.removeAllComponents();
			capitalStructureChartScenario2.addComponent(chart);
			capitalStructureChartScenario2.setHeight(240, UNITS_PIXELS);
			capitalStructureChartScenario2.setWidth(200, UNITS_PIXELS);
			capitalStructureChartScenario2.setComponentAlignment(chart, Alignment.TOP_CENTER);
			break;
			
		case 2:
			capitalStructureChartScenario3.removeAllComponents();
			capitalStructureChartScenario3.addComponent(chart);
			capitalStructureChartScenario3.setHeight(240, UNITS_PIXELS);
			capitalStructureChartScenario3.setWidth(200, UNITS_PIXELS);
			capitalStructureChartScenario3.setComponentAlignment(chart, Alignment.TOP_LEFT);
			break;
		}
	}
	
	/**
	 * Diese Methode wird vom Presenter dazu genutzt, die Planungswerte, sowie den Unternehmenswert im UI darszustellen
	 * 
	 * @author Tobias Lindner
	 */
	public void setScenarioValue(int numScenario, String renditeEK, String renditeFK, String gewerbeSt, String koerperSt, String companyValue){
		switch (numScenario) {
		case 0:
			this.renditeEK1.setValue(renditeEK);
			this.renditeFK1.setValue(renditeFK);
			this.gewerbeSt1.setValue(gewerbeSt);
			this.koerperSt1.setValue(koerperSt);
			this.companyValue1.setValue(companyValue);
			logger.debug("Values von Szenario 1 gesetzt");
			break;
			
		case 1:
			this.renditeEK2.setValue(renditeEK);
			this.renditeFK2.setValue(renditeFK);
			this.gewerbeSt2.setValue(gewerbeSt);
			this.koerperSt2.setValue(koerperSt);
			this.companyValue2.setValue(companyValue);
			logger.debug("Values von Szenario 2 gesetzt");
			break;
			
		case 2:
			this.renditeEK3.setValue(renditeEK);
			this.renditeFK3.setValue(renditeFK);
			this.gewerbeSt3.setValue(gewerbeSt);
			this.koerperSt3.setValue(koerperSt);
			this.companyValue3.setValue(companyValue);
			logger.debug("Values von Szenario 3 gesetzt");
			break;			

		}
	}
	
	public void createLayout() {
		logger.debug ("createLayout");
		
		grid = new GridLayout(2, 4);
		planningLabel = new Label("Planungsprämissen:");
		companyValueLabel = new Label("Unternehmenswert:");
		planningLayout = new GridLayout();
		companyValue = new Label("30.000.000€");
		
		grid.setSizeFull();
		grid.setColumnExpandRatio(1, 5);
		
		grid.addComponent(planningLabel, 0, 0);
		grid.addComponent(planningLayout, 1, 0);
		grid.addComponent(companyValueLabel, 0, 1);
		grid.addComponent(companyValue, 1, 1);
		
		addComponent(grid);
		
	}

	@Override

	public void showOutputView() {
		this.removeAllComponents();
		generateUi();
		
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
