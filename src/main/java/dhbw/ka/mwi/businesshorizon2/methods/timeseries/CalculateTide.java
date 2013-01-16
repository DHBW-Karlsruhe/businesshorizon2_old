package dhbw.ka.mwi.businesshorizon2.methods.timeseries;

import org.apache.log4j.Logger;

/**
 * Diese Klasse stellt die Methoden fuer die Trendbereinigung zur Verfuegung.
 * 
 * @author Kai Westerholz
 * 
 */

public class CalculateTide implements CalculateTideInterface {

	private Logger logger = Logger.getLogger(CalculateTide.class);
	private double reduceTideParameterA;
	private double reduceTideParameterB;
	private double averageTimeseries;
	private double averagePeriod;
	private double[] timeseries;

	/**
	 * Diese Methode bereinigt die Zeitreihe um die Trendgerade und berechnet
	 * diese wenn noetig. Die Formel der Bereinigung lautet: Y*(t) = T(t) - Y(t)
	 * 
	 * @author Kai Westerholz
	 * 
	 * @param Zeitreihe
	 *            der Zeitreihe
	 * @result Trendbereinigte Zeitreihe
	 */

	public double[] reduceTide(double[] timeseries) {
		this.timeseries = timeseries;

		double parameterB = this.calculateTideParameterB(timeseries);
		this.reduceTideParameterB = parameterB;
		this.reduceTideParameterA = this.calculateTideParameterA(parameterB);

		for (int i = 0; i < timeseries.length; i++) {
			timeseries[i] = (this.getTideValue(i) - timeseries[i]);
		}
		logger.debug("Timeseries reduced.");
		return timeseries;
	}

	/**
	 * Diese Methode liefert den Wert der Trendgerade zum Zeitpunkt der
	 * uebergebenen Periode zurueck. Formel: (t) = a + b *t
	 * 
	 * @author Kai Westerholz
	 * 
	 * @param period
	 *            Periode
	 * @return double Wert der Trendgeriode
	 */
	public double getTideValue(int period) {
		return (this.reduceTideParameterA + this.reduceTideParameterB * period);
	}

	/**
	 * Diese Methode berechnet das Arithmetische Mittel der Werte der Zeitreihe
	 * 
	 * @author Kai Westerholz
	 * @return Arithmetische Mittel Zeitenreihenwerte
	 */
	public double calculateAverageTimeseries() {
		if (this.averageTimeseries == 0) {
			double sum = 0;

			for (int i = 0; i < timeseries.length; i++) {
				sum += timeseries[i];
			}

			this.averageTimeseries = (sum / (timeseries.length));
		}
		return this.averageTimeseries;
	}

	/**
	 * Diese Methode berechnet das Aritmetische Mittel der Perioden.
	 * 
	 * @author Kai Westerholz
	 * @return Arithmetische Mittel Perioden
	 */
	public double calculateAveragePeriods() {
		double sum = 0;

		for (int i = 0; i < timeseries.length; i++) {
			sum += i;
		}

		this.averagePeriod = (sum / timeseries.length);
		return this.averagePeriod;

	}

	/**
	 * Diese Methode berechnet den Paramater b der Trendgerade T(t) = a + b *t.
	 * b = Summe((Periode - Mittel der Perioden) * (Wert zur Periode - Mittel
	 * der Werte )) / Summe((periode - Mittel der Perioden)^2)
	 * 
	 * @author Kai Westerholz
	 * @return double ParameterB
	 */
	private double calculateTideParameterB(double[] timeseries) {
		double averageTimeseries = this.calculateAverageTimeseries();
		double averagePeriods = this.calculateAveragePeriods();
		double numerator = 0;
		double denominator = 0;

		for (int i = 0; i < timeseries.length; i++) {
			numerator += (i - averagePeriods)
					* (timeseries[i] - averageTimeseries);
			denominator += Math.pow((i - averagePeriods), 2);
		}

		return numerator / denominator;
	}

	/**
	 * Diese Methode berechnet den Paramater a der Trendgerade T(t) = a + b *t.
	 * Fuer die Berechnung wird der Paramter B benoetigt. Die Berechnung basiert
	 * auf folgender Formel: a = Durchschnitt(y) - b * Durchschnitt(t);
	 * 
	 * @author Kai Westerholz
	 * @return double ParameterA
	 */
	private double calculateTideParameterA(double parameterB) {
		return reduceTideParameterA = this.calculateAverageTimeseries()
				- (parameterB * this.calculateAveragePeriods());
	}

}
