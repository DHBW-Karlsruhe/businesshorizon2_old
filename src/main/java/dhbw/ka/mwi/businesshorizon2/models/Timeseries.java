package dhbw.ka.mwi.businesshorizon2.models;

import java.util.HashMap;

/**
 * Diese Klasse stellt das Datenobjet der Zeitreiehenanalyse und die damit
 * verbundenen Operationen dar. Die Zeitreihe hat als Schlüssel die Periode, zu
 * der der jeweilige Wert gehört.
 * 
 * @author Kai Westerholz
 * 
 */

public class Timeseries {

	private HashMap<Integer, Double> timeseries;
	private double reduceTideParameterA;
	private double reduceTideParameterB;
	private boolean tideCalculated = false;
	private boolean tideIsReduced = false;
	private int yearOfPeriodZero;

	public Timeseries(HashMap<Integer, Double> timeseries, int yearOfPeriodZero) {
		this.timeseries = timeseries;
		this.yearOfPeriodZero = yearOfPeriodZero;
	}

	/**
	 * Diese Methode bereinigt die Zeitreihe um die Trendgerade und berechnet
	 * diese wenn nötig. Die Formel der Bereinigung lautet: Y*(t) = T(t) - Y(t)
	 */
	public void reduceTide() {
		if (this.tideIsReduced) {
			return;
		}
		if (!tideCalculated) {
			this.calculateTideB();
			this.calculateTideA();
			this.tideCalculated = true;
		}
		for (int key : timeseries.keySet()) {
			timeseries.put(key, (this.getTideValue(key) - timeseries.get(key)));
		}
		this.tideIsReduced = true;

	}

	/**
	 * Diese Methode addiert zur Zeitreihe die Werte der Trendgerade nach
	 * folgender Formel: Y(t) = T(t) + (Y*(t)
	 * 
	 */

	public void addTide() {
		if (!this.tideIsReduced) {
			return;
		}
		for (int key : timeseries.keySet()) {
			timeseries.put(key, (this.getTideValue(key) + timeseries.get(key)));
		}
		this.tideIsReduced = false;
	}

	/**
	 * Diese Methode liefert den Wert der Trendgerade zum Zeitpunkt der
	 * übergebenen Periode zurück. Formel: (t) = a + b *t
	 * 
	 * @param period
	 *            Periode
	 * @return double Wert der Trendgeriode
	 */
	private double getTideValue(int period) {
		return (reduceTideParameterA + reduceTideParameterB * period);
	}

	/**
	 * Diese Methode berechnet das Arithmetische Mittel der Werte der Zeitreihe
	 * 
	 * @author Kai Westerholz
	 * @return Arithmetische Mittel Zeitenreihenwerte
	 */
	public double calculateAverageTimeseries() {
		double sum = 0;
		for (double value : timeseries.values()) {
			sum += value;
		}
		return (sum / timeseries.size());
	}

	/**
	 * Diese Methode berechnet das Aritmetische Mittel der Perioden
	 * 
	 * @author Kai Westerholz
	 * @return Arithmetische Mittel Perioden
	 */
	public double calculateAveragePeriods() {
		double sum = 0;
		for (int key : timeseries.keySet()) {
			sum += key;
		}
		return (sum / timeseries.size());
	}

	/**
	 * Diese Methode berechnet den Paramater b der Trendgerade T(t) = a + b *t.
	 * b = Summe((Periode - Mittel der Perioden) * (Wert zur Periode - Mittel
	 * der Werte )) / Summe((periode - Mittel der Perioden)²)
	 * 
	 * @author Kai Westerholz
	 * @return double ParameterB
	 */
	private double calculateTideB() {
		double averageTimeseries = this.calculateAverageTimeseries();
		double averagePeriods = this.calculateAveragePeriods();
		double numerator = 0;
		double denominator = 0;
		for (int key : this.timeseries.keySet()) {

			numerator += (key - averagePeriods)
					* (timeseries.get(key) - averageTimeseries);
			denominator += Math.pow((key - averagePeriods), 2);

		}
		return (numerator / denominator);
	}

	/**
	 * Diese Methode berechnet den Paramater a der Trendgerade T(t) = a + b *t.
	 * Für die Berechnung wird der Paramter B benötigt. Die Berechnung basiert
	 * auf folgender Formel: a = Durchschnitt(y) - b * Durchschnitt(t);
	 * 
	 * @authoer Kai Westerholz
	 * @return double ParameterA
	 */
	private double calculateTideA() {
		return this.calculateAverageTimeseries()
				- (this.reduceTideParameterB * this.calculateAveragePeriods());
	}

	/**
	 * Diese Methode gibt die Zeitreihe in Form "Jahreszahl : Wert " zurück
	 * 
	 * @authoer Kai Westerholz
	 * @return String zeitreihe
	 */
	@Override
	public String toString() {
		String out = "Zeitreihe:";
		for (int key : this.timeseries.keySet()) {
			out += (yearOfPeriodZero + key) + " : " + this.timeseries.get(key);
		}
		return out;
	}

	public HashMap<Integer, Double> getTimeseries() {
		return timeseries;
	}

}