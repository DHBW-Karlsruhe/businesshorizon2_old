package dhbw.ka.mwi.businesshorizon2.ui.periodedit;

import com.mvplite.event.Event;

import dhbw.ka.mwi.businesshorizon2.models.Period;

/**
 * Der Event, der zum Anzeigen der PeriodEditView, vom PeriodListPresenter abgesetzt 
 * wird.
 * 
 * @author Christian Gahlert
 *
 */
public class ShowPeriodEditEvent extends Event {
	private static final long serialVersionUID = 1L;

	private Period period;

	/**
	 * Die anzuzeigende Periode.
	 * 
	 * @author Christian Gahlert
	 * @param period
	 */
	public ShowPeriodEditEvent(Period period) {
		this.period = period;
	}

	/**
	 * Die anzuzeigende Periode.
	 * 
	 * @author Christian Gahlert
	 * @return
	 */
	public Period getPeriod() {
		return period;
	}
	
}
