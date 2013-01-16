package dhbw.ka.mwi.businesshorizon2.methods.timeseries;

import dhbw.ka.mwi.businesshorizon2.methods.StochasticMethodException;

/**
 * Diese Exception bildet den Fall ab, dass zu viele Perioden in der
 * Vergangenheit betrachtet werden sollen.
 * 
 * @author Kai Westerholz
 * 
 */

public class ConsideredPeriodsOfPastException extends StochasticMethodException {

	private static final long serialVersionUID = 1L;

	public ConsideredPeriodsOfPastException(String s) {
		super(s);
	}

}
