package dhbw.ka.mwi.businesshorizon2.ui.process.scenario;

import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.mvplite.event.EventBus;
import com.mvplite.event.EventHandler;

import dhbw.ka.mwi.businesshorizon2.models.Szenario;
import dhbw.ka.mwi.businesshorizon2.services.proxies.ProjectProxy;
import dhbw.ka.mwi.businesshorizon2.ui.process.IllegalValueException;
import dhbw.ka.mwi.businesshorizon2.ui.process.InvalidStateEvent;
import dhbw.ka.mwi.businesshorizon2.ui.process.ScreenPresenter;
import dhbw.ka.mwi.businesshorizon2.ui.process.ScreenSelectableEvent;
import dhbw.ka.mwi.businesshorizon2.ui.process.ValidStateEvent;
import dhbw.ka.mwi.businesshorizon2.ui.process.ValidateContentStateEvent;
import dhbw.ka.mwi.businesshorizon2.ui.process.navigation.NavigationSteps;
import dhbw.ka.mwi.businesshorizon2.ui.process.scenario.ScenarioViewInterface;

/**
 * Der Presenter fuer die Maske des Prozessschrittes zur Eingabe des Berechnungsszenarios.
 * 
 * @author Julius Hacker
 *
 */

public class ScenarioPresenter extends ScreenPresenter<ScenarioViewInterface> {
	private static final long serialVersionUID = 1L;

	private Logger logger = Logger.getLogger("ScenarioPresenter.class");

	@Autowired
	private EventBus eventBus;
	
	@Autowired
	private ProjectProxy projectProxy;
	
	/**
	 * Dies ist der Konstruktor, der von Spring nach der Initialierung der Dependencies 
	 * aufgerufen wird. Er registriert lediglich sich selbst als einen EventHandler.
	 * 
	 * @author Julius Hacker
	 */
	@PostConstruct
	public void init() {
		eventBus.addHandler(this);
	}

	/**
	 * Diese Methode ueberprueft, ob die im Screen getaetigten Eingaben valide und somit korrekt
	 * sind. Er nutzt hierbei die Validierungsmethoden zur Ueberpruefung der einzelnen Dateneingabefelder.
	 * 
	 * @author Julius Hacker
	 * @return true: Die Eingabewerte der Maske sind insgesamt korrekt
	 * false: Die Eingabewerte der Maske sind an mindestens einer Stelle nicht korrekt.
	 */
	@Override
	public boolean isValid() {
		boolean isValid = true;
		
		List<Szenario> scenarios = this.projectProxy.getSelectedProject().getScenarios();
		
		int scenarioNumber = 1;
		for(Szenario scenario : scenarios) {
			if(scenario.isIncludeInCalculation()) {
				if(!isValidCorporateAndSolitaryTax(scenarioNumber) || !isValidBusinessTax(scenarioNumber) || !isValidRateReturnCapitalStock(scenarioNumber) || !isValidRateReturnEquity(scenarioNumber)) {
					isValid = false;
				}
			}
			
			scenarioNumber++;
		}
		
		return isValid;
	}
	
	/**
	 * Diese Methode fuegt dem Projekt ein neues Szenario hinzu und zeigt die dazugehoerigen
	 * Eingabefelder auf dem Screen an.
	 * 
	 * @author Julius Hacker
	 */
	public void addScenario() {
		Szenario scenario = new Szenario();
		this.projectProxy.getSelectedProject().addScenario(scenario);
		
		getView().addScenario(Double.toString(scenario.getRateReturnEquity()), Double.toString(scenario.getRateReturnCapitalStock()), Double.toString(scenario.getCorporateAndSolitaryTax()), Double.toString(scenario.getBusinessTax()), scenario.isIncludeInCalculation(), this.projectProxy.getSelectedProject().getScenarios().size());
	}
	
