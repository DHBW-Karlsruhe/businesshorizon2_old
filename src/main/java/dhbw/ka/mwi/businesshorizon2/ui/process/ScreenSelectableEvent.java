package dhbw.ka.mwi.businesshorizon2.ui.process;

import com.mvplite.event.Event;

import dhbw.ka.mwi.businesshorizon2.ui.process.navigation.NavigationSteps;

public class ScreenSelectableEvent extends Event {
	private static final long serialVersionUID = 1L;

	private NavigationSteps navigationStep;
	
	private boolean isSelectable;
	
	public ScreenSelectableEvent(NavigationSteps navigationStep, boolean isSelectable) {
		this.setNavigationStep(navigationStep);
		this.setSelectable(isSelectable);
	}

	public NavigationSteps getNavigationStep() {
		return navigationStep;
	}

	public void setNavigationStep(NavigationSteps navigationStep) {
		this.navigationStep = navigationStep;
	}

	public boolean isSelectable() {
		return isSelectable;
	}

	public void setSelectable(boolean isSelectable) {
		this.isSelectable = isSelectable;
	}
	
	
}
