package dhbw.ka.mwi.businesshorizon2.models.PeriodContainer;

import java.util.TreeSet;

import dhbw.ka.mwi.businesshorizon2.models.Period.AggregateCostMethodPeriod;

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

	public AggregateCostMethodBalanceSheetPeriodContainer() {
		super();
	}

	@Override
	public TreeSet<AggregateCostMethodPeriod> getPeriods() {
		return (TreeSet<AggregateCostMethodPeriod>) super.getPeriods();
	}

}
