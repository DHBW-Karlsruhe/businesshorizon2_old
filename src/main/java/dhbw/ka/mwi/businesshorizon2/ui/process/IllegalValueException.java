package dhbw.ka.mwi.businesshorizon2.ui.process;

import dhbw.ka.mwi.businesshorizon2.BusinessHorizonException;

public class IllegalValueException extends BusinessHorizonException {
	private static final long serialVersionUID = 1L;

	public IllegalValueException(String s) {
		super(s);
	}

}
