package dhbw.ka.mwi.businesshorizon2.methods.timeseries;

public interface CalculateTideInterface {
	public void addTide();

	public void reduceTide();

	public double getTideValue(int period);

	public void setTimeseries(
			dhbw.ka.mwi.businesshorizon2.models.Timeseries timeseries);
}
