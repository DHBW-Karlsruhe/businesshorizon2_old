/*******************************************************************************
 * BusinessHorizon2
 *
 * Copyright (C) 
 * 2012-2013 Christian Gahlert, Florian Stier, Kai Westerholz,
 * Timo Belz, Daniel Dengler, Katharina Huber, Christian Scherer, Julius Hacker
 * 2013-2014 Marcel Rosenberger, Mirko Göpfrich, Annika Weis, Katharina Narlock, 
 * Volker Meier
 * 
 *
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/


package dhbw.ka.mwi.businesshorizon2.methods.timeseries;

import org.apache.log4j.Logger;

/**
 * Diese Klasse stellt die Methoden fuer die Trendbereinigung zur Verfuegung.
 * 
 * @author Kai Westerholz
 * 
 */

public class CalculateTide implements CalculateTideInterface {

	private static final Logger logger = Logger.getLogger(CalculateTide.class);
	private double reduceTideParameterA;
	private double reduceTideParameterB;
	private double averageTimeseries;
	private double averagePeriod;
	private double[] timeseries;

	/**
	 * Diese Methode bereinigt die Zeitreihe um die Trendgerade und berechnet
	 * diese wenn noetig. Die Formel der Bereinigung lautet: Y*(t) = Y(t) - T(t)
	 * 
	 * Änderung 08.01.2014: das Vorjahresprojekt hat die Formel Y*(t) = T(t) - Y(t)
	 * verwendet. Dies ist allerdings falsch und wurde korrigiert.
	 * 
	 * @author Kai Westerholz, Marcel Rosenberger, Christian Meder, Valeska Heidt
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
			timeseries[i] = (timeseries[i] - this.getTideValue(i));
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
	 * Es wird die Methode der kleinsten Quadrate verwendet.
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
