package dhbw.ka.mwi.businesshorizon2.ui.process.output;

import dhbw.ka.mwi.businesshorizon2.ui.process.contentcontainer.ContentView;
import dhbw.ka.mwi.businesshorizon2.ui.process.output.charts.DeterministicChartArea;
import dhbw.ka.mwi.businesshorizon2.ui.process.output.charts.StochasticChartArea;

public interface OutputViewInterface extends ContentView {


	void showOutputView();

	/**
	 * Fügt der View einen Ausgabebereich mit den Ergebnissen des stochastischen
	 * Verfahrens hinzu
	 * 
	 * @param chartArea
	 *            Eine ChartArea mit stochastischen Ergebnissen
	 */
	void addStochasticChartArea(StochasticChartArea chartArea);

	/**
	 * Fügt der View einen Ausgabebereich mit den Ergebnissen des
	 * deterministischen Verfahrens hinzu
	 * 
	 * @param chartArea
	 *            Eine ChartArea mit deterministischen Ergebnissen
	 */
	void addDeterministicChartArea(DeterministicChartArea chartArea);

	void showErrorMessge(String message);

	void changeProgress(float progress);


}
