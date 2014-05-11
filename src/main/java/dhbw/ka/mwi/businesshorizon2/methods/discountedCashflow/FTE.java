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

import dhbw.ka.mwi.businesshorizon2.methods.AbstractDeterministicMethod;
import dhbw.ka.mwi.businesshorizon2.methods.CallbackInterface;
import dhbw.ka.mwi.businesshorizon2.methods.DeterministicMethodException;
import dhbw.ka.mwi.businesshorizon2.models.DeterministicResultContainer;
import dhbw.ka.mwi.businesshorizon2.models.Project;
import dhbw.ka.mwi.businesshorizon2.models.StochasticResultContainer;
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
public class FTE extends AbstractDeterministicMethod {

	@Override
	public String getName() {

		return "Flow-to-Equity (FTE)";
	}

	@Override
	public int getOrderKey() {

		return 4;
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

	public double calculateValues( // DeterministicResultContainer
			double[] cashflow,  Szenario szenario) {
		TreeSet<CashFlowPeriodContainer> prognose = new TreeSet<CashFlowPeriodContainer>();

		double unternehmenswert = 0;
		double restwert = 0;
		double nbv = 0; // Nicht-betriebsnotwendiges Vermögen
		double sSteuersatz;
		double sKS;
		double sZinsen;
		double sEK;

		Double first_period = null;
		Double period;
		Double lastPeriod = null;
		double jahr = 1;

		sKS = szenario.getCorporateAndSolitaryTax() / 100;
		sSteuersatz = 0.75 * szenario.getBusinessTax() / 100 + sKS;
		sEK = szenario.getRateReturnEquity() / 100;
		sZinsen = szenario.getRateReturnCapitalStock() / 100;

		for (int durchlauf = 1; durchlauf <= cashflow.length; durchlauf++) {
			period = cashflow[durchlauf-1];

			// zum Unternehmenswert einen weiteren abgezinsten Cashflow addieren
			unternehmenswert += abzinsen(period, sEK,
					durchlauf);
			lastPeriod = period;

			jahr = durchlauf;
		}

		
		// Restwert berechnen
		restwert = lastPeriod / sEK;
		// Restwert abzinsen
		restwert = abzinsen(restwert, sEK, (int) jahr);
		// Unternehmenswert gesamt berechnen
		unternehmenswert = unternehmenswert + restwert // / Math.pow(1 + sEK,
														// jahr-1)
				+ nbv;

		return unternehmenswert;
	}

	public List<String> getPeriodenNamen(Project project) {
		List<String> perioden = null;

		return perioden;
	}

	/**
	 * Zinst einen angegebenen Wert zum Zinssatz über die Jahre ab
	 * 
	 * @author Annika Weis
	 * @param wert
	 * @param zinssatz
	 * @param jahre
	 * @return Double, abgezinster Wert
	 */
	private double abzinsen(double wert, double zinssatz, int jahre) {
		return wert / Math.pow(1 + zinssatz, jahre);
	}
}
