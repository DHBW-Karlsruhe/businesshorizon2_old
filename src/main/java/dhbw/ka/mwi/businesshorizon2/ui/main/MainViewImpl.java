package dhbw.ka.mwi.businesshorizon2.ui.main;

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
 * Dies ist die Vaadin-Implementierung der MainView (dem Haupt-Fenster).
 * 
 * @author Christian Gahlert
 *
 */
public class MainViewImpl extends Window implements MainViewInterface {
	private static final long serialVersionUID = 1L;

	@Autowired
	private MainPresenter presenter;
	
	private VerticalSplitPanel verticalPanel;

	private HorizontalSplitPanel horizontalPanel;

	private HorizontalLayout toolbar;

	private VerticalSplitPanel leftVerticalPanel;

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
	 * erstellt die Toolbar sowie die drei variablen Komponenten.
	 * 
	 * @author Christian Gahlert
	 */
	private void generateUi() {
		setCaption("Business Horizon 2");
		

		verticalPanel = new VerticalSplitPanel();
		verticalPanel.setSizeFull();
		verticalPanel.setSplitPosition(100, UNITS_PIXELS);

		
		toolbar = new HorizontalLayout();
		toolbar.addComponent(new Label("Toolbar"));
		verticalPanel.setFirstComponent(toolbar);
		
		
		horizontalPanel = new HorizontalSplitPanel();
		horizontalPanel.setSizeFull();
		horizontalPanel.setSplitPosition(330, UNITS_PIXELS);
		verticalPanel.setSecondComponent(horizontalPanel);
		
		
		leftVerticalPanel = new VerticalSplitPanel();
		leftVerticalPanel.setSizeFull();
		leftVerticalPanel.setSplitPosition(60, UNITS_PERCENTAGE);
		horizontalPanel.setFirstComponent(leftVerticalPanel);
		
		
		setContent(verticalPanel);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void showView(View leftTopView, View leftBottomView, View rightView) {
		leftVerticalPanel.setFirstComponent((Component) leftTopView);
		leftVerticalPanel.setSecondComponent((Component) leftBottomView);
		
		if(rightView == null) {
			horizontalPanel.setSecondComponent(new Label("hallo"));
		} else {
			horizontalPanel.setSecondComponent((Component) rightView);
		}
	}

}
