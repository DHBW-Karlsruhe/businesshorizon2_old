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
 * Diese Klasse bildet eine Periode ab. Die Inhalte sind nach dem
 * Gesamtkostenverfahren bewertet und enthalten nur diese Werte. Das Jahr der
 * Periode kann nicht mehr verändert werden.
 * 
 * @author Kai Westerholz
 * 
 */

public class AggregateCostMethodPeriod extends BalanceSheetPeriod {


	private static final long serialVersionUID = 1L;

	/**
	 * Umsatzerlöse
	 */
	private double salesRevenue;
	 boolean salesRevenueSet;

	/**
	 * Sonstige betriebliche Erträge
	 */
	private double otherBusinessRevenue;
	boolean otherBusinessRevenueSet;

	/**
	 * Andere aktivierte Eigenleistungen
	 */
	private double internallyProducedAndCapitalizedAssets;
	boolean internallyProducedAndCapitalizedAssetsSet;

	/**
	 * Materialaufwand
	 */
	private double materialCosts;
	boolean materialCostsSet;

	/**
	 * Personalaufwand
	 */
	private double humanCapitalCosts;
	boolean humanCapitalCostsSet;

	/**
	 * Abschreibungen
	 */
	private double writeDowns;
	boolean writeDownsSet;

	/**
	 * Sonstige betriebliche Aufwendungen
	 */
	private double otherBusinessCosts;
	boolean otherBusinessCostsSet;

	/**
	 * Zinsen und andere Aufwendungen
	 */
	private double interestAndOtherCosts;
	boolean interestAndOtherCostsSet;

	/**
	 * Der Konstruktor erstellt eine Methode für das Jahr year
	 * 
	 * @param year
	 *            Jahr der Periode
	 * @author Kai Westerholz
	 * 
	 */

	public AggregateCostMethodPeriod(int year) {
		super(year);
	}

	/**
	 * Gebe die 'Umsatzerlöse' aus der Gewinn- und Verlustrechnung nach dem
	 * Gesamtkostenverfahren zurück.
	 * 
	 * @return Umsatzerlöse
	 */
	public double getSalesRevenue() {
		return salesRevenue;
	}

	/**
	 * Setze die 'Umsatzerlöse' in der Gewinn- und Verlustrechnung nach dem
	 * Gesamtkostenverfahren.
	 * 
	 * @param salesRevenue
	 *            Umsatzerlöse
	 */
	public void setSalesRevenue(double salesRevenue) {
		this.salesRevenue = salesRevenue;
		salesRevenueSet=true;
	}

	/**
	 * Gebe die 'Sonstigen betrieblichen Erträge' aus der Gewinn- und
	 * Verlustechnung nach dem Gesamtkostenverfahren zurück.
	 * 
	 * @return Sonstige betriebliche Erträge
	 */
	public double getOtherBusinessRevenue() {
		return otherBusinessRevenue;
	}

	/**
	 * Setze die 'Sonstigen betrieblichen Erträge' in der Gewinn- und
	 * Verlustrechnung nach dem Gesamtkostenverfahren.
	 * 
	 * @param otherBusinessRevenue
	 *            Sonstige betriebliche Erträge
	 */
	public void setOtherBusinessRevenue(double otherBusinessRevenue) {
		this.otherBusinessRevenue = otherBusinessRevenue;
		otherBusinessRevenueSet=true;
	}

	/**
	 * Gebe die 'Anderen aktivierten Eigenleistungen' aus der Gewinn- und
	 * Verlustechnung nach dem Gesamtkostenverfahren zurück.
	 * 
	 * @return Andere aktivierte Eigenleistungen
	 */
	public double getInternallyProducedAndCapitalizedAssets() {
		return internallyProducedAndCapitalizedAssets;
	}


	/**
	 * Setze die 'Anderen aktivierten Eigenleistungen' in der Gewinn- und
	 * Verlustrechnung nach dem Gesamtkostenverfahren.
	 * 
	 * @param internallyProducedAndCapitalizedAssets
	 *            Andere aktivierte Eigenleistungen
	 */
	public void setInternallyProducedAndCapitalizedAssets(
			double internallyProducedAndCapitalizedAssets) {
		this.internallyProducedAndCapitalizedAssets = internallyProducedAndCapitalizedAssets;
		internallyProducedAndCapitalizedAssetsSet=true;
	}

	/**
	 * Gebe den 'Materialaufwand' aus der Gewinn- und Verlustechnung nach dem
	 * Gesamtkostenverfahren zurück.
	 * 
	 * @return Materialaufwand
	 */
	public double getMaterialCosts() {
		return materialCosts;
	}

	/**
	 * Setze den 'Materialauswand' in der Gewinn- und Verlustrechnung nach dem
	 * Gesamtkostenverfahren.
	 * 
	 * @param materialCosts
	 *            Materialaufwand
	 */
	public void setMaterialCosts(double materialCosts) {
		this.materialCosts = materialCosts;
		materialCostsSet = true;
	}

	/**
	 * Gebe den 'Personalaufwand' aus der Gewinn- und Verlustechnung nach dem
	 * Gesamtkostenverfahren zurück.
	 * 
	 * @return Personalaufwand
	 */
	public double getHumanCapitalCosts() {
		return humanCapitalCosts;
	}

	/**
	 * Setze den 'Personalaufwand' in der Gewinn- und Verlustrechnung nach dem
	 * Gesamtkostenverfahren.
	 * 
	 * @param humanCapitalCosts
	 *            Personalaufwand
	 */
	public void setHumanCapitalCosts(double humanCapitalCosts) {
		this.humanCapitalCosts = humanCapitalCosts;
		humanCapitalCostsSet=true;
	}

