/*******************************************************************************
* BusinessHorizon2
*
* Copyright (C) 2012-2013 Christian Gahlert, Florian Stier, Kai Westerholz,
* Timo Belz, Daniel Dengler, Katharina Huber, Christian Scherer, Julius Hacker
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


package dhbw.ka.mwi.businesshorizon2.ui.process.method;

import java.util.Set;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.terminal.UserError;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Reindeer;

import dhbw.ka.mwi.businesshorizon2.methods.AbstractCalculationMethod;
import dhbw.ka.mwi.businesshorizon2.methods.AbstractStochasticMethod;
import dhbw.ka.mwi.businesshorizon2.methods.discountedCashflow.APV;
import dhbw.ka.mwi.businesshorizon2.methods.discountedCashflow.FTE;
import dhbw.ka.mwi.businesshorizon2.models.CashflowSource;
import dhbw.ka.mwi.businesshorizon2.models.InputType;



/**

 * Diese Klasse implementiert das GUI fuer den Prozessschritt "Methoden" in Vaadin.
 * 
 * @author Julius Hacker, Annika Weis, Mirko Göpfrich
 *
 */


public class MethodViewImpl extends VerticalLayout implements MethodViewInterface {


	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger("MethodViewImpl.class");
	
	@Autowired
	private MethodPresenter presenter;
	
	private VerticalLayout methodList;
	private HorizontalLayout full;
	
	private HorizontalLayout berechnungsmethoden;
	private HorizontalLayout erklaerungsFenster;
	
	private Label erklaerung;
	private Panel panel;
	
	// Berechnungsmethode: Panel für die Auswahl zischen APV und FTE
	private Panel calculationMethodPanel;
	// Herkunft der Cashflows: Panel für die Auwahl zwischen Deterministisch und Stochastisch;
	private Panel cashflowSourcePanel;
	// Stochastische Methode: momentan nur Zeitreihenanalyse. Deswegen auskommentieren.
	//private Panel stochasticMethodPanel; //
	// Eingabe der Cashflows: Panels für die Auswahl der Eingabemethode (nur bei APV)
	private Panel stochasticInputPanel;
	private Panel deterministicInputPanel;
	
	//Die entsprechenden OptionGroups (RadioButton) für die Auswahlpanels.
	private OptionGroup calculationMethod;
	private OptionGroup cashflowSource;
	//private OptionGroup stochasticMethod; //
	private OptionGroup deterministicInput;
	private OptionGroup stochasticInput;
	
	private AbstractCalculationMethod apv;
	private AbstractCalculationMethod fte;
	
	/**
	 * Dies ist der Konstruktor, der von Spring nach der Initialierung der Dependencies 
	 * aufgerufen wird. Er registriert sich selbst beim Presenter und initialisiert die 
	 * View-Komponenten.
	 * 
	 * @author Julius Hacker
	 */
	
	@PostConstruct
	public void init() {
		presenter.setView(this);
	}
	
