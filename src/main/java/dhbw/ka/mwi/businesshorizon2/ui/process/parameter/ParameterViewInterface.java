package dhbw.ka.mwi.businesshorizon2.ui.process.parameter;

import dhbw.ka.mwi.businesshorizon2.ui.process.contentcontainer.ContentView;

public interface ParameterViewInterface extends ContentView {

	/**
	 * Diese Methode setzt das Array der Iteraionsschritte, welches spaeter fuer
	 * die Benutzerangebe noetig ist
	 * 
	 * @author Christian Scherer
	 * @param numberIterations
	 *            Das Iteraionsarray mit Werte 1.000 10.000 und 1000.000
	 */
	void setIterations(int[] numberIterations);

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
	 * Setzt den Wert des Texfelds 'Wahl des Basisjahr'
	 * 
	 * @author Christian Scherer
	 * @param basisYear
	 *            Das Jahr, das Basis-Jahr, auf das die Cashflows abgezinst
	 *            werden
	 */
	void setTextFieldValueBasisYear(String basisYear);

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

}