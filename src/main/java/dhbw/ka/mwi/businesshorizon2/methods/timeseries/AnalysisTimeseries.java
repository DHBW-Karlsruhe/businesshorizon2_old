package dhbw.ka.mwi.businesshorizon2.methods.timeseries;

import java.util.ArrayList;
import java.util.SortedSet;

import cern.colt.list.DoubleArrayList;
import cern.colt.matrix.DoubleFactory2D;
import cern.colt.matrix.DoubleMatrix2D;
import cern.colt.matrix.linalg.LUDecomposition;
import cern.jet.stat.Descriptive;
import dhbw.ka.mwi.businesshorizon2.methods.AbstractMethod;
import dhbw.ka.mwi.businesshorizon2.methods.MethodRunner;
import dhbw.ka.mwi.businesshorizon2.methods.Result;
import dhbw.ka.mwi.businesshorizon2.models.Period;

/**
 * Diese Klasse stellt die Methoden zur Verfügung, die benötigt werden, um die
 * Zeitreihenanalyse durch zu führen. Sie baut auf der YuleWalkerGleichung und
 * implementiert das AR-Modell.
 * 
 * @author Kai Westerholz
 * 
 */

public class AnalysisTimeseries extends AbstractMethod {
	private static final long serialVersionUID = 1L;
	private dhbw.ka.mwi.businesshorizon2.models.Timeseries timeseries;
	private ArrayList<Double> timeseriesArrayList;
	private double mean;
	private double variance;
	private int consideredPeriodsOfPast;
	private DoubleArrayList DoubleArrayListTimeseries;
	private DoubleMatrix2D matrixValutaions;
	private double yuleWalkerVariance;
	private int numberPeriodsToForcast;
	private int numberOfIterations;
	private TimeseriesResult result;

	public AnalysisTimeseries(
			dhbw.ka.mwi.businesshorizon2.models.Timeseries timeseries,
			int periodsOfPast, int numberPeriodsToForcast) {
		this.timeseries = timeseries;
		this.consideredPeriodsOfPast = periodsOfPast;
		this.numberPeriodsToForcast = numberPeriodsToForcast;
		this.timeseriesArrayList = timeseries.getArrayList();
		DoubleArrayListTimeseries = new DoubleArrayList();
		for (int i = 0; i < this.timeseriesArrayList.size(); i++) {
			DoubleArrayListTimeseries.add(this.timeseriesArrayList.get(i));
		}
		this.timeseries.reduceTide();
		this.calculateMean();
		this.calculateVariance();
	}

	public String getName() {
		return "Zeitreihenanalyse";
	}

	public int getOrderKey() {
		return 1;
	}

	public double getMean() {
		return mean;
	}

	public void setMean(double mean) {
		this.mean = mean;
	}

	public double getVariance() {
		return variance;
	}

	public void setVariance(double variance) {
		this.variance = variance;
	}

	public int getConsideredPeriodsOfPast() {
		return consideredPeriodsOfPast;
	}

	public void setConsideredPeriodsOfPast(int p) {
		this.consideredPeriodsOfPast = p;
	}

	/**
	 * Diese Methode berechnet den Mittelwert der Zeitreihe
	 * 
	 * @author Kai Westerholz
	 * 
	 * @return double Mittelwert der Zeitreihe
	 */
	private double calculateMean() {
		this.mean = Descriptive.mean(DoubleArrayListTimeseries);
		return mean;
	}

