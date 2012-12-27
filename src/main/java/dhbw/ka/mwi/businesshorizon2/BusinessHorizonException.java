package dhbw.ka.mwi.businesshorizon2;

/**
 * Exception Klasse, von der alle anderen selbst angelegten Exceptionklassen
 * erben.
 * 
 * @author Florian Stier
 * 
 */

public class BusinessHorizonException extends Exception {

	private static final long serialVersionUID = 1L;

	public BusinessHorizonException(String s) {
		super("Business Horizon Applicaton Error: " + s);
	}

}
