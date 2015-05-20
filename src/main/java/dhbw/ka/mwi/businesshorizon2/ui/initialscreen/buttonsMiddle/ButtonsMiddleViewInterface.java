/*******************************************************************************
 * BusinessHorizon2
 *
 * Copyright (C) 
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
	
	public void setFCFButton();
	
	public void setGKVButton();
	
	public void setUKVButton();
	
	public void setScenarioButton();
	
	public void setResultButton();
	
	public void hideStepButton();
	
	public void enableNext ();
	
	public void disableNext();

}

