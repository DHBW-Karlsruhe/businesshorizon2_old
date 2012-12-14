package dhbw.ka.mwi.businesshorizon2.models;

import java.io.Serializable;

public class Period implements Serializable, Comparable<Period> {
	private static final long serialVersionUID = 1L;
	
	protected int year;
	
	protected double cashFlow;
	
	protected double companyValue;

	public Period(int year) {
		this.year = year;
	}

	public int getYear() {
		return year;
	}

	@Override
	public String toString() {
		return String.valueOf(getYear());
	}

	@Override
	public int compareTo(Period o) {
		return o.getYear() - this.getYear();
	}

	public double getCashFlow() {
		return cashFlow;
	}

	public void setCashFlow(double cashFlow) {
		this.cashFlow = cashFlow;
	}

	public double getCompanyValue() {
		return companyValue;
	}

	public void setCompanyValue(double companyValue) {
		this.companyValue = companyValue;
	}
	
}
