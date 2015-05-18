/*******************************************************************************
 * BusinessHorizon2
 *
 * Copyright (C) 
 * 2012-2013 Christian Gahlert, Florian Stier, Kai Westerholz,
 * Timo Belz, Daniel Dengler, Katharina Huber, Christian Scherer, Julius Hacker
 * 2013-2014 Marcel Rosenberger, Mirko Göpfrich, Annika Weis, Katharina Narlock, 
 * Volker Meier
 * 2014-2015 Marco Glaser, Tobias Lindner
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

package dhbw.ka.mwi.businesshorizon2.ui.scenarioscreen;

import dhbw.ka.mwi.businesshorizon2.ui.ContentView;

/**
 * 
 * @author Julius Hacker, Tobias Lindner
 *
 */
public interface ScenarioScreenViewInterface extends ContentView {

	public void clear();
	
	public void addScenario(String rateReturnEquity,
			String rateReturnCapitalStock, String corporateAndSolitaryTax,
			String businessTax, boolean isIncludeInCalculation, int numberOfScenario);
	
	public void removeScenario(int number);
	
	public void updateLabels();
	
	public String getValue(int scenarioNumber, String identifier);
	
	public void setIncludedInCalculation(int scenarioNumber, boolean newValue);
	public void setRateReturnEquity(int scenarioNumber, String newValue);
	public void setRateReturnCapitalStock(int scenarioNumber, String newValue);
	public void setBusinessTax(int scenarioNumber, String newValue);
	public void setCorporateAndSolitaryTax(int scenarioNumber, String newValue);
	
	public void setInvalid(int scenarioNumber, String identifier);
	
	public void setValid(int scenarioNumber, String identifier);

	public Boolean getIncludeInCalculation(int scenarioNumber);
	
}
