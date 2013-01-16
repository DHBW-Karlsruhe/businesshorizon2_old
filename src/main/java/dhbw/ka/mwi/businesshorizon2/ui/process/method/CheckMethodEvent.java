package dhbw.ka.mwi.businesshorizon2.ui.process.method;

import java.util.Set;

import com.mvplite.event.Event;

import dhbw.ka.mwi.businesshorizon2.methods.AbstractStochasticMethod;

public class CheckMethodEvent extends Event {

	private static final long serialVersionUID = 1L;	
	private Set<AbstractStochasticMethod> checkedMethods;
	
	public CheckMethodEvent(Set<AbstractStochasticMethod> checkedMethods) {
		super();
		this.checkedMethods=checkedMethods;
	}

	public Set<AbstractStochasticMethod> getCheckedMethods() {
		return checkedMethods;
	}


}