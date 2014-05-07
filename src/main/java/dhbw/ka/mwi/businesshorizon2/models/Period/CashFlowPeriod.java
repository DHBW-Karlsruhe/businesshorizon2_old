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

package dhbw.ka.mwi.businesshorizon2.models.Period;

/**
 * Diese Klasse bildet eine Periode ab. Das Jahr muss übergeben werden und kann
 * nicht mehr bewertet werden. Die Perioden enthalten nur CashFlows.
 * 
 * @author Kai Westerholz
 * 
 */

public class CashFlowPeriod extends Period {

	private static final long serialVersionUID = 1L;

	public CashFlowPeriod(int year) {
		super(year);
	}

	/**
	 * Gibt zurück, ob alle erforderlichen Parameter gesetzt sind
	 * 
	 * @author Annika Weis
	 */
	public boolean isValid() {
		Boolean valid = true;
		Double capitalStock = this.getCapitalStock();
		Double freeCashFlowSet = this.getFreeCashFlow();
		if (capitalStock == null) {
			valid = false;
		}
		if (freeCashFlowSet == null) {
			valid = false;
		}
		return valid;
	}
}
