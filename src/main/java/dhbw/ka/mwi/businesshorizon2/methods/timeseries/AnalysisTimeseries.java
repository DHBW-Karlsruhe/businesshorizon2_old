/*******************************************************************************
 * BusinessHorizon2
 * 
 *     Copyright (C) 2012-2013  Christian Gahlert, Florian Stier, Kai Westerholz,
 *     Timo Belz, Daniel Dengler, Katharina Huber, Christian Scherer, Julius Hacker
 * 
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Affero General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 * 
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Affero General Public License for more details.
 * 
 *     You should have received a copy of the GNU Affero General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/

package dhbw.ka.mwi.businesshorizon2.methods.timeseries;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;
import org.jamesii.core.math.statistics.timeseries.AutoCovariance;

import cern.colt.list.DoubleArrayList;
import cern.colt.matrix.DoubleFactory2D;
import cern.colt.matrix.DoubleMatrix2D;
import cern.colt.matrix.linalg.LUDecomposition;
import cern.jet.stat.Descriptive;
import dhbw.ka.mwi.businesshorizon2.methods.CallbackInterface;
import dhbw.ka.mwi.businesshorizon2.methods.StochasticMethodException;

/**
 * Diese Klasse stellt die Methoden zur Verfuegung, die benoetigt werden, um die
 * Zeitreihenanalyse durch zu fuehren. Sie baut auf der YuleWalkerGleichung auf
 * und implementiert das AR-Modell.
 * 
 * @author Kai Westerholz, Nina Brauch, Raffaele Cipolla, Mirko Göpfrich, Marcel
 *         Rosenberger
 * 
 */

public class AnalysisTimeseries {

	private static final Logger logger = Logger
			.getLogger(AnalysisTimeseries.class);
	private DoubleArrayList autokovarianzen;
	private DoubleArrayList bereinigteZeitreihe;
	private DoubleMatrix2D modellparameter;;
	private double standardabweichung;
	private double mittelwert;

	/**
	 * Methode zur Berechnung des Mittelwerts einer Zeitreihe.
	 * 
	 * @author Marcel Rosenberger, Nina Brauch, Mirko Göpfrich
	 * 
	 * @param zeitreihe
	 *            Durch den Benutzer gegebene Zeitreihe vergangener Werte.
	 * @return Gibt den Mittelwert einer Zeitreihe zurück.
	 */
	public double berechneMittelwert(DoubleArrayList zeitreihe) {
		double mittelwert = 0;
		double summe = 0;
		int zaehler = 0;

		for (int i = 0; i < zeitreihe.size(); i++) {
			summe = summe + zeitreihe.get(i);
			zaehler = zaehler + 1;
		}

		if (zaehler != 0) {
			mittelwert = summe / zaehler;
		}

		return mittelwert;
	}

	/**
	 * Methode zur Berechnung der Autokovarianzen einer Zeitreihe. Die
	 * eigentliche Berechnung der Autokovarianzen wird durch eine Methode
	 * durchgeführt, die aus der Java-Bibliothek "james ii" importiert wird.
	 * Hierfür muss jedoch zunächst die eingegebene Zeitreihe zu einer von
	 * Number erbenden Liste gecastet werden.
	 * 
	 * @author Marcel Rosenberger, Nina Brauch, Mirko Göpfrich
	 * 
	 * @param zeitreihe
	 *            Durch den Benutzer gegebene Zeitreihe vergangener Werte.
	 * @return Autokovarianzen der Zeitreihe
	 */
	public DoubleArrayList berechneAutokovarianz(DoubleArrayList zeitreihe) {

		DoubleArrayList autokovarianz = new DoubleArrayList();

		// DoubleArrayList zu einer List casten, die von der
		// autoCovariance-Methode verwendet werden kann
		List<Double> lokalereihe = new ArrayList<Double>();
		Double t = new Double(0);
		for (int i = 0; i < zeitreihe.size(); i++) {
			t = zeitreihe.get(i);
			lokalereihe.add(t);
		}

		// berechnet die Autokovarianzen der Zeitreihe in Abhängigkeit von j
		for (int j = 0; j < zeitreihe.size(); j++) {
			autokovarianz.add(AutoCovariance.autoCovariance(lokalereihe, j));
		}
		return autokovarianz;
	}

