package dhbw.ka.mwi.businesshorizon2.ui.process.period.timeline;

import dhbw.ka.mwi.businesshorizon2.models.Period.PeriodInterface;
import dhbw.ka.mwi.businesshorizon2.ui.process.contentcontainer.ContentView;

public interface TimelineViewInterface extends ContentView {
	
	
	
	
	public void addFuturePeriod(PeriodInterface period);
	
	public void removeFuturePeriod();
	
	public void addPastPeriod(PeriodInterface period);
	
	public void removePastPeriod();
	
	public void setPeriodValid(int year, boolean isValid);
	
	public void setPastButtonAccess(boolean usable);
	
	public void setFutureButtonAccess(boolean usable);

	void addBasePeriod(PeriodInterface period);
	

}