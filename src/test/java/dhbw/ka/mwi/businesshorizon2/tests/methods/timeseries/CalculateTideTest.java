package dhbw.ka.mwi.businesshorizon2.tests.methods.timeseries;

import junit.framework.TestCase;

import org.junit.Test;

import dhbw.ka.mwi.businesshorizon2.methods.timeseries.CalculateTide;

public class CalculateTideTest extends TestCase {
	@Test
	public void testReduceTide() {
		double[] timeseries = new double[6];
		timeseries[0] = 130594000.00;
		timeseries[1] = 147552000.00;
		timeseries[2] = 144040000.00;
		timeseries[3] = 146004000.00;
		timeseries[4] = 154857000.00;
		timeseries[5] = 162117000.00;
		CalculateTide tide = new CalculateTide();
		timeseries = tide.reduceTide(timeseries);
		double[] results = new double[timeseries.length];
		results[0] = 3969476.190476209;
		results[1] = -7802980.952380925;
		results[2] = 894561.9047619104;
		results[3] = 4116104.761904776;
		results[4] = 448647.61904764175;
		results[5] = -1625809.5238094926;

		System.out.println(timeseries[0]);
		System.out.println(results[0]);
		for (int i = 0; i < timeseries.length; i++) {
			assertEquals(timeseries[i], results[i]);
		}

	}
}
