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

import java.util.Iterator;
import java.util.TreeSet;

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

	private String projektname;

	private int fixedPastPeriods;
	private int fixedFuturePeriods;// gibbets nibbets

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
		// prüfen, ob es sich um das selbe Projekt handelt wie zuvor
		if (isSameProject()) {
			logger.debug("=====gleiches Projekt====");
			if (isValid() & isValid_Zeitraum()) {
				// alles wie zuvor, keine Änderung notwendig
				logger.debug("alles valid");
				return;
			} else {
				// Wenn sich so viel geändert hat, dass alles neu angelegt
				// werden muss
				if (!isValid()) {
					alles_neu();
				} else {
					// /Wenn sich NUR die Anzahl der Jahre geändert hat
					jahresanzahl_geaendert();
				}
			}
		} else {
			logger.debug("====anderes Projekt====");
			// wird das Projekt zum ersten Mal aufgerufen
			if (isFirst_call()) {
				logger.debug("Alles wird neu erstellt da erster Aufruf");
				alles_neu();
			} else {

				// /Wenn sich NUR die Anzahl der Jahre geändert hat
				logger.debug("Anzahl Jahre hat sich geändert");
				jahresanzahl_geaendert();

				logger.debug("===========ENDE=========");
				// Wenn sich unser Bezugsjahr aendert werden alle Perioden
				// ungueltig
				if (true) {
				} else if (projectProxy.getSelectedProject().getBasisYear() != baseYear) {
					logger.debug("Basisjahr geändert");
					/**
					 * TODO testen
					 */
					removeEverything();
					if (stochastic) {
						logger.debug("PastPeriods: "
								+ projectProxy.getSelectedProject()
										.getRelevantPastPeriods());
						addPastPeriods(projectProxy.getSelectedProject()
								.getRelevantPastPeriods(), projectProxy
								.getSelectedProject().getProjectInputType()
								.getStochasticInput());
					}
					if (deterministic) {
						logger.debug("FuturePeriode: "
								+ projectProxy.getSelectedProject()
										.getPeriodsToForecast());
						addFuturePeriods(projectProxy.getSelectedProject()
								.getRelevantPastPeriods(), projectProxy
								.getSelectedProject().getProjectInputType()
								.getDeterministicInput());
					}
				} else {
					baseYear = projectProxy.getSelectedProject().getBasisYear();

					if (projectProxy.getSelectedProject()
							.getRelevantPastPeriods() != fixedPastPeriods && false) {
						if (projectProxy.getSelectedProject()
								.getRelevantPastPeriods() > sumPastPeriods) {
							// Hat sich zusaetzlich die Eingabe geaendert
							// muessen
							// alle
							// verworfen werden
							if (stochasticInput != projectProxy
									.getSelectedProject().getProjectInputType()
									.getStochasticInput()) {
								removeAllPastPeriods();
								/*
								 * Annika Weis Umsetzung aus dem Pflichtenheft:
								 * 
								 * Bei stochastischen Verfahren: Standardanzahl
								 * vergangener Perioden = Wert des Textfelds
								 * „Anzahl einbezogener, vergangener Perioden“
								 * (Reiter „Parameter“) + 1 (wichtig zur
								 * Berechnung der Autokorrelation). Dies
								 * entspricht dann auch der Mindestanzahl
								 * vergangener Perioden (Perioden können nur bis
								 * zu dieser Anzahl gelöscht werden).
								 */
								if (projectProxy.getSelectedProject()
										.getProjectInputType().getStochastic()) {
									addPastPeriods(projectProxy
											.getSelectedProject()
											.getRelevantPastPeriods() + 1,
											projectProxy.getSelectedProject()
													.getProjectInputType()
													.getStochasticInput());
									logger.debug("Perioden plus 1");
								} else {
									addPastPeriods(projectProxy
											.getSelectedProject()
											.getRelevantPastPeriods(),
											projectProxy.getSelectedProject()
													.getProjectInputType()
													.getStochasticInput());
									logger.debug("Perioden plus 0");
								}
								// Ende AW

								stochasticInput = projectProxy
										.getSelectedProject()
										.getProjectInputType()
										.getStochasticInput();
								createContainer(pastPeriods, stochasticInput);
							} else {
								addPastPeriods(projectProxy
										.getSelectedProject()
										.getRelevantPastPeriods()
										- sumPastPeriods, stochasticInput);
							}
						}
						fixedPastPeriods = projectProxy.getSelectedProject()
								.getRelevantPastPeriods();
					}

					// Deterministische Verfahren
					// Hat sich nur der Inputtyp geaendert, muessen alle
					// betroffenen
					// Perioden verworfen werden und neu angelegt werden.

					// Annika Weis
					// Deterministische UND stochastische Verfahren
					// Hat sich nur der Inputtyp geaendert, muessen alle
					// betroffenen
					// Perioden verworfen werden und neu angelegt werden.
					/*
					 * Annika Weis Deterministische UND stochastische Verfahren
					 * -kompletter Zeitstrahl vorhanden -Zukünftige Perioden
					 * können weder hinzugefügt noch gelöscht werden.
					 */
					/*
					 * if (projectProxy.getSelectedProject().getProjectInputType
					 * () .getStochastic() && projectProxy.getSelectedProject
					 * ().getProjectInputType() .getDeterministic())
					 * {//(projectProxy
					 * .getSelectedProject().getProjectInputType
					 * ().getStochastic() != stochastic ||
					 * projectProxy.getSelectedProject
					 * ().getProjectInputType().getDeterministic() !=
					 * deterministic) &&
					 * 
					 * logger.debug("Initialisierung (d,s)");
					 * removeAllFuturePeriods(); removeAllPastPeriods();
					 * getView().setPastButtonAccess(true);
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
					 * createContainer(pastPeriods, stochasticInput);
					 * addPastPeriods(5, stochasticInput); //Annika Weis
					 * 
					 * //Zukünftige Perioden hinzufügen addFuturePeriods(5,
					 * projectProxy .getSelectedProject().getProjectInputType()
					 * .getDeterministicInput()); deterministicInput =
					 * projectProxy.getSelectedProject()
					 * .getProjectInputType().getDeterministicInput();
					 * createContainer(futurePeriods, deterministicInput);
					 * 
					 * 
					 * addFuturePeriods(5, deterministicInput); //Annika Weis }
					 */

				}
			}
		}
		// getView().setPastButtonAccess(stochastic);
		// getView().setFutureButtonAccess(deterministic);

	}

	/**
	 * @author Annika Weis Wenn sich NUR die Anzahl der Jahre geändert hat,
	 *         werden die entsprechenden perioden angezeigt, hinzugefügt,
	 *         gelöscht
	 */
	private void jahresanzahl_geaendert() {
		logger.debug("Jahresanzahl ändern");
		initalise();

		if (projectProxy.getSelectedProject().getProjectInputType()
				.getDeterministic()) {// projectProxy.getSelectedProject().getProjectInputType().getDeterministic()
			// != deterministic &&
			logger.debug("Initialisierung (d) " + fixedFuturePeriods);
			removeAllFuturePeriods();
			removeAllPastPeriods();
			deterministicInput = projectProxy.getSelectedProject()
					.getProjectInputType().getDeterministicInput();
			createContainer(futurePeriods, deterministicInput);
			/**
			 * Annika Weis
			 */
			addFuturePeriods_vorhanden();
			fixedPastPeriods = projectProxy.getSelectedProject()
					.getRelevantPastPeriods();

			/*
			 * Annika Weis Nur deterministisches Verfahren: keine vergangenen
			 * Perioden
			 */
			getView().setPastButtonAccess(false);
			getView().setPastDeleteButtonAccess(true);
			getView().setFutureDeleteButtonAccess(true);

		}

		// Stochastische Verfahren
		// Hat sich nur der Inputtyp geaendert, muessen alle
		// betroffenen
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

			addPastPeriods_vorhanden();
			fixedFuturePeriods = projectProxy.getSelectedProject()
					.getPeriodsToForecast_deterministic();

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
	}

	/**
	 * legt so viele neue Perioden an, wie der Benutzer vorgegeben hat <br>
	 * handelt es sich um den ersten Aufruf ODER es gab eine der folgenden
	 * Änderungen <br>
	 * - Typ stochastisch/deterministisch geändert, <br>
	 * - Verfahren geändert, <br>
	 * - Basisjahr geändert <br>
	 * muss alles neu erstellt werden
	 * 
	 * @author Annika Weis
	 * 
	 */
	private void alles_neu() {
		logger.debug("Alles neu " + baseYear);
		initalise();

		removeEverything();
		if (stochastic) {
			logger.debug("PastPeriods: "
					+ projectProxy.getSelectedProject()
							.getRelevantPastPeriods());
			addPastPeriods(projectProxy.getSelectedProject()
					.getRelevantPastPeriods(), projectProxy
					.getSelectedProject().getProjectInputType()
					.getStochasticInput());
		}
		if (deterministic) {
			logger.debug("FuturePeriode: "
					+ projectProxy.getSelectedProject().getPeriodsToForecast());
			addFuturePeriods(projectProxy.getSelectedProject()
					.getPeriodsToForecast_deterministic(), projectProxy
					.getSelectedProject().getProjectInputType()
					.getDeterministicInput());
		}
	}

	/**
	 * Ueberprüft ob sich die für die Maske relevanten Daten im ProjektObjekt
	 * geändert haben.<br>
	 * 
	 * Überprüft werden: <br>
	 * - Basisjahr<br>
	 * - Inputtyp (deterministisch, stochastisch)<br>
	 * - Berechnungsart (Direkt, Umsatzkostenverfahren, Gesamtkostenverfahren)
	 * 
	 * @return Boolaen<br>
	 *         true, falls alles gleich ist<br>
	 *         false wenn sich etwas geaendert hat
	 * @author Daniel Dengler
	 * @author Annika Weis
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
	 * Üerprueft ob sich die Periodenanzahl (zu planen, vergangene) geändert hat
	 * 
	 * @return true falls alles gleich ist, false wenn sich etwas geaendert hat
	 * @author Annika WEis
	 */
	public boolean isValid_Zeitraum() {
		logger.debug("Periodenanzahl: "
				+ projectProxy.getSelectedProject().getRelevantPastPeriods()
				+ " = "
				+ fixedPastPeriods
				+ " | "
				+ projectProxy.getSelectedProject()
						.getPeriodsToForecast_deterministic() + " = "
				+ fixedFuturePeriods);
		if (projectProxy.getSelectedProject().getRelevantPastPeriods() == fixedPastPeriods
				&& projectProxy.getSelectedProject()
						.getPeriodsToForecast_deterministic() == fixedFuturePeriods) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @author Annika Weis
	 * @return: Boolean, true: gleiches Projekt, false: andere Projekt
	 *          Überprüfung anhand des Projektnamens
	 */
	private boolean isSameProject() {
		return (projektname == projectProxy.getSelectedProject().getName());
	}

	/**
	 * Prüft, ob bereits Perioden für diesen Inputtyp vorhanden sind. <br>
	 * Wenn ja, werden diese überprüft, ob es auch die gleichen
	 * Berechnungsverfahren sind. <br>
	 * Wenn ja, wird noch überprüft, ob das erste Jahr gleich dem Basisjahr ist
	 * 
	 * Nur wenn das alles zutrifft, gibt es bereits benutzbare Perioden<br>
	 * Ansonsten gab es Änderungen, alles wird verworfen und das Fenster neu
	 * aufgebaut
	 * 
	 * @author Annika Weis
	 * @return Boolean: erster Aufruf des Projekts (true)
	 * 
	 * 
	 */
	private boolean isFirst_call() {
		// nur wenn sich was finden lässt, das ist wie angegeben, false. Sonst
		// wird alles neu erstellt
		boolean first_call = true;
		int anz = 0;

		if (projectProxy.getSelectedProject().getProjectInputType()
				.getDeterministic()) {
			// deterministisch
			try {
				anz = projectProxy.getSelectedProject()
						.getDeterministicPeriods().getPeriods().size();
				Period periode = (Period) projectProxy.getSelectedProject()
						.getDeterministicPeriods().getPeriods().toArray()[0];
				// Inputtype der Periode abgleichen mit angegebenem Typ
				switch (projectProxy.getSelectedProject().getProjectInputType()
						.getDeterministicInput()) {
				case REVENUE:
					if (periode instanceof CostOfSalesMethodPeriod) {
						first_call = false;
					}
					break;
				case TOTAL:
					if (periode instanceof AggregateCostMethodPeriod) {
						first_call = false;
					}
					break;
				case DIRECT:
					if (periode instanceof CashFlowPeriod) {
						first_call = false;
					}
					break;
				}
				// Basisjahr überprüfen
				if (!first_call) {
					if (periode.getYear() != projectProxy.getSelectedProject()
							.getBasisYear()) {
						first_call = true;
						logger.debug("Basisjahr: "
								+ projectProxy.getSelectedProject()
										.getBasisYear());
					}
				}
				logger.debug("Deterministische Perioden vorhanden");
			} catch (Exception e) {
			}
		} else if (projectProxy.getSelectedProject().getProjectInputType()
				.getStochastic()) {
			// stochastisch
			try {
				anz = projectProxy.getSelectedProject().getStochasticPeriods()
						.getPeriods().size();
				Period periode = (Period) projectProxy.getSelectedProject()
						.getStochasticPeriods().getPeriods().toArray()[projectProxy
						.getSelectedProject().getStochasticPeriods()
						.getPeriods().toArray().length - 1];
				// Inputtype der Periode abgleichen mit angegebenem Typ
				switch (projectProxy.getSelectedProject().getProjectInputType()
						.getDeterministicInput()) {
				case REVENUE:
					if (periode instanceof CostOfSalesMethodPeriod) {
						first_call = false;
					}
					break;
				case TOTAL:
					if (periode instanceof AggregateCostMethodPeriod) {
						first_call = false;
					}
					break;
				case DIRECT:
					if (periode instanceof CashFlowPeriod) {
						first_call = false;
					}
					break;
				}

				// Basisjahr überprüfen
				if (!first_call) {
					if (periode.getYear() != projectProxy.getSelectedProject()
							.getBasisYear()) {
						first_call = true;
						logger.debug("Basisjahr: "
								+ projectProxy.getSelectedProject()
										.getBasisYear() + " / "
								+ periode.getYear());
					}
				}
				logger.debug("Stochastische Perioden vorhanden");
			} catch (Exception e) {
			}

		}
		logger.debug("First_call: " + first_call);
		return first_call;
	}

	/**
	 * @author Annika Weis
	 */
	private void initalise() {
		deterministicInput = projectProxy.getSelectedProject()
				.getProjectInputType().getDeterministicInput();
		stochasticInput = projectProxy.getSelectedProject()
				.getProjectInputType().getStochasticInput();

		stochastic = projectProxy.getSelectedProject().getProjectInputType()
				.getStochastic();
		deterministic = projectProxy.getSelectedProject().getProjectInputType()
				.getDeterministic();
		baseYear = projectProxy.getSelectedProject().getBasisYear();

		fixedFuturePeriods = projectProxy.getSelectedProject()
				.getPeriodsToForecast_deterministic();
		fixedPastPeriods = projectProxy.getSelectedProject()
				.getRelevantPastPeriods();

		projektname = projectProxy.getSelectedProject().getName();

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
	 * @author Annika Weis TODO
	 */
	private void addFuturePeriods_vorhanden() {
		/*
		 * Wenn bereits Perioden vorhanden sind: so viele anlegen, sonst so
		 * viele, wie es der Benutzer vorgibt auf der Parameter-Maske
		 */
		logger.debug("future periods");

		int i = 0;
		sumFuturePeriods = 0;
		try {
			//enthält die Perioden die gelöscht werden sollen
			TreeSet<Period> del_periods = new TreeSet<>();
			
			//alle vorhandene Perioden durchlaufen
			for (Period periode : projectProxy.getSelectedProject()
					.getDeterministicPeriods().getPeriods()) {
				if (i == 0) {
					// erste Periode = Basisjahr
					getView().addBasePeriod(periode);
					futurePeriods.addPeriod(periode);
					logger.debug("Basisjahr");
				} else if (i + 2 > projectProxy.getSelectedProject()
						.getPeriodsToForecast_deterministic()) {
					// mehr Perioden vorhanden, als der Benutzer will
					// Diese werden gelöscht
					logger.debug("Überspringen " + periode.getYear());
					//Zwischenspeichern, wird später gelöscht
					del_periods.add(periode);
				} else {
					// Normalfall, Periode anzeigen
					getView().addFuturePeriod(periode);
					sumFuturePeriods++;
					futurePeriods.addPeriod(periode);
					logger.debug("Normalfall");
				}
				logger.debug(++i + " + " + periode.getYear());
				projectProxy.getSelectedProject().setDeterministicPeriods(
						futurePeriods);
			}
			
			for (Period periode : del_periods){
				projectProxy.getSelectedProject().getDeterministicPeriods()
				.removePeriod(periode);
				logger.debug("Löschen " + periode.getYear());
			}
		} catch (Exception e) {
			logger.debug("Fehler:::");
			e.printStackTrace();
		}

		int vorhandene = 0;
		try {
			// -1 wegen Basisjahr
			vorhandene = projectProxy.getSelectedProject()
					.getDeterministicPeriods().getPeriods().size() - 1;
		} catch (Exception e) {
		}
		if (vorhandene == 0) {
			create_base();
		}
		// Wenn weniger Perioden vorhanden sind als geplant
		if (vorhandene < projectProxy.getSelectedProject()
				.getPeriodsToForecast_deterministic()) {
			logger.debug("Manuell Perioden anlegen "
					+ (projectProxy.getSelectedProject()
							.getPeriodsToForecast_deterministic() - vorhandene));
			addFuturePeriods(projectProxy.getSelectedProject()
					.getPeriodsToForecast_deterministic() - vorhandene,
					deterministicInput);
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
		for (int i = 0; i < howMany; i++) {
			sumPastPeriods++;
			Period period = buildNewPeriod(inputType, baseYear - sumPastPeriods);
			pastPeriods.addPeriod(period);
			getView().addPastPeriod(period);
			logger.debug("Periode " + period.getYear() + " angelegt ("
					+ inputType.toString() + ")");
		}
		projectProxy.getSelectedProject().setStochasticPeriods(pastPeriods);
	}

	/**
	 * Zukünftige stochastische Perioden anlegen
	 * 
	 * @author Annika Weis TODO
	 */
	private void addPastPeriods_vorhanden() {
		/*
		 * Wenn bereits Perioden vorhanden sind: so viele anlegen, sonst so
		 * viele, wie es der Benutzer vorgibt auf der Parameter-Maske
		 */
		logger.debug("past periods: "
				+ projectProxy.getSelectedProject().getRelevantPastPeriods());
		int i = 0;
		sumPastPeriods = 0;
		try {
			/*
			 * Perioden müssen in umgekehrter Reihenfolge angegeben werden,
			 * sonst ensteht etwas wie: 2012-2011-2010-2009-2008-2013
			 */
			int laenge = projectProxy.getSelectedProject()
					.getStochasticPeriods().getPeriods().size() - 1;
			TreeSet<Period> perioden = (TreeSet<Period>) projectProxy
					.getSelectedProject().getStochasticPeriods().getPeriods();
			// Länge der vorhandenen Perioden
			laenge = perioden.size();
			// nur so viele Perioden ausgeben, wie der Benutzer angegeben hat
			// bzw vorhanden sind
			laenge = Math.min(laenge, projectProxy.getSelectedProject()
					.getRelevantPastPeriods());
			// Länge-1 wegen dem Array-Index 0
			laenge = laenge ;
			logger.debug("Länge: " + laenge);
			// wenn mehr Perioden vorhanden sind als gewünscht...
			if (perioden.size() > projectProxy.getSelectedProject()
					.getRelevantPastPeriods()) {
				// ...dann nur die letzten gewünschten ausgeben
				laenge = perioden.size()
						- projectProxy.getSelectedProject()
								.getRelevantPastPeriods() + 1;
			} else {
				// ...sonst alle ausgeben
				laenge = 0;
			}

			// Basis-Periode anlegen
			getView().addBasePeriod((Period) perioden.toArray()[0]);
			pastPeriods.addPeriod(basePeriod);

			/**
			 * Perioden ausgeben: Anfangen bei der letzten (höchstes Jahr!) bis
		     * zur gewünschten Länge
			 * Ausgabe erfolgt rückwärts
			 * -2 wegen Array-Index 0 UND Basisperiode abziehen
			 * 
			 * TODO übrige Perioden löschen
			 */
			for (int x = perioden.size() - 1; x >= laenge-2; x--) {
				Period period = (Period) perioden.toArray()[x];
				logger.debug(x + " - " + period.getYear());
				if (x == perioden.size() - 1) {
					logger.debug("Basisperiode: " + period.getYear());
					getView().addBasePeriod(period);
				} else {
					getView().addPastPeriod(period);
					sumPastPeriods++;
				}
				pastPeriods.addPeriod(period);
			}
			projectProxy.getSelectedProject().setStochasticPeriods(pastPeriods);
			
			//Übrige Perioden löschen
			for (int x = laenge-3; x >= 0; x--) {
				Period period = (Period) perioden.toArray()[x];
				logger.debug("Lösche Jahr " + period.getYear());
				pastPeriods.removePeriod(period);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		// wenn nicht genug Perioden vorhanden angelegt wurden wie vom Benutzer
		// angegeben
		logger.debug(sumPastPeriods + " | "
				+ projectProxy.getSelectedProject().getRelevantPastPeriods());
		if (sumPastPeriods == 0) {
			create_base();
		}
		if (sumPastPeriods < projectProxy.getSelectedProject()
				.getRelevantPastPeriods()) {
			logger.debug("Manuell Perioden anlegen");
			addPastPeriods(projectProxy.getSelectedProject()
					.getRelevantPastPeriods() - sumPastPeriods,
					stochasticInput);
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
		logger.debug("removeEverything: " + sumPastPeriods + " | "
				+ sumFuturePeriods);
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

		create_base();

	}

	/**
	 *
	 */
	private void create_base() {
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
		/**
		 * Annika Weis<br>
		 * Bei deterministischem Verfahren: Bis auf zwei Perioden können alle
		 * gelöscht werden
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
