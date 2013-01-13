package dhbw.ka.mwi.businesshorizon2.ui.process.parameter;

import java.util.Calendar;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.mvplite.event.EventBus;
import com.mvplite.event.EventHandler;

import dhbw.ka.mwi.businesshorizon2.services.proxies.ProjectProxy;
import dhbw.ka.mwi.businesshorizon2.ui.process.InvalidStateEvent;
import dhbw.ka.mwi.businesshorizon2.ui.process.ScreenPresenter;
import dhbw.ka.mwi.businesshorizon2.ui.process.ScreenSelectableEvent;
import dhbw.ka.mwi.businesshorizon2.ui.process.ValidStateEvent;
import dhbw.ka.mwi.businesshorizon2.ui.process.ValidateContentStateEvent;
import dhbw.ka.mwi.businesshorizon2.ui.process.navigation.NavigationSteps;
import dhbw.ka.mwi.businesshorizon2.ui.process.parameter.ParameterViewInterface;

/**
 * Der Presenter fuer die Maske des Prozessschrittes zur Eingabe der Parameter.
 * 
 * @author Julius Hacker, Christian Scherer
 * 
 */

public class ParameterPresenter extends ScreenPresenter<ParameterViewInterface> {
	private static final long serialVersionUID = 1L;

	private Logger logger = Logger.getLogger("ParameterPresenter.class");

	@Autowired
	private EventBus eventBus;

	@Autowired
	private ProjectProxy projectProxy;

	private boolean iterationsValid;
	private boolean basisYearValid;
	private boolean periodsToForecastValid;
	private boolean relevantPastPeriodsValid;

	private boolean determMethod;
	private boolean stochMethod;

	private int[] numberIterations;

	private boolean firstCall;

	/**
	 * Dies ist der Konstruktor, der von Spring nach der Initialierung der
	 * Dependencies aufgerufen wird. Er registriert sich selbst als einen
	 * EventHandler, prueft ob welche Eingabemthode im Screen zuvor gewaehlt
	 * wurde. Zudem werden die validitaeten der Felder zunaechst auf false
	 * gesetzt. Zudem wird der Wert der firstCall Variable auf true gesetzt,
	 * sodass die erste Pruefung des screens noch keine Fehlermeldung wirft, da
	 * der Benutzer den Screen auch noch nicht geoeffnet hat
	 * 
	 * @author Julius Hacker, Christian SCherer
	 */
	@PostConstruct
	public void init() {

		// TODO setzen ob determ
		// determMethod = project.getInputType().getDeterm...
		// TODO setzen ob stoch
		// stochMethod =project.getInputType().getStoch...

		eventBus.addHandler(this);

		basisYearValid = false;
		iterationsValid = false;
		periodsToForecastValid = false;
		relevantPastPeriodsValid = false;

		setIterations();
		firstCall = true;

	}

	/**
	 * Dies ist der Konstruktor, der von Spring nach der Initialierung der
	 * Dependencies aufgerufen wird. Er registriert sich selbst als einen
	 * EventHandler, prueft ob welche Eingabemthode im Screen zuvor gewaehlt
	 * wurde. Desweiteren wird die firstCall Variable auf false gesetzt, sodass
	 * ab jetzt bei jeder Validierungsanfrage alle Felder geprueft und ggf. als
	 * unkorrekt mariert werden. Beim ersten Aufruf ist dies noch nicht
	 * gewuenscht, da der benutzer den Screen noch nicht geoffnet hatte. Als
	 * letztes wird ein ScreenSelectable Event gefeuert, sodass gewaehrleistet
	 * ist, dass der erste Durchgang zwar streng nach Reihenfloge geschieht,
	 * danach aber jeder Screen frei angewaehlt werden kann.
	 * 
	 * @author Julius Hacker, Christian Scherer
	 */
	@EventHandler
	public void onShowParameterScreen(ShowParameterViewEvent event) {
		if (projectProxy.getSelectedProject().getBasisYear() == 0) {
			initializeBasisYear();
		}
		greyOut();
		firstCall = false;
		eventBus.fireEvent(new ScreenSelectableEvent(NavigationSteps.PARAMETER,
				true));

	}

	/**
	 * Erzeugt das Integerarray der Iterationen, befuellt es mit Werten und
	 * setzt diese dann in die View zur anzeige
	 * 
	 * @author Christian Scherer
	 */
	public void setIterations() {

		numberIterations = new int[3];
		numberIterations[0] = 1000;
		numberIterations[1] = 10000;
		numberIterations[2] = 100000;
		logger.debug("Iterationsarray befuellt");
	}

