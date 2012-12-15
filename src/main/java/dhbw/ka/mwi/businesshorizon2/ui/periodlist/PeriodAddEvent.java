package dhbw.ka.mwi.businesshorizon2.ui.periodlist;

import com.mvplite.event.Event;

import dhbw.ka.mwi.businesshorizon2.models.Period;

public class PeriodAddEvent extends Event {
	private static final long serialVersionUID = 1L;
	
	private Period period;

	public PeriodAddEvent(Period period) {
		this.period = period;
	}

	public Period getPeriod() {
		return period;
	}
	
}