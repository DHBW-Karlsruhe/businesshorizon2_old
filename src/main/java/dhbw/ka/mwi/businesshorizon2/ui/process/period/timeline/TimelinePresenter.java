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
package dhbw.ka.mwi.businesshorizon2.ui.process.period.timeline;

import java.util.TreeSet;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.mvplite.event.EventBus;
import com.mvplite.event.EventHandler;

import dhbw.ka.mwi.businesshorizon2.methods.AbstractDeterministicMethod;
import dhbw.ka.mwi.businesshorizon2.methods.AbstractStochasticMethod;
import dhbw.ka.mwi.businesshorizon2.models.InputType;
import dhbw.ka.mwi.businesshorizon2.models.Period.CashFlowPeriod;
import dhbw.ka.mwi.businesshorizon2.models.Period.GesamtkostenVerfahrenCashflowPeriod;
import dhbw.ka.mwi.businesshorizon2.models.Period.UmsatzkostenVerfahrenCashflowPeriod;
import dhbw.ka.mwi.businesshorizon2.models.Period.Period;
import dhbw.ka.mwi.businesshorizon2.models.PeriodContainer.AbstractPeriodContainer;
import dhbw.ka.mwi.businesshorizon2.models.PeriodContainer.CashFlowPeriodContainer;
import dhbw.ka.mwi.businesshorizon2.models.PeriodContainer.GesamtkostenVerfahrenCashflowPeriodContainer;
import dhbw.ka.mwi.businesshorizon2.models.PeriodContainer.UmsatzkostenVerfahrenCashflowPeriodContainer;
import dhbw.ka.mwi.businesshorizon2.services.proxies.ProjectProxy;
import dhbw.ka.mwi.businesshorizon2.ui.process.InvalidStateEvent;
import dhbw.ka.mwi.businesshorizon2.ui.process.ScreenPresenter;
import dhbw.ka.mwi.businesshorizon2.ui.process.ShowErrorsOnScreenEvent;
import dhbw.ka.mwi.businesshorizon2.ui.process.ValidStateEvent;
import dhbw.ka.mwi.businesshorizon2.ui.process.ValidateContentStateEvent;
import dhbw.ka.mwi.businesshorizon2.ui.process.navigation.NavigationSteps;
import dhbw.ka.mwi.businesshorizon2.ui.process.period.ShowPeriodViewEvent;
import dhbw.ka.mwi.businesshorizon2.ui.process.period.input.ShowDirektViewEvent;
import dhbw.ka.mwi.businesshorizon2.ui.process.period.input.ShowGKVEvent;
import dhbw.ka.mwi.businesshorizon2.ui.process.period.input.ShowUKVEvent;
import dhbw.ka.mwi.businesshorizon2.ui.process.period.input.WrongFieldsEvent;

