package dhbw.ka.mwi.businesshorizon2.methods;

import dhbw.ka.mwi.businesshorizon2.models.BalanceSheetItemPeriod;
import dhbw.ka.mwi.businesshorizon2.models.CashFlowPeriod;
import dhbw.ka.mwi.businesshorizon2.models.PeriodenContainer;

public class StochasticCalculator {

	public static interface Callback {
		public void onComplete(double[] result);

		public void onProgressChange(float progress);
	}

	public PeriodenContainer<BalanceSheetItemPeriod> periodsBSI;
	public PeriodenContainer<CashFlowPeriod> periodsCF;

}
