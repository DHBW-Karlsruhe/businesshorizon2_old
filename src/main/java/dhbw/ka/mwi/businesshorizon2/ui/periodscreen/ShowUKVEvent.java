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

package dhbw.ka.mwi.businesshorizon2.ui.periodscreen;

import com.mvplite.event.Event;

import dhbw.ka.mwi.businesshorizon2.models.Period.UmsatzkostenVerfahrenCashflowPeriod;

public class ShowUKVEvent extends Event implements ShowInputViewEventInterface{
        
        /**
         *
         */
        private static final long serialVersionUID = -2773797208503072554L;
        private UmsatzkostenVerfahrenCashflowPeriod period;
        
        public ShowUKVEvent(UmsatzkostenVerfahrenCashflowPeriod period){
                this.period = period;
        }

        public ShowUKVEvent() {
			// TODO Auto-generated constructor stub
		}

		public UmsatzkostenVerfahrenCashflowPeriod getPeriod() {
                return period;
        }

}
