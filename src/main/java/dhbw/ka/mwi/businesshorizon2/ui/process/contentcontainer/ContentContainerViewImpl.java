package dhbw.ka.mwi.businesshorizon2.ui.process.contentcontainer;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.ui.Component;
import com.vaadin.ui.VerticalLayout;

import dhbw.ka.mwi.businesshorizon2.ui.process.contentcontainer.ContentView;

/**
 * Dies ist die Vaadin-Implementierung der MainView (dem Haupt-Fenster).
 * 
 * @author Julius Hacker
 *
 */
public class ContentContainerViewImpl extends VerticalLayout implements ContentContainerView {
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private ContentContainerPresenter presenter;
	
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
	}
	
	/**
	 * Diese Methode uebernimmt die Anzeige der vom Presenter vorgegebenen Maske
	 * in Vaadin.
	 * 
	 * @author Julius Hacker
	 */
	public void showContentView(ContentView newView) {
		this.removeAllComponents();
		this.addComponent((Component) newView);
	}
}
