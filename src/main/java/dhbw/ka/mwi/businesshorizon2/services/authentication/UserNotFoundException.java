package dhbw.ka.mwi.businesshorizon2.services.authentication;

import dhbw.ka.mwi.businesshorizon2.BusinessHorizonException;

/**
 * Exception die geworfen wird, wenn ein User eingeloggt werden soll, der nicht
 * existiert.
 * 
 * @author Florian Stier
 * 
 */
public class UserNotFoundException extends BusinessHorizonException {

	private static final long serialVersionUID = 1L;

	public UserNotFoundException(String s) {
		super(s);
	}
}