	/**
	 * Stellt ein Gleichungssystem auf und berechnet daraus die Modellparameter
	 * (Yule-Walker-Schätzer)
	 * 
	 * @author Marcel Rosenberger, Nina Brauch, Mirko Göpfrich
	 * @param Autokovarianzen
	 *            die zuvor in einer eigenen Methode berechnet wurden
	 * @param p
	 *            die Anzahl der mit einbezogenen, vergangenen Perioden
	 * @return Gibt den Vektor der Phi-Werte (Gewichtungen der
	 *         Vergangenheitswerte) zurück.
	 * @throws StochasticMethodException
	 */
	public DoubleMatrix2D berechneModellparameter(
			DoubleArrayList autokovarianzen, int p)
			throws StochasticMethodException {

		// linke Seite des Gleichungssystems
		DoubleMatrix2D matrixValuations = DoubleFactory2D.dense.make(p, p);
		for (int i = 0; i < p; i++) { // Aktuelle
										// Zeile
			for (int j = 0; j < p; j++) { // Aktuelle
											// Spalte

				matrixValuations.set(i, j,
						autokovarianzen.get(Math.abs((int) (i - j))));

			}
		}

		// rechte Seite des Gleichungssystems
		DoubleMatrix2D matrixERG = DoubleFactory2D.dense.make((int) (p), 1);
		for (int i = 1; i <= p; i++) {
			matrixERG.set((i - 1), 0, autokovarianzen.get(i));
		}

		LUDecomposition lUDecomp = new LUDecomposition(matrixValuations);

		// Matrix mit Modellparametern (Phi)
		DoubleMatrix2D matrixPhi = null;

		try {
			matrixPhi = lUDecomp.solve(matrixERG);
			logger.debug("C-Values of Yule-Walker-Equitation calculated.");
		} catch (IllegalArgumentException exception) {

			logger.debug("Calculation of C-Values failed!");
			throw new StochasticMethodException(exception.getMessage());

		}

		return matrixPhi;
	}

	/**
	 * Methode zur Berechung der Standardabweichung einer Zeitreihe (mithilfe
	 * des Yule-Walker-Schätzer).
	 * 
	 * @author Nina Brauch, Mirko Göpfrich, Raffaele Cipolla, Marcel Rosenberger
	 * 
	 * @param Autokovarianzen
	 *            die zuvor in einer eigenen Methode berechnet wurden
	 * @param matrixPhi
	 *            Vektor der Phi-Werte ( =Modellparameter)
	 * @return Gibt die Standardabweichung zurück.
	 */
	//
	public double berechneStandardabweichung(DoubleArrayList autokovarianzen,
			DoubleMatrix2D matrixPhi) {
		double standardabweichung = 0;
		double s2 = autokovarianzen.get(0);

		// Berechnung der Varianz
		for (int i = 0; i < matrixPhi.size(); i++) {
			s2 = s2 - (matrixPhi.get(i, 0) * autokovarianzen.get(i + 1));
		}

		// Berechnung der Standardabweichung aus der Varianz
		standardabweichung = Math.sqrt(s2);

		return standardabweichung;
	}

	/**
	 * Methode zur Prognose zukünftiger Werte einer gegebenen Zeitreihe und
	 * einem AR-Modell. Sie berechnet die jeweiligen Prognosewerte und gibt sie
	 * in einem zweidimensionalen Array zurück.
	 * 
	 * @author Nina Brauch, Mirko Göpfrich, Raffaele Cipolla, Marcel Rosenberger
	 * 
	 * @param trendbereinigtezeitreihe
	 *            , die bereits trendbereinigte Zeitreihe
	 * @param matrixPhi
	 *            die ermittelte Matrix Phi
	 * @param standardabweichung
	 *            die ermittelte Standardabweichung der Zeitreihe
	 * @param Ordnung
	 *            p die Anzahl der mit einbezogenen, vergangenen Perioden
	 * @param zuberechnendeperioden
	 *            die Anzahl der zu prognostizierenden, zukünftigen Perioden
	 * @param durchlaeufe
	 *            die Anzahl der Iterationen, die die Zeitreihenanalyse
	 *            durchlaufen soll
	 * @param mittelwert
	 *            der ermittelte Mittelwert der Zeitreihe
	 * @return Alle prognostizierten Werte in einem Array.
	 */

