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

package dhbw.ka.mwi.businesshorizon2.models.CompanyValue;

import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;

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
	private static final int klassierungsschritte = 40;

	public CompanyValueStochastic() {
		super();
		map = new TreeMap<>();
		gradedmap = new TreeMap<>();
	}

	public void addCompanyValue(double companyValue) {

		companyValue = this.roundToDecimalPlaces(companyValue, 0, false);

		if (map.containsKey(companyValue)) {
			map.get(companyValue).increaseCount();
		} else {
			map.put(companyValue, new Couple(companyValue));
		}
	}

	public void addCompanyBalue(double companyValue, int count) {

		companyValue = this.roundToDecimalPlaces(companyValue, 0, false);

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
	 * Häufigkeiten zurück. Die Anzahl der Klassierungsschritten (=Balken im
	 * Diagramm) ist festgesetzt und sollte nur unter Vorbehalt geändert werden.
	 * Wenn man bspw. zu viele Klassierungsschritte wählt, kann es sein dass der
	 * Erwartungswert später nicht mehr sichtbar ist. Die Map kann
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
		// Kleinsten Wert ermitteln
		double kleinster = this.map.firstEntry().getValue().getCompanyValue();
		logger.debug("kleinster:" + kleinster);
		// Größten Wert ermitteln
		double größter = this.map.lastEntry().getValue().getCompanyValue();
		logger.debug("größter:" + größter);
		// Delta Ermitteln
		double delta = größter - kleinster;
		logger.debug("delta:" + delta);
		double deltahalbe = delta / 2.0;
		// Schritte Ermitteln
		double schritt = delta / klassierungsschritte;
		//Klassierungsdurchschnitt initialisieren
		double klassierungsdurchschnitt = kleinster;
		
		// legt die Klassierte Map an mit einem Eintrag pro Klassierungsschritt
		for(int i = 0; i <= klassierungsschritte; i++ ) {
			klassierungsdurchschnitt = klassierungsdurchschnitt +  schritt;
			logger.debug("Klassierung:" + klassierungsdurchschnitt);
			this.gradedmap.put(klassierungsdurchschnitt, new Couple(
					klassierungsdurchschnitt, 0));
		}

		// Unternehmenswerte werden auf die klassierte Map verteilt
		for (Map.Entry<Double, Couple> entry : this.map.entrySet()) {
			double unternehmenswert = entry.getValue().getCompanyValue();
			for (Map.Entry<Double, Couple> klassierterEintrag : this.gradedmap
					.entrySet()) {
				double key = klassierterEintrag.getKey();
				// wenn ein Unternehmenswert innerhalb eines
				// Klassierungsschrittes liegt,
				// wird die Anzahl der Werte dieses Klassierungsschrittes
				// erhöht.
				if (((unternehmenswert - deltahalbe) <= key)
						&& ((unternehmenswert + deltahalbe) >= key)) {
					klassierterEintrag.getValue().increaseCount();
				}
			}
		}

		return this.gradedmap;
	}
}
