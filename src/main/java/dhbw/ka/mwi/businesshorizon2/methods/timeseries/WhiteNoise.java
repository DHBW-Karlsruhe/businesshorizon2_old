package dhbw.ka.mwi.businesshorizon2.methods.timeseries;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//import org.apache.log4j.Logger;

/**
 * Dies ist die WhiteNoise-Klasse der Zeitreihenanalyse. -----
 * 
 * @author Christian Scherer
 * 
 */
public class WhiteNoise {

	private static final long serialVersionUID = 1L;

	// private Logger logger = Logger.getLogger("WhiteNoise.class");

	private double deviation;
	private Random randomGenerator;
	private List<Double> whiteNoiseList;
	private int iterationSteps;
	private int counter;

	/**
	 * Dies ist die WhiteNoise-Klasse der Zeitreihenanalyse. -----
	 * 
	 * @author Christian Scherer
	 * 
	 */
	public WhiteNoise(int iterationSteps, double variance) {
		this.deviation = Math.sqrt(variance);
		this.iterationSteps = iterationSteps;
		this.randomGenerator = new Random();
		// logger.debug("WhiteNoise Objekt mit Anzahl vorherzusagener Perioden ("+numberPeriodsToForcast+"), Varianz ("+variance+") und Ramdom Obket Initialisiert");

	}

	/**
	 * Konkrete Berechnung eines der Werte.....
	 * 
	 * @author Kai Westerholz
	 * 
	 */
	public double getWhiteNoiseValue() {
		return randomGenerator.nextGaussian() * deviation;
		// logger.debug("Zufallszahl erstellt");
	}

	public double getNextValue() {
		return whiteNoiseList.get(counter++);
	}

	public boolean hasNextValue() {
		return whiteNoiseList.get(counter) != null;
	}

	/**
	 * Erstellung der Liste für eine Periode über alle Iterationsschritte...
	 * diese läuft weiter während von außen schon per getNextValue() auf das
	 * Objekt zugegriffen wird
	 * 
	 * @author Christian Scherer
	 * 
	 */
	public void calculateWhiteNoiseList() {
		// so oft anzahl vorherzusehender Perioden ausführen
		counter = 0;
		whiteNoiseList = new ArrayList<Double>();

		for (int i = 0; i < iterationSteps; i++) {
			whiteNoiseList.add(getWhiteNoiseValue());
		}
		// logger.debug("Liste an Zufallszahlen für eine Zukunftszeitreihe erstellt");

	}

}