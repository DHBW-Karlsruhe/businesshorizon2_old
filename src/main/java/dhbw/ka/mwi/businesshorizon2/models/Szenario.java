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

public class Szenario implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -9003657837833197321L;

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
