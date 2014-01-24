package dhbw.ka.mwi.businesshorizon2.tests.models;

import java.util.TreeSet;

import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.junit.Test;

import dhbw.ka.mwi.businesshorizon2.models.DeterministicResultContainer;
import dhbw.ka.mwi.businesshorizon2.models.Szenario;
import dhbw.ka.mwi.businesshorizon2.models.Period.CashFlowCalculator;
import dhbw.ka.mwi.businesshorizon2.models.Period.DirectCalculatedCashflowPeriod;
import dhbw.ka.mwi.businesshorizon2.models.Period.IndirectCalculatedCashflowPeriod;
import dhbw.ka.mwi.businesshorizon2.models.PeriodContainer.AbstractPeriodContainer;
import dhbw.ka.mwi.businesshorizon2.models.PeriodContainer.DirectCalculatedCashflowPeriodContainer;
import dhbw.ka.mwi.businesshorizon2.models.PeriodContainer.IndirectCalculatedCashflowPeriodContainer;

/**
 * Diese Klasse stellt den jUnit-Test der im Klassenname aufgeführten Methode in der Klasse CashFlowCalculator dar.
 * 
 * @author Volker Maier, Marcel Rosenberger
 * 
 * ACHTUNG: TEST SCHLÄGT BIS JETZT FEHL; DESWEGEN ERGEBNIS-VORGABE AUSKOMMENTIERT
 * 
 */

public class TestCashFlowCalculatorIndirectCalcDet extends TestCase{
	
	private static final Logger logger = Logger.getLogger("TestCashFlowCalculatorIndirectCalcDet.class");
	
	
	@Test
	public void testCashFlowCalculatorDirectCalcDet() {
		
		//Eingangswerte bereitstellen
		IndirectCalculatedCashflowPeriod idccfp = new IndirectCalculatedCashflowPeriod(2013);
		
		idccfp.setEbit(288.59);
		idccfp.setZinsaufwand(112.0);
		idccfp.setNichtZahlungswirksameAufwendungen(280.0);
		idccfp.setNichtZahlungswirksameErtraege(0.0);
		idccfp.setJahresÜberschuss(120.0);
		
		
		TreeSet<AbstractPeriodContainer> periodContainer = new TreeSet<AbstractPeriodContainer>();
		AbstractPeriodContainer apc = new IndirectCalculatedCashflowPeriodContainer();
		apc.addPeriod(idccfp);
		periodContainer.add(apc);				
		DeterministicResultContainer drc =  new DeterministicResultContainer(periodContainer);
		
		double rateReturnEquity = 10.0;  
		double rateReturnCapitalStock = 0.0;
		double businessTax = 14.0; 
		double corporateAndSolitaryTax = 15.825; 
		boolean includeInCalculation = true;
		Szenario szenario = new Szenario( rateReturnEquity,  rateReturnCapitalStock,
				 businessTax,  corporateAndSolitaryTax,  includeInCalculation);
		
		//Erwarteter Ausgabe CashFlow
		double ergebnisVorgabe = 455.4120325;
		
		CashFlowCalculator.calculateCashflows(drc, szenario);
		double ergebnis = drc.getPeriodContainers().first().getPeriods().first().getFreeCashFlow();
		
				
		logger.debug(ergebnisVorgabe);
		logger.debug(ergebnis);
		
			assertEquals(ergebnisVorgabe,ergebnis);
		}

}
