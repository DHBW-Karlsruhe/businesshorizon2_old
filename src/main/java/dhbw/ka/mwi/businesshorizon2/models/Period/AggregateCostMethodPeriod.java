package dhbw.ka.mwi.businesshorizon2.models.Period;

/**
 * Diese Klasse bildet eine Periode ab. Die Inhalte sind nach dem
 * Umsatzkostenverfahren bewertet und enthalten nur diese Werte. Das Jahr der
 * Periode kann nicht mehr verändert werden.
 * 
 * @author Kai Westerholz
 * 
 */

public class AggregateCostMethodPeriod extends BalanceSheetPeriod {

	private double salesRevenue; // Umsatzerlöse
	private double otherBusinessRevenue; // Sonstige betriebliche Erträge
	private double internallyProducedAndCapitalizedAssets; // Andere aktivierte
															// Eigenleistungen
	private double materialCosts; // Materialaufwand
	private double humanCapitalCosts; // Personalaufwand
	private double writeDowns; // Abschriebungen
	private double otherBusinessCosts; // Sonstige betriebliche Aufwendungen
	private double interestAndOtherCosts; // Zinsen und andere Aufwendungen
	private final int year; // Jahr der Periode

	/**
	 * Der Konstruktor erstellt eine Methode für das Jahr year
	 * 
	 * @param year
	 *            Jahr der Periode
	 * @author Kai Westerholz
	 * 
	 */

	public AggregateCostMethodPeriod(int year) {
		super();
		this.year = year;
	}

	@Override
	public double getFreeCashFlow() {
		// TODO calculate cashflow aus Bilanz posten
		return 0;
	}

	@Override
	public int getYear() {
		return this.year;
	}

	public double getSalesRevenue() {
		return salesRevenue;
	}

	public void setSalesRevenue(double salesRevenue) {
		this.salesRevenue = salesRevenue;
	}

	public double getOtherBusinessRevenue() {
		return otherBusinessRevenue;
	}

	public void setOtherBusinessRevenue(double otherBusinessRevenue) {
		this.otherBusinessRevenue = otherBusinessRevenue;
	}

	public double getInternallyProducedAndCapitalizedAssets() {
		return internallyProducedAndCapitalizedAssets;
	}

	public void setInternallyProducedAndCapitalizedAssets(double internallyProducedAndCapitalizedAssets) {
		this.internallyProducedAndCapitalizedAssets = internallyProducedAndCapitalizedAssets;
	}

	public double getMaterialCosts() {
		return materialCosts;
	}

	public void setMaterialCosts(double materialCosts) {
		this.materialCosts = materialCosts;
	}

	public double getHumanCapitalCosts() {
		return humanCapitalCosts;
	}

	public void setHumanCapitalCosts(double humanCapitalCosts) {
		this.humanCapitalCosts = humanCapitalCosts;
	}

	public double getWriteDowns() {
		return writeDowns;
	}

	public void setWriteDowns(double writeDowns) {
		this.writeDowns = writeDowns;
	}

	public double getOtherBusinessCosts() {
		return otherBusinessCosts;
	}

	public void setOtherBusinessCosts(double otherBusinessCosts) {
		this.otherBusinessCosts = otherBusinessCosts;
	}

	public double getInterestAndOtherCosts() {
		return interestAndOtherCosts;
	}

	public void setInterestAndOtherCosts(double interestAndOtherCosts) {
		this.interestAndOtherCosts = interestAndOtherCosts;
	}

	@Override
	public int compareTo(PeriodInterface o) {
		if (this.getYear() < o.getYear()) {
			return -1;
		} else if (this.getYear() == o.getYear()) {
			return 0;
		} else {
			return 1;
		}
	}

}
