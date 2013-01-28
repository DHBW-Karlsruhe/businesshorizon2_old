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


package dhbw.ka.mwi.businesshorizon2.ui.process;

import com.mvplite.presenter.Presenter;
import com.mvplite.view.View;

/**
 * Diese Klasse ist die Oberklasse fuer die Presenter der Prozessmasken. Sie
 * stellt insbesondere die beiden Methoden isValid und isSelectable zur
 * Verfuegung, die es der Prozessverwaltung ermoeglichen, zu entscheiden, ob
 * sich eine Maske in einem gueltigen Zustand befindet und ob sie aufrufbar ist.
 * 
 * @author Julius Hacker
 * @param <T>
 *            Die zum Presenter gehoerende View.
 */
public abstract class ScreenPresenter<T extends View> extends Presenter<T> {
	private static final long serialVersionUID = 1L;

	/**
	 * Diese Methode gibt zurueck, ob die Eingabewerte der Maske insgesamt
	 * gueltig sind oder ob auf der Maske Korrekturen vorgenommen werden
	 * muessen, um den Wizard abschliessen zu koennen.
	 * 
	 * @return true: Eingabewerte der Maske sind gueltig false: Eingabewerte der
	 *         Maske sind ungueltig und benoetigen Korrektur
	 * @author Julius Hacker
	 */
	public abstract boolean isValid();

	/**
	 * Diese Methode ueberprueft auf Anforderung durch ein Event, ob die
	 * Eingaben im aktuellen Screen korrekt oder inkorrekt sind. Bei validen
	 * Eingaben sollte sie ein ValidStateEvent feuern, bei invaliden Eingaben
	 * ein InvalidStateEvent.
	 * 
	 * 
	 * @param event
	 *            Das gefeuerte ValidContentStateEvent
	 * @author Julius Hacker
	 */
	public abstract void validate(ValidateContentStateEvent event);

	/**
	 * Diese Methode kuemmert sich um das ShowErrorsOnScreenEvent. Dieses sagt
	 * insbesondere aus, dass ab sofort in dem Screen, der im Event angegeben
	 * ist, Fehlermeldungen angezeigt werden sollen. Die Methode sollte
	 * ueberpruefen, ob das Event fuer ihren Screen relevant ist (per
	 * event.getStep()) und gegebenenfalls das Verhalten des Screens
	 * entsprechend umstellen, sodass Fehlermeldungen angezeigt werden.
	 * 
	 * @param event
	 */
	public abstract void handleShowErrors(ShowErrorsOnScreenEvent event);
}
