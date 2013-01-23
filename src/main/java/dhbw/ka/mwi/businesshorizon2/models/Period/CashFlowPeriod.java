package dhbw.ka.mwi.businesshorizon2.models.Period;

/**
 * Diese Klasse bildet eine Periode ab. Das Jahr muss übergeben werden und kann
 * nicht mehr bewertet werden. Die Perioden enthalten nur CashFlows.
 * 
 * @author Kai Westerholz
 * 
 */

public class CashFlowPeriod extends Period {

	private static final long serialVersionUID = 1L;

	public CashFlowPeriod(int year) {
		super(year);
	}
}
