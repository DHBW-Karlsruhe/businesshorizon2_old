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


package dhbw.ka.mwi.businesshorizon2.tests.methods.discountedCashflow;

import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.junit.Test;


import dhbw.ka.mwi.businesshorizon2.methods.discountedCashflow.APV_2;
import dhbw.ka.mwi.businesshorizon2.models.Szenario;


/**
 * Diese Klasse stellt den jUnit-Test der im Klassenname aufgeführten Methode in der Klasse AnalysisTime dar.
 * 
 * @author Volker Maier
 * 
 */

public class TestAPV_2 extends TestCase {
	
	private static final Logger logger = Logger.getLogger("APV_2.class");
	
		
	@Test
	public void testAPV_2() {
		double[] cashflow = new double [5]; 
		double[] fremdkapital= new double [5];
		double rateReturnEquity = 9.969;  
		double rateReturnCapitalStock = 8.0;
		double businessTax = 14.0; 
		double corporateAndSolitaryTax = 15.825; 
		boolean includeInCalculation = true;
		Szenario szenario = new Szenario (rateReturnEquity, rateReturnCapitalStock, businessTax, corporateAndSolitaryTax, includeInCalculation);
		double ergebnisVorgabe= 1055.247;
		double ergebnis;
		
		
		cashflow [0]= 0.0;
		cashflow [1]= 133.61;
		cashflow [2]= 202.31;
		cashflow [3]= 174.41;
		cashflow [4]= 202.52;
		
		
		fremdkapital [0]= 1260;
		fremdkapital [1]= 1320;
		fremdkapital [2]= 1330;
		fremdkapital [3]= 1400;
		fremdkapital [4]= 1400;
		
		APV_2 ap = new APV_2();
	
		ergebnis = ap.calculateValues(cashflow , fremdkapital, szenario);
				
		logger.debug(ergebnisVorgabe);
		logger.debug(ergebnis);
		
			assertEquals(ergebnisVorgabe,ergebnis);
		}

	}


