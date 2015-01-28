package dhbw.ka.mwi.businesshorizon2.tests.methods.discountedCashflow;

import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.junit.Test;

import dhbw.ka.mwi.businesshorizon2.methods.discountedCashflow.WACC;
import dhbw.ka.mwi.businesshorizon2.models.Szenario;

/**
 * Diese Klasse stellt den jUnit-Test der im Klassenname aufgeführten Methode in der Klasse AnalysisTime dar.
 *
 * @author Tobias Blasy
 */
public class TestWACC extends TestCase{
	
	private static final Logger logger = Logger.getLogger("TestWacc.class");

	@Test
	public void testWacc() {
		double[] cashflow = new double[5];
		double[] fremdkapital = new double[5];
		double rateReturnEquity = 9.969137;  
		double rateReturnCapitalStock = 8.0;
		double businessTax = 28.0; 
		double corporateAndSolitaryTax = 5.325;
		boolean includeInCalculation = true;
		Szenario szenario = new Szenario(rateReturnEquity, rateReturnCapitalStock, businessTax, corporateAndSolitaryTax, includeInCalculation);
		double ergebnisVorgabe= 1055.25;
		double ergebnis;
		
		cashflow[0] = 0.0;
		cashflow[1] = 138.61;
		cashflow[2] = 202.31;
		cashflow[3] = 174.41;
		cashflow[4] = 202.52;
		
		fremdkapital[0] = 1260.0;
		fremdkapital[1] = 1320.0;
		fremdkapital[2] = 1330.0;
		fremdkapital[3] = 1400.0;
		fremdkapital[4] = 1400.0;
		
		WACC wacc = new WACC();
		ergebnis = wacc.calculateValues(cashflow, fremdkapital, szenario);
		ergebnis = ((double)Math.round(ergebnis * 100)) / 100;
		
		logger.debug(ergebnisVorgabe);
		logger.debug(ergebnis);
		
		assertEquals(ergebnisVorgabe,ergebnis);
	}
	
}