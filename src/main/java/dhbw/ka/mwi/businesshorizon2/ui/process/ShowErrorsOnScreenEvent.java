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
<<<<<<< HEAD
<<<<<<< HEAD
 * Der Event, der gefeuert wird, um die Ausgabe von Fehlermeldungen auf einem Prozess-Screen
 * zu aktivieren.
=======
 * Der Event, der gefeuert wird, um die Ausgabe von Fehlermeldungen auf einem
 * Prozess-Screen zu aktivieren.
>>>>>>> refs/heads/master
=======
 * Der Event, der gefeuert wird, um die Ausgabe von Fehlermeldungen auf einem
 * Prozess-Screen zu aktivieren.
>>>>>>> branch 'master' of local repository
 * 
 * @author Julius Hacker
 *
 */
public class ShowErrorsOnScreenEvent extends Event {
	private static final long serialVersionUID = 1L;
	
	private NavigationSteps step;

	/**
	 * Der Konstruktor setzt den Navigationsstep, auf dessen Screen
	 * ab sofort Fehlermeldungen angezeigt werden sollen.
	 * Dieser kann spaeter durch die bearbeitende Funktion
	 * durch getStep() wieder ausgelesen werden.
	 * 
	 * @param step der Navigationsstep, auf dessen Screen ab sofort Fehlermeldungen angezeigt werden sollen.
	 * @author Julius Hacker
	 */
	public ShowErrorsOnScreenEvent(NavigationSteps step) {
		this.step = step;
	}
	
	/**
	 * Die Methode liefert Navigationsschritt, auf dessen Screen
	 * ab sofort die Fehlermeldungen angezeigt werden sollen.
	 * 
	 * @return Der anzuzeigende Navigationsschritt.
	 * @author Julius Hacker
	 */
	public NavigationSteps getStep() {
		return step;
	}

}
