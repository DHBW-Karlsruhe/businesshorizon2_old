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

public abstract class CompanyValue {

	protected CompanyValue() {
	}

	/**
	 * Die Methode rundet einen double auf eine bestimmte Zahl an
	 * Nachkommastelle.
	 * 
	 * @param number
	 *            Zahl, die gerundet werden soll.
	 * @param decimalPlace
	 *            Stellen, auf die gerundet werden soll. 
	 * @param place Wenn place
	 *            auf true gesetzt wird, wird auf die Vorkommastellen gerundet,
	 *            wenn place auf false gesetzt wird, wird auf die
	 *            Nachkommastellen gerundet.
	 * @return number Gerundete Zahl
	 */
	protected double roundToDecimalPlaces(double number, int decimalPlace,
			boolean place) {

		int temp = 1;

		for (int i = 0; i < decimalPlace; i++) {
			temp = temp * 10;
		}

		if (place) {
			number = number / temp;
			number = Math.round(number);
			number = number * temp;
		} else {
			number = number * temp;
			number = Math.round(number);
			number = number / temp;
		}
		return number;
	}
}
