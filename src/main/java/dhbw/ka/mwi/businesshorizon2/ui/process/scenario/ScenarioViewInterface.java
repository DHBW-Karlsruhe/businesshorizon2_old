package dhbw.ka.mwi.businesshorizon2.ui.process.scenario;

import dhbw.ka.mwi.businesshorizon2.ui.process.contentcontainer.ContentView;

public interface ScenarioViewInterface extends ContentView {

	/**
	 * Diese Methode entfernt alle GUI-Elemente aus der View. Diese Methode kann so zur Vorbereitung
	 * des Neuaufbaus der View dienen.
	 * 
	 * @author Julius Hacker
	 */
	public void clear();
	
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
	
	/**
	 * Diese Methode setzt das betreffende Eingabefeld auf den Status ungueltig.
	 * Hierbei wird neben dem Eingabefeld ein kleins Fehlericon mit entsprechendem Tooltip-Fehlertext angezeigt.
	 * 
	 * @author Julius Hacker
	 * @param scenarioNumber Die Nummer des Szenarios, zu dem das Eingabefeld gehoert
	 * @param identifier Der Eingabewert (Renditeforderungen, Steuern, ...), zu dem das Eingabefeld gehoert.
	 */
	public void setInvalid(int scenarioNumber, String identifier);
	
	/**
	 * Diese Methode setzt das betreffende Eingabefeld auf den Status gueltig.
	 * 
	 * @author Julius Hacker
	 * @param scenarioNumber Die Nummer des Szenarios, zu dem das Eingabefeld gehoert
	 * @param identifier Der Eingabewert (Renditeforderungen, Steuern, ...), zu dem das Eingabefeld gehoert.
	 */
	public void setValid(int scenarioNumber, String identifier);
}