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
 * Presenter fuer die Anzeige des PeriodenZeitstrahls in der Periodenmaske
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

	private static final Logger logger = Logger
			.getLogger(TimelinePresenter.class);

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
	 * Ueberprueft ob sich die fuer die Maske relevanten Daten im ProjektObjekt
	 * geaendert haben.
	 * 
	 * @return true falls alles gleich ist, false wenn sich etwas geaendert hat
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
	 * enthaelt. Sollte sich etwas in den Daten des ProjektObjekts geaendert
	 * haben oder wurde noch nicht initialisiert, dann kuemmert sich die Methode
	 * um die Befuellung internen Variablen
	 * 
	 * @param event
	 *            ShowPeriodViewEvent, wird derzeit von der Navigationsleiste
	 *            geworfen.
	 * @throws NullPointerException
	 */

	@EventHandler
	public void onShowPeriodEvent(ShowPeriodViewEvent event)
			throws NullPointerException {
		// Wenn alles gleich geblieben ist muessen wir nichts tun
		if (isValid() && false) {
			logger.debug("alles valid");
			return;
		} else {

			// Wenn sich unser Bezugsjahr aendert werden alle Perioden ungueltig
			if (projectProxy.getSelectedProject().getBasisYear() != baseYear) {
				removeEverything();
			}

			if (projectProxy.getSelectedProject().getRelevantPastPeriods() != fixedPastPeriods && false) {
				if (projectProxy.getSelectedProject().getRelevantPastPeriods() > sumPastPeriods) {
					// Hat sich zusaetzlich die Eingabe geaendert muessen alle
					// verworfen werden
					if (stochasticInput != projectProxy.getSelectedProject()
							.getProjectInputType().getStochasticInput()) {
						removeAllPastPeriods();
						/*
						 * Annika Weis Umsetzung aus dem Pflichtenheft:
						 * 
						 * Bei stochastischen Verfahren: Standardanzahl
						 * vergangener Perioden = Wert des Textfelds „Anzahl
						 * einbezogener, vergangener Perioden“ (Reiter
						 * „Parameter“) + 1 (wichtig zur Berechnung der
						 * Autokorrelation). Dies entspricht dann auch der
						 * Mindestanzahl vergangener Perioden (Perioden können
						 * nur bis zu dieser Anzahl gelöscht werden).
						 */
						if (projectProxy.getSelectedProject()
								.getProjectInputType().getStochastic()) {
							addPastPeriods(projectProxy.getSelectedProject()
									.getRelevantPastPeriods() + 1, projectProxy
									.getSelectedProject().getProjectInputType()
									.getStochasticInput());
							logger.debug("Perioden plus 1");
						} else {
							addPastPeriods(projectProxy.getSelectedProject()
									.getRelevantPastPeriods(), projectProxy
									.getSelectedProject().getProjectInputType()
									.getStochasticInput());
							logger.debug("Perioden plus 0");
						}
						// Ende AW

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

			// Deterministische Verfahren
			// Hat sich nur der Inputtyp geaendert, muessen alle betroffenen
			// Perioden verworfen werden und neu angelegt werden.
			if (projectProxy.getSelectedProject().getProjectInputType()
					.getDeterministic()) {// projectProxy.getSelectedProject().getProjectInputType().getDeterministic()
				// != deterministic &&
				logger.debug("Initialisierung (d)");
				removeAllFuturePeriods();
				removeAllPastPeriods();
				deterministicInput = projectProxy.getSelectedProject()
						.getProjectInputType().getDeterministicInput();
				createContainer(futurePeriods, deterministicInput);

				/*
				 * Annika Weis Nur deterministisches Verfahren: keine
				 * vergangenen Perioden
				 */
				getView().setPastButtonAccess(false);
				getView().setPastDeleteButtonAccess(true);
				getView().setFutureDeleteButtonAccess(true);

				/**
				 * Annika Weis
				 */
				addFuturePeriods_vorhanden(4, deterministicInput);
			}

			// Stochastische Verfahren
			// Hat sich nur der Inputtyp geaendert, muessen alle betroffenen
			// Perioden verworfen werden und neu angelegt werden.

			if (projectProxy.getSelectedProject().getProjectInputType()
					.getStochastic()) { // projectProxy.getSelectedProject().getProjectInputType().getStochastic()
				// != stochastic &&
				logger.debug("Initialisierung (s) " + fixedPastPeriods);
				removeAllFuturePeriods();
				removeAllPastPeriods();
				// fixedPastPeriods =
				// projectProxy.getSelectedProject().getRelevantPastPeriods();

				stochasticInput = projectProxy.getSelectedProject()
						.getProjectInputType().getStochasticInput();
				createContainer(pastPeriods, stochasticInput);

				addPastPeriods_vorhanden(4, stochasticInput);

				/*
				 * Annika Weis Nur stochastische Verfahren -keine zukünftigen
				 * Perioden -Zukünftige Perioden können weder hinzugefügt noch
				 * gelöscht werden.
				 */

				getView().setPastButtonAccess(true);
				getView().setPastDeleteButtonAccess(false);
				getView().setFutureButtonAccess(false);
				getView().setFutureDeleteButtonAccess(false);
			}

			// Annika Weis
			// Deterministische UND stochastische Verfahren
			// Hat sich nur der Inputtyp geaendert, muessen alle betroffenen
			// Perioden verworfen werden und neu angelegt werden.
			/*
			 * Annika Weis Deterministische UND stochastische Verfahren
			 * -kompletter Zeitstrahl vorhanden -Zukünftige Perioden können
			 * weder hinzugefügt noch gelöscht werden.
			 */
			/*
			 * if (projectProxy.getSelectedProject().getProjectInputType()
			 * .getStochastic() &&
			 * projectProxy.getSelectedProject().getProjectInputType()
			 * .getDeterministic())
			 * {//(projectProxy.getSelectedProject().getProjectInputType
			 * ().getStochastic() != stochastic ||
			 * projectProxy.getSelectedProject
			 * ().getProjectInputType().getDeterministic() != deterministic) &&
			 * 
			 * logger.debug("Initialisierung (d,s)"); removeAllFuturePeriods();
			 * removeAllPastPeriods(); getView().setPastButtonAccess(true);
			 * getView().setPastDeleteButtonAccess(true);
			 * getView().setFutureButtonAccess(false);
			 * getView().setFutureDeleteButtonAccess(false);
			 * 
			 * //Vergangene Perioden hinzufügen
			 * addPastPeriods(fixedPastPeriods+1, projectProxy
			 * .getSelectedProject().getProjectInputType()
			 * .getStochasticInput()); stochasticInput =
			 * projectProxy.getSelectedProject()
			 * .getProjectInputType().getStochasticInput();
			 * createContainer(pastPeriods, stochasticInput); addPastPeriods(5,
			 * stochasticInput); //Annika Weis
			 * 
			 * //Zukünftige Perioden hinzufügen addFuturePeriods(5, projectProxy
			 * .getSelectedProject().getProjectInputType()
			 * .getDeterministicInput()); deterministicInput =
			 * projectProxy.getSelectedProject()
			 * .getProjectInputType().getDeterministicInput();
			 * createContainer(futurePeriods, deterministicInput);
			 * 
			 * 
			 * addFuturePeriods(5, deterministicInput); //Annika Weis }
			 */

		}
		// getView().setPastButtonAccess(stochastic);
		// getView().setFutureButtonAccess(deterministic);

	}

	/**
	 * Erstellt einen neuen konkreten Container
	 * 
	 * @param container
	 *            Referenz auf einen der AbstactPeriodContainern dieser Klasse
	 * @param inputType
	 *            Definiert die Art des Containers anhand des gewaehlten
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
	 * Fuegt eine beliebige Anzahl von zukuenftigen Perioden zum
	 * Periodencontainer und der View
	 * 
	 * @param howMany
	 *            Anzahl der hinzuzufuegenden Perioden
	 * @param inputType
	 *            Art der hinzuzufuegenden Perioden
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

	/**
	 * Zukünftige deterministische Perioden anlegen
	 * 
	 * @author Annika Weis
	 * 
	 * @param howMany
	 * @param inputType
	 *            TODO
	 */
	private void addFuturePeriods_vorhanden(int howMany, InputType inputType) {
		/*
		 * Wenn bereits Perioden vorhanden sind: so viele anlegen, sonst so
		 * viele, wie es der Benutzer vorgibt auf der Parameter-Maske
		 */
		logger.debug("future periods");

		int i = 0;
		sumFuturePeriods = 0;
		try {
			for (Period periode : projectProxy.getSelectedProject()
					.getDeterministicPeriods().getPeriods()) {
				logger.debug(++i + " + " + periode.getYear());
				getView().addFuturePeriod(periode);
				sumFuturePeriods++;
			}

		} catch (Exception c) {
		}
		if (i == 0) {
			logger.debug("Manuell Perioden anlegen");
			addFuturePeriods(projectProxy.getSelectedProject()
					.getPeriodsToForecast_deterministic(), deterministicInput);
		}
		logger.debug("Periodenanzahl: " + sumFuturePeriods);
		return;
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
	 * Fuegt eine beliebige Anzahl von vergangenen Perioden zum
	 * Periodencontainer und der View hinzu
	 * 
	 * @param howMany
	 *            Anzahl der hinzuzufuegenden Perioden
	 * @param inputType
	 *            Art der hinzuzufuegenden Perioden
	 */

	private void addPastPeriods(int howMany, InputType inputType) {
		// TODO Auto-generated method stub
		for (int i = 0; i < howMany; i++) {
			sumPastPeriods++;
			Period period = buildNewPeriod(inputType, baseYear - sumPastPeriods);
			pastPeriods.addPeriod(period);
			getView().addPastPeriod(period);
		}
		projectProxy.getSelectedProject().setStochasticPeriods(pastPeriods);
	}

	/**
	 * Zukünftige stochastische Perioden anlegen
	 * 
	 * @author Annika Weis
	 * 
	 * @param howMany
	 * @param inputType
	 *            TODO
	 */
	private void addPastPeriods_vorhanden(int howMany, InputType inputType) {
		/*
		 * Wenn bereits Perioden vorhanden sind: so viele anlegen, sonst so
		 * viele, wie es der Benutzer vorgibt auf der Parameter-Maske
		 */
		logger.debug("past periods");
		int i = 0;
		sumPastPeriods = 0;
		try {
			for (Period periode : projectProxy.getSelectedProject()
					.getStochasticPeriods().getPeriods()) {
				logger.debug(++i + " - " + periode.getYear());
				getView().addPastPeriod(periode);
				sumPastPeriods++;
			}
		} catch (Exception c) {
		}
		if (i == 0) {
			logger.debug("Manuell Perioden anlegen");
			addPastPeriods(projectProxy.getSelectedProject()
					.getRelevantPastPeriods(), stochasticInput);
		}
		logger.debug("Periodenanzahl: " + sumPastPeriods);
		return;
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
	 * Entfernt alle Perioden aus View und Containern und kuemmert sich darum
	 * eine richtige Basisperiode zur Verfuegung zu stellen
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
		// Wird nicht gebraucht... koennen keine Fehler in der View selber
		// durch Benutzereingaben entstehen

	}

	/**
	 * Methode zum Aufruf aus der View. Ruft die Folgemethode auf.
	 */
	public void addPastPeriod() {
		addPastPeriods(1, projectProxy.getSelectedProject()
				.getProjectInputType().getStochasticInput());
		eventBus.fireEvent(new ShowPeriodViewEvent());
		/*
		 * Annika Weis Hat sich der Benutzer im ersten Prozessschritt nur für
		 * das deterministische Verfahren entschieden, können keine vergangenen
		 * Perioden angelegt werden
		 */
		Boolean deterministic;
		deterministic = (projectProxy.getSelectedProject()
				.getProjectInputType().getDeterministic() && projectProxy
				.getSelectedProject().getProjectInputType().getStochastic() == false);
		logger.debug("Neue Vergangenheit (d)?" + deterministic);
		if (deterministic) {
			getView().setPastButtonAccess(false);
			getView().setPastDeleteButtonAccess(false);
		} else {
			getView().setPastDeleteButtonAccess(true);
		}

	}

	/**
	 * Methode zum Aufruf aus der View. Ruft die Folgemethode auf.
	 */
	public void addFuturePeriod() {
		addFuturePeriods(1, projectProxy.getSelectedProject()
				.getProjectInputType().getDeterministicInput());
		eventBus.fireEvent(new ShowPeriodViewEvent());
		/*
		 * Annika Weis Bei deterministischem UND stochastischem Verfahren: Die
		 * Anzahl der zukünftigen Perioden entspricht dem Wert des Textfelds
		 * „Anzahl zu prognostizierender Perioden“ (Reiter „Parameter“).
		 * Zukünftige Perioden können weder hinzugefügt noch gelöscht werden.
		 */
		Boolean stochastic_deterministic;
		stochastic_deterministic = (projectProxy.getSelectedProject()
				.getProjectInputType().getDeterministic() && projectProxy
				.getSelectedProject().getProjectInputType().getStochastic());
		logger.debug("Neue Zukunft (d,s)?" + stochastic_deterministic);
		if (stochastic_deterministic) {
			getView().setFutureButtonAccess(false);
			getView().setFutureDeleteButtonAccess(false);
			return;
		} else {
			getView().setFutureButtonAccess(true);
			getView().setFutureDeleteButtonAccess(true);
		}

	}

	/**
	 * Wird von der View bei einer Benutzereingabe aufgerufen und feuert,
	 * entsprechend der ausgewaehlten Periode, das richtige ViewEvent fuer die
	 * EingabeViews
	 * 
	 * @param period
	 *            Die Periode die zum gedrueckten PeriodenKnopf gehoert
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
	 * Methode wird aus der View aufgerufen um die letzte zukuenftige Periode zu
	 * entfernen
	 * 
	 * @param period
	 *            Periode die entfernt werden soll
	 */
	public void removeLastFuturePeriod(Period period) {
		getView().removeFuturePeriod();
		futurePeriods.removePeriod(period);
		sumFuturePeriods--;
		/*
		 * Annika Weis Bei deterministischem Verfahren: Bis auf zwei Perioden
		 * können alle gelöscht werden
		 */
		Boolean deterministic_2;
		deterministic_2 = (projectProxy.getSelectedProject()
				.getProjectInputType().getDeterministic()
				&& projectProxy.getSelectedProject().getProjectInputType()
						.getStochastic() == false && sumFuturePeriods <= 2);
		logger.debug("Lösche Zukunft(d) ?" + sumFuturePeriods + " / "
				+ deterministic_2);
		if (deterministic_2) {
			getView().setFutureDeleteButtonAccess(false);
			getView().setFutureButtonAccess(true);
			return;
		}

		/*
		 * Annika Weis Bei deterministischem UND stochastischem Verfahren: Die
		 * Anzahl der zukünftigen Perioden entspricht dem Wert des Textfelds
		 * „Anzahl zu prognostizierender Perioden“ (Reiter „Parameter“).
		 * Zukünftige Perioden können weder hinzugefügt noch gelöscht werden.
		 */
		Boolean stochastic_deterministic;
		stochastic_deterministic = (projectProxy.getSelectedProject()
				.getProjectInputType().getDeterministic() && projectProxy
				.getSelectedProject().getProjectInputType().getStochastic());

		logger.debug("Lösche Zukunft(s) ?" + stochastic_deterministic);
		if (stochastic_deterministic) {
			getView().setFutureButtonAccess(false);
			getView().setFutureDeleteButtonAccess(true);
		}

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
		logger.debug("Fixed Periods: " + fixedPastPeriods + " Sum Periods: "
				+ sumPastPeriods);

		getView().setPastButtonAccess(true);
		/*
		 * Annika Weis: Bei stochastischen Verfahren: (Gilt auch für
		 * stochastische UND deterministische Verfahren) Standardanzahl
		 * vergangener Perioden = Wert des Textfelds „Anzahl einbezogener,
		 * vergangener Perioden“ (Reiter „Parameter“) + 1 (wichtig zur
		 * Berechnung der Autokorrelation). Dies entspricht dann auch der
		 * Mindestanzahl vergangener Perioden (Perioden können nur bis zu dieser
		 * Anzahl gelöscht werden).
		 */

		Boolean stochastic;
		stochastic = (projectProxy.getSelectedProject().getProjectInputType()
				.getStochastic() && projectProxy.getSelectedProject()
				.getRelevantPastPeriods() + 1 < sumPastPeriods);
		logger.debug("Löschen Vergangenheit (s)? "
				+ projectProxy.getSelectedProject().getRelevantPastPeriods()
				+ " / " + sumPastPeriods + " / " + stochastic);
		if (stochastic) {
			getView().setPastDeleteButtonAccess(true);
		} else {
			getView().setPastDeleteButtonAccess(false);
			return;
		}

		/*
		 * Annika Weis Bei deterministischen Verfahren: Bis auf zwei Perioden
		 * können alle gelöscht werden (der Löschen-Button erscheint jeweils
		 * neben der am weitest in der Zukunft liegenden Periode).
		 */
		Boolean deterministisch_3;
		deterministisch_3 = (projectProxy.getSelectedProject()
				.getProjectInputType().getDeterministic()
				&& !projectProxy.getSelectedProject().getProjectInputType()
						.getStochastic() && projectProxy.getSelectedProject()
				.getRelevantPastPeriods() == 3);
		logger.debug("Löschen Vergangenheit (d)? "
				+ projectProxy.getSelectedProject().getRelevantPastPeriods()
				+ " / " + deterministisch_3);
		if (deterministisch_3) {
			getView().setPastDeleteButtonAccess(false);
		}

	}

	@EventHandler
	public void onWrongFieldEvent(WrongFieldsEvent e) {
		setButtonWrong(Integer.parseInt(e.getWrongFields().get(0)), true);
	}

	public void setButtonWrong(int year, boolean isWrong) {
		getView().setButtonWrong(year, isWrong);
	}

}
