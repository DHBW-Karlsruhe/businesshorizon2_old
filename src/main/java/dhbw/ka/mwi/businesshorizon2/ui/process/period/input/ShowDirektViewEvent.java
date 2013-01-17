package dhbw.ka.mwi.businesshorizon2.ui.process.period.input;

import com.mvplite.event.Event;

import dhbw.ka.mwi.businesshorizon2.models.Period.CashFlowPeriod;

public class ShowDirektViewEvent extends Event {
	
	public ShowDirektViewEvent(CashFlowPeriod period){
		this.period = period;
	}
	public CashFlowPeriod getPeriod() {
		return period;
	}
	private CashFlowPeriod period; 

}
