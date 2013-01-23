package dhbw.ka.mwi.businesshorizon2.methods.timeseries;

import java.text.DecimalFormat;

import org.apache.log4j.Logger;

import cern.colt.list.DoubleArrayList;
import cern.colt.matrix.DoubleFactory2D;
import cern.colt.matrix.DoubleMatrix2D;
import cern.colt.matrix.linalg.LUDecomposition;
import cern.jet.stat.Descriptive;
import dhbw.ka.mwi.businesshorizon2.methods.CallbackInterface;
import dhbw.ka.mwi.businesshorizon2.methods.StochasticMethodException;

/**
 * Diese Klasse stellt die Methoden zur Verfuegung, die benoetigt werden, um die
 * Zeitreihenanalyse durch zu fuehren. Sie baut auf der YuleWalkerGleichung auf
 * und implementiert das AR-Modell.
 * 
 * @author Kai Westerholz
 * 
 */

public class AnalysisTimeseries {

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
	 * Diese Methode stellt das Lineare Gleichungssystem auf, das benoetigt
	 * wird, um die Schaetzwerte ci der Zeitreihenanalyse zu erhalten und loest
	 * dieses. Die berechneten Werte werden benötigt um die Vergangenheitswerte
	 * zu gewichten.
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
		return matrixC;
	}

	/**
	 * Diese Methode berechnet die Varianz, die relevant ist für das weiße
	 * Rauschen. Die übergebene Zeitreihe muss stationaer sein.
	 * 
	 * @param double[] timeseries trendbereinigte Vergangenheitswerte
	 * @return double Varianz für das weiße Rauschen
	 * 
	 * @author Kai Westerholz
	 */
	private double calculateMatrixVariance(double[] timeseries) {

		double sum = 0;
		for (int i = 0; i < timeseries.length; i++) {
			sum += Math.pow(timeseries[i] - this.mean, 2) / timeseries.length;
		}
		logger.debug("" + Math.sqrt(sum));
		return Math.sqrt(sum);
	}

	/**
	 * Fuer die Formel des AR-Model wird eine bestimmte Anzahl an bisherigen
	 * Perioden betrachtet. Der Term (forecast - past) gibt an wie viele dieser
	 * Perioden aus den Beobachtungen gespeisst werden. Ist der Termn (forecast
	 * - past) kleiner als 1 werden Beobachtungswerte herangezogen. Ansonsten
	 * werden bereits prognostizierte Werte verwendet Da das Array der
	 * Beobachtungswerte den aeltesten Wert beim Index = 0 hat wird der letzte
	 * Werte abzueglich der bereits betrachteten Werte benoetigt. Die Werte
	 * werden zwischengespeichert und es wird ueberprueft, ob eine Berechnung
	 * noetig ist.
	 * 
	 * @author Kai Westerholz
	 * 
	 * @param consideredPeriodsOfPast
	 *            betrachtete Methoden der Vergangenheit
	 * @param forecast
	 *            zu prognostizierende Periode
	 * @param valuations
	 *            Gewichtungsparameter
	 * @param previousValues
	 *            trendbereinigte Beobachtungswerte;
	 * @return geglaetteter Prognosewert
	 */
	private double calculateARModel(int consideredPeriodsOfPast, int forecast,
			DoubleMatrix2D valuations, double[] previousValues)
			throws StochasticMethodException {
		if (this.equalizedValues[forecast - 1] == 0) {
			if (valuations == null) {
				valuations = calculateValuations(consideredPeriodsOfPast);
			}
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
	 * Diese Methode berechnet den prognostizierten Wert fuer die Periode auf
	 * Basis der beobachteten Zeitreihe.
	 * 
	 * @author Kai Westerholz
	 */

	// @Override
	public double[][] calculate(double[] previousValues,
			int consideredPeriodsOfPast, int periodsToForecast,
			int numberOfIterations, CallbackInterface callback)
			throws InterruptedException, StochasticMethodException {

		// vorbereitene Initialisierung
		double[][] returnValues = new double[periodsToForecast][numberOfIterations];
		int progress_complete = periodsToForecast
				* (consideredPeriodsOfPast + numberOfIterations);
		int progress = 0;

		// Trendbereinigung der Zeitreihe wenn diese nicht stationaer ist
		CalculateTide tide = new CalculateTide();
		boolean isStationary = StationaryTest.isStationary(previousValues);
		if (!isStationary) {
			previousValues = tide.reduceTide(previousValues);
		}
		/**
		 * Uebertragung der Werte der Zeitreihe in eine DoubleArrayList. Diese
		 * wird von der COLT Bibliothek verwendet zur Loesung der Matrix.
		 */

		this.DoubleArrayListTimeseries = new DoubleArrayList();
		for (int i = 0; i < previousValues.length; i++) {
			this.DoubleArrayListTimeseries.add(previousValues[i]);
		}

		// Start der zur Prognose benoetigten Berechnungen
		this.mean = Descriptive.mean(DoubleArrayListTimeseries);
		this.variance = this.calculateVariance(this.DoubleArrayListTimeseries);
		this.matrixValutaions = calculateValuations(consideredPeriodsOfPast);
		this.yuleWalkerVariance = this.calculateMatrixVariance(previousValues);

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
		}
		return returnValues;
	}

	public double[] getExpectedValues(double[] previousValues,
			int consideredPeriodsOfPast, int periodsToForecast)
			throws StochasticMethodException {

		double[] expectedValues = new double[periodsToForecast];
		CalculateTide tide = new CalculateTide();
		boolean isStationary = StationaryTest.isStationary(previousValues);
		if (!isStationary) {
			previousValues = tide.reduceTide(previousValues);
		}
		/**
		 * Uebertragung der Werte der Zeitreihe in eine DoubleArrayList. Diese
		 * wird von der COLT Bibliothek verwendet zur Loesung der Matrix.
		 */

		this.DoubleArrayListTimeseries = new DoubleArrayList();
		for (int i = 0; i < previousValues.length; i++) {
			this.DoubleArrayListTimeseries.add(previousValues[i]);
		}

		// Start der zur Prognose benoetigten Berechnungen
		this.mean = Descriptive.mean(DoubleArrayListTimeseries);
		this.variance = this.calculateVariance(this.DoubleArrayListTimeseries);

		this.yuleWalkerVariance = this.calculateMatrixVariance(previousValues);

		this.equalizedValues = new double[periodsToForecast];

		for (int forecast = 1; forecast <= expectedValues.length; forecast++) {
			if (!isStationary) {
				double newTide = tide.getTideValue(forecast
						+ previousValues.length - 1);
				expectedValues[forecast - 1] = (double) ((newTide - calculateARModel(
						consideredPeriodsOfPast, forecast, matrixValutaions,
						previousValues)));
			} else {
				expectedValues[forecast - 1] = (double) (calculateARModel(
						consideredPeriodsOfPast, forecast, matrixValutaions,
						previousValues));
			}
		}

		return expectedValues;
	}
}
