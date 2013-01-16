package dhbw.ka.mwi.businesshorizon2.ui.process.output;

import java.util.TreeSet;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.mvplite.event.EventBus;
import com.mvplite.event.EventHandler;

import dhbw.ka.mwi.businesshorizon2.methods.CallbackInterface;
import dhbw.ka.mwi.businesshorizon2.methods.StochasticMethodException;
import dhbw.ka.mwi.businesshorizon2.methods.timeseries.TimeseriesCalculator;
import dhbw.ka.mwi.businesshorizon2.models.CashFlowPeriod;
import dhbw.ka.mwi.businesshorizon2.models.Project;
import dhbw.ka.mwi.businesshorizon2.models.StochasticResultContainer;
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

public class OutputPresenter extends ScreenPresenter<OutputViewInterface> implements CallbackInterface {
	private static final long serialVersionUID = 1L;

	private Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private EventBus eventBus;

	@Autowired
	private TimeseriesCalculator timeSeriesCalculator;

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

		StochasticResultContainer src;
		TreeSet<CashFlowPeriod> periods = new TreeSet<CashFlowPeriod>();
		CashFlowPeriod period1 = new CashFlowPeriod(2005);
		period1.setFreeCashFlow(130594000.00);
		periods.add(period1);
		CashFlowPeriod period2 = new CashFlowPeriod(2006);
		period2.setFreeCashFlow(147552000.00);
		periods.add(period2);
		CashFlowPeriod period3 = new CashFlowPeriod(2007);
		period3.setFreeCashFlow(144040000.00);
		periods.add(period3);
		CashFlowPeriod period4 = new CashFlowPeriod(2008);
		period4.setFreeCashFlow(146004000.00);
		periods.add(period4);
		CashFlowPeriod period5 = new CashFlowPeriod(2009);
		period5.setFreeCashFlow(154857000.00);
		periods.add(period5);
		CashFlowPeriod period6 = new CashFlowPeriod(2010);
		period6.setFreeCashFlow(162117000.00);
		periods.add(period6);
		Project project = new Project("Test Zeitreihenanalyse");
		project.setBasisYear(2010);
		project.setIterations(10000);
		project.setRelevantPastPeriods(2);
		project.setPeriodsToForecast(5);
		project.setPeriods(periods);

		try {
			src = timeSeriesCalculator.calculate(project, this);
			TreeSet<CashFlowPeriod> period = (TreeSet<CashFlowPeriod>) src.getPeriodContainers().first().getPeriods();
			StochasticChartArea stochasticChart = new StochasticChartArea(period);
			getView().addStochasticChartArea(stochasticChart);
		} catch (StochasticMethodException | InterruptedException e) {
			e.printStackTrace();
		}
		// Für jedes Berechnungsverfahren ein Chart generieren

		DeterministicChartArea deterministicChart = new DeterministicChartArea();

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

	@Override
	public void onComplete(StochasticResultContainer result) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProgressChange(float progress) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onError(Throwable t) {
		// TODO Auto-generated method stub

	}

}
