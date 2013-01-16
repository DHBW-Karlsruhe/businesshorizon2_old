package dhbw.ka.mwi.businesshorizon2.methods.timeseries;

import java.text.DecimalFormat;

import org.apache.log4j.Logger;

import cern.colt.list.DoubleArrayList;
import cern.colt.matrix.DoubleFactory2D;
import cern.colt.matrix.DoubleMatrix2D;
import cern.colt.matrix.linalg.LUDecomposition;
import cern.jet.stat.Descriptive;
import dhbw.ka.mwi.businesshorizon2.methods.Callback;
import dhbw.ka.mwi.businesshorizon2.methods.StochasticMethodException;

/**
 * Diese Klasse stellt die Methoden zur Verfügung, die benötigt werden, um die
 * Zeitreihenanalyse durch zu führen. Sie baut auf der YuleWalkerGleichung und
 * implementiert das AR-Modell.
 * 
 * @author Kai Westerholz
 * 
 */

public class AnalysisTimeseries {// extends AbstractStochasticMethod {

	private Logger logger = Logger.getLogger(AnalysisTimeseries.class);
	private double variance;
	private DoubleArrayList DoubleArrayListTimeseries;
	private DoubleMatrix2D matrixValutaions;
	private double yuleWalkerVariance;
	private double mean;
	private double[] equalizedValues;

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
		return Descriptive.autoCorrelation(DoubleArrayListTimeseries, lag,
				this.mean, this.variance);

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
	private DoubleMatrix2D calculateValuations(int consideredPeriodsOfPast)
			throws StochasticMethodException {

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

			logger.debug("Calculation of C-Values failed!");
			throw new StochasticMethodException(exception.getMessage());

		}
		System.out.println(matrixC);
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
	private double calculateMatrixVariance(int consideredPeriodsOfPast)
			throws StochasticMethodException {

		this.matrixValutaions = calculateValuations(consideredPeriodsOfPast);
		double variance = calculateAutoCorrelation(0);
		for (int i = 1; i <= consideredPeriodsOfPast; i++) {
			variance -= this.matrixValutaions.get(i - 1, 0)
					* calculateAutoCorrelation(i);
		}
		if (variance < 0) {
			String errorMessage = "Exception: YuleWalker-Variance is negative!";
			logger.debug(errorMessage);
			throw new VarianceNegativeException(errorMessage);
		} else {
			logger.debug("Variance of Yule-Walker-Equitation calculated.");
			return variance;
		}
	}

	/**
	 * Für die Formel des AR-Model wird eine bestimmte Anzahl an bisherigen
	 * Perioden betrachtet. Der Term (i-j) gibt an wie viele dieser Perioden aus
	 * den Beobachtungen gespeißt werden. Ist der Termn (i-j) kleiner als 1
	 * werden Beobachtungswerte herangezogen. Ansonsten werden bereits
	 * prognostizierte Werte verwendet Da das Array der Beobachtungswerte den
	 * ältesten Wert beim Index = 0 hat wird der letzte Werte abzüglich der
	 * bereits betraachteten Werte benötigt. Die Werte werden
	 * zwischengespeichert und es wird überprüft, ob eine Berechnung nötig ist.
	 * 
	 * @author Kai Westerholz
	 * 
	 * @param consideredPeriodsOfPast
	 *            betrachtete Methoden der Vergangenheit
	 * @param forecast
	 *            zu prognostizierende Periode
	 * @param valuations
	 *            Gewichtungsparamter
	 * @param previousValues
	 *            trendbereinigte Beobachtungswerte;
	 * @return geglätteter Prognosewert
	 */
	private double calculateARModel(int consideredPeriodsOfPast, int forecast,
			DoubleMatrix2D valuations, double[] previousValues) {
		if (this.equalizedValues[forecast - 1] == 0) {

			double equalizedValuePerPeriod = 0;
			for (int past = 1; past <= consideredPeriodsOfPast; past++) {
				double previousValue;
				if ((forecast - past) < 1) {
					int oldIndex = previousValues.length - 1
							- Math.abs(forecast - past);
					previousValue = previousValues[oldIndex];
				} else {
					previousValue = this.equalizedValues[(forecast - past) - 1];
				}
				double add = valuations.get(past - 1, 0) * previousValue;
				equalizedValuePerPeriod += add;

			}

			this.equalizedValues[forecast - 1] = equalizedValuePerPeriod;
			logger.debug("Calculated constant forecast Value: "
					+ new DecimalFormat("0.00").format(equalizedValuePerPeriod));
			return equalizedValuePerPeriod;

		} else {
			return this.equalizedValues[forecast - 1];
		}
	}

