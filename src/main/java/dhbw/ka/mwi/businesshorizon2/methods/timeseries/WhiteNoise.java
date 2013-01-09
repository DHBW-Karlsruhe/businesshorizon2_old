package dhbw.ka.mwi.businesshorizon2.methods.timeseries;

import java.util.Random;

import org.apache.log4j.Logger;

/**
 * Dies ist die WhiteNoise-Klasse der Zeitreihenanalyse. Sie stellt mit der
 * getValue() Methode den Service bereit Zufallszahlen abhängig von Varianz bzw.
 * Standardabweichung zu errechnen und zurueckzugeben.
 * 
 * @author Christian Scherer
 * 
 */
public class WhiteNoise {

	private static final long serialVersionUID = 1L;

	private Logger logger = Logger.getLogger("WhiteNoise.class");

	private double deviation;
	private Random randomGenerator;

	/**
	 * 
	 * Dem Konstruktor wird die Varianz übergeben, woraus die Standardabweichung
	 * errechnet und abgespeichert wird. Zudem wird ein Random Objekt erzeugt,
	 * mit dem die spätere Generierung der Zufallszahlen erfolgt.
	 * 
	 * @author Christian Scherer
	 * @param variance
	 *            Varinaz als Grundlage fuer die Zufallszahl
	 * 
	 */
	public WhiteNoise(double variance) {
		this.deviation = Math.sqrt(variance);
		this.randomGenerator = new Random();
		logger.debug("WhiteNoise Objekt Standardabweichung (" + this.deviation
				+ ") und Ramdom Objekt Initialisiert");

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
		// logger.debug("Zufallszahl erstellt");
	}

}