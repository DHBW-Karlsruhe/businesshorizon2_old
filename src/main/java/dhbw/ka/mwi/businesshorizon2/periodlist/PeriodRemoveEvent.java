package dhbw.ka.mwi.businesshorizon2.periodlist;

import com.mvplite.event.Event;

import dhbw.ka.mwi.businesshorizon2.models.Period;

public class PeriodRemoveEvent extends Event {
	private static final long serialVersionUID = 1L;
	
	private Period period;

	public PeriodRemoveEvent(Period period) {
		this.period = period;
	}

	public Period getPeriod() {
		return period;
	}
	
}
