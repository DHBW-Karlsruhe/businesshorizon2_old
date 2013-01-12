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
import dhbw.ka.mwi.businesshorizon2.models.ProjectInputType;

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

	protected NavigableSet<PeriodInterface> periods = new TreeSet<PeriodInterface>();

	protected Date lastChanged;

	protected String name;
	
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
	
	public Project(){
		this.projectInputType = new ProjectInputType();

	}

	/**
	 * Gibt die Perioden in einem sortierten NavigableSet zurueck.
	 * 
	 * @author Christian Gahlert
	 * @return Die Perioden
	 */
	public NavigableSet<PeriodInterface> getPeriods() {
		return periods;
	}

	/**
	 * Ueberschreibt die bisher verwendeten Methoden. Die Perioden muessen in
	 * Form eines sortierten NavigableSet vorliegen.
	 * 
	 * @param periods
	 *            Die Perioden
	 */
	public void setPeriods(NavigableSet<PeriodInterface> periods) {
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
	
	public List<Szenario> getScenarios() {
		return this.scenarios;
	}
	
	public void addScenario(Szenario scenario) {
		this.scenarios.add(scenario);

	}
}
