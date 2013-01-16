package dhbw.ka.mwi.businesshorizon2.ui.process.output;

import dhbw.ka.mwi.businesshorizon2.ui.process.contentcontainer.ContentView;
import dhbw.ka.mwi.businesshorizon2.ui.process.output.charts.DeterministicChartArea;
import dhbw.ka.mwi.businesshorizon2.ui.process.output.charts.StochasticChartArea;

public interface OutputViewInterface extends ContentView {

	void addStochasticChartArea(StochasticChartArea chartArea);

	void addDeterministicChartArea(DeterministicChartArea chartArea);

}