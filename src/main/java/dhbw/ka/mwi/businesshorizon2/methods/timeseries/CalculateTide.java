package dhbw.ka.mwi.businesshorizon2.methods.timeseries;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.SortedSet;

import dhbw.ka.mwi.businesshorizon2.models.Period;
import dhbw.ka.mwi.businesshorizon2.models.Timeseries;

public class CalculateTide implements CalculateTideInterface {

	private double reduceTideParameterA;
	private double reduceTideParameterB;
	private boolean tideCalculated = false;
	private boolean tideIsReduced = false;
	private double averageTimeseries;
	private double averagePeriod;
	private SortedSet<Period> timeseries;

	/**
	 * Diese Methode bereinigt die Zeitreihe um die Trendgerade und berechnet
	 * diese wenn nötig. Die Formel der Bereinigung lautet: Y*(t) = T(t) - Y(t)
	 * 
	 * @author Kai Westerholz
	 */
	public void setTimeseries(
			dhbw.ka.mwi.businesshorizon2.models.Timeseries timeseries) {
		this.timeseries = timeseries.getTimeseries();
	}

	public void reduceTide() {
		if (this.tideIsReduced) {
			return;
		}
		if (!tideCalculated) {
			this.calculateTideParameterB();
			this.calculateTideParameterA();
			this.tideCalculated = true;
		}
		/**
		 * for (int i = 0; i < timeseries.size(); i++) { timeseries.set(i,
		 * (this.getTideValue(i) - timeseries.get(i))); }
		 */
		Period period;
		int periodCount = 0;
		Iterator<Period> it = timeseries.iterator();
		while (it.hasNext()) {
			period = it.next();
			period.setCashFlow(this.getTideValue(periodCount)
					- period.getCashFlow());
			periodCount++;
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
		/**
		 * for (int i = 0; i < timeseries.size(); i++) { timeseries.set(i,
		 * (this.getTideValue(i) + timeseries.get(i))); }
		 */

		Period period;
		int periodCount = 0;
		Iterator<Period> it = timeseries.iterator();
		while (it.hasNext()) {
			period = it.next();
			period.setCashFlow(this.getTideValue(periodCount)
					+ period.getCashFlow());
			periodCount++;
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
	@Override
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
			/**
			 * for (int i = 0; i < timeseries.size(); i++) { sum +=
			 * timeseries.get(i); }
			 */

			Period period;
			Iterator<Period> it = timeseries.iterator();
			while (it.hasNext()) {
				period = it.next();
				sum += period.getCashFlow();
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
		/**
		 * for (int i = 0; i < timeseries.size(); i++) { sum += i; }
		 */

		int periodCount = 0;
		Iterator<Period> it = timeseries.iterator();
		while (it.hasNext()) {
			it.next();
			sum += periodCount;
			periodCount++;
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
		/**
		 * for (int i = 0; i < this.timeseries.size(); i++) { numerator += (i -
		 * averagePeriods) (timeseries.get(i) - averageTimeseries); denominator
		 * += Math.pow((i - averagePeriods), 2); }
		 */

		Period period;
		int periodCount = 0;
		Iterator<Period> it = timeseries.iterator();
		while (it.hasNext()) {
			period = it.next();
			numerator += (periodCount - averagePeriods)
					* (period.getCashFlow() - averageTimeseries);
			denominator += Math.pow((periodCount - averagePeriods), 2);
			periodCount++;
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
