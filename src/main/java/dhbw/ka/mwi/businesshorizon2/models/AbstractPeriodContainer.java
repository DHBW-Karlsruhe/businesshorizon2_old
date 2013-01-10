package dhbw.ka.mwi.businesshorizon2.models;

import java.util.TreeSet;

/**
 * Diese Klasse stellt die Oberklasse aller PeriodenContainer da und stellt die
 * Methode getPeriods zur Verfügung, um auf die Perioden zuzugreufen. Das Set
 * der Perioden wird initialisiert und kann mit getPeriods().add() verwendet
 * werden.
 * 
 * @author Kai Westerholz
 * 
 */

abstract public class AbstractPeriodContainer {

	private final TreeSet<? extends PeriodInterface> perioden = new TreeSet<>();

	/**
	 * Diese Methode liefer die Referenz auf das Set der Perioden zurück.
	 * 
	 * @return SortedSet der Perioden
	 * @author Kai Westerholz
	 */
	public TreeSet<? extends PeriodInterface> getPeriods() {
		return perioden;
	}

}
