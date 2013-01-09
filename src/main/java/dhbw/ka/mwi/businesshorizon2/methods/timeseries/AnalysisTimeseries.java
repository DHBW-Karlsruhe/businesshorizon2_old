package dhbw.ka.mwi.businesshorizon2.methods.timeseries;

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
	private double mean;
	private double variance;
	private int consideredPeriodsOfPast;
	private DoubleArrayList DoubleArrayListTimeseries;
	private DoubleMatrix2D matrixValutaions;
	private double yuleWalkerVariance;

	public String getName() {
		return "Zeitreihenanalyse";
	}

	public int getOrderKey() {
		return 1;
	}

	public double getMean() {
		return mean;
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
		} catch (IllegalArgumentException exception) {
			// TODO: Exception Handling
			System.out.println(exception.getMessage());
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
	 */
	private double calculateMatrixVariance(int consideredPeriodsOfPast) {

		this.matrixValutaions = calculateValuations(consideredPeriodsOfPast);
		;
		double variance = calculateAutoCorrelation(0);
		for (int i = 1; i <= consideredPeriodsOfPast; i++) {
			variance -= this.matrixValutaions.get(i - 1, 0)
					* calculateAutoCorrelation(i);
		}
		return variance;
	}

	/**
	 * Diese Methode berechnet den prognostizierten Wert für die Periode auf
	 * Basis der bisherigen Werte. Es ist irrelevant, ob es sich hierbei um
	 * einen CashFlow oder eine andere Bilanzposition handelt.
	 * 
	 */

	// public double[] calculate(SortedSet<Period> periods, Callback callback)
	// throws InterruptedException {
	//
	// this.calculateMatrixVariance();
	// WhiteNoise whiteNoise = new WhiteNoise(this.numberOfIterations,
	// this.yuleWalkerVariance);
	// int progress_complete = this.numberPeriodsToForcast
	// * (consideredPeriodsOfPast + this.numberOfIterations);
	// int progress = 0;
	/**
	 * for (int i = 1; i <= this.numberPeriodsToForcast; i++) {
	 * ArrayList<Double> temp = new ArrayList<Double>();
	 * whiteNoise.calculateWhiteNoiseList(); double equalizedValuePerPeriod =
	 * 0.0; double previousValue = 0.0;
	 * 
	 * for (int j = 1; j <= this.consideredPeriodsOfPast; j++) { if (i == 1) {
	 * previousValue = this.timeseriesArrayList .get(timeseriesArrayList.size()
	 * - 1); } else { previousValue = this.result.getResultList().get(i - 1)
	 * .getArrayList().get(j - 1); }
	 * 
	 * equalizedValuePerPeriod += matrixValutaions.get(j, 0) previousValue; }
	 * progress += this.consideredPeriodsOfPast;
	 * Thread.currentThread().isInterrupted(); boolean nextNeeded = true; for
	 * (int j = 0; j <= this.numberOfIterations; i++) {
	 * 
	 * while (nextNeeded) { if (whiteNoise.hasNextValue()) {
	 * temp.add(whiteNoise.getNextValue() + equalizedValuePerPeriod); nextNeeded
	 * = false; } } nextNeeded = true; if (j % 200 == 0) { progress += j;
	 * callback.onProgressChange((float) (progress / progress_complete));
	 * Thread.currentThread().isInterrupted(); } } result.add(new
	 * dhbw.ka.mwi.businesshorizon2.models.Timeseries(temp,
	 * this.timeseries.getYearofPeriodZero())); }
	 */
	// return new double[1];
	// }

	@Override
	public double[][] calculate(double[] previousValues,
			int consideredPeriodsofPast, int periodsToForecast,
			int numberOfIterations, Callback callback)
			throws InterruptedException {
		double[][] returnValues = new double[periodsToForecast][numberOfIterations];
		this.consideredPeriodsOfPast = consideredPeriodsofPast;

		CalculateTide tide = new CalculateTide();
		previousValues = tide.reduceTide(previousValues);
		this.DoubleArrayListTimeseries = new DoubleArrayList();
		for (int i = 0; i < previousValues.length; i++) {
			this.DoubleArrayListTimeseries.add(previousValues[i]);
		}
		this.mean = Descriptive.mean(DoubleArrayListTimeseries);
		this.variance = this.calculateVariance(this.DoubleArrayListTimeseries);
		this.yuleWalkerVariance = this
				.calculateMatrixVariance(consideredPeriodsOfPast);
		WhiteNoise whiteNoise = new WhiteNoise(this.yuleWalkerVariance);
		int progress_complete = periodsToForecast
				* (consideredPeriodsOfPast + numberOfIterations);
		int progress = 0;
		for (int i = 1; i <= periodsToForecast; i++) {
			double[] forecastsForPeriod = new double[numberOfIterations];
			double equalizedValuePerPeriod = 0.0;
			double previousValue = 0.0;

			for (int j = 1; j <= this.consideredPeriodsOfPast; j++) {

				if ((i - j) < 1) {
					int oldIndex = previousValues.length - 1 - Math.abs(i - j);
					previousValue = previousValues[oldIndex];
				} else {
					previousValue = returnValues[i - 1][(int) (Math.random() * numberOfIterations)];
				}

				equalizedValuePerPeriod += matrixValutaions.get(j - 1, 0)
						* previousValue;
			}
			progress += consideredPeriodsOfPast;
			Thread.currentThread().isInterrupted();
			double newTide = tide.getTideValue(i + consideredPeriodsOfPast);

			for (int j = 0; j < numberOfIterations; j++) {

				// forecastsForPeriod[j] = (double) (whiteNoise
				// .getWhiteNoiseValue() + (newTide - equalizedValuePerPeriod));
				forecastsForPeriod[j] = (double) ((newTide - equalizedValuePerPeriod));

				if (j % 200 == 0) {
					progress += j;
					// callback.onProgressChange((float) (progress /
					// progress_complete));
					// Thread.currentThread().isInterrupted();
				}
			}
			returnValues[i - 1] = forecastsForPeriod;

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
			System.out.println(result[1][0]);
		} catch (InterruptedException e) {
			System.out.println("Error al.calculate");
			System.out.println(e.getClass());
			System.out.println(e.getStackTrace());
			System.out.println(e.getMessage());
		}

	}
}
