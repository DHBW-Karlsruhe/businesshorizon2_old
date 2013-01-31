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
 * Diese Klasse bildet eine Periode nach dem Umsatzkosten Verfahren ab.
 * 
 * @author Kai Westerholz
 * 
 */

public class CostOfSalesMethodPeriod extends BalanceSheetPeriod {


	private static final long serialVersionUID = 1L;

	/**
	 * Umsatzerlöse
	 */
	private double salesRevenue;

	/**
	 * Sonstige betriebliche Erträge
	 */
	private double otherBusinessRevenue;

	/**
	 * Herstellkosten
	 */
	private double costOfPoduction;

	/**
	 * Vertriebskosten, Allgemeine Verwaltungskosten und Sonstige betriebliche
	 * Aufwendungen
	 */
	private double costOfSalesAdministrationOthers;

	public CostOfSalesMethodPeriod(int year) {
		super(year);
	}

	/**
	 * Gebe die 'Umsatzerlöse' aus der Gewinn- und Verlustrechnung nach dem
	 * Umsatzkostenverfahren zurück.
	 * 
	 * @return Umsatzerlöse
	 */
	public double getSalesRevenue() {
		return salesRevenue;
	}

	/**
	 * Setze die 'Umsatzerlöse' in der Gewinn- und Verlustrechnung nach dem
	 * Umsatzkostenverfahren.
	 * 
	 * @param salesRevenue
	 *            Umsatzerlöse
	 */
	public void setSalesRevenue(double salesRevenue) {
		this.salesRevenue = salesRevenue;
	}

	/**
	 * Gebe die 'Sonstigen betrieblichen Erträge' aus der Gewinn- und
	 * Verlustrechnung nach dem Umsatzkostenverfahren zurück.
	 * 
	 * @return Sonstige betriebliche Erträge
	 */
	public double getOtherBusinessRevenue() {
		return otherBusinessRevenue;
	}

	/**
	 * Setze die 'Sonstigen betrieblichen Erträge' in der Gewinn- und
	 * Verlustrechnung nach dem Umsatzkostenverfahren.
	 * 
	 * @param otherBusinessRevenue
	 *            Sonstige betriebliche Erträge
	 */
	public void setOtherBusinessRevenue(double otherBusinessRevenue) {
		this.otherBusinessRevenue = otherBusinessRevenue;
	}

	/**
	 * Gebe die 'Herstellkosten' aus der Gewinn- und Verlustrechnung nach dem
	 * Umsatzkostenverfahren zurück.
	 * 
	 * @return Herstellkosten
	 */
	public double getCostOfPoduction() {
		return costOfPoduction;
	}

	/**
	 * Setze die 'Herstellkosten' in der Gewinn- und Verlustrechnung nach dem
	 * Umsatzkostenverfahren.
	 * 
	 * @param costOfPoduction
	 *            Herstellkosten
	 */
	public void setCostOfPoduction(double costOfPoduction) {
		this.costOfPoduction = costOfPoduction;
	}

	/**
	 * Gebe die 'Vertriebskosten, Allgemeine Verwaltungskosten und Sonstige
	 * betriebliche Aufwendungen ' aus der Gewinn- und Verlustrechnung nach dem
	 * Umsatzkostenverfahren zurück.
	 * 
	 * @return Vertriebskosten, Allgemeine Verwaltungskosten und Sonstige
	 *         betriebliche Aufwendungen
	 */
	public double getCostOfSalesAdministrationOthers() {
		return costOfSalesAdministrationOthers;
	}


	/**
	 * Setze die 'Vertriebskosten, Allgemeine Verwaltungskosten und Sonstige
	 * betriebliche Aufwendungen' in der Gewinn- und Verlustrechnung nach dem
	 * Umsatzkostenverfahren.
	 * 
	 * @param costOfSalesAdministrationOthers
	 *            Vertriebskosten, Allgemeine Verwaltungskosten und Sonstige
	 *            betriebliche Aufwendungen
	 */
	public void setCostOfSalesAdministrationOthers(
			double costOfSalesAdministrationOthers) {
		this.costOfSalesAdministrationOthers = costOfSalesAdministrationOthers;
	}

}
