package dhbw.ka.mwi.businesshorizon2.models.Period;


/**
 * Diese Klasse bildet eine Periode ab. Das Jahr muss übergeben werden und kann
 * nicht mehr bewertet werden. Die Perioden enthalten nur CashFlows.
 * 
 * @author Kai Westerholz
 * 
 */

public class CashFlowPeriod implements PeriodInterface {

	private final int year;
	private double freeCashFlow;
	private double borrowedCapital;

	public CashFlowPeriod(int year) {
		this.year = year;
	}

	@Override
	public double getFreeCashFlow() {
		return this.freeCashFlow;
	}

	/**
	 * 
	 * @param CF
	 *            Wert des CashFlows
	 * @author Kai Westerholz
	 */
	public void setFreeCashFlow(double CF) {
		this.freeCashFlow = CF;
	}

	@Override
	public int getYear() {
		return this.year;
	}

	@Override
	public double getBorrowedCapital() {
		return this.borrowedCapital;
	}

	@Override
	public void setBorrowedCapital(double value) {
		this.borrowedCapital = value;

	}

	@Override
	public int compareTo(PeriodInterface o) {
		if (this.getYear() < o.getYear()) {
			return -1;
		} else if (this.getYear() == o.getYear()) {
			return 0;
		} else {
			return 1;
		}
	}

}
