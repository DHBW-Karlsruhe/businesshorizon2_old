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

package dhbw.ka.mwi.businesshorizon2.models;

import java.io.Serializable;
import java.util.Iterator;
import java.util.TreeSet;

import dhbw.ka.mwi.businesshorizon2.models.Period.CashFlowPeriod;
import dhbw.ka.mwi.businesshorizon2.models.Period.Period;
import dhbw.ka.mwi.businesshorizon2.models.PeriodContainer.AbstractPeriodContainer;

/**
 * Diese Klasse stellt ein Ergebnis einer Stochastischen Methode zur Verfügung.
 * Dies bedeutet, dass sie eine Liste von PeriodenContainer enthaelt.
 * 
 * @author Kai Westerholz
 * 
 */

public class StochasticResultContainer implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	TreeSet<? extends AbstractPeriodContainer> periodContainer = new TreeSet<>();

	public StochasticResultContainer(
			TreeSet<? extends AbstractPeriodContainer> tree) {
		periodContainer = tree;
	}

	/**
	 * Diese Methode liefert die Referenz auf die Liste der Container zurueck.
	 * 
	 * @return Referenz auf Liste der Container
	 * @author Kai Westerholz
	 */

	public TreeSet<? extends AbstractPeriodContainer> getPeriodContainers() {
		return this.periodContainer;
	}
	
}