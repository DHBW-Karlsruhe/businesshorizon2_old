/*******************************************************************************
 * BusinessHorizon2
 *
 * Copyright (C) 
 * 2012-2013 Christian Gahlert, Florian Stier, Kai Westerholz,
 * Timo Belz, Daniel Dengler, Katharina Huber, Christian Scherer, Julius Hacker
 * 2013-2014 Marcel Rosenberger, Mirko Göpfrich, Annika Weis, Katharina Narlock, 
 * Volker Meier
 * 
 *
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/

package dhbw.ka.mwi.businesshorizon2.models;

import java.io.Serializable;


/**
 * The Class Szenario.
 */
public class Szenario implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -9003657837833197321L;

	/** The rate return equity. */
	private double rateReturnEquity;
	
	/** The rate return capital stock. */
	private double rateReturnCapitalStock;
	
	/** The business tax. */
	private double businessTax;
	
	/** The corporate and solitary tax. */
	private double corporateAndSolitaryTax;
	
	/** The include in calculation. */
	private boolean includeInCalculation;

	/**
	 * Instantiates a new szenario.
	 *
	 * @param rateReturnEquity the rate return equity
	 * @param rateReturnCapitalStock the rate return capital stock
	 * @param businessTax the business tax
	 * @param corporateAndSolitaryTax the corporate and solitary tax
	 * @param includeInCalculation the include in calculation
	 */
	public Szenario(double rateReturnEquity, double rateReturnCapitalStock,
			double businessTax, double corporateAndSolitaryTax, boolean includeInCalculation) {
		super();
		this.rateReturnEquity = rateReturnEquity;
		this.rateReturnCapitalStock = rateReturnCapitalStock;
		this.businessTax = businessTax;
		this.corporateAndSolitaryTax = corporateAndSolitaryTax;
		this.includeInCalculation = includeInCalculation;
	}
	
	/**
	 * Instantiates a new szenario.
	 */
	public Szenario() {
		super();
	}

	/**
	 * Gets the rate return equity.
	 *
	 * @return the rate return equity
	 */
	public double getRateReturnEquity() {
		return rateReturnEquity;
	}

	/**
	 * Sets the rate return equity.
	 *
	 * @param rateReturnEquity the new rate return equity
	 */
	public void setRateReturnEquity(double rateReturnEquity) {
		this.rateReturnEquity = rateReturnEquity;
	}

	/**
	 * Gets the rate return capital stock.
	 *
	 * @return the rate return capital stock
	 */
	public double getRateReturnCapitalStock() {
		return rateReturnCapitalStock;
	}

	/**
	 * Sets the rate return capital stock.
	 *
	 * @param rateReturnCapitalStock the new rate return capital stock
	 */
	public void setRateReturnCapitalStock(double rateReturnCapitalStock) {
		this.rateReturnCapitalStock = rateReturnCapitalStock;
	}

	/**
	 * Gets the business tax.
	 *
	 * @return the business tax
	 */
	public double getBusinessTax() {
		return businessTax;
	}

	/**
	 * Sets the business tax.
	 *
	 * @param businessTax the new business tax
	 */
	public void setBusinessTax(double businessTax) {
		this.businessTax = businessTax;
	}

	/**
	 * Gets the corporate and solitary tax.
	 *
	 * @return the corporate and solitary tax
	 */
	public double getCorporateAndSolitaryTax() {
		return corporateAndSolitaryTax;
	}

	/**
	 * Sets the corporate and solitary tax.
	 *
	 * @param corporateAndSolitaryTax the new corporate and solitary tax
	 */
	public void setCorporateAndSolitaryTax(double corporateAndSolitaryTax) {
		this.corporateAndSolitaryTax = corporateAndSolitaryTax;
	}

	/**
	 * Checks if is include in calculation.
	 *
	 * @return true, if is include in calculation
	 */
	public boolean isIncludeInCalculation() {
		return includeInCalculation;
	}

	/**
	 * Sets the include in calculation.
	 *
	 * @param includeInCalculation the new include in calculation
	 */
	public void setIncludeInCalculation(boolean includeInCalculation) {
		this.includeInCalculation = includeInCalculation;
	}

}
