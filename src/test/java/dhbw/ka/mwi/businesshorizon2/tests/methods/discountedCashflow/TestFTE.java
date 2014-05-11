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

package dhbw.ka.mwi.businesshorizon2.tests.methods.discountedCashflow;



import junit.framework.TestCase;
import org.apache.log4j.Logger;
import org.junit.Test;
import dhbw.ka.mwi.businesshorizon2.methods.discountedCashflow.FTE;
import dhbw.ka.mwi.businesshorizon2.models.Szenario;

/**
* Diese Klasse stellt den jUnit-Test der im Klassenname aufgeführten Methode dar.
*
* @author Volker Maier
*
*/


public class TestFTE extends TestCase {
        private static final Logger logger = Logger.getLogger("TestFTE.class");
        
        
        @Test
        public void testFTE() {
                double[] cashflow = new double [5];
                double rateReturnEquity = 10.0;
                double rateReturnCapitalStock = 0.0;
                double businessTax = 0.0;
                double corporateAndSolitaryTax = 0.0;
                boolean includeInCalculation = true;
                Szenario szenario = new Szenario( rateReturnEquity, rateReturnCapitalStock,
                                 businessTax, corporateAndSolitaryTax, includeInCalculation);
                double ergebnisVorgabe = 10649.55877330783;
                double ergebnis;
                
                
                cashflow [0]= 125.0;
                cashflow [1]= 847.5;
                cashflow [2]= 957.89;
                cashflow [3]= 1083.69;
                cashflow [4]= 1226.28;
                
                
                FTE ft = new FTE();
        
                ergebnis = ft.calculateValues(cashflow , szenario);
                
                
                                
                logger.debug(ergebnisVorgabe);
                logger.debug(ergebnis);
                
                assertEquals(ergebnisVorgabe, ergebnis);
        
        }
}