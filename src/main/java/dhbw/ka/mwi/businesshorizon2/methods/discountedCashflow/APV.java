package dhbw.ka.mwi.businesshorizon2.methods.discountedCashflow;

import dhbw.ka.mwi.businesshorizon2.models.AbstractPeriodContainer;
import dhbw.ka.mwi.businesshorizon2.models.PeriodInterface;
import dhbw.ka.mwi.businesshorizon2.models.StochasticResultContainer;

public class APV extends RatingMethods {

	@Override
	double[] calculateCompanyValue(StochasticResultContainer container) {

		for (AbstractPeriodContainer i : container.getPeriodContainers()) {
			int length = i.getPeriods().size();
			double[][][] array = new double[length][1][1];
			for (PeriodInterface period : i.getPeriods()) {

			}
		}
		return null;
	}
}
