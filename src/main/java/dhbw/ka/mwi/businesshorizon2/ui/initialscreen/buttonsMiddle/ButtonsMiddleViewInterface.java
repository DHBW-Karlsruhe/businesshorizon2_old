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

package dhbw.ka.mwi.businesshorizon2.ui.initialscreen.buttonsMiddle;

import com.mvplite.view.View;

/**
 * Dieses Interface zeigt die von der View zur Verfuegung stehenden Methoden,
 * mit denen der Presenter mit der View kommunizieren kann.
 * 
 * @author Tobias Lindner
 * 
 */
public interface ButtonsMiddleViewInterface extends View {
	
	public void setStochasticParameter ();
	
	public void setDeterministicParameter ();
	
	public void setGoToStep(int step);
	
	public void setInitialButtons();
	
	/**
	 * Diese Methode passt die Beschriftung des mittleren Buttons an FCF an.
	 * 
	 * @author Tobias Lindner
	 */
	public void setFCFButton();
	
	/**
	 * Diese Methode passt die Beschriftung des mittleren Buttons an GKV an.
	 * 
	 * @author Tobias Lindner
	 */
	public void setGKVButton();
	
	/**
	 * Diese Methode passt die Beschriftung des mittleren Buttons an UKV an.
	 * 
	 * @author Tobias Lindner
	 */
	public void setUKVButton();
	
	/**
	 * Diese Methode passt die Beschriftung des mittleren Buttons für den ScenarioScreen an.
	 * 
	 * @author Tobias Lindner
	 */
	public void setScenarioButton();
	
	/**
	 * Diese Methode passt die Beschriftung des mittleren Buttons für den Result an.
	 * 
	 * @author Tobias Lindner
	 */
	public void setResultButton();
	
	public void hideStepButton();
	
	public void enableNext ();
	
	public void disableNext();

}

