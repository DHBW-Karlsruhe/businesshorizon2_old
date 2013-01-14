package dhbw.ka.mwi.businesshorizon2.methods.timeseries;

import java.util.TreeSet;

import org.apache.log4j.Logger;

import dhbw.ka.mwi.businesshorizon2.methods.AbstractStochasticMethod;
import dhbw.ka.mwi.businesshorizon2.methods.Callback;
import dhbw.ka.mwi.businesshorizon2.models.AbstractPeriodContainer;
import dhbw.ka.mwi.businesshorizon2.models.AggregateCostMethodBalanceSheetPeriodContainer;
import dhbw.ka.mwi.businesshorizon2.models.CashFlowPeriod;
import dhbw.ka.mwi.businesshorizon2.models.CashFlowPeriodContainer;
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
		StochasticResultContainer result = new StochasticResultContainer();
		AbstractPeriodContainer periods = (AbstractPeriodContainer) project
				.getPeriods();
		if (project.getRelevantPastPeriods() > periods.getPeriods().size() - 1) {
			logger.debug("Anzahl der betrachteten Perioden der Vergangenheit ist zu groß!");
			throw new ConsideredPeriodsOfPastException(
					"Die Anzahl der betrachteten Perioden der Vergangenheit muss kleiner sein als die Azahl der beobachteten Perioden.");
		}
		if (periods instanceof CashFlowPeriodContainer) {
			CashFlowPeriodContainer cFPeriods = (CashFlowPeriodContainer) periods
					.getPeriods();
			double[] previousValues = new double[cFPeriods.getPeriods().size()];
			AnalysisTimeseries timeseries = new AnalysisTimeseries();
			int counter = 0;
			for (CashFlowPeriod cFPeriod : cFPeriods.getPeriods()) {
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
							project.getBasisYear() + periodToForecast);
					cfPeriod.setFreeCashFlow(resultTimeseries[periodToForecast][iteration]);
					cFContainer.getPeriods().add(cfPeriod);
				}
				TreeSet<CashFlowPeriodContainer> apc = (TreeSet<CashFlowPeriodContainer>) result
						.getPeriodContainers();
				apc.add(cFContainer);
			}
		} else if (periods instanceof AggregateCostMethodBalanceSheetPeriodContainer) {
			AggregateCostMethodBalanceSheetPeriodContainer balanceSheetContainter = (AggregateCostMethodBalanceSheetPeriodContainer) periods
					.getPeriods();

		} else if (periods instanceof CostOfSalesMethodPeriodContainer) {
			CostOfSalesMethodPeriodContainer costOfSalesContainer = (CostOfSalesMethodPeriodContainer) periods
					.getPeriods();
		}
		return result;
	}
}
