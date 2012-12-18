package dhbw.ka.mwi.businesshorizon2.ui.parameter;

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
public class ParameterViewImpl implements ParameterView {

	@Override
	public String getCaption() {
		return "Parameter";
	}

	@Override
	public Resource getIcon() {
		// TODO: Find Icon for View.
		return null;
	}

}
