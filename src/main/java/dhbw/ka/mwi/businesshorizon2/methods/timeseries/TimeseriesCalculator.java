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
	private static final Logger logger = Logger
			.getLogger("TimeseriesCalculator.class");
	public AbstractPeriodContainer periodsBSI;

	private StochasticResultContainer expectedCashFlows;

	@Override
	public String getName() {
		return "Zeitreihenanalyse";
	}

	@Override
	public int getOrderKey() {
		return 1;
	}

	/**
	 * Diese Methode speichert pro Periode den Erwartungswert aller
	 * prognostizierten Casfhlows in einen StochasticResultContainer.
	 * 
	 * @author Marcel Rosenberger
	 * 
	 * @param cashflows
	 *            die erwarteten Cashflows
	 * @param project
	 *            das Projekt für das die Zeitreihenanalyse durchgeführt wurde
	 * 
	 * @return StochasticResultContainer der Container, in dem die Prognosewerte
	 *         enthalten sind
	 * 
	 */
	public void setExpectedCashFlows(
			double[] cashflows, Project project)
			throws StochasticMethodException {

		StochasticResultContainer resultContainer;

		CashFlowPeriodContainer cFContainer = new CashFlowPeriodContainer();

		// wird pro Periode einmal durchlaufen und berechnet jeweils den Mittelwert
		for (int i = 0; i < cashflows.length; i++) {
			CashFlowPeriod cfPeriod = new CashFlowPeriod(project.getBasisYear()
					+ (i));
			//als Cashflow der Periode wird der Erwartungswert eingesetzt
			cfPeriod.setFreeCashFlow(cashflows[i]);
			cFContainer.getPeriods().add(cfPeriod);
		}

		TreeSet<CashFlowPeriodContainer> periodContainer = new TreeSet<CashFlowPeriodContainer>();
		periodContainer.add(cFContainer);
		resultContainer = new StochasticResultContainer(periodContainer);

		this.expectedCashFlows = resultContainer;
		}

	/**
	 * Diese Methode ruft die Zeitreihenanalyse auf und speichert die
	 * enthaltenen Werte anschließend in einem StochasticResultContainer.
	 * 
	 * @author Kai Westerholz, Marcel Rosenberger, Maurizio Di Nunzio
	 * 
	 * @param project
	 *            das Projekt für das die Zeitreihenanalyse durchgeführt werden
	 *            soll
	 * @param callback
	 *            das CallbackInterface, das für die Kommunikation zwischen den
	 *            Threads zuständig ist
	 * @return StochasticResultContainer der Container, in dem die Prognosewerte
	 *         enthalten sind
	 * 
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
			
			TreeSet<CashFlowPeriod> alleperioden = (TreeSet<CashFlowPeriod>) project
					.getStochasticPeriods().getPeriods();
			
			TreeSet<? super CashFlowPeriodContainer> cFResultContainer = resultPeriods;

			logger.debug("Übergebene Periodenanzahl: " + project
					.getStochasticPeriods().getPeriods().size());
			double[] previousCashflows = new double[project
					.getStochasticPeriods().getPeriods().size()];
			double[] previousFremdkapital = new double[project
					.getStochasticPeriods().getPeriods().size()];
			AnalysisTimeseries timeseries = new AnalysisTimeseries();
			int counter = 0;

			// Umwandlung der Perioden in ein Double-Arrays
			for (CashFlowPeriod cFPeriod : (TreeSet<CashFlowPeriod>) project
					.getStochasticPeriods().getPeriods()) {
				previousFremdkapital[counter] = cFPeriod.getCapitalStock();				
				logger.debug("Fremdkapital: " + previousFremdkapital[counter]);
				previousCashflows[counter] = cFPeriod.getFreeCashFlow();
				logger.debug("Cashflow: " + previousCashflows[counter]);
				counter++;
			}
			// Durchfuehrung der Zeitreihenanalyse
			double[][] resultTimeseriesBorrowedCapital = timeseries.calculate(
					previousFremdkapital, project.getRelevantPastPeriods(),
					project.getPeriodsToForecast(), project.getIterations(),
					callback);
			double[][] resultTimeseries = timeseries.calculate(
					previousCashflows, project.getRelevantPastPeriods(),
					project.getPeriodsToForecast(), project.getIterations(),
					callback);
		
			// berechnet die zu erwartenden Cashflows
			setExpectedCashFlows(timeseries.getErwarteteCashFlows(), project);
			logger.debug("Zu erwartende Cashflows berechnet.");
			// Diese Schleife wird sooft durchlaufen, wie die Zeitreihenanalyse
			// durchgeführt wurde
			for (int prognosedurchlauf = 0; prognosedurchlauf < (project
					.getIterations() - 1); prognosedurchlauf++) {
				/*
				 * Pro Schleifen- (und somit auch Prognose-)durchlauf wird ein
				 * neuer Cashflow-Period-Container (= eine Zeitreihe von
				 * Cashflow-Perioden) erstellt. In einer inneren Schleife werden
				 * diesem dann soviele Perioden hinzugefügt wie durch die
				 * Zeitreihenanalyse prognostiziert werden sollten. Die Menge an
				 * Cashflow-Period-Container wird anschließend in einen
				 * übergreifenden StochasticResultContainer eingefügt.
				 */

				CashFlowPeriodContainer cFContainer = new CashFlowPeriodContainer();
				// die innere Schleife wird sooft durchlaufen wie Perioden
				// prognostiziert werden sollen
				// resultTimeseries.length = Anzahl der zu prognostizierenden
				// Perioden (Wert im ersten Array)
				for (int periode = 0; periode < resultTimeseries[0].length; periode++) {

					// eine neue Cashflow-Periode wird erstellt und mit dem Jahr
					// der aktuellen Periode initialisiert
					CashFlowPeriod cfPeriod = new CashFlowPeriod(
							project.getBasisYear() + (periode + 1));
					// der Cashflow des aktuellen Prognosedurchlauf pro Periode
					// wird gesetzt
					cfPeriod.setFreeCashFlow(resultTimeseries[prognosedurchlauf][periode]);
					// das Fremdkapital des aktuellen Prognosedurchlauf pro
					// Periode wird gesetzt
					cfPeriod.setCapitalStock(resultTimeseriesBorrowedCapital[prognosedurchlauf][periode]);
					// die Periode mit den gesetzten Werten wird dem
					// Cashflow-Perioden-Container hinzugefügt
					cFContainer.getPeriods().add(cfPeriod);
				}
				// der Cashflow-Perioden-Container wird mit all seinen Perioden
				// dem Cashflow-Result-Container
				// hinzugefügt.
				cFResultContainer.add(cFContainer);
			}
			logger.debug("Ergebnisse der Zeitreihenanalyse dem Result-Container hinzugefügt.");
			// der Cashflow-Result-Container wird in einen
			// StochasticResultContainer kopiert
			StochasticResultContainer src = new StochasticResultContainer(
					resultPeriods);

			// bereits auskommentierter Code vom Vorjahr, Verwendung noch unklar
			// if (callback != null) {
			// callback.onComplete(src);
			// }
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
			// if (callback != null) {
			// callback.onComplete(src);
			// }
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
		// if (callback != null) {
		// callback.onComplete(resultContainer);
		// }
		return resultContainer;
	}

	@Override
	public Boolean getImplemented() {
		return true;
	}

	public StochasticResultContainer getExpectedCashFlows() {
		return this.expectedCashFlows;
	}

}