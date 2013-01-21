package dhbw.ka.mwi.businesshorizon2.models.PeriodContainer;

import java.util.TreeSet;

import dhbw.ka.mwi.businesshorizon2.models.Period.Period;

/**
 * Diese Klasse stellt die Oberklasse aller PeriodenContainer da und stellt die
 * Methode getPeriods zur Verfügung, um auf die Perioden zuzugreufen. Das Set
 * der Perioden wird initialisiert und kann mit getPeriods().add() verwendet
 * werden.
 * 
 * @author Kai Westerholz
 * 
 */

abstract public class AbstractPeriodContainer implements
		Comparable<AbstractPeriodContainer> {

	private final TreeSet<Period> perioden;
	private static int counter = 0;
	private final int myCount;

	public AbstractPeriodContainer() {
		this.perioden = new TreeSet<>();
		this.myCount = counter;
		counter++;
	}

	public void addPeriod(Period period) {
		this.perioden.add(period);
	}

	/**
	 * Diese Methode liefer die Referenz auf das Set der Perioden zurück.
	 * 
	 * @return SortedSet der Perioden
	 * @author Kai Westerholz
	 */
	public TreeSet<? extends Period> getPeriods() {
		return perioden;
	}

	public int getCounter() {
		return this.myCount;
	}

	@Override
	public int compareTo(AbstractPeriodContainer o) {
		if (this.myCount < o.getCounter()) {
			return -1;
		} else if (this.myCount == o.getCounter()) {
			return 0;
		} else {
			return 1;
		}
	}

}
