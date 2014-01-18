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

import dhbw.ka.mwi.businesshorizon2.models.StochasticResultContainer;
import dhbw.ka.mwi.businesshorizon2.models.Szenario;
import dhbw.ka.mwi.businesshorizon2.models.PeriodContainer.AbstractPeriodContainer;
import dhbw.ka.mwi.businesshorizon2.models.PeriodContainer.DirectCalculatedCashflowPeriodContainer;
import dhbw.ka.mwi.businesshorizon2.models.PeriodContainer.IndirectCalculatedCashflowPeriodContainer;

/**
 * 
 * @author kathie
 * 
 */
public class CashFlowCalculator {

	/**
	 * Mit Hilfe dieser Methode wird der 'Free Cashflow' aus den direkten und
	 * indirekten Berechnungsmethoden ermitteln. Der 'Free Cashflow' wird
	 * einfach in der entsprechenden Periode durch eine setter-Methode gesetzt.
	 * 
	 * @author Marcel Rosenberger
	 * 
	 * @param result
	 *            StochasticResultContainer
	 * @param szenario
	 *            Szenario
	 */
	public static void calculateCashflows(StochasticResultContainer result,
			Szenario szenario) {

		for (AbstractPeriodContainer container : result.getPeriodContainers()) {
			if (container instanceof DirectCalculatedCashflowPeriodContainer) {
				calculateDirectCashflows((DirectCalculatedCashflowPeriodContainer) container);
			} else if (container instanceof IndirectCalculatedCashflowPeriodContainer) {
				calculateIndirectCashflows(
						(IndirectCalculatedCashflowPeriodContainer) container,
						szenario);
			}
		}
	}

	/**
	 * Direkte Free-Cash-Flow Ermittlung
	 * 
	 * @author Marcel Rosenberger
	 * 
	 */

	private static void calculateDirectCashflows(
			DirectCalculatedCashflowPeriodContainer container) {

		for (DirectCalculatedCashflowPeriod period : container.getPeriods()) {
			double freeCashFlow = period.getUmsatzErlöse()
					- period.getUmsatzKosten()
					- period.getSteuernBeiReinerEigenfinanzierung()
					- period.getSaldoAusAuszahlungen();

			period.setFreeCashFlow(freeCashFlow);

		}

	}

	/**
	 * Indirekte Free-Cash-Flow Ermittlung
	 * 
	 * @author Marcel Rosenberger
	 * 
	 */

	private static void calculateIndirectCashflows(
			IndirectCalculatedCashflowPeriodContainer container,
			Szenario szenario) {

		for (IndirectCalculatedCashflowPeriod period : container.getPeriods()) {
			double freeCashFlow = period.getJahresÜberschuss()
					- period.getTaxShield()
					+ period.getNichtZahlungswirksameAufwendungen()
					- period.getNichtZahlungswirksameErtraege()
					- period.getBruttoInvestitionen();

			period.setFreeCashFlow(freeCashFlow);

		}

	}

}
