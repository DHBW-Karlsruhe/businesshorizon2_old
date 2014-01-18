/*******************************************************************************
 * BusinessHorizon2
 * 
 *     Copyright (C) 2012-2013  Christian Gahlert, Florian Stier, Kai Westerholz,
 *     Timo Belz, Daniel Dengler, Katharina Huber, Christian Scherer, Julius Hacker
 * 
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Affero General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 * 
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Affero General Public License for more details.
 * 
 *     You should have received a copy of the GNU Affero General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/

package dhbw.ka.mwi.businesshorizon2.models.Period;

/**
 * Diese Klasse bildet eine Periode der indirekten Cashflow Ermittlung ab.
 * 
 * @author Marcel Rosenberger
 * 
 */

public class DirectCalculatedCashflowPeriod extends Period {

	private static final long serialVersionUID = 1L;

	/**
	 * Umsatzerlöse
	 */
	private double umsatzErlöse;
	private boolean umsatzErlöseSet;

	/**
	 * Umsatzkosten
	 */
	private double umsatzKosten;
	private boolean umsatzKostenSet;

	/**
	 * * Steuern bei reiner Eigenfinanzierung
	 */
	private double steuernBeiReinerEigenfinanzierung;
	private boolean steuernBeiReinerEigenfinanzierungSet;

	/**
	 * * Saldo aus Auszahlungen für Investitionen und Einzahlungen aus
	 * Desinves-titionen
	 */
	private double saldoAusAuszahlungen;
	private boolean saldoAusAuszahlungenSet;

	/**
	 * Der Konstruktor erstellt eine Methode für das Jahr year
	 * 
	 * @param year
	 *            Jahr der Periode
	 * @author Marcel Rosenberger
	 * 
	 */

	public DirectCalculatedCashflowPeriod(int year) {
		super(year);
	}

	/**
	 * @return the umsatzErlöse
	 */
	public double getUmsatzErlöse() {
		return umsatzErlöse;
	}

	/**
	 * @param umsatzErlöse the umsatzErlöse to set
	 */
	public void setUmsatzErlöse(double umsatzErlöse) {
		this.umsatzErlöse = umsatzErlöse;
		umsatzErlöseSet = true;
	}

	/**
	 * @return the umsatzErlöseSet
	 */
	public boolean isUmsatzErlöseSet() {
		return umsatzErlöseSet;
	}

	/**
	 * @param umsatzErlöseSet the umsatzErlöseSet to set
	 */
	public void setUmsatzErlöseSet(boolean umsatzErlöseSet) {
		this.umsatzErlöseSet = umsatzErlöseSet;
	}

	/**
	 * @return the umsatzKosten
	 */
	public double getUmsatzKosten() {
		return umsatzKosten;
	}

	/**
	 * @param umsatzKosten the umsatzKosten to set
	 */
	public void setUmsatzKosten(double umsatzKosten) {
		this.umsatzKosten = umsatzKosten;
		umsatzKostenSet = true;
	}

	/**
	 * @return the umsatzKostenSet
	 */
	public boolean isUmsatzKostenSet() {
		return umsatzKostenSet;
	}

	/**
	 * @param umsatzKostenSet the umsatzKostenSet to set
	 */
	public void setUmsatzKostenSet(boolean umsatzKostenSet) {
		this.umsatzKostenSet = umsatzKostenSet;
	}

	/**
	 * @return the steuernBeiReinerEigenfinanzierung
	 */
	public double getSteuernBeiReinerEigenfinanzierung() {
		return steuernBeiReinerEigenfinanzierung;
	}

	/**
	 * @param steuernBeiReinerEigenfinanzierung the steuernBeiReinerEigenfinanzierung to set
	 */
	public void setSteuernBeiReinerEigenfinanzierung(
			double steuernBeiReinerEigenfinanzierung) {
		this.steuernBeiReinerEigenfinanzierung = steuernBeiReinerEigenfinanzierung;
		steuernBeiReinerEigenfinanzierungSet = true;
	}

	/**
	 * @return the steuernBeiReinerEigenfinanzierungSet
	 */
	public boolean isSteuernBeiReinerEigenfinanzierungSet() {
		return steuernBeiReinerEigenfinanzierungSet;
	}

	/**
	 * @param steuernBeiReinerEigenfinanzierungSet the steuernBeiReinerEigenfinanzierungSet to set
	 */
	public void setSteuernBeiReinerEigenfinanzierungSet(
			boolean steuernBeiReinerEigenfinanzierungSet) {
		this.steuernBeiReinerEigenfinanzierungSet = steuernBeiReinerEigenfinanzierungSet;
	}

	/**
	 * @return the saldoAusAuszahlungen
	 */
	public double getSaldoAusAuszahlungen() {
		return saldoAusAuszahlungen;
	}

	/**
	 * @param saldoAusAuszahlungen the saldoAusAuszahlungen to set
	 */
	public void setSaldoAusAuszahlungen(double saldoAusAuszahlungen) {
		this.saldoAusAuszahlungen = saldoAusAuszahlungen;
		saldoAusAuszahlungenSet = true;
	}

	/**
	 * @return the saldoAusAuszahlungenSet
	 */
	public boolean isSaldoAusAuszahlungenSet() {
		return saldoAusAuszahlungenSet;
	}

	/**
	 * @param saldoAusAuszahlungenSet the saldoAusAuszahlungenSet to set
	 */
	public void setSaldoAusAuszahlungenSet(boolean saldoAusAuszahlungenSet) {
		this.saldoAusAuszahlungenSet = saldoAusAuszahlungenSet;
	}

}