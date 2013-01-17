package dhbw.ka.mwi.businesshorizon2.ui.process.period.timeline;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.ibm.icu.impl.duration.Period;
import com.mvplite.event.EventBus;
import com.mvplite.event.EventHandler;

import dhbw.ka.mwi.businesshorizon2.models.InputType;
import dhbw.ka.mwi.businesshorizon2.models.Period.AggregateCostMethodPeriod;
import dhbw.ka.mwi.businesshorizon2.models.Period.CashFlowPeriod;
import dhbw.ka.mwi.businesshorizon2.models.Period.CostOfSalesMethodPeriod;
import dhbw.ka.mwi.businesshorizon2.models.Period.PeriodInterface;
import dhbw.ka.mwi.businesshorizon2.models.PeriodContainer.AbstractPeriodContainer;
import dhbw.ka.mwi.businesshorizon2.models.PeriodContainer.AggregateCostMethodBalanceSheetPeriodContainer;
import dhbw.ka.mwi.businesshorizon2.models.PeriodContainer.CashFlowPeriodContainer;
import dhbw.ka.mwi.businesshorizon2.models.PeriodContainer.CostOfSalesMethodPeriodContainer;
import dhbw.ka.mwi.businesshorizon2.services.proxies.ProjectProxy;
import dhbw.ka.mwi.businesshorizon2.ui.process.InvalidStateEvent;
import dhbw.ka.mwi.businesshorizon2.ui.process.ScreenPresenter;
import dhbw.ka.mwi.businesshorizon2.ui.process.ShowErrorsOnScreenEvent;
import dhbw.ka.mwi.businesshorizon2.ui.process.ValidStateEvent;
import dhbw.ka.mwi.businesshorizon2.ui.process.ValidateContentStateEvent;
import dhbw.ka.mwi.businesshorizon2.ui.process.navigation.NavigationSteps;
import dhbw.ka.mwi.businesshorizon2.ui.process.period.ShowPeriodViewEvent;

/**
 * Der Presenter fuer die Maske des Prozessschrittes zur Eingabe der Perioden.
 * 
 * @author Julius Hacker
 * 
 */

public class TimelinePresenter extends ScreenPresenter<TimelineViewInterface> {
	private static final long serialVersionUID = 1L;

	@Autowired
	ProjectProxy projectProxy;

	@Autowired
	EventBus eventBus;

	private int fixedPastPeriods;
	private int fixedFuturePeriods;

	private int sumPastPeriods;
	private int sumFuturePeriods;

	private int baseYear;

	private boolean showErrors;

	private AbstractPeriodContainer pastPeriods;
	private AbstractPeriodContainer futurePeriods;

	private Boolean deterministic;

	private Boolean stochastic;

	private InputType deterministicInput;

	private InputType stochasticInput;

	/**
	 * Dies ist der Konstruktor, der von Spring nach der Initialierung der
	 * Dependencies aufgerufen wird. Er registriert lediglich sich selbst als
	 * einen EventHandler.
	 * 
	 * @author Julius Hacker
	 */
	@PostConstruct
	public void init() {
		eventBus.addHandler(this);
	}

	@Override
	public boolean isValid() {
		try{
		if (projectProxy.getSelectedProject().getBasisYear() == baseYear
				&& projectProxy.getSelectedProject().getPeriodsToForecast() == fixedFuturePeriods
				&& projectProxy.getSelectedProject().getRelevantPastPeriods() == fixedPastPeriods
				&& projectProxy.getSelectedProject().getProjectInputType()
						.getDeterministic() == deterministic
				&& projectProxy.getSelectedProject().getProjectInputType()
						.getStochastic() == stochastic
				&& projectProxy.getSelectedProject().getProjectInputType()
						.getDeterministicInput() == deterministicInput
				&& projectProxy.getSelectedProject().getProjectInputType()
						.getStochasticInput() == stochasticInput)
			return true;
		else
			return false;
		}catch(Exception e){
			return false;
		}
	}

