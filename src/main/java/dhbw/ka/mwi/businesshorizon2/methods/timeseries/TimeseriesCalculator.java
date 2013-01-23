package dhbw.ka.mwi.businesshorizon2.methods.timeseries;

import java.util.TreeSet;

import org.apache.log4j.Logger;

import dhbw.ka.mwi.businesshorizon2.methods.AbstractStochasticMethod;
import dhbw.ka.mwi.businesshorizon2.methods.CallbackInterface;
import dhbw.ka.mwi.businesshorizon2.methods.StochasticMethodException;
import dhbw.ka.mwi.businesshorizon2.models.Project;
import dhbw.ka.mwi.businesshorizon2.models.StochasticResultContainer;
import dhbw.ka.mwi.businesshorizon2.models.Period.AggregateCostMethodPeriod;
import dhbw.ka.mwi.businesshorizon2.models.Period.CashFlowPeriod;
import dhbw.ka.mwi.businesshorizon2.models.Period.CostOfSalesMethodPeriod;
import dhbw.ka.mwi.businesshorizon2.models.PeriodContainer.AbstractPeriodContainer;
import dhbw.ka.mwi.businesshorizon2.models.PeriodContainer.AggregateCostMethodBalanceSheetPeriodContainer;
import dhbw.ka.mwi.businesshorizon2.models.PeriodContainer.CashFlowPeriodContainer;
import dhbw.ka.mwi.businesshorizon2.models.PeriodContainer.CostOfSalesMethodPeriodContainer;

/**
 * Diese Klasse steuert den Aufruf der Zeitreihenanalyse. Es gibt drei Szenarien
 * die betrachtet werden muesen. Bei CashFlowPerioden wird die Zeitreihenanalyse
 * einmal fuer den CashFlow aufgerufen und prognostiziert diesen. Die anderen
 * beiden Varianten unterscheiden die BilanzPerioden auf Gesamtkostenverfahren
 * und Umsatzkostenverfahren. Hierbei muessen jeweils alle Bilanzposten einzeln
 * mit der Zeitreihenanalyse prognostiziert werden. Je nach Anzahld der Posten
 * weist dies eine schlechtere Performance auf.
 * 
 * @author Kai Westerholz
 * 
 */

public class TimeseriesCalculator extends AbstractStochasticMethod {
	private static final long serialVersionUID = 1L;
	private final Logger logger = Logger.getLogger(TimeseriesCalculator.class);
	public AbstractPeriodContainer periodsBSI;

	@Override
	public String getName() {
		return "Zeitreihenanalyse";
	}

	@Override
	public int getOrderKey() {
		return 1;
	}

	public StochasticResultContainer calculateExpectedValues(Project project)
			throws StochasticMethodException {

		TreeSet<AbstractPeriodContainer> resultPeriods = new TreeSet<AbstractPeriodContainer>();
		StochasticResultContainer resultContainer = null;

		/**
		 * Die Zeitreihenanalyse kann nur durchgefuehrt werden, wenn die Anzahl
		 * der beruecksichtigten Vergangenheitsperioden kleiner ist als die
		 * Anzahl der eingegebenen Vergangenheitsperioden
		 */
		if (project.getRelevantPastPeriods() > project.getStochasticPeriods()
				.getPeriods().size() - 1) {
			logger.debug("Anzahl der betrachteten Perioden der Vergangenheit ist zu groß!");
			throw new ConsideredPeriodsOfPastException(
					"Die Anzahl der betrachteten Perioden der Vergangenheit muss kleiner sein als die Azahl der beobachteten Perioden.");
		}

		if (project.getStochasticPeriods().getPeriods().first() instanceof CashFlowPeriod) {
			// Nachfolgend wird die Zeitreihenanalyse fuer CashFlowPerioden
			// ausgefuehrt
			TreeSet<? super CashFlowPeriodContainer> cFResultContainer = resultPeriods;

			double[] previousValues = new double[project.getStochasticPeriods()
					.getPeriods().size()];
			double[] previousBorrowedCapital = new double[project
					.getStochasticPeriods().getPeriods().size()];
			AnalysisTimeseries timeseries = new AnalysisTimeseries();
			int counter = 0;

			// Umwandlung der Perioden in ein Double-Arrays
			for (CashFlowPeriod cFPeriod : (TreeSet<CashFlowPeriod>) project
					.getStochasticPeriods().getPeriods()) {
				previousValues[counter] = cFPeriod.getFreeCashFlow();
				previousBorrowedCapital[counter] = cFPeriod
						.getCapitalStock();
				counter++;
			}

			double[] expectedCF = timeseries.getExpectedValues(previousValues,
					project.getRelevantPastPeriods(),
					project.getPeriodsToForecast());
			double[] expectedBC = timeseries.getExpectedValues(
					previousBorrowedCapital, project.getRelevantPastPeriods(),
					project.getPeriodsToForecast());
			CashFlowPeriodContainer cFContainer = new CashFlowPeriodContainer();
			for (int i = 0; i < expectedCF.length; i++) {
				CashFlowPeriod cfPeriod = new CashFlowPeriod(
						project.getBasisYear() + (i));
				cfPeriod.setFreeCashFlow(expectedCF[i]);
				cfPeriod.setCapitalStock(expectedBC[i]);
				cFContainer.getPeriods().add(cfPeriod);
			}
			TreeSet<CashFlowPeriodContainer> periodContainer = new TreeSet<CashFlowPeriodContainer>();
			periodContainer.add(cFContainer);
			resultContainer = new StochasticResultContainer(periodContainer);

		}
		return resultContainer;

	}

