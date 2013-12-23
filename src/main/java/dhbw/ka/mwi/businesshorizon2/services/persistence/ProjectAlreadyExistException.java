package dhbw.ka.mwi.businesshorizon2.services.persistence;

import dhbw.ka.mwi.businesshorizon2.BusinessHorizonException;

public class ProjectAlreadyExistException extends BusinessHorizonException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2811188894734446303L;

	public ProjectAlreadyExistException(String s) {
		super(s);
	}
}
