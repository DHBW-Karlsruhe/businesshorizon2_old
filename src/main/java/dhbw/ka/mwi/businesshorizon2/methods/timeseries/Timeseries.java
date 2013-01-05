package dhbw.ka.mwi.businesshorizon2.methods.timeseries;

import dhbw.ka.mwi.businesshorizon2.methods.AbstractStochasticMethod;

/**
 * Dies ist die Methoden-Klasse der Zeitreihenanalyse. Hier sollte keine grosse
 * Berechnung stattfinden. Diese Klasse ist lediglich als Verbindungs-Klasse zu
 * der eigentlichen Berechnungsmethode zu verstehen.
 * 
 * @author Christian Gahlert
 * 
 */
public class Timeseries extends AbstractStochasticMethod {
	private static final long serialVersionUID = 1L;

	/**
	 * {@inheritDoc}
	 * 
	 * @author Christian Gahlert
	 */
	@Override
	public String getName() {
		return "Zeitreihenanalyse";
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @author Christian Gahlert
	 */
	@Override
	public int getOrderKey() {
		return 1;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @author Christian Gahlert
	 */

	@Override
	public double[] calculate(
			double[] previousValues,
			int consideredPeriodsofPast,
			int periodsToForecast,
			int numberOfIterations,
			dhbw.ka.mwi.businesshorizon2.methods.timeseries.TimeseriesCalculator.Callback callback)
			throws InterruptedException {
		// TODO Auto-generated method stub
		return null;
	}

}
