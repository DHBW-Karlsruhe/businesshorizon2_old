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

package dhbw.ka.mwi.businesshorizon2.ui.parameterScreen.input;

import dhbw.ka.mwi.businesshorizon2.ui.process.contentcontainer.ContentView;

public interface ParameterInputViewInterface extends ContentView {

	/**
	 * Gibt eine Fehlermeldung an den Benutzer aus.
	 * 
	 * @author Christian Scherer
	 * @param message
	 *            Fehlermeldung die der Methode zur Ausgabe uebergeben wird
	 */
	void showErrorMessage(String message);

	/**
	 * Diese Methode graut das Textfeld 'textfieldNumPeriods' aus.
	 * 
	 * @author Christian Scherer
	 * @param enabled
	 *            true aktiviert den Komponenten, false deaktiviert (graut aus)
	 *            den Komponenten
	 */
	void activatePeriodsToForecast(boolean enabled);

	/**
	 * Diese Methode graut das Textfeld 'textfieldNumPeriods_deterministic' aus.
	 * TODO Welches Feld???
	 * @author Annika Weis
	 * @param enabled
	 *            true aktiviert den Komponenten, false deaktiviert (graut aus)
	 *            den Komponenten
	 */
	void activatePeriodsToForecast_deterministic(boolean enabled);

	/**
	 * Diese Methode graut das Textfeld 'textfieldNumSpecifiedPastPeriods' aus.
	 * 
	 * @author Marcel Rosenberger
	 * @param enabled
	 *            true aktiviert die Komponente, false deaktiviert (graut aus)
	 *            die Komponente
	 */
	void activateSpecifiedPastPeriods(boolean enabled);
	
	/**
	 * Diese Methode graut das Textfeld 'textfieldNumPastPeriods' aus.
	 * 
	 * @author Christian Scherer
	 * @param enabled
	 *            true aktiviert den Komponenten, false deaktiviert (graut aus)
	 *            den Komponenten
	 */
	void activateRelevantPastPeriods(boolean enabled);

	/**
	 * Diese Methode graut die ComboBox 'comboBoxIteraions' aus.
	 * 
	 * @author Christian Scherer
	 * @param enabled
	 *            true aktiviert den Komponenten, false deaktiviert (graut aus)
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
	 * Setzt den Wert des Texfelds 'Anzahl zu prognostizierender Perioden' bei den deterministischen Verfahren
	 * 
	 * @author Annika Weis
	 * @param periodsToForecast_deterministic
	 *            Anzahl zu prognostizierender Perioden (deterministisch)
	 */
	void setPeriodsToForecast_deterministic(String periodsToForecast_deterministic);

	/**
	 * Setzt den Wert des Texfelds 'Anzahl Wiederholungen'
	 * 
	 * @author Christian Scherer
	 * @param iterations
	 *            Anzahl Wiederholungen
	 */
	void setIterations(String iterations);
	
	/**
	 * Setzt den Wert des Texfelds 'Anzahl anzugebender, vergangener Perioden'
	 * 
	 * @author Marcel Rosenberger
	 * @param specifiedPastPeriods
	 *            Anzahl anzugebender, vergangener Perioden
	 */
	void setSpecifiedPastPeriods(String specifiedPastPeriods);

	/**
	 * Setzt den Wert des Texfelds 'Anzahl einbezogener, vergangener Perioden'
	 * 
	 * @author Christian Scherer
	 * @param relevantPastPeriods
	 *            Anzahl einbezogener, vergangener Perioden
	 */
	void setRelevantPastPeriods(String relevantPastPeriods);

	void showParameterView();

	public void setDeterministicParameters();
	
	public void setStochasticParameters();
	



}