	/**
	 * Diese Methode berechnet den prognostizierten Wert für die Periode auf
	 * Basis der beobachteten Zeitreihe
	 * 
	 * @author Kai Westerholz
	 */

	// @Override
	public double[][] calculate(double[] previousValues,
			int consideredPeriodsOfPast, int periodsToForecast,
			int numberOfIterations, Callback callback)
			throws InterruptedException, StochasticMethodException {

		// vorbereitene Initialisierung
		double[][] returnValues = new double[periodsToForecast][numberOfIterations];
		int progress_complete = periodsToForecast
				* (consideredPeriodsOfPast + numberOfIterations);
		int progress = 0;

		// Trendbereinigung der Zeitreihe wenn diese nicht stationär ist
		CalculateTide tide = new CalculateTide();
		boolean isStationary = StationaryTest.isStationary(previousValues);
		if (!isStationary) {
			previousValues = tide.reduceTide(previousValues);
		}
		/**
		 * Übertraugung der Werte der Zeitreihe in eine DoubleArrayList. Diese
		 * wird von der COLT Bibliothek verwendet zur Lösung der Matrix.
		 */

		this.DoubleArrayListTimeseries = new DoubleArrayList();
		for (int i = 0; i < previousValues.length; i++) {
			this.DoubleArrayListTimeseries.add(previousValues[i]);
		}
		logger.debug(DoubleArrayListTimeseries);

		// Start der zur Prognose benötigten Berechnungen
		this.mean = Descriptive.mean(DoubleArrayListTimeseries);
		this.variance = this.calculateVariance(this.DoubleArrayListTimeseries);
		this.yuleWalkerVariance = this
				.calculateMatrixVariance(consideredPeriodsOfPast);

		WhiteNoise whiteNoise = new WhiteNoise(this.yuleWalkerVariance);
		this.equalizedValues = new double[periodsToForecast];

		// Start der Prognose
		for (int forecast = 1; forecast <= periodsToForecast; forecast++) {
			double[] forecastsForPeriod = new double[numberOfIterations];
			double equalizedValuePerPeriod = 0.0;

			progress += consideredPeriodsOfPast;
			Thread.currentThread().isInterrupted();

			for (int iterationStep = 0; iterationStep < numberOfIterations; iterationStep++) {
				equalizedValuePerPeriod = 0;

				// Berechnung des konstanten Teils der Prognose
				equalizedValuePerPeriod = calculateARModel(
						consideredPeriodsOfPast, forecast, matrixValutaions,
						previousValues);

				// Eigentliche Berechnung
				if (!isStationary) {
					double newTide = tide.getTideValue(forecast
							+ previousValues.length - 1);
					forecastsForPeriod[iterationStep] = (double) (whiteNoise
							.getWhiteNoiseValue() + (newTide - equalizedValuePerPeriod));
					// Vergleichswert ohne Weißes Rauschen
					forecastsForPeriod[iterationStep] = (double) ((newTide - equalizedValuePerPeriod));
				} else {
					forecastsForPeriod[iterationStep] = (double) (whiteNoise
							.getWhiteNoiseValue() + equalizedValuePerPeriod);
				}

				if (iterationStep % 200 == 0) {
					progress += iterationStep;
					if (callback != null) {
						callback.onProgressChange((float) (progress / progress_complete));
					}
					Thread.currentThread().isInterrupted();
				}
			}
			equalizedValues[forecast - 1] = equalizedValuePerPeriod;

			returnValues[forecast - 1] = forecastsForPeriod;
			// logger.debug("Period " + forecast + "  of " + periodsToForecast
			// + " predicted.");
		}
		return returnValues;
	}

}
