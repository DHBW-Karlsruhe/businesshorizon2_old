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
package dhbw.ka.mwi.businesshorizon2.models.CompanyValue;

import java.text.DecimalFormat;
import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;

import com.vaadin.ui.Label;

/**
 * Diese Klasse speichert die Unternehmenswerte und ihre jeweiligen Häufigkeiten
 * in einer Map ab.
 * 
 * @author kathie, Marcel Rosenberger
 * 
 */
public class CompanyValueStochastic extends CompanyValue {

	private static final Logger logger = Logger
			.getLogger("CompanyValueStochastic.class");

	public class Couple {
		private final double companyValue;
		private int count;

		public Couple(double companyValue) {
			this.companyValue = companyValue;
			count = 1;
		}

		public Couple(double companyValue, int count) {
			this.companyValue = companyValue;
			this.count = count;
		}

		public double getCompanyValue() {
			return companyValue;
		}

		public int getCount() {
			return count;
		}

		private void increaseCount() {
			count++;
		}

		private void increaseCount(int count) {
			this.count = this.count + count;
		}
	}

	private final TreeMap<Double, Couple> map;
	private final TreeMap<Double, Couple> gradedmap;

	// begrenzt die anzahl der angezeigten Unternehmenswerte

	public CompanyValueStochastic() {
		super();
		map = new TreeMap<>();
		gradedmap = new TreeMap<>();
	}

	public void addCompanyValue(double companyValue) {
		
		map.put(companyValue, new Couple(companyValue));
		
	}

	public void addCompanyBalue(double companyValue, int count) {


		if (map.containsKey(companyValue)) {
			map.get(companyValue).increaseCount(count);
		} else {
			map.put(companyValue, new Couple(companyValue, count));
		}
	}

	/**
	 * Gibt eine Map mit den einzelnen Unternehmenswerten und deren Häufigkeiten
	 * zurück. Die Map kann beispielsweise folgendermaßen ausgelesen werden:
	 * 
	 * <pre>
	 * {@code
	 * for (Map.Entry<Double, Couple> entry : map.entrySet()) {
	 * 			entry.getValue().getCompanyValue();
	 * 			entry.getValue().getCount();
	 * 		}
	 * </pre>
	 * 
	 * @return Ein Objekt der Klasse TreeMap mit den Unternehmenswerten und den
	 *         jeweiligen Häufigkeiten, wobei die TreeMap nach den
	 *         Unternehmenswerten sortiert ist.
	 */
	public TreeMap<Double, Couple> getCompanyValues() {
		return map;
	}

	/**
	 * Gibt eine Map mit den klassierten Unternehmenswerten und deren
	 * Häufigkeiten zurück. Die Anzahl der Klassierungsbreiten-/bzw.schritten
	 * (=Balken im Diagramm) wird. Dies geschieht anhand der Regel nach Scott
	 * zur Ermittlung der Klassenbreite. Ausgehend von der Klassenbreite kann
	 * berechnet werden wie viele Balken benötigt werden. Die Map kann
	 * beispielsweise folgendermaßen ausgelesen werden:
	 * 
	 * @author: Marcel Rosenberger
	 * 
	 * @return Ein Objekt der Klasse TreeMap mit den klassierten
	 *         Unternehmenswerten und den jeweiligen Häufigkeiten, wobei die
	 *         TreeMap nach den Unternehmenswerten sortiert ist.
	 */
	public TreeMap<Double, Couple> getGradedCompanyValues() {
		// Klassierungsschrittweite ermitteln

		// Streuung der Unternehmenswerte ermitteln
		double[] unternehmenswerte = new double[this.map.entrySet().size()];
		int z = 0;
		// Unternehmenswerte in ein Array casten
		for (Map.Entry<Double, Couple> entry : this.map.entrySet()) {
			unternehmenswerte[z] = entry.getValue().getCompanyValue();
			z++;
		}
		double streuung = this.berechnestreuung(unternehmenswerte); // Streuung
		double n = unternehmenswerte.length; // Anzahl der Unternehmenswerte

		// Klassenbreite (Berechnung mit der Regel nach Scott) aus Wikipedia
		double h = (3.49 * streuung) / Math.pow(n, (1.0 / 3.0));
		logger.debug("klassenbreite: " + h);

		// Kleinsten Wert ermitteln
		double kleinster = this.map.firstEntry().getValue().getCompanyValue();
	
		// Größten Wert ermitteln
		double größter = this.map.lastEntry().getValue().getCompanyValue();
	
		// Delta Ermitteln
		double delta = größter - kleinster;

		// Anzahl Klassierungsschritte ermitteln
		int klassierungsschritte = (int) (delta / h);
		logger.debug("klassierungsschritte: " + klassierungsschritte);
		
		//halbenSchritt ermitteln
		double halberschritt = h / 2.0;
		// Klassierungsdurchschnitt initialisieren
		double klassierungsdurchschnitt = kleinster;
		
		//In wenigen Ausnahmefällen kann es sein, dass zu wenig (< 10)Balken errechnet werden.
		//In diesen Fällen sollen default-mäßig 14 Balken angezeigt werden.
		if(klassierungsschritte < 10){
			klassierungsschritte = 14;
			h = delta / klassierungsschritte;
			halberschritt = h / 2.0;
		}
		
		// legt die Klassierte Map an mit einem Eintrag pro Klassierungsschritt
		for (int i = 0; i <= klassierungsschritte; i++) {
			klassierungsdurchschnitt = klassierungsdurchschnitt + h;
			// logger.debug("Klassierung:" + klassierungsdurchschnitt);
			this.gradedmap.put(klassierungsdurchschnitt, new Couple(
					klassierungsdurchschnitt, 0));
		}
		
		boolean nochnichtverteilt = true;
		// Unternehmenswerte werden auf die klassierte Map verteilt
		for (Map.Entry<Double, Couple> entry : this.map.entrySet()) {
			double unternehmenswert = entry.getValue().getCompanyValue();
			nochnichtverteilt = true;
			for (Map.Entry<Double, Couple> klassierterEintrag : this.gradedmap
					.entrySet()) {
				double key = klassierterEintrag.getKey();
				// wenn ein Unternehmenswert innerhalb eines
				// Klassierungsschrittes liegt,
				// wird die Anzahl der Werte dieses Klassierungsschrittes
				// erhöht.
				if ((((unternehmenswert - halberschritt) < key) )
						&& ((unternehmenswert + halberschritt) >= key) && nochnichtverteilt) {
					klassierterEintrag.getValue().increaseCount();
					nochnichtverteilt = false;
				} 
				
				
			}
		}

		return this.gradedmap;
	}

	public double berechnestreuung(double[] unternehmenswerte) {
		// Mittelwert berechnen
		double s = 0; // Vorbereitung
		for (int j = 0; j < unternehmenswerte.length; j++) { // Schleife
			s = s + unternehmenswerte[j];
		}
		double mw = s / unternehmenswerte.length; // Nachbereitung

		// Streuung berechnen
		s = 0;
		for (int i = 0; i < unternehmenswerte.length; i++) {
			s = s + ((mw - unternehmenswerte[i]) * (mw - unternehmenswerte[i]));
		}
		double streuung = Math.sqrt(s / unternehmenswerte.length);
		return streuung;
	}
}
