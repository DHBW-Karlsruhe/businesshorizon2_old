package dhbw.ka.mwi.businesshorizon2.models;

import java.util.TreeSet;

import dhbw.ka.mwi.businesshorizon2.models.PeriodContainer.AbstractPeriodContainer;

/**
 * Diese Klasse stellt ein Ergebnis einer Stochastischen Methode zur Verfügung.
 * Dies bedeutet, dass sie eine Liste von PeriodenContainer enthaelt.
 * 
 * @author Kai Westerholz
 * 
 */

public class StochasticResultContainer {
	TreeSet<? extends AbstractPeriodContainer> periodContainer = new TreeSet<>();

	public StochasticResultContainer(
			TreeSet<? extends AbstractPeriodContainer> tree) {
		periodContainer = tree;
	}

	/**
	 * Diese Methode liefert die Referenz auf die Liste der Container zurueck.
	 * 
	 * @return Referenz auf Liste der Container
	 * @author Kai Westerholz
	 */

	public TreeSet<? extends AbstractPeriodContainer> getPeriodContainers() {
		return this.periodContainer;
	}

}
