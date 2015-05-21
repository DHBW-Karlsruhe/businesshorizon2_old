package dhbw.ka.mwi.businesshorizon2.tests.methods.timeseries;

import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.junit.Test;

import dhbw.ka.mwi.businesshorizon2.methods.timeseries.LinearRegression;
/**
 * Test für die Berechnung der Modellparamter für die Zeitreihenanalyse
 * @author Felix Schlosser
 *
 */
public class TestLinearRegression extends TestCase{
	
	private static final Logger logger = Logger.getLogger("LinearRegression.class");
	
	@Test
	public void testgetLinearRegressionParameters(){
		
		//Erzeugen der Testdaten
		double[] zeitreihe = new double[]{1,2,3,4,5};
		double[] timeseries = new double[]{30,35,39,46,50};
		double[] ergebnis;
		LinearRegression regression = new LinearRegression();
		
		ergebnis = regression.getLinearRegressionParameters(zeitreihe, timeseries);
		logger.debug("ParameterA: " + ergebnis[0]);
		logger.debug("ParameterB: " + ergebnis[1]);
		
		
		assertEquals(5.1, ergebnis[1]);
		assertEquals(24.700000000000003, ergebnis[0]);
	}

}