	/**
	 * @author Kai Westerholz
	 */
	@Override
	public StochasticResultContainer calculate(Project project,
			CallbackInterface callback) throws InterruptedException,
			ConsideredPeriodsOfPastException, VarianceNegativeException,
			StochasticMethodException {

		TreeSet<AbstractPeriodContainer> resultPeriods = new TreeSet<AbstractPeriodContainer>();
		StochasticResultContainer resultContainer = null;

		/**
		 * Die Zeitreihenanalyse kann nur durchgefuehrt werden, wenn die Anzahl
		 * der beruecksichtigten Vergangenheitsperioden kleiner ist als die
		 * Anzahl der eingegebenen Vergangenheitsperioden
		 */
		if (project.getRelevantPastPeriods() > project.getStochasticPeriods()
				.getPeriods().size() - 1) {
			logger.debug("Anzahl der betrachteten Perioden der Vergangenheit ist zu groß!");
			throw new ConsideredPeriodsOfPastException(
					"Die Anzahl der betrachteten Perioden der Vergangenheit muss kleiner sein als die Azahl der beobachteten Perioden.");
		}

		if (project.getStochasticPeriods().getPeriods().first() instanceof CashFlowPeriod) {
			// Nachfolgend wird die Zeitreihenanalyse fuer CashFlowPerioden
			// ausgefuehrt
			TreeSet<? super CashFlowPeriodContainer> cFResultContainer = resultPeriods;

			double[] previousValues = new double[project.getStochasticPeriods()
					.getPeriods().size()];
			double[] previousBC = new double[project.getStochasticPeriods()
					.getPeriods().size()];
			AnalysisTimeseries timeseries = new AnalysisTimeseries();
			int counter = 0;

			// Umwandlung der Perioden in ein Double-Arrays
			for (CashFlowPeriod cFPeriod : (TreeSet<CashFlowPeriod>) project
					.getStochasticPeriods().getPeriods()) {
				previousValues[counter] = cFPeriod.getFreeCashFlow();
				previousBC[counter] = cFPeriod.getCapitalStock();
				counter++;
			}
			// Durchfuehrung der Zeitreihenanalyse
			double[][] resultTimeseries = timeseries.calculate(previousValues,
					project.getRelevantPastPeriods(),
					project.getPeriodsToForecast(), project.getIterations(),
					callback);
			double[][] resultTimeseriesBorrowedCapital = timeseries.calculate(
					previousBC, project.getRelevantPastPeriods(),
					project.getPeriodsToForecast(), project.getIterations(),
					callback);

			for (int iteration = 0; iteration < project.getIterations(); iteration++) {
				CashFlowPeriodContainer cFContainer = new CashFlowPeriodContainer();
				for (int periodToForecast = 0; periodToForecast < resultTimeseries.length; periodToForecast++) {
					CashFlowPeriod cfPeriod = new CashFlowPeriod(
							project.getBasisYear() + (periodToForecast + 1));
					cfPeriod.setFreeCashFlow(resultTimeseries[periodToForecast][iteration]);
					cfPeriod.setCapitalStock(resultTimeseriesBorrowedCapital[periodToForecast][iteration]);
					cFContainer.getPeriods().add(cfPeriod);
				}
				cFResultContainer.add(cFContainer);
			}
			StochasticResultContainer src = new StochasticResultContainer(
					resultPeriods);

			if (callback != null) {
				callback.onComplete(src);
			}
			return src;

		} else if (project.getStochasticPeriods().getPeriods().first() instanceof AggregateCostMethodPeriod) {
			// nachfolgend wird die Zeitreihenanalyse fuer alle Posten der
			// GesamtkostenPeriode einzeln durchgefuehrt
			AnalysisTimeseries timeseries = new AnalysisTimeseries();
			TreeSet<? super AggregateCostMethodBalanceSheetPeriodContainer> aCMPResultContainer = resultPeriods;
			// Allgemeine Bilanzposten AusgangsArrys
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
			// Allgemeine Bilanzposten Ergebnis-Arrays
			double[][] immaterialFortuneResult = new double[project
					.getPeriodsToForecast()][project.getIterations()];
			double[][] propertyValueResult = new double[project
					.getPeriodsToForecast()][project.getIterations()];
			double[][] financialValueResult = new double[project
					.getPeriodsToForecast()][project.getIterations()];
			double[][] equityResult = new double[project.getPeriodsToForecast()][project
					.getIterations()];
			double[][] provisionsResult = new double[project
					.getPeriodsToForecast()][project.getIterations()];
			double[][] supliesResult = new double[project
					.getPeriodsToForecast()][project.getIterations()];
			double[][] claimsResult = new double[project.getPeriodsToForecast()][project
					.getIterations()];
			double[][] stocksResult = new double[project.getPeriodsToForecast()][project
					.getIterations()];
			double[][] cashAssetsResult = new double[project
					.getPeriodsToForecast()][project.getIterations()];
			double[][] borrowedCapitalResult = new double[project
					.getPeriodsToForecast()][project.getIterations()];
			// spezielle Ausgangsarrays
			double[] salesRevenue = new double[project.getStochasticPeriods()
					.getPeriods().size()];
			double[] otherBusinessRevenue = new double[project
					.getStochasticPeriods().getPeriods().size()];
			double[] internallyProducedAndCapitalizedAssets = new double[project
					.getPeriods().size()];
			double[] materialCosts = new double[project.getStochasticPeriods()
					.getPeriods().size()];
			double[] humanCapitalCosts = new double[project
					.getStochasticPeriods().getPeriods().size()];
			double[] writeDowns = new double[project.getStochasticPeriods()
					.getPeriods().size()];
			double[] otherBusinessCosts = new double[project
					.getStochasticPeriods().getPeriods().size()];
			double[] interestAndOtherCosts = new double[project
					.getStochasticPeriods().getPeriods().size()];
			// spezielle Ergebnisarrays
			double[][] salesRevenueResult = new double[project
					.getPeriodsToForecast()][project.getIterations()];
			double[][] otherBusinessRevenueResult = new double[project
					.getPeriodsToForecast()][project.getIterations()];
			double[][] internallyProducedAndCapitalizedAssetsResult = new double[project
					.getPeriodsToForecast()][project.getIterations()];
			double[][] materialCostsResult = new double[project
					.getPeriodsToForecast()][project.getIterations()];
			double[][] humanCapitalCostsResult = new double[project
					.getPeriodsToForecast()][project.getIterations()];
			double[][] writeDownsResult = new double[project
					.getPeriodsToForecast()][project.getIterations()];
			double[][] otherBusinessCostsResult = new double[project
					.getPeriodsToForecast()][project.getIterations()];
			double[][] interestAndOtherCostsResult = new double[project
					.getPeriodsToForecast()][project.getIterations()];

			// Erstellung der einzelnen Eingabe Arrays
			int counter = 0;
			for (AggregateCostMethodPeriod aCMPeriod : (TreeSet<AggregateCostMethodPeriod>) project
					.getStochasticPeriods().getPeriods()) {
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
				borrowedCapital[counter] = aCMPeriod.getCapitalStock();
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
			// Durchfuehrung der Zeitriehenanalyse fuer jeden einzelnen
			// Bilanzposten
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
			// Erstellung der Ausgabecontainer und Integration in den
			// StochasticResultContainer
			for (int iteration = 0; iteration < project.getIterations(); iteration++) {
				AggregateCostMethodBalanceSheetPeriodContainer aCMContainer = new AggregateCostMethodBalanceSheetPeriodContainer();
				for (int forecast = 0; forecast < project
						.getStochasticPeriods().getPeriods().size(); forecast++) {
					AggregateCostMethodPeriod aCMPeriod = new AggregateCostMethodPeriod(
							project.getBasisYear() + (forecast + 1));
					aCMPeriod
							.setImmaterialFortune(immaterialFortuneResult[forecast][iteration]);
					aCMPeriod
							.setPropertyValue(propertyValueResult[forecast][iteration]);
					aCMPeriod
							.setFinancialValue(financialValueResult[forecast][iteration]);
					aCMPeriod
							.setCapitalStock(borrowedCapitalResult[forecast][iteration]);
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
				aCMPResultContainer.add(aCMContainer);
			}
			StochasticResultContainer src = new StochasticResultContainer(
					resultPeriods);
			if (callback != null) {
				callback.onComplete(src);
			}
			resultContainer = src;

		} else if (project.getStochasticPeriods().getPeriods().first() instanceof CostOfSalesMethodPeriod) {
			// nachfolgend wird die Zeitreihenanalyse fuer alle Posten der
			// UmsatzkostenPeriode einzeln durchgefuehrt
			AnalysisTimeseries timeseries = new AnalysisTimeseries();
			TreeSet<? super CostOfSalesMethodPeriodContainer> cOSResultContainer = resultPeriods;
			// Arrays der Ausgangswerte
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
			// Arrays fuer die Rueckgabe
			double[][] immaterialFortuneResult = new double[project
					.getPeriodsToForecast()][project.getIterations()];
			double[][] propertyValueResult = new double[project
					.getPeriodsToForecast()][project.getIterations()];
			double[][] financialValueResult = new double[project
					.getPeriodsToForecast()][project.getIterations()];
			double[][] equityResult = new double[project.getPeriodsToForecast()][project
					.getIterations()];
			double[][] provisionsResult = new double[project
					.getPeriodsToForecast()][project.getIterations()];
			double[][] supliesResult = new double[project
					.getPeriodsToForecast()][project.getIterations()];
			double[][] claimsResult = new double[project.getPeriodsToForecast()][project
					.getIterations()];
			double[][] stocksResult = new double[project.getPeriodsToForecast()][project
					.getIterations()];
			double[][] cashAssetsResult = new double[project
					.getPeriodsToForecast()][project.getIterations()];
			double[][] borrowedCapitalResult = new double[project
					.getPeriodsToForecast()][project.getIterations()];

			// Befuellung der Ausgangsarrays
			int counter = 0;
			for (CostOfSalesMethodPeriod cOSPeriod : (TreeSet<CostOfSalesMethodPeriod>) project
					.getStochasticPeriods().getPeriods()) {

				borrowedCapital[counter] = cOSPeriod.getCapitalStock();
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
			// Aufruf der Zeitreihenanalyse fuer die Einzelnen Bilanzposten
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

			// Erstellung des Rueckgabecontainers aus den einzelnen
			// Ergebnisarrays
			for (int iteration = 0; iteration < project.getIterations(); iteration++) {
				CostOfSalesMethodPeriodContainer cOSContainer = new CostOfSalesMethodPeriodContainer();
				for (int forecast = 0; forecast < project
						.getStochasticPeriods().getPeriods().size(); forecast++) {
					CostOfSalesMethodPeriod cOSPeriod = new CostOfSalesMethodPeriod(
							project.getBasisYear() + (forecast + 1));

					cOSPeriod
							.setImmaterialFortune(immaterialFortuneResult[forecast][iteration]);
					cOSPeriod
							.setPropertyValue(propertyValueResult[forecast][iteration]);
					cOSPeriod
							.setFinancialValue(financialValueResult[forecast][iteration]);
					cOSPeriod
							.setCapitalStock(borrowedCapitalResult[forecast][iteration]);
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
				cOSResultContainer.add(cOSContainer);
			}
			resultContainer = new StochasticResultContainer(resultPeriods);

		}
		if (callback != null) {
			callback.onComplete(resultContainer);
		}
		return resultContainer;
	}

	@Override
	public Boolean getImplemented() {
		return true;
	}

}
