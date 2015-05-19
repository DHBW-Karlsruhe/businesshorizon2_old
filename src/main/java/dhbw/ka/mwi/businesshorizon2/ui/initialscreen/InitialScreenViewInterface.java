/*******************************************************************************
 * BusinessHorizon2
 *
 * Copyright (C) 
 * 2012-2013 Christian Gahlert, Florian Stier, Kai Westerholz,
 * Timo Belz, Daniel Dengler, Katharina Huber, Christian Scherer, Julius Hacker
 * 2013-2014 Marcel Rosenberger, Mirko Göpfrich, Annika Weis, Katharina Narlock, 
 * Volker Meier
 * 2014-2015 Marco Glaser, Tobias Lindner
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
import com.vaadin.ui.Button.ClickListener;

import dhbw.ka.mwi.businesshorizon2.ui.TopBarButton;

/**
 * Dieses Interface zeigt die von der View zur Verfuegung stehenden Methoden,
 * mit denen der Presenter mit der View kommunizieren kann.
 *
 * @author Christian Scherer, Marco Glaser, Tobias Lindner
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


	public void showProjectCreationScreen(View view);
	
	public void setTopButton(TopBarButton button, int index, ClickListener listener);
	
	public void deleteTopButton(int index);

	public void setInitialTopButtons();

	public void setInitialPageDescription();
	
	public void setPageDescription(String source, String page, String description);
	
	public void setPageDescription(String source, String page, String[] description);
	
	public void clearUnusedButtons(int count);
	
	public void setProgress(String resource);
	
	public void clearProgressBar();
	
	public void showExtendedView(View exView);
	
	public void showNotification (String warningText);
	
	public void showInfoNotification (String notificationText);
	
}