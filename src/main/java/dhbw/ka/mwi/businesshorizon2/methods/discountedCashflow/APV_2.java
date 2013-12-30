/**
 * 
 */
package dhbw.ka.mwi.businesshorizon2.methods.discountedCashflow;

import java.util.TreeSet;

import dhbw.ka.mwi.businesshorizon2.methods.AbstractDeterministicMethod;
import dhbw.ka.mwi.businesshorizon2.methods.CallbackInterface;
import dhbw.ka.mwi.businesshorizon2.methods.DeterministicMethodException;
import dhbw.ka.mwi.businesshorizon2.models.DeterministicResultContainer;
import dhbw.ka.mwi.businesshorizon2.models.Project;
import dhbw.ka.mwi.businesshorizon2.models.PeriodContainer.CashFlowPeriodContainer;

/**
 * @author Annika Weis
 * @date 29.12.2013
 *
 */
public class APV_2 extends AbstractDeterministicMethod {

	
	@Override
	public String getName() {

		return "APV";
	}

	@Override
	public int getOrderKey() {

		return 5;
	}


	@Override
	public Boolean getImplemented() {
		// TODO Auto-generated method stub
		return true;
	}



	@Override
	public DeterministicResultContainer calculate(Project project,
			CallbackInterface callback) throws InterruptedException,
			DeterministicMethodException {
		// TODO Auto-generated method stub
		TreeSet<CashFlowPeriodContainer> prognose = new TreeSet<CashFlowPeriodContainer>();
		
		
		DeterministicResultContainer drc = new DeterministicResultContainer(prognose);
		return drc;
	}
}
