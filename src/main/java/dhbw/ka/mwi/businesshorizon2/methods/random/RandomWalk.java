package dhbw.ka.mwi.businesshorizon2.methods.random;

import dhbw.ka.mwi.businesshorizon2.methods.AbstractStochasticMethod;
import dhbw.ka.mwi.businesshorizon2.methods.CallbackInterface;
import dhbw.ka.mwi.businesshorizon2.methods.StochasticMethodException;
import dhbw.ka.mwi.businesshorizon2.models.Project;
import dhbw.ka.mwi.businesshorizon2.models.StochasticResultContainer;


public class RandomWalk extends AbstractStochasticMethod {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String getName() {
		
		return "Random Walk";
	}

	@Override
	public int getOrderKey() {

		return 3;
	}

	@Override
	public Boolean getImplemented() {
		return false;
	}

	@Override
	public StochasticResultContainer calculate(Project project,
			CallbackInterface callback) throws InterruptedException,
			StochasticMethodException {
		// TODO Auto-generated method stub
		return null;
	}

}
