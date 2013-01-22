package dhbw.ka.mwi.businesshorizon2.models.Period;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public abstract class Period implements Comparable<Period>, Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Jahr
	 */
	private final int year;

	/**
	 * Free Cashflow
	 */
	private double freeCashFlow;

	/**
	 * Fremdkapital
	 */
	private double capitalStock;

	/**
	 * Konstruktor der Klasse Period.
	 * 
	 * @param year
	 *            Jahr
	 */
	public Period(int year) {
		this.year = year;
	}

	/**
	 * Setze den 'Free Cashflow'.
	 * 
	 * @param freeCashFlow
	 *            Free Cashflow
	 */
	public void setFreeCashFlow(double freeCashFlow) {
		this.freeCashFlow = freeCashFlow;
	}

	/**
	 * Gebe den 'Free Cashflow' zurück.
	 * 
	 * @return Free Cashflow
	 */
	public double getFreeCashFlow() {
		return freeCashFlow;
	}

	/**
	 * Gebe das 'Fremdkapital' zurück.
	 * 
	 * @return Fremdkapital
	 */
	public double getCapitalStock() {
		return capitalStock;
	}

	/**
	 * Setze das 'Fremdkapital'.
	 * 
	 * @param capitalStock
	 *            Fremdkapital
	 */
	public void setCapitalStock(double capitalStock) {
		this.capitalStock = capitalStock;
	}

	/**
	 * Gebe das 'Jahr' zurück.
	 * 
	 * @return Jahr
	 */
	public int getYear() {
		return year;
	}

	@Override
	public int compareTo(Period o) {
		if (this.getYear() < o.getYear()) {
			return -1;
		} else if (this.getYear() == o.getYear()) {
			return 0;
		} else {
			return 1;
		}
	}

	/**
	 * Erstelle eine tiefe Objektkopie eines Objekts der Klasse 'Period' oder
	 * einer davon abgeleiteten Klasse.
	 * 
	 * @return Objektkopie
	 */
	public Period deepCopy() {

		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			new ObjectOutputStream(baos).writeObject(this);

			ByteArrayInputStream bais = new ByteArrayInputStream(
					baos.toByteArray());

			Period p = (Period) new ObjectInputStream(bais).readObject();

			baos.close();
			bais.close();

			return p;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
