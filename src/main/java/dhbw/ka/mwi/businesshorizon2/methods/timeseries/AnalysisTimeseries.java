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

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.jamesii.core.math.statistics.timeseries.AutoCovariance;
import cern.colt.list.DoubleArrayList;
import cern.colt.matrix.DoubleFactory2D;
import cern.colt.matrix.DoubleMatrix2D;
import cern.colt.matrix.linalg.LUDecomposition;
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
			.getLogger("AnalysisTimeseries.class");
	private DoubleArrayList bereinigteZeitreihe;
	private DoubleMatrix2D modellparameter;;
	private double standardabweichung;
	private double mittelwert;
	private double[] erwarteteCashFlows;
	private double[] erwartetesFremdkapital;
	private double abweichung;
	private CalculateTide tide;

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
		
		logger.debug("BerechneAutokovarianz");
		
		DoubleArrayList autokovarianz = new DoubleArrayList();

		// DoubleArrayList zu einer List casten, die von der
		// autoCovariance-Methode verwendet werden kann
		List<Double> lokalereihe = new ArrayList<Double>();
		Double t = new Double(0);
		for (int i = 0; i < zeitreihe.size(); i++) {
			t = zeitreihe.get(i);
			lokalereihe.add(t);
			//logger.debug("Lokalreihe: " + zeitreihe.get(i));
		
		}

		// berechnet die Autokovarianzen der Zeitreihe in Abhängigkeit von j
		for (int j = 0; j < zeitreihe.size(); j++) {
			autokovarianz.add(AutoCovariance.autoCovariance(lokalereihe, j));
			//logger.debug("Autokorvarianz: " + AutoCovariance.autoCovariance(lokalereihe, j));
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
	public DoubleMatrix2D berechneModellparameter(DoubleArrayList autokovarianzen, int p)	throws StochasticMethodException {
		logger.debug("BerechneModellparameter");

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
		//logger.debug("MatrixValuations:" + matrixValuations.toString());

		// Matrix mit Modellparametern (Phi)
		DoubleMatrix2D matrixPhi = null;
		
		//Überprüfen der Matrix auf Singularität
		//Wenn ja, Rückgabe der Matrix unverändert
		//TODO Überprüfen wie die Berechnung angepasst werden muss
		//author Felix Schlosser
		if (!lUDecomp.isNonsingular()){
			logger.debug("Matrix ist singular, wird unverändert zurückgeben");
			//logger.debug("MatrixErg: " + matrixERG.toString());
			return matrixERG;
		}
		
		try {
			matrixPhi = lUDecomp.solve(matrixERG);
			logger.debug("C-Values of Yule-Walker-Equitation calculated.");
			//logger.debug("MatrixPhi:" + matrixPhi.toString());
		} catch (IllegalArgumentException exception) {

			logger.debug("Calculation of C-Values failed!");
			throw new StochasticMethodException(exception.getMessage());

		}

		return matrixPhi;
	}

	/**
	 * Methode zur Berechung der Standardabweichung einer Zeitreihe 
	 * 
	 * @author Felix Schlosser
	 * 
	 * @param Zeitreihe
	 *            die aktuelle Zeitreihe
	 * @param einbezogenePerioden
	 *            Verrechnungsparameter, wie viele Perioden nicht in die Berechnung einbezogen werden
	 * @return Gibt die Standardabweichung zurück.
	 */
	//
	public double berechneStandardabweichung(DoubleArrayList zeitreihe, int einbezogenePerioden) {
		double standardabweichung = 0;
		int durchlauf = zeitreihe.size() - einbezogenePerioden; 
		
		// Berechnung Mittelwert
		double mw= 0;
		for (int i = 0; i < durchlauf; i++){
			mw += zeitreihe.get(i);
		}
		mw = mw / durchlauf;
		// Berechnung der Varianz
		
		double variance = 0;
		for (int i = 0; i < durchlauf; i++){
			variance += Math.pow((zeitreihe.get(i)-mw), 2);	
		}
		variance = variance / durchlauf;

		//logger.debug("Varianz: " + variance);
		
		// Berechnung der Standardabweichung aus der Varianz
		standardabweichung = Math.sqrt(variance);

		//logger.debug("Standardabweichung:" + standardabweichung);
		
		return standardabweichung;
	}

	/**
	 * Methode zur Prognose zukünftiger Werte einer gegebenen Zeitreihe und
	 * einem AR-Modell. Sie berechnet die jeweiligen Prognosewerte und gibt sie
	 * in einem zweidimensionalen Array zurück.
	 * 
	 * @author Nina Brauch, Mirko Göpfrich, Raffaele Cipolla, Marcel Rosenberger, Felix Schlosser
	 * 
	 * @param trendbereinigtezeitreihe
	 *            , die bereits trendbereinigte Zeitreihe
	 * @param matrixPhi
	 *            die ermittelte Matrix Phi
	 * @param Ordnung
	 *            p die Anzahl der mit einbezogenen, vergangenen Perioden
	 * @param zuberechnendeperioden
	 *            die Anzahl der zu prognostizierenden, zukünftigen Perioden
	 * @param durchlaeufe
	 *            die Anzahl der Iterationen, die die Zeitreihenanalyse
	 *            durchlaufen soll
	 * @param mittelwert
	 *            der ermittelte Mittelwert der Zeitreihe
	 * @param isfremdkapital 
	 * @return Alle prognostizierten Werte in einem Array.
	 */

	public double[] prognoseBerechnen(DoubleArrayList trendbereinigtezeitreihe, DoubleMatrix2D matrixPhi, int zuberechnendeperioden, int durchlaeufe, int p, double mittelwert, boolean isfremdkapital) {

		logger.debug("PrognoseBerechnen");
		
		DoubleArrayList vergangeneUndZukuenftigeWerte = new DoubleArrayList();
		vergangeneUndZukuenftigeWerte = trendbereinigtezeitreihe.copy();
		double[] erwarteteWerte = new double[zuberechnendeperioden];
		double prognosewert = 0;
		double zNull = 0;

		// Ein Durchlauf der Schleife entpricht einer Prognose für j
		// Zukunftswerte
		for (int i = zuberechnendeperioden; i > 0; i--) {

				standardabweichung = this.berechneStandardabweichung(vergangeneUndZukuenftigeWerte, i);
				
				//Berechnen einer Zufallszahl zwischen -1 und 1
				double z = Math.random();
				if (Math.random() < 0.5){
					z *= -1;
				}
				
				//logger.debug("Zufallszahl:" + z);
				zNull = z * standardabweichung;
				//logger.debug("zNull: " + zNull);
				
				prognosewert = vergangeneUndZukuenftigeWerte.get(vergangeneUndZukuenftigeWerte.size()-i) + zNull;  //prognosewert + zNull;
				logger.debug("Prognosewert: " + prognosewert);
				
				vergangeneUndZukuenftigeWerte.set(vergangeneUndZukuenftigeWerte.size()-i, prognosewert);
								
				erwarteteWerte[zuberechnendeperioden-i] = prognosewert;

				prognosewert = 0;
			}
	
			//logger.debug("Erwartete Werte: " + erwarteteWerte.toString());
			
			if(isfremdkapital){
				this.erwartetesFremdkapital = erwarteteWerte;
			}else{
				this.erwarteteCashFlows = erwarteteWerte;
			}
		
		
		return erwarteteWerte;
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
	public double[] calculate(double[] zeitreihe, int p,
			int zuberechnendePerioden, int durchlaeufe,
			CallbackInterface callback, boolean isfremdkapital) throws InterruptedException,
			StochasticMethodException {
		logger.debug("AnalysisTimeseries Calculate");
		logger.debug("Zeitreihe: " + zeitreihe.toString());
		
		// vorbereitende Initialisierung
		double[] alteUndPrognosewerte = new double[zuberechnendePerioden];

		
		// Trendbereinigung der Zeitreihe wenn diese nicht stationaer ist
		//Der Stationaritätstest ist nicht implementiert und liefert zurück dass die Zeitreihe nicht stationär ist
		tide = new CalculateTide();
		boolean isStationary = StationaryTest.isStationary(zeitreihe);
		if (!isStationary) {
			zeitreihe = tide.reduceTide(zeitreihe, zuberechnendePerioden);
		}
		logger.debug("Bereinigte Zeitreihe: " + zeitreihe.toString());
		/**
		 * Uebertragung der Werte der Zeitreihe in eine DoubleArrayList. Diese
		 * wird von der COLT Bibliothek verwendet zur Loesung der Matrix.
		 */

		this.bereinigteZeitreihe = new DoubleArrayList();
		for (int i = 0; i < zeitreihe.length; i++) {
			this.bereinigteZeitreihe.add(zeitreihe[i]);
		}

		logger.debug("Bereinigte Zeitreihe:");
		logger.debug(bereinigteZeitreihe);

		// Start der zur Prognose benoetigten Berechnungen
		this.mittelwert = berechneMittelwert(bereinigteZeitreihe);
		//this.standardabweichung = berechneStandardabweichung(bereinigteZeitreihe);
		logger.debug("Zur Prognose benötigten Berechnungen abgeschlossen");

		// Start der Prognose
		alteUndPrognosewerte = prognoseBerechnen(bereinigteZeitreihe, modellparameter, zuberechnendePerioden, durchlaeufe, p,
				mittelwert, isfremdkapital);
		logger.debug("Berechnung der Prognosewerte abgeschlossen.");
		

		return alteUndPrognosewerte;
	}

	/*
	 * Diese Methode berechnet eine Prognose für die mitgegebene Zeitreihe und
	 * dem gegebenen Modell. Da Z0=seinem Erwartungswert= 0 gesetzt wird,
	 * entspricht jeder prognostizierte Wert seinem Erwartungswert. Die Anzahl
	 * der Prognosewerte ergibt sich aus der Variablen zuberechnendeperioden.
	 * Die berechneten Werte werden in der Instanzvariablen erwarteteCashFlows
	 * der Klasse AnalysisTimeseries gespeichert.
	 * 
	 * @author Nina Brauch
	 */
	public void erwarteteWerteBerechnen(DoubleArrayList trendbereinigtezeitreihe, DoubleMatrix2D matrixPhi,	int zuberechnendeperioden, int p, double mittelwert, boolean isfremdkapital) {
		logger.debug("ErwarteteWerteBerechnen");
		double[] erwarteteWerte = new double[zuberechnendeperioden];
		double prognosewert = 0;
		DoubleArrayList vergangeneUndZukuenftigeWerte = new DoubleArrayList();
		vergangeneUndZukuenftigeWerte = trendbereinigtezeitreihe.copy();

		erwarteteWerte = new double[zuberechnendeperioden];
		// Ein Durchlauf entspricht der Prognose eines Jahres j
		for (int j = 0; j < zuberechnendeperioden; j++) {
			// Ein Durchlauf findet den Gewichtungsfaktor Phi und den dazu
			// passenden Vergangenheitswert.
			for (int t = 0; t < p; t++) {
				prognosewert = prognosewert
						+ matrixPhi.get(t, 0)
						* vergangeneUndZukuenftigeWerte
								.get(vergangeneUndZukuenftigeWerte.size() //Weißes Rauschen?
										- (t + 1));
			}
			vergangeneUndZukuenftigeWerte.add(prognosewert);
			prognosewert = prognosewert + mittelwert;
			erwarteteWerte[j] = prognosewert;
			logger.debug("ErwarteteWerte"+ j+ ": " + erwarteteWerte[j]);

			prognosewert = 0;
		}
		
		if(isfremdkapital){
			this.erwartetesFremdkapital = erwarteteWerte;
		}else{
			this.erwarteteCashFlows = erwarteteWerte;
		}
		
	}

	public double[] getErwarteteCashFlows() {
		return this.erwarteteCashFlows;
	}

	/*
	 * Diese Methode validiert das berechnete AR-Modell. Dabei wird angenommen,
	 * dass der Wert zum Zeitpunkt 0 noch nicht realisiert ist und wird aus dem
	 * berechneten Modell prognostiziert. Danach wird der Prognosewert mit dem
	 * tatsächlich realiserten Wert verglichen und eine prozentuale Abweichung
	 * berechnet.
	 * 
	 * @author: Nina Brauch
	 */

//	public void validierung(DoubleArrayList trendbereinigtezeitreihe,
//			DoubleMatrix2D matrixPhi, int p) {		
//		
//		double prognosewert = 0;
//		double realisierungsWert = trendbereinigtezeitreihe
//				.get(trendbereinigtezeitreihe.size() - 1);
//		realisierungsWert = realisierungsWert + tide.getTideValue(p);
//		logger.debug("Realisierungswert: " + realisierungsWert);
//		
//		// Ein Durchlauf findet den Gewichtungsfaktor Phi und den dazu passenden
//		// Vergangenheitswert.
//		// Hier wird der Prognosewert für den Zeitpunkt 0 berechnet
//		for (int t = 0; t < p; t++) {
//			prognosewert = prognosewert
//					+ (matrixPhi.get(t, 0)
//					* trendbereinigtezeitreihe.get(trendbereinigtezeitreihe
//							.size() - (t + 2)));			
//		}
//		prognosewert = prognosewert + tide.getTideValue(p);
//		// Berechnung der prozentualen Abweichung
//		double h = prognosewert / (realisierungsWert / 100);
//		// Die Variable abweichung enthält die Abweichung in %, abweichung =1
//		// --> Die Abweichung beträgt 1%
//		double abweichung = Math.abs(h - 100);
//		setAbweichung(abweichung);
//	}

	public double getAbweichung() {
		return abweichung;
	}

	public void setAbweichung(double abweichung) {
		this.abweichung = abweichung;
	}

	public double[] getErwartetesFremdkapital() {
		return erwartetesFremdkapital;
	}

}