	/**
	 * Dieser EventHandler reagiert auf die Nachricht, dass der Prozesschritt zur Szenarienanzeige
	 * angezeigt werden soll. Er legt hierbei, falls noch kein Szenario existiert, zunaechst das im
	 * Fachkonzept angegebene Standardszenario an und baut den Screen danach komplett neu auf.
	 */
	@EventHandler
	public void handleShowView(ShowScenarioViewEvent event) {
		List<Szenario> scenarios = this.projectProxy.getSelectedProject().getScenarios();
		if(scenarios.size() < 1) {
			scenarios.add(new Szenario(14.0, 10.0, 3.5, 15.0, true));
		}
		
		getView().clear();
		
		int numberOfScenario = 1;
		for(Szenario scenario : scenarios) {
			getView().addScenario(Double.toString(scenario.getRateReturnEquity()), Double.toString(scenario.getRateReturnCapitalStock()), Double.toString(scenario.getCorporateAndSolitaryTax()), Double.toString(scenario.getBusinessTax()), scenario.isIncludeInCalculation(), numberOfScenario);
			numberOfScenario++;
		}
		
		eventBus.fireEvent(new ScreenSelectableEvent(NavigationSteps.SCENARIO, true));
	}
	
	/**
	 * Dieser Handler reagiert auf die Nachricht, dass der Prozesschritt seinen Inhalt validieren soll. 
	 * Er bedient sich dabei der Methode isValid und setzt den Status des Screens entsprechend des Ergebnisses
	 * auf valide oder invalide.
	 * 
	 * @author Julius Hacker
	 */
	@Override
	@EventHandler
	public void validate(ValidateContentStateEvent event) {
		if(!this.isValid()) {
			eventBus.fireEvent(new InvalidStateEvent(NavigationSteps.SCENARIO));
			logger.debug("Scenario not valid, InvalidStateEvent fired");
		}
		else {
			eventBus.fireEvent(new ValidStateEvent(NavigationSteps.SCENARIO));
			logger.debug("Scenario valid, ValidStateEvent fired");
		}
	}
	
	/**
	 * Diese Methode ueberprueft, ob die Eingabe im Eingabefeld zur Renditeforderung Eigenkapital korrekt ist.
	 * 
	 * @author Julius Hacker
	 * @return true: Eingabe ist korrekt und gueltig.
	 * false: Eingabe ist nicht korrekt.
	 */
	public boolean isValidRateReturnEquity(int scenarioNumber) {
		boolean isValid = true;
		
		try {
			Double rateReturnEquity = Double.parseDouble(getView().getValue(scenarioNumber, "rateReturnEquity"));
			
			if(rateReturnEquity < 0) {
				throw new IllegalValueException("corporateAndSolitaryTax kleiner 0");
			}
			
			getView().setValid(scenarioNumber, "rateReturnEquity");
		}
		catch(Exception exception) {
			getView().setInvalid(scenarioNumber, "rateReturnEquity");
			isValid = false;
		}
		
		return isValid;
	}
	
	/**
	 * Diese Methode ueberprueft, ob die Eingabe im Eingabefeld zur Renditeforderung Fremdkapital korrekt ist.
	 * 
	 * @author Julius Hacker
	 * @return true: Eingabe ist korrekt und gueltig.
	 * false: Eingabe ist nicht korrekt.
	 */
	public boolean isValidRateReturnCapitalStock(int scenarioNumber) {
		boolean isValid = true;
		
		try {
			Double rateReturnCapitalStock = Double.parseDouble(getView().getValue(scenarioNumber, "rateReturnCapitalStock"));
			
			if(rateReturnCapitalStock < 0) {
				throw new IllegalValueException("corporateAndSolitaryTax kleiner 0");
			}
			
			getView().setValid(scenarioNumber, "rateReturnCapitalStock");
		}
		catch(Exception exception) {
			getView().setInvalid(scenarioNumber, "rateReturnCapitalStock");
			isValid = false;
		}
		
		return isValid;
	}
	
