package dhbw.ka.mwi.businesshorizon2.ui.period;

import com.vaadin.terminal.Resource;

import dhbw.ka.mwi.businesshorizon2.ui.main.ContentView;

/**
 * Dieses Interface zeigt die von bezueglich des Hauptfensters zur Verfuegung
 * stehenden Methoden, mit denen der Presenter mit der View kommunizieren kann.
 * 
 * @author Christian Gahlert, Julius Hacker
 *
 */
public class PeriodViewImpl implements PeriodView {

	@Override
	public String getCaption() {
		return "Perioden";
	}

	@Override
	public Resource getIcon() {
		// TODO Find icon for view.
		return null;
	}


}
