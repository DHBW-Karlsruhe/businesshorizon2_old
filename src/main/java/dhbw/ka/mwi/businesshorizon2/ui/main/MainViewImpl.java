package dhbw.ka.mwi.businesshorizon2.ui.main;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.mvplite.view.View;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalSplitPanel;
import com.vaadin.ui.Window;

import dhbw.ka.mwi.businesshorizon2.ui.method.MethodView;

/**
 * Dies ist die Vaadin-Implementierung der MainView (dem Haupt-Fenster).
 * 
 * @author Christian Gahlert, Julius Hacker
 *
 */
public class MainViewImpl extends Window implements MainView {
	private static final long serialVersionUID = 1L;

	@Autowired
	private MainPresenter presenter;
	
	private TabSheet tabsheet;
	
	private ContentView methodView;
	private ContentView parameterView;
	private ContentView periodView;
	private ContentView processingView;
	private ContentView outputView;

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
		
		//MethodView 
		
		tabsheet = new TabSheet();
		tabsheet.setSizeFull();
		
		setContent(tabsheet);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setActiveTab(int number) {
		//TODO: Tabwechsel implementieren
	}

}
