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

package dhbw.ka.mwi.businesshorizon2.tests.methods.timeseries;

import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.junit.Test;

import cern.colt.list.DoubleArrayList;

import dhbw.ka.mwi.businesshorizon2.methods.timeseries.AnalysisTimeseries;

/**
 * Diese Klasse stellt den jUnit-Test der im Klassenname aufgeführten Methode in der Klasse AnalysisTime dar.
 * 
 * @author Volker Maier
 * 
 */


public class TestMittelwert extends TestCase {
	
	private static final Logger logger = Logger.getLogger("AnalysisTimeseries.class");
	
	@Test
	public void testMittelwert() {
		AnalysisTimeseries at = new AnalysisTimeseries();
		DoubleArrayList cashflows = new DoubleArrayList ();
		double mittelwert;
		double mittelwertVorgabe= 8.166666666666666;
		cashflows.add (7);
		cashflows.add (9);
		cashflows.add (5);
		cashflows.add (14);
		cashflows.add (6);
		cashflows.add (8);
		mittelwert = at.berechneMittelwert(cashflows);
		logger.debug(mittelwertVorgabe);
		logger.debug(mittelwert);
		
			assertEquals(mittelwertVorgabe, mittelwert);
		}
	
	}
