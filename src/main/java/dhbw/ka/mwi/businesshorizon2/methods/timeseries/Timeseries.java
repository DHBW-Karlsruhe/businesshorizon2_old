package dhbw.ka.mwi.businesshorizon2.methods.timeseries;

import java.util.SortedSet;

import dhbw.ka.mwi.businesshorizon2.methods.AbstractStochasticMethod;
import dhbw.ka.mwi.businesshorizon2.methods.MethodRunner;
import dhbw.ka.mwi.businesshorizon2.methods.Result;
import dhbw.ka.mwi.businesshorizon2.models.Period;

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
	public Result calculate(SortedSet<Period> periods,
			MethodRunner.Callback callback) throws InterruptedException {
		for (int i = 0; i <= 100; i++) {
			Thread.sleep(100);
			callback.onProgressChange((float) i / 100);
		}

		return new Result();
	}

}
