package dhbw.ka.mwi.businesshorizon2.models;

public class CashFlowPeriod extends Period {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CashFlowPeriod(int year) {
		this.year = year;
		// TODO Auto-generated constructor stub
	}

	@Override
	public double getCashflow() {
		// TODO Auto-generated method stub
		return this.cashFlow;
	}

	@Override
	public double getWert() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	int getYear() {
		// TODO Auto-generated method stub
		return 0;
	}

}
