package dhbw.ka.mwi.businesshorizon2.ui.process.navigation;

import java.util.HashMap;
import java.util.Map;

/**
 * Dieses Enum bildet die verfuegbaren Prozessschritte ab. Sie stellt
 * hierzu passende Namen sowie Buttonbeschriftungen bereit.
 * 
 * @author Julius Hacker
 */
public enum NavigationSteps {
	METHOD("Methoden", 1), PERIOD("Perioden", 2), PARAMETER("Parameter", 3), SCENARIO("Szenarien", 4), OUTPUT("Ausgabe", 5);
	
	private String caption;
	private Integer number;
	
	 private static final Map<Integer, NavigationSteps> lookup = new HashMap<Integer, NavigationSteps>();
     static {
         for (NavigationSteps step : NavigationSteps.values())
             lookup.put(step.getNumber(), step);
     }
	
	private NavigationSteps(String caption, int number) {
		this.caption = caption;
		this.number = number;
	}
	
	/**
	 * Gibt die Buttonueberschrift des Prozessschrittes zurueck.
	 * 
	 * @return String mit der Buttonueberschrift des Prozessschrittes.
	 */
	public String getCaption() {
		return this.caption;
	}
	
	public int getNumber() {
		return this.number;
	}
	
	public static NavigationSteps getByNumber(int number) {
		return lookup.get(new Integer(number));
	}
	
	public static int getStepCount() {
		return lookup.size();
	}
}