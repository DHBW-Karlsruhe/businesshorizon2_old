package dhbw.ka.mwi.businesshorizon2.ui.process.scenario;

import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.mvplite.event.EventBus;
import com.mvplite.event.EventHandler;

import dhbw.ka.mwi.businesshorizon2.models.Project;
import dhbw.ka.mwi.businesshorizon2.models.Szenario;
import dhbw.ka.mwi.businesshorizon2.ui.process.IllegalValueException;
import dhbw.ka.mwi.businesshorizon2.ui.process.InvalidStateEvent;
import dhbw.ka.mwi.businesshorizon2.ui.process.ScreenPresenter;
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
	private Project project;
	
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

	@Override
	public boolean isValid() {
		boolean isValid = true;
		
		List<Szenario> scenarios = this.project.getScenarios();
		
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

	@Override
	public boolean isSelectable() {
		// TODO Auto-generated method stub
		return false;
	}
	
	public void addScenario() {
		Szenario scenario = new Szenario();
		this.project.addScenario(scenario);
		
		getView().addScenario(Double.toString(scenario.getRateReturnEquity()), Double.toString(scenario.getRateReturnCapitalStock()), Double.toString(scenario.getCorporateAndSolitaryTax()), Double.toString(scenario.getBusinessTax()), scenario.isIncludeInCalculation(), this.project.getScenarios().size());
	}
	

	@EventHandler
	public void showScenarios(ShowScenarioViewEvent event) {
		List<Szenario> scenarios = this.project.getScenarios();
		if(scenarios.size() < 1) {
			scenarios.add(new Szenario(14.0, 10.0, 3.5, 15.0, true));
		}
		
		getView().clear();
		
		int numberOfScenario = 1;
		for(Szenario scenario : scenarios) {
			getView().addScenario(Double.toString(scenario.getRateReturnEquity()), Double.toString(scenario.getRateReturnCapitalStock()), Double.toString(scenario.getCorporateAndSolitaryTax()), Double.toString(scenario.getBusinessTax()), scenario.isIncludeInCalculation(), numberOfScenario);
			numberOfScenario++;
		}
	}
	
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
	
	public void updateScenario(int scenarioNumber) {
		Szenario scenario = this.project.getScenarios().get(scenarioNumber-1);
		
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
		
		
		
	}
	
}
