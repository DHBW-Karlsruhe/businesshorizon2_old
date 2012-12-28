package dhbw.ka.mwi.businesshorizon2.ui.main;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.terminal.Sizeable;
import com.vaadin.ui.Component;
import com.vaadin.ui.VerticalSplitPanel;
import com.vaadin.ui.Window;

import dhbw.ka.mwi.businesshorizon2.ui.contentcontainer.ContentContainerView;
import dhbw.ka.mwi.businesshorizon2.ui.navigation.NavigationView;

/**
 * Dies ist die Vaadin-Implementierung der MainView (dem Haupt-Fenster).
 * 
 * @author Christian Gahlert, Julius Hacker
 *
 */
public class MainViewImpl extends Window implements MainViewInterface {
	private static final long serialVersionUID = 1L;

	@Autowired
	private MainPresenter presenter;
	
	@Autowired
	private NavigationView navigationView;
	
	@Autowired
	private ContentContainerView contentContainerView;
	
	private VerticalSplitPanel splitpanel;

	/**
	 * Dies ist der Konstruktor, der von Spring nach der Initialierung der Dependencies 
	 * aufgerufen wird. Er registriert sich selbst beim Presenter und initialisiert die 
	 * View-Komponenten.
	 * 
	 * @author Christian Gahlert
	 */
	@PostConstruct
	public void init() {
		presenter.setView(this);
		generateUi();
	}
	
	/**
	 * Diese Methode setzt den Titel (im Browser-Fenster) zu "Business Horizon 2" und
	 * erstellt das Tabsheet mit den einzelnen Tab-Views.
	 * 
	 * @author Christian Gahlert, Julius Hacker
	 */
	private void generateUi() {
		setCaption("Business Horizon 2"); 
		
		splitpanel = new VerticalSplitPanel();
		splitpanel.setSplitPosition(75, Sizeable.UNITS_PIXELS);
		splitpanel.setFirstComponent((Component) navigationView);
		splitpanel.setSecondComponent((Component) contentContainerView);
		
		setContent(splitpanel);
	}
}
