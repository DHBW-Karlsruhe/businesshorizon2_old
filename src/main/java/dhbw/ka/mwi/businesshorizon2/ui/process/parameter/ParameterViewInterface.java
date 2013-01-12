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
	 * Diese Methode graut die Checkbox fuer die Branchenstellvertreter aus, da
	 * diese Funktionalitaet noch nicht gegeben ist.
	 * 
	 * @author Christian Scherer
	 */
	void greyOutCheckboxIndustryRepresentative();

	/**
	 * Diese Methode graut die ComboBox (DropDown-Liste) fuer die
	 * Branchenstellvertreter aus, da diese Funktionalitaet noch nicht gegeben
	 * ist.
	 * 
	 * @author Christian Scherer
	 */
	void greyOutComboBoxRepresentatives();

	/**
	 * Loescht den gesetzten Wert des Texfelds 'Anzahl zu prognostizierender
	 * Perioden'
	 * 
	 * @author Christian Scherer
	 */
	void clearTextFieldnumPeriodsToForecast();

	/**
	 * Loescht den gesetzten Wert des Texfelds 'Anzahl einbezogener, vergangener
	 * Perioden'
	 * 
	 * @author Christian Scherer
	 */
	void clearTextFieldNumPastPeriods();

	/**
	 * Setzt den Wert des Texfelds 'Wahl des Basisjahr'
	 * 
	 * @author Christian Scherer
	 * @param basisYear
	 *            Das Jahr, das Basis-Jahr, auf das die Cashflows abgezinst
	 *            werden
	 */
	void setTextFieldValueBasisYear(String basisYear);

}