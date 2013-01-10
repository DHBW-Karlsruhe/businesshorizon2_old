package dhbw.ka.mwi.businesshorizon2.models;

import java.util.TreeSet;

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
	public TreeSet<AggregateCostMethodPeriod> getPeriods() {
		return (TreeSet<AggregateCostMethodPeriod>) super.getPeriods();
	}

}