	/**
	 * Diese Methode ueberprueft, ob die Eingabe im Eingabefeld zur Gewerbesteuer korrekt ist.
	 * 
	 * @author Julius Hacker
	 * @return true: Eingabe ist korrekt und gueltig.
	 * false: Eingabe ist nicht korrekt.
	 */
	public boolean isValidBusinessTax(int scenarioNumber) {
		boolean isValid = true;
		
		try {
			Double businessTax = Double.parseDouble(getView().getValue(scenarioNumber, "businessTax"));
			
			if(businessTax < 0) {
				throw new IllegalValueException("corporateAndSolitaryTax kleiner 0");
			}
			
			getView().setValid(scenarioNumber, "businessTax");
		}
		catch(Exception exception) {
			getView().setInvalid(scenarioNumber, "businessTax");
			isValid = false;
		}
		
		return isValid;
	}
	
	/**
	 * Diese Methode ueberprueft, ob die Eingabe im Eingabefeld zur Koerperschaftssteuer
	 * mit Solidaritaetszuschlag korrekt ist.
	 * 
	 * @author Julius Hacker
	 * @return true: Eingabe ist korrekt und gueltig.
	 * false: Eingabe ist nicht korrekt.
	 */
	public boolean isValidCorporateAndSolitaryTax(int scenarioNumber) {
		boolean isValid = true;
		
		try {
			Double corporateAndSolitaryTax = Double.parseDouble(getView().getValue(scenarioNumber, "corporateAndSolitaryTax"));
			
			if(corporateAndSolitaryTax < 0) {
				throw new IllegalValueException("corporateAndSolitaryTax kleiner 0");
			}
			
			getView().setValid(scenarioNumber, "corporateAndSolitaryTax");
		}
		catch(Exception exception) {
			getView().setInvalid(scenarioNumber, "corporateAndSolitaryTax");
			isValid = false;
		}
		
		return isValid;
	}
	
	/**
	 * Diese Methode holt sich die in den Eingabefeldern der View eingetragenen Werte und
	 * speichert sie nach einer Validierung im Szenarien-Model. Die Methode wird insbesondere
	 * durch die EventHandler der View genutzt, die auf entsprechende Textaenderungen reagieren.
	 * 
	 * @author Julius Hacker
	 * @param scenarioNumber Nummer des Szenarios, dessen Werte geaendert wurden.
	 */
	public void updateScenario(int scenarioNumber) {
		Szenario scenario = this.projectProxy.getSelectedProject().getScenarios().get(scenarioNumber-1);
		
		if(isValidRateReturnEquity(scenarioNumber)) {
			scenario.setRateReturnEquity(Double.parseDouble(getView().getValue(scenarioNumber, "rateReturnEquity")));
			logger.debug("Renditeforderung Eigenkapital Szenario " + scenarioNumber + " auf " + scenario.getRateReturnEquity() + " (" + getView().getValue(scenarioNumber, "rateReturnEquity") + ")");
		}
		
		if(isValidRateReturnCapitalStock(scenarioNumber)) {
			scenario.setRateReturnCapitalStock(Double.parseDouble(getView().getValue(scenarioNumber, "rateReturnCapitalStock")));
			logger.debug("Renditeforderung Fremdkapital Szenario " + scenarioNumber + " auf " + scenario.getRateReturnCapitalStock() + " (" + getView().getValue(scenarioNumber, "rateReturnCapitalStock") + ")");
		}
		
		if(isValidBusinessTax(scenarioNumber)) {
			scenario.setBusinessTax(Double.parseDouble(getView().getValue(scenarioNumber, "businessTax")));
			logger.debug("Gewerbesteuer Szenario " + scenarioNumber + " auf " + scenario.getBusinessTax() + " (" + getView().getValue(scenarioNumber, "businessTax") + ")");
		}
		
		if(isValidCorporateAndSolitaryTax(scenarioNumber)) {
			scenario.setCorporateAndSolitaryTax(Double.parseDouble(getView().getValue(scenarioNumber, "corporateAndSolitaryTax")));
			logger.debug("Koerperschaftssteuer und Solidaritaetszuschlag Szenario " + scenarioNumber + " auf " + scenario.getCorporateAndSolitaryTax() + " (" + getView().getValue(scenarioNumber, "corporateAndSolitaryTax") + ")");
		}
		
		scenario.setIncludeInCalculation(getView().getIncludeInCalculation(scenarioNumber));
		
		eventBus.fireEvent(new ValidateContentStateEvent());
	}
	
}
