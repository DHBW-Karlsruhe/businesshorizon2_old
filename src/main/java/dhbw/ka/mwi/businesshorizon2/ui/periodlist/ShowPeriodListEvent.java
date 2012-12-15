package dhbw.ka.mwi.businesshorizon2.ui.periodlist;

import java.util.Set;

import com.mvplite.event.Event;

import dhbw.ka.mwi.businesshorizon2.models.Period;

public class ShowPeriodListEvent extends Event {
	private static final long serialVersionUID = 1L;

	private Set<Period> periods;

	public ShowPeriodListEvent(Set<Period> periods) {
		this.periods = periods;
	}

	public Set<Period> getPeriods() {
		return periods;
	}
	
}
