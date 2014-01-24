/*******************************************************************************
* BusinessHorizon2
*
* Copyright (C) 2012-2013 Christian Gahlert, Florian Stier, Kai Westerholz,
* Timo Belz, Daniel Dengler, Katharina Huber, Christian Scherer, Julius Hacker
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


package dhbw.ka.mwi.businesshorizon2.models.PeriodContainer;

import java.util.TreeSet;
import dhbw.ka.mwi.businesshorizon2.models.Period.IndirectCalculatedCashflowPeriod;

/**
* Diese Klasse stellt einen Container für Perioden zu verfügung. Die
* enthaltenen Bilanzposten der Periode sind nach dem Umsatzkostenverfahren
* bewertet.
*
*
* @author Kai Westerholz
*
*/

public class IndirectCalculatedCashflowPeriodContainer extends
                AbstractPeriodContainer {

        /**
         *
         */
        private static final long serialVersionUID = -6233272268973216545L;

        public IndirectCalculatedCashflowPeriodContainer() {
                super();
        }

        @Override
        public TreeSet<IndirectCalculatedCashflowPeriod> getPeriods() {
                return (TreeSet<IndirectCalculatedCashflowPeriod>) super.getPeriods();
        }

}
