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

package dhbw.ka.mwi.businesshorizon2.ui.initialscreen;

import com.mvplite.view.View;

/**
* Dieses Interface zeigt die von der View zur Verfuegung stehenden Methoden,
* mit denen der Presenter mit der View kommunizieren kann.
*
* @author Christian Scherer
*
*/

public interface InitialScreenViewInterface extends View {

/**
* Diese Methode ist dafuer verantwortlich, die jeweiligen 2 View-Elemente
* anzuzeigen. Bei den Elementen handelt es sich um die entsprechenden
* ViewImpl-Instanzen.
*
* @author Christian Scherer
* @param leftView
* Die View im Hauptfenster links
* @param rightView
* Die View im Hauptfenster rechts
*/
public void showView(View leftView, View rightView);

/**
* Diese Methode zeigt im Header zusätzlich die Daten des eingeloggten User
* an
*
* @param username
* Der angezeigte Username
*/
public void showUserData(String username);

}