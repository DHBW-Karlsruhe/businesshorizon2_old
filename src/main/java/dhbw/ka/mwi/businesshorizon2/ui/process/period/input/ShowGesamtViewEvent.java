package dhbw.ka.mwi.businesshorizon2.ui.process.period.input;

import com.mvplite.event.Event;

import dhbw.ka.mwi.businesshorizon2.models.Period.AggregateCostMethodPeriod;

public class ShowGesamtViewEvent extends Event {
	
	public ShowGesamtViewEvent(AggregateCostMethodPeriod period){
		this.period = period;
	}
	
	public AggregateCostMethodPeriod getPeriod() {
		return period;
	}

	private AggregateCostMethodPeriod period;

}
