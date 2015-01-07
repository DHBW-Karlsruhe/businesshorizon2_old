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
		
		projectNameLayout = new HorizontalLayout();
		projectDetailsLayout = new HorizontalLayout();
		projectDescriptionLayout = new HorizontalLayout();
		lastChangedLayout = new HorizontalLayout();
		pNameVertical = new VerticalLayout();
		pDetailsVertical = new VerticalLayout();
		pDescVertical = new VerticalLayout();
		lChangedVertical = new VerticalLayout();
		pNameValueVertical = new VerticalLayout();
		pDetailsValueVertical = new VerticalLayout();
		pDescValueVertical = new VerticalLayout();
		lChangedValueVertical = new VerticalLayout();
		projectName = new Label();
		projectDetails = new Label();
		projectDescription = new Label();
		lastChanged = new Label();
		projectNameValue = new Label();
		projectDetailsValue = new Label();
		projectDescriptionValue = new Label();
		lastChangedValue = new Label();
		expandingGap = new Label();
		
		pNameVertical.setWidth(200, UNITS_PIXELS);
		pNameVertical.setHeight(100, UNITS_PERCENTAGE);
		pDetailsVertical.setWidth(200, UNITS_PIXELS);
		pDetailsVertical.setHeight(100, UNITS_PERCENTAGE);
		pDescVertical.setWidth(200, UNITS_PIXELS);
		pDescVertical.setHeight(100, UNITS_PERCENTAGE);
		lChangedVertical.setWidth(200, UNITS_PIXELS);
		lChangedVertical.setHeight(100, UNITS_PERCENTAGE);
		pNameValueVertical.setHeight(100, UNITS_PERCENTAGE);
		pDetailsValueVertical.setHeight(100, UNITS_PERCENTAGE);
		pDescValueVertical.setHeight(100, UNITS_PERCENTAGE);
		lChangedValueVertical.setHeight(100, UNITS_PERCENTAGE);
		expandingGap.setSizeFull();
		
		projectNameLayout.setWidth(Sizeable.SIZE_UNDEFINED, 0);
		projectNameLayout.setHeight(50, UNITS_PIXELS);
		projectDetailsLayout.setWidth(Sizeable.SIZE_UNDEFINED, 0);
		projectDetailsLayout.setHeight(50, UNITS_PIXELS);
		projectDescriptionLayout.setWidth(Sizeable.SIZE_UNDEFINED, 0);
		projectDescriptionLayout.setHeight(50, UNITS_PIXELS);
		lastChangedLayout.setWidth(Sizeable.SIZE_UNDEFINED, 0);
		lastChangedLayout.setHeight(50, UNITS_PIXELS);
//		projectName.setWidth(100, UNITS_PERCENTAGE);
//		projectName.setHeight(Sizeable.SIZE_UNDEFINED, 0);
		projectName.setSizeUndefined();
		projectName.setStyleName("projectDetailsLabel");
//		projectDetails.setWidth(30, UNITS_PERCENTAGE);
//		projectDetails.setHeight(Sizeable.SIZE_UNDEFINED, 0);
		projectDetails.setSizeUndefined();
		projectDetails.setStyleName("projectDetailsLabel");
//		projectDescription.setWidth(30, UNITS_PERCENTAGE);
//		projectDescription.setHeight(Sizeable.SIZE_UNDEFINED, 0);
		projectDescription.setSizeUndefined();
		projectDescription.setStyleName("projectDetailsLabel");
//		lastChanged.setWidth(30, UNITS_PERCENTAGE);
//		lastChanged.setHeight(Sizeable.SIZE_UNDEFINED, 0);
		lastChanged.setSizeUndefined();
		lastChanged.setStyleName("projectDetailsLabel");
		projectNameValue.setHeight(Sizeable.SIZE_UNDEFINED, 0);
		projectDetailsValue.setHeight(Sizeable.SIZE_UNDEFINED, 0);
		projectDescriptionValue.setHeight(Sizeable.SIZE_UNDEFINED, 0);
		lastChangedValue.setHeight(Sizeable.SIZE_UNDEFINED, 0);
		projectNameValue.setStyleName("projectDetailsValue");
		projectDetailsValue.setStyleName("projectDetailsValue");
		projectDescriptionValue.setStyleName("projectDetailsValue");
		lastChangedValue.setStyleName("projectDetailsValue");
		
		projectNameLayout.addComponent(pNameVertical);
		projectNameLayout.addComponent(pNameValueVertical);
		pNameVertical.addComponent(projectName);
		pNameValueVertical.addComponent(projectNameValue);
		projectDetailsLayout.addComponent(pDetailsVertical);
		projectDetailsLayout.addComponent(pDetailsValueVertical);
		pDetailsVertical.addComponent(projectDetails);
		pDetailsValueVertical.addComponent(projectDetailsValue);
		projectDescriptionLayout.addComponent(pDescVertical);
		projectDescriptionLayout.addComponent(pDescValueVertical);
		pDescVertical.addComponent(projectDescription);
		pDescValueVertical.addComponent(projectDescriptionValue);
		lastChangedLayout.addComponent(lChangedVertical);
		lastChangedLayout.addComponent(lChangedValueVertical);
		lChangedVertical.addComponent(lastChanged);
		lChangedValueVertical.addComponent(lastChangedValue);
		
		pNameVertical.setComponentAlignment(projectName, Alignment.MIDDLE_LEFT);
		pDetailsVertical.setComponentAlignment(projectDetails, Alignment.MIDDLE_LEFT);
		pDescVertical.setComponentAlignment(projectDescription, Alignment.MIDDLE_LEFT);
		lChangedVertical.setComponentAlignment(lastChanged, Alignment.MIDDLE_LEFT);
		pNameValueVertical.setComponentAlignment(projectNameValue, Alignment.MIDDLE_LEFT);
		pDetailsValueVertical.setComponentAlignment(projectDetailsValue, Alignment.MIDDLE_LEFT);
		pDescValueVertical.setComponentAlignment(projectDescriptionValue, Alignment.MIDDLE_LEFT);
		lChangedValueVertical.setComponentAlignment(lastChangedValue, Alignment.MIDDLE_LEFT);
		
		addComponent(projectNameLayout);
		addComponent(projectDetailsLayout);
		addComponent(projectDescriptionLayout);
		addComponent(lastChangedLayout);
		addComponent(expandingGap);
		addComponent(generateStartCalculatingButton());
		setExpandRatio(expandingGap, 1.0f);
	}
	
	private VerticalLayout generateStartCalculatingButton(){
		VerticalLayout button = new VerticalLayout();
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
//				presenter.projectSelected(proj);
//				switchProjectsStyle(a);
//				
//				eventBus.fireEvent(new SelectProjectEvent());
			}
			
		});
		return button;
	}
	
	/**
	 * Diese Methode setzt die Details zu einem Projekt, welche als Parameter übergeben werden,
	 * als Werte der Labels.
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
	}
	
	/**
	 * Diese Methode löscht die Werte aller Labels.
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
	}

}
