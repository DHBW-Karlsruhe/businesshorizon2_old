/*******************************************************************************
 * BusinessHorizon2
 *
 * Copyright (C) 2012-2013 Christian Gahlert, Florian Stier, Kai Westerholz,
 * Timo Belz, Daniel Dengler, Katharina Huber, Christian Scherer, Julius Hacker
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

package dhbw.ka.mwi.businesshorizon2.models.Period;

import org.apache.log4j.Logger;

import dhbw.ka.mwi.businesshorizon2.models.DeterministicResultContainer;
import dhbw.ka.mwi.businesshorizon2.models.StochasticResultContainer;
import dhbw.ka.mwi.businesshorizon2.models.Szenario;
import dhbw.ka.mwi.businesshorizon2.models.PeriodContainer.AbstractPeriodContainer;
import dhbw.ka.mwi.businesshorizon2.models.PeriodContainer.DirectCalculatedCashflowPeriodContainer;
import dhbw.ka.mwi.businesshorizon2.models.PeriodContainer.IndirectCalculatedCashflowPeriodContainer;

/**
 * 
 * @author Marcel Rosenberger
 * 
 */
public class CashFlowCalculator {

	private static final Logger logger = Logger
			.getLogger("CashFlowCalculator.class");

	/**
	 * Mit Hilfe dieser Methode wird der 'Free Cashflow' aus den direkten und
	 * indirekten Berechnungsmethoden ermitteln. Der 'Free Cashflow' wird
	 * einfach in der entsprechenden Periode durch eine setter-Methode gesetzt.
	 * 
	 * @author Marcel Rosenberger
	 * 
	 * @param result
	 *            DeterministicResultContainer
	 * @param szenario
	 *            Szenario
	 */
	public static void calculateCashflows(DeterministicResultContainer result,
			Szenario scenario) {

		for (AbstractPeriodContainer container : result.getPeriodContainers()) {
			if (container instanceof DirectCalculatedCashflowPeriodContainer) {
				calculateDirectCashflows(
						(DirectCalculatedCashflowPeriodContainer) container,
						scenario);
			} else if (container instanceof IndirectCalculatedCashflowPeriodContainer) {
				calculateIndirectCashflows(
						(IndirectCalculatedCashflowPeriodContainer) container,
						scenario);
			}
		}
	}

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
			Szenario scenario) {

		for (AbstractPeriodContainer container : result.getPeriodContainers()) {
			if (container instanceof DirectCalculatedCashflowPeriodContainer) {
				calculateDirectCashflows(
						(DirectCalculatedCashflowPeriodContainer) container,
						scenario);
			} else if (container instanceof IndirectCalculatedCashflowPeriodContainer) {
				calculateIndirectCashflows(
						(IndirectCalculatedCashflowPeriodContainer) container,
						scenario);
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
			DirectCalculatedCashflowPeriodContainer container, Szenario scenario) {

		for (DirectCalculatedCashflowPeriod period : container.getPeriods()) {
			// Cash-Flow vor Zins und Steuern.
			double freeCashFlow = period.getUmsatzErlöse()
					- period.getUmsatzKosten();
			double steuersatz = (scenario.getCorporateAndSolitaryTax() + scenario
					.getBusinessTax()) / 100;
			logger.debug("Steuersatz: " + steuersatz);
			freeCashFlow = freeCashFlow - (period.getEbit() * steuersatz)
					+ period.getDesinvestitionen() - period.getInvestitionen();
			logger.debug("FCF: " + freeCashFlow);

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
			Szenario scenario) {

		for (IndirectCalculatedCashflowPeriod period : container.getPeriods()) {

			double freeCashFlow = period.getJahresÜberschuss()
					+ period.getZinsaufwand();
			logger.debug("FreeCashFlow: " + freeCashFlow);
			double fiktivesteuern = period.getEbit()-period.getZinsaufwand()*0.75;
			logger.debug("Fiktive Steuern: " + fiktivesteuern);
			fiktivesteuern = fiktivesteuern*(scenario.getBusinessTax()/100);
			logger.debug("Fiktive Steuern: " + fiktivesteuern);
			fiktivesteuern = fiktivesteuern + ((period.getEbit()-period.getZinsaufwand())*(scenario.getCorporateAndSolitaryTax()/100));
			logger.debug("Fiktive Steuern: " + fiktivesteuern);
			freeCashFlow = freeCashFlow - fiktivesteuern
					+ period.getNichtZahlungswirksameAufwendungen()
					- period.getNichtZahlungswirksameErtraege()
					- period.getBruttoInvestitionen();
			logger.debug("FreeCashFlow: " + freeCashFlow);
			period.setFreeCashFlow(freeCashFlow);

		}

	}
}