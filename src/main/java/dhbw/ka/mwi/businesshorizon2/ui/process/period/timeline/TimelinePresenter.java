package dhbw.ka.mwi.businesshorizon2.ui.process.period.timeline;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

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
import dhbw.ka.mwi.businesshorizon2.ui.process.period.input.ShowDirektViewEvent;
import dhbw.ka.mwi.businesshorizon2.ui.process.period.input.ShowGesamtViewEvent;
import dhbw.ka.mwi.businesshorizon2.ui.process.period.input.ShowUmsatzViewEvent;

/**
 * Presenter für die Anzeige des PeriodenZeitstrahls in der Periodenmaske
 * 
 * @author Daniel Dengler
 * 
 */

public class TimelinePresenter extends ScreenPresenter<TimelineViewInterface> {
	private static final long serialVersionUID = 1L;

	@Autowired
	ProjectProxy projectProxy;

	@Autowired
	EventBus eventBus;

	Logger logger = Logger.getLogger(TimelinePresenter.class);

	private int fixedPastPeriods;
	private int fixedFuturePeriods;

	private int sumPastPeriods;
	private int sumFuturePeriods;

	private int baseYear = -9999999;

	private boolean showErrors;

	private AbstractPeriodContainer pastPeriods = new CashFlowPeriodContainer();
	private AbstractPeriodContainer futurePeriods = new CashFlowPeriodContainer();

	private Boolean deterministic = false;

	private Boolean stochastic = false;

	private InputType deterministicInput;

	private InputType stochasticInput;

	/**
	 * Dies ist der Konstruktor, der von Spring nach der Initialierung der
	 * Dependencies aufgerufen wird. Er registriert lediglich sich selbst als
	 * einen EventHandler.
	 * 
	 * @author Daniel Dengler
	 */
	@PostConstruct
	public void init() {
		eventBus.addHandler(this);
	}

	/**
	 * Überprüft ob sich die für die Maske relevanten Daten im ProjektObjekt
	 * geändert haben.
	 * 
	 * @return true falls alles gleich ist, false wenn sich etwas geändert hat
	 * @author Daniel Dengler
	 */

	@Override
	public boolean isValid() {
		try {
			projectProxy.getSelectedProject();
		} catch (Exception e) {
			logger.debug("crash at getSelectedProject()");
		}
		try {
			projectProxy.getSelectedProject().getProjectInputType();
		} catch (Exception e) {
			logger.debug("crash at getProjectInputType");
		}
		try {
			projectProxy.getSelectedProject().getProjectInputType()
					.getDeterministic();
		} catch (Exception e) {
			logger.debug("crash at getDeterministic()"
					+ projectProxy.getSelectedProject().getProjectInputType());
		}
		try {
			if (projectProxy.getSelectedProject().getBasisYear() == baseYear
					&& projectProxy.getSelectedProject().getPeriodsToForecast() == fixedFuturePeriods
					&& projectProxy.getSelectedProject()
							.getRelevantPastPeriods() == fixedPastPeriods
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
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Diese Methode reagiert auf den Aufruf der View die auch diesen Zeitstrahl
	 * enthält. Sollte sich etwas in den Daten des ProjektObjekts geändert haben
	 * oder wurde noch nicht initialisiert, dann kümmert sich die Methode um die
	 * Befüllung internen Variablen
	 * 
	 * @param event
	 *            ShowPeriodViewEvent, wird derzeit von der Navigationsleiste
	 *            geworfen.
	 * @throws NullPointerException
	 */

	@EventHandler
	public void onShowPeriodEvent(ShowPeriodViewEvent event)
			throws NullPointerException {
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
						stochasticInput = projectProxy.getSelectedProject()
								.getProjectInputType().getStochasticInput();
						createContainer(pastPeriods, stochasticInput);
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
						deterministicInput = projectProxy.getSelectedProject()
								.getProjectInputType().getDeterministicInput();
						createContainer(futurePeriods, deterministicInput);
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
					deterministicInput = projectProxy.getSelectedProject()
							.getProjectInputType().getDeterministicInput();
					createContainer(futurePeriods, deterministicInput);
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
					stochasticInput = projectProxy.getSelectedProject()
							.getProjectInputType().getStochasticInput();
					createContainer(pastPeriods, stochasticInput);

				}

			}

		}
		getView().setPastButtonAccess(stochastic);
		getView().setFutureButtonAccess(deterministic);

	}

