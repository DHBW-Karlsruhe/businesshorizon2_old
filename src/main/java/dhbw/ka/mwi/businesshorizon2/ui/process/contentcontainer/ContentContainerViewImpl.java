package dhbw.ka.mwi.businesshorizon2.ui.process.contentcontainer;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.ui.Component;
import com.vaadin.ui.VerticalLayout;

import dhbw.ka.mwi.businesshorizon2.ui.process.contentcontainer.ContentView;

/**
 * Dies ist die Vaadin-Implementierung der MainView (dem Haupt-Fenster).
 * 
 * @author Christian Gahlert, Julius Hacker
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

	}
	
	public void showContentView(ContentView newView) {
		this.removeAllComponents();
		this.addComponent((Component) newView);
	}
}
