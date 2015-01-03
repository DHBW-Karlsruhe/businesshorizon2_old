package dhbw.ka.mwi.businesshorizon2.ui.initialscreen.projectdetails;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.terminal.Sizeable;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

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
	
	@PostConstruct
	public void init(){
		presenter.setView(this);
		generateUi();
		logger.debug("Initialisierung beendet");
	}
	
	private void generateUi(){
		setWidth(95, UNITS_PERCENTAGE);
		setHeight(Sizeable.SIZE_UNDEFINED, 0);
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
		
		pNameVertical.setWidth(250, UNITS_PIXELS);
		pNameVertical.setHeight(100, UNITS_PERCENTAGE);
		pDetailsVertical.setWidth(250, UNITS_PIXELS);
		pDetailsVertical.setHeight(100, UNITS_PERCENTAGE);
		pDescVertical.setWidth(250, UNITS_PIXELS);
		pDescVertical.setHeight(100, UNITS_PERCENTAGE);
		lChangedVertical.setWidth(250, UNITS_PIXELS);
		lChangedVertical.setHeight(100, UNITS_PERCENTAGE);
		pNameValueVertical.setHeight(100, UNITS_PERCENTAGE);
		pDetailsValueVertical.setHeight(100, UNITS_PERCENTAGE);
		pDescValueVertical.setHeight(100, UNITS_PERCENTAGE);
		lChangedValueVertical.setHeight(100, UNITS_PERCENTAGE);
		
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
	}
	
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
