/*******************************************************************************
 * BusinessHorizon2
 * 
 *     Copyright (C) 2012-2013  Christian Gahlert, Florian Stier, Kai Westerholz,
 *     Timo Belz, Daniel Dengler, Katharina Huber, Christian Scherer, Julius Hacker
 * 
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Affero General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 * 
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Affero General Public License for more details.
 * 
 *     You should have received a copy of the GNU Affero General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/

/*
 * Annika Weis
 */


package dhbw.ka.mwi.businesshorizon2.models;

import java.util.ArrayList;
import java.util.TreeSet;

import dhbw.ka.mwi.businesshorizon2.models.PeriodContainer.AbstractPeriodContainer;

/**
 * Diese Klasse stellt ein Ergebnis einer Deterministischen Methode zur Verfügung.
 * Dies bedeutet, dass sie eine Liste von PeriodenContainer enthaelt.
 * 
 * @author Kai Westerholz
 * 
 */

public class DeterministicResultContainer {
	TreeSet<? extends AbstractPeriodContainer> periodContainer = new TreeSet<>();
	
	//Annika Weis
	ArrayList <Double> unternehmenswerte = new ArrayList<Double>();
	
	public DeterministicResultContainer(
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
	
	/**
	 * Diese Methode nimmt den Unternehmenswert auf
	 * 
	 * @return void
	 * @author Annika Weis
	 */
	public void setUnternehmenswert(Double unternehmenswert){
		this.unternehmenswerte.add(unternehmenswert);
	}

	/**
	 * Diese Methode liefert den Unternehemnswert als Arraylist
	 * 
	 * @return Unternhemswert ArrayList<Double>
	 * @author Annika Weis
	 */
	public ArrayList<Double> getUnternehmenswert(){
		return unternehmenswerte;
	}
	
	/**
	 * Diese Methode liefert den Unternehemnswert im Array
	 * 
	 * @return Unternhemswert (Double[])
	 * @author Annika Weis
	 */
	public Double[] getUnternehemnswert(){
		return (Double[]) unternehmenswerte.toArray();
	}
}
