package dhbw.ka.mwi.businesshorizon2.services.authentication;

import dhbw.ka.mwi.businesshorizon2.BusinessHorizonException;

/**
 * Exception, die geworfen wird, wenn ein falsches Passwort eingegeben wurde.
 * @author Florian Stier
 *
 */

public class WrongPasswordException extends BusinessHorizonException {

	public WrongPasswordException(String s) {
		super(s);
	}

}
