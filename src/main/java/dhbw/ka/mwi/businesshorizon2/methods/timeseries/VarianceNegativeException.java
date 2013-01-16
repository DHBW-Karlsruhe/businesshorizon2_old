package dhbw.ka.mwi.businesshorizon2.methods.timeseries;

import dhbw.ka.mwi.businesshorizon2.methods.StochasticMethodException;

/**
 * Diese Exception bildet den Fall ab, dass die Berechnung der Varianz
 * fehlgeschlagen ist. Diese kann nicht negativ sein, da mit der Wurzel aus
 * dieser Varianz weiter gerechnet wird (WhiteNoise).
 * 
 * @author Kai Westerholz
 * 
 */

public class VarianceNegativeException extends StochasticMethodException {

	private static final long serialVersionUID = 1L;

	public VarianceNegativeException(String s) {
		super(s);
	}

}
