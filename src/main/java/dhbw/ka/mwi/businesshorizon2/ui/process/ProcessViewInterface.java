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

import com.mvplite.view.View;

/**
 * Dieses Interface zeigt die von der View zur Verfuegung stehenden Methoden,
 * mit denen der Presenter mit der View kommunizieren kann.
 * 
<<<<<<< HEAD
<<<<<<< HEAD
 * @author Christian Gahlert
=======
 * @author Julius Hacker
>>>>>>> refs/heads/master
=======
 * @author Julius Hacker
>>>>>>> branch 'master' of local repository
 *
 */
public interface ProcessViewInterface extends View {
	
	/**
	 * Diese Methode setzt die obere und untere View in der Prozessansicht.
	 * 
	 * @param topView Die obere View-Komponente, gedacht fuer die Navigation
	 * @param bottomView Die untere View-Komponente, gedacht fuer die anzuzeigenden Masken.
	 * @author Julius Hacker
	 */
	public void showView(View topView, View bottomView);

	public void showView(View topVie, View bottomViewleft, View bottomViewRigth);
}