	/**
	 * In dieser Methode werden alle Eingabefelder auf Validitaet geprueft. Sie
	 * wird auch von anderen Screens aufzurufen um sicherzustellen, dass bei
	 * Aenderungen in anderen Screens diese Eingabe immer noch valide sind.
	 * Falls der Screen nicht mehr valide ist, muss zudem geprueft werden welche
	 * Felder nicht mehr gueltig sind und diese mit einem ComponentenError
	 * (rotem Ausrufezeichen) markiert werden. Zudem wird der Sonderfall
	 * behandelt, dass es der erste Aufruf ist, dann wird sofort true
	 * zurueckgegeben, da der Nutzer noch nicht die moeglichkeit hatte korrekte
	 * Angaben einzugeben
	 * 
	 * @author Christian Scherer
	 * @return Ob alle Validierungspruefungen der Eingabefelder positiv gelaufen
	 *         verlaufen ist
	 */
	@Override
	public boolean isValid() {
		if (firstCall) {
			return true;
		}

		if (determMethod && !stochMethod) {
			if (basisYearValid) {
				return true;
			} else {
				getView()
						.setComponentError(
								true,
								"basisYear",
								"Bitte geben Sie ein g\u00FCltiges Jahr an, jedoch nicht kleiner als letztes Jahr. Beispiel: 2015");
				return false;
			}

		} else {
			if (periodsToForecastValid && relevantPastPeriodsValid
					&& basisYearValid && iterationsValid) {
				return true;
			} else {
				if (!periodsToForecastValid) {
					getView()
							.setComponentError(
									true,
									"periodsToForecast",
									"Bitte geben Sie die Anzahl vorherzusehender Perioden in einer Ganzzahl gr\u00F6\u00DFer 0 an. Beispiel: 5");
				}
				if (!relevantPastPeriodsValid) {
					getView()
							.setComponentError(
									true,
									"pastPeriods",
									"Bitte geben Sie die Anzahl der relevanten vergangenen Perioden in einer Ganzzahl gr\u00F6\u00DFer oder gleich 5 an. Beispiel: 10");
				}
				if (!iterationsValid) {
					getView()
							.setComponentError(true, "iterations",
									"Bitte w\u00E4hlen Sie die Anzahl der Wiederholungen. Beispiel: 10.000");
				}
				if (!basisYearValid) {
					getView()
							.setComponentError(
									true,
									"basisYear",
									"Bitte geben Sie ein g\u00FCltiges Jahr an, jedoch nicht kleiner als letztes Jahr. Beispiel: 2015");
				}
				return false;
			}
		}
	}

	/**
	 * Methode die sich nach der Auswahl der Iteration um die davon abhaengigen
	 * Objekte kuemmert. Zudem wird das iterionsInitialized-Wert auf true
	 * gesetzt, damit ist eine valide Befuellung des Felds gewaehrleistet, da
	 * danach kein Nullwert mehr eingetragen werden kann. Achtung: Dies gilt
	 * fuer die Implementierung in Vaadin, da hier Null-Werte bei der Eingabe
	 * ausgeschlossen wurden.
	 * 
	 * @author Christian Scherer
	 * @param iterations
	 *            Anzahl der ausgewaehlten Wiederholungen(Iterationen)
	 */
	public void iterationChosen(int iterations) {
		iterationsValid = true;
		this.projectProxy.getSelectedProject().setIterations(iterations);
		getView().setComponentError(false, "iterations", null);
		logger.debug("Iterationen in Objekten gesetzt: "
				+ this.projectProxy.getSelectedProject().getName());
	}

