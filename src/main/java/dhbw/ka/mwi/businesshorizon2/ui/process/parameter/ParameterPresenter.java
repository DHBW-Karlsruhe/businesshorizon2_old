package dhbw.ka.mwi.businesshorizon2.ui.process.parameter;

import java.util.Calendar;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;

import dhbw.ka.mwi.businesshorizon2.ui.process.ScreenPresenter;
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

	private int[] numberIterations;

	/**
	 * Dies ist der Konstruktor, der von Spring nach der Initialierung der
	 * Dependencies aufgerufen wird. Er registriert lediglich sich selbst als
	 * einen EventHandler.
	 * 
	 * @author Julius Hacker
	 */
	@PostConstruct
	public void init() {

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
		getView().setIterations(numberIterations);
	}

	@Override
	public boolean isSelectable() {
		// TODO NOTWENDIG?
		return false;
	}

	@Override
	public boolean isValid() {
		// TODO NOTWENDIG?
		return false;
	}

	/**
	 * Methode die sich nach der Auswahl der Iteration um die davon abhaengigen
	 * Objekte kuemmert
	 * 
	 * @author Christian Scherer
	 * @param iteration
	 *            Anzahl der ausgewaehlten Wiederholungen(Iterationen)
	 */
	public void iterationChosen(int iteration) {
		// TODO die Iteration in das Projekt abspeichern
		logger.debug("Iterationen in Objekten gesetzt: " + iteration);
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
				// TODO: in projekt-Objekt abspeichern
				logger.debug("Anzahl Perioden die vorherzusagen sind in das Projekt-Objekten gesetzt");
			} else {
				throw new NumberFormatException();
			}
		} catch (NumberFormatException nfe) {
			getView().clearTextFieldnumPeriodsToForecast();
			getView()
					.showErrorMessage(
							"Keine Zul\u00E4ssige Eingabe in Feld 'Anzahl zu prognostizierender Perioden'. <br> Bitte geben Sie die Anzahl vorherzusehender Perioden in einer Ganzzahl gr\u00F6\u00DFer 0 an. <br> Beispiel: 5");
			logger.debug("Keine gueltige Eingabe in Feld 'Anzahl zu prognostizierender Perioden'");
		}

	}

	/**
	 * Methode die sich nach der Auswahl der zu beachtenenden vergangenen
	 * Perioden um die davon abhaengigen Objekte kuemmert
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
			if (relevantPastPeriodsInt > 0) {
				// TODO in projekt-Objekt abspeichern
				logger.debug("Anzahl relevanter Perioden der Vergangenheit sind in das Projekt-Objekten gesetzt");
			} else {
				throw new NumberFormatException();
			}
		} catch (NumberFormatException nfe) {
			getView().clearTextFieldNumPastPeriods();
			getView()
					.showErrorMessage(
							"Keine Zul\u00E4ssige Eingabe in Feld 'Anzahl einbezogener, vergangener Perioden'. <br> Bitte geben Sie die Anzahl der relevanten vergangenen Perioden in einer Ganzzahl gr\u00F6\u00DFer 0 an. <br> Beispiel: 5");
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
				// TODO in projekt-Objekt abspeichern
				logger.debug("Basisjahr in das Projekt-Objekten gesetzt");
			} else {
				throw new NumberFormatException();
			}
		} catch (NumberFormatException nfe) {
			getView().setTextFieldValueBasisYear("");
			getView()
					.showErrorMessage(
							"Keine Zul\u00E4ssige Eingabe in Feld 'Wahl des Basisjahr'. <br> Bitte geben Sie ein g\u00FCltiges Jahr an. <br> Beispiel: 2012");
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
	 * ggf. die zu prognostizierenden Peroden / Anzahl relevanter Alt-Perioden,
	 * je nach gewaehltem Verfahren
	 * 
	 * @author Christian Scherer
	 * 
	 */
	public void greyOut() {
		getView().greyOutCheckboxIndustryRepresentative();
		getView().greyOutComboBoxRepresentatives();

		// TODO: ANDERE AUSGRAUEN!?

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

	}

}
