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
import org.junit.Ignore;
import org.junit.Test;

import dhbw.ka.mwi.businesshorizon2.methods.discountedCashflow.APV;
import dhbw.ka.mwi.businesshorizon2.models.Szenario;


/**
 * Diese Klasse stellt den jUnit-Test der im Klassenname aufgeführten Methode in der Klasse AnalysisTime dar.
 * 
 * @author Volker Maier
 * 
 */
@Ignore //FIXME seazzle
public class TestAPV extends TestCase {
	
	private static final Logger logger = Logger.getLogger("TestAPV.class");
	
		
	@Test
	public void testAPV() {
		double[] cashflow = new double [5]; 
		double[] fremdkapital= new double [5];
		double rateReturnEquity = 9.969;  
		double rateReturnCapitalStock = 8.0;
		double businessTax = 14.0; 
		double corporateAndSolitaryTax = 15.825; 
		boolean includeInCalculation = true;
		Szenario szenario = new Szenario( rateReturnEquity,  rateReturnCapitalStock,
				 businessTax,  corporateAndSolitaryTax,  includeInCalculation);
		double ergebnisVorgabe= 1055.2755762598144;
		double ergebnis;
		
		
		cashflow [0]= 0.0;
		cashflow [1]= 138.61;
		cashflow [2]= 202.31;
		cashflow [3]= 174.41;
		cashflow [4]= 202.52;
		
		
		fremdkapital [0]= 1260.0;
		fremdkapital [1]= 1320.0;
		fremdkapital [2]= 1330.0;
		fremdkapital [3]= 1400.0;
		fremdkapital [4]= 1400.0;
		
		APV ap = new APV();
	
		ergebnis = ap.calculateValues(cashflow , fremdkapital, szenario);
				
		logger.debug(ergebnisVorgabe);
		logger.debug(ergebnis);
		
			assertEquals(ergebnisVorgabe,ergebnis);
		}

	}


