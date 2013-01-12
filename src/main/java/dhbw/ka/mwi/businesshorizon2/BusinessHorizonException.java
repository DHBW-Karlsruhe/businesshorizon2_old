package dhbw.ka.mwi.businesshorizon2;

/**
 * Diese Klasse stellt die zentrale Exception Klasse der Anwendung dar. Alle
 * selbst erstellten Exceptions sollen von dieser Klasse erben. Den Messages
 * wird jeweils 'Business Horizon Application Error' voran gestellt.
 * 
 * @author Florian Stier
 * 
 */
public class BusinessHorizonException extends Exception {
	private static final long serialVersionUID = 1L;

	public BusinessHorizonException(String s) {
		super("Business Horizon Application Error: " + s);
	}

}
