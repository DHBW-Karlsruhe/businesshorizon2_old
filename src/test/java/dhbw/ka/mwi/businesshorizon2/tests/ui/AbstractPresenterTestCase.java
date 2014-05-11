/*******************************************************************************
 * BusinessHorizon2
 *
 * Copyright (C) 
 * 2012-2013 Christian Gahlert, Florian Stier, Kai Westerholz,
 * Timo Belz, Daniel Dengler, Katharina Huber, Christian Scherer, Julius Hacker
 * 2013-2014 Marcel Rosenberger, Mirko Göpfrich, Annika Weis, Katharina Narlock, 
 * Volker Meier
 * 
 *
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/


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
