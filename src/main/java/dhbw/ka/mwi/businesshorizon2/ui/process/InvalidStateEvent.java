package dhbw.ka.mwi.businesshorizon2.ui.process;

import com.mvplite.event.Event;

import dhbw.ka.mwi.businesshorizon2.ui.process.navigation.NavigationSteps;

/**
 * Der Event, der von den Prozess-Screens gefeuert wird, wenn sie sich in einem ungueltigem
 * Zustand befinden.
 * 
 * @author Julius Hacker
 *
 */
public class InvalidStateEvent extends Event {
	private static final long serialVersionUID = 1L;
	
	private NavigationSteps navigationStep;
	
	private boolean showErrors;
	
	/**
	 * Der Konstruktor setzt den Screen, auf den sich das Event bezieht sowie
	 * eine Angabe, ob der Navigationsbutton eine entsprechende Fehlermarkierung
	 * erhalten soll.
	 * 
	 * @author Julius Hacker
	 * @param navigationStep Der Screen, auf den sich das Event bezieht
	 * @param showErrors true: Navigationsbutton soll eine Fehlermarkierung erhalten.
	 * false: Navigationsbutton soll keine Fehlermarkierung erhalten.
	 */
	public InvalidStateEvent(NavigationSteps navigationStep, boolean showErrors) {
		this.setNavigationStep(navigationStep);
		this.setShowErrors(showErrors);
	}

	public NavigationSteps getNavigationStep() {
		return navigationStep;
	}

	public void setNavigationStep(NavigationSteps navigationStep) {
		this.navigationStep = navigationStep;
	}

	public boolean isShowErrors() {
		return showErrors;
	}
	
	public void setShowErrors(boolean showErrors) {
		this.showErrors = showErrors;
	}

}