	@EventHandler
	public void onShowPeriodEvent(ShowPeriodViewEvent event) {
		// Wenn alles gleich geblieben ist müssen wir nichts tun
		if (isValid()) {
			return;
		} else {

			// Wenn sich unser Bezugsjahr ändert werden alle Perioden ungültig
			if (projectProxy.getSelectedProject().getBasisYear() != baseYear) {
				removeEverything();
			}

			// Wenn sich die relevanten Perioden ändern muss man nur auffüllen
			// sollten es mehr sein als vorhanden
			if (projectProxy.getSelectedProject().getRelevantPastPeriods() != fixedPastPeriods) {
				if (projectProxy.getSelectedProject().getRelevantPastPeriods() > sumPastPeriods) {
					// Hat sich zusätzlich die Eingabe geändert müssen alle
					// verworfen werden
					if (stochasticInput != projectProxy.getSelectedProject()
							.getProjectInputType().getStochasticInput()) {
						removeAllPastPeriods();
						addPastPeriods(projectProxy.getSelectedProject()
								.getRelevantPastPeriods(), projectProxy
								.getSelectedProject().getProjectInputType()
								.getStochasticInput());
						createContainer(pastPeriods,projectProxy
								.getSelectedProject().getProjectInputType()
								.getStochasticInput());
					} else {
						addPastPeriods(projectProxy.getSelectedProject()
								.getRelevantPastPeriods() - sumPastPeriods,
								stochasticInput);
					}
				}
				fixedPastPeriods = projectProxy.getSelectedProject()
						.getRelevantPastPeriods();
			}

			// Gleiches Vorgehen wie direkt darüber
			if (projectProxy.getSelectedProject().getPeriodsToForecast() != fixedFuturePeriods) {
				if (projectProxy.getSelectedProject().getPeriodsToForecast() > sumFuturePeriods) {
					if (deterministicInput != projectProxy.getSelectedProject()
							.getProjectInputType().getDeterministicInput()) {
						removeAllFuturePeriods();
						addFuturePeriods(projectProxy.getSelectedProject()
								.getPeriodsToForecast(), projectProxy
								.getSelectedProject().getProjectInputType()
								.getDeterministicInput());
						createContainer(futurePeriods,projectProxy
								.getSelectedProject().getProjectInputType()
								.getDeterministicInput());
					} else {
						addFuturePeriods(projectProxy.getSelectedProject()
								.getPeriodsToForecast() - sumFuturePeriods,
								deterministicInput);
					}
				}
				fixedFuturePeriods = projectProxy.getSelectedProject()
						.getPeriodsToForecast();
			}

			// Hat sich nur der Inputtyp geändert, müssen wir alle betroffenen
			// Perioden verworfen werden und neu angelegt werden.
			if (projectProxy.getSelectedProject().getProjectInputType()
					.getDeterministic() != deterministic) {
				removeAllFuturePeriods();
				fixedFuturePeriods = projectProxy.getSelectedProject()
						.getPeriodsToForecast();
				if ((projectProxy.getSelectedProject().getProjectInputType()
						.getDeterministic())) {
					createContainer(futurePeriods,projectProxy
							.getSelectedProject().getProjectInputType()
							.getDeterministicInput());
					addFuturePeriods(fixedFuturePeriods, projectProxy
							.getSelectedProject().getProjectInputType()
							.getDeterministicInput());

				}

			}

			// Siehe eins drüber
			if (projectProxy.getSelectedProject().getProjectInputType()
					.getStochastic() != stochastic) {
				removeAllPastPeriods();
				fixedPastPeriods = projectProxy.getSelectedProject()
						.getRelevantPastPeriods();
				if (!(projectProxy.getSelectedProject().getProjectInputType()
						.getStochastic())) {
					addPastPeriods(fixedPastPeriods, projectProxy
							.getSelectedProject().getProjectInputType()
							.getStochasticInput());
					
					createContainer(pastPeriods,projectProxy
							.getSelectedProject().getProjectInputType()
							.getStochasticInput());

				}

			}

		}

	}

