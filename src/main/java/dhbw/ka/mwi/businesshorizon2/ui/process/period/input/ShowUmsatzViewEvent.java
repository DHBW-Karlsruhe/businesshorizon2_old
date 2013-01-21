package dhbw.ka.mwi.businesshorizon2.ui.process.period.input;

import com.mvplite.event.Event;

import dhbw.ka.mwi.businesshorizon2.models.Period.CostOfSalesMethodPeriod;

public class ShowUmsatzViewEvent extends Event {

	
	
	private CostOfSalesMethodPeriod period;

	public ShowUmsatzViewEvent(CostOfSalesMethodPeriod period){
		this.period = period;
	}

	public CostOfSalesMethodPeriod getPeriod() {
		return period;
	}


	
}
