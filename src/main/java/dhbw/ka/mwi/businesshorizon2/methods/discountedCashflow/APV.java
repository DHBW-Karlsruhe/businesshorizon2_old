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

package dhbw.ka.mwi.businesshorizon2.methods.discountedCashflow;

import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

import org.apache.log4j.Logger;

import dhbw.ka.mwi.businesshorizon2.methods.AbstractDeterministicMethod;
import dhbw.ka.mwi.businesshorizon2.methods.CallbackInterface;
import dhbw.ka.mwi.businesshorizon2.methods.DeterministicMethodException;
import dhbw.ka.mwi.businesshorizon2.models.DeterministicResultContainer;
import dhbw.ka.mwi.businesshorizon2.models.Project;
import dhbw.ka.mwi.businesshorizon2.models.Szenario;
import dhbw.ka.mwi.businesshorizon2.models.Period.CashFlowPeriod;
import dhbw.ka.mwi.businesshorizon2.models.Period.Period;
import dhbw.ka.mwi.businesshorizon2.models.PeriodContainer.AbstractPeriodContainer;
import dhbw.ka.mwi.businesshorizon2.models.PeriodContainer.CashFlowPeriodContainer;

/**
 * @author Annika Weis
 * @date 29.12.2013
 * 
 */
public class APV extends AbstractDeterministicMethod {

	/**
	 * 
	 */
	private static final Logger logger = Logger.getLogger("APV.class");
	private static final long serialVersionUID = 3577122037488635230L;
	private double uwsteuerfrei;
	private double steuervorteile;
	private double fremdkapital;

	@Override
	public String getName() {

		return "Adjusted-Present-Value (APV)";
	}

	@Override
	public int getOrderKey() {

		return 5;
	}

	@Override
	public Boolean getImplemented() {
		return true;
	}

	@Override
	public DeterministicResultContainer calculate(Project project,
			CallbackInterface callback) throws InterruptedException,
			DeterministicMethodException {
		TreeSet<CashFlowPeriodContainer> prognose = new TreeSet<CashFlowPeriodContainer>();
		DeterministicResultContainer drc = new DeterministicResultContainer(
				prognose);
		return drc;
	}

	/**
	 * @author Annika Weis
	 * @param double[] cashflow
	 * @param double[] fremdkapital,
	 * @param Szenario
	 *            szenario
	 * @return double unternehemnswert
	 */
	public double calculateValues(double[] cashflow, double[] fremdkapital,
			Szenario szenario) {

		double gk = 0;
		double v = 0;
		double unternehmenswert = 0;
		double sSteuersatz;
		double sKS;
		double sZinsen;
		double sEK;

		Double first_period_cashflow = null;
		Double first_period_fremdkapital = null;
		Double period_cashflow;
		Double period_fremdkapital;
		Double lastPeriod_cashflow = null;
		Double lastPeriod_fremdkapital = null;
		double jahr = 1;

		sKS = szenario.getCorporateAndSolitaryTax() / 100;
		sSteuersatz = 0.75 * szenario.getBusinessTax() / 100 + sKS;
		sEK = szenario.getRateReturnEquity() / 100;
		sZinsen = szenario.getRateReturnCapitalStock() / 100;

		for (int durchlauf = 0; durchlauf < cashflow.length; durchlauf++) {
			period_cashflow = cashflow[durchlauf];
			period_fremdkapital = fremdkapital[durchlauf];

			if (durchlauf == 0) { // Basisjahr
				first_period_cashflow = cashflow[durchlauf];
				first_period_fremdkapital = fremdkapital[durchlauf];
			}

//			} else if (durchlauf + 1 == cashflow.length) { // letztes Jahr wird
//															// nach der Schleife
//															// extra berechnet
//
//			} 
			else {
				gk += abzinsen(cashflow[durchlauf], sEK, durchlauf);
				v += (sSteuersatz * sZinsen * fremdkapital[durchlauf - 1])
						/ Math.pow(1 + sZinsen, durchlauf);

			}

			lastPeriod_cashflow = period_cashflow;
			lastPeriod_fremdkapital = period_fremdkapital;

			jahr = durchlauf;
		}

//		// Jahr -1, denn im letzten Durchlauf wird von der Schleife 1 addiert
//		jahr = jahr - 1;
//
//		// Berechnung des letzten Jahres
//		gk = gk + lastPeriod_cashflow / (sEK * Math.pow(1 + sEK, jahr));
//		v = v + (sSteuersatz * sZinsen * lastPeriod_fremdkapital)
//				/ (sZinsen * Math.pow(1 + sZinsen, jahr));
		
		// Unternehmenswert gesamt berechnen
		unternehmenswert = gk + v - first_period_fremdkapital;
		this.setUwsteuerfrei(gk);
		this.setSteuervorteile(v);
		this.setFremdkapital(first_period_fremdkapital);
		logger.debug("Unternehmenswert: " + unternehmenswert);
		return unternehmenswert;
	}

	/**
	 * @author Annika Weis
	 * @param wert
	 * @param zinssatz
	 * @param jahre
	 * @return Double, abgezinster Wert
	 */
	private double abzinsen(double wert, double zinssatz, int jahre) {
		return wert / Math.pow(1 + zinssatz, jahre);
	}

	/**
	 * @author Marcel Rosenberger
	 */
	public double getUwsteuerfrei() {
		return uwsteuerfrei;
	}

	/**
	 * @author Marcel Rosenberger
	 */
	public void setUwsteuerfrei(double uwsteuerfrei) {
		this.uwsteuerfrei = uwsteuerfrei;
	}

	/**
	 * @author Marcel Rosenberger
	 */
	public double getSteuervorteile() {
		return steuervorteile;
	}

	/**
	 * @author Marcel Rosenberger
	 */
	public void setSteuervorteile(double steuervorteile) {
		this.steuervorteile = steuervorteile;
	}

	/**
	 * @author Marcel Rosenberger
	 */
	public double getFremdkapital() {
		return fremdkapital;
	}

	/**
	 * @author Marcel Rosenberger
	 */
	public void setFremdkapital(double fremdkapital) {
		this.fremdkapital = fremdkapital;
	}

}
