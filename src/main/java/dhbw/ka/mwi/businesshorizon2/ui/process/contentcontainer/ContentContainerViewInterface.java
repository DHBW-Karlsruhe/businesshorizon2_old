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
package dhbw.ka.mwi.businesshorizon2.ui.process.contentcontainer;

import com.mvplite.view.View;

/**
 * Dieses Interface zeigt die von bezueglich des Maskenfensters zur Verfuegung
 * stehenden Methoden, mit denen der Presenter mit der View kommunizieren kann.
 * 
 * @author Julius Hacker
 * 
 */
public interface ContentContainerViewInterface extends View {

	/**
	 * Diese Methode kuemmert sich um die Anzeige der ihr vom Presenter
	 * uebergebene Maske in Form einer ContentView.
	 * 
	 * @param newView
	 *            Die anzuzeigende Maske in Form einer ContentView.
	 * @author Julius Hacker
	 */
	public void showContentView(ContentView newView);

	/**
	 * Diese Methode kuemmert sich darum, den Weiter-Button unter der
	 * ContentView zu aktivieren bzw. deaktivieren. Dies hat den Grund, dass am
	 * Ende des Prozesses nichtmehr weitergegangen werden kann und der Button
	 * somit nichtmehr funktionsfaehig sein darf.
	 * 
	 * @param activate
	 *            true: Weiter-Button soll aktiviert werden. false:
	 *            Weiter-Button soll deaktiviert werden.
	 * @author Julius Hacker
	 */
	public void activateNext(boolean activate);

	/**
	 * Diese Methode kuemmert sich darum, den Weiter-Button unter der
	 * ContentView zu aktivieren bzw. deaktivieren. Dies hat den Grund, dass am
	 * Anfang des Prozesses nicht noch weiter vorgegangen werden kann und der
	 * Button in dieser Situation nicht funktionsfaehig sein darf.
	 * 
	 * @param activate
	 *            true: Zurueck-Button soll aktiviert werden. false:
	 *            Zurueck-Button soll deaktiviert werden. >>>>>>> branch
	 *            'master' of local repository
	 * @author Julius Hacker
	 */
	public void activateBack(boolean activate);
}
