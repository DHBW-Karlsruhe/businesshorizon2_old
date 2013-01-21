package dhbw.ka.mwi.businesshorizon2.models.Period;

/**
 * Diese Klasse bietet die Oberklasse der Bilanzperioden und beinhaltet die vom
 * Bewertungsverfahren unabhängigen Attribute
 * 
 * @author Kai Westerholz
 * 
 */

abstract class BalanceSheetPeriod extends Period {

	private static final long serialVersionUID = 1L;

	/**
	 * Immaterielle Vermögensgegenstände
	 */
	private double immaterialFortune;

	/**
	 * Sachanlagen
	 */
	private double propertyValue;

	/**
	 * Finanzanlagen
	 */
	private double financialValue;

	/**
	 * Eigenkapital
	 */
	private double equity;

	/**
	 * Rückstellungen
	 */
	private double provisions;

	/**
	 * Vorräte
	 */
	private double suplies;

	/**
	 * Forderungen und sonstige Vermögensgegenstände
	 */
	private double claims;

	/**
	 * Wertpapiere
	 */
	private double stocks;

	/**
	 * Kassenbestand, Bundesbankguthaben, Guthaben bei Kreditinstituten
	 */
	private double cashAssets;

	public BalanceSheetPeriod(int year) {
		super(year);
	}

	/**
	 * Gebe die 'Immateriellen Vermögensgegenstände' aus der Bilanz zurück.
	 * 
	 * @return Immaterielle Vermögensgegenstände
	 */
	public double getImmaterialFortune() {
		return immaterialFortune;
	}

	/**
	 * Setze die 'Immateriellen Vermögensgegenstände' in der Bilanz.
	 * 
	 * @param immaterialFortune
	 *            Immaterielle Vermögensgegenstände
	 */
	public void setImmaterialFortune(double immaterialFortune) {
		this.immaterialFortune = immaterialFortune;
	}

	/**
	 * Gebe die 'Sachanlagen' aus der Bilanz zurück.
	 * 
	 * @return Sachanlagen
	 */
	public double getPropertyValue() {
		return propertyValue;
	}

	/**
	 * Setze die 'Sachanlagen' in der Bilanz.
	 * 
	 * @param propertyValue
	 *            Sachanlagen
	 */
	public void setPropertyValue(double propertyValue) {
		this.propertyValue = propertyValue;
	}

	/**
	 * Gebe die 'Finanzanlagen' aus der Bilanz zurück.
	 * 
	 * @return Finanzanlagen
	 */
	public double getFinancialValue() {
		return financialValue;
	}

	/**
	 * Setze die 'Finanzanlagen' in der Bilanz
	 * 
	 * @param financialValue
	 *            Finanzanlagen
	 */
	public void setFinancialValue(double financialValue) {
		this.financialValue = financialValue;
	}

	/**
	 * Gebe das 'Eigenkapital' aus der Bilanz zurück.
	 * 
	 * @return Eigenkapital
	 */
	public double getEquity() {
		return equity;
	}

	/**
	 * Setze das 'Eigenkapital' in der Bilanz.
	 * 
	 * @param equity
	 *            Eigenkapital
	 */
	public void setEquity(double equity) {
		this.equity = equity;
	}

	/**
	 * Gebe die 'Rückstellungen' aus der Bilanz zurück.
	 * 
	 * @return Rückstellungen
	 */
	public double getProvisions() {
		return provisions;
	}

	/**
	 * Setze die 'Rückstellungen' in der Bilanz.
	 * 
	 * @param provisions
	 *            Rückstellungen
	 */
	public void setProvisions(double provisions) {
		this.provisions = provisions;
	}

	/**
	 * Gebe die 'Vorräte' aus der Bilanz zurück.
	 * 
	 * @return Vorräte
	 */
	public double getSuplies() {
		return suplies;
	}

	/**
	 * Setze die 'Vorräte' in der Bilanz.
	 * 
	 * @param suplies
	 *            Vorräte
	 */
	public void setSuplies(double suplies) {
		this.suplies = suplies;
	}

	/**
	 * Gebe die 'Forderungen und sonstigen Vermögensgegenstände' aus der Bilanz
	 * zurück-
	 * 
	 * @return Forderungen und sonstige Vermögensgegenstände
	 */
	public double getClaims() {
		return claims;
	}

	/**
	 * Setze die 'Forderungen und sonstigen Vermögensgegenstände' in der Bilanz.
	 * 
	 * @param claims
	 *            Forderungen und sonstigen Vermögensgegenstände
	 */
	public void setClaims(double claims) {
		this.claims = claims;
	}

	/**
	 * Gebe die 'Wertpapiere' aus der Bilanz zurück.
	 * 
	 * @return Wertpapiere
	 */
	public double getStocks() {
		return stocks;
	}

	/**
	 * Setze die 'Wertpapiere' in der Bilanz.
	 * 
	 * @param stocks
	 *            Wertpapiere
	 */
	public void setStocks(double stocks) {
		this.stocks = stocks;
	}

	/**
	 * Gebe den 'Kassenbestand, Bundesbankguthaben, Guthaben bei
	 * Kreditinstituten' aus der Bilanz zurück.
	 * 
	 * @return Kassenbestand, Bundesbankguthaben, Guthaben bei Kreditinstituten
	 */
	public double getCashAssets() {
		return cashAssets;
	}

	/**
	 * Setze den 'Kassenbestand, Bundesbankguthaben, Guthaben bei
	 * Kreditinstituten' in der Bilanz.
	 * 
	 * @param cashAssets
	 *            Kassenbestand, Bundesbankguthaben, Guthaben bei
	 *            Kreditinstituten
	 */
	public void setCashAssets(double cashAssets) {
		this.cashAssets = cashAssets;
	}

}
