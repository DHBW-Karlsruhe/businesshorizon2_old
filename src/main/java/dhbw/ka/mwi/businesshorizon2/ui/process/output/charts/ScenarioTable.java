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
package dhbw.ka.mwi.businesshorizon2.ui.process.output.charts;

import com.vaadin.ui.Table;

import dhbw.ka.mwi.businesshorizon2.models.Szenario;

/**
 * Tabelle zur Anzeige der jeweiligen Szenariowerte
 * 
 * @author Marcel Rosenberger
 * 
 */
public class ScenarioTable extends Table {

	public ScenarioTable(Szenario scenario) {
		addContainerProperty("Eingegebene Werte", String.class, null);
		addContainerProperty("Wert", Double.class, null);
		addItem(new Object[] {
				"Gewerbesteuer (in %)",new Double(scenario.getBusinessTax())}, null);
		addItem(new Object[] {
				"Kst. Incl. SoliZu.(in %)",new Double(scenario.getCorporateAndSolitaryTax())}, null);
		addItem(new Object[] {
				"EK Kosten(in %) ",new Double(scenario.getRateReturnEquity())}, null);
		addItem(new Object[] {
				"FK Kosten(in %) ",new Double(scenario.getRateReturnCapitalStock())}, null);
	}

}