/**
 * Presenter fuer die Anzeige des PeriodenZeitstrahls in der Periodenmaske
 * 
 * @author Daniel Dengler, Annika Weis
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

	private int fixedPastPeriods; // Wie viele vergangene Perioden will der User
	private int fixedFuturePeriods;// Wie viele zukünftige Perioden will der
	// User

	private int sumPastPeriods; // wie viele vergangene Perioden sind schon
	// angelegt
	private int sumFuturePeriods;// wie viele zukünftige Perioden sind schon
	// angelegt

	private int baseYear = -9999999;

	private boolean showErrors;

	private AbstractPeriodContainer pastPeriods;
	private AbstractPeriodContainer futurePeriods;

	private Boolean deterministic = false;

	private Boolean stochastic = false;

	private InputType deterministicInput;

	private InputType stochasticInput;

	private Period basePeriod; // Basisperiode

	private String methode; // Vom Benutzer gewählte Periode

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
					try {
						periodClicked(basePeriod);
					} catch (Exception e) {
						logger.debug("Keine Basisperiode angelegt");
					}
				} else {
					// /Wenn sich NUR die Anzahl der Jahre geändert hat
					jahresanzahl_geaendert();
					try {
						periodClicked(basePeriod);
					} catch (Exception e) {
						logger.debug("Keine Basisperiode angelegt");
					}
				}
			}
		} else {
			logger.debug("====anderes Projekt====");
			// wird das Projekt zum ersten Mal aufgerufen
			if (isFirst_call()) {
				logger.debug("Alles wird neu erstellt da erster Aufruf");
				alles_neu();
				try {
					periodClicked(basePeriod);
				} catch (Exception e) {
					logger.debug("Keine Basisperiode angelegt");
				}
			} else {

				// /Wenn sich NUR die Anzahl der Jahre geändert hat
				logger.debug("Anzahl Jahre hat sich geändert");
				jahresanzahl_geaendert();

				logger.debug("===========ENDE=========");
			}
		}

		periodenanzahl_geaendert();

	}

	/**
	 * 
	 * Anpassen der Buttons zum Anlegen und Löschen der Perioden<br>
	 * <b>Diese Funktion sollte IMMER aufgerufen werden, wenn sich etwas an der
	 * Periodenanzahl geändert hat</b><br>
	 * <u>Bei stochastischem:</u><br>
	 * nur vergangene Perioden können angelegt werden <br>
	 * <u>Bei deterministischem:</u><br>
	 * nur zukünftige Perioden können angelegt werden
	 * 
	 * @author Annika Weis <br>
	 */

	private void periodenanzahl_geaendert() {
		logger.debug("Richtigstellen der [+] / [X] - Buttons");
		// Ausnahmen bei Zeitreihenanalyse und APV
		int weitere_perioden_past = 0;
		if (methode == "Zeitreihenanalyse") {
			weitere_perioden_past = -1; // Annika Weis 2014-05-03
		}
		int weitere_perioden_future = -1;
		// }
		if (deterministic) {
			getView().setFutureButtonAccess(false);// (true);
			if (sumFuturePeriods > 1 + weitere_perioden_future) {
				getView().setFutureDeleteButtonAccess(false); // (true);
			} else {
				getView().setFutureDeleteButtonAccess(false);
			}
			getView().setPastButtonAccess(false);
			getView().setPastDeleteButtonAccess(false);
		}
		if (stochastic) {
			if (sumPastPeriods > 3 + weitere_perioden_past) {
				getView().setPastDeleteButtonAccess(false);// (true);
			} else {
				getView().setPastDeleteButtonAccess(false);
			}
			getView().setPastButtonAccess(false);// (true);
			getView().setFutureButtonAccess(false);
			getView().setFutureDeleteButtonAccess(false);
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
	 * @return Boolean<br>
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
					.isDeterministic();
		} catch (Exception e) {
			logger.debug("crash at getDeterministic()"
					+ projectProxy.getSelectedProject().getProjectInputType());
		}
		try {
			if (projectProxy.getSelectedProject().getBasisYear() == baseYear
					&& projectProxy.getSelectedProject().getProjectInputType()
							.isDeterministic() == deterministic
					&& projectProxy.getSelectedProject().getProjectInputType()
							.isStochastic() == stochastic
					&& projectProxy.getSelectedProject().getProjectInputType()
							.getDeterministicInput() == deterministicInput
					&& projectProxy.getSelectedProject().getProjectInputType()
							.getStochasticInput() == stochasticInput) {

				// Methoden abgleichen
				if (stochastic) {
					for (AbstractStochasticMethod method_stochastic : projectProxy
							.getSelectedProject().getMethods()) {
						if (method_stochastic.getSelected()) {
							if (methode == method_stochastic.getName()) {
								logger.debug("gewählte Methode: " + methode);
								return true;
							}
						}
					}
				}

				if (deterministic) {
					for (AbstractDeterministicMethod method_deterministic : projectProxy
							.getSelectedProject().getMethods_deterministic()) {
						if (method_deterministic.getSelected()) {
							if (methode == method_deterministic.getName()) {
								logger.debug("gewählte Methode: " + methode);
								return true;
							}
						}
					}
				}

			} else {
				return false;
			}

			// wenn nie true zurückgegeben wird
			return false;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Üerprueft ob sich die Periodenanzahl (zu planen, vergangene) geändert hat
	 * 
	 * @author Annika Weis
	 * @return true falls alles gleich ist, false wenn sich etwas geaendert hat
	 */
	public boolean isValid_Zeitraum() {
		logger.debug("Periodenanzahl: "
				+ projectProxy.getSelectedProject().getSpecifiedPastPeriods()
				+ " = "
				+ fixedPastPeriods
				+ " | "
				+ projectProxy.getSelectedProject()
						.getPeriodsToForecast_deterministic() + " = "
				+ fixedFuturePeriods);

		/**
		 * Annika Weis <br>
		 * bei der Zeitreihenanalyse<br>
		 * vergangene, angezeigte Perioden = Eingabe PLUS 1
		 * 
		 * bei APV<br>
		 * zukünftige, angezeigte Perioden = Eingabe PLUS 1
		 */
		int weitere_perioden_past = 0;
		int weitere_perioden_future = -1;
		if (methode == "Zeitreihenanalyse") {
			weitere_perioden_past = -1; // Annika Weis 2014-05-03
		}

		if (projectProxy.getSelectedProject().getSpecifiedPastPeriods() == fixedPastPeriods
				+ weitere_perioden_future
				&& projectProxy.getSelectedProject()
						.getPeriodsToForecast_deterministic() == fixedFuturePeriods
						+ weitere_perioden_past) {
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
	 */
	private boolean isFirst_call() {
		// nur wenn sich was finden lässt, das ist wie angegeben, false. Sonst
		// wird alles neu erstellt
		boolean first_call = true;
		int anz = 0;

		if (projectProxy.getSelectedProject().getProjectInputType()
				.isDeterministic()) {
			// deterministisch
			try {
				anz = projectProxy.getSelectedProject()
						.getDeterministicPeriods().getPeriods().size();
				Period periode = (Period) projectProxy.getSelectedProject()
						.getDeterministicPeriods().getPeriods().toArray()[0];
				// Inputtype der Periode abgleichen mit angegebenem Typ
				switch (projectProxy.getSelectedProject().getProjectInputType()
						.getDeterministicInput()) {
				case GESAMTKOSTENVERFAHREN:
					if (periode instanceof GesamtkostenVerfahrenCashflowPeriod) {
						first_call = false;
					}
					break;
				case UMSATZKOSTENVERFAHREN:
					if (periode instanceof UmsatzkostenVerfahrenCashflowPeriod) {
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
				.isStochastic()) {
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
				case GESAMTKOSTENVERFAHREN:
					if (periode instanceof GesamtkostenVerfahrenCashflowPeriod) {
						first_call = false;
					}
					break;
				case UMSATZKOSTENVERFAHREN:
					if (periode instanceof UmsatzkostenVerfahrenCashflowPeriod) {
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
	 * Setzt die weiter benötigten Variablen:<br>
	 * - deterministicInput / stochasticInput<br>
	 * - stochastic / deterministic<br>
	 * - baseYear<br>
	 * - fixedFuturePeriods / fixedPastPeriods<br>
	 * - projektname<br>
	 * - methode<br>
	 * 
	 * @author Annika Weis
	 */
	private void initalise() {
		deterministicInput = projectProxy.getSelectedProject()
				.getProjectInputType().getDeterministicInput();
		stochasticInput = projectProxy.getSelectedProject()
				.getProjectInputType().getStochasticInput();

		stochastic = projectProxy.getSelectedProject().getProjectInputType()
				.isStochastic();
		deterministic = projectProxy.getSelectedProject().getProjectInputType()
				.isDeterministic();
		baseYear = projectProxy.getSelectedProject().getBasisYear();

		fixedFuturePeriods = projectProxy.getSelectedProject()
				.getPeriodsToForecast_deterministic();
		fixedPastPeriods = projectProxy.getSelectedProject()
				.getSpecifiedPastPeriods();

		projektname = projectProxy.getSelectedProject().getName();

		if (stochastic) {
			for (AbstractStochasticMethod method_stochastic : projectProxy
					.getSelectedProject().getMethods()) {
				if (method_stochastic.getSelected()) {
					methode = method_stochastic.getName();
					logger.debug("Methode: " + methode);
				}
			}
			switch (projectProxy.getSelectedProject().getProjectInputType()
					.getStochasticInput()) {
			case GESAMTKOSTENVERFAHREN:
				this.pastPeriods = new GesamtkostenVerfahrenCashflowPeriodContainer();
				this.futurePeriods = new GesamtkostenVerfahrenCashflowPeriodContainer();
				break;
			case UMSATZKOSTENVERFAHREN:
				this.pastPeriods = new UmsatzkostenVerfahrenCashflowPeriodContainer();
				this.futurePeriods = new GesamtkostenVerfahrenCashflowPeriodContainer();
				break;
			case DIRECT:
				this.pastPeriods = new CashFlowPeriodContainer();
				this.futurePeriods = new CashFlowPeriodContainer();
				break;
			}
		}

		if (deterministic) {
			for (AbstractDeterministicMethod method_deterministic : projectProxy
					.getSelectedProject().getMethods_deterministic()) {
				if (method_deterministic.getSelected()) {
					methode = method_deterministic.getName();
					logger.debug("Methode: " + methode);
				}
			}
			switch (projectProxy.getSelectedProject().getProjectInputType()
					.getDeterministicInput()) {
			case GESAMTKOSTENVERFAHREN:
				this.pastPeriods = new GesamtkostenVerfahrenCashflowPeriodContainer();
				this.futurePeriods = new GesamtkostenVerfahrenCashflowPeriodContainer();
				break;
			case UMSATZKOSTENVERFAHREN:
				this.pastPeriods = new UmsatzkostenVerfahrenCashflowPeriodContainer();
				this.futurePeriods = new UmsatzkostenVerfahrenCashflowPeriodContainer();
				break;
			case DIRECT:
				this.pastPeriods = new CashFlowPeriodContainer();
				this.futurePeriods = new CashFlowPeriodContainer();
				break;
			}
		}
		logger.debug("Methode: " + methode);

		if (stochastic) {
			for (AbstractStochasticMethod method_stochastic : projectProxy
					.getSelectedProject().getMethods()) {
				if (method_stochastic.getSelected()) {
					methode = method_stochastic.getName();
				}
			}
		}

		if (deterministic) {
			for (AbstractDeterministicMethod method_deterministic : projectProxy
					.getSelectedProject().getMethods_deterministic()) {
				if (method_deterministic.getSelected()) {
					methode = method_deterministic.getName();
				}
			}
		}
		logger.debug("Methode: " + methode);

	}

	/**
	 * Wenn sich NUR die Anzahl der Jahre geändert hat, werden die
	 * entsprechenden Perioden angezeigt, hinzugefügt, gelöscht
	 * 
	 * @author Annika Weis
	 */
	private void jahresanzahl_geaendert() {
		logger.debug("Jahresanzahl ändern");
		initalise();

		if (projectProxy.getSelectedProject().getProjectInputType()
				.isDeterministic()) {
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
					.getSpecifiedPastPeriods();

		}

		// Stochastische Verfahren
		// Hat sich nur der Inputtyp geaendert, muessen alle
		// betroffenen
		// Perioden verworfen werden und neu angelegt werden.

		if (projectProxy.getSelectedProject().getProjectInputType()
				.isStochastic()) { // projectProxy.getSelectedProject().getProjectInputType().getStochastic()
		// != stochastic &&
			logger.debug("Initialisierung (s) " + fixedPastPeriods);
			removeAllFuturePeriods();
			removeAllPastPeriods();
			// fixedPastPeriods =
			// projectProxy.getSelectedProject().getSpecifiedPastPeriods();

			stochasticInput = projectProxy.getSelectedProject()
					.getProjectInputType().getStochasticInput();
			createContainer(pastPeriods, stochasticInput);

			addPastPeriods_vorhanden();
			fixedFuturePeriods = projectProxy.getSelectedProject()
					.getPeriodsToForecast_deterministic();

		}
	}

	/**
	 * Legt so viele neue Perioden an, wie der Benutzer vorgegeben hat <br>
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
		logger.debug("Alles neu " + baseYear + ", " + methode);
		initalise();

		removeEverything();
		if (stochastic) {

			/**
			 * Annika Weis <br>
			 * Bei der Zeitreihenanalyse: <br>
			 * Standardanzahl vergangener Perioden = Eingabefeld PLUS 1
			 */
			int weitere_perioden_past = 0;
			if (methode == "Zeitreihenanalyse") {
				weitere_perioden_past = -1; // Annika Weis 2014-05-03
			}

			logger.debug("PastPeriods: "
					+ projectProxy.getSelectedProject()
							.getSpecifiedPastPeriods() + " + "
					+ weitere_perioden_past);
			addPastPeriods(projectProxy.getSelectedProject()
					.getSpecifiedPastPeriods() + weitere_perioden_past,
					projectProxy.getSelectedProject().getProjectInputType()
							.getStochasticInput());
		}
		if (deterministic) {

			/**
			 * Annika Weis <br>
			 * Bei der Zeitreihenanalyse: <br>
			 * Standardanzahl vergangener Perioden = Eingabefeld PLUS 1
			 */
			int weitere_perioden_future = -1;

			logger.debug("FuturePeriods: "
					+ projectProxy.getSelectedProject()
							.getPeriodsToForecast_deterministic() + " + "
					+ weitere_perioden_future);
			addFuturePeriods(projectProxy.getSelectedProject()
					.getPeriodsToForecast_deterministic()
					+ weitere_perioden_future, projectProxy
					.getSelectedProject().getProjectInputType()
					.getDeterministicInput());
		}
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
		case UMSATZKOSTENVERFAHREN:
			container = new UmsatzkostenVerfahrenCashflowPeriodContainer();
			break;
		case GESAMTKOSTENVERFAHREN:
			container = new GesamtkostenVerfahrenCashflowPeriodContainer();
			break;
		case DIRECT:
			container = new CashFlowPeriodContainer();
			break;
		}
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
		case GESAMTKOSTENVERFAHREN:
			p = new GesamtkostenVerfahrenCashflowPeriod(year);
			return p;

		case UMSATZKOSTENVERFAHREN:
			p = new UmsatzkostenVerfahrenCashflowPeriod(year);
			return p;

		case DIRECT:
			p = new CashFlowPeriod(year);
			return p;

		default:
			return null;
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
			logger.debug("Periode " + period.getYear() + " angelegt ("
					+ inputType.toString() + ")");
		}

		projectProxy.getSelectedProject()
				.setDeterministicPeriods(futurePeriods);
		periodenanzahl_geaendert();
	}

	/**
	 * Zukünftige deterministische Perioden anlegen <br>
	 * dabei wird berücksichtigt, dass bereits Perioden vorhanden sind.<br>
	 * Diese werden zuerst ausgegeben und, sofern mehr ausgegeben werden sollen,<br>
	 * neue Perioden hinzugefügt.<br>
	 * Übrige Perioden werden ggf gelöscht
	 * 
	 * @author Annika Weis
	 */
	private void addFuturePeriods_vorhanden() {
		/*
		 * Wenn bereits Perioden vorhanden sind: so viele anlegen, sonst so
		 * viele, wie es der Benutzer vorgibt auf der Parameter-Maske
		 */
		logger.debug("future periods");

		int i = 0;
		int weitere_perioden_future = -1;
		sumFuturePeriods = 0;
		Period basisperiode = null;
		try {
			// del_periods: enthält die Perioden die gelöscht werden sollen
			TreeSet<Period> del_periods = new TreeSet<>();
			// alle vorhandene Perioden durchlaufen
			for (Period periode : projectProxy.getSelectedProject()
					.getDeterministicPeriods().getPeriods()) {
				if (i == 0) {
					// erste Periode = Basisjahr
					getView().addBasePeriod(periode);
					futurePeriods.addPeriod(periode);
					basisperiode = periode;
					logger.debug("Basisjahr");
				} else if (i > projectProxy.getSelectedProject()
						.getPeriodsToForecast_deterministic()
						+ weitere_perioden_future) {
					// mehr Perioden vorhanden, als der Benutzer will
					// Diese werden gelöscht
					logger.debug("Überspringen " + periode.getYear());
					// Zwischenspeichern, wird später gelöscht
					del_periods.add(periode);
				} else {
					// Normalfall, Periode anzeigen
					getView().addFuturePeriod(periode);
					sumFuturePeriods++;
					futurePeriods.addPeriod(periode);
					logger.debug("Normalfall " + periode.getYear());
				}
				logger.debug(++i + " + " + periode.getYear() + " _ " + periode.getFreeCashFlow());
				projectProxy.getSelectedProject().setDeterministicPeriods(
						futurePeriods);
			}

			for (Period periode : del_periods) {
				projectProxy.getSelectedProject().getDeterministicPeriods()
						.removePeriod(periode);
				logger.debug("Löschen " + periode.getYear());
			}

			periodClicked(basisperiode);
		} catch (Exception e) {
			logger.debug("Fehler:::");
			e.printStackTrace();
		}

		int vorhandene = 0;
		try {
			vorhandene = projectProxy.getSelectedProject()
					.getDeterministicPeriods().getPeriods().size();
		} catch (Exception e) {
		}
		if (vorhandene == 0) {
			logger.debug("Basis aufbauen");
			create_base();
		}
			// -1 wegen Basisjahr
		vorhandene = vorhandene -1;
		// Wenn weniger Perioden vorhanden sind als geplant
		if (vorhandene < projectProxy.getSelectedProject()
				.getPeriodsToForecast_deterministic() + weitere_perioden_future) {
			logger.debug("Manuell Perioden anlegen "
					+ (projectProxy.getSelectedProject()
							.getPeriodsToForecast_deterministic()
							+ weitere_perioden_future - vorhandene));
			addFuturePeriods(projectProxy.getSelectedProject()
					.getPeriodsToForecast_deterministic()
					+ weitere_perioden_future - vorhandene, deterministicInput);

		}
		logger.debug("Periodenanzahl fut: " + sumFuturePeriods);
		return;
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
		periodenanzahl_geaendert();

	}

	/**
	 * Zukünftige stochastische Perioden anlegen<br>
	 * dabei wird berücksichtigt, dass bereits Perioden vorhanden sind.<br>
	 * Diese werden zuerst ausgegeben und, sofern mehr ausgegeben werden sollen,<br>
	 * neue Perioden hinzugefügt.<br>
	 * Übrige Perioden werden ggf gelöscht
	 * 
	 * @author Annika Weis
	 */
	private void addPastPeriods_vorhanden() {
		/*
		 * Wenn bereits Perioden vorhanden sind: so viele anlegen, sonst so
		 * viele, wie es der Benutzer vorgibt auf der Parameter-Maske
		 */
		logger.debug("past periods: "
				+ projectProxy.getSelectedProject().getSpecifiedPastPeriods());
		int i = 0;
		// bei Zeitreihenanalyse: ein Jahr mehr
		int weitere_perioden_past = 0;
		if (methode == "Zeitreihenanalyse") {
			weitere_perioden_past = -1; // Annika Weis 2014-05-03
		}

		sumPastPeriods = 0;
		Period basisperiode = null;
		try {
			/*
			 * Perioden müssen in umgekehrter Reihenfolge angegeben werden,
			 * sonst ensteht etwas wie: 2012-2011-2010-2009-2008-2013
			 */
			int laenge;
			TreeSet<Period> perioden = (TreeSet<Period>) projectProxy
					.getSelectedProject().getStochasticPeriods().getPeriods();
			// Länge der vorhandenen Perioden
			laenge = perioden.size();
			// nur so viele Perioden ausgeben, wie der Benutzer angegeben hat
			// bzw vorhanden sind
			laenge = Math.min(laenge, projectProxy.getSelectedProject()
					.getSpecifiedPastPeriods() + weitere_perioden_past);//

			// wenn mehr Perioden vorhanden sind als gewünscht...
			if (perioden.size() > projectProxy.getSelectedProject()
					.getSpecifiedPastPeriods() + weitere_perioden_past) {
				// ...dann nur die letzten gewünschten ausgeben
				laenge = perioden.size()
						- (projectProxy.getSelectedProject()
								.getSpecifiedPastPeriods() + weitere_perioden_past)
						+ 1;
			} else {
				// ...sonst alle ausgeben
				laenge = 0;
			}
			logger.debug("Länge: " + laenge);

			/**
			 * Perioden ausgeben: Anfangen bei der letzten (höchstes Jahr!) bis
			 * zur gewünschten Länge Ausgabe erfolgt rückwärts -2 wegen
			 * Array-Index 0 UND Basisperiode abziehen
			 */

			for (int x = perioden.size() - 1; x >= 0; x--) {
				Period period = (Period) perioden.toArray()[x];
				logger.debug(x + " - " + period.getYear());
				if (x == perioden.size() - 1) {
					logger.debug("Basisperiode: " + period.getYear());
					getView().addBasePeriod(period);
					basisperiode = period;
					pastPeriods.addPeriod(period);
				} else if (x < laenge - 2) {
					logger.debug("Lösche Jahr " + period.getYear());
					pastPeriods.removePeriod(period);
				} else {
					logger.debug("Anlegen " + period.getYear());
					getView().addPastPeriod(period);
					sumPastPeriods++;
					pastPeriods.addPeriod(period);
				}
			}

			projectProxy.getSelectedProject().setStochasticPeriods(pastPeriods);

			periodClicked(basisperiode);
		} catch (Exception e) {
			logger.debug("Fehler: " + e.getMessage());
			e.printStackTrace();
		}
		// wenn nicht genug Perioden angelegt wurden wie vom Benutzer angegeben
		logger.debug(sumPastPeriods + " | "
				+ projectProxy.getSelectedProject().getSpecifiedPastPeriods());
		if (sumPastPeriods == 0) {
			create_base();
		}

		if (sumPastPeriods < projectProxy.getSelectedProject()
				.getSpecifiedPastPeriods() + weitere_perioden_past) {
			logger.debug("Manuell Perioden anlegen");
			addPastPeriods(projectProxy.getSelectedProject()
					.getSpecifiedPastPeriods()
					+ weitere_perioden_past
					- sumPastPeriods, stochasticInput);
		}
		logger.debug("Periodenanzahl: " + sumPastPeriods);
		return;
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
				.isStochastic();

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
				.isDeterministic();
		stochastic = projectProxy.getSelectedProject().getProjectInputType()
				.isStochastic();

		create_base();

	}

	// Folgende Funktionen handeln die Buttonklicks aus der View

	/**
	 * Methode zum Aufruf aus der View. Ruft die Folgemethode auf.
	 */
	public void addPastPeriod() {
		// Anzahl der Perioden wird im Projekt angepasst
		// muss passieren, bevor das Event gefeuert wird
		projectProxy.getSelectedProject()
				.setRelevantPastPeriods(
						projectProxy.getSelectedProject()
								.getSpecifiedPastPeriods() + 1);
		projectProxy.getSelectedProject().setStochasticPeriods(pastPeriods);

		addPastPeriods(1, projectProxy.getSelectedProject()
				.getProjectInputType().getStochasticInput());

		eventBus.fireEvent(new ShowPeriodViewEvent());

		// andere Periode anzeigen
		// TODO
		try {
			TreeSet set = projectProxy.getSelectedProject()
					.getStochasticPeriods().getPeriods();
			int laenge = set.toArray().length;
			Period t;
			t = (Period) set.toArray()[0];
			periodClicked(t);
		} catch (Exception e) {
			logger.debug("Fehler beim anzeigen der neuesten Periode");
		}

	}

	/**
	 * Methode zum Aufruf aus der View. Ruft die Folgemethode auf.
	 */
	public void addFuturePeriod() {
		// Anzahl der Perioden wird im Projekt angepasst
		// muss passieren, bevor das Event gefeuert wird
		projectProxy.getSelectedProject().setPeriodsToForecast_deterministic(
				projectProxy.getSelectedProject()
						.getPeriodsToForecast_deterministic() + 1);
		projectProxy.getSelectedProject()
				.setDeterministicPeriods(futurePeriods);

		addFuturePeriods(1, projectProxy.getSelectedProject()
				.getProjectInputType().getDeterministicInput());

		eventBus.fireEvent(new ShowPeriodViewEvent());
		// andere Periode anzeigen
		// TODO
		try {
			TreeSet set = projectProxy.getSelectedProject()
					.getDeterministicPeriods().getPeriods();
			int laenge = set.toArray().length;
			Period t;
			t = (Period) set.toArray()[laenge - 1];
			periodClicked(t);
		} catch (Exception e) {
			logger.debug("Fehler beim anzeigen der neuesten Periode");
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

		// Anzahl der Perioden wird im Projekt angepasst

		projectProxy.getSelectedProject().setPeriodsToForecast_deterministic(
				projectProxy.getSelectedProject()
						.getPeriodsToForecast_deterministic() - 1);

		projectProxy.getSelectedProject()
				.setDeterministicPeriods(futurePeriods);
		periodenanzahl_geaendert();

		// andere Periode anzeigen
		try {
			TreeSet set = projectProxy.getSelectedProject()
					.getDeterministicPeriods().getPeriods();
			int laenge = set.toArray().length;
			periodClicked((Period) set.toArray()[laenge - 1]);
		} catch (Exception e) {
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

		// Anzahl der Perioden wird im Projekt angepasst
		projectProxy.getSelectedProject()
				.setRelevantPastPeriods(
						projectProxy.getSelectedProject()
								.getSpecifiedPastPeriods() - 1);

		projectProxy.getSelectedProject()
				.setDeterministicPeriods(futurePeriods);

		periodenanzahl_geaendert();

		// andere Periode anzeigen
		// TODO
		try {
			TreeSet set = projectProxy.getSelectedProject()
					.getStochasticPeriods().getPeriods();
			int laenge = set.toArray().length;
			periodClicked((Period) set.toArray()[0]);
		} catch (Exception e) {
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
		if (period instanceof GesamtkostenVerfahrenCashflowPeriod) {
			eventBus.fireEvent(new ShowGKVEvent(
					(GesamtkostenVerfahrenCashflowPeriod) period));
		}
		if (period instanceof UmsatzkostenVerfahrenCashflowPeriod) {
			eventBus.fireEvent(new ShowUKVEvent(
					(UmsatzkostenVerfahrenCashflowPeriod) period));
		}
	}

	private void removeAllFuturePeriods() {
		for (int i = 0; i < sumFuturePeriods; i++) {
			getView().removeFuturePeriod();
		}
		sumFuturePeriods = 0;

		deterministic = projectProxy.getSelectedProject().getProjectInputType()
				.isDeterministic();

	}

	@EventHandler
	public void onWrongFieldEvent(WrongFieldsEvent e) {
		setButtonWrong(Integer.parseInt(e.getWrongFields().get(0)), true);
	}

	public void setButtonWrong(int year, boolean isWrong) {
		getView().setButtonWrong(year, isWrong);
	}

	/**
*
*/
	private void create_base() {
		if (stochastic) {
			switch (projectProxy.getSelectedProject().getProjectInputType()
					.getStochasticInput()) {
			case GESAMTKOSTENVERFAHREN:
				basePeriod = new GesamtkostenVerfahrenCashflowPeriod(baseYear);
				getView().addBasePeriod(basePeriod);
				pastPeriods.addPeriod(basePeriod);
				break;
			case UMSATZKOSTENVERFAHREN:
				basePeriod = new UmsatzkostenVerfahrenCashflowPeriod(baseYear);
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
			case GESAMTKOSTENVERFAHREN:
				basePeriod = new GesamtkostenVerfahrenCashflowPeriod(baseYear);
				getView().addBasePeriod(basePeriod);
				futurePeriods.addPeriod(basePeriod);
				break;
			case UMSATZKOSTENVERFAHREN:
				basePeriod = new UmsatzkostenVerfahrenCashflowPeriod(baseYear);
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

}
