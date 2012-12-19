package dhbw.ka.mwi.businesshorizon2.tests.ui.assets;

import java.util.HashSet;
import java.util.Set;

import com.mvplite.event.Event;
import com.mvplite.event.EventBus;


public class TestEventBus extends EventBus {
	private static final long serialVersionUID = 1L;
	
	private Set<GenericEventHandler> genericHandlers = new HashSet<GenericEventHandler>();
	
	public void addGenericHandler(GenericEventHandler handler) {
		genericHandlers.add(handler);
	}
	
	@Override
	public boolean fireEvent(Event event) {
		for(GenericEventHandler handler : genericHandlers) {
			handler.onEventFired(event);
		}
		
		return super.fireEvent(event);
	}
}
