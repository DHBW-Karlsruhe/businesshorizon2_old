package dhbw.ka.mwi.businesshorizon2.tests.methods.timeseries;

import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.junit.Test;

import cern.colt.list.DoubleArrayList;
import cern.colt.matrix.DoubleFactory2D;
import cern.colt.matrix.DoubleMatrix2D;

import dhbw.ka.mwi.businesshorizon2.methods.StochasticMethodException;
import dhbw.ka.mwi.businesshorizon2.methods.timeseries.AnalysisTimeseries;


/**
 * Diese Klasse stellt den jUnit-Test der im Klassenname aufgeführten Methode in der Klasse AnalysisTime dar.
 * 
 * @author Volker Maier
 * 
 */

public class TestModellparameter extends TestCase {
	
	private static final Logger logger = Logger.getLogger("AnalysisTimeseries.class");
	
		
	@Test
	public void testModellparameter() {
		int p = 5;
		int i = 1;
		DoubleMatrix2D matrixValuations = DoubleFactory2D.dense.make(p, i);
		DoubleMatrix2D matrixErgebnis = DoubleFactory2D.dense.make(p, i);
		
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
		
		try {
			matrixErgebnis = at.berechneModellparameter(autokovarianzVorgabe,p)  ;
		} catch (StochasticMethodException e) {
			logger.debug(e.getMessage());
		}
		
		logger.debug(matrixValuations);
		logger.debug(matrixErgebnis);
		
			assertEquals(matrixValuations, matrixErgebnis);
		}

	}
