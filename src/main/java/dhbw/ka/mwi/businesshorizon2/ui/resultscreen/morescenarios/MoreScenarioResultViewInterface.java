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

package dhbw.ka.mwi.businesshorizon2.ui.resultscreen.morescenarios;

import org.vaadin.vaadinvisualizations.ColumnChart;

import com.mvplite.view.View;
import com.vaadin.ui.Label;

import dhbw.ka.mwi.businesshorizon2.ui.process.contentcontainer.ContentView;
import dhbw.ka.mwi.businesshorizon2.ui.process.output.charts.BasicLineChart;
import dhbw.ka.mwi.businesshorizon2.ui.process.output.charts.DeterministicChartArea;
import dhbw.ka.mwi.businesshorizon2.ui.process.output.charts.StochasticChartArea;

public interface MoreScenarioResultViewInterface extends View {


	public void showOutputView();

	/**
	 * Fügt der View einen Ausgabebereich mit den Ergebnissen des stochastischen
	 * Verfahrens hinzu
	 * 
	 * @param chartArea
	 *            Eine ChartArea mit stochastischen Ergebnissen
	 */
	public void addStochasticChartArea(StochasticChartArea chartArea, int number);

	/**
	 * Fügt der View einen Ausgabebereich mit den Ergebnissen des
	 * deterministischen Verfahrens hinzu
	 * 
	 * @param chartArea
	 *            Eine ChartArea mit deterministischen Ergebnissen
	 */

	public void addDeterministicChartArea(DeterministicChartArea chartArea, int number);
	
	public void addHeadline(Label head);
	
	public void addSubline(Label head);
	
	public void addSubline(Label head, Label abw);
	
	public void addLabel(Label label);
	
	public void showErrorMessge(String message);

	public void changeProgress(float progress);

	public void createLayout();
	
	public void setScenarioValue(int numScenario, String renditeEK, String renditeFK, String gewerbeSt, String koerperSt, String companyValue);
	
	public void addScenario3ToLayout ();
	
	public void addChartScenario(int numScenario, ColumnChart chart);



}
