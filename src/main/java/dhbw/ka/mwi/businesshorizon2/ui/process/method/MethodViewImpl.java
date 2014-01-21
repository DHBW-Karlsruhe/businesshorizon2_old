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
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Label;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Reindeer;

import dhbw.ka.mwi.businesshorizon2.methods.AbstractCalculationMethod;
import dhbw.ka.mwi.businesshorizon2.methods.AbstractStochasticMethod;
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
	
	// Berechnungsmethode: Panel für die Auswahl zischen APV und FTE
	private Panel calculationMethodPanel;
	// Herkunft der Cashflows: Panel für die Auwahl zwischen Deterministisch und Stochastisch;
	private Panel cashflowSourcePanel;
	// Stochastische Methode: momentan nur Zeitreihenanalyse. Deswegen auskommentieren.
	private Panel stochasticMethodPanel; //
	// Eingabe der Cashflows: Panels für die Auswahl der Eingabemethode (nur bei APV)
	private Panel stochasticInputPanel;
	private Panel deterministicInputPanel;
	
	//Die entsprechenden OptionGroups (RadioButton) für die Auswahlpanels.
	private OptionGroup calculationMethod;
	private OptionGroup cashflowSource;
	private OptionGroup stochasticMethod; //
	private OptionGroup deterministicInput;
	private OptionGroup stochasticInput;
	
	//auskommentieren
	private CheckBox stochasticCheckbox; //= new CheckBox("Stochastische Berechnung");
	private CheckBox deterministicCheckbox; //= new CheckBox("Deterministische Eingabe");
	
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
		
		this.removeAllComponents();
		
		stochasticCheckbox = new CheckBox("Stochastische Berechnung"); //
		deterministicCheckbox = new CheckBox("Deterministische Eingabe"); //
		
		//?
		stochasticCheckbox.setImmediate(true); //
		deterministicCheckbox.setImmediate(true); //	
		
		//Auswahlpanels initialisieren
		calculationMethodPanel = new Panel("Berechnungsmethode:");
		calculationMethodPanel.addStyleName("light");
		
		cashflowSourcePanel = new Panel("Herkunft der Cashflows:");
		cashflowSourcePanel.addStyleName("light");
		
		//deaktivieren
		stochasticMethodPanel = new Panel("Stochastische Methode:"); //
		stochasticMethodPanel.addStyleName("light"); //
		
		deterministicInputPanel = new Panel("Zukünftige Perioden (deterministisch):");
		deterministicInputPanel.addStyleName("light");
		
		stochasticInputPanel = new Panel("Vergangene Perioden (stochastisch):");
		stochasticInputPanel.addStyleName("light");
		
		
		//entfernen
		stochasticCheckbox.addListener(new Button.ClickListener() { //
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				presenter.toggleStochasticMethodType(true,event.getButton().booleanValue());				
			}
		});
		
		//entfernen
		/**deterministicCheckbox.addListener(new Button.ClickListener() { //
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				presenter.toggleDeterministicMethodType(true,event.getButton().booleanValue());
				
			}
		});*/

		 // OptionGroup für Auswahl der Berechnungsmethoden erstellen
		calculationMethod = new OptionGroup();
		calculationMethod.setMultiSelect(false);
		calculationMethod.setImmediate(true);
		calculationMethod.addListener(new Property.ValueChangeListener() {
			private static final long serialVersionUID = 1L;
			
			@Override
			public void valueChange(ValueChangeEvent event) {
				presenter.toggleCalculationMethod((AbstractCalculationMethod) calculationMethod.getValue());
			}
		});
		
		// OptipnGroup für Auswahl der Herkunft der Cashflows
		cashflowSource = new OptionGroup();
		cashflowSource.setMultiSelect(false);
		//cashflowSource.setImmediate(true);
		cashflowSource.addListener(new Property.ValueChangeListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				InputType selected = (InputType) event.getProperty().getValue();
				presenter.toggleCashflowSource(true,selected);	
			}
		});
		
		// ff deaktivieren
		stochasticMethod = new OptionGroup();
		stochasticMethod.setMultiSelect(false);
		//stochasticMethod.setImmediate(true);
		stochasticMethod.addListener(new Property.ValueChangeListener() {
			
			private static final long serialVersionUID = 1L;

			@SuppressWarnings("unchecked")
			@Override
			public void valueChange(ValueChangeEvent event) {
				presenter.toggleStochasticMethod((Set<AbstractStochasticMethod>) stochasticMethod.getValue());
			}
		});
		
		stochasticInput = new OptionGroup();
		stochasticInput.setMultiSelect(false);
		stochasticInput.setImmediate(true);
		stochasticInput.addListener(new Property.ValueChangeListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				InputType selected = (InputType) event.getProperty().getValue();
				presenter.toggleStochasticInput(true,selected);	
			}
		});
		
		deterministicInput = new OptionGroup();
		deterministicInput.setMultiSelect(false);
		deterministicInput.setImmediate(true);
		deterministicInput.addListener(new Property.ValueChangeListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				InputType selected = (InputType) event.getProperty().getValue();
				presenter.toggleDeterministicInput(true,selected);				
			}
		});
		
		initOptionGroups();
		generateUi();
		
	}

	private void initOptionGroups() {
		
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
		
		//presenter.toggleDeterministicMethodType(true,true);
		//presenter.toggleStochasticMethodType(true,true);	
	}

	/**
	 * Erstelle das GUI zum Prozessschritt "Methoden"
	 * 
	 * @author Julius Hacker
	 */
	private void generateUi() {

		methodList = new VerticalLayout();
		methodList.setSizeFull();
		
		this.addComponent(methodList);
		
		calculationMethodPanel.addComponent(calculationMethod);
		stochasticMethodPanel.addComponent(stochasticMethod);
		
		methodList.addComponent(calculationMethodPanel);
		methodList.addComponent(cashflowSourcePanel);
		methodList.addComponent(stochasticMethodPanel);
		methodList.addComponent(deterministicInputPanel);
		methodList.addComponent(stochasticInputPanel);
		
		//entfernen
		methodList.addComponent(deterministicCheckbox);
		methodList.addComponent(stochasticCheckbox);
	
	}

	/*
	 * Annika Weis
	 * zeigt die deterministische Methode an
	 */
	@Override
	public void showCalculationMethod(AbstractCalculationMethod method) {
		calculationMethod.addItem(method);
		calculationMethod.setItemEnabled(method, method.getImplemented());
		
		if (method.getSelected()){
			calculationMethod.select(method);
		}
	}

	@Override
	public void showStochasticMethod(AbstractStochasticMethod method) { 
		stochasticMethod.addItem(method);
		stochasticMethod.setItemEnabled(method, method.getImplemented());
		
		if (method.getSelected()){
			stochasticMethod.select(method);
		}
	}

	
	@Override
	public void enableOptions() {
			
	}

	@Override
	public void enableStochasticMethodSelection(Boolean state) {
		stochasticMethod.setEnabled(state);
	}
	
	//Annika Weis
	@Override
	public void enableCalculationMethodSelection(Boolean state) {
		calculationMethod.setEnabled(state);
		
	}

	@Override
	public void showInputMethodSelection(Boolean stochasticBool, Boolean checked) {
	
		if (stochasticBool){
			stochasticInputPanel.setVisible(checked);
		}
		else{
			deterministicInputPanel.setVisible(checked);
		}		
	}
	
	//Annika Weis
	@Override
	public void showInputMethod_deterministicSelection(Boolean deterministicBool, Boolean checked) {
	
		if (deterministicBool){
			deterministicInputPanel.setVisible(checked);
		}
		else{
			stochasticInputPanel.setVisible(checked);
		}		
	}

	public void setStochastic(Boolean checked) {
		this.stochasticCheckbox.setValue(checked);
	}
	
	public void setDeterministic(Boolean checked) {
		this.deterministicCheckbox.setValue(checked);
	}
	
	@Override
	public void selectInput(Boolean stochastic,InputType selected) {
		if (stochastic){
			stochasticInput.select(selected);
		}
		else{
			deterministicInput.select(selected);
		}
		
	}

	@Override
	public void showErrorNoMethodSelected(Boolean state) {
		if (!state){
		//this.methods.setComponentError(new UserError("Wenn stochastisch gew\u00E4hlt wurde, muss mindestens eine Methode ausw\u00E4hlt werden"));
		this.stochasticMethod.setComponentError(new UserError("Es muss mindestens eine Methode ausw\u00E4hlt werden"));
		}
		else {
			this.stochasticMethod.setComponentError(null);
		}
	}

	@Override
	public void showErrorNothingSelected(Boolean state) {
		
		if (!state) {
			this.stochasticCheckbox.setComponentError(new UserError("Es muss mindestens eine Cashflowherkunft ausgew\u00E4hlt werden"));
		}
		else {
			this.stochasticCheckbox.setComponentError(null);
		}
		
	}
}
