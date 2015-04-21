package dhbw.ka.mwi.businesshorizon2.tests.methods.timeseries;

import org.apache.log4j.Logger;
import org.junit.Test;

import dhbw.ka.mwi.businesshorizon2.methods.timeseries.LinearRegression;
import junit.framework.TestCase;

public class TestLinearRegression extends TestCase{
	
	private static final Logger logger = Logger.getLogger("LinearRegression.class");
	
	@Test
	public void TestgetLinearRegressionParameters(){
		double[] zeitreihe = new double[]{1,2,3,4,5};
		double[] timeseries = new double[]{30,35,39,46,50};
		double[] ergebnis;
		LinearRegression regression = new LinearRegression();
		
		ergebnis = regression.getLinearRegressionParameters(zeitreihe, timeseries);
		assertEquals(5.1, ergebnis[1]);
		assertEquals(24.7, ergebnis[0]);
	}

}
