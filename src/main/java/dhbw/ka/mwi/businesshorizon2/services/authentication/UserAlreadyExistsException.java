package dhbw.ka.mwi.businesshorizon2.services.authentication;

import dhbw.ka.mwi.businesshorizon2.BusinessHorizonException;

public class UserAlreadyExistsException extends BusinessHorizonException {

	public UserAlreadyExistsException(String s) {
		super(s);
	}

}
