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

package dhbw.ka.mwi.businesshorizon2.models.PeriodContainer;

import java.io.Serializable;
import java.util.TreeSet;

import dhbw.ka.mwi.businesshorizon2.models.Period.Period;

/**
 * Diese Klasse stellt die Oberklasse aller PeriodenContainer da und stellt die
 * Methode getPeriods zur Verfügung, um auf die Perioden zuzugreufen. Das Set
 * der Perioden wird initialisiert und kann mit getPeriods().add() verwendet
 * werden.
 * 
 * @author Kai Westerholz
 * 
 */
abstract public class AbstractPeriodContainer implements Comparable<AbstractPeriodContainer>, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4351757223322455067L;
	private TreeSet<Period> perioden;
	private static int counter = 0;
	private final int myCount;

	public AbstractPeriodContainer() {
		this.perioden = new TreeSet<>();
		this.myCount = counter;
		counter++;
	}

	public void addPeriod(Period period) {
		this.perioden.add(period);
	}
	
	public void setPeriods(TreeSet<Period> _periods){
		this.perioden = _periods;
	}

	/**
	 * Diese Methode liefer die Referenz auf das Set der Perioden zurück.
	 * 
	 * @return SortedSet der Perioden
	 * @author Kai Westerholz
	 */
	public TreeSet<? extends Period> getPeriods() {
		return perioden;
	}

	public int getCounter() {
		return this.myCount;
	}

	@Override
	public int compareTo(AbstractPeriodContainer o) {
		if (this.myCount < o.getCounter()) {
			return -1;
		} else if (this.myCount == o.getCounter()) {
			return 0;
		} else {
			return 1;
		}
	}

	public void removePeriod(Period period) {
		this.perioden.remove(period);
	}

}
