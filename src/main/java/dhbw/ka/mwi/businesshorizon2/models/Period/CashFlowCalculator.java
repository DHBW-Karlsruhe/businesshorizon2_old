/*******************************************************************************
 * BusinessHorizon2
 *
 * Copyright (C) 
 * 2012-2013 Christian Gahlert, Florian Stier, Kai Westerholz,
 * Timo Belz, Daniel Dengler, Katharina Huber, Christian Scherer, Julius Hacker
 * 2013-2014 Marcel Rosenberger, Mirko Göpfrich, Annika Weis, Katharina Narlock, 
 * Volker Meier
 * 2014-2015 Marco Glaser
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
 * @author Marcel Rosenberger, Marco Glaser
 * 
 */
public class CashFlowCalculator {

	private static final Logger logger = Logger
			.getLogger("CashFlowCalculator.class");

	private static Period periodBefore;

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
	 * @author Marco Glaser
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
					+ period.getAktivEigenleistung()
					- period.getMaterialaufwand() 
					- period.getPersonalaufwand()
					- period.getAbschreibungen() 
					+ period.getSonstigerertrag()
					- period.getSonstigeraufwand();
			logger.debug("Betriebsergebnis: " + freeCashFlow);

			// Ergebnis der gewöhnlichen Geschäftstätigkeit
			freeCashFlow = freeCashFlow 
					+ period.getWertpapiererträge()
					+ period.getBeteiligungenErtraege()
					+ period.getZinsertraege()
					- period.getAbschreibungenFinanzanlagen()
					- period.getZinsenundaufwendungen();
			logger.debug("Ergebnis der gewöhnlichen Geschäftstätigkeit: "
					+ freeCashFlow);

			// Außerordentliches Ergebnis
			freeCashFlow = freeCashFlow 
					+ period.getAußerordentlicheerträge()
					- period.getAußerordentlicheaufwände()
					- period.getSteueraufwand();
			logger.debug("Jahresüberschuss: " + freeCashFlow);

			
			double taxshield = 0;
			if(periodBefore != null){
				double fkVorjahr = periodBefore.getCapitalStock();
				taxshield = (0.75*scenario.getBusinessTax()+scenario.getCorporateAndSolitaryTax())*scenario.getRateReturnCapitalStock()*period.getCapitalStock();
			}
			freeCashFlow = freeCashFlow 
					- taxshield
					- period.getBruttoinvestitionen()
					+ period.getZinsenundaufwendungen()
					+ period.getAbschreibungen();
			logger.debug("Free Cash Flow: " + freeCashFlow);


			period.setFreeCashFlow(freeCashFlow);

			periodBefore = period;

		}

	}

	/**
	 * Free-Cash-Flow Ermittlung Umsatzkostenverfahren
	 * 
	 * @author Marco Glaser
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
					- period.getVerwaltungskosten()
					+ period.getSonstigerertrag()
					- period.getSonstigeraufwand();
			logger.debug("Betriebsergebnis: " + freeCashFlow);

			// Ergebnis der gewöhnlichen Geschäftstätigkeit
			freeCashFlow = freeCashFlow 
					+ period.getWertpapiererträge()
					+ period.getBeteiligungenErtraege()
					+ period.getZinsertraege()
					- period.getAbschreibungenFinanzanlagen()
					- period.getZinsenundaufwendungen();
			logger.debug("Ergebnis der gewöhnlichen Geschäftstätigkeit: " + freeCashFlow);

			// Außerordentliches Ergebnis
			freeCashFlow = freeCashFlow 
					+ period.getAußerordentlicheerträge()
					- period.getAußerordentlicheaufwände()
					- period.getSteueraufwand();
			logger.debug("Jahresüberschuss: " + freeCashFlow);

			// Periodenüberschuss/-fehlbetrag
			double taxshield = 0;
			if(periodBefore != null){
				double fkVorjahr = periodBefore.getCapitalStock();
				taxshield = (0.75*scenario.getBusinessTax()+scenario.getCorporateAndSolitaryTax())*scenario.getRateReturnCapitalStock()*period.getCapitalStock();
			}
			freeCashFlow = freeCashFlow 
					- taxshield
					- period.getBruttoinvestitionen()
					+ period.getZinsenundaufwendungen()
					+ period.getAbschreibungen();
			logger.debug("Free Cash Flow: " + freeCashFlow);

			period.setFreeCashFlow(freeCashFlow);

			periodBefore = period;

		}

	}
}
