package dhbw.ka.mwi.businesshorizon2.methods;

import java.security.InvalidParameterException;

/**
 * Diese Klasse ist zur eigentlichen Ausfuehrung der Berechnungen gedacht. Dabei
 * wird fuer die Berechnung ein eigener Thread verwendet.
 * 
 * @author Christian Gahlert
 * 
 */
public class MethodRunner extends Thread {

	/**
	 * Dieses Interface beschreibt das Callback, das zur
	 * Informations-Uebertragung zwischen den Threads waehrend und nach der
	 * Berechnung verwendet wird.
	 * 
	 * @author Christian Gahlert
	 * 
	 */
	public static interface Callback {
		public void onComplete(double[] result);

		public void onProgressChange(float progress);
	}

	private AbstractStochasticMethod method;
	private double[] periods;
	private StochasticCalculator.Callback callback;

	/**
	 * Der Konstruktor - diesem sollte die zur Berechnung zu verwendende Methode
	 * sowie die Perioden uebergeben werden. Zuletzt sollte noch das
	 * Callback-Objekt uebergeben werden.
	 * 
	 * @author Christian Gahlert
	 * @param method
	 *            Die zu verwendende Methode
	 * @param periods
	 *            Die zu berechnenden Perioden
	 * @param callback
	 *            Das Callback
	 */
	public MethodRunner(AbstractStochasticMethod method, double[] periods,
			StochasticCalculator.Callback callback) {
		if (method == null || periods == null || callback == null) {
			throw new InvalidParameterException(
					"No null parameters are allowed here");
		}

		this.method = method;
		this.periods = periods;
		this.callback = callback;
	}

	/**
	 * Diese Methode startet die Ausfuehrung. Diese sollte nicht direkt
	 * aufgerufen werden. Stattdessen sollte die Ausfuehrung des Threads mit
	 * MethodRunner.start() gestartet werden. Anschliessend wird dann innerhalb
	 * des neuen Threads diese Methode aufgerufen.
	 * 
	 * @author Christian Gahlert
	 */
	@Override
	public void run() {
		try {
			double[] result = method.calculate(periods, 1, 1, 1, callback);
			callback.onComplete(result);
		} catch (InterruptedException e) {
			callback.onComplete(null);
		}
	}
}
