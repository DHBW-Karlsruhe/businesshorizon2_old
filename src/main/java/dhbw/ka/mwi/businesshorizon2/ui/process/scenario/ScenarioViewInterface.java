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
	public void removeScenario(int number);
	
	public void updateLabels();
	
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

	public Boolean getIncludeInCalculation(int scenarioNumber);
}
