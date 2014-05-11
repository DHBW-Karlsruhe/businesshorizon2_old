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

package dhbw.ka.mwi.businesshorizon2.ui.initialscreen.projectlist;

import com.mvplite.event.Event;

import dhbw.ka.mwi.businesshorizon2.models.User;

/**
 * Das Event, der zum Anzeigen der ProjectListView, das vom
 * InitialScreenPresenter gefeuert wird. Es beinhaltet das eingeloggte
 * User-Objekt.
 * 
 * @author Christian Scherer
 * 
 */
public class ShowProjectListEvent extends Event {

	private static final long serialVersionUID = 1L;

	private User user;

	/**
	 * Der Konstrutkor empfängt ein User Objekt, welches er speichert um es
	 * später mittels der getter Methode weiterzugeben.
	 * 
	 * @author Christian Scherer
	 * @param user
	 *            Das Objekt des erfolgreich angemeldeten User
	 * 
	 */
	public ShowProjectListEvent(User user) {
		this.user = user;
	}

	/**
	 * Der Konstrutkor empfängt ein User Objekt, welches er speichert um es
	 * später mittels der getter Methode weiterzugeben.
	 * 
	 * @author Christian Scherer
	 * @return Rückgabe des User Objekts
	 */
	public User getUser() {
		return this.user;

	}
}
