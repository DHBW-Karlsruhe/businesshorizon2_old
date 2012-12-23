package dhbw.ka.mwi.businesshorizon2.ui.contentcontainer;

import com.mvplite.view.View;

import dhbw.ka.mwi.businesshorizon2.ui.contentcontainer.ContentView;

/**
 * Dieses Interface zeigt die von bezueglich des Hauptfensters zur Verfuegung
 * stehenden Methoden, mit denen der Presenter mit der View kommunizieren kann.
 * 
 * @author Christian Gahlert, Julius Hacker
 *
 */
public interface ContentContainerView extends View {
	public void showContentView(ContentView newView);
}
