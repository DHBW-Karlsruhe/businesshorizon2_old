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

package dhbw.ka.mwi.businesshorizon2.models;

import java.io.Serializable;

/**
 * Diese Klasse enthält die Methoden die für ein Projekt ausgewählt sind.
 * 
 * @author Timo Belz
 * @param Methods
 *            enthält die Namen der ausgewählten Methoden
 * @param balanceSheet
 *            false steht für direkte Casfloweingabe, true steht für die Eingabe
 *            von Bilanzwerten
 * 
 */

public class ProjectInputType implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5922498137882142059L;
	private Boolean stochastic = true;
	private Boolean deterministic = false;
	private InputType stochasticInput = InputType.DIRECT;
	private InputType deterministicInput = InputType.DIRECT;

	public Boolean isStochastic() {
		return stochastic;
	}

	public void setStochastic(Boolean stochastic) {
		this.stochastic = stochastic;
	}

	public Boolean isDeterministic() {
		return deterministic;
	}

	public void setDeterministic(Boolean deterministic) {
		this.deterministic = deterministic;
	}

	public InputType getStochasticInput() {
		return stochasticInput;
	}

	public void setStochasticInput(InputType stochasticInput) {
		this.stochasticInput = stochasticInput;
	}

	public InputType getDeterministicInput() {
		return deterministicInput;
	}

	public void setDeterministicInput(InputType deterministicInput) {
		this.deterministicInput = deterministicInput;
	}

}
