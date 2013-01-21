package dhbw.ka.mwi.businesshorizon2.ui.process.period.timeline;

import com.vaadin.ui.Button;

import dhbw.ka.mwi.businesshorizon2.models.Period.Period;

public class PeriodButton extends Button {

	Period period;

	public PeriodButton(String string, ClickListener clickListener) {
		super(string, clickListener);
	}

	public Period getPeriod() {
		return period;
	}

	public void setPeriod(Period period) {
		this.period = period;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
