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
import cern.colt.matrix.DoubleFactory2D;
import cern.colt.matrix.DoubleMatrix2D;


import dhbw.ka.mwi.businesshorizon2.methods.timeseries.AnalysisTimeseries;


/**
 * Diese Klasse stellt den jUnit-Test der im Klassenname aufgeführten Methode in der Klasse AnalysisTime dar.
 * 
 * @author Volker Maier
 * 
 */

public class TestPrognoseBerechnen extends TestCase {
	
	private static final Logger logger = Logger.getLogger("AnalysisTimeseries.class");
	
		
	@Test
	public void testPrognoseBerechnen() {
		int p = 5;
		int i = 1;
		DoubleMatrix2D matrixValuations = DoubleFactory2D.dense.make(p, i);
		DoubleArrayList cashflows = new DoubleArrayList ();
		double standardabweichung = 1.8551461075783124; 
		int zuberechnendeperioden = 5;
		int durchlaeufe = 10000;
		double mittelwert = 8.166666666666666;
		boolean isfremdkapital = true;
		double[][] prognosewerte = new double[zuberechnendeperioden][durchlaeufe];
		
		cashflows.add (7);
		cashflows.add (9);
		cashflows.add (5);
		cashflows.add (14);
		cashflows.add (6);
		cashflows.add (8);
		
		matrixValuations.set(0,0,-1.09253751);
		matrixValuations.set(1,0,-0.790322469);
		matrixValuations.set(2,0,-0.651577395);
		matrixValuations.set(3,0,-0.488155996);
		matrixValuations.set(4,0,-0.215330237);
	
		AnalysisTimeseries at = new AnalysisTimeseries();
		DoubleArrayList autokovarianzVorgabe = new DoubleArrayList();
		autokovarianzVorgabe.add (8.472222222222223);
		autokovarianzVorgabe.add (-5.726851851851852);
		autokovarianzVorgabe.add (2.407407407407408);
		autokovarianzVorgabe.add (-1.3472222222222223);
		autokovarianzVorgabe.add (0.3981481481481479);
		autokovarianzVorgabe.add (0.032407407407407274);
		
		
		prognosewerte = at.prognoseBerechnen(cashflows, matrixValuations, standardabweichung, zuberechnendeperioden, durchlaeufe, p, mittelwert, isfremdkapital)  ;
		
		logger.debug("prognosewerte[0][0] " + prognosewerte[0][0]);
		logger.debug("prognosewerte[0][1] " + prognosewerte[0][1]);
		logger.debug("prognosewerte[0][2] " + prognosewerte[0][2]);
		logger.debug("prognosewerte[0][3] " + prognosewerte[0][3]);
		logger.debug("prognosewerte[0][4] " + prognosewerte[0][4]);
		logger.debug("prognosewerte[1][0] " + prognosewerte[1][0]);
		logger.debug("prognosewerte[1][1] " + prognosewerte[1][1]);
		logger.debug("prognosewerte[1][2] " + prognosewerte[1][2]);
		logger.debug("prognosewerte[1][3] " + prognosewerte[1][3]);
		logger.debug("prognosewerte[1][4] " + prognosewerte[1][4]);
		
		assertNotNull(prognosewerte);
		
		}

	}
