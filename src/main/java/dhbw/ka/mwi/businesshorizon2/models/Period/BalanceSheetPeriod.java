/*******************************************************************************
 * BusinessHorizon2
 * 
 *     Copyright (C) 2012-2013  Christian Gahlert, Florian Stier, Kai Westerholz,
 *     Timo Belz, Daniel Dengler, Katharina Huber, Christian Scherer, Julius Hacker
 * 
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Affero General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 * 
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Affero General Public License for more details.
 * 
 *     You should have received a copy of the GNU Affero General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/


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
	boolean immaterialFortuneSet;

	/**
	 * Sachanlagen
	 */
	private double propertyValue;
	boolean propertyValueSet;

	/**
	 * Finanzanlagen
	 */
	private double financialValue;
	boolean financialValueSet;

	/**
	 * Eigenkapital
	 */
	private double equity;
	boolean equitySet;

	/**
	 * Rückstellungen
	 */
	private double provisions;
	boolean provisionsSet;

	/**
	 * Vorräte
	 */
	private double suplies;
	boolean supliesSet;

	/**
	 * Forderungen und sonstige Vermögensgegenstände
	 */
	private double claims;
	boolean claimsSet;

	/**
	 * Wertpapiere
	 */
	private double stocks;
	boolean stocksSet;

	/**
	 * Kassenbestand, Bundesbankguthaben, Guthaben bei Kreditinstituten
	 */
	private double cashAssets;
	boolean cashAssetsSet;

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
		immaterialFortuneSet=true;
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
		propertyValueSet =true;
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
		financialValueSet = true;
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
		equitySet = true;
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
		provisionsSet=true;
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
		supliesSet = true;
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
		claimsSet = true;
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
		stocksSet = true;
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
		cashAssetsSet=true;
	}

	/**
	 * @return the immaterialFortuneSet
	 */
	public boolean isImmaterialFortuneSet() {
		return immaterialFortuneSet;
	}

	/**
	 * @param immaterialFortuneSet the immaterialFortuneSet to set
	 */
	public void setImmaterialFortuneSet(boolean immaterialFortuneSet) {
		this.immaterialFortuneSet = immaterialFortuneSet;
	}

	/**
	 * @return the propertyValueSet
	 */
	public boolean isPropertyValueSet() {
		return propertyValueSet;
	}

	/**
	 * @param propertyValueSet the propertyValueSet to set
	 */
	public void setPropertyValueSet(boolean propertyValueSet) {
		this.propertyValueSet = propertyValueSet;
	}

	/**
	 * @return the financialValueSet
	 */
	public boolean isFinancialValueSet() {
		return financialValueSet;
	}

	/**
	 * @param financialValueSet the financialValueSet to set
	 */
	public void setFinancialValueSet(boolean financialValueSet) {
		this.financialValueSet = financialValueSet;
	}

	/**
	 * @return the equitySet
	 */
	public boolean isEquitySet() {
		return equitySet;
	}

	/**
	 * @param equitySet the equitySet to set
	 */
	public void setEquitySet(boolean equitySet) {
		this.equitySet = equitySet;
	}

	/**
	 * @return the provisionsSet
	 */
	public boolean isProvisionsSet() {
		return provisionsSet;
	}

	/**
	 * @param provisionsSet the provisionsSet to set
	 */
	public void setProvisionsSet(boolean provisionsSet) {
		this.provisionsSet = provisionsSet;
	}

	/**
	 * @return the supliesSet
	 */
	public boolean isSupliesSet() {
		return supliesSet;
	}

	/**
	 * @param supliesSet the supliesSet to set
	 */
	public void setSupliesSet(boolean supliesSet) {
		this.supliesSet = supliesSet;
	}

	/**
	 * @return the claimsSet
	 */
	public boolean isClaimsSet() {
		return claimsSet;
	}

	/**
	 * @param claimsSet the claimsSet to set
	 */
	public void setClaimsSet(boolean claimsSet) {
		this.claimsSet = claimsSet;
	}

	/**
	 * @return the stocksSet
	 */
	public boolean isStocksSet() {
		return stocksSet;
	}

	/**
	 * @param stocksSet the stocksSet to set
	 */
	public void setStocksSet(boolean stocksSet) {
		this.stocksSet = stocksSet;
	}

	/**
	 * @return the cashAssetsSet
	 */
	public boolean isCashAssetsSet() {
		return cashAssetsSet;
	}

	/**
	 * @param cashAssetsSet the cashAssetsSet to set
	 */
	public void setCashAssetsSet(boolean cashAssetsSet) {
		this.cashAssetsSet = cashAssetsSet;
	}

}