	/**
	 * Gebe die 'Abschreibungen' aus der Gewinn- und Verlustechnung nach dem
	 * Gesamtkostenverfahren zurück.
	 * 
	 * @return Abschreibungen
	 */
	public double getWriteDowns() {
		return writeDowns;
	}

	/**
	 * Setze die 'Abschreibungen' in der Gewinn- und Verlustrechnung nach dem
	 * Gesamtkostenverfahren.
	 * 
	 * @param writeDowns
	 *            Abschreibungen
	 */
	public void setWriteDowns(double writeDowns) {
		this.writeDowns = writeDowns;
		writeDownsSet=true;
	}

	/**
	 * Gebe die 'Sonstigen betrieblichen Aufwendungen' aus der Gewinn- und
	 * Verlustechnung nach dem Gesamtkostenverfahren zurück.
	 * 
	 * @return Sonstige betriebliche Aufwendungen
	 */
	public double getOtherBusinessCosts() {
		return otherBusinessCosts;
	}

	/**
	 * Setze die 'Sonstigen betrieblichen Aufwendungen' in der Gewinn- und
	 * Verlustrechnung nach dem Gesamtkostenverfahren.
	 * 
	 * @param otherBusinessCosts
	 *            Sonstige betriebliche Aufwendungen
	 */
	public void setOtherBusinessCosts(double otherBusinessCosts) {
		this.otherBusinessCosts = otherBusinessCosts;
		otherBusinessCostsSet=true;
	}

	/**
	 * Gebe die 'Zinsen und ähnliche Aufwendungen' aus der Gewinn- und
	 * Verlustechnung nach dem Gesamtkostenverfahren zurück.
	 * 
	 * @return Zinsen und ähnliche Aufwendungen
	 */
	public double getInterestAndOtherCosts() {
		return interestAndOtherCosts;
	}

	/**
	 * Setze die 'Zinsen und ähnliche Aufwendungen' in der Gewinn- und
	 * Verlustrechnung nach dem Gesamtkostenverfahren.
	 * 
	 * @param interestAndOtherCosts
	 *            Zinsen und ähnliche Aufwendungen
	 */
	public void setInterestAndOtherCosts(double interestAndOtherCosts) {
		this.interestAndOtherCosts = interestAndOtherCosts;
		interestAndOtherCostsSet=true;
	}

	/**
	 * @return the salesRevenueSet
	 */
	public boolean getSalesRevenueSet() {
		return salesRevenueSet;
	}

	/**
	 * @param salesRevenueSet the salesRevenueSet to set
	 */
	public void setSalesRevenueSet(boolean salesRevenueSet) {
		this.salesRevenueSet = salesRevenueSet;
	}

	/**
	 * @return the otherBusinessRevenueSet
	 */
	public boolean getOtherBusinessRevenueSet() {
		return otherBusinessRevenueSet;
	}

	/**
	 * @param otherBusinessRevenueSet the otherBusinessRevenueSet to set
	 */
	public void setOtherBusinessRevenueSet(boolean otherBusinessRevenueSet) {
		this.otherBusinessRevenueSet = otherBusinessRevenueSet;
	}

	/**
	 * @return the internallyProducedAndCapitalizedAssetsSet
	 */
	public boolean getInternallyProducedAndCapitalizedAssetsSet() {
		return internallyProducedAndCapitalizedAssetsSet;
	}

	/**
	 * @param internallyProducedAndCapitalizedAssetsSet the internallyProducedAndCapitalizedAssetsSet to set
	 */
	public void setInternallyProducedAndCapitalizedAssetsSet(
			boolean internallyProducedAndCapitalizedAssetsSet) {
		this.internallyProducedAndCapitalizedAssetsSet = internallyProducedAndCapitalizedAssetsSet;
	}

	/**
	 * @return the materialCostsSet
	 */
	public boolean getMaterialCostsSet() {
		return materialCostsSet;
	}

	/**
	 * @param materialCostsSet the materialCostsSet to set
	 */
	public void setMaterialCostsSet(boolean materialCostsSet) {
		this.materialCostsSet = materialCostsSet;
	}

	/**
	 * @return the humanCapitalCostsSet
	 */
	public boolean getHumanCapitalCostsSet() {
		return humanCapitalCostsSet;
	}

	/**
	 * @param humanCapitalCostsSet the humanCapitalCostsSet to set
	 */
	public void setHumanCapitalCostsSet(boolean humanCapitalCostsSet) {
		this.humanCapitalCostsSet = humanCapitalCostsSet;
	}

	/**
	 * @return the writeDownsSet
	 */
	public boolean getWriteDownsSet() {
		return writeDownsSet;
	}

	/**
	 * @param writeDownsSet the writeDownsSet to set
	 */
	public void setWriteDownsSet(boolean writeDownsSet) {
		this.writeDownsSet = writeDownsSet;
	}

	/**
	 * @return the otherBusinessCostsSet
	 */
	public boolean getOtherBusinessCostsSet() {
		return otherBusinessCostsSet;
	}

	/**
	 * @param otherBusinessCostsSet the otherBusinessCostsSet to set
	 */
	public void setOtherBusinessCostsSet(boolean otherBusinessCostsSet) {
		this.otherBusinessCostsSet = otherBusinessCostsSet;
	}

	/**
	 * @return the interestAndOtherCostsSet
	 */
	public boolean getInterestAndOtherCostsSet() {
		return interestAndOtherCostsSet;
	}

	/**
	 * @param interestAndOtherCostsSet the interestAndOtherCostsSet to set
	 */
	public void setInterestAndOtherCostsSet(boolean interestAndOtherCostsSet) {
		this.interestAndOtherCostsSet = interestAndOtherCostsSet;
	}

}