	/**
	 * Methode die sich nach der Auswahl der zu Vorherzusagenden Perioden um die
	 * davon abhaengigen Objekte kuemmert. Konkret wird aus dem String des
	 * Eingabefelds der Integer-Wert gezogen und geprueft ob der eingegebene
	 * Wert groesser 0 ist. Ist einer der beiden Kriterien nicht erfuellt wird
	 * eine ClassCastException geworfen, die zu einer Fehlermeldung auf der
	 * Benutzeroberflaecher fuehrt.
	 * 
	 * @author Christian Scherer
	 * @param numberPeriodsToForecast
	 *            Anzahl der Perioden die in die Vorhergesagt werden sollen
	 */
	public void numberPeriodsToForecastChosen(String periodsToForecast) {
		logger.debug("Anwender-Eingabe zu Perioden die vorherzusagen sind");

		int periodsToForecastInt;
		try {
			periodsToForecastInt = Integer.parseInt(periodsToForecast);
			if (periodsToForecastInt > 0) {
				periodsToForecastValid = true;
				getView().setComponentError(false, "periodsToForecast", "");
				this.projectProxy.getSelectedProject().setPeriodsToForecast(
						periodsToForecastInt);
				logger.debug("Anzahl Perioden die vorherzusagen sind in das Projekt-Objekten gesetzt");
			} else {
				throw new NumberFormatException();
			}
		} catch (NumberFormatException nfe) {
			periodsToForecastValid = false;
			getView()
					.setComponentError(
							true,
							"periodsToForecast",
							"Bitte geben Sie die Anzahl vorherzusehender Perioden in einer Ganzzahl gr\u00F6\u00DFer 0 an. Beispiel: 5");
			getView()
					.showErrorMessage(
							"Keine Zul\u00E4ssige Eingabe in Feld 'Anzahl zu prognostizierender Perioden'. <br> Bitte geben Sie die Anzahl vorherzusehender Perioden in einer Ganzzahl gr\u00F6\u00DFer 0 an. <br> Beispiel: 5");
			logger.debug("Keine gueltige Eingabe in Feld 'Anzahl zu prognostizierender Perioden'");
		}

	}

	/**
	 * Methode die sich nach der Auswahl der zu beachtenenden vergangenen
	 * Perioden um die davon abhaengigen Objekte kuemmert. Diese muessen laut
	 * Fachkonzept mindestens 5 Perioden betragen
	 * 
	 * @author Christian Scherer
	 * @param relevantPastPeriods
	 *            die Anzahl der Perioden der Vergangenheit die einbezogen
	 *            werden sollen
	 */
	public void relevantPastPeriodsChosen(String relevantPastPeriods) {
		logger.debug("Anwender-Eingabe zu relevanter Perioden der Vergangenheit ");

		int relevantPastPeriodsInt;
		try {
			relevantPastPeriodsInt = Integer.parseInt(relevantPastPeriods);
			if (relevantPastPeriodsInt >= 5) {
				relevantPastPeriodsValid = true;
				getView().setComponentError(false, "pastPeriods", "");
				this.projectProxy.getSelectedProject().setRelevantPastPeriods(
						relevantPastPeriodsInt);
				logger.debug("Anzahl relevanter Perioden der Vergangenheit sind in das Projekt-Objekten gesetzt");
			} else {
				throw new NumberFormatException();
			}
		} catch (NumberFormatException nfe) {
			relevantPastPeriodsValid = false;
			getView()
					.setComponentError(
							true,
							"pastPeriods",
							"Bitte geben Sie die Anzahl der relevanten vergangenen Perioden in einer Ganzzahl gr\u00F6\u00DFer oder gleich 5 an. Beispiel: 10");
			getView()
					.showErrorMessage(
							"Keine Zul\u00E4ssige Eingabe in Feld 'Anzahl einbezogener, vergangener Perioden'. <br> Bitte geben Sie die Anzahl der relevanten vergangenen Perioden in einer Ganzzahl gr\u00F6\u00DFer oder gleich 5 an. <br> Beispiel: 10");
			logger.debug("Keine gueltige Eingabe in Feld 'Anzahl einbezogener, vergangener Perioden'");
		}
	}

	/**
	 * Methode die sich nach der Auswahl des Basisjahrs um die davon abhaengigen
	 * Objekte kuemmert. Wenn ein int Wert vorliegt wird geprueft ob es sich bei
	 * der Eingegebenen Zahl um ein Jahr groesser dem aktuellen Jahr-1 handelt
	 * 
	 * @author Christian Scherer
	 * @param basisYear
	 *            das Basis-Jahr, auf das die Cashflows abgezinst werden
	 */
	public void basisYearChosen(String basisYear) {
		logger.debug("Anwender-Eingabe zu relevanter Perioden der Vergangenheit ");

		int basisYearInt;
		try {
			basisYearInt = Integer.parseInt(basisYear);
			Calendar now = Calendar.getInstance();

			if (basisYearInt >= (now.get(Calendar.YEAR) - 1)) {
				basisYearValid = true;
				getView().setComponentError(false, "basisYear", "");
				this.projectProxy.getSelectedProject().setBasisYear(
						basisYearInt);
				logger.debug("Basisjahr in das Projekt-Objekten gesetzt");
			} else {
				throw new NumberFormatException();
			}
		} catch (NumberFormatException nfe) {
			basisYearValid = false;
			getView()
					.setComponentError(
							true,
							"basisYear",
							"Bitte geben Sie ein g\u00FCltiges Jahr an, jedoch nicht kleiner als letztes Jahr. Beispiel: 2015");
			getView()
					.showErrorMessage(
							"Keine Zul\u00E4ssige Eingabe in Feld 'Wahl des Basisjahr'. <br> Bitte geben Sie ein g\u00FCltiges Jahr an, jedoch nicht kleiner als letztes Jahr. <br> Beispiel: 2015");
			logger.debug("Keine gueltige Eingabe in Feld 'Wahl des Basisjahr'");
		}

	}

