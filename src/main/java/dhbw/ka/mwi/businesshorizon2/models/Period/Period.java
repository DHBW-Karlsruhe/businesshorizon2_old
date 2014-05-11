/*******************************************************************************
 * BusinessHorizon2
 *
 * Copyright (C) 
 * 2012-2013 Christian Gahlert, Florian Stier, Kai Westerholz,
 * Timo Belz, Daniel Dengler, Katharina Huber, Christian Scherer, Julius Hacker
 * 2013-2014 Marcel Rosenberger, Mirko Göpfrich, Annika Weis, Katharina Narlock, 
 * Volker Meier
 * 
 *
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/

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
	private boolean freeCashFlowSet;
	private double freeCashFlow;

	/**
	 * Fremdkapital
	 */
	private boolean capitalStockSet;
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
		freeCashFlowSet=true;
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
		capitalStockSet=true;
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
			// TODO Stacktrace sollte noch geloggt werden.
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * @return the freeCashFlowSet
	 */
	public boolean getFreeCashFlowSet() {
		return freeCashFlowSet;
	}

	/**
	 * @param freeCashFlowSet the freeCashFlowSet to set
	 */
	public void setFreeCashFlowSet( boolean freeCashFlowSet) {
		this.freeCashFlowSet = freeCashFlowSet;
	}

	/**
	 * @return the capitalStockSet
	 */
	public boolean getCapitalStockSet() {
		return capitalStockSet;
	}

	/**
	 * @param capitalStockSet the capitalStockSet to set
	 */
	public void setCapitalStockSet( boolean capitalStockSet) {
		this.capitalStockSet = capitalStockSet;
	}

}
