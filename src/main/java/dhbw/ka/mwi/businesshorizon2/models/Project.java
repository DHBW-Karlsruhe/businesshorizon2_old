package dhbw.ka.mwi.businesshorizon2.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.NavigableSet;
import java.util.SortedSet;
import java.util.TreeSet;

import dhbw.ka.mwi.businesshorizon2.methods.AbstractStochasticMethod;
import dhbw.ka.mwi.businesshorizon2.models.Period.Period;
import dhbw.ka.mwi.businesshorizon2.models.PeriodContainer.AbstractPeriodContainer;

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

	protected TreeSet<? extends Period> periods = new TreeSet<>();

	private Date lastChanged;


	private String name;

	private AbstractPeriodContainer stochasticPeriods, deterministicPeriods;

	public AbstractPeriodContainer getStochasticPeriods() {
		return stochasticPeriods;
	}

	public void setStochasticPeriods(AbstractPeriodContainer stochasticPeriods) {
		this.stochasticPeriods = stochasticPeriods;
	}

	public AbstractPeriodContainer getDeterministicPeriods() {
		return deterministicPeriods;
	}

	public void setDeterministicPeriods(
			AbstractPeriodContainer deterministicPeriods) {
		this.deterministicPeriods = deterministicPeriods;
	}

	private double CashFlowProbabilityOfRise;
	private double CashFlowStepRange;
	private double BorrowedCapitalProbabilityOfRise;
	private double BorrowedCapitalStepRange;

	private int periodsToForecast;
	private int relevantPastPeriods;
	private int iterations;
	private int basisYear;
	private ProjectInputType projectInputType;

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
		this.projectInputType = new ProjectInputType();
		this.iterations = 10000;
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
	 * Gibt die Erhöhungswahrscheinlichkeit des CashFlows zurueck.
	 * 
	 * @author Kai Westerholz
	 * @return
	 */
	public double getCashFlowProbabilityOfRise() {
		return CashFlowProbabilityOfRise;
	}

	/**
	 * Setzt die Erhöhungswahrscheinlichkeit des CashFlows.
	 * 
	 * @author Kai Westerholz
	 * @param cashFlowProbabilityOfRise
	 */
	public void setCashFlowProbabilityOfRise(double cashFlowProbabilityOfRise) {
		CashFlowProbabilityOfRise = cashFlowProbabilityOfRise;
	}

	/**
	 * Gibt die Schrittweise des CashFlows zurueck.
	 * 
	 * @author Kai Westerholz
	 * @return
	 */
	public double getCashFlowStepRange() {
		return CashFlowStepRange;
	}

	/**
	 * Setzt die Schrittweise vom CashFlow.
	 * 
	 * @author Kai Westerholz
	 * @param cashFlowStepRange
	 */
	public void setCashFlowStepRange(double cashFlowStepRange) {
		CashFlowStepRange = cashFlowStepRange;
	}

	/**
	 * Gibt die Erhöhungswahrscheinlichkeit des CashFlows zurueck.
	 * 
	 * @author Kai Westerholz
	 * @return Erhöhungswahrscheinlichkeit CashFlow
	 */

	public double getBorrowedCapitalProbabilityOfRise() {
		return BorrowedCapitalProbabilityOfRise;
	}

	/**
	 * Setzt die Wahrscheinlichkeit der Erhoehung des Fremdkapitals.
	 * 
	 * @author Kai Westerholz
	 * 
	 * @param borrowedCapitalProbabilityOfRise
	 */
	public void setBorrowedCapitalProbabilityOfRise(double borrowedCapitalProbabilityOfRise) {
		BorrowedCapitalProbabilityOfRise = borrowedCapitalProbabilityOfRise;
	}

	/**
	 * Gibt die Schrittweite des Fremdkapitals an.
	 * 
	 * @author Kai Westerholz
	 * 
	 * @return Schrittweite
	 */
	public double getBorrowedCapitalStepRange() {
		return BorrowedCapitalStepRange;
	}

	/**
	 * Setzt die Schrittweite des Fremdkapitals.
	 * 
	 * @author Kai Westerholz
	 * @param borrowedCapitalStepRange
	 */
	public void setBorrowedCapitalStepRange(double borrowedCapitalStepRange) {
		BorrowedCapitalStepRange = borrowedCapitalStepRange;
	}

	/**
	 * Gibt die Perioden in einem sortierten NavigableSet zurueck.
	 * 
	 * @author Christian Gahlert
	 * @return Die Perioden
	 */
	public NavigableSet<? extends Period> getPeriods() {
		return periods;
	}


	/**
	 * @deprecated Bitte getter für die stochastiPeriods und DeterministicPeriods
	 *             verwenden Ueberschreibt die bisher verwendeten Methoden. Die
	 *             Perioden muessen in Form eines sortierten NavigableSet
	 *             vorliegen.
	 * 
	 * @param periods
	 *            Die Perioden
	 */
	@Deprecated
	public void setPeriods(TreeSet<? extends Period> periods) {
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

			for (Period period : periods) {
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

	public void setMethods(SortedSet<AbstractStochasticMethod> methods) {
		this.methods = methods;
	}

	public SortedSet<AbstractStochasticMethod> getMethods() {
		return methods;
	}

	public ProjectInputType getProjectInputType() {
		return projectInputType;
	}

	public void setProjectInputType(ProjectInputType projectInputType) {
		this.projectInputType = projectInputType;
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
