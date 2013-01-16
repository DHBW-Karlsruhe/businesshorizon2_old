package dhbw.ka.mwi.businesshorizon2.models.Period;

public interface PeriodInterface extends Comparable<PeriodInterface> {

	public double getFreeCashFlow();

	public double getBorrowedCapital();

	public void setBorrowedCapital(double value);

	public int getYear();
}
