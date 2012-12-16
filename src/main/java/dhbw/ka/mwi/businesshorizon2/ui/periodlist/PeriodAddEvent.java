package dhbw.ka.mwi.businesshorizon2.ui.periodlist;

import com.mvplite.event.Event;

import dhbw.ka.mwi.businesshorizon2.models.Period;

/**
 * Der Event, der nach dem Hinzufuegen einer Periode abgesetzt wird. Aktuell
 * gibt es dafuer noch keinen EventHandler.
 * 
 * @author Christian Gahlert
 *
 */
public class PeriodAddEvent extends Event {
	private static final long serialVersionUID = 1L;
	
	private Period period;

	/**
	 * Die hinzugefuegte Periode
	 * 
	 * @author Christian Gahlert
	 * @param period
	 */
	public PeriodAddEvent(Period period) {
		this.period = period;
	}

	/**
	 * Die hinzugefuegte Periode
	 * 
	 * @author Christian Gahlert
	 * @return
	 */
	public Period getPeriod() {
		return period;
	}
	
}
