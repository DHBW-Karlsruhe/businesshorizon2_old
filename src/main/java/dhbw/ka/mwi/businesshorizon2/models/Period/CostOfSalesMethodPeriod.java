package dhbw.ka.mwi.businesshorizon2.models.Period;


/**
 * Diese Klasse bildet eine Periode nach dem Umsatzkosten Verfahren ab.
 * 
 * @author Kai Westerholz
 * 
 */

public class CostOfSalesMethodPeriod extends BalanceSheetPeriod {
	private int year;

	private double salesRevenue; // Umsatzerlöse
	private double otherBusinessRevenue; // sonstige betriebliche Erträge
	private double costOfPoduction; // Herstellkosten
	private double costOfSalesAdministrationOthers; // Vertriebskosten, allg
													// Verwaltungskosten und
													// sonstige betriebliche
													// Aufwendung

	/**
	 * Diese Methode soll aus den Bilanzwerten den FreeCashFlow berechnen.
	 */
	@Override
	public double getFreeCashFlow() {
		// TODO calculate FreeCashFlow aus Bilanz
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

	public double getCostOfPoduction() {
		return costOfPoduction;
	}

	public void setCostOfPoduction(double costOfPoduction) {
		this.costOfPoduction = costOfPoduction;
	}

	public double getCostOfSalesAdministrationOthers() {
		return costOfSalesAdministrationOthers;
	}

	public void setCostOfSalesAdministrationOthers(
			double costOfSalesAdministrationOthers) {
		this.costOfSalesAdministrationOthers = costOfSalesAdministrationOthers;
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
