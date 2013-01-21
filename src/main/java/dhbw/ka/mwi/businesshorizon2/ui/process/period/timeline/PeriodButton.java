package dhbw.ka.mwi.businesshorizon2.ui.process.period.timeline;

import com.vaadin.ui.Button;

import dhbw.ka.mwi.businesshorizon2.models.Period.PeriodInterface;

/**
 * 
 * Erweiterung eines Buttons um sich die Periode die zum Button gehört zu merken
 * @author daniel dengler
 */
public class PeriodButton extends Button {
	
	PeriodInterface period;

	public PeriodButton(String string, ClickListener clickListener) {
		super(string, clickListener);
	}

	public PeriodInterface getPeriod() {
		return period;
	}

	public void setPeriod(PeriodInterface period) {
		this.period = period;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
