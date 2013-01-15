package dhbw.ka.mwi.businesshorizon2.methods.timeseries;

import java.util.TreeSet;

import org.apache.log4j.Logger;

import dhbw.ka.mwi.businesshorizon2.methods.AbstractStochasticMethod;
import dhbw.ka.mwi.businesshorizon2.methods.Callback;
import dhbw.ka.mwi.businesshorizon2.models.AbstractPeriodContainer;
import dhbw.ka.mwi.businesshorizon2.models.AggregateCostMethodBalanceSheetPeriodContainer;
import dhbw.ka.mwi.businesshorizon2.models.AggregateCostMethodPeriod;
import dhbw.ka.mwi.businesshorizon2.models.CashFlowPeriod;
import dhbw.ka.mwi.businesshorizon2.models.CashFlowPeriodContainer;
import dhbw.ka.mwi.businesshorizon2.models.CostOfSalesMethodPeriod;
import dhbw.ka.mwi.businesshorizon2.models.CostOfSalesMethodPeriodContainer;
import dhbw.ka.mwi.businesshorizon2.models.Project;
import dhbw.ka.mwi.businesshorizon2.models.StochasticResultContainer;

/**
 * 
 * 
 * @author Kai Westerholz
 * 
 */

public class TimeseriesCalculator extends AbstractStochasticMethod {
	private static final long serialVersionUID = 1L;
	private Logger logger = Logger.getLogger(TimeseriesCalculator.class);
	public AbstractPeriodContainer periodsBSI;

	@Override
	public String getName() {
		return "Zeitreihenanalyse";
	}

	@Override
	public int getOrderKey() {
		return 1;
	}

