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

import dhbw.ka.mwi.businesshorizon2.methods.timeseries.CalculateTide;

/**
 * Diese Klasse stellt den jUnit-Test der im Klassenname aufgeführten Methode in der Klasse AnalysisTime dar.
 * 
 * @author Volker Maier
 * 
 */

public class CalculateTideTest extends TestCase {
	private static final Logger logger = Logger.getLogger("CalculateTideTest.class");
	
	@Test
	public void testReduceTide() {
		double[] timeseries = new double[3];
		timeseries[0] = 130594000.00;
		timeseries[1] = 147552000.00;
		timeseries[2] = 144040000.00;
		CalculateTide tide = new CalculateTide();
		for (int i = 0; i < timeseries.length; i++){
			timeseries = tide.reduceTide(timeseries, timeseries.length-i);
		}
		logger.debug("Timeseries: " + timeseries[0]);
		logger.debug("Timeseries: " + timeseries[1]);
		logger.debug("Timeseries: " + timeseries[2]);
		double[] results = new double[timeseries.length];
		results[0] = 1.345634761904765E8;
		results[1] = 1.3974901904761934E8;
		results[2] = 1.4493456190476218E8;
		//System.out.println(timeseries[0]);
		//System.out.println(results[0]);
		for (int i = 0; i < timeseries.length-1; i++) {
			assertEquals(timeseries[i], results[i]);
		}

	}
}
