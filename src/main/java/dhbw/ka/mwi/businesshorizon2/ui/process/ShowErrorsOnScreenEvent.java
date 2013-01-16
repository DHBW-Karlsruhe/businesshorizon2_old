package dhbw.ka.mwi.businesshorizon2.ui.process;

import com.mvplite.event.Event;

import dhbw.ka.mwi.businesshorizon2.ui.process.navigation.NavigationSteps;

/**
 * Der Event, der gefeuert wird, um die Ausgabe von Fehlermeldungen auf einem Prozess-Screen
 * zu aktivieren.
 * 
 * @author Julius Hacker
 *
 */
public class ShowErrorsOnScreenEvent extends Event {
	private static final long serialVersionUID = 1L;
	
	private NavigationSteps step;

	/**
	 * Der Konstruktor setzt den Navigationsstep, auf dessen Screen
	 * ab sofort Fehlermeldungen angezeigt werden sollen.
	 * Dieser kann spaeter durch die bearbeitende Funktion
	 * durch getStep() wieder ausgelesen werden.
	 * 
	 * @param step der Navigationsstep, auf dessen Screen ab sofort Fehlermeldungen angezeigt werden sollen.
	 * @author Julius Hacker
	 */
	public ShowErrorsOnScreenEvent(NavigationSteps step) {
		this.step = step;
	}
	
	/**
	 * Die Methode liefert Navigationsschritt, auf dessen Screen
	 * ab sofort die Fehlermeldungen angezeigt werden sollen.
	 * 
	 * @return Der anzuzeigende Navigationsschritt.
	 * @author Julius Hacker
	 */
	public NavigationSteps getStep() {
		return step;
	}

}
