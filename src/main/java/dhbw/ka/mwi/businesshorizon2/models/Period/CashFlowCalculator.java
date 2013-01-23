package dhbw.ka.mwi.businesshorizon2.models.Period;

import dhbw.ka.mwi.businesshorizon2.models.StochasticResultContainer;
import dhbw.ka.mwi.businesshorizon2.models.Szenario;
import dhbw.ka.mwi.businesshorizon2.models.PeriodContainer.AbstractPeriodContainer;
import dhbw.ka.mwi.businesshorizon2.models.PeriodContainer.AggregateCostMethodBalanceSheetPeriodContainer;
import dhbw.ka.mwi.businesshorizon2.models.PeriodContainer.CostOfSalesMethodPeriodContainer;

/**
 * 
 * @author kathie
 * 
 */
public class CashFlowCalculator {

	/**
	 * Mit Hilfe dieser Methode kann der 'Free Cashflow' aus den Bilanzen nach
	 * dem Umsatz- und nach dem Gesamtkostenverfahren berechnet werden. Der
	 * 'Free Cashflow' wird einfach in der entsprechenden Periode durch eine
	 * setter-Methode gesetzt.
	 * 
	 * @param result
	 *            StochasticResultContainer
	 * @param szenario
	 *            Szenario
	 */
	public static void calculateCashflows(StochasticResultContainer result,
			Szenario szenario) {

		for (AbstractPeriodContainer container : result.getPeriodContainers()) {
			if (container instanceof AggregateCostMethodBalanceSheetPeriodContainer) {
				calculateAggregateCostCashflows(
						(AggregateCostMethodBalanceSheetPeriodContainer) container,
						szenario);
			} else if (container instanceof CostOfSalesMethodPeriodContainer) {
				calculateCostOfSalesCashflows(
						(CostOfSalesMethodPeriodContainer) container, szenario);
			}
		}
	}

	private static void calculateAggregateCostCashflows(
			AggregateCostMethodBalanceSheetPeriodContainer container,
			Szenario szenario) {

		boolean firstPeriod = true;
		AggregateCostMethodPeriod pastPeriod = null;

		for (AggregateCostMethodPeriod period : container.getPeriods()) {
			if (firstPeriod) {
				pastPeriod = (AggregateCostMethodPeriod) period.deepCopy();
				firstPeriod = false;
			} else {

				/**
				 * EBIT steht für 'earnings before interest and taxes', also für
				 * den 'Gewinn vor Zinsen und Steuern'. Der EBIT wird wie folgt
				 * berechnet:<br>
				 * (Umsatzerlöse + Sonstige betriebliche Erträge + Sonstige
				 * aktive Eigenleistungen) - (Materialaufwand + Personalaufwand
				 * + Abschreibungen + Sonstiger betrieblicher Aufwand)
				 */
				double ebit = (period.getSalesRevenue()
						+ period.getOtherBusinessRevenue() + period
							.getInternallyProducedAndCapitalizedAssets())
						- (period.getMaterialCosts()
								+ period.getHumanCapitalCosts()
								+ period.getWriteDowns() + period
									.getOtherBusinessCosts());
				/**
				 * EBT steht für 'earnings beforde taxes', also für den 'Gewinn
				 * vor Steuern'. Der EBT wird wie folgt berechnet:<br>
				 * EBIT - Zinsen und ähnliche Anwendungen
				 */
				double ebt = ebit - period.getInterestAndOtherCosts();

				double businessTax = ebt * szenario.getBusinessTax();
				double corporateAndBusinessTax = ebt
						* szenario.getBusinessTax()
						* szenario.getCorporateAndSolitaryTax();

				/**
				 * Nettojahreseinkommen
				 */
				double annualNetIncome = ebt + businessTax
						+ corporateAndBusinessTax;

				double operationalCF = annualNetIncome + period.getWriteDowns()
						+ (period.getProvisions() - pastPeriod.getProvisions())
						+ period.getInterestAndOtherCosts();

				double avBefore = pastPeriod.getImmaterialFortune()
						+ pastPeriod.getPropertyValue()
						+ pastPeriod.getFinancialValue()
						- period.getWriteDowns();
				double avNow = period.getImmaterialFortune()
						+ period.getPropertyValue()
						+ period.getFinancialValue();

				double invDelta = avNow - avBefore;

				double uvDiff = (period.getSuplies() + period.getClaims()
						+ period.getStocks() + period.getCashAssets())
						- (pastPeriod.getSuplies() + pastPeriod.getClaims()
								+ pastPeriod.getStocks() + pastPeriod
									.getCashAssets());

				double afterInvestmentCF = invDelta + uvDiff;

				double ekdif = period.getEquity() - pastPeriod.getEquity();

				double fkdif = (period.getCapitalStock() + period
						.getProvisions())
						- (pastPeriod.getCapitalStock() + pastPeriod
								.getProvisions());

				double afterFinancingCF = ekdif + fkdif;

				double freeCashFlow = operationalCF + afterInvestmentCF
						+ afterFinancingCF;

				period.setFreeCashFlow(freeCashFlow);

				pastPeriod = (AggregateCostMethodPeriod) period.deepCopy();

			}
		}

	}

	private static void calculateCostOfSalesCashflows(
			CostOfSalesMethodPeriodContainer container, Szenario szenario) {
		// TODO Muss noch ausimplementiert werden

	}

}