	@Override
	public StochasticResultContainer calculate(Project project,
			Callback callback) throws InterruptedException,
			ConsideredPeriodsOfPastException {

		TreeSet<? extends AbstractPeriodContainer> resultPeriods;
		StochasticResultContainer resultContainer;
		AbstractPeriodContainer periods = (AbstractPeriodContainer) project
				.getPeriods();
		if (project.getRelevantPastPeriods() > periods.getPeriods().size() - 1) {
			logger.debug("Anzahl der betrachteten Perioden der Vergangenheit ist zu groß!");
			throw new ConsideredPeriodsOfPastException(
					"Die Anzahl der betrachteten Perioden der Vergangenheit muss kleiner sein als die Azahl der beobachteten Perioden.");
		}
		if (periods instanceof CashFlowPeriodContainer) {
			resultPeriods = new TreeSet<CashFlowPeriodContainer>();

			double[] previousValues = new double[periods.getPeriods().size()];
			AnalysisTimeseries timeseries = new AnalysisTimeseries();
			int counter = 0;
			for (CashFlowPeriod cFPeriod : (TreeSet<CashFlowPeriod>) periods
					.getPeriods()) {
				previousValues[counter] = cFPeriod.getFreeCashFlow();
				counter++;
			}

			double[][] resultTimeseries = timeseries.calculate(previousValues,
					project.getRelevantPastPeriods(),
					project.getPeriodsToForecast(), project.getIterations(),
					callback);
			for (int iteration = 0; iteration < project.getIterations(); iteration++) {
				CashFlowPeriodContainer cFContainer = new CashFlowPeriodContainer();
				for (int periodToForecast = 0; periodToForecast < resultTimeseries.length; periodToForecast++) {
					CashFlowPeriod cfPeriod = new CashFlowPeriod(
							project.getBasisYear() + (periodToForecast + 1));
					cfPeriod.setFreeCashFlow(resultTimeseries[periodToForecast][iteration]);
					cFContainer.getPeriods().add(cfPeriod);
				}
				resultPeriods.add(cFContainer);
			}
			resultContainer = new StochasticResultContainer(resultPeriods);

		} else if (periods instanceof AggregateCostMethodBalanceSheetPeriodContainer) {
			AnalysisTimeseries timeseries = new AnalysisTimeseries();
			resultPeriods = new TreeSet<AggregateCostMethodBalanceSheetPeriodContainer>();

			double[] immaterialFortune = new double[project
					.getRelevantPastPeriods()];
			double[] propertyValue = new double[project
					.getRelevantPastPeriods()];
			double[] financialValue = new double[project
					.getRelevantPastPeriods()];
			double[] equity = new double[project.getRelevantPastPeriods()];
			double[] provisions = new double[project.getRelevantPastPeriods()];
			double[] suplies = new double[project.getRelevantPastPeriods()];
			double[] claims = new double[project.getRelevantPastPeriods()];
			double[] stocks = new double[project.getRelevantPastPeriods()];
			double[] cashAssets = new double[project.getRelevantPastPeriods()];
			double[] borrowedCapital = new double[project
					.getRelevantPastPeriods()];

			double[][] immaterialFortuneResult = new double[project
					.getRelevantPastPeriods()][project.getIterations()];
			double[][] propertyValueResult = new double[project
					.getRelevantPastPeriods()][project.getIterations()];
			double[][] financialValueResult = new double[project
					.getRelevantPastPeriods()][project.getIterations()];
			double[][] equityResult = new double[project
					.getRelevantPastPeriods()][project.getIterations()];
			double[][] provisionsResult = new double[project
					.getRelevantPastPeriods()][project.getIterations()];
			double[][] supliesResult = new double[project
					.getRelevantPastPeriods()][project.getIterations()];
			double[][] claimsResult = new double[project
					.getRelevantPastPeriods()][project.getIterations()];
			double[][] stocksResult = new double[project
					.getRelevantPastPeriods()][project.getIterations()];
			double[][] cashAssetsResult = new double[project
					.getRelevantPastPeriods()][project.getIterations()];
			double[][] borrowedCapitalResult = new double[project
					.getRelevantPastPeriods()][project.getIterations()];

			double[] salesRevenue = new double[periods.getPeriods().size()]; // Umsatzerlöse
			double[] otherBusinessRevenue = new double[periods.getPeriods()
					.size()]; // Sonstige betriebliche Erträge
			double[] internallyProducedAndCapitalizedAssets = new double[periods
					.getPeriods().size()]; // Andere aktivierte
			// Eigenleistungen
			double[] materialCosts = new double[periods.getPeriods().size()]; // Materialaufwand
			double[] humanCapitalCosts = new double[periods.getPeriods().size()]; // Personalaufwand
			double[] writeDowns = new double[periods.getPeriods().size()]; // Abschriebungen
			double[] otherBusinessCosts = new double[periods.getPeriods()
					.size()]; // Sonstige betriebliche Aufwendungen
			double[] interestAndOtherCosts = new double[periods.getPeriods()
					.size()];

			double[][] salesRevenueResult = new double[periods.getPeriods()
					.size()][project.getIterations()]; // Umsatzerlöse
			double[][] otherBusinessRevenueResult = new double[periods
					.getPeriods().size()][project.getIterations()]; // Sonstige
																	// betriebliche
																	// Erträge
			double[][] internallyProducedAndCapitalizedAssetsResult = new double[periods
					.getPeriods().size()][project.getIterations()]; // Andere
																	// aktivierte
			// Eigenleistungen
			double[][] materialCostsResult = new double[periods.getPeriods()
					.size()][project.getIterations()]; // Materialaufwand
			double[][] humanCapitalCostsResult = new double[periods
					.getPeriods().size()][project.getIterations()]; // Personalaufwand
			double[][] writeDownsResult = new double[periods.getPeriods()
					.size()][project.getIterations()]; // Abschriebungen
			double[][] otherBusinessCostsResult = new double[periods
					.getPeriods().size()][project.getIterations()]; // Sonstige
																	// betriebliche
																	// Aufwendungen
			double[][] interestAndOtherCostsResult = new double[periods
					.getPeriods().size()][project.getIterations()];

			int counter = 0;
			for (AggregateCostMethodPeriod aCMPeriod : (TreeSet<AggregateCostMethodPeriod>) periods
					.getPeriods()) {
				salesRevenue[counter] = aCMPeriod.getSalesRevenue();
				otherBusinessRevenue[counter] = aCMPeriod
						.getOtherBusinessRevenue();
				internallyProducedAndCapitalizedAssets[counter] = aCMPeriod
						.getInternallyProducedAndCapitalizedAssets();
				materialCosts[counter] = aCMPeriod.getMaterialCosts();
				humanCapitalCosts[counter] = aCMPeriod.getHumanCapitalCosts();
				writeDowns[counter] = aCMPeriod.getWriteDowns();
				otherBusinessCosts[counter] = aCMPeriod.getOtherBusinessCosts();
				interestAndOtherCosts[counter] = aCMPeriod
						.getInterestAndOtherCosts();
				borrowedCapital[counter] = aCMPeriod.getBorrowedCapital();
				cashAssets[counter] = aCMPeriod.getCashAssets();
				stocks[counter] = aCMPeriod.getStocks();
				claims[counter] = aCMPeriod.getClaims();
				suplies[counter] = aCMPeriod.getSuplies();
				provisions[counter] = aCMPeriod.getProvisions();
				equity[counter] = aCMPeriod.getEquity();
				financialValue[counter] = aCMPeriod.getFinancialValue();
				propertyValue[counter] = aCMPeriod.getPropertyValue();
				immaterialFortune[counter] = aCMPeriod.getImmaterialFortune();
				counter++;
			}

			salesRevenueResult = timeseries.calculate(salesRevenue,
					project.getRelevantPastPeriods(),
					project.getPeriodsToForecast(), project.getIterations(),
					callback);
			otherBusinessRevenueResult = timeseries.calculate(
					otherBusinessRevenue, project.getRelevantPastPeriods(),
					project.getPeriodsToForecast(), project.getIterations(),
					callback);
			interestAndOtherCostsResult = timeseries.calculate(
					interestAndOtherCosts, project.getRelevantPastPeriods(),
					project.getPeriodsToForecast(), project.getIterations(),
					callback);
			internallyProducedAndCapitalizedAssetsResult = timeseries
					.calculate(internallyProducedAndCapitalizedAssets,
							project.getRelevantPastPeriods(),
							project.getPeriodsToForecast(),
							project.getIterations(), callback);
			materialCostsResult = timeseries.calculate(materialCosts,
					project.getRelevantPastPeriods(),
					project.getPeriodsToForecast(), project.getIterations(),
					callback);
			humanCapitalCostsResult = timeseries.calculate(humanCapitalCosts,
					project.getRelevantPastPeriods(),
					project.getPeriodsToForecast(), project.getIterations(),
					callback);
			writeDownsResult = timeseries.calculate(writeDowns,
					project.getRelevantPastPeriods(),
					project.getPeriodsToForecast(), project.getIterations(),
					callback);
			otherBusinessCostsResult = timeseries.calculate(otherBusinessCosts,
					project.getRelevantPastPeriods(),
					project.getPeriodsToForecast(), project.getIterations(),
					callback);
			borrowedCapitalResult = timeseries.calculate(borrowedCapital,
					project.getRelevantPastPeriods(),
					project.getPeriodsToForecast(), project.getIterations(),
					callback);
			cashAssetsResult = timeseries.calculate(cashAssets,
					project.getRelevantPastPeriods(),
					project.getPeriodsToForecast(), project.getIterations(),
					callback);
			stocksResult = timeseries.calculate(stocks,
					project.getRelevantPastPeriods(),
					project.getPeriodsToForecast(), project.getIterations(),
					callback);
			claimsResult = timeseries.calculate(claims,
					project.getRelevantPastPeriods(),
					project.getPeriodsToForecast(), project.getIterations(),
					callback);
			supliesResult = timeseries.calculate(suplies,
					project.getRelevantPastPeriods(),
					project.getPeriodsToForecast(), project.getIterations(),
					callback);
			provisionsResult = timeseries.calculate(provisions,
					project.getRelevantPastPeriods(),
					project.getPeriodsToForecast(), project.getIterations(),
					callback);
			equityResult = timeseries.calculate(equity,
					project.getRelevantPastPeriods(),
					project.getPeriodsToForecast(), project.getIterations(),
					callback);
			financialValueResult = timeseries.calculate(financialValue,
					project.getRelevantPastPeriods(),
					project.getPeriodsToForecast(), project.getIterations(),
					callback);
			propertyValueResult = timeseries.calculate(propertyValue,
					project.getRelevantPastPeriods(),
					project.getPeriodsToForecast(), project.getIterations(),
					callback);
			immaterialFortuneResult = timeseries.calculate(immaterialFortune,
					project.getRelevantPastPeriods(),
					project.getPeriodsToForecast(), project.getIterations(),
					callback);

			for (int iteration = 0; iteration < project.getIterations(); iteration++) {
				AggregateCostMethodBalanceSheetPeriodContainer aCMContainer = new AggregateCostMethodBalanceSheetPeriodContainer();
				for (int forecast = 0; forecast < periods.getPeriods().size(); forecast++) {
					AggregateCostMethodPeriod aCMPeriod = new AggregateCostMethodPeriod(
							project.getBasisYear() + (forecast + 1));
					aCMPeriod
							.setImmaterialFortune(immaterialFortuneResult[forecast][iteration]);
					aCMPeriod
							.setPropertyValue(propertyValueResult[forecast][iteration]);
					aCMPeriod
							.setFinancialValue(financialValueResult[forecast][iteration]);
					aCMPeriod
							.setBorrowedCapital(borrowedCapitalResult[forecast][iteration]);
					aCMPeriod
							.setProvisions(provisionsResult[forecast][iteration]);
					aCMPeriod.setSuplies(supliesResult[forecast][iteration]);
					aCMPeriod.setClaims(claimsResult[forecast][iteration]);
					aCMPeriod
							.setCashAssets(cashAssetsResult[forecast][iteration]);
					aCMPeriod.setStocks(stocksResult[forecast][iteration]);
					aCMPeriod.setEquity(equityResult[forecast][iteration]);
					aCMPeriod
							.setSalesRevenue(salesRevenueResult[forecast][iteration]);
					aCMPeriod
							.setOtherBusinessCosts(otherBusinessCostsResult[forecast][iteration]);
					aCMPeriod
							.setOtherBusinessRevenue(otherBusinessRevenueResult[forecast][iteration]);
					aCMPeriod
							.setInterestAndOtherCosts(interestAndOtherCostsResult[forecast][iteration]);
					aCMPeriod
							.setInternallyProducedAndCapitalizedAssets(internallyProducedAndCapitalizedAssetsResult[forecast][iteration]);
					aCMPeriod
							.setWriteDowns(writeDownsResult[forecast][iteration]);
					aCMPeriod
							.setMaterialCosts(materialCostsResult[forecast][iteration]);
					aCMPeriod
							.setHumanCapitalCosts(humanCapitalCostsResult[forecast][iteration]);
					aCMContainer.getPeriods().add(aCMPeriod);
				}
				resultPeriods.add(aCMContainer);
			}
			resultContainer = new StochasticResultContainer(resultPeriods);
		} else if (periods instanceof CostOfSalesMethodPeriodContainer) {

			AnalysisTimeseries timeseries = new AnalysisTimeseries();
			resultPeriods = new TreeSet<CostOfSalesMethodPeriodContainer>();

			double[] salesRevenue = new double[project.getRelevantPastPeriods()];
			double[] otherBusinessRevenue = new double[project
					.getRelevantPastPeriods()];
			double[] costOfPoduction = new double[project
					.getRelevantPastPeriods()];
			double[] costOfSalesAdministrationOthers = new double[project
					.getRelevantPastPeriods()];
			double[][] salesRevenueResult = new double[project
					.getRelevantPastPeriods()][project.getIterations()];
			double[][] otherBusinessRevenueResult = new double[project
					.getRelevantPastPeriods()][project.getIterations()];
			double[][] costOfPoductionResult = new double[project
					.getRelevantPastPeriods()][project.getIterations()];
			double[][] costOfSalesAdministrationOthersResult = new double[project
					.getRelevantPastPeriods()][project.getIterations()];

			double[] immaterialFortune = new double[project
					.getRelevantPastPeriods()];
			double[] propertyValue = new double[project
					.getRelevantPastPeriods()];
			double[] financialValue = new double[project
					.getRelevantPastPeriods()];
			double[] equity = new double[project.getRelevantPastPeriods()];
			double[] provisions = new double[project.getRelevantPastPeriods()];
			double[] suplies = new double[project.getRelevantPastPeriods()];
			double[] claims = new double[project.getRelevantPastPeriods()];
			double[] stocks = new double[project.getRelevantPastPeriods()];
			double[] cashAssets = new double[project.getRelevantPastPeriods()];
			double[] borrowedCapital = new double[project
					.getRelevantPastPeriods()];

			double[][] immaterialFortuneResult = new double[project
					.getRelevantPastPeriods()][project.getIterations()];
			double[][] propertyValueResult = new double[project
					.getRelevantPastPeriods()][project.getIterations()];
			double[][] financialValueResult = new double[project
					.getRelevantPastPeriods()][project.getIterations()];
			double[][] equityResult = new double[project
					.getRelevantPastPeriods()][project.getIterations()];
			double[][] provisionsResult = new double[project
					.getRelevantPastPeriods()][project.getIterations()];
			double[][] supliesResult = new double[project
					.getRelevantPastPeriods()][project.getIterations()];
			double[][] claimsResult = new double[project
					.getRelevantPastPeriods()][project.getIterations()];
			double[][] stocksResult = new double[project
					.getRelevantPastPeriods()][project.getIterations()];
			double[][] cashAssetsResult = new double[project
					.getRelevantPastPeriods()][project.getIterations()];
			double[][] borrowedCapitalResult = new double[project
					.getRelevantPastPeriods()][project.getIterations()];

			int counter = 0;
			for (CostOfSalesMethodPeriod cOSPeriod : (TreeSet<CostOfSalesMethodPeriod>) periods
					.getPeriods()) {
				borrowedCapital[counter] = cOSPeriod.getBorrowedCapital();
				cashAssets[counter] = cOSPeriod.getCashAssets();
				stocks[counter] = cOSPeriod.getStocks();
				claims[counter] = cOSPeriod.getClaims();
				suplies[counter] = cOSPeriod.getSuplies();
				provisions[counter] = cOSPeriod.getProvisions();
				equity[counter] = cOSPeriod.getEquity();
				financialValue[counter] = cOSPeriod.getFinancialValue();
				propertyValue[counter] = cOSPeriod.getPropertyValue();
				immaterialFortune[counter] = cOSPeriod.getImmaterialFortune();
				counter++;
			}

			borrowedCapitalResult = timeseries.calculate(borrowedCapital,
					project.getRelevantPastPeriods(),
					project.getPeriodsToForecast(), project.getIterations(),
					callback);
			cashAssetsResult = timeseries.calculate(cashAssets,
					project.getRelevantPastPeriods(),
					project.getPeriodsToForecast(), project.getIterations(),
					callback);
			stocksResult = timeseries.calculate(stocks,
					project.getRelevantPastPeriods(),
					project.getPeriodsToForecast(), project.getIterations(),
					callback);
			claimsResult = timeseries.calculate(claims,
					project.getRelevantPastPeriods(),
					project.getPeriodsToForecast(), project.getIterations(),
					callback);
			supliesResult = timeseries.calculate(suplies,
					project.getRelevantPastPeriods(),
					project.getPeriodsToForecast(), project.getIterations(),
					callback);
			provisionsResult = timeseries.calculate(provisions,
					project.getRelevantPastPeriods(),
					project.getPeriodsToForecast(), project.getIterations(),
					callback);
			equityResult = timeseries.calculate(equity,
					project.getRelevantPastPeriods(),
					project.getPeriodsToForecast(), project.getIterations(),
					callback);
			financialValueResult = timeseries.calculate(financialValue,
					project.getRelevantPastPeriods(),
					project.getPeriodsToForecast(), project.getIterations(),
					callback);
			propertyValueResult = timeseries.calculate(propertyValue,
					project.getRelevantPastPeriods(),
					project.getPeriodsToForecast(), project.getIterations(),
					callback);
			immaterialFortuneResult = timeseries.calculate(immaterialFortune,
					project.getRelevantPastPeriods(),
					project.getPeriodsToForecast(), project.getIterations(),
					callback);
			costOfSalesAdministrationOthersResult = timeseries.calculate(
					costOfSalesAdministrationOthers,
					project.getRelevantPastPeriods(),
					project.getPeriodsToForecast(), project.getIterations(),
					callback);
			costOfPoductionResult = timeseries.calculate(costOfPoduction,
					project.getRelevantPastPeriods(),
					project.getPeriodsToForecast(), project.getIterations(),
					callback);
			otherBusinessRevenueResult = timeseries.calculate(
					otherBusinessRevenue, project.getRelevantPastPeriods(),
					project.getPeriodsToForecast(), project.getIterations(),
					callback);
			salesRevenueResult = timeseries.calculate(salesRevenue,
					project.getRelevantPastPeriods(),
					project.getPeriodsToForecast(), project.getIterations(),
					callback);

			for (int iteration = 0; iteration < project.getIterations(); iteration++) {
				CostOfSalesMethodPeriodContainer cOSContainer = new CostOfSalesMethodPeriodContainer();
				for (int forecast = 0; forecast < periods.getPeriods().size(); forecast++) {
					CostOfSalesMethodPeriod cOSPeriod = new CostOfSalesMethodPeriod(
							project.getBasisYear() + (forecast + 1));

					cOSPeriod
							.setImmaterialFortune(immaterialFortuneResult[forecast][iteration]);
					cOSPeriod
							.setPropertyValue(propertyValueResult[forecast][iteration]);
					cOSPeriod
							.setFinancialValue(financialValueResult[forecast][iteration]);
					cOSPeriod
							.setBorrowedCapital(borrowedCapitalResult[forecast][iteration]);
					cOSPeriod
							.setProvisions(provisionsResult[forecast][iteration]);
					cOSPeriod.setSuplies(supliesResult[forecast][iteration]);
					cOSPeriod.setClaims(claimsResult[forecast][iteration]);
					cOSPeriod
							.setCashAssets(cashAssetsResult[forecast][iteration]);
					cOSPeriod.setStocks(stocksResult[forecast][iteration]);
					cOSPeriod.setEquity(equityResult[forecast][iteration]);
					cOSPeriod
							.setSalesRevenue(salesRevenueResult[forecast][iteration]);
					cOSPeriod
							.setOtherBusinessRevenue(otherBusinessRevenueResult[forecast][iteration]);
					cOSPeriod
							.setCostOfPoduction(costOfPoductionResult[forecast][iteration]);
					cOSPeriod
							.setCostOfSalesAdministrationOthers(costOfSalesAdministrationOthersResult[forecast][iteration]);

					cOSContainer.getPeriods().add(cOSPeriod);
				}
				resultPeriods.add(cOSContainer);
			}
			resultContainer = new StochasticResultContainer(resultPeriods);

		}

		return resultContainer;
	}
}