	/**
	 * Methode die sich nach der Aenderung des Wertes der Checkbox fuer die
	 * Branchenstellvertreter um die weiter Logik kuemmert. Hier muesste nun die
	 * Liste der Branchenvertreter aktiviert werden, die standardmaessig
	 * ausgegrautist. Derzeit werden die Branchenvertreter jedoch nicht genutzt
	 * und somit hier auch nicht weiter behandelt. Eine spaetere
	 * ausimplementierung kann hier folgen
	 * 
	 * @author Christian Scherer
	 * @param selected
	 *            "true" wenn der Haken ausgewaehlt wurde, "false" wenn der
	 *            Haken entfernt wurde
	 */
	public void industryRepresentativeCheckBoxSelected(boolean selected) {
		if (selected) {
			// Liste aktivieren
		} else {
			// Liste deaktivieren
		}
	}

	/**
	 * Methode die sich nach der Auswahl in der Liste der Branchenvertreter um
	 * die weiter Logik kuemmert. Derzeit nicht genutzt - kann jedoch spaeter
	 * hier ausprogrammiert werden.
	 * 
	 * @author Christian Scherer
	 * @param selected
	 *            String mit Namen des gewaehlten Branchenvertreters
	 */
	public void industryRepresentativeListItemChosen(String selected) {
	}

	/**
	 * Methode die sich um das Ausgrauen unrelevanter Komponenten. In unserem
	 * Fall die Branchenstellvertreter, die noch nicht implementiert sind und
	 * ggf. die zu prognostizierenden Perioden / Anzahl relevanter Alt-Perioden,
	 * je nach gewaehltem Verfahren. Falls im Method-Screen NUR die
	 * Deterministische Methode ausgewaehlt wurde, muessen ebenso alle anderen
	 * Felder ausser das Basisjahr ausgegraut werden.
	 * 
	 * @author Christian Scherer
	 * 
	 */
	public void greyOut() {
		getView().activateCheckboxIndustryRepresentative(false);
		getView().activateComboBoxRepresentatives(false);

		if (determMethod && !stochMethod) {
			getView().activatePeriodsToForecast(false);
			getView().activateRelevantPastPeriods(false);
			getView().activateIterations(false);
		}

	}

	/**
	 * Initialisiert das Basisjahr mit dem aktuellem Jahr-1
	 * 
	 * @author Christian Scherer
	 * 
	 */
	public void initializeBasisYear() {
		Calendar now = Calendar.getInstance();
		getView().setTextFieldValueBasisYear("" + (now.get(Calendar.YEAR) - 1));
		basisYearValid = true;
		logger.debug("Initialjahr " + (now.get(Calendar.YEAR) - 1)
				+ " gesetzt.");

	}

	/**
	 * Eventhandler der zuerst prueft ob sich Vorbedingungen geaendert haben und
	 * prueft darauf hin ob der Screen an sich immer noch komplett valide ist.
	 * Falls nicht, wird ein InvalidStateEven gefeuert. Wichtig ist noch, dass
	 * zunaechst geprueft werden muss ob es sich um den ersten Aufruf handelt,
	 * also der Anwender noch keine Moeglichkeit hatte die Felder korrekt zu
	 * befuellen. Ist dem so wird der Screen noch als valide gewertet. Erst nach
	 * dem ersten Aufrufen des Screens wird dann die pruefung bei falschen
	 * Eintraegen auch ein Invalid Event feuern
	 * 
	 * @author Christian Scherer
	 */
	@Override
	@EventHandler
	public void validate(ValidateContentStateEvent event) {
		// TODO setzen ob determ
		// determMethod = project.getInputType().getDeterm...
		// TODO setzen ob stoch
		// stochMethod =project.getInputType().getStoch...
		if (!firstCall && !isValid()) {
			eventBus.fireEvent(new InvalidStateEvent(NavigationSteps.PARAMETER));
			logger.debug("Parameter not valid, InvalidStateEvent fired");
		} else {
			eventBus.fireEvent(new ValidStateEvent(NavigationSteps.PARAMETER));
			logger.debug("Parameter valid, ValidStateEvent fired");
		}
	}

	public int[] getNumberIterations() {
		return numberIterations;
	}

}