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

package dhbw.ka.mwi.businesshorizon2.ui.scenarioscreen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.event.MouseEvents.ClickEvent;
import com.vaadin.event.MouseEvents.ClickListener;
import com.vaadin.terminal.Sizeable;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.terminal.UserError;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.Form;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

/**
 * Diese Klasse implementiert das GUI fuer den Prozessschritt "Szenario" in Vaadin.
 * 
 * @author Julius Hacker, Tobias Lindner
 *
 */
public class ScenarioScreenViewImpl extends VerticalLayout implements ScenarioScreenViewInterface {
	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger("ScenarioViewImpl.class");

	@Autowired
	private ScenarioScreenPresenter presenter;
	
	private Form berechnungForm;
	
	private OptionGroup berechnungGroup;
	
	private Label gap1;
	private Label gap2;
	private Label gap3;
	private Label gap4;
	private Label splitter;
	private Label addLabel;
	
	private Embedded addIcon;
	private Embedded deleteIcon; 
	
	private VerticalLayout vlScenarios;
	private HorizontalLayout addScenarioLayout;
	
	private List<HashMap<String, AbstractComponent>> scenarios = new ArrayList<HashMap<String, AbstractComponent>>();
	
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
		generateUi();
	}

	/**
	 * Erstelle das GUI zum Prozessschritt "Szenarien"
	 * 
	 * @author Julius Hacker, Tobias Lindner
	 */
	private void generateUi() {
		
		berechnungForm = new Form();
		berechnungGroup = new OptionGroup();
		
		berechnungForm.setWidth(90, UNITS_PERCENTAGE);
		berechnungForm.setCaption("Berechnungsmethode");
		
		berechnungGroup.addItem("apv");
		berechnungGroup.addItem("fte");
		berechnungGroup.addItem("wac");
		berechnungGroup.setItemCaption("apv", "APV (Adjusted Present Value)");
		berechnungGroup.setItemCaption("fte", "FTE (Flow to Equity)");
		berechnungGroup.setItemCaption("wac", "WACC (Weighted Average Cost of Capital)");
		
		berechnungForm.addField("berechnungGroup", berechnungGroup);
		
		gap1 = new Label();
		gap1.setHeight(35, UNITS_PIXELS);
		
		splitter = new Label("<hr style='border:none;background-color:black;height:2px'>", Label.CONTENT_XHTML);
		
		gap2 = new Label();
		gap2.setHeight(20, UNITS_PIXELS);
		
		addScenarioLayout = new HorizontalLayout();
		
		addIcon = new Embedded (null, new ThemeResource ("./images/icons/newIcons/1418766077_circle_add_plus_-128_green.png"));
		addIcon.setHeight(30, UNITS_PIXELS);
		
		addLabel = new Label ("Szenario hinzufügen");
		addLabel.addStyleName("scenarios");
		addLabel.addStyleName("addScenario");
		
		addScenarioLayout.addComponent(addIcon);
		gap3 = new Label();
		gap3.setWidth(10, UNITS_PIXELS);
		addScenarioLayout.addComponent(gap3);
		addScenarioLayout.addComponent(addLabel);
		addScenarioLayout.setComponentAlignment(addLabel, Alignment.MIDDLE_CENTER);
		addScenarioLayout.addListener(new LayoutClickListener () {
			private static final long serialVersionUID = 1L;

			@Override
			public void layoutClick(LayoutClickEvent event) {
				presenter.addScenario();
			}
		});
		
		gap4 = new Label ();
		gap4.setHeight(15, UNITS_PIXELS);
		
		this.vlScenarios = new VerticalLayout();
		this.vlScenarios.setSizeFull();
//		this.setStyleName("small");
		this.setMargin(true);
		
		addComponent(berechnungForm);
		setComponentAlignment(berechnungForm, Alignment.MIDDLE_CENTER);
		addComponent(gap1);
		addComponent(splitter);
		addComponent(gap2);
		addComponent(addScenarioLayout);
		setComponentAlignment(addScenarioLayout, Alignment.MIDDLE_LEFT);
		addComponent(gap4);
		addComponent(this.vlScenarios);
		
		vlScenarios.addStyleName("scenarios");
	}
	
	/**
	 * Setzt den Radio-Button auf APV
	 * 
	 * @author Tobias Lindner
	 */
	public void setAPVSelected () {
		berechnungGroup.setValue("apv");
	}
	
	/**
	 * Setzt den Radio-Button auf FTE
	 * 
	 * @author Tobias Lindner
	 */
	public void setFTESelected () {
		berechnungGroup.setValue("fte");
		
	}
	
	/**
	 * Setzt den Radio-Button auf WACC
	 * 
	 * @author Tobias Lindner
	 */
	public void setWACCSelected () {
		berechnungGroup.setValue("wacc");
	}
	
	/**
	 * Die Methode fuegt der View ein Szenario hinzu. Sie baut hierzu saemtliche
	 * notwendigen GUI-Elemente und entsprechenden Listener hinzu.
	 * 
	 * Auf ein GridLayout umgestellt.
	 * 
	 * @author Julius Hacker, Tobias Lindner
	 * @param rateReturnEquity Standardwert fuer die Renditeforderung Eigenkapital
	 * @param rateReturnCapitalStock Standardwert fuer die Renditeforderung Fremdkapital
	 * @param businessTax Standardwert fuer die Gewerbesteuer
	 * @param corporateAndSolitaryTax Standardwert fuer die Koerperschaftssteuer mit Solidaritaetszuschlag.
	 */
	@Override
	public void addScenario(String rateReturnEquity,
			String rateReturnCapitalStock, String corporateAndSolitaryTax,
			String businessTax, boolean isIncludeInCalculation, final int number) {
		HashMap<String, AbstractComponent> scenarioComponents = new HashMap<String, AbstractComponent>();
		
		Property.ValueChangeListener changeListener = new Property.ValueChangeListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				presenter.updateScenario(number);
				logger.debug("TextChange ausgeloest");
				logger.debug("ChangeListener " + System.identityHashCode(this));
			}
		};
		
		final GridLayout gl = new GridLayout(3, 6);
		gl.addStyleName("gridLayoutScenarios");
		gl.setSizeFull();
		gl.setColumnExpandRatio(0, 2);
		gl.setColumnExpandRatio(1, 1);
		gl.setColumnExpandRatio(2, 1);
				
		final Label scenarioName = new Label("<strong>Szenario " + number + "</strong>");
		scenarioName.setContentMode(Label.CONTENT_XHTML);
		scenarioComponents.put("label", scenarioName);
		
		logger.debug("SzenarioName: " + scenarioName);
		gl.addComponent(scenarioName, 0, 0);
		