	public void showMethodView() {
		logger.debug("showMethodView aufgerufen");
		
		this.removeAllComponents();
		logger.debug("Alle Komponenten entfernt");	
		
		//Auswahlpanels initialisieren
		calculationMethodPanel = new Panel("Berechnungsmethode:");
		calculationMethodPanel.addStyleName("light");
		calculationMethodPanel.setWidth("700px");
		calculationMethodPanel.setHeight("150px");
		
		cashflowSourcePanel = new Panel("Herkunft der Cashflows:");
		cashflowSourcePanel.addStyleName("light");
		cashflowSourcePanel.setWidth("700px");
		cashflowSourcePanel.setHeight("150px");
		
		deterministicInputPanel = new Panel("Zukünftige Perioden (deterministisch):");
		deterministicInputPanel.addStyleName("light");
		deterministicInputPanel.setWidth("700px");
		deterministicInputPanel.setHeight("150px");
		
		stochasticInputPanel = new Panel("Vergangene Perioden (stochastisch):");
		stochasticInputPanel.addStyleName("light");
		stochasticInputPanel.setHeight("150px");
		stochasticInputPanel.setWidth("700px");
		
		logger.debug("Panels mit Ueberschrift erstellt");

		 // OptionGroup für Auswahl der Berechnungsmethoden erstellen
		calculationMethod = new OptionGroup();
		calculationMethod.setMultiSelect(false);
		calculationMethod.setImmediate(true);
		calculationMethod.addListener(new Property.ValueChangeListener() {
			private static final long serialVersionUID = 1L;
			
			@Override
			public void valueChange(ValueChangeEvent event) {
				logger.debug("calculationMethod geklickt");
				presenter.saveCalculationMethod((AbstractCalculationMethod) calculationMethod.getValue());
			}
		});
		
		// OptipnGroup für Auswahl der Herkunft der Cashflows
		cashflowSource = new OptionGroup();
		cashflowSource.setMultiSelect(false);
		cashflowSource.setImmediate(true);
		cashflowSource.addListener(new Property.ValueChangeListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				logger.debug("calculationSource geklickt");
				presenter.saveCashflowSource((CashflowSource) cashflowSource.getValue());;	
			}	
		});
		
		deterministicInput = new OptionGroup();
		deterministicInput.setMultiSelect(false);
		deterministicInput.setImmediate(true);
		deterministicInput.addListener(new Property.ValueChangeListener() {
			private static final long serialVersionUID = 1L;


			@Override
			public void valueChange(ValueChangeEvent event) {
				logger.debug("deterministicInput geklickt");
				InputType selected = (InputType) event.getProperty().getValue();
				presenter.saveDeterministicInput(selected);				
			}
		});
		
		stochasticInput = new OptionGroup();
		stochasticInput.setMultiSelect(false);
		stochasticInput.setImmediate(true);
		stochasticInput.addListener(new Property.ValueChangeListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				logger.debug("stochasticInput geklickt");
				InputType selected = (InputType) event.getProperty().getValue();
				presenter.saveStochasticInput(selected);	
			}
		});
		
		logger.debug("OptionGroups ohne Items erstellt");
		
		
		initOptionGroups();
		
		generateUi();
		
		logger.debug("showMethodView abgeschlossen");
		
	}

	private void initOptionGroups() {
		
		apv = new APV();
		fte = new FTE();
		
		calculationMethod.addItem(apv);
		calculationMethod.addItem(fte);
		calculationMethodPanel.addComponent(calculationMethod);
		
		cashflowSource.addItem(CashflowSource.DETERMINISTIC);
		cashflowSource.addItem(CashflowSource.STOCHASTIC);
		cashflowSourcePanel.addComponent(cashflowSource);
		
		deterministicInput.addItem(InputType.DIRECT);
		deterministicInput.addItem(InputType.DIRECTCALCULATION);
		deterministicInput.addItem(InputType.INDIRECTCALCULATION);
		deterministicInputPanel.addComponent(deterministicInput);
		
		stochasticInput.addItem(InputType.DIRECT);
		stochasticInput.addItem(InputType.DIRECTCALCULATION);
		stochasticInput.addItem(InputType.INDIRECTCALCULATION);
		stochasticInputPanel.addComponent(stochasticInput);
		
		logger.debug("Items den OptionGroups hinzugefuegt, außer der calculationMethod");
		logger.debug("OptionGroups den Panels hinzugefuegt");
	}

	/**
	 * Erstelle das GUI zum Prozessschritt "Methoden"
	 * 
	 * @author Julius Hacker
	 */
	private void generateUi() {
		
		panel = new Panel();
		panel.setWidth("500px");
		panel.setHeight("450px");
		
		full = new HorizontalLayout();
		
		berechnungsmethoden = new HorizontalLayout();
		berechnungsmethoden.setWidth("50%");
		erklaerungsFenster = new HorizontalLayout();
		
		erklaerung = new Label("<b>Hier können Sie die Berechnungsmethode wählen. Zur Auswahl stehen Ihnen die beiden folgenden Verfahren:</b></br></br>"
							+ " - Adjusted-Present-Value </br></br> - Flow-to-Equity </br></br>"
							+ "Bei der Adjusted-Present-Value Methode berechnet sich der Unternehmenswert aus dem Wert des rein eigenfinanzierten "
							+ "Unternehmens zuzüglich der Steuervorteile durch das ver-zinsliche Fremdkapital, abzüglich des verzinslichen Fremdkapitals. </br> </br>"
							+ "Die Flow-to-Equity Methode diskontiert alle zukünftigen Cashflows auf einen Stichtag und addiert den diskontierten Restwert."
							+ "Folglich betrachtet dieses Verfahren für die Be-rechnung des Unternehmenswertes ausschließlich das Eigenkapital.");
		
		erklaerung.setContentMode(Label.CONTENT_XHTML);
		
		panel.addComponent(erklaerung);
				
		methodList = new VerticalLayout();
		//methodList.setSizeFull();
		
		
		
		this.addComponent(full);
		
		/*
		methodList.addComponent(calculationMethodPanel);
		methodList.addComponent(cashflowSourcePanel);
		methodList.addComponent(deterministicInputPanel);
		methodList.addComponent(stochasticInputPanel);
		*/
		
		methodList.addComponent(calculationMethodPanel);
		methodList.addComponent(cashflowSourcePanel);
		methodList.addComponent(deterministicInputPanel);
		methodList.addComponent(stochasticInputPanel);
		
		berechnungsmethoden.addComponent(methodList);
		methodList.addComponent(erklaerungsFenster);
		erklaerungsFenster.addComponent(panel);
		erklaerungsFenster.setComponentAlignment(panel, Alignment.TOP_RIGHT);
		
		full.addComponent(berechnungsmethoden);
		full.setComponentAlignment(berechnungsmethoden, Alignment.TOP_LEFT);
		full.addComponent(erklaerungsFenster);
		full.setComponentAlignment(erklaerungsFenster, Alignment.TOP_RIGHT);
		
		logger.debug("Panels dem Layout hinzugefuegt");
		
		logger.debug("genarateUI abgeschlossen");
		
	
	}

	/**Fügt die Berechungsmethoden zur OptionGroup hinu
	 * 
	 */
	/**@Override
	public void addCalculationMethod(AbstractCalculationMethod method) {
		calculationMethod.addItem(method);
		calculationMethod.setItemEnabled(method, method.getImplemented());
		
		if (method.isSelected()){
			calculationMethod.select(method);
		}
	}*/
		
	
	@Override
	public void showCashflowSourcePanel(Boolean calculationMethodChecked) {
			cashflowSourcePanel.setVisible(calculationMethodChecked);
	}
	
	@Override
	public void hideInputPanels() {
		deterministicInputPanel.setVisible(false);
		methodList.setComponentAlignment(deterministicInputPanel, Alignment.MIDDLE_CENTER);
		stochasticInputPanel.setVisible(false);
	}
	
	@Override
	public void showDeterministicInputPanel() {
		deterministicInputPanel.setVisible(true);
		stochasticInputPanel.setVisible(false);
	}

	@Override
	public void showStochasticInputPanel() {
		stochasticInputPanel.setVisible(true);
		deterministicInputPanel.setVisible(false);
	
	}

	@Override
	public void selectCalculation(AbstractCalculationMethod selected){
		if (selected instanceof APV){
			calculationMethod.select(apv);
		}
		else if (selected instanceof FTE){
			calculationMethod.select(fte);
		}
	}
	
	@Override
	public void selectCashflow(CashflowSource selected){
		cashflowSource.select(selected);
	}
	
	@Override
	public void selectInput(Boolean stochastic, InputType selected) {
		if (stochastic){
			stochasticInput.select(selected);
		}
		else{
			deterministicInput.select(selected);
		}
		
	}

	/**@Override
	public void showErrorNoMethodSelected(Boolean state) {
		if (!state){
		//this.methods.setComponentError(new UserError("Wenn stochastisch gew\u00E4hlt wurde, muss mindestens eine Methode ausw\u00E4hlt werden"));
		this.stochasticMethod.setComponentError(new UserError("Es muss mindestens eine Methode ausw\u00E4hlt werden"));
		}
		else {
			this.stochasticMethod.setComponentError(null);
		}
	}*/

	@Override
	public void showErrorNothingSelected(Boolean state) {
		/**
		if (!state) {
			this.stochasticCheckbox.setComponentError(new UserError("Es muss mindestens eine Cashflowherkunft ausgew\u00E4hlt werden"));
		}
		else {
			this.stochasticCheckbox.setComponentError(null);
		}
		*/
		
	}

}

