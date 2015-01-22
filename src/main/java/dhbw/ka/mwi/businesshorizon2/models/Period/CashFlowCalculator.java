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
package dhbw.ka.mwi.businesshorizon2.models.Period;

import org.apache.log4j.Logger;

import dhbw.ka.mwi.businesshorizon2.models.DeterministicResultContainer;
import dhbw.ka.mwi.businesshorizon2.models.StochasticResultContainer;
import dhbw.ka.mwi.businesshorizon2.models.Szenario;
import dhbw.ka.mwi.businesshorizon2.models.PeriodContainer.AbstractPeriodContainer;
import dhbw.ka.mwi.businesshorizon2.models.PeriodContainer.GesamtkostenVerfahrenCashflowPeriodContainer;
import dhbw.ka.mwi.businesshorizon2.models.PeriodContainer.UmsatzkostenVerfahrenCashflowPeriodContainer;

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
			if (container instanceof GesamtkostenVerfahrenCashflowPeriodContainer) {
				calculateGKVCashflows(
						(GesamtkostenVerfahrenCashflowPeriodContainer) container,
						scenario);
			} else if (container instanceof UmsatzkostenVerfahrenCashflowPeriodContainer) {
				calculateUKVCashflows(
						(UmsatzkostenVerfahrenCashflowPeriodContainer) container,
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
			if (container instanceof GesamtkostenVerfahrenCashflowPeriodContainer) {

				calculateGKVCashflows(
						(GesamtkostenVerfahrenCashflowPeriodContainer) container,
						scenario);
			} else if (container instanceof UmsatzkostenVerfahrenCashflowPeriodContainer) {
				calculateUKVCashflows(
						(UmsatzkostenVerfahrenCashflowPeriodContainer) container,
						scenario);

			}
		}
	}

	/**
	 * Free-Cash-Flow Ermittlung Gesamtkostenverfahren
	 * 
	 * @author Marcel Rosenberger
	 * 
	 */

	public static void calculateGKVCashflows(
			GesamtkostenVerfahrenCashflowPeriodContainer container,
			Szenario scenario) {
		logger.debug("Cash-Flow-Ermittlung nach Gesamtkostenverfahren");
		for (GesamtkostenVerfahrenCashflowPeriod period : container
				.getPeriods()) {
			logger.debug("=================================================");
			logger.debug("Jahr: " + period.getYear());
			

			// Betriebsergebnis
			double freeCashFlow = period.getUmsatzerlöse()
					+ period.getBestandserhöhung()
					- period.getBestandsverminderung()
					- period.getMaterialaufwand() - period.getLöhne()
					- period.getEinstellungskosten()
					- period.getPensionsrückstellungen()
					- period.getSonstigepersonalkosten()
					- period.getAbschreibungen() + period.getSonstigerertrag()
					- period.getSonstigeraufwand();
			logger.debug("Betriebsergebnis: " + freeCashFlow);

			// Ergebnis der gewöhnlichen Geschäftstätigkeit
			freeCashFlow = freeCashFlow + period.getWertpapiererträge()
					- period.getZinsenundaufwendungen();
			logger.debug("Ergebnis der gewöhnlichen Geschäftstätigkeit: "
					+ freeCashFlow);

			// Außerordentliches Ergebnis
			freeCashFlow = freeCashFlow + period.getAußerordentlicheerträge()
					- period.getAußerordentlicheaufwände();
			logger.debug("Außerordentliches Ergebnis: " + freeCashFlow);

			// Steuersatzberechnung
			double steuersatz = (scenario.getCorporateAndSolitaryTax() + scenario
					.getBusinessTax()) / 100;
			logger.debug("Steuersatz: " + steuersatz);

			// Periodenüberschuss/-fehlbetrag
			if (freeCashFlow > 0) {
				freeCashFlow = freeCashFlow - (freeCashFlow * steuersatz);
			}
			logger.debug("Periodenüberschuss/-fehlbetrag: " + freeCashFlow);

			// Cash-Flow nach Steuern
			freeCashFlow = freeCashFlow + period.getAbschreibungen()
					+ period.getPensionsrückstellungen();
			logger.debug("Cash-Flow nach Steuern: " + freeCashFlow);
			period.setFreeCashFlow(freeCashFlow);

		}

	}

	/**
	 * Free-Cash-Flow Ermittlung Umsatzkostenverfahren
	 * 
	 * @author Marcel Rosenberger
	 * 
	 */

	public static void calculateUKVCashflows(
			UmsatzkostenVerfahrenCashflowPeriodContainer container,
			Szenario scenario) {
		logger.debug("Cash-Flow-Ermittlung nach Umsatzkostenverfahren");
		for (UmsatzkostenVerfahrenCashflowPeriod period : container
				.getPeriods()) {
			logger.debug("=================================================");
			logger.debug("Jahr: " + period.getYear());
			

			// Betriebsergebnis
			double freeCashFlow = period.getUmsatzerlöse()
					- period.getHerstellungskosten()
					- period.getVertriebskosten()
					- period.getForschungskosten()
					- period.getVerwaltungskosten()
					+ period.getSonstigerertrag();
			logger.debug("Betriebsergebnis: " + freeCashFlow);

			// Ergebnis der gewöhnlichen Geschäftstätigkeit
			freeCashFlow = freeCashFlow + period.getWertpapiererträge()
					- period.getZinsenundaufwendungen();
			logger.debug("Ergebnis der gewöhnlichen Geschäftstätigkeit: "
					+ freeCashFlow);

			// Außerordentliches Ergebnis
			freeCashFlow = freeCashFlow + period.getAußerordentlicheerträge()
					- period.getAußerordentlicheaufwände();
			logger.debug("Außerordentliches Ergebnis: " + freeCashFlow);

			// Steuersatzberechnung
			double steuersatz = (scenario.getCorporateAndSolitaryTax() + scenario
					.getBusinessTax()) / 100;
			logger.debug("Steuersatz: " + steuersatz);

			// Periodenüberschuss/-fehlbetrag
			if (freeCashFlow > 0) {
				freeCashFlow = freeCashFlow - (freeCashFlow * steuersatz);
			}
			logger.debug("Periodenüberschuss/-fehlbetrag: " + freeCashFlow);

			// Cash-Flow nach Steuern
			freeCashFlow = freeCashFlow + period.getAbschreibungen()
					+ period.getPensionsrückstellungen();
			logger.debug("Cash-Flow nach Steuern: " + freeCashFlow);
			period.setFreeCashFlow(freeCashFlow);

		}

	}
}
