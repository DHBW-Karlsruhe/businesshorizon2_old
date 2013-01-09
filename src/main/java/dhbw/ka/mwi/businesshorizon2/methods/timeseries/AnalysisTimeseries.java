package dhbw.ka.mwi.businesshorizon2.methods.timeseries;

import org.apache.log4j.Logger;

import cern.colt.list.DoubleArrayList;
import cern.colt.matrix.DoubleFactory2D;
import cern.colt.matrix.DoubleMatrix2D;
import cern.colt.matrix.linalg.LUDecomposition;
import cern.jet.stat.Descriptive;
import dhbw.ka.mwi.businesshorizon2.methods.AbstractStochasticMethod;
import dhbw.ka.mwi.businesshorizon2.methods.Callback;

/**
 * Diese Klasse stellt die Methoden zur Verfügung, die benötigt werden, um die
 * Zeitreihenanalyse durch zu führen. Sie baut auf der YuleWalkerGleichung und
 * implementiert das AR-Modell.
 * 
 * @author Kai Westerholz
 * 
 */

public class AnalysisTimeseries extends AbstractStochasticMethod {
	private static final long serialVersionUID = 1L;
	private Logger logger = Logger.getLogger("AnalysisTimeseries.class");
	private double variance;
	private DoubleArrayList DoubleArrayListTimeseries;
	private DoubleMatrix2D matrixValutaions;
	private double yuleWalkerVariance;

	public String getName() {
		return "Zeitreihenanalyse";
	}

	public int getOrderKey() {
		return 1;
	}

	public double getVariance() {
		return variance;
	}

	/**
	 * Diese Methode berechnet die Varianz der Zeitreihe
	 * 
	 * @authoer Kai Westerholz
	 * 
	 * @return double Varianz der Zeitreihe
	 */
	private double calculateVariance(DoubleArrayList DoubleArrayListTimeseries) {
		double variance = Descriptive.variance(
				DoubleArrayListTimeseries.size(),
				Descriptive.sum(DoubleArrayListTimeseries),
				Descriptive.sumOfSquares(DoubleArrayListTimeseries));
		logger.debug("Variance of Timeseries calculated.");
		return variance;
	}

	/**
	 * Diese Methode berechnet die Autokorrelation zum angegebenen Zeit Lag.
	 * 
	 * @author Kai Westerholz
	 * 
	 * @param lag
	 * @return double AutoKorrelation
	 */
	private double calculateAutoCorrelation(int lag) {
		if (lag == 0) {
			return 1.0;
		} else {
			double tmp = Descriptive.autoCorrelation(DoubleArrayListTimeseries,
					lag, Descriptive.mean(DoubleArrayListTimeseries),
					this.variance);
			return tmp;
		}
	}

	/**
	 * Diese Methode stellt das Lineare Gleichungssystem auf, das benötigt wird,
	 * um die Schätzwerte ci der Zeitreihenanalyse zu erhalten und löst dieses.
	 * Die berechneten Werte
	 * 
	 * @param consideredPeriodsOfPast
	 * @return Matrix der C Werte
	 * @author Kai Westerholz
	 */
	private DoubleMatrix2D calculateValuations(int consideredPeriodsOfPast) {

		DoubleMatrix2D matrixValuations = DoubleFactory2D.dense.make(
				consideredPeriodsOfPast, consideredPeriodsOfPast);
		for (int i = 0; i < consideredPeriodsOfPast; i++) { // Aktuelle
															// Zeile
			for (int j = 0; j < consideredPeriodsOfPast; j++) {// Aktuelle
																// Spalte

				matrixValuations.set(i, j,
						calculateAutoCorrelation(Math.abs((int) (i - j))));

			}
		}
		DoubleMatrix2D matrixERG = DoubleFactory2D.dense.make(
				(int) (consideredPeriodsOfPast), 1);
		for (int i = 1; i <= consideredPeriodsOfPast; i++) {
			matrixERG.set(i - 1, 0, calculateAutoCorrelation(i));
		}
		LUDecomposition lUDecomp = new LUDecomposition(matrixValuations);
		DoubleMatrix2D matrixC = null;

		try {
			matrixC = lUDecomp.solve(matrixERG);
			logger.debug("C-Values of Yule-Walker-Equitation calculated.");
		} catch (IllegalArgumentException exception) {
			// TODO: Exception Handling
			logger.debug("Calculation of C-Values failed!");

		}
		return matrixC;
	}

	/**
	 * Diese Methode berechnet die Varianz auf Basis der YuleWalkerGleichung.
	 * Die Varianz wird für die Berechnung des weißen Rauschens benötigt.
	 * 
	 * @param matrix
	 *            Matrix aus der die Varianz berechnet werden soll
	 * @return double Varianz aus YuleWalkerGleichung
	 * 
	 * @author Kai Westerholz
	 */
	private double calculateMatrixVariance(int consideredPeriodsOfPast) {

		this.matrixValutaions = calculateValuations(consideredPeriodsOfPast);
		double variance = calculateAutoCorrelation(0);
		for (int i = 1; i <= consideredPeriodsOfPast; i++) {
			variance -= this.matrixValutaions.get(i - 1, 0)
					* calculateAutoCorrelation(i);
		}
		logger.debug("Variance of Yule-Walker-Equitation calculated.");
		return variance;
	}