//		formLeft.addComponent(scenarioName);
//		scenarioName.setWidth(Sizeable.SIZE_UNDEFINED, 0);
		
//		final CheckBox cbBerechnungEinbezug = new CheckBox("In Berechnung einbeziehen");
//		cbBerechnungEinbezug.setValue(isIncludeInCalculation);
//		cbBerechnungEinbezug.setImmediate(true);
//		cbBerechnungEinbezug.addListener(new ClickListener() {
//			private static final long serialVersionUID = 1L;
//
//			@Override
//			public void buttonClick(ClickEvent event) {
//				presenter.updateScenario(number);
//				logger.debug("ChangeListener " + System.identityHashCode(this));
//			}
//			
//		});
//		scenarioComponents.put("isIncludeInCalculation", cbBerechnungEinbezug);
//		formLeft.addComponent(cbBerechnungEinbezug);
		
		final Label textEigenkapital = new Label ("Renditeforderung Eigenkapital: ");
		textEigenkapital.setSizeFull();
		
		final TextField tfEigenkapital = new TextField();
		if(!"0.0".equals(rateReturnEquity)) {
			tfEigenkapital.setValue(rateReturnEquity);
		}
		tfEigenkapital.setImmediate(true);
		tfEigenkapital.addStyleName("scenario");
		tfEigenkapital.addListener(changeListener);
		
		gl.addComponent(textEigenkapital, 0, 1);
		gl.addComponent(tfEigenkapital, 1, 1);
		
		scenarioComponents.put("rateReturnEquity", tfEigenkapital);
		
		final Label textFremdkapitel = new Label ("Renditeforderung FK: ");
		
		final TextField tfFremdkapital = new TextField();
		if(!"0.0".equals(rateReturnCapitalStock)) {
			tfFremdkapital.setValue(rateReturnCapitalStock);
		}
		tfFremdkapital.setImmediate(true);
		tfFremdkapital.addStyleName("scenario");
		tfFremdkapital.addListener(changeListener);
		
		gl.addComponent(textFremdkapitel, 0, 2);
		gl.addComponent(tfFremdkapital, 1, 2);
		
		scenarioComponents.put("rateReturnCapitalStock", tfFremdkapital);
		
		final Label textGewerbesteuer = new Label ("Gewerbesteuer:");
		final TextField tfGewerbesteuer = new TextField();
		if(!"0.0".equals(businessTax)) {
			tfGewerbesteuer.setValue(businessTax);
		}
		tfGewerbesteuer.setImmediate(true);
		tfGewerbesteuer.addStyleName("scenario");
		tfGewerbesteuer.addListener(changeListener);
		
		gl.addComponent(textGewerbesteuer, 0, 3);
		gl.addComponent(tfGewerbesteuer, 1, 3);
		
		scenarioComponents.put("businessTax", tfGewerbesteuer);
		
		final Label textKoerperschaftssteuer = new Label ("Körperschaftssteuer mit Solidaritätszuschlag: ");
		
		final TextField tfKoerperschaftssteuer = new TextField();
		if(!"0.0".equals(corporateAndSolitaryTax)) {
			tfKoerperschaftssteuer.setValue(corporateAndSolitaryTax);
		}
		tfKoerperschaftssteuer.setImmediate(true);
		tfKoerperschaftssteuer.addStyleName("scenario");
		tfKoerperschaftssteuer.addListener(changeListener);
		
		gl.addComponent(textKoerperschaftssteuer, 0, 4);
		gl.addComponent(tfKoerperschaftssteuer, 1, 4);
		
		scenarioComponents.put("corporateAndSolitaryTax", tfKoerperschaftssteuer);
		
