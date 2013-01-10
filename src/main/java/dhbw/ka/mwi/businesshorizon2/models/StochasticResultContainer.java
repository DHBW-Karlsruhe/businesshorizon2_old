package dhbw.ka.mwi.businesshorizon2.models;

import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Diese Klasse stellt ein Ergebnis einer Stochastischen Methode zur Verfügung.
 * Dies bedeutet, dass sie eine Liste von PeriodenContainer enthält.
 * 
 * @author Kai Westerholz
 * 
 */

public class StochasticResultContainer {
	SortedSet<? extends AbstractPeriodContainer> periodContainer = new TreeSet<>();

	/**
	 * Diese Methode liefert die Referenz auf die Liste der Container zurück.
	 * 
	 * @return Referenz auf Liste der Container
	 * @author Kai Westerholz
	 */

	public SortedSet<? extends AbstractPeriodContainer> getPeriodContainers() {
		return this.periodContainer;
	}

}
