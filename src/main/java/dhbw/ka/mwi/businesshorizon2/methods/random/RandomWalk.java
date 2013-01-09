package dhbw.ka.mwi.businesshorizon2.methods.random;

import dhbw.ka.mwi.businesshorizon2.methods.AbstractStochasticMethod;
import dhbw.ka.mwi.businesshorizon2.methods.Callback;

public class RandomWalk extends AbstractStochasticMethod {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String getName() {
		
		return "Random Walk";
	}

	@Override
	public int getOrderKey() {

		return 3;
	}

	@Override
	public double[] calculate(double[] previousValues,
			int consideredPeriodsofPast, int periodsToForecast,
			int numberOfIterations, Callback callback)
			throws InterruptedException {
		return null;
	}

	@Override
	public Boolean getImplemented() {
		return true;
	}

}
