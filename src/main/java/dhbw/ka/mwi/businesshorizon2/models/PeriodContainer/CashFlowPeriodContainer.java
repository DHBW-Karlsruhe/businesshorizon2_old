package dhbw.ka.mwi.businesshorizon2.models.PeriodContainer;

import java.util.TreeSet;

import dhbw.ka.mwi.businesshorizon2.models.Period.CashFlowPeriod;

/**
 * Diese Klasse stellt einen Zeitreihe von CashFlow Perioden dar.
 * 
 * @author Kai Westerholz
 * 
 */

public class CashFlowPeriodContainer extends AbstractPeriodContainer {

	public CashFlowPeriodContainer() {
		super();
	}

	@Override
	public TreeSet<CashFlowPeriod> getPeriods() {
		// TODO Auto-generated method stub
		return (TreeSet<CashFlowPeriod>) super.getPeriods();
	}

}
