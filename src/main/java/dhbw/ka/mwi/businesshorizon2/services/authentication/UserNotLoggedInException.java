package dhbw.ka.mwi.businesshorizon2.services.authentication;

import dhbw.ka.mwi.businesshorizon2.BusinessHorizonException;

/**
 * Exception, die geworfen wird, wenn ein User ausgeloggt werden soll, der gar
 * nicht eingeloggt ist
 * 
 * @author Florian Stier
 * 
 */
public class UserNotLoggedInException extends BusinessHorizonException {

	public UserNotLoggedInException(String s) {
		super(s);
	}

}
