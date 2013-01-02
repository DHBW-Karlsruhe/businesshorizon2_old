package dhbw.ka.mwi.businesshorizon2;

public class BusinessHorizonException extends Exception {

	public BusinessHorizonException(String s) {
		super("Business Horizon Application Error: " + s);
	}

}