	/**
	 * Diese Methode berechnet den prognostizierten Wert für die Periode auf
	 * Basis der beobachteten Zeitreihe
	 * 
	 * @author Kai Westerholz
	 */

	@Override
	public double[][] calculate(double[] previousValues,
			int consideredPeriodsOfPast, int periodsToForecast,
			int numberOfIterations, Callback callback)
			throws InterruptedException {
		// vorbereitene Initialisierung
		double[][] returnValues = new double[periodsToForecast][numberOfIterations];
		int progress_complete = periodsToForecast
				* (consideredPeriodsOfPast + numberOfIterations);
		int progress = 0;

		// Trendbereinigung der Zeitreihe
		CalculateTide tide = new CalculateTide();
		previousValues = tide.reduceTide(previousValues);

		/**
		 * Übertraugung der Werte der Zeitreihe in eine DoubleArrayList. Diese
		 * wird von der COLT Bibliothek verwendet zur Lösung der Matrix.
		 */

		this.DoubleArrayListTimeseries = new DoubleArrayList();
		for (int i = 0; i < previousValues.length; i++) {
			this.DoubleArrayListTimeseries.add(previousValues[i]);
		}

		// Start der zur Prognose benötigten Berechnungen
		this.variance = this.calculateVariance(this.DoubleArrayListTimeseries);
		this.yuleWalkerVariance = this
				.calculateMatrixVariance(consideredPeriodsOfPast);

		WhiteNoise whiteNoise = new WhiteNoise(this.yuleWalkerVariance);

		// Start der Prognose
		for (int forecast = 1; forecast <= periodsToForecast; forecast++) {
			double[] forecastsForPeriod = new double[numberOfIterations];
			double equalizedValuePerPeriod = 0.0;
			double previousValue = 0.0;

			progress += consideredPeriodsOfPast;
			Thread.currentThread().isInterrupted();
			double newTide = tide.getTideValue(forecast
					+ consideredPeriodsOfPast);

			for (int iterationStep = 0; iterationStep < numberOfIterations; iterationStep++) {

				for (int past = 1; past <= consideredPeriodsOfPast; past++) {

					/**
					 * Für die Formel des AR-Model wird eine bestimmte Anzahl an
					 * bisherigen Perioden betrachtet. Der Term (i-j) gibt an
					 * wie viele dieser Perioden aus den Beobachtungen gespeißt
					 * werden. Ist der Termn (i-j) kleiner als 1 werden
					 * Beobachtungswerte herangezogen. Ansonsten werden bereits
					 * prognostizierte Werte verwendet Da das Array der
					 * Beobachtungswerte den ältesten Wert beim Index = 0 hat
					 * wird der letzte Werte abzüglich der bereits betraachteten
					 * Werte benötigt.
					 */
					if ((forecast - past) < 1) {
						int oldIndex = previousValues.length - 1
								- Math.abs(forecast - past);
						previousValue = previousValues[oldIndex];
					} else {
						previousValue = returnValues[forecast - 1][iterationStep];
					}

					equalizedValuePerPeriod += matrixValutaions
							.get(past - 1, 0) * previousValue;
				}

				// Eigentliche Berechnung
				forecastsForPeriod[iterationStep] = (double) (whiteNoise
						.getWhiteNoiseValue() + (newTide - equalizedValuePerPeriod));
				// Vergleichswert ohne Weißes Rauschen
				forecastsForPeriod[iterationStep] = (double) ((newTide - equalizedValuePerPeriod));

				if (iterationStep % 200 == 0) {
					progress += iterationStep;
					// callback.onProgressChange((float) (progress /
					// progress_complete));
					Thread.currentThread().isInterrupted();
				}
			}
			returnValues[forecast - 1] = forecastsForPeriod;
			logger.debug("Period " + forecast + "  of " + periodsToForecast
					+ " predicted.");

		}
		return returnValues;
	}

	public static void main(String[] args) {
		AnalysisTimeseries al = new AnalysisTimeseries();
		double[][] result = null;
		double[] timeseries = new double[6];
		timeseries[0] = 130594000.00;
		timeseries[1] = 147552000.00;
		timeseries[2] = 144040000.00;
		timeseries[3] = 146004000.00;
		timeseries[4] = 154857000.00;
		timeseries[5] = 162117000.00;
		Callback callback = null;
		try {
			result = al.calculate(timeseries, 5, 2, 100000, callback);
			System.out.println(result[0][0]);
			System.out.println(result[1][0]);
			System.out.println("1.6686479843069828E8 - 1.650882630275696E8");
		} catch (InterruptedException e) {
			System.out.println("Error al.calculate");
			System.out.println(e.getClass());
			System.out.println(e.getStackTrace());
			System.out.println(e.getMessage());
		}

	}
}
