package dhbw.ka.mwi.businesshorizon2.ui.process.method;

import java.util.Set;

import com.mvplite.event.Event;

public class CheckMethodEvent extends Event {

	private static final long serialVersionUID = 1L;	
	private Set<String> checkedMethods;
	
	public CheckMethodEvent(Set<String> checkedMethods) {
		super();
		this.checkedMethods=checkedMethods;
	}

	public Set<String> getCheckedMethods() {
		return checkedMethods;
	}


}