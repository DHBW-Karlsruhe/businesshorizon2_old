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



package dhbw.ka.mwi.businesshorizon2.methods.discountedCashflow;

import java.util.Map;
import java.util.TreeMap;

// TODO: Auto-generated Javadoc
/**
 * Diese Klasse speichert die Unternehmenswerte und ihre jeweiligen Häufigkeiten
 * in einer Map ab.
 * 
 * @author kathie
 * 
 */
public class CompanyValues {

	/**
	 * The Class Couple.
	 */
	public class Couple {
		
		/** The company value. */
		private final double companyValue;
		
		/** The count. */
		private int count;

		/**
		 * Instantiates a new couple.
		 *
		 * @param companyValue the company value
		 */
		public Couple(double companyValue) {
			this.companyValue = companyValue;
			count = 1;
		}

		/**
		 * Gets the company value.
		 *
		 * @return the company value
		 */
		public double getCompanyValue() {
			return companyValue;
		}

		/**
		 * Gets the count.
		 *
		 * @return the count
		 */
		public int getCount() {
			return count;
		}

		/**
		 * Increase count.
		 */
		private void increaseCount() {
			count++;
		}
	}

	/** The map. */
	private final TreeMap<Double, Couple> map;

	/**
	 * Instantiates a new company values.
	 */
	public CompanyValues() {
		map = new TreeMap<>();
	}

	/**
	 * Adds the company value.
	 *
	 * @param companyValue the company value
	 */
	public void addCompanyValue(double companyValue) {
		if (map.containsKey(companyValue)) {
			map.get(companyValue).increaseCount();
		} else {
			map.put(companyValue, new Couple(companyValue));
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

		for (Map.Entry<Double, Couple> entry : map.entrySet()) {
			entry.getValue().getCompanyValue();
			entry.getValue().getCount();
		}

		return map;
	}
}
