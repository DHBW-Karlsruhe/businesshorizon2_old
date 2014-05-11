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

import java.util.Random;

import org.apache.log4j.Logger;

/**
 * Dies ist die WhiteNoise-Klasse der Zeitreihenanalyse. Sie stellt mit der
 * getValue() Methode den Service bereit Zufallszahlen abhaengig von Varianz
 * bzw. Standardabweichung zu errechnen und zurueckzugeben.
 * 
 * @author Christian Scherer
 * 
 */
public class WhiteNoise {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger(WhiteNoise.class);

	private double deviation;
	private Random randomGenerator;

	/**
	 * 
	 * Dem Konstruktor wird die Varianz uebergeben, woraus die
	 * Standardabweichung errechnet und abgespeichert wird. Zudem wird ein
	 * Random Objekt erzeugt, mit dem die spaetere Generierung der Zufallszahlen
	 * erfolgt.
	 * 
	 * @author Christian Scherer
	 * @param variance
	 *            Varianz als Grundlage fuer die Zufallszahl
	 * 
	 */
	public WhiteNoise(double variance) {
		this.deviation = Math.sqrt(variance);
		this.randomGenerator = new Random();
		logger.debug("WhiteNoise Objekt Standardabweichung (" + this.deviation
				+ ") und Random Objekt Initialisiert");

	}

	/**
	 * Konkrete Berechnung einer normalverteilten Zufallszahl im Bereich der
	 * Standardabweichung
	 * 
	 * @author Kai Westerholz, Christian Scherer
	 * 
	 */
	public double getWhiteNoiseValue() {
		return randomGenerator.nextGaussian() * deviation;
	}

}
