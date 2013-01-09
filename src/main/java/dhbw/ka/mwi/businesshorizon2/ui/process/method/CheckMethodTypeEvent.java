package dhbw.ka.mwi.businesshorizon2.ui.process.method;

import com.mvplite.event.Event;

public class CheckMethodTypeEvent extends Event {

	private static final long serialVersionUID = 1L;
	
	private Boolean stochastic;
	private Boolean checked;

	public CheckMethodTypeEvent(Boolean stochastic, Boolean checked){
		super();
		this.stochastic = stochastic;
		this.checked = checked;
		
	}
	public Boolean getStochastic() {
		return stochastic;
	}

	public Boolean getChecked() {
		return checked;
	}

}
