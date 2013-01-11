package dhbw.ka.mwi.businesshorizon2.ui.process.scenario;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.terminal.UserError;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;

/**
 * Diese Klasse implementiert das GUI fuer den Prozessschritt "Methoden" in Vaadin.
 * 
 * @author Julius Hacker
 *
 */
public class ScenarioViewImpl extends VerticalLayout implements ScenarioViewInterface {
	private static final long serialVersionUID = 1L;

	private final Logger logger = Logger.getLogger("ScenarioViewImpl.class");

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
		this.vlScenarios = new VerticalLayout();
		
		Button newScenario = new Button("Weiteres Szenario");
		
		newScenario.addListener(new Button.ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				presenter.addScenario();
			}
		});
		
		this.addComponent(this.vlScenarios);
		this.addComponent(newScenario);
		this.setComponentAlignment(newScenario, Alignment.BOTTOM_RIGHT);
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
		hlScenario.addComponent(formRight);
		
		final Label scenarioName = new Label("<strong>Szenario " + number + "</strong>");
		scenarioName.setContentMode(Label.CONTENT_XHTML);
		formLeft.addComponent(scenarioName);
		
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
		formRight.addComponent(cbBerechnungEinbezug);
		
		final TextField tfEigenkapital = new TextField("Renditeforderung Eigenkapital: ");
		tfEigenkapital.setValue(rateReturnEquity);
		tfEigenkapital.setImmediate(true);
		tfEigenkapital.addListener(changeListener);
		scenarioComponents.put("rateReturnEquity", tfEigenkapital);
		formLeft.addComponent(tfEigenkapital);
		
		final TextField tfFremdkapital = new TextField("Renditeforderung Fremdkapital: ");
		tfFremdkapital.setValue(rateReturnCapitalStock);
		tfFremdkapital.setImmediate(true);
		tfFremdkapital.addListener(changeListener);
		scenarioComponents.put("rateReturnCapitalStock", tfFremdkapital);
		formLeft.addComponent(tfFremdkapital);
		
		final TextField tfGewerbesteuer = new TextField("Gewerbesteuer: ");
		tfGewerbesteuer.setValue(businessTax);
		tfGewerbesteuer.setImmediate(true);
		tfGewerbesteuer.addListener(changeListener);
		scenarioComponents.put("businessTax", tfGewerbesteuer);
		formRight.addComponent(tfGewerbesteuer);
		
		final TextField tfKoerperschaftssteuer = new TextField("K\u00F6rperschaftssteuer mit Solidarit\u00E4tszuschlag: ");
		tfKoerperschaftssteuer.setValue(corporateAndSolitaryTax);
		tfKoerperschaftssteuer.setImmediate(true);
		tfKoerperschaftssteuer.addListener(changeListener);
		scenarioComponents.put("corporateAndSolitaryTax", tfKoerperschaftssteuer);
		formRight.addComponent(tfKoerperschaftssteuer);
		
		this.scenarios.add(scenarioComponents);
		this.vlScenarios.addComponent(hlScenario);
	}
	
	public boolean getIncludedInCalculation(int scenarioNumber) {
		return (boolean) ((CheckBox) this.scenarios.get(scenarioNumber-1).get("isIncludeInCalculation")).getValue();
	}
	
	public String getValue(int scenarioNumber, String identifier) {
		return (String) ((TextField) this.scenarios.get(scenarioNumber-1).get(identifier)).getValue();
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
