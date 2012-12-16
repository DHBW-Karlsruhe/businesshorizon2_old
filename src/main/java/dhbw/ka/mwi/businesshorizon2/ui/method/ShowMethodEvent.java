package dhbw.ka.mwi.businesshorizon2.ui.method;

import com.mvplite.event.Event;

import dhbw.ka.mwi.businesshorizon2.methods.Method;

/**
 * Der Event, zum Anzeigen der MethodView, der im MethodListPresenter abgesetzt wird.
 * 
 * @author Christian Gahlert
 *
 */
public class ShowMethodEvent extends Event {
	private static final long serialVersionUID = 1L;

	private Method method;

	/**
	 * Dem Event muss eine anzuzeigende Methode uebergeben werden.
	 * 
	 * @author Christian Gahlert
	 * @param method Die anzuzeigende Methode
	 */
	public ShowMethodEvent(Method method) {
		this.method = method;
	}

	/**
	 * Die anzuzeigende Methode
	 * 
	 * @author Christian Gahlert
	 * @return
	 */
	public Method getMethod() {
		return method;
	}
	
}