	/**
	 * Diese Methode berechnet die Varianz der Zeitreihe
	 * 
	 * @authoer Kai Westerholz
	 * 
	 * @return double Varianz der Zeitreihe
	 */
	private double calculateVariance() {
		if (this.mean == 0) {
			calculateMean();
		}
		this.variance = Descriptive.variance(DoubleArrayListTimeseries.size(),
				Descriptive.sum(DoubleArrayListTimeseries),
				Descriptive.sumOfSquares(DoubleArrayListTimeseries));
		return this.variance;
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
			return 1;
		} else {
			return Descriptive.autoCorrelation(DoubleArrayListTimeseries, lag,
					this.mean, this.variance);
		}
	}

	/**
	 * Diese Methode stellt das Lineare Gleichungssystem auf, das benötigt wird,
	 * um die Schätzwerte ci der Zeitreihenanalyse zu erhalten und löst dieses.
	 * Die berechneten Werte
	 */
	private DoubleMatrix2D calculateValuations() {

		DoubleMatrix2D matrixValuations = DoubleFactory2D.dense.make(
				(int) (this.consideredPeriodsOfPast - 1),
				(int) (this.consideredPeriodsOfPast - 1));
		for (int i = 0; i < this.consideredPeriodsOfPast - 1; i++) { // Aktuelle
																		// Zeile
			for (int j = 0; j < this.consideredPeriodsOfPast - 1; j++) {// Aktuelle
																		// Spalte
				matrixValuations.set(i, j,
						calculateAutoCorrelation(Math.abs(j - i)));
			}
		}
		DoubleMatrix2D matrixERG = DoubleFactory2D.dense.make(
				(int) (this.consideredPeriodsOfPast - 1), 1);
		for (int i = 0; i < this.consideredPeriodsOfPast - 1; i++) {
			matrixERG.set(i, 1, calculateAutoCorrelation(i + 1));
		}
		LUDecomposition lUDecomp = new LUDecomposition(matrixValuations);
		try {
			DoubleMatrix2D matrixC = lUDecomp.solve(matrixERG);
			this.matrixValutaions = matrixC;
		} catch (IllegalArgumentException exception) {
			// TODO: Exception Handling
		}
		return this.matrixValutaions;
	}

	/**
	 * Diese Methode berechnet die Varianz auf Basis der YuleWalkerGleichung.
	 * Die Varianz wird für die Berechnung des weißen Rauschens benötigt.
	 * 
	 * @param matrix
	 *            Matrix aus der die Varianz berechnet werden soll
	 * @return double Varianz aus YuleWalkerGleichung
	 */
	private double calculateMatrixVariance() {
		if (this.matrixValutaions == null) {
			this.calculateValuations();
		}
		double variance = calculateAutoCorrelation(0);
		for (int i = 1; i <= this.consideredPeriodsOfPast; i++) {
			variance -= this.matrixValutaions.get(i, 0)
					* calculateAutoCorrelation(i);
		}
		this.yuleWalkerVariance = variance;
		return this.yuleWalkerVariance;
	}

	/**
	 * Diese Methode berechnet den prognostizierten Wert für die Periode auf
	 * Basis der bisherigen Werte. Es ist irrelevant, ob es sich hierbei um
	 * einen CashFlow oder eine andere Bilanzposition handelt.
	 * 
	 */

	public Result calculate(SortedSet<Period> periods,
			MethodRunner.Callback callback) throws InterruptedException {

		this.calculateMatrixVariance();
		WhiteNoise whiteNoise = new WhiteNoise(this.numberOfIterations,
				this.yuleWalkerVariance);
		int progress_complete = this.numberPeriodsToForcast
				* (consideredPeriodsOfPast + this.numberOfIterations);
		int progress = 0;
		for (int i = 1; i <= this.numberPeriodsToForcast; i++) {
			ArrayList<Double> temp = new ArrayList<Double>();
			whiteNoise.calculateWhiteNoiseList();
			double equalizedValuePerPeriod = 0.0;
			double previousValue = 0.0;

			for (int j = 1; j <= this.consideredPeriodsOfPast; j++) {
				if (i == 1) {
					previousValue = this.timeseriesArrayList
							.get(timeseriesArrayList.size() - 1);
				} else {
					previousValue = this.result.getResultList().get(i - 1)
							.getArrayList().get(j - 1);
				}

				equalizedValuePerPeriod += matrixValutaions.get(j, 0)
						* previousValue;
			}
			progress += this.consideredPeriodsOfPast;
			Thread.currentThread().isInterrupted();
			boolean nextNeeded = true;
			for (int j = 0; j <= this.numberOfIterations; i++) {

				while (nextNeeded) {
					if (whiteNoise.hasNextValue()) {
						temp.add(whiteNoise.getNextValue()
								+ equalizedValuePerPeriod);
						nextNeeded = false;
					}
				}
				nextNeeded = true;
				if (j % 200 == 0) {
					progress += j;
					callback.onProgressChange((float) (progress / progress_complete));
					Thread.currentThread().isInterrupted();
				}
			}
			result.add(new dhbw.ka.mwi.businesshorizon2.models.Timeseries(temp,
					this.timeseries.getYearofPeriodZero()));
		}
		return result;
	}
}
