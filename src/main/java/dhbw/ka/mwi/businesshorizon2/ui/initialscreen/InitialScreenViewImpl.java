package dhbw.ka.mwi.businesshorizon2.ui.initialscreen;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.mvplite.view.View;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalSplitPanel;
import com.vaadin.ui.Window;


/**
 * Dies ist die Vaadin-Implementierung der InitalScreenView (dem
 * Eingangs-Fenster).
 * 
 * @author Christian Scherer
 * 
 */
public class InitialScreenViewImpl extends Window implements
		InitialScreenViewInterface {
	private static final long serialVersionUID = 1L;

	@Autowired
	private InitialScreenPresenter presenter;

	private VerticalSplitPanel verticalPanel;

	private HorizontalSplitPanel horizontalPanel;

	private HorizontalLayout heading;

	private Label title;
	/**
	 * Dies ist der Konstruktor, der von Spring nach der Initialierung der
	 * Dependencies aufgerufen wird. Er registriert sich selbst beim Presenter
	 * und initialisiert die View-Komponenten.
	 * 
	 * @author Christian Scherer
	 */
	@PostConstruct
	public void init() {
		presenter.setView(this);
		generateUi();
	}

	/**
	 * Diese Methode setzt den Titel (im Browser-Fenster) zu
	 * "Business Horizon 2" und erstellt die Überschrift sowie die zwei
	 * variablen Komponenten.
	 * 
	 * @author Christian Scherer
	 */
	private void generateUi() {
		setCaption("Business Horizon 2");

		verticalPanel = new VerticalSplitPanel();
		verticalPanel.setSizeFull();
		verticalPanel.setSplitPosition(100, UNITS_PIXELS);

		heading = new HorizontalLayout();
		title = new Label("<h1>Business Horizon 2</h1>");
        title.setContentMode(Label.CONTENT_XHTML);
		heading.addComponent(title);
		
		verticalPanel.setFirstComponent(heading);

		horizontalPanel = new HorizontalSplitPanel();
		horizontalPanel.setSizeFull();
		horizontalPanel.setSplitPosition(50, UNITS_PERCENTAGE);

		verticalPanel.setSecondComponent(horizontalPanel);

		setContent(verticalPanel);
	}

	/**
	 * Diese Methode setzt nun die übergebenen zwei Views das Horizontale Layout
	 * unter der Überschrift.
	 * 
	 * @param leftView:  Die PeriodenListe
	 * @param rightView: Die Infoanzeige
	 * @author Christian Scherer
	 */
	@Override
	public void showView(View leftView, View rightView) {
		horizontalPanel.setFirstComponent((Component) leftView);
		horizontalPanel.setSecondComponent((Component) rightView);
	}

}
