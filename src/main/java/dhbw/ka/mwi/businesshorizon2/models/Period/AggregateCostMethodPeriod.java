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

	/**
	 * Sonstige betriebliche Erträge
	 */
	private double otherBusinessRevenue;

	/**
	 * Andere aktivierte Eigenleistungen
	 */
	private double internallyProducedAndCapitalizedAssets;

	/**
	 * Materialaufwand
	 */
	private double materialCosts;

	/**
	 * Personalaufwand
	 */
	private double humanCapitalCosts;

	/**
	 * Abschreibungen
	 */
	private double writeDowns;

	/**
	 * Sonstige betriebliche Aufwendungen
	 */
	private double otherBusinessCosts;

	/**
	 * Zinsen und andere Aufwendungen
	 */
	private double interestAndOtherCosts;

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
	}

}