	private void createContainer(AbstractPeriodContainer pastPeriods2,
			InputType stochasticInput2) {
		deterministicInput = stochasticInput2;
		switch(stochasticInput2){
		case TOTAL:
			pastPeriods2 = new AggregateCostMethodBalanceSheetPeriodContainer();
		case REVENUE:
			pastPeriods2 = new CostOfSalesMethodPeriodContainer();
		case DIRECT:
			pastPeriods2 = new CashFlowPeriodContainer();
		}
	}

	private void addFuturePeriods(int periodsToForecast,
			InputType deterministicInput2) {
		for (int i = 0; i < periodsToForecast; i++) {
			sumFuturePeriods++;
			PeriodInterface period = buildNewPeriod(deterministicInput2, baseYear
					+ sumFuturePeriods);
			futurePeriods.addPeriod(period);
			getView().addFuturePeriod(period);
		}
	}

	private void removeAllFuturePeriods() {
		for (int i = 0; i < sumFuturePeriods; i++) {
			getView().removeFuturePeriod();
		}
		sumFuturePeriods = 0;

	}

	private void addPastPeriods(int relevantPastPeriods,
			InputType stochasticInput2) {
		// TODO Auto-generated method stub
		for (int i = 0; i < relevantPastPeriods; i++) {
			sumPastPeriods++;
			PeriodInterface period = buildNewPeriod(stochasticInput2, baseYear
					- sumPastPeriods);
			pastPeriods.addPeriod(period);
			getView().addPastPeriod(period);
		}

	}

	private PeriodInterface buildNewPeriod(InputType stochasticInput2, int year) {
		PeriodInterface p;
		switch (stochasticInput2) {
		case REVENUE:
			p = new CostOfSalesMethodPeriod(year);
			return p;

		case TOTAL:
			p = new AggregateCostMethodPeriod(year);
			return p;

		case DIRECT:
			p = new CashFlowPeriod(year);
			return p;

		default:
			return null;
		}

	}

	private void removeAllPastPeriods() {
		// TODO Auto-generated method stub
		for (int i = 0; i < sumPastPeriods; i++) {
			getView().removePastPeriod();
		}
		sumPastPeriods = 0;

	}

	private void removeEverything() {
		baseYear = projectProxy.getSelectedProject().getBasisYear();
		for (int i = 0; i < sumPastPeriods; i++) {
			getView().removePastPeriod();
		}
		sumPastPeriods = 0;
		for (int i = 0; i < sumFuturePeriods; i++) {
			getView().removeFuturePeriod();
		}
		sumFuturePeriods = 0;

		getView().addBasePeriod(new CashFlowPeriod(baseYear));

	}

	@Override
	@EventHandler
	public void validate(ValidateContentStateEvent event) {
		if (isValid()) {
			eventBus.fireEvent(new ValidStateEvent(NavigationSteps.PERIOD));
		} else {
			eventBus.fireEvent(new InvalidStateEvent(NavigationSteps.PERIOD,
					showErrors));
		}
	}

	@Override
	public void handleShowErrors(ShowErrorsOnScreenEvent event) {
		// TODO Auto-generated method stub

	}

	public void addPastPeriod() {
		addPastPeriods(1, projectProxy.getSelectedProject()
				.getProjectInputType().getStochasticInput());

	}

	public void addFuturePeriod() {
		addFuturePeriods(1, projectProxy.getSelectedProject()
				.getProjectInputType().getDeterministicInput());

	}

	public void periodClicked(PeriodInterface period) {
		if(period instanceof CashFlowPeriod){
			
		}
		if(period instanceof AggregateCostMethodPeriod){
			
		}
		if(period instanceof CostOfSalesMethodPeriod){
			
		}
	}

	public void removeLastFuturePeriod(PeriodInterface period) {
		getView().removeFuturePeriod();
		futurePeriods.removePeriod(period);
	}

	public void removeLastPastPeriod(PeriodInterface periodInterface) {
		getView().removePastPeriod();
		pastPeriods.removePeriod(periodInterface);

	}

}
