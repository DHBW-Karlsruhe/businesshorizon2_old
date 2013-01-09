package dhbw.ka.mwi.businesshorizon2.ui.process.scenario;

import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.mvplite.event.EventBus;
import com.mvplite.event.EventHandler;

import dhbw.ka.mwi.businesshorizon2.models.Project;
import dhbw.ka.mwi.businesshorizon2.models.Szenario;
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
		
		for(Szenario scenario : scenarios) {
			if(scenario.isIncludeInCalculation()) {
				if(scenario.getCorporateAndSolitaryTax() < 0 || scenario.getCorporateAndSolitaryTax() > 100) {
					
					isValid = false;
				}
				
				if(scenario.getBusinessTax() < 0 || scenario.getBusinessTax() > 100) {
					isValid = false;
				}
				
				if(scenario.getRateReturnCapitalStock() < 0 || scenario.getRateReturnCapitalStock() > 100) {
					isValid = false;
				}
				
				if(scenario.getRateReturnEquity() < 0 || scenario.getRateReturnEquity() > 100) {
					isValid = false;
				}
			}
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
		
		getView().addScenario(scenario.getRateReturnEquity(), scenario.getRateReturnCapitalStock(), scenario.getCorporateAndSolitaryTax(), scenario.getBusinessTax(), scenario.isIncludeInCalculation(), this.project.getScenarios().size());
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
	

	public boolean isValidDebt(String value) {
		boolean isValidDebt = true;
		
		if(Integer.getInteger(value) < 0 || Integer.getInteger(value) > 100) {
			isValidDebt = false;
		}
		
		return isValidDebt;
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
	
	public void updateScenario(int scenarioNumber) {
		Szenario scenario = this.project.getScenarios().get(scenarioNumber-1);
		
		try {
			scenario.setRateReturnEquity(Double.parseDouble(getView().getValue(scenarioNumber, "rateReturnEquity")));
			getView().setValid(scenarioNumber, "rateReturnEquity");
		}
		catch(Exception exception) {
			getView().setInvalid(scenarioNumber, "rateReturnEquity");
		}
		
		try {
			scenario.setRateReturnCapitalStock(Double.parseDouble(getView().getValue(scenarioNumber, "rateReturnCapitalStock")));
			getView().setValid(scenarioNumber, "rateReturnCapitalStock");
			logger.debug("Renditeforderung Fremdkapital korrekt: " + scenario.getRateReturnCapitalStock() + " - " + getView().getValue(scenarioNumber, "rateReturnCapitalStock"));
		}
		catch(Exception exception) {
			logger.debug("Inkorrekt: " + getView().getValue(scenarioNumber, "rateReturnCapitalStock"));
			logger.debug(exception.getMessage());
			getView().setInvalid(scenarioNumber, "rateReturnCapitalStock");
		}
		
		try {
			scenario.setBusinessTax(Double.parseDouble(getView().getValue(scenarioNumber, "businessTax")));
			getView().setValid(scenarioNumber, "businessTax");
		}
		catch(Exception exception) {
			getView().setInvalid(scenarioNumber, "businessTax");
		}
		
		try {
			scenario.setCorporateAndSolitaryTax(Double.parseDouble(getView().getValue(scenarioNumber, "corporateAndSolitaryTax")));
			getView().setValid(scenarioNumber, "corporateAndSolitaryTax");
		}
		catch(Exception exception) {
			getView().setInvalid(scenarioNumber, "corporateAndSolitaryTax");
		}
		
	}
	
}
