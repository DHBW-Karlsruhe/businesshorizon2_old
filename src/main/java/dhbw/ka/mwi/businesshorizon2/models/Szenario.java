package dhbw.ka.mwi.businesshorizon2.models;

public class Szenario {
	private double rateReturnEquity;
	
	private double rateReturnCapitalStock;
	
	private double businessTax;
	
	private double corporateAndSolitaryTax;
	
	private boolean includeInCalculation;

	public Szenario(double rateReturnEquity, double rateReturnCapitalStock,
			double businessTax, double corporateAndSolitaryTax, boolean includeInCalculation) {
		super();
		this.rateReturnEquity = rateReturnEquity;
		this.rateReturnCapitalStock = rateReturnCapitalStock;
		this.businessTax = businessTax;
		this.corporateAndSolitaryTax = corporateAndSolitaryTax;
		this.includeInCalculation = includeInCalculation;
	}
	
	public Szenario() {
		super();
	}

	public double getRateReturnEquity() {
		return rateReturnEquity;
	}

	public void setRateReturnEquity(double rateReturnEquity) {
		this.rateReturnEquity = rateReturnEquity;
	}

	public double getRateReturnCapitalStock() {
		return rateReturnCapitalStock;
	}

	public void setRateReturnCapitalStock(double rateReturnCapitalStock) {
		this.rateReturnCapitalStock = rateReturnCapitalStock;
	}

	public double getBusinessTax() {
		return businessTax;
	}

	public void setBusinessTax(double businessTax) {
		this.businessTax = businessTax;
	}

	public double getCorporateAndSolitaryTax() {
		return corporateAndSolitaryTax;
	}

	public void setCorporateAndSolitaryTax(double corporateAndSolitaryTax) {
		this.corporateAndSolitaryTax = corporateAndSolitaryTax;
	}

	public boolean isIncludeInCalculation() {
		return includeInCalculation;
	}

	public void setIncludeInCalculation(boolean includeInCalculation) {
		this.includeInCalculation = includeInCalculation;
	}

}
