package dhbw.ka.mwi.businesshorizon2.models.CompanyValue;

public abstract class CompanyValue {

	protected CompanyValue() {
	}

	/**
	 * Die Methode rundet einen double auf vier Nachkommastellen.
	 * 
	 * @param number
	 * @return number auf vier Nachkommastellen gerundet.
	 */
	protected double roundTo4DecimalPlaces(double number) {

		number = number * 10000;
		number = Math.round(number);
		number = number / 10000;

		return number;
	}

}
