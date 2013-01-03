package dhbw.ka.mwi.businesshorizon2.ui.process.navigation;

import com.mvplite.view.View;


/**
 * Diese View stellt die Navigation innerhalb der Prozessansicht dar.
 * 
 * @author Julius Hacker
 */
public interface NavigationViewInterface extends View {
	/**
	 * Diese Methode fuegt der Navigation einen Navigationsbutton hinzu.
	 * 
	 * @param value Der Prozessschritt, der durch den Navigationsbutton repraesentiert werden soll.
	 * @author Julius Hacker
	 */
	public void addNavigationButton(NavigationSteps value);
}
