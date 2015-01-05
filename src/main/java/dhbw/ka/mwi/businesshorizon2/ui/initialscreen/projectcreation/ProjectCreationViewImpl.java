package dhbw.ka.mwi.businesshorizon2.ui.initialscreen.projectcreation;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.terminal.Sizeable;
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
import dhbw.ka.mwi.businesshorizon2.ui.initialscreen.InitialScreenViewInterface;

public class ProjectCreationViewImpl extends VerticalLayout implements ProjectCreationViewInterface{

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private ProjectCreationPresenter presenter;
	
	@Autowired
	private ProjectProxy projectProxy;

	private TextField projectNameInput;

	private TextField projectDescriptionInput;

	private Label gap;

	private Label secondGap;
	
	private static final Logger logger = Logger.getLogger("ProjectCreationPresenter.class");
	
	@PostConstruct
	public void init(){
		presenter.setView(this);
		generateUi();
		logger.debug("Initialisierung beendet");
	}
	
	public void generateUi(){
		setWidth(95, UNITS_PERCENTAGE);
		setHeight(Sizeable.SIZE_UNDEFINED, 0);
		setStyleName("projectDetailsLayout");
		
		projectNameInput = new TextField("Projektname");
		projectDescriptionInput = new TextField("Projektbeschreibung");
		gap = new Label();
		secondGap = new Label();
		
		projectNameInput.setWidth(80, UNITS_PERCENTAGE);
		projectNameInput.setHeight(SIZE_UNDEFINED, 0);
		projectNameInput.setStyleName("projectNameInput");
		projectDescriptionInput.setWidth(80, UNITS_PERCENTAGE);
		projectDescriptionInput.setHeight(300, UNITS_PIXELS);
		projectDescriptionInput.setStyleName("projectDescriptionInput");
		gap.setHeight("10px");
		secondGap.setHeight("2px");
		
		projectNameInput.setValue("Geben sie hier den Projektname ein");
		projectDescriptionInput.setValue("Geben sie hier eine Beschreibung des Projekts ein");
		
		addComponent(projectNameInput);
		addComponent(gap);
		addComponent(projectDescriptionInput);
		addComponent(secondGap);
	}
	
	public void setInitialScreen(InitialScreenViewInterface view){
		presenter.setInitialScreenView(view);
	}
	
	public void showErrorMessage(String message) {
		Window.Notification notif = new Notification((String) "",
				message, Notification.TYPE_WARNING_MESSAGE);
		notif.setPosition(Window.Notification.POSITION_CENTERED_TOP);
		getWindow().showNotification(notif);
	}
	
	public void addProject(){
		String projectName = (String) projectNameInput.getValue();
		String projectDescription = (String) projectDescriptionInput.getValue();
		presenter.addProject(projectName, projectDescription);
	}
	
	public void editProject(){
		String projectName = (String) projectNameInput.getValue();
		String projectDescription = (String) projectDescriptionInput.getValue();
		Project project = projectProxy.getSelectedProject();
		presenter.editProject(project, projectName, projectDescription);
	}
	
	public void setProjectData(){
		Project project = projectProxy.getSelectedProject();
		projectNameInput.setValue(project.getName());
		projectDescriptionInput.setValue(project.getDescription());
	}
	
	public void clearProjectData(){
		projectNameInput.setValue("Geben sie hier den Projektname ein");
		projectDescriptionInput.setValue("Geben sie hier eine Beschreibung des Projekts ein");
	}

}
