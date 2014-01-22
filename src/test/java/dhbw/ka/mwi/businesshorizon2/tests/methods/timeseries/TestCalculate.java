package dhbw.ka.mwi.businesshorizon2.tests.methods.timeseries;

import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.junit.Test;



import dhbw.ka.mwi.businesshorizon2.methods.CallbackInterface;
import dhbw.ka.mwi.businesshorizon2.methods.StochasticMethodException;
import dhbw.ka.mwi.businesshorizon2.methods.timeseries.AnalysisTimeseries;
import dhbw.ka.mwi.businesshorizon2.methods.CallbackInterface;


/**
 * Diese Klasse stellt den jUnit-Test der im Klassenname aufgeführten Methode in der Klasse AnalysisTime dar.
 * 
 * @author Volker Maier
 * 
 */

public class TestCalculate extends TestCase {
	
	private static final Logger logger = Logger.getLogger("AnalysisTimeseries.class");
	
		
	@Test
	public void testCalculate() {
		int p = 5;
		double[] zeitreihe = new double [6]; 
		int zuberechnendeperioden = 5; 
		int durchlaeufe = 10000;
		CallbackInterface callback = null; 
		boolean isfremdkapital = true;
		double[][] prognosewerte = new double[zuberechnendeperioden][durchlaeufe];
		
		zeitreihe [0]= 7;
		zeitreihe [1]= 9;
		zeitreihe [2]= 5;
		zeitreihe [3]= 14;
		zeitreihe [4]= 6;
		zeitreihe [5]= 8;
		
		
		AnalysisTimeseries at = new AnalysisTimeseries();
		
		try {
			prognosewerte = at.calculate (zeitreihe, p , zuberechnendeperioden, durchlaeufe, callback, isfremdkapital)  ;
		} catch (StochasticMethodException | InterruptedException e) {
			logger.debug(e.getMessage());
		}
				
		
		
		assertNotNull(prognosewerte);
		}

	}
