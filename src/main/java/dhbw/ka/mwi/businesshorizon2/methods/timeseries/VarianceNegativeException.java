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

import dhbw.ka.mwi.businesshorizon2.methods.StochasticMethodException;

/**
 * Diese Exception bildet den Fall ab, dass die Berechnung der Varianz
 * fehlgeschlagen ist. Diese kann nicht negativ sein, da mit der Wurzel aus
 * dieser Varianz weiter gerechnet wird (WhiteNoise).
 * 
 * @author Kai Westerholz
 * 
 */

public class VarianceNegativeException extends StochasticMethodException {

	private static final long serialVersionUID = 1L;

	public VarianceNegativeException(String s) {
		super(s);
	}

}
