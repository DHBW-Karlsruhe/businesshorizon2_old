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
