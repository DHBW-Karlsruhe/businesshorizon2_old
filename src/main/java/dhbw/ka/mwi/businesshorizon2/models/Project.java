package dhbw.ka.mwi.businesshorizon2.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.NavigableSet;
import java.util.SortedSet;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;

import dhbw.ka.mwi.businesshorizon2.methods.AbstractStochasticMethod;
import dhbw.ka.mwi.businesshorizon2.models.Period.PeriodInterface;

/**
 * Bei dieser Klasse handelt es sich um eine Art Container-Objekt. Dieses Objekt
 * wird via Spring einmal pro Session erstellt und bleibt somit bestehen. Sollte
 * z.B. vorher gespeicherte Daten geladen werden, dann muessen diese an dieses
 * Objekt uebergeben werden (d.h. es ist nicht moeglich, dieses Objekt zu
 * ersetzen).
 * 
 * @author Christian Gahlert
 * 
 */
public class Project implements Serializable {

	private static final long serialVersionUID = 1L;

	protected TreeSet<? extends PeriodInterface> periods = new TreeSet<>();

	protected Date lastChanged;

	protected String name;

	protected int periodsToForecast;
	protected int relevantPastPeriods;
	protected int iterations;
	protected int basisYear;
	protected ProjectInputType projectInputType;

	@Autowired
	private SortedSet<AbstractStochasticMethod> methods;	

	protected List<Szenario> scenarios = new ArrayList<Szenario>();


	/**
	 * Konstruktor des Projekts, mit dessen der Name gesetzt wird.
	 * 
	 * @author Christian Scherer
	 * @param Der
	 *            Name des Projekts
	 */
	public Project(String name) {
		this.name = name;
	}

	/**
	 * Standardkonstruktor des Projekt
	 * 
	 * @author Christian Scherer
	 */


	public Project() {
		this.projectInputType = new ProjectInputType();


	}

	/**
	 * Gibt die Perioden in einem sortierten NavigableSet zurueck.
	 * 
	 * @author Christian Gahlert
	 * @return Die Perioden
	 */
	public NavigableSet<? extends PeriodInterface> getPeriods() {
		return periods;
	}

	/**
	 * Ueberschreibt die bisher verwendeten Methoden. Die Perioden muessen in
	 * Form eines sortierten NavigableSet vorliegen.
	 * 
	 * @param periods
	 *            Die Perioden
	 */
	public void setPeriods(TreeSet<? extends PeriodInterface> periods) {
		this.periods = periods;
	}

	/**
	 * Diese Methode soll lediglich eine Liste von verfuegbaren Jahren
	 * zurueckgeben. Ein Jahr ist verfuegbar, wenn noch keine Periode fuer das
	 * Jahr existiert. Es wird beim aktuellen Jahr begonnen und dann
	 * schrittweise ein Jahr runtergezaehlt. Dabei sollte z.B. nach 10 oder 20
	 * Jahren schluss sein (mehr Jahre wird wohl niemand eingeben wollen).
	 * 
	 * @author Christian Gahlert
	 * @return Die verfuegbaren Jahre absteigend sortiert
	 */
	public List<Integer> getAvailableYears() {
		ArrayList<Integer> years = new ArrayList<Integer>();

		int start = Calendar.getInstance().get(Calendar.YEAR);
		boolean contains;

		for (int i = start; i > start - 5; i--) {
			contains = false;

			for (PeriodInterface period : periods) {
				if (period.getYear() == i) {
					contains = true;
					break;
				}
			}

			if (!contains) {
				years.add(i);
			}
		}

		return years;
	}

	/**
	 * Liesst das Datum der letzten Bearbeitung aus. Wird benötigt für die
	 * Anwenderinformation auf dem Auswahl-screen für Projekte.
	 * 
	 * @author Christian Scherer
	 * @return Datum der letzten Aenderung
	 */
	public Date getLastChanged() {
		return lastChanged;
	}

