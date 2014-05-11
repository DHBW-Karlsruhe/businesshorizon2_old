/*******************************************************************************
 * /*******************************************************************************
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

package dhbw.ka.mwi.businesshorizon2.models.CompanyValue;

import java.util.NavigableMap;
import java.util.TreeMap;

import dhbw.ka.mwi.businesshorizon2.models.Szenario;

public class CompanyValueDeterministic extends CompanyValue {

	public class Couple {

		private final int year;
		private final double companyValue;
		private final double capitalStock;
		private final double freeCashFlow;
		private final double freeCashFlowT;
		private final double debitFreeCompanyT;
		private final double taxBenefitsT;
		private final double s;
		private final Szenario szenario;

		/**
		 * 
		 * @param year
		 *            Jahr
		 * @param companyValue
		 *            Unternehmenswert zum Zeitpunkt t
		 * @param capitalStock
		 *            Fremdkapital zum Zeitpunkt t
		 * @param freeCashFlow
		 *            Cashflow zum Zeitpunkt t
		 * @param freeCashFlowT
		 *            Cashflow zum Zeitpunkt t+1
		 * @param debitFreeCompanyT
		 *            Wert des unverschuldeten Unternhemens zum Zeitpunkt t+1
		 * @param taxBenefitsT
		 *            Wert der Steuervorteile zum Zeitpunkt t+1
		 * @param s
		 * @param szenario
		 */
		public Couple(int year, double companyValue, double capitalStock,
				double freeCashFlow, double freeCashFlowT,
				double debitFreeCompanyT, double taxBenefitsT, double s,
				Szenario szenario) {

			this.year = year;
			this.companyValue = companyValue;
			this.capitalStock = capitalStock;
			this.freeCashFlow = freeCashFlow;
			this.freeCashFlowT = freeCashFlowT;
			this.debitFreeCompanyT = debitFreeCompanyT;
			this.taxBenefitsT = taxBenefitsT;
			this.s = s;
			this.szenario = szenario;
		}

		public int getYear() {
			return year;
		}

		public double getCompanyValue() {
			return companyValue;
		}

		public double getCapitalStock() {
			return capitalStock;
		}

		public double getFreeCashFlow() {
			return freeCashFlow;
		}

		public double getFreeCashFlowT() {
			return freeCashFlowT;
		}

		public double getDebitFreeCompany() {
			return debitFreeCompanyT;
		}

		public double getTaxBenefits() {
			return taxBenefitsT;
		}

		public double getS() {
			return s;
		}

		public Szenario getSzenario() {
			return szenario;
		}

		@Override
		public String toString() {
			return "Year: " + year + " CompanyValue: " + companyValue
					+ " FreeCashFlow: " + freeCashFlow + " FreeCashFlowT: "
					+ freeCashFlowT + " CapitalStock: " + capitalStock
					+ " DebitFreeCompanyT: " + debitFreeCompanyT
					+ " TaxBenefitsT: " + taxBenefitsT + " s: " + s;
		}
	}

	private final TreeMap<Integer, Couple> map;

	public CompanyValueDeterministic() {
		super();
		map = new TreeMap<>();
	}

	/**
	 * 
	 * @param year
	 * @param companyValue
	 * @param capitalStock
	 * @param freeCashFlow
	 * @param freeCashFlowT
	 * @param debitFreeCompany
	 * @param taxBenefits
	 * @param s
	 * @param szenario
	 */
	public void addPeriod(int year, double companyValue, double capitalStock,
			double freeCashFlow, double freeCashFlowT, double debitFreeCompany,
			double taxBenefits, double s, Szenario szenario) {

		if (map.containsKey(year)) {
			// TODO Exception werfen
		} else {

			companyValue = roundToDecimalPlaces(companyValue,4,false);
			capitalStock = roundToDecimalPlaces(capitalStock,4,false);
			freeCashFlow = roundToDecimalPlaces(freeCashFlow,4,false);
			freeCashFlowT = roundToDecimalPlaces(freeCashFlowT,4,false);
			debitFreeCompany = roundToDecimalPlaces(debitFreeCompany,4,false);
			taxBenefits = roundToDecimalPlaces(taxBenefits,4,false);
			s = roundToDecimalPlaces(s,4,false);

			Couple couple = new Couple(year, companyValue, capitalStock,
					freeCashFlow, freeCashFlowT, debitFreeCompany, taxBenefits,
					s, szenario);
			map.put(year, couple);
		}
	}

	public NavigableMap<Integer, Couple> getCompanyValues() {
		return map.descendingMap();
	}
}
