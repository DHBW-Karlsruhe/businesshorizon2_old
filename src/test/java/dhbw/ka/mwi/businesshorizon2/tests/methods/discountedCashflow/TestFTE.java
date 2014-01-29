package dhbw.ka.mwi.businesshorizon2.tests.methods.discountedCashflow;



import junit.framework.TestCase;
import org.apache.log4j.Logger;
import org.junit.Test;
import dhbw.ka.mwi.businesshorizon2.methods.discountedCashflow.FTE;
import dhbw.ka.mwi.businesshorizon2.models.Szenario;

/**
* Diese Klasse stellt den jUnit-Test der im Klassenname aufgeführten Methode dar.
*
* @author Volker Maier
*
*/


public class TestFTE extends TestCase {
        private static final Logger logger = Logger.getLogger("TestFTE.class");
        
        
        @Test
        public void testFTE() {
                double[] cashflow = new double [5];
                double rateReturnEquity = 10.0;
                double rateReturnCapitalStock = 0.0;
                double businessTax = 0.0;
                double corporateAndSolitaryTax = 0.0;
                boolean includeInCalculation = true;
                Szenario szenario = new Szenario( rateReturnEquity, rateReturnCapitalStock,
                                 businessTax, corporateAndSolitaryTax, includeInCalculation);
                double ergebnisVorgabe = 10649.55877330783;
                double ergebnis;
                
                
                cashflow [0]= 125.0;
                cashflow [1]= 847.5;
                cashflow [2]= 957.89;
                cashflow [3]= 1083.69;
                cashflow [4]= 1226.28;
                
                
                FTE ft = new FTE();
        
                ergebnis = ft.calculateValues(cashflow , szenario);
                
                
                                
                logger.debug(ergebnisVorgabe);
                logger.debug(ergebnis);
                
                assertEquals(ergebnisVorgabe, ergebnis);
        
        }
}