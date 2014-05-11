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

package dhbw.ka.mwi.businesshorizon2.ui.process;

import com.mvplite.event.Event;

import dhbw.ka.mwi.businesshorizon2.ui.process.navigation.NavigationSteps;

/**
 * Der Event, der zum Anzeigen eines Prozessschrittes gefeuert wird.
 * 
 * @author Julius Hacker
 *
 */
public class ShowNavigationStepEvent extends Event {
	private static final long serialVersionUID = 1L;
	
	private NavigationSteps step;

	/**
	 * Der Konstruktor setzt den Navigationsstep, der angezeigt
	 * werden soll. Dieser kann spaeter durch die bearbeitende
	 * Funktion durch getStep() wieder ausgelesen werden.
	 * 
	 * @param step
	 * @author Julius Hacker
	 */
	public ShowNavigationStepEvent(NavigationSteps step) {
		this.step = step;
	}
	
	/**
	 * Die Methode liefert den anzuzeigenden Navigationsschritt zurueck.
	 * 
	 * @return Der anzuzeigende Navigationsschritt.
	 * @author Julius Hacker
	 */
	public NavigationSteps getStep() {
		return step;
	}

}
