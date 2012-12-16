package dhbw.ka.mwi.businesshorizon2.ui.periodlist;

import com.mvplite.event.Event;

import dhbw.ka.mwi.businesshorizon2.models.Period;

/**
 * Der Event, der beim Entfernen einer Periode von PeriodListPresenter abgesetzt wird.
 * 
 * @author Christian Gahlert
 *
 */
public class PeriodRemoveEvent extends Event {
	private static final long serialVersionUID = 1L;
	
	private Period period;

	/**
	 * Die entfernte Periode.
	 * 
	 * @author Christian Gahlert
	 * @param period
	 */
	public PeriodRemoveEvent(Period period) {
		this.period = period;
	}

	/**
	 * Die entfernte Periode.
	 * 
	 * @author Christian Gahlert
	 * @return
	 */
	public Period getPeriod() {
		return period;
	}
	
}
