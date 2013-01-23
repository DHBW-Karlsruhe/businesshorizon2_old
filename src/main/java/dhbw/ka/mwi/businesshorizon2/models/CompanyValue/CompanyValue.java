package dhbw.ka.mwi.businesshorizon2.models.CompanyValue;

public abstract class CompanyValue {

	protected CompanyValue() {
	}

	/**
	 * Die Methode rundet einen double auf eine bestimmte Zahl an
	 * Nachkommastelle.
	 * 
	 * @param number
	 *            Zahl, die gerundet werden soll.
	 * @param decimalPlace
	 *            Stellen, auf die gerundet werden soll. 
	 * @param place Wenn place
	 *            auf true gesetzt wird, wird auf die Vorkommastellen gerundet,
	 *            wenn place auf false gesetzt wird, wird auf die
	 *            Nachkommastellen gerundet.
	 * @return number Gerundete Zahl
	 */
	protected double roundToDecimalPlaces(double number, int decimalPlace,
			boolean place) {

		int temp = 1;

		for (int i = 0; i < decimalPlace; i++) {
			temp = temp * 10;
		}

		if (place) {
			number = number / temp;
			number = Math.round(number);
			number = number * temp;
		} else {
			number = number * temp;
			number = Math.round(number);
			number = number / temp;
		}
		return number;
	}
}
