package dhbw.ka.mwi.businesshorizon2.periodedit;

import com.mvplite.event.Event;

import dhbw.ka.mwi.businesshorizon2.models.Period;

public class ShowPeriodEditEvent extends Event {
	private static final long serialVersionUID = 1L;

	private Period period;

	public ShowPeriodEditEvent(Period object) {
		super();
		this.period = object;
	}

	public Period getPeriod() {
		return period;
	}
	
}
