package dhbw.ka.mwi.businesshorizon2.ui.initialscreen;

import com.mvplite.view.View;

/**
 * Dieses Interface zeigt die von der View zur Verfuegung stehenden Methoden,
 * mit denen der Presenter mit der View kommunizieren kann.
 * 
 * @author Christian Scherer
 *
 */

public interface InitialScreenViewInterface extends View {

	/**
	 * Diese Methode ist dafuer verantwortlich, die jeweiligen 2 View-Elemente
	 * anzuzeigen. Bei den Elementen handelt es sich um die entsprechenden
	 * ViewImpl-Instanzen.
	 * 
	 * @author Christian Scherer
	 * @param leftView Die View im Hauptfenster links
	 * @param rightView Die View im Hauptfenster rechts
	 */
	public void showView(View leftView, View rightView);

}