//		final Button removeScenario = new Button("Szenario entfernen");
//		removeScenario.addListener(new ClickListener() {
//			private static final long serialVersionUID = 1L;
//
//			@Override
//			public void buttonClick(ClickEvent event) {
//				presenter.removeScenario(number);
//			}
//			
//		});
//		formLeft.addComponent(removeScenario);
//		gl.addComponent(removeScenario, 0, 5);
		
		deleteIcon = new Embedded (null, new ThemeResource ("./images/icons/newIcons/1418766003_editor_trash_delete_recycle_bin_-128.png"));
		deleteIcon.setHeight(60, UNITS_PIXELS);
		deleteIcon.addStyleName("deleteScenario");
		deleteIcon.addListener(new ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void click(ClickEvent event) {
				presenter.removeScenario(number);
			}
			
		});
		
		gl.addComponent(deleteIcon, 2, 0, 2, 5);
		gl.setComponentAlignment(deleteIcon, Alignment.MIDDLE_CENTER);
		
		final Label gap = new Label();
		gap.setHeight(20, UNITS_PIXELS);
		
		gl.addComponent(gap, 0, 5);
				
		scenarioComponents.put("scenario", gl);
		
		this.scenarios.add(scenarioComponents);
		this.vlScenarios.addComponent(gl);
		
		//Button bei 3 Scenarios deaktivieren
		if (number == 3) {
			deactivateAddScenario();
		}
	}
	
	public void updateLabels() {
		int number = 1;
		
		for(HashMap<String, AbstractComponent> scenarioComponents : this.scenarios) {
			((Label) scenarioComponents.get("label")).setValue("<strong>Szenario " + number + "</strong>");
			number++;
		}
	}
	
	public void removeScenario(final int number) {
		logger.debug("Removing scenario from view");
		this.vlScenarios.removeComponent(this.scenarios.get(number).get("scenario"));
		this.scenarios.remove(number);
		
		activateAddScenario();
	}
	
	public boolean getIncludedInCalculation(int scenarioNumber) {
		return (boolean) ((CheckBox) this.scenarios.get(scenarioNumber-1).get("isIncludeInCalculation")).getValue();
	}
	
	public String getValue(int scenarioNumber, String identifier) {
		return (String) ((AbstractField) this.scenarios.get(scenarioNumber-1).get(identifier)).getValue();
	}
	
	public Boolean getIncludeInCalculation(int scenarioNumber) {
//		return (Boolean) ((CheckBox) this.scenarios.get(scenarioNumber-1).get("isIncludeInCalculation")).getValue();
		return true;
	}
	
	
	public void setIncludedInCalculation(int scenarioNumber, boolean newValue) {
		((CheckBox) this.scenarios.get(scenarioNumber-1).get("isIncludeInCalculation")).setValue(newValue);
	}
	
	public void setRateReturnEquity(int scenarioNumber, String newValue) {
		((Button) this.scenarios.get(scenarioNumber-1).get("rateReturnEquity")).setValue(newValue);
	}
	
	public void setRateReturnCapitalStock(int scenarioNumber, String newValue) {
		((Button) this.scenarios.get(scenarioNumber-1).get("rateReturnCapitalStock")).setValue(newValue);
	}
	
	public void setBusinessTax(int scenarioNumber, String newValue) {
		((Button) this.scenarios.get(scenarioNumber-1).get("businessTax")).setValue(newValue);
	}
	
	public void setCorporateAndSolitaryTax(int scenarioNumber, String newValue) {
		((Button) this.scenarios.get(scenarioNumber-1).get("corporateAndSolitaryTax")).setValue(newValue);
	}
	
	/**
	 * Diese Methode setzt das betreffende Eingabefeld auf den Status ungueltig.
	 * Hierbei wird neben dem Eingabefeld ein kleins Fehlericon mit entsprechendem Tooltip-Fehlertext angezeigt.
	 * 
	 * @author Julius Hacker
	 * @param scenarioNumber Die Nummer des Szenarios, zu dem das Eingabefeld gehoert
	 * @param identifier Der Eingabewert (Renditeforderungen, Steuern, ...), zu dem das Eingabefeld gehoert.
	 */
	public void setInvalid(int scenarioNumber, String identifier) {
		this.scenarios.get(scenarioNumber-1).get(identifier).setComponentError(new UserError("Nur Zahlen gr\u00F6\u00DFer gleich 0 und kleiner gleich 100 sind erlaubt!"));
	}
	
	/**
	 * Diese Methode setzt das betreffende Eingabefeld auf den Status gueltig.
	 * 
	 * @author Julius Hacker
	 * @param scenarioNumber Die Nummer des Szenarios, zu dem das Eingabefeld gehoert
	 * @param identifier Der Eingabewert (Renditeforderungen, Steuern, ...), zu dem das Eingabefeld gehoert.
	 */
	public void setValid(int scenarioNumber, String identifier) {
		this.scenarios.get(scenarioNumber-1).get(identifier).setComponentError(null);
	}
	
	/**
	 * Diese Methode entfernt alle GUI-Elemente aus der View. Diese Methode kann so zur Vorbereitung
	 * des Neuaufbaus der View dienen.
	 * 
	 * @author Julius Hacker
	 */
	public void clear() {
		vlScenarios.removeAllComponents();
		scenarios.clear();
	}
	
	public void deactivateAddScenario () {
		addScenarioLayout.setEnabled(false);
	}
	
	public void activateAddScenario () {
		addScenarioLayout.setEnabled(true);
	}
}
