package dhbw.ka.mwi.businesshorizon2.tests.ui;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.mvplite.event.Event;

import dhbw.ka.mwi.businesshorizon2.tests.AbstractSpringBeanTestCase;
import dhbw.ka.mwi.businesshorizon2.tests.ui.assets.GenericEventHandlerInteface;
import dhbw.ka.mwi.businesshorizon2.tests.ui.assets.TestEventBus;


abstract public class AbstractPresenterTestCase extends AbstractSpringBeanTestCase implements GenericEventHandlerInteface {

	@Autowired
	protected TestEventBus eventBus;
	
	protected List<Event> eventsFired;
	
	@PostConstruct
	protected void registerEventHandler() throws Exception {
		eventsFired = new ArrayList<Event>();
		eventBus.addGenericHandler(this);
	}
	
	@Override
	public void onEventFired(Event event) {
		eventsFired.add(event);
	}
	
	protected void assertEventFired(Class<? extends Event> eventClass) {
		assertTrue(eventsFired.size() == 1);
		assertTrue(eventsFired.get(0).getClass() == eventClass);
	}
	
	@SuppressWarnings("unchecked")
	protected <T extends Event> T getEvent(Class<T> eventClass) {
		T event = null;
		
		for(Event eventFired : eventsFired) {
			if(eventFired.getClass() == eventClass) {
				if(event == null) {
					event = (T) eventFired;
				} else {
					fail("There is more than 1 event of the type " + eventClass.getName());
				}
			}
		}
		
		return event;
	}
}
