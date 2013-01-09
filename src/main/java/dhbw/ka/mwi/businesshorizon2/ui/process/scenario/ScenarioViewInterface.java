package dhbw.ka.mwi.businesshorizon2.ui.process.scenario;

import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;

import dhbw.ka.mwi.businesshorizon2.models.Szenario;
import dhbw.ka.mwi.businesshorizon2.ui.process.contentcontainer.ContentView;

public interface ScenarioViewInterface extends ContentView {
	public void clear();
	public void addScenario(String rateReturnEquity,
			String rateReturnCapitalStock, String corporateAndSolitaryTax,
			String businessTax, boolean isIncludeInCalculation, int numberOfScenario);
	
	public boolean getIncludedInCalculation(int scenarioNumber);
	public String getValue(int scenarioNumber, String identifier);
	
	
	public void setIncludedInCalculation(int scenarioNumber, boolean newValue);
	public void setRateReturnEquity(int scenarioNumber, String newValue);
	public void setRateReturnCapitalStock(int scenarioNumber, String newValue);
	public void setBusinessTax(int scenarioNumber, String newValue);
	public void setCorporateAndSolitaryTax(int scenarioNumber, String newValue);
	
	public void setInvalid(int scenarioNumber, String identifier);
	public void setValid(int scenarioNumber, String identifier);
}