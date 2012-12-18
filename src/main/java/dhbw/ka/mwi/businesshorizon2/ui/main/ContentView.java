package dhbw.ka.mwi.businesshorizon2.ui.main;

import com.mvplite.view.View;
import com.vaadin.terminal.Resource;

/**
 * Dieses Interface zeigt die von bezueglich des Hauptfensters zur Verfuegung
 * stehenden Methoden, mit denen der Presenter mit der View kommunizieren kann.
 * 
 * @author Julius Hacker
 *
 */
public interface ContentView extends View {

	/**
	 * Diese Methode liefert die Titelzeile der ContentView zurueck.
	 * Diese wird unter anderem als Beschriftung des Tabs in der MainView verwendet.
	 * 
	 * @author Julius Hacker
	 */
	public String getCaption();
	
	/**
	 * Diese Methode liefert das Icon der ContentView zurueck.
	 * Dieses wird unter anderem zur Beschriftung des Tabs in der MainView verwendet.
	 * 
	 * @author Julius Hacker
	 */
	public Resource getIcon();
}
