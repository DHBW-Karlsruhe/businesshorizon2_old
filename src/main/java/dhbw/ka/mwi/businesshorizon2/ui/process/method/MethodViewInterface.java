
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

package dhbw.ka.mwi.businesshorizon2.ui.process.method;

import dhbw.ka.mwi.businesshorizon2.methods.AbstractCalculationMethod;
import dhbw.ka.mwi.businesshorizon2.methods.AbstractStochasticMethod;
import dhbw.ka.mwi.businesshorizon2.models.CashflowSource;
import dhbw.ka.mwi.businesshorizon2.models.InputType;
import dhbw.ka.mwi.businesshorizon2.ui.process.contentcontainer.ContentView;

/**
 * Dieses Interface zeigt die von der View zur Verfuegung stehenden Methoden,
 * mit denen der Presenter mit der View kommunizieren kann.
 * 
 * @author Christian Gahlert, Annika Weis
 *
 */
public interface MethodViewInterface extends ContentView {

	//public void showStochasticMethod(AbstractStochasticMethod method);
	
	//Annika Weis
	//public void addCalculationMethod(AbstractCalculationMethod method);

	//public void enableStochasticMethodSelection(Boolean state);

	//public void setStochastic(Boolean checked);
	
	//public void setDeterministic(Boolean checked);
	
	public void selectInput(Boolean stochastic,InputType selected);
	
	//public void showErrorNoMethodSelected(Boolean state);
	
	public void showErrorNothingSelected(Boolean state);

	public void showMethodView();

	public void showCashflowSourcePanel(Boolean checked);

	public void showDeterministicInputPanel();

	public void showStochasticInputPanel();

	public void hideInputPanels();

	public void selectCalculation(AbstractCalculationMethod selected);

	public void selectCashflow(CashflowSource selected);
	
	
}
