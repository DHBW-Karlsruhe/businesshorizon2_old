package dhbw.ka.mwi.businesshorizon2.models;

public class CashFlowPeriod extends Period {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CashFlowPeriod(int year) {
		this.year = year;
	}

	@Override
	public double getCashflow() {
		return this.cashFlow;
	}

	@Override
	public double getWert() {
		return this.cashFlow;
	}

	@Override
	public int getYear() {
		return this.year;
	}

}
