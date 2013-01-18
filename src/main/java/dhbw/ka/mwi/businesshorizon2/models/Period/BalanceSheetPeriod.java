package dhbw.ka.mwi.businesshorizon2.models.Period;


/**
 * Diese Klasse bietet die Oberklasse der Bilanzperioden und beinhaltet die vom
 * Bewertungsverfahren unabhängigen Attribute
 * 
 * @author Kai Westerholz
 * 
 */

abstract class BalanceSheetPeriod implements PeriodInterface {

	private double immaterialFortune; // Immaterielle Vermögensgegenstände
	private double propertyValue; // Sachanlagen
	private double financialValue; // Finanzlagen
	private double equity; // Eigenkapital
	private double provisions; // Rückstellungen
	private double suplies; // Vorräte
	private double claims;// Forderungen und sontige Vermögensgegenstände
	private double stocks;// Wertpapiere;
	private double cashAssets;// Kassenbestand,Bundesbankguthaben, Guthaben bei
								// Kreditinstituten
	private double borrowedCapital; // Fremdkapital

	public double getBorrowedCapital() {
		return borrowedCapital;
	}

	public void setBorrowedCapital(double borrowedCapital) {
		this.borrowedCapital = borrowedCapital;
	}

	public double getImmaterialFortune() {
		return immaterialFortune;
	}

	public void setImmaterialFortune(double immaterialFortune) {
		this.immaterialFortune = immaterialFortune;
	}

	public double getPropertyValue() {
		return propertyValue;
	}

	public void setPropertyValue(double propertyValue) {
		this.propertyValue = propertyValue;
	}

	public double getFinancialValue() {
		return financialValue;
	}

	public void setFinancialValue(double financialValue) {
		this.financialValue = financialValue;
	}

	public double getEquity() {
		return equity;
	}

	public void setEquity(double equity) {
		this.equity = equity;
	}

	public double getProvisions() {
		return provisions;
	}

	public void setProvisions(double provisions) {
		this.provisions = provisions;
	}

	public double getSuplies() {
		return suplies;
	}

	public void setSuplies(double suplies) {
		this.suplies = suplies;
	}

	public double getClaims() {
		return claims;
	}

	public void setClaims(double claims) {
		this.claims = claims;
	}

	public double getStocks() {
		return stocks;
	}

	public void setStocks(double stocks) {
		this.stocks = stocks;
	}

	public double getCashAssets() {
		return cashAssets;
	}

	public void setCashAssets(double cashAssets) {
		this.cashAssets = cashAssets;
	}

}
