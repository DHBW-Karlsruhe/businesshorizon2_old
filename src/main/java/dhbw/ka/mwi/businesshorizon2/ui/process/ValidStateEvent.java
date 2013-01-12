package dhbw.ka.mwi.businesshorizon2.ui.process;

import com.mvplite.event.Event;

import dhbw.ka.mwi.businesshorizon2.ui.process.navigation.NavigationSteps;

public class ValidStateEvent extends Event {
	private static final long serialVersionUID = 1L;
	
	private NavigationSteps navigationStep;
	
	public ValidStateEvent(NavigationSteps navigationStep) {
		this.setNavigationStep(navigationStep);
	}

	public NavigationSteps getNavigationStep() {
		return navigationStep;
	}

	public void setNavigationStep(NavigationSteps navigationStep) {
		this.navigationStep = navigationStep;
	}

}