	public double[][] prognoseBerechnen(
			DoubleArrayList trendbereinigtezeitreihe, DoubleMatrix2D matrixPhi,
			double standardabweichung, int zuberechnendeperioden,
			int durchlaeufe, int p, double mittelwert) {

		DoubleArrayList vergangeneUndZukuenftigeWerte = new DoubleArrayList();
		vergangeneUndZukuenftigeWerte = trendbereinigtezeitreihe.copy();
		double[][] prognosewertSammlung = new double[durchlaeufe][zuberechnendeperioden];
		double prognosewert = 0;
		double zNull = 0;
		Random zufall = new Random();

		// Ein Durchlauf der Schleife entpricht einer Prognose für j
		// Zukunftswerte
		for (int i = 0; i < durchlaeufe; i++) {
			logger.debug("Durchlauf " + (i + 1) + ":");

			// Ein Durchlauf entspricht der Prognose eines Jahres j
			for (int j = 0; j < zuberechnendeperioden; j++) {

				// Ein Durchlauf findet den Gewichtungsfaktor Phi und den dazu
				// passenden Vergangenheitswert.
				for (int t = 0; t < p; t++) {
					logger.debug(vergangeneUndZukuenftigeWerte.size() - (t + 1));
					logger.debug(vergangeneUndZukuenftigeWerte
							.get(vergangeneUndZukuenftigeWerte.size() - (t + 1)));
					logger.debug(matrixPhi.get(t, 0));
					prognosewert = prognosewert
							+ matrixPhi.get(t, 0)
							* vergangeneUndZukuenftigeWerte
									.get(vergangeneUndZukuenftigeWerte.size()
											- (t + 1));
				}

				zNull = zufall.nextGaussian() * standardabweichung;
				prognosewert = prognosewert + zNull;
				vergangeneUndZukuenftigeWerte.add(prognosewert);
				prognosewert = prognosewert + mittelwert;
				prognosewertSammlung[i][j] = prognosewert;
				logger.debug("Prognosewert " + (j + 1) + ":" + prognosewert);
				prognosewert = 0;
			}
			vergangeneUndZukuenftigeWerte = trendbereinigtezeitreihe.copy();
		}

		// TODO: Prüfung der Eingabewerte (ggf. an anderer Stelle)

		return prognosewertSammlung;
	}

	/**
	 * Methode für die Durchführung einer Zeitreihenanalyse und Prognostizierung
	 * zukünftiger Werte.
	 * 
	 * @author Marcel Rosenberger, Mirko Göpfrich, Nina Brauch, Raffaele
	 *         Cipolla, Maurizio di Nunzio
	 * @param zeitreihe
	 *            auf deren Basis die Prognose erfolgt
	 * @param p
	 *            die Anzahl der mit einbezogenen, vergangenen Perioden
	 * @param zuberechnendeperioden
	 *            die Anzahl der zu prognostizierenden, zukünftigen Perioden
	 * @param durchlaeufe
	 *            die Anzahl der Iterationen, die die Zeitreihenanalyse
	 *            durchlaufen soll
	 * @param callback
	 *            das Callback-Objekt, über das die Kommunikation über Threads
	 *            hinweg gesteuert wird
	 * @return Alle prognostizierten Werte in einem Array.
	 */

	// @Override
	public double[][] calculate(double[] zeitreihe, int p,
			int zuberechnendePerioden, int durchlaeufe,
			CallbackInterface callback) throws InterruptedException,
			StochasticMethodException {

		// vorbereitene Initialisierung
		double[][] prognosewerte = new double[zuberechnendePerioden][durchlaeufe];

		// Trendbereinigung der Zeitreihe wenn diese nicht stationaer ist
		CalculateTide tide = new CalculateTide();
		boolean isStationary = StationaryTest.isStationary(zeitreihe);
		if (!isStationary) {
			zeitreihe = tide.reduceTide(zeitreihe);
		}
		/**
		 * Uebertragung der Werte der Zeitreihe in eine DoubleArrayList. Diese
		 * wird von der COLT Bibliothek verwendet zur Loesung der Matrix.
		 */

		this.bereinigteZeitreihe = new DoubleArrayList();
		for (int i = 0; i < zeitreihe.length; i++) {
			this.bereinigteZeitreihe.add(zeitreihe[i]);
		}

		// Start der zur Prognose benoetigten Berechnungen
		this.mittelwert = berechneMittelwert(bereinigteZeitreihe);
		this.autokovarianzen = berechneAutokovarianz(bereinigteZeitreihe);
		this.modellparameter = berechneModellparameter(autokovarianzen, p);
		this.standardabweichung = berechneStandardabweichung(autokovarianzen,
				modellparameter);
		logger.debug("Zur Prognose benötigten Berechnungen abgeschlossen");

		// Start der Prognose
		prognosewerte = prognoseBerechnen(bereinigteZeitreihe, modellparameter,
				standardabweichung, zuberechnendePerioden, durchlaeufe, p,
				mittelwert);
		logger.debug("Berechnung der Prognosewerte abgeschlossen.");
		return prognosewerte;
	}

}
