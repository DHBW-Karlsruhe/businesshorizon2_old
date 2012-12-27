package dhbw.ka.mwi.businesshorizon2.methods.timeseries;

import cern.colt.list.DoubleArrayList;
import cern.colt.matrix.DoubleFactory2D;
import cern.colt.matrix.DoubleMatrix2D;
import cern.colt.matrix.linalg.LUDecomposition;
import cern.jet.stat.Descriptive;

/**
 * Diese Klasse stellt die Methoden zur Verfügung, die benötigt werden, um die
 * Zeitreihenanalyse durch zu führen.
 * 
 * @author Kai Westerholz
 * 
 */

public class AnalysisTimeseries {
	private dhbw.ka.mwi.businesshorizon2.models.Timeseries timeseries;
	private double mean;
	private double variance;
	private double consideredPeriodsOfPast;
	private DoubleArrayList timeseriesAL;
	private DoubleMatrix2D matrixCValues;
	private DoubleMatrix2D matrixAutoCorrelation;
	private double yuleWalkerVariance;

	public AnalysisTimeseries(
			dhbw.ka.mwi.businesshorizon2.models.Timeseries timeseries,
			double periodsOfPast) {
		this.timeseries = timeseries;
		this.consideredPeriodsOfPast = periodsOfPast;
		timeseriesAL = new DoubleArrayList();
		for (double value : timeseries.getTimeseries().values()) {
			timeseriesAL.add(value);
		}
		calculateMean();
		calculateVariance();
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

	public double getConsideredPeriodsOfPast() {
		return consideredPeriodsOfPast;
	}

	public void setConsideredPeriodsOfPast(double p) {
		this.consideredPeriodsOfPast = p;
	}

	/**
	 * Diese Methode berechnet den Mittelwert der Zeitreihe
	 * 
	 * @return double Mittelwert der Zeitreihe
	 */
	private double calculateMean() {
		this.mean = Descriptive.geometricMean(timeseriesAL);
		return mean;
	}

	/**
	 * Diese Methode berechnet die Varianz der Zeitreihe
	 * 
	 * @return double Varianz der Zeitreihe
	 */
	private double calculateVariance() {
		if (this.mean == 0) {
			calculateMean();
		}
		this.variance = Descriptive.sampleVariance(timeseriesAL, this.mean);
		return this.variance;
	}

	/**
	 * Diese Methode berechnet die Autokorrelation zum angegebenen Zeit Lag.
	 * 
	 * @param lag
	 * @return double AutoKorrelation
	 */
	private double calculateAutoCorrelation(int lag) {
		return Descriptive.autoCorrelation(timeseriesAL, lag, this.mean,
				this.variance);
	}

	/**
	 * Diese Methode stellt das Lineare Gleichungssystem auf, das benötigt wird,
	 * um die Schätzwerte ci der Zeitreihenanalyse zu erhalten und löst dieses.
	 * Die berechneten Werte
	 */
	private DoubleMatrix2D calculateCValues() {

		DoubleMatrix2D matrixGamma = DoubleFactory2D.dense.make(
				(int) (this.consideredPeriodsOfPast - 1),
				(int) (this.consideredPeriodsOfPast - 1));
		for (int i = 0; i < this.consideredPeriodsOfPast - 1; i++) { // Aktuelle
																		// Zeile
			for (int j = 0; j < this.consideredPeriodsOfPast - 1; j++) {// Aktuelle
																		// Spalte
				matrixGamma
						.set(i, j, calculateAutoCorrelation(Math.abs(j - i)));
			}
		}
		this.matrixAutoCorrelation = matrixGamma;
		DoubleMatrix2D matrixERG = DoubleFactory2D.dense.make(
				(int) (this.consideredPeriodsOfPast - 1), 1);
		for (int i = 0; i < this.consideredPeriodsOfPast - 1; i++) {
			matrixERG.set(i, 1, calculateAutoCorrelation(i));
		}
		LUDecomposition lUDecomp = new LUDecomposition(matrixGamma);
		try {
			DoubleMatrix2D matrixC = lUDecomp.solve(matrixERG);
			this.matrixCValues = matrixC;
		} catch (IllegalArgumentException exception) {
			// TODO: Exception Handling
		}
		return this.matrixCValues;
	}

	/**
	 * Diese Methode berechnet die Varianz auf Basis der YuleWalkerGleichung.
	 * Die Varianz wird für die Berechnung des weißen Rauschens benötigt.
	 * 
	 * @param matrix
	 *            Matrix aus der die Varianz berechnet werden soll
	 * @return double Varianz aus YuleWalkerGleichung
	 */
	private double calculateMatrixVariance(DoubleMatrix2D matrix) {
		double variance = 0;
		for (int i = 0; i < this.consideredPeriodsOfPast; i++) {
			variance += matrix.get(i, 0);
		}
		variance *= matrixCValues.get(0, 0);
		this.yuleWalkerVariance = variance;
		return variance;
	}

	/**
	 * Diese Methode berechnet das weiße Rauschen auf Basis der periode und der
	 * Varianz.
	 * 
	 * @param period
	 *            varianz
	 * @return double Gauß-verteilte Zufallszahl
	 */
	private double calculateWhiteNoise(int period, double varianz) {
		return 0.0;
	}

	/**
	 * Diese Methode berechnet den prognostizierten Wert für die periode auf
	 * Basis der bisherigen Werte. Es ist irrelevant, ob es sich hierbei um
	 * einen CashFlow oder eine andere Bilanzposition handelt.
	 * 
	 * @param period
	 * @return double prognostizierterter Wert
	 */
	private double calculateARModel(int period) {
		double sum = 0;
		for (int i = 1; i <= consideredPeriodsOfPast; i++) {
			sum += matrixCValues.get(i, 0)
					* timeseries.getTimeseries().get(period - 1);
		}
		return (calculateWhiteNoise(period, this.yuleWalkerVariance) + sum);
	}
}
