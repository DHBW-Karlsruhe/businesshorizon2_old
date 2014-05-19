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

package dhbw.ka.mwi.businesshorizon2.ui.process.scenario;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.terminal.Sizeable;
import com.vaadin.terminal.UserError;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

/**
 * Diese Klasse implementiert das GUI fuer den Prozessschritt "Methoden" in Vaadin.
 * 
 * @author Julius Hacker
 *
 */
public class ScenarioViewImpl extends HorizontalSplitPanel implements ScenarioViewInterface {
	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger("ScenarioViewImpl.class");

	@Autowired
	private ScenarioPresenter presenter;
	
	private VerticalLayout vlScenarios;
	
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
	 * @author Julius Hacker
	 */
	private void generateUi() {
		VerticalLayout content = new VerticalLayout();
		
		
		this.vlScenarios = new VerticalLayout();
		this.setLocked(true);
		this.setStyleName("small");
		this.setMargin(true);
		
		Button newScenario = new Button("Weiteres Szenario");
		
		newScenario.addListener(new Button.ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				presenter.addScenario();
			}
		});
		content.addComponent(this.vlScenarios);
		content.addComponent(newScenario);
		setFirstComponent(content);
		
		VerticalLayout infoBox = new VerticalLayout();
		infoBox.setMargin(true);
		Label infoText1 = new Label ("<h3>Eingabe der Szenarien</h3>");
		infoText1.setContentMode(Label.CONTENT_XHTML);
		Label infoText2 = new Label("Sie können verschiedene Szenarien für die Berechnung erstellen. Über die Checkbox „Berechnung einbeziehen“, können Sie selbst festlegen, für welche Szenarien eine Berechnung durchgeführt werden soll. "
				+ " Über den Button 'Weiteres Szenario' kann man beliebig viele weitere Szenarien anlegen. Für jedes Szenario können Sie unterschiedliche Berechnungswerte für die Eigen- und Fremdkapitalrendite, sowie die einzelnen Steuersätze angeben. "
				+ " Info: Bei dem Flow-to-Equity Verfahren beschränken sich die geforderten Werte auf die Eigenkapitalkosten."
				+ " Sie müssen mindestens ein Szenario in die Berechnung einbeziehen. Des Weiteren können Sie jedes Szenario über den 'Szenario entfernen'-Button löschen. Dabei muss jedoch mindestens ein Szenario angelegt bleiben. "
				+ "Über den Button 'Nächster Schritt' können Sie die Berechnung starten.");
		infoBox.addComponent(infoText1);
		infoBox.addComponent(infoText2);
		setSecondComponent(infoBox);
		
		
	}
	
	/**
	 * Die Methode fuegt der View ein Szenario hinzu. Sie baut hierzu saemtliche
	 * notwendigen GUI-Elemente und entsprecheenden Listener hinzu.
	 * 
	 * @author Julius Hacker
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
		
		HorizontalLayout hlScenario = new HorizontalLayout();
		hlScenario.setSizeFull();
		
		FormLayout formLeft = new FormLayout();
		FormLayout formRight = new FormLayout();
		hlScenario.addComponent(formLeft);
		//hlScenario.addComponent(formRight);
		
		final Label scenarioName = new Label("<strong>Szenario " + number + "</strong>");
		scenarioName.setContentMode(Label.CONTENT_XHTML);
		scenarioComponents.put("label", scenarioName);
		formLeft.addComponent(scenarioName);
		scenarioName.setWidth(Sizeable.SIZE_UNDEFINED, 0);
		
		final CheckBox cbBerechnungEinbezug = new CheckBox("In Berechnung einbeziehen");
		cbBerechnungEinbezug.setValue(isIncludeInCalculation);
		cbBerechnungEinbezug.setImmediate(true);
		cbBerechnungEinbezug.addListener(new ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				presenter.updateScenario(number);
				logger.debug("ChangeListener " + System.identityHashCode(this));
			}
			
		});
		scenarioComponents.put("isIncludeInCalculation", cbBerechnungEinbezug);
		formLeft.addComponent(cbBerechnungEinbezug);
		
		final TextField tfEigenkapital = new TextField("Renditeforderung Eigenkapital: ");
		if(!"0.0".equals(rateReturnEquity)) {
			tfEigenkapital.setValue(rateReturnEquity);
		}
		tfEigenkapital.setImmediate(true);
		tfEigenkapital.addListener(changeListener);
		scenarioComponents.put("rateReturnEquity", tfEigenkapital);
		formLeft.addComponent(tfEigenkapital);
		
		final TextField tfFremdkapital = new TextField("Renditeforderung Fremdkapital: ");
		if(!"0.0".equals(rateReturnCapitalStock)) {
			tfFremdkapital.setValue(rateReturnCapitalStock);
		}
		tfFremdkapital.setImmediate(true);
		tfFremdkapital.addListener(changeListener);
		scenarioComponents.put("rateReturnCapitalStock", tfFremdkapital);
		formLeft.addComponent(tfFremdkapital);
		
		final TextField tfGewerbesteuer = new TextField("Gewerbesteuer: ");
		if(!"0.0".equals(businessTax)) {
			tfGewerbesteuer.setValue(businessTax);
		}
		tfGewerbesteuer.setImmediate(true);
		tfGewerbesteuer.addListener(changeListener);
		scenarioComponents.put("businessTax", tfGewerbesteuer);
		formLeft.addComponent(tfGewerbesteuer);
		
		final TextField tfKoerperschaftssteuer = new TextField("K\u00F6rperschaftssteuer mit Solidarit\u00E4tszuschlag: ");
		if(!"0.0".equals(corporateAndSolitaryTax)) {
			tfKoerperschaftssteuer.setValue(corporateAndSolitaryTax);
		}
		tfKoerperschaftssteuer.setImmediate(true);
		tfKoerperschaftssteuer.addListener(changeListener);
		scenarioComponents.put("corporateAndSolitaryTax", tfKoerperschaftssteuer);
		formLeft.addComponent(tfKoerperschaftssteuer);
		
		final Button removeScenario = new Button("Szenario entfernen");
		removeScenario.addListener(new ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				presenter.removeScenario(number);
			}
			
		});
		formLeft.addComponent(removeScenario);
		
		formLeft.setWidth(Sizeable.SIZE_UNDEFINED, 0);
		formLeft.setWidth(Sizeable.SIZE_UNDEFINED, 0);
		
		scenarioComponents.put("scenario", hlScenario);
		
		this.scenarios.add(scenarioComponents);
		this.vlScenarios.addComponent(hlScenario);
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
	}
	
	public boolean getIncludedInCalculation(int scenarioNumber) {
		return (boolean) ((CheckBox) this.scenarios.get(scenarioNumber-1).get("isIncludeInCalculation")).getValue();
	}
	
	public String getValue(int scenarioNumber, String identifier) {
		return (String) ((AbstractField) this.scenarios.get(scenarioNumber-1).get(identifier)).getValue();
	}
	
	public Boolean getIncludeInCalculation(int scenarioNumber) {
		return (Boolean) ((CheckBox) this.scenarios.get(scenarioNumber-1).get("isIncludeInCalculation")).getValue();
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
}
