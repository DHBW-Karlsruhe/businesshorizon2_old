package dhbw.ka.mwi.businesshorizon2.ui.initialscreen.projectcreation;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.mvplite.event.EventBus;
import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.terminal.Sizeable;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Window.Notification;

import dhbw.ka.mwi.businesshorizon2.models.Project;
import dhbw.ka.mwi.businesshorizon2.services.proxies.ProjectProxy;
import dhbw.ka.mwi.businesshorizon2.ui.ButtonMiddle;
import dhbw.ka.mwi.businesshorizon2.ui.initialscreen.InitialScreenViewInterface;
import dhbw.ka.mwi.businesshorizon2.ui.initialscreen.ShowInitialTopButtonsEvent;

/**
 * Diese View ist zuständig für das Erstellen eines Projektes und das Bearbeiten eines Projektes,
 * da sich diese beiden Prozesse im UI nicht unterscheiden.
 * Sie wird in den rechten Teil des horizontalen SplitPanels der InitialScreenView eingefügt.
 *
 * @author Marco Glaser
 */
public class StartCalculationButtonViewImpl extends VerticalLayout implements StartCalculationButtonViewInterface{

	private static final long serialVersionUID = 1L;
	
	private static final Logger logger = Logger.getLogger("ProjectCreationPresenter.class");

	@Autowired
	private StartCalculationButtonPresenter presenter;
	
	@Autowired
	private EventBus eventBus;

	private Label gap;

	private ButtonMiddle button;
	
	/**
	 * Dies ist der Konstruktor, der von Spring nach der Initialierung der
	 * Dependencies aufgerufen wird. Er registriert sich selbst beim Presenter
	 * und initialisiert die View-Komponenten.
	 *
	 * @author Marco Glaser
	 */
	@PostConstruct
	public void init(){
		presenter.setView(this);
		generateUi();
		logger.debug("Initialisierung beendet");
	}
	
	/**
	 * Diese Methode erstellt das UI, bestehend aus Inputfeld für Projektname und
	 * Projektbeschreibung.
	 *
	 * @author Marco Glaser
	 */
	public void generateUi(){
		setWidth(85, UNITS_PERCENTAGE);
		setHeight(100, UNITS_PERCENTAGE);
		
		gap = new Label();
		button = new ButtonMiddle("./images/icons/newIcons/1418766041_circle_arrow-forward_next-128.png", "Zur Berechnung", new LayoutClickListener(){

			private static final long serialVersionUID = 1L;

			@Override
			public void layoutClick(LayoutClickEvent event) {
				eventBus.fireEvent(new SaveProjectEvent());
			}
			
		});
		button.changeStyleClass("buttonBottom");
		
		gap.setSizeFull();
		
		addComponent(gap);
		addComponent(button);
		setExpandRatio(gap, 1.0f);
	}
	
}