	/**
	 * Erstellt einen neuen konkreten Container
	 * 
	 * @param container
	 *            Referenz auf einen der AbstactPeriodContainern dieser Klasse
	 * @param inputType
	 *            Definiert die Art des Containers anhand des gewählten
	 *            InputTypes
	 */
	private void createContainer(AbstractPeriodContainer container,
			InputType inputType) {
		switch (inputType) {
		case TOTAL:
			container = new AggregateCostMethodBalanceSheetPeriodContainer();
			break;
		case REVENUE:
			container = new CostOfSalesMethodPeriodContainer();
			break;
		case DIRECT:
			container = new CashFlowPeriodContainer();
			break;
		}
	}

	/**
	 * Fügt eine beliebige Anzahl von zukünftigen Perioden zum Periodencontainer
	 * und der View
	 * 
	 * @param howMany
	 *            Anzahl der hinzuzufügenden Perioden
	 * @param inputType
	 *            Art der hinzuzufügenden Perioden
	 */

	private void addFuturePeriods(int howMany, InputType inputType) {
		for (int i = 0; i < howMany; i++) {
			sumFuturePeriods++;
			PeriodInterface period = buildNewPeriod(inputType, baseYear
					+ sumFuturePeriods);
			futurePeriods.addPeriod(period);
			getView().addFuturePeriod(period);
			projectProxy.getSelectedProject().setDeterministicPeriods(
					futurePeriods);
		}
	}

	private void removeAllFuturePeriods() {
		for (int i = 0; i < sumFuturePeriods; i++) {
			getView().removeFuturePeriod();
		}
		sumFuturePeriods = 0;

		deterministic = projectProxy.getSelectedProject().getProjectInputType()
				.getDeterministic();

	}

	/**
	 * Fügt eine beliebige Anzahl von vergangenen Perioden zum Periodencontainer
	 * und der View hinzu
	 * 
	 * @param howMany
	 *            Anzahl der hinzuzufügenden Perioden
	 * @param inputType
	 *            Art der hinzuzufügenden Perioden
	 */

	private void addPastPeriods(int howMany, InputType inputType) {
		// TODO Auto-generated method stub
		for (int i = 0; i < howMany; i++) {
			sumPastPeriods++;
			PeriodInterface period = buildNewPeriod(inputType, baseYear
					- sumPastPeriods);
			pastPeriods.addPeriod(period);
			getView().addPastPeriod(period);
		}
		projectProxy.getSelectedProject().setStochasticPeriods(pastPeriods);

	}

	/**
	 * Erstellt eine konkrete Periode
	 * 
	 * @param inputType
	 *            Art der Periode
	 * @param year
	 *            Jahr der Periode
	 * @return Die erstellte Periode als PeriodInterface
	 */

