package dhbw.ka.mwi.businesshorizon2.ui.process;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.mvplite.view.View;
import com.vaadin.terminal.Sizeable;
import com.vaadin.ui.Component;
import com.vaadin.ui.VerticalSplitPanel;
import com.vaadin.ui.Window;

/**
 * Dies ist die Vaadin-Implementierung der ProcessView (dem Prozess-Fenster).
 * Sie rendert die Ansicht der Navigation als obere View und den entsprechenden
 * Masken als untere View
 * 
 * @author Christian Gahlert, Julius Hacker
 *
 */
public class ProcessViewImpl extends Window implements ProcessViewInterface {
	private static final long serialVersionUID = 1L;

	@Autowired
	private ProcessPresenter presenter;
	
	private VerticalSplitPanel splitpanel;

	/**
	 * Dies ist der Konstruktor, der von Spring nach der Initialierung der Dependencies 
	 * aufgerufen wird. Er registriert sich selbst beim Presenter und initialisiert die 
	 * View-Komponenten.
	 * 
	 * @author Julius Hacker
	 */
	@PostConstruct
	public void init() {
		presenter.setView(this);
		generateUi();
	}
	
	/**
	 * Diese Methode setzt den Titel (im Browser-Fenster) zu "Business Horizon 2" und
	 * erstellt das zugehoerige Vertikale Splitpanel, in dem oben die Navigation und
	 * unten die anzuzeigende Maske eingefuegt werden koennen.
	 * 
	 * @author Julius Hacker
	 */
	private void generateUi() {
		setCaption("Business Horizon 2"); 
		
		splitpanel = new VerticalSplitPanel();
		splitpanel.setSplitPosition(75, Sizeable.UNITS_PIXELS);
		
		setContent(splitpanel);
	}
	
	/**
	 * Diese Methode setzt die obere und untere View in der Prozessansicht.
	 * 
	 * @param topView Die obere View-Komponente, gedacht fuer die Navigation
	 * @param bottomView Die untere View-Komponente, gedacht fuer die anzuzeigenden Masken.
	 * @author Julius Hacker
	 */
	@Override
	public void showView(View topView, View bottomView) {
		splitpanel.setFirstComponent((Component) topView);
		splitpanel.setSecondComponent((Component) bottomView);
		
	}
}
