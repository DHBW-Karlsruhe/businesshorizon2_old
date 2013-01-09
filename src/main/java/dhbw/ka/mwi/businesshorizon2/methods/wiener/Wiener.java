package dhbw.ka.mwi.businesshorizon2.methods.wiener;

import dhbw.ka.mwi.businesshorizon2.methods.AbstractStochasticMethod;
import dhbw.ka.mwi.businesshorizon2.methods.Callback;

/**
 * Dies ist bisher nur eine Beispiel-Klasse um die Verwendung von verschiedenen
 * Berechnungs- Methoden zu verdeutlichen.
 * 
 * @author Christian Gahlert
 * 
 */
public class Wiener extends AbstractStochasticMethod {
	private static final long serialVersionUID = 1L;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getOrderKey() {
		return 2;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double[][] calculate(double[] previousValues,
			int consideredPeriodsofPast, int periodsToForecast,
			int numberOfIterations, Callback callback)
			throws InterruptedException {
		// TODO Auto-generated method stub
		return null;
	}
}
