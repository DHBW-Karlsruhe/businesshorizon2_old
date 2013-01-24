package dhbw.ka.mwi.businesshorizon2.ui.process.parameter;

import dhbw.ka.mwi.businesshorizon2.ui.process.contentcontainer.ContentView;

public interface ParameterViewInterface extends ContentView {

	/**
	 * Gibt eine Fehlermeldung an den Benutzer aus.
	 * 
	 * @author Christian Scherer
	 * @param message
	 *            Fehlermeldung die der Methode zur Ausgabe uebergeben wird
	 */
	void showErrorMessage(String message);

	/**
	 * Diese Methode graut die ComboBox (DropDown-Liste) fuer die
	 * Branchenstellvertreter aus, da diese Funktionalitaet noch nicht gegeben
	 * ist.
	 * 
	 * @author Christian Scherer
	 * @param enabled
	 *            true aktiviert den Kombonenten, false deaktiviert (graut aus)
	 *            den Komponenten
	 */
	void activateComboBoxRepresentatives(boolean enabled);

	/**
	 * Diese Methode graut die Checkbox fuer die Branchenstellvertreter aus, da
	 * diese Funktionalitaet noch nicht gegeben ist.
	 * 
	 * @author Christian Scherer
	 * @param enabled
	 *            true aktiviert den Kombonenten, false deaktiviert (graut aus)
	 *            den Komponenten
	 */
	void activateCheckboxIndustryRepresentative(boolean enabled);

	/**
	 * Diese Methode graut das Textfeld 'textfieldNumPeriods' aus.
	 * 
	 * @author Christian Scherer
	 * @param enabled
	 *            true aktiviert den Kombonenten, false deaktiviert (graut aus)
	 *            den Komponenten
	 */
	void activatePeriodsToForecast(boolean enabled);

	/**
	 * Diese Methode graut das Textfeld 'textfieldNumPastPeriods' aus.
	 * 
	 * @author Christian Scherer
	 * @param enabled
	 *            true aktiviert den Kombonenten, false deaktiviert (graut aus)
	 *            den Komponenten
	 */
	void activateRelevantPastPeriods(boolean enabled);

	/**
	 * Diese Methode graut die ComboBox 'comboBoxIteraions' aus.
	 * 
	 * @author Christian Scherer
	 * @param enabled
	 *            true aktiviert den Kombonenten, false deaktiviert (graut aus)
	 *            den Komponenten
	 */
	void activateIterations(boolean enabled);

	/**
	 * Setzt eine Fehleranzeige an das Entsprechende Feld bzw. entfernt diese
	 * wieder je nach Parametriesierung
	 * 
	 * @author Christian Scherer
	 * @param setError
	 *            true, wenn eine Fehleranzeige gezeigt werden soll und false,
	 *            wenn die Fehleranzeige geloescht werden soll
	 * @param component
	 *            Identifiziert den Componenten, bei dem die Fehleranzeige
	 *            angezeigt bzw. entfernt werden soll
	 * @param message
	 *            Fehlermeldung die neben dem Componenten gezeigt werden soll
	 * 
	 */
	void setComponentError(boolean setError, String component, String message);

	/**
	 * Diese Methode graut das Textfeld 'textfieldCashFlowStepRange' aus.
	 * 
	 * @author Christian Scherer
	 * @param enabled
	 *            true aktiviert den Kombonenten, false deaktiviert (graut aus)
	 *            den Komponenten
	 */
	void activateCashFlowStepRang(boolean enabled);

	/**
	 * Diese Methode graut das Textfeld 'textfieldCashFlowProbabilityOfRise' aus.
	 * 
	 * @author Christian Scherer
	 * @param enabled
	 *            true aktiviert den Kombonenten, false deaktiviert (graut aus)
	 *            den Komponenten
	 */
	void activateCashFlowProbabilityOfRise(boolean enabled);

	/**
	 * Diese Methode graut das Textfeld 'textfieldBorrowedCapitalProbabilityOfRise' aus.
	 * 
	 * @author Christian Scherer
	 * @param enabled
	 *            true aktiviert den Kombonenten, false deaktiviert (graut aus)
	 *            den Komponenten
	 */
	void activateBorrowedCapitalProbabilityOfRise(boolean enabled);

	/**
	 * Diese Methode graut das Textfeld 'textfieldBorrowedCapitalStepRange' aus.
	 * 
	 * @author Christian Scherer
	 * @param enabled
	 *            true aktiviert den Kombonenten, false deaktiviert (graut aus)
	 *            den Komponenten
	 */
	void activateBorrowedCapitalStepRange(boolean enabled);
	
