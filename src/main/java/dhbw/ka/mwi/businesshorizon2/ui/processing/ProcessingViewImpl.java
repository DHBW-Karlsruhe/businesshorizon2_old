package dhbw.ka.mwi.businesshorizon2.ui.processing;

import com.mvplite.view.View;
import com.vaadin.terminal.Resource;

import dhbw.ka.mwi.businesshorizon2.ui.main.ContentView;

/**
 * Dieses Interface zeigt die von bezueglich des Hauptfensters zur Verfuegung
 * stehenden Methoden, mit denen der Presenter mit der View kommunizieren kann.
 * 
 * @author Christian Gahlert, Julius Hacker
 *
 */
public class ProcessingViewImpl implements ProcessingView {

	@Override
	public String getCaption() {
		return "Verarbeitung";
	}

	@Override
	public Resource getIcon() {
		// TODO Auto-generated method stub
		return null;
	}

}
