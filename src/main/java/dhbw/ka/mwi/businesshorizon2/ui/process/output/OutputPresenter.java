package dhbw.ka.mwi.businesshorizon2.ui.process.output;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.mvplite.event.EventBus;
import com.mvplite.event.EventHandler;

import dhbw.ka.mwi.businesshorizon2.ui.process.ScreenPresenter;
import dhbw.ka.mwi.businesshorizon2.ui.process.ScreenSelectableEvent;
import dhbw.ka.mwi.businesshorizon2.ui.process.ShowErrorsOnScreenEvent;
import dhbw.ka.mwi.businesshorizon2.ui.process.ValidateContentStateEvent;
import dhbw.ka.mwi.businesshorizon2.ui.process.navigation.NavigationSteps;
import dhbw.ka.mwi.businesshorizon2.ui.process.output.charts.DeterministicChartArea;
import dhbw.ka.mwi.businesshorizon2.ui.process.output.charts.StochasticChartArea;

/**
 * Der Presenter fuer die Maske des Prozessschrittes zur Ergebnisausgabe.
 * 
 * @author Florian Stier
 * 
 */

public class OutputPresenter extends ScreenPresenter<OutputViewInterface> {
	private static final long serialVersionUID = 1L;

	private Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private EventBus eventBus;

	/**
	 * Dies ist der Konstruktor, der von Spring nach der Initialierung der
	 * Dependencies aufgerufen wird. Er registriert lediglich sich selbst als
	 * einen EventHandler.
	 * 
	 * @author Florian Stier
	 */
	@PostConstruct
	public void init() {
		eventBus.addHandler(this);
	}

	@EventHandler
	public void onShowOutputView(ShowOutputViewEvent event) {

		// ausgewählte Berechnungsverfahren holen
		// Für jedes Berechnungsverfahren die Berechnung triggern
		// Für jedes Berechnungsverfahren ein Chart generieren

		StochasticChartArea stochasticChart = new StochasticChartArea();
		DeterministicChartArea deterministicChart = new DeterministicChartArea();

		getView().addStochasticChartArea(stochasticChart);
		getView().addDeterministicChartArea(deterministicChart);

	}

	@Override
	public boolean isValid() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void validate(ValidateContentStateEvent event) {
		// TODO Auto-generated method stub

	}

	@EventHandler
	public void handleShowView(ShowOutputViewEvent event) {
		eventBus.fireEvent(new ScreenSelectableEvent(NavigationSteps.OUTPUT, true));
		logger.debug("ShowOutputViewEvent handled");
	}

	@Override
	public void handleShowErrors(ShowErrorsOnScreenEvent event) {
		// TODO Auto-generated method stub

	}

}
