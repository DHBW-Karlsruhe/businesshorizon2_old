package dhbw.ka.mwi.businesshorizon2.tests.models;

import java.util.TreeSet;

import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.junit.Test;

import dhbw.ka.mwi.businesshorizon2.models.StochasticResultContainer;
import dhbw.ka.mwi.businesshorizon2.models.Szenario;
import dhbw.ka.mwi.businesshorizon2.models.Period.CashFlowCalculator;
import dhbw.ka.mwi.businesshorizon2.models.Period.DirectCalculatedCashflowPeriod;
import dhbw.ka.mwi.businesshorizon2.models.PeriodContainer.AbstractPeriodContainer;
import dhbw.ka.mwi.businesshorizon2.models.PeriodContainer.DirectCalculatedCashflowPeriodContainer;

/**
 * Diese Klasse stellt den jUnit-Test der im Klassenname aufgeführten Methode in der Klasse CashFlowCalculator dar.
 * 
 * @author Volker Maier, Marcel Rosenberger
 * 
 */

public class TestCashFlowCalculatorDirectCalcStoc extends TestCase{
	
	private static final Logger logger = Logger.getLogger("TestCashFlowCalculatorDirectCalcStoc.class");
	
	
	@Test
	public void testCashFlowCalculatorDirectCalcStoc() {
		
		//Eingangswerte bereitstellen
		DirectCalculatedCashflowPeriod dccfp = new DirectCalculatedCashflowPeriod(2013);
		dccfp.setDesinvestitionen(0.0);
		dccfp.setEbit(288.59);
		dccfp.setInvestitionen(280.0);
		dccfp.setUmsatzErlöse(2000.0);
		dccfp.setUmsatzKosten(1431.41);
		
		TreeSet<AbstractPeriodContainer> periodContainer = new TreeSet<AbstractPeriodContainer>();
		AbstractPeriodContainer apc = new DirectCalculatedCashflowPeriodContainer();
		apc.addPeriod(dccfp);
		periodContainer.add(apc);				
		StochasticResultContainer src =  new StochasticResultContainer(periodContainer);
		
		double rateReturnEquity = 10.0;  
		double rateReturnCapitalStock = 0.0;
		double businessTax = 14.0; 
		double corporateAndSolitaryTax = 15.825; 
		boolean includeInCalculation = true;
		Szenario szenario = new Szenario( rateReturnEquity,  rateReturnCapitalStock,
				 businessTax,  corporateAndSolitaryTax,  includeInCalculation);
		
		//Erwarteter Ausgabe CashFlow
		double ergebnisVorgabe = 202.5180324999999;
		
		CashFlowCalculator.calculateCashflows(src, szenario);
		double ergebnis = src.getPeriodContainers().first().getPeriods().first().getFreeCashFlow();
		
				
		logger.debug(ergebnisVorgabe);
		logger.debug(ergebnis);
		
			assertEquals(ergebnisVorgabe,ergebnis);
		}

}
