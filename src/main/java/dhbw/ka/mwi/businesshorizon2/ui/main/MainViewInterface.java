package dhbw.ka.mwi.businesshorizon2.ui.main;

import com.mvplite.view.View;

/**
 * Dieses Interface zeigt die von der View zur Verfuegung stehenden Methoden,
 * mit denen der Presenter mit der View kommunizieren kann.
 * 
 * @author Christian Gahlert
 *
 */
public interface MainViewInterface extends View {

	/**
	 * Diese Methode ist dafuer verantwortlich, die jeweiligen 3 View-Elemente
	 * anzuzeigen. Bei den Elementen handelt es sich um die entsprechenden
	 * *ViewImpl-Instanzen.
	 * 
	 * @author Christian Gahlert
	 * @param leftTopView Die View links oben
	 * @param leftBottomView Die View links unten
	 * @param rightView Die View im Hauptfenster rechts
	 */
	public void showView(View leftTopView, View leftBottomView, View rightView);

}
