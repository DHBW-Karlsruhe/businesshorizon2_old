package dhbw.ka.mwi.businesshorizon2.ui.process.contentcontainer;

import com.mvplite.view.View;

import dhbw.ka.mwi.businesshorizon2.ui.process.contentcontainer.ContentView;

/**
 * Dieses Interface zeigt die von bezueglich des Maskenfensters zur Verfuegung
 * stehenden Methoden, mit denen der Presenter mit der View kommunizieren kann.
 * 
 * @author Julius Hacker
 *
 */
public interface ContentContainerView extends View {
	
	/**
	 * Diese Methode kuemmert sich um die Anzeige der ihr vom Presenter
	 * uebergebene Maske in Form einer ContentView.
	 * 
	 * @param newView Die anzuzeigende Maske in Form einer ContentView.
	 * @author Julius Hacker
	 */
	public void showContentView(ContentView newView);
}
