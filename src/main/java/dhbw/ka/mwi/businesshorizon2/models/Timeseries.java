package dhbw.ka.mwi.businesshorizon2.models;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Diese Klasse stellt das Datenobjet der Zeitreiehenanalyse und die damit
 * verbundenen Operationen dar. Der Wert an der stelle 0 ist die am weitesten
 * Entfernte Periode in der Verangenheit! Der letzte Wert entspricht dem
 * Beobachtungswert t=0 Die Abfolge der Perioden sieht also wie folgt aus
 * (Beispiel: 5 perioden): [t=-4] [t=-3] [t=-2] [t=-1] [t=0]
 * 
 * @author Kai Westerholz
 * 
 */

public class Timeseries {

	private ArrayList<Double> timeseries;
	private double reduceTideParameterA;
	private double reduceTideParameterB;
	private boolean tideCalculated = false;
	private boolean tideIsReduced = false;
	private double averageTimeseries;
	private double averagePeriod;
	private int yearOfPeriodZero;

	public Timeseries(ArrayList<Double> timeseries, int yearOfPeriodZero) {
		this.timeseries = timeseries;
		this.yearOfPeriodZero = yearOfPeriodZero;
	}

	/**
	 * Diese Methode bereinigt die Zeitreihe um die Trendgerade und berechnet
	 * diese wenn nötig. Die Formel der Bereinigung lautet: Y*(t) = T(t) - Y(t)
	 * 
	 * @author Kai Westerholz
	 */
	public void reduceTide() {
		if (this.tideIsReduced) {
			return;
		}
		if (!tideCalculated) {
			this.calculateTideParameterB();
			this.calculateTideParameterA();
			this.tideCalculated = true;
		}
		for (int i = 0; i < timeseries.size(); i++) {
			timeseries.set(i, (this.getTideValue(i) - timeseries.get(i)));

		}
		this.tideIsReduced = true;

	}

	/**
	 * Diese Methode addiert zur Zeitreihe die Werte der Trendgerade nach
	 * folgender Formel: Y(t) = T(t) + Y*(t)
	 * 
	 * @author Kai Westerholz
	 */

	public void addTide() {
		if (!this.tideIsReduced) {
			return;
		}
		for (int i = 0; i < timeseries.size(); i++) {
			timeseries.set(i, (this.getTideValue(i) + timeseries.get(i)));
		}
		this.tideIsReduced = false;
	}

	/**
	 * Diese Methode liefert den Wert der Trendgerade zum Zeitpunkt der
	 * übergebenen Periode zurück. Formel: (t) = a + b *t
	 * 
	 * @author Kai Westerholz
	 * 
	 * @param period
	 *            Periode
	 * @return double Wert der Trendgeriode
	 */
	private double getTideValue(int period) {
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
			for (int i = 0; i < timeseries.size(); i++) {
				sum += timeseries.get(i);
			}
			this.averageTimeseries = (sum / timeseries.size());
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
		for (int i = 0; i < timeseries.size(); i++) {
			sum += i;
		}
		this.averagePeriod = (sum / timeseries.size());
		return this.averagePeriod;

	}

	/**
	 * Diese Methode berechnet den Paramater b der Trendgerade T(t) = a + b *t.
	 * b = Summe((Periode - Mittel der Perioden) * (Wert zur Periode - Mittel
	 * der Werte )) / Summe((periode - Mittel der Perioden)²)
	 * 
	 * @author Kai Westerholz
	 * @return double ParameterB
	 */
	private double calculateTideParameterB() {
		double averageTimeseries = this.calculateAverageTimeseries();
		double averagePeriods = this.calculateAveragePeriods();
		double numerator = 0;
		double denominator = 0;
		for (int i = 0; i < this.timeseries.size(); i++) {
			numerator += (i - averagePeriods)
					* (timeseries.get(i) - averageTimeseries);
			denominator += Math.pow((i - averagePeriods), 2);
		}
		this.reduceTideParameterB = numerator / denominator;
		return this.reduceTideParameterB;
	}

	/**
	 * Diese Methode berechnet den Paramater a der Trendgerade T(t) = a + b *t.
	 * Für die Berechnung wird der Paramter B benötigt. Die Berechnung basiert
	 * auf folgender Formel: a = Durchschnitt(y) - b * Durchschnitt(t);
	 * 
	 * @author Kai Westerholz
	 * @return double ParameterA
	 */
	private double calculateTideParameterA() {
		this.reduceTideParameterA = this.calculateAverageTimeseries()
				- (this.reduceTideParameterB * this.calculateAveragePeriods());
		return this.reduceTideParameterA;
	}

	/**
	 * Diese Methode gibt die Zeitreihe in Form "Jahreszahl : Wert " zurück
	 * 
	 * @author Kai Westerholz
	 * @return String zeitreihe
	 */
	@Override
	public String toString() {
		DecimalFormat df = new DecimalFormat("0.00");
		String out = "Zeitreihe: ";
		for (int i = 0; i < this.timeseries.size(); i++) {
			out += "\""
					+ (yearOfPeriodZero - (this.timeseries.size() - (i + 1)))
					+ "\": " + df.format(this.timeseries.get(i)) + "\t";
		}
		return out;
	}

	public ArrayList<Double> getTimeseries() {
		return timeseries;
	}

	public static void main(String[] args) {
		ArrayList<Double> timeseries = new ArrayList<Double>();
		timeseries.add(638.07);
		timeseries.add(680.01);
		timeseries.add(988.85);
		timeseries.add(1686.7);
		timeseries.add(1504.92);
		timeseries.add(1826.95);
		timeseries.add(1607.90);
		timeseries.add(1826.82);
		timeseries.add(1950.00);
		timeseries.add(2158.00);
		Timeseries ts = new Timeseries(timeseries, 2012);
		System.out.println(ts);
		ts.reduceTide();
		System.out.println(ts);

	}
}