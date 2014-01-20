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

//Annika Weis

package dhbw.ka.mwi.businesshorizon2.ui.process.method;

import java.util.Set;

import com.mvplite.event.Event;

import dhbw.ka.mwi.businesshorizon2.methods.AbstractCalculationMethod;
import dhbw.ka.mwi.businesshorizon2.methods.AbstractStochasticMethod;

public class CheckMethod_deterministicEvent extends Event {

	private static final long serialVersionUID = 1L;	
	private Set<AbstractCalculationMethod> checkedMethods;
	
	public CheckMethod_deterministicEvent(Set<AbstractCalculationMethod> checkedMethods) {
		super();
		this.checkedMethods=checkedMethods;
	}

	public Set<AbstractCalculationMethod> getCheckedMethods() {
		return checkedMethods;
	}


}
