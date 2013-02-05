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
import dhbw.ka.mwi.businesshorizon2.models.Period.Period;
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
import dhbw.ka.mwi.businesshorizon2.ui.process.period.input.WrongFieldsEvent;

/**
 * Presenter f/u00fcr die Anzeige des PeriodenZeitstrahls in der Periodenmaske
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
	// private int fixedFuturePeriods; gibbets nibbets

	private int sumPastPeriods;
	private int sumFuturePeriods;

	private int baseYear = -9999999;

	private boolean showErrors;

	private final AbstractPeriodContainer pastPeriods = new CashFlowPeriodContainer();
	private final AbstractPeriodContainer futurePeriods = new CashFlowPeriodContainer();

	private Boolean deterministic = false;

	private Boolean stochastic = false;

	private InputType deterministicInput;

	private InputType stochasticInput;

	private Period basePeriod;

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
	 * /u00fcberpr/u00fcft ob sich die f/u00fcr die Maske relevanten Daten im ProjektObjekt
	 * ge/u00e4ndert haben.
	 * 
	 * @return true falls alles gleich ist, false wenn sich etwas ge/u00e4ndert hat
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
	 * enth/u00e4lt. Sollte sich etwas in den Daten des ProjektObjekts ge/u00e4ndert haben
	 * oder wurde noch nicht initialisiert, dann k/u00fcmmert sich die Methode um die
	 * Bef/u00fcllung internen Variablen
	 * 
	 * @param event
	 *            ShowPeriodViewEvent, wird derzeit von der Navigationsleiste
	 *            geworfen.
	 * @throws NullPointerException
	 */

	@EventHandler
	public void onShowPeriodEvent(ShowPeriodViewEvent event)
			throws NullPointerException {
		// Wenn alles gleich geblieben ist m/u00fcssen wir nichts tun
		if (isValid()) {
			return;
		} else {

			// Wenn sich unser Bezugsjahr /u00e4ndert werden alle Perioden ung/u00fcltig
			if (projectProxy.getSelectedProject().getBasisYear() != baseYear) {
				removeEverything();
			}

			// Wenn sich die relevanten Perioden /u00e4ndern muss man nur auff/u00fcllen
			// sollten es mehr sein als vorhanden
			if (projectProxy.getSelectedProject().getRelevantPastPeriods() != fixedPastPeriods) {
				if (projectProxy.getSelectedProject().getRelevantPastPeriods() > sumPastPeriods) {
					// Hat sich zus/u00e4tzlich die Eingabe ge/u00e4ndert m/u00fcssen alle
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


			// Hat sich nur der Inputtyp ge/u00e4ndert, m/u00fcssen wir alle betroffenen
			// Perioden verworfen werden und neu angelegt werden.
			if (projectProxy.getSelectedProject().getProjectInputType()
					.getDeterministic() != deterministic) {

				removeAllFuturePeriods();
				if ((projectProxy.getSelectedProject().getProjectInputType()
						.getDeterministic())) {
					deterministicInput = projectProxy.getSelectedProject()
							.getProjectInputType().getDeterministicInput();
					createContainer(futurePeriods, deterministicInput);
				}

			}

			// Siehe eins dr/u00fcber
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
	 *            Definiert die Art des Containers anhand des gew/u00e4hlten
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
	 * F/u00fcgt eine beliebige Anzahl von zuk/u00fcnftigen Perioden zum Periodencontainer
	 * und der View
	 * 
	 * @param howMany
	 *            Anzahl der hinzuzuf/u00fcgenden Perioden
	 * @param inputType
	 *            Art der hinzuzuf/u00fcgenden Perioden
	 */

	private void addFuturePeriods(int howMany, InputType inputType) {
		for (int i = 0; i < howMany; i++) {
			sumFuturePeriods++;
			Period period = buildNewPeriod(inputType, baseYear
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
	 * F/u00fcgt eine beliebige Anzahl von vergangenen Perioden zum Periodencontainer
	 * und der View hinzu
	 * 
	 * @param howMany
	 *            Anzahl der hinzuzuf/u00fcgenden Perioden
	 * @param inputType
	 *            Art der hinzuzuf/u00fcgenden Perioden
	 */

	private void addPastPeriods(int howMany, InputType inputType) {
		// TODO Auto-generated method stub
		for (int i = 0; i < howMany; i++) {
			sumPastPeriods++;
			Period period = buildNewPeriod(inputType, baseYear
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
	private Period buildNewPeriod(InputType inputType, int year) {
		Period p;

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
	 * Entfernt alle Perioden aus View und Containern und k/u00fcmmert sich darum
	 * eine richtige Basisperiode zur Verf/u00fcgung zu stellen
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
				basePeriod = new CostOfSalesMethodPeriod(baseYear);
				getView().addBasePeriod(basePeriod);
				pastPeriods.addPeriod(basePeriod);
				break;
			case TOTAL:
				basePeriod = new AggregateCostMethodPeriod(baseYear);
				getView().addBasePeriod(basePeriod);
				pastPeriods.addPeriod(basePeriod);

				break;
			case DIRECT:
				basePeriod = new CashFlowPeriod(baseYear);
				getView().addBasePeriod(basePeriod);
				pastPeriods.addPeriod(basePeriod);
				break;
			}
		} else {
			switch (projectProxy.getSelectedProject().getProjectInputType()
					.getDeterministicInput()) {
			case REVENUE:
				basePeriod = new CostOfSalesMethodPeriod(baseYear);
				getView().addBasePeriod(basePeriod);
				futurePeriods.addPeriod(basePeriod);
				break;
			case TOTAL:
				basePeriod = new AggregateCostMethodPeriod(baseYear);
				getView().addBasePeriod(basePeriod);
				futurePeriods.addPeriod(basePeriod);

				break;
			case DIRECT:
				basePeriod = new CashFlowPeriod(baseYear);
				getView().addBasePeriod(basePeriod);
				futurePeriods.addPeriod(basePeriod);
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
		// Wird nicht gebraucht... k/u00f6nnen keine Fehler in der View selber
		// durch Benutzereingaben entstehen

	}

	/**
	 * Methode zum Aufruf aus der View. Ruft die Folgemethode auf.
	 */
	public void addPastPeriod() {
		addPastPeriods(1, projectProxy.getSelectedProject()
				.getProjectInputType().getStochasticInput());
		eventBus.fireEvent(new ShowPeriodViewEvent());

	}

	/**
	 * Methode zum Aufruf aus der View. Ruft die Folgemethode auf.
	 */
	public void addFuturePeriod() {
		addFuturePeriods(1, projectProxy.getSelectedProject()
				.getProjectInputType().getDeterministicInput());
		eventBus.fireEvent(new ShowPeriodViewEvent());

	}
	/**
	 * Wird von der View bei einer Benutzereingabe aufgerufen und feuert,
	 * entsprechend der ausgew/u00e4hlten Periode, das richtige ViewEvent f/u00fcr die
	 * EingabeViews
	 * 
	 * @param period
	 *            Die Periode die zum gedr/u00fcckten PeriodenKnopf geh/u00f6rt
	 */
	public void periodClicked(Period period) {



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
 * Methode wird aus der View aufgerufen um die letzte zuk/u00fcnftige Periode zu
 * entfernen
 * 
 * @param period
 *            Periode die entfernt werden soll
 */
	public void removeLastFuturePeriod(Period period) {
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
	public void removeLastPastPeriod(Period periodInterface) {

		getView().removePastPeriod();
		pastPeriods.removePeriod(periodInterface);
		sumPastPeriods--;
	}
	
	@EventHandler
	public void onWrongFieldEvent(WrongFieldsEvent e){
		setButtonWrong(Integer.parseInt(e.getWrongFields().get(0)),true);
	}
	
	public void setButtonWrong(int year, boolean isWrong){
		getView().setButtonWrong( year,isWrong);
	}

}
