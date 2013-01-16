package dhbw.ka.mwi.businesshorizon2.methods.timeseries;

import dhbw.ka.mwi.businesshorizon2.methods.StochasticMethodException;

public class VarianceNegativeException extends StochasticMethodException {

	private static final long serialVersionUID = 1L;

	public VarianceNegativeException(String s) {
		super(s);
	}

}
