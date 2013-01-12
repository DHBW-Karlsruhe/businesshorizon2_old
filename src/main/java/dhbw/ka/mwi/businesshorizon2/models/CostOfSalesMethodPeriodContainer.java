package dhbw.ka.mwi.businesshorizon2.models;

import java.util.TreeSet;

/**
 * Diese Klasse stellt den Container für die Perioden zur Verfügung. Die
 * Bilanzinhalte der Perioden sind nach dem Umsazukostenverfahren bewertet.
 * 
 * @author Kai Westerholz
 * 
 */

public class CostOfSalesMethodPeriodContainer extends AbstractPeriodContainer {

	public CostOfSalesMethodPeriodContainer() {
		super();
	}

	@Override
	public TreeSet<CostOfSalesMethodPeriod> getPeriods() {
		return (TreeSet<CostOfSalesMethodPeriod>) super.getPeriods();
	}

}
