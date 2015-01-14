package dhbw.ka.mwi.businesshorizon2.ui.initialscreen.projectdetails;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.terminal.Sizeable;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import dhbw.ka.mwi.businesshorizon2.ui.initialscreen.projectlist.SelectProjectEvent;

/**
 * Diese View ist zuständig für das Anzeigen der Details zu einem Projekt.
 * Sie wird in den rechten Bereich des horizontalen SplitPanels der
 * initialScreenView eingefügt.
 *
 * @author Marco Glaser
 */
public class ProjectDetailsViewImpl extends VerticalLayout implements ProjectDetailsViewInterface{

	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger("ProjectDetailsViewImpl.class");
	
	@Autowired
	private ProjectDetailsPresenter presenter; 
	
	private HorizontalLayout projectNameLayout;
	private HorizontalLayout projectDetailsLayout;
	private HorizontalLayout projectDescriptionLayout;
	private HorizontalLayout lastChangedLayout;
	private VerticalLayout pNameVertical;
	private VerticalLayout pDetailsVertical;
	private VerticalLayout pDescVertical;
	private VerticalLayout lChangedVertical;
	private VerticalLayout pNameValueVertical;
	private VerticalLayout pDetailsValueVertical;
	private VerticalLayout pDescValueVertical;
	private VerticalLayout lChangedValueVertical;
	private Label projectName;
	private Label projectDetails;
	private Label projectDescription;
	private Label lastChanged;
	private Label projectNameValue;
	private Label projectDetailsValue;
	private Label projectDescriptionValue;
	private Label lastChangedValue;

	private Label expandingGap;

	private VerticalLayout button;

	private GridLayout gridLayout;
	
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
	 * Diese Methode erstellt das UI, bestehend aus den Labels für die Details zum Projekt:
	 * Name, Details, Beschreibung und zuletzt geändert.
	 *
	 * @author Marco Glaser
	 */
	private void generateUi(){
		setWidth(95, UNITS_PERCENTAGE);
		setHeight(100, UNITS_PERCENTAGE);
		setStyleName("projectDetailsLayout");
		
		gridLayout = new GridLayout(2, 10);
		gridLayout.setSpacing(true);
		gridLayout.setWidth(100, UNITS_PERCENTAGE);
//		gridLayout.setStyleName("parameter");
		projectName = new Label();
		projectDetails = new Label();
		projectDescription = new Label();
		lastChanged = new Label();
		projectNameValue = new Label();
		projectDetailsValue = new Label();
		projectDescriptionValue = new Label();
		lastChangedValue = new Label();
		expandingGap = new Label();
		
		expandingGap.setSizeFull();

		projectName.setStyleName("projectDetailsLabel");
		projectDetails.setStyleName("projectDetailsLabel");
		projectDescription.setStyleName("projectDetailsLabel");
		lastChanged.setStyleName("projectDetailsLabel");
		projectNameValue.setStyleName("projectDetailsValue");
		projectDetailsValue.setStyleName("projectDetailsValue");
		projectDescriptionValue.setStyleName("projectDetailsValue");
		lastChangedValue.setStyleName("projectDetailsValue");
		projectName.setWidth(180, UNITS_PIXELS);
		projectDetails.setWidth(180, UNITS_PIXELS);
		projectDescription.setWidth(180, UNITS_PIXELS);
		lastChanged.setWidth(180, UNITS_PIXELS);
		
		gridLayout.addComponent(projectName, 0, 1);
		gridLayout.addComponent(projectNameValue, 1, 1);
		gridLayout.addComponent(projectDetails, 0, 2);
		gridLayout.addComponent(projectDetailsValue, 1, 2);
		gridLayout.addComponent(projectDescription, 0, 3);
		gridLayout.addComponent(projectDescriptionValue, 1, 3);
		gridLayout.addComponent(lastChanged, 0, 4);
		gridLayout.addComponent(lastChangedValue, 1, 4);
		
		gridLayout.setColumnExpandRatio(1, 5);
		
		addComponent(gridLayout);
		addComponent(expandingGap);
		addComponent(generateStartCalculatingButton());
		setExpandRatio(expandingGap, 1.0f);
	}
	
