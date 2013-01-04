package dhbw.ka.mwi.businesshorizon2.ui.process.navigation;

/**
 * Dieses Enum bildet die verfuegbaren Prozessschritte ab. Sie stellt
 * hierzu passende Namen sowie Buttonbeschriftungen bereit.
 * 
 * @author Julius Hacker
 */
public enum NavigationSteps {
	METHOD("Methoden"), PERIOD("Perioden"), PARAMETER("Parameter"), SCENARIO("Szenarien"), OUTPUT("Ausgabe");
	
	private String caption;
	
	private NavigationSteps(String caption) {
		this.caption = caption;
	}
	
	/**
	 * Gibt die Buttonueberschrift des Prozessschrittes zurueck.
	 * 
	 * @return String mit der Buttonueberschrift des Prozessschrittes.
	 */
	public String getCaption() {
		return this.caption;
	}
}