	private PeriodInterface buildNewPeriod(InputType inputType, int year) {
		PeriodInterface p;
		switch (inputType) {
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

	/**
	 * Entfernt alle vergangenen Perioden
	 */

	private void removeAllPastPeriods() {
		// TODO Auto-generated method stub
		for (int i = 0; i < sumPastPeriods; i++) {
			getView().removePastPeriod();
		}
		sumPastPeriods = 0;
		stochastic = projectProxy.getSelectedProject().getProjectInputType()
				.getStochastic();

	}

	/**
	 * Entfernt alle Perioden aus View und Containern und kümmert sich darum
	 * eine richtige Basisperiode zur Verfügung zu stellen
	 */
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
		stochasticInput = projectProxy.getSelectedProject()
				.getProjectInputType().getStochasticInput();
		deterministicInput = projectProxy.getSelectedProject()
				.getProjectInputType().getDeterministicInput();
		logger.debug("Container created!");
		createContainer(pastPeriods, projectProxy.getSelectedProject()
				.getProjectInputType().getStochasticInput());
		createContainer(futurePeriods, projectProxy.getSelectedProject()
				.getProjectInputType().getDeterministicInput());
		deterministic = projectProxy.getSelectedProject().getProjectInputType()
				.getDeterministic();
		stochastic = projectProxy.getSelectedProject().getProjectInputType()
				.getStochastic();
		if (stochastic) {
			switch (projectProxy.getSelectedProject().getProjectInputType()
					.getStochasticInput()) {
			case REVENUE:
				getView().addBasePeriod(new CostOfSalesMethodPeriod(baseYear));
				break;
			case TOTAL:
				getView()
						.addBasePeriod(new AggregateCostMethodPeriod(baseYear));
				break;
			case DIRECT:
				getView().addBasePeriod(new CashFlowPeriod(baseYear));
				break;
			}
		} else {
			switch (projectProxy.getSelectedProject().getProjectInputType()
					.getDeterministicInput()) {
			case REVENUE:
				getView().addBasePeriod(new CostOfSalesMethodPeriod(baseYear));
				break;
			case TOTAL:
				getView()
						.addBasePeriod(new AggregateCostMethodPeriod(baseYear));
				break;
			case DIRECT:
				getView().addBasePeriod(new CashFlowPeriod(baseYear));
				break;
			}

		}

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
		// Wird nicht gebraucht... können keine Fehler in der View selber
		// durch Benutzereingaben entstehen

	}

	/**
	 * Methode zum Aufruf aus der View. Ruft die Folgemethode auf.
	 */
	public void addPastPeriod() {
		addPastPeriods(1, projectProxy.getSelectedProject()
				.getProjectInputType().getStochasticInput());

	}

	/**
	 * Methode zum Aufruf aus der View. Ruft die Folgemethode auf.
	 */
	public void addFuturePeriod() {
		addFuturePeriods(1, projectProxy.getSelectedProject()
				.getProjectInputType().getDeterministicInput());

	}

	/**
	 * Wird von der View bei einer Benutzereingabe aufgerufen und feuert,
	 * entsprechend der ausgewählten Periode, das richtige ViewEvent für die
	 * EingabeViews
	 * 
	 * @param period
	 *            Die Periode die zum gedrückten PeriodenKnopf gehört
	 */

	public void periodClicked(PeriodInterface period) {
		if (period instanceof CashFlowPeriod) {
			eventBus.fireEvent(new ShowDirektViewEvent((CashFlowPeriod) period));
		}
		if (period instanceof AggregateCostMethodPeriod) {
			eventBus.fireEvent(new ShowGesamtViewEvent(
					(AggregateCostMethodPeriod) period));
		}
		if (period instanceof CostOfSalesMethodPeriod) {
			eventBus.fireEvent(new ShowUmsatzViewEvent(
					(CostOfSalesMethodPeriod) period));
		}
	}

	/**
	 * Methode wird aus der View aufgerufen um die letzte zukünftige Periode zu
	 * entfernen
	 * 
	 * @param period
	 *            Periode die entfernt werden soll
	 */
	public void removeLastFuturePeriod(PeriodInterface period) {
		getView().removeFuturePeriod();
		futurePeriods.removePeriod(period);
		sumFuturePeriods--;
	}

	/**
	 * Methode wird aus der View aufgerufen um die letzte vergangene Periode zu
	 * entfernen
	 * 
	 * @param period
	 *            Periode die entfernt werden soll
	 */

	public void removeLastPastPeriod(PeriodInterface periodInterface) {
		getView().removePastPeriod();
		pastPeriods.removePeriod(periodInterface);
		sumPastPeriods--;
	}

}
