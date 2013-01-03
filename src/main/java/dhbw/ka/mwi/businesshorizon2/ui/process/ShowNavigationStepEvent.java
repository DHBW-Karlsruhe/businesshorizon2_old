package dhbw.ka.mwi.businesshorizon2.ui.process;

import com.mvplite.event.Event;

import dhbw.ka.mwi.businesshorizon2.ui.process.navigation.NavigationSteps;

/**
 * Der Event, der zum Anzeigen eines Prozessschrittes gefeuert wird.
 * 
 * @author Julius Hacker
 *
 */
public class ShowNavigationStepEvent extends Event {
	private static final long serialVersionUID = 1L;
	
	private NavigationSteps step;

	/**
	 * Der Konstruktor setzt den Navigationsstep, der angezeigt
	 * werden soll. Dieser kann spaeter durch die bearbeitende
	 * Funktion durch getStep() wieder ausgelesen werden.
	 * 
	 * @param step
	 * @author Julius Hacker
	 */
	public ShowNavigationStepEvent(NavigationSteps step) {
		this.step = step;
	}
	
	/**
	 * Die Methode liefert den anzuzeigenden Navigationsschritt zurueck.
	 * 
	 * @return Der anzuzeigende Navigationsschritt.
	 * @author Julius Hacker
	 */
	public NavigationSteps getStep() {
		return step;
	}

}