	/**
	 * Wird aufgerufen, wenn Aenderungen am Projekt vorgenommen wurden und
	 * aktualisiert dann das bisherige Aenderungsdatum.
	 * 
	 * @author Christian Scherer
	 * @param heutiges
	 *            Datum (Aenderungszeitpunkt)
	 */
	public void setLastChanged(Date lastChanged) {
		this.lastChanged = lastChanged;
	}

	/**
	 * Gibt den Namen des Projekts zurück.
	 * 
	 * @author Christian Scherer
	 * @return Name des Projekts
	 */
	public String getName() {
		return name;
	}

	public SortedSet<AbstractStochasticMethod> getMethods() {
		return methods;
	}

	public ProjectInputType getProjectInputType() {
		return projectInputType;
	}


	/**
	 * Setzt den Namen des Projekts.
	 * 
	 * @author Christian Scherer
	 * @param name
	 *            Name des Projekts
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gibt die Anzahl vorherzusagender Perioden des Projekts zurück.
	 * 
	 * @author Christian Scherer
	 * @return Anzahl vorherzusagender Perioden
	 */
	public int getPeriodsToForecast() {
		return periodsToForecast;
	}

	/**
	 * Setzt die Anzahl vorherzusagender Perioden des Projekts.
	 * 
	 * @author Christian Scherer
	 * @param periodsToForecast
	 *            Anzahl vorherzusagender Perioden
	 */
	public void setPeriodsToForecast(int periodsToForecast) {
		this.periodsToForecast = periodsToForecast;
	}

	/**
	 * Setzt die Anzahl der vergangenen relevanten Perioden des Projekts.
	 * 
	 * @author Christian Scherer
	 * @param relevantPastPeriods
	 *            Anzahl der vergangenen relevanten Perioden
	 */
	public void setRelevantPastPeriods(int relevantPastPeriods) {
		this.relevantPastPeriods = relevantPastPeriods;
	}

	/**
	 * Gibt die Anzahl der vergangenen relevanten Perioden des Projekts zurück.
	 * 
	 * @author Christian Scherer
	 * @return Anzahl der vergangenen relevanten Perioden
	 */
	public int getRelevantPastPeriods() {
		return relevantPastPeriods;
	}

	/**
	 * Gibt die Anzahl der Wiederholungen fuer die Zeitreihenanalyse des
	 * Projekts zurück.
	 * 
	 * @author Christian Scherer
	 * @return Anzahl der Wiederholungen fuer die Zeitreihenanalyse
	 */
	public int getIterations() {
		return iterations;
	}

	/**
	 * Setzt die Anzahl der Wiederholungen fuer die Zeitreihenanalyse des
	 * Projekts.
	 * 
	 * @author Christian Scherer
	 * @param iterations
	 *            Anzahl der Wiederholungen fuer die Zeitreihenanalyse
	 */
	public void setIterations(int iterations) {
		this.iterations = iterations;
	}

	/**
	 * Setzt Basis-Jahr des Projekts auf das die Cashflows abgezinst werden
	 * muessen.
	 * 
	 * @author Christian Scherer
	 * @param basisYear
	 *            Basis-Jahr des Projekts auf das die Cashflows abgezinst werden
	 *            muessen
	 */
	public void setBasisYear(int basisYear) {
		this.basisYear = basisYear;
	}

	/**
	 * Gibt Basis-Jahr des Projekts auf das die Cashflows abgezinst werden
	 * muessen zurück.
	 * 
	 * @author Christian Scherer
	 * @return Basis-Jahr des Projekts auf das die Cashflows abgezinst werden
	 *         muessen
	 */
	public int getBasisYear() {
		return basisYear;
	}

	public List<Szenario> getScenarios() {
		return this.scenarios;
	}

	public void addScenario(Szenario scenario) {
		this.scenarios.add(scenario);

	}
}