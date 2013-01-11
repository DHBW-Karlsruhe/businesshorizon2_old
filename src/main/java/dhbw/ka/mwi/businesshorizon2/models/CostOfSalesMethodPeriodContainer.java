package dhbw.ka.mwi.businesshorizon2.models;

import java.util.SortedSet;

/**
 * Diese Klasse stellt den Container für die Perioden zur Verfügung. Die
 * Bilanzinhalte der Perioden sind nach dem Umsazukostenverfahren bewertet.
 * 
 * @author Kai Westerholz
 * 
 */

public class CostOfSalesMethodPeriodContainer extends AbstractPeriodContainer {

	@Override
	public SortedSet<CostOfSalesMethodPeriod> getPeriods() {
		return (SortedSet<CostOfSalesMethodPeriod>) super.getPeriods();
	}

}
