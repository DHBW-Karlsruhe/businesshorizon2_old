package dhbw.ka.mwi.businesshorizon2.ui.process;

import com.mvplite.event.Event;

import dhbw.ka.mwi.businesshorizon2.ui.process.navigation.NavigationSteps;

/**
 * Der Event, der zum Anzeigen eines Prozessschrittes gefeuert wird.
 * 
 * @author Christian Gahlert
 *
 */
public class ShowNavigationStepEvent extends Event {
	private static final long serialVersionUID = 1L;
	
	private NavigationSteps step;

	public ShowNavigationStepEvent(NavigationSteps step) {
		this.step = step;
	}
	
	public NavigationSteps getStep() {
		return step;
	}

}
