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

package dhbw.ka.mwi.businesshorizon2.models.PeriodContainer;

import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

import dhbw.ka.mwi.businesshorizon2.models.Period.CashFlowPeriod;
import dhbw.ka.mwi.businesshorizon2.models.Period.Period;

/**
 * Diese Klasse stellt einen Zeitreihe von CashFlow Perioden dar.
 * 
 * @author Kai Westerholz
 * 
 */

public class CashFlowPeriodContainer extends AbstractPeriodContainer {

	public CashFlowPeriodContainer() {
		super();
	}

	@Override
	public TreeSet<CashFlowPeriod> getPeriods() {
		// TODO Auto-generated method stub
		return (TreeSet<CashFlowPeriod>) super.getPeriods();
	}

	/**
	 * @author: Annika Weis
	 * @date: 02.01.2014
	 */
	public double[] getCashflows() {
		TreeSet<CashFlowPeriod> perioden = (TreeSet<CashFlowPeriod>) super.getPeriods();

		Iterator<CashFlowPeriod> itr = perioden.iterator();
		double[] cashflows = new double[perioden.size()];
		int i = 0;
		while (itr.hasNext()) {
			CashFlowPeriod c = itr.next();
			cashflows[i] = c.getFreeCashFlow();
			i++;
		}

		return cashflows;
	}

}
