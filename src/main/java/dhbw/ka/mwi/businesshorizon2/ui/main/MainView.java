package dhbw.ka.mwi.businesshorizon2.ui.main;

import com.mvplite.view.View;

/**
 * Dieses Interface zeigt die von bezueglich des Hauptfensters zur Verfuegung
 * stehenden Methoden, mit denen der Presenter mit der View kommunizieren kann.
 * 
 * @author Christian Gahlert, Julius Hacker
 *
 */
public interface MainView extends View {

	/**
	 * Diese Methode ist dafuer verantwortlich, die untere View
	 * anzuzeigen. Beim Parameter handelt es sich um die entsprechende
	 * *ViewImpl-Instanz.
	 * 
	 * @author Christian Gahlert, Julius Hacker
	 * @param bottomView Die View im Hauptfenster unten
	 */
	public void setActiveTab(int number);

}
