package dhbw.ka.mwi.businesshorizon2.models;

import java.util.SortedSet;

/**
 * Diese Klasse stellt einen Container für Perioden zu verfügung. Die
 * enthaltenen Bilanzposten der Periode sind nach dem Umsatzkostenverfahren
 * bewertet.
 * 
 * 
 * @author Kai Westerholz
 * 
 */

public class AggregateCostMethodBalanceSheetPeriodContainer extends
		AbstractPeriodContainer {

	@Override
	public SortedSet<AggregateCostMethodPeriod> getPeriods() {
		return (SortedSet<AggregateCostMethodPeriod>) super.getPeriods();
	}

}
