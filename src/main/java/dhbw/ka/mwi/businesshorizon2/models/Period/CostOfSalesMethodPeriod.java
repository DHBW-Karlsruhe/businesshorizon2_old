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
