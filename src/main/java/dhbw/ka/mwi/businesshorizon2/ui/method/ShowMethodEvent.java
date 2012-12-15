package dhbw.ka.mwi.businesshorizon2.ui.method;

import com.mvplite.event.Event;

import dhbw.ka.mwi.businesshorizon2.methods.Method;

public class ShowMethodEvent extends Event {
	private static final long serialVersionUID = 1L;

	private Method method;

	public ShowMethodEvent(Method method) {
		this.method = method;
	}

	public Method getMethod() {
		return method;
	}
	
}