	/**
	 * Diese Methode generiert einen Button, der zum Berechnungsprozess führt.
	 * Der Button wird ganz unten links in dieser View hinzugefügt.
	 *
	 * @author Marco Glaser
	 */
	private VerticalLayout generateStartCalculatingButton(){
		button = new VerticalLayout();
		HorizontalLayout container = new HorizontalLayout();
		Embedded icon = new Embedded(null, new ThemeResource("./images/icons/newIcons/1418766041_circle_arrow-forward_next-128.png"));
		Label gap1 = new Label();
		Label gap2 = new Label();
		Label gap3 = new Label();
		icon.setHeight(40, UNITS_PIXELS);
		icon.setWidth(40, UNITS_PIXELS);
		gap1.setWidth("30px");
		gap2.setWidth("15px");
		gap3.setWidth("15px");
		container.setSizeFull();
		
		Label label = new Label("Zur Berechnung");
		label.setWidth(160, UNITS_PIXELS);
		label.setHeight(Sizeable.SIZE_UNDEFINED, 0);
		label.setStyleName("gotoCalculationLabel");
		
		container.addComponent(gap1);
		container.addComponent(label);
		container.addComponent(gap2);
		container.addComponent(icon);
		container.addComponent(gap3);
		container.setComponentAlignment(icon, Alignment.MIDDLE_CENTER);
		container.setComponentAlignment(label, Alignment.MIDDLE_CENTER);
		
		button.addComponent(container);
		button.setWidth(260, UNITS_PIXELS);
		button.setHeight(70, UNITS_PIXELS);
		button.setStyleName("gotoCalculationButton");
//		button.setComponentAlignment(label, Alignment.MIDDLE_CENTER);
		button.addListener(new LayoutClickListener(){

			private static final long serialVersionUID = 1L;

			@Override
			public void layoutClick(LayoutClickEvent event) {
				presenter.showMethodselectionScreen();
			}
			
		});
		button.setVisible(false);
		return button;
	}
	
	/**
	 * Diese Methode setzt die Details zu einem Projekt, welche als Parameter übergeben werden,
	 * als Werte der Labels und blendet den Button ein.
	 * 
	 * @param projectName
	 * : Projektname
	 * @param projectDetails
	 * : Projektdetails
	 * @param projectDescription
	 * : Projektbeschreibung
	 * @param lastChanged
	 * : Datum, wann das Projekt zuletzt geändert wurde
	 *
	 * @author Marco Glaser
	 */
	public void setProjectDetails(String projectName, String projectDetails, String projectDescription, String lastChanged){
		this.projectName.setValue("Projektname:");
		this.projectDetails.setValue("Projektdetails:");
		this.projectDescription.setValue("Beschreibung:");
		this.lastChanged.setValue("Zuletzt geändert:");
		projectNameValue.setValue(projectName);
		projectDetailsValue.setValue(projectDetails);
		projectDescriptionValue.setValue(projectDescription);
		lastChangedValue.setValue(lastChanged);
		button.setVisible(true);
	}
	
	/**
	 * Diese Methode löscht die Werte aller Labels und blendet den Button aus.
	 * Wird benötigt, wenn keine Projekte vorhanden sind.
	 *
	 * @author Marco Glaser
	 */
	public void clearProjectDetails(){
		projectName.setValue("");
		projectDetails.setValue("");
		projectDescription.setValue("");
		lastChanged.setValue("");
		projectNameValue.setValue("");
		projectDetailsValue.setValue("");
		projectDescriptionValue.setValue("");
		lastChangedValue.setValue("");
		button.setVisible(false);
	}

}