	/**
	 * Diese Methode graut das Textfeld 'textfieldRiseOfPeriods' aus.
	 * 
	 * @author Christian Scherer
	 * @param enabled
	 *            true aktiviert den Kombonenten, false deaktiviert (graut aus)
	 *            den Komponenten
	 */
	public void activateRiseOfPeriods(boolean enabled);
	
	/**
	 * Diese Methode graut die Checkbox 'checkboxRiseOfPeriods' aus.
	 * 
	 * @author Christian Scherer
	 * @param enabled
	 *            true aktiviert den Kombonenten, false deaktiviert (graut aus)
	 *            den Komponenten
	 */
	public void activateRiseOfPeriodsCheckbox(boolean enabled);
	
	/**
	 * Diese Methode graut das Textfeld 'textfieldDeviaton' aus.
	 * 
	 * @author Christian Scherer
	 * @param enabled
	 *            true aktiviert den Kombonenten, false deaktiviert (graut aus)
	 *            den Komponenten
	 */
	public void activateDeviation(boolean enabled);
	

	/**
	 * Diese Methode graut die Checkbox 'checkboxDeviationOfPeriods' aus.
	 * 
	 * @author Christian Scherer
	 * @param enabled
	 *            true aktiviert den Kombonenten, false deaktiviert (graut aus)
	 *            den Komponenten
	 */
	public void activateDeviationCheckbox(boolean enabled);
	
	/**
	 * Diese Methode graut das Textfeld 'textfieldStepsPerPeriod' aus.
	 * 
	 * @author Christian Scherer
	 * @param enabled
	 *            true aktiviert den Kombonenten, false deaktiviert (graut aus)
	 *            den Komponenten
	 */
	void activateStepsPerPeriod(boolean enabled);

	/**
	 * Setzt den Wert des Texfelds 'Wahl des Basisjahr'
	 * 
	 * @author Christian Scherer
	 * @param basisYear
	 *            Das Jahr, das Basis-Jahr, auf das die Cashflows abgezinst
	 *            werden
	 */
	void setValueBasisYear(String basisYear);
	
	/**
	 * Setzt den Wert des Texfelds 'Anzahl zu prognostizierender Perioden'
	 * 
	 * @author Christian Scherer
	 * @param periodsToForecast
	 *            Anzahl zu prognostizierender Perioden
	 */
	void setPeriodsToForecast(String periodsToForecast);

	/**
	 * Setzt den Wert des Texfelds 'Anzahl Wiederholungen'
	 * 
	 * @author Christian Scherer
	 * @param iterations
	 *            Anzahl Wiederholungen
	 */
	void setIterations(String iterations);

	/**
	 * Setzt den Wert des Texfelds 'Anzahl einbezogener, vergangener Perioden'
	 * 
	 * @author Christian Scherer
	 * @param relevantPastPeriods
	 *            Anzahl einbezogener, vergangener Perioden
	 */
	void setRelevantPastPeriods(String relevantPastPeriods);

	/**
	 * Setzt den Wert des Texfelds 'Schrittweite Cashflows'
	 * 
	 * @author Christian Scherer
	 * @param cashFlowStepRange
	 *            Schrittweite der Cashflows
	 */
	void setCashFlowStepRange(String cashFlowStepRange);

	/**
	 * Setzt den Wert des Texfelds 'Wahrscheinlichkeit für steigende Cashflowentwicklung'
	 * 
	 * @author Christian Scherer
	 * @param cashFlowProbabilityOfRise
	 *            Wahrscheinlichkeit für steigende Cashflowentwicklung
	 */
	void setCashFlowProbabilityOfRise(String cashFlowProbabilityOfRise);

	/**
	 * Setzt den Wert des Texfelds 'Schrittweite Fremdkapital'
	 * 
	 * @author Christian Scherer
	 * @param borrowedCapitalStepRange
	 *            Schrittweite Fremdkapital
	 */
	void setBorrowedCapitalStepRange(String borrowedCapitalStepRange);

	/**
	 * Setzt den Wert des Texfelds 'Wahrscheinlichkeit für steigende Fremdkaptialentwicklung'
	 * 
	 * @author Christian Scherer
	 * @param borrowedCapitalProbabilityOfRise
	 *            Wahrscheinlichkeit für steigende Fremdkaptialentwicklung
	 */
	void setBorrowedCapitalProbabilityOfRise(String borrowedCapitalProbabilityOfRise);

}