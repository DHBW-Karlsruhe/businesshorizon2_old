package dhbw.ka.mwi.businesshorizon2.ui.navigation;

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
	
	public String getCaption() {
		return this.caption;
	}
}