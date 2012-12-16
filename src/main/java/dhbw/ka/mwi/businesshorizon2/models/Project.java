package dhbw.ka.mwi.businesshorizon2.models;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.NavigableSet;
import java.util.TreeSet;

/**
 * Bei dieser Klasse handelt es sich um eine Art Container-Objekt. Dieses Objekt wird via
 * Spring einmal pro Session erstellt und bleibt somit bestehen. Sollte z.B. vorher
 * gespeicherte Daten geladen werden, dann muessen diese an dieses Objekt uebergeben
 * werden (d.h. es ist nicht moeglich, dieses Objekt zu ersetzen).
 * 
 * @author Christian Gahlert
 *
 */
public class Project {

	protected NavigableSet<Period> periods = new TreeSet<Period>();

	/**
	 * Gibt die Perioden in einem sortierten NavigableSet zurueck.
	 * 
	 * @author Christian Gahlert
	 * @return Die Perioden
	 */
	public NavigableSet<Period> getPeriods() {
		return periods;
	}

	/**
	 * Ueberschreibt die bisher verwendeten Methoden. Die Perioden muessen in Form
	 * eines sortierten NavigableSet vorliegen.
	 * 
	 * @param periods Die Perioden
	 */
	public void setPeriods(NavigableSet<Period> periods) {
		this.periods = periods;
	}
	
	/**
	 * Diese Methode soll lediglich eine Liste von verfuegbaren Jahren zurueckgeben. 
	 * Ein Jahr ist verfuegbar, wenn noch keine Periode fuer das Jahr existiert.
	 * Es wird beim aktuellen Jahr begonnen und dann schrittweise ein Jahr 
	 * runtergezaehlt. Dabei sollte z.B. nach 10 oder 20 Jahren schluss sein (mehr 
	 * Jahre wird wohl niemand eingeben wollen).
	 * 
	 * @author Christian Gahlert
	 * @return Die verfuegbaren Jahre absteigend sortiert
	 */
	public List<Integer> getAvailableYears() {
		ArrayList<Integer> years = new ArrayList<Integer>();
		
		int start = Calendar.getInstance().get(Calendar.YEAR);
		boolean contains;
		
		for(int i = start; i > start - 5; i--) {
			contains = false;
			
			for(Period period : periods) {
				if(period.getYear() == i) {
					contains = true;
					break;
				}
			}
			
			if(!contains) {
				years.add(i);
			}
		}
		
		return years;
	}
	
}
