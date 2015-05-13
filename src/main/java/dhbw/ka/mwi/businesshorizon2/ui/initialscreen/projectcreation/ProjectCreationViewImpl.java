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

/**
 * Diese View ist zuständig für das Erstellen eines Projektes und das Bearbeiten eines Projektes,
 * da sich diese beiden Prozesse im UI nicht unterscheiden.
 * Sie wird in den rechten Teil des horizontalen SplitPanels der InitialScreenView eingefügt.
 *
 * @author Marco Glaser, Tobias Lindner
 */
public class ProjectCreationViewImpl extends VerticalLayout implements ProjectCreationViewInterface{

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private ProjectCreationPresenter presenter;
	
	@Autowired
	private ProjectProxy projectProxy;

	private TextField projectNameInput;

	private TextArea projectDescriptionInput;

	private Label gap;

	private Label secondGap;
	
	private static final Logger logger = Logger.getLogger("ProjectCreationPresenter.class");
	
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
		setWidth(95, UNITS_PERCENTAGE);
		setHeight(SIZE_UNDEFINED, 0);
		setStyleName("projectCreationLayout");
		
		projectNameInput = new TextField();
		projectDescriptionInput = new TextArea();
		gap = new Label();
		secondGap = new Label();
		
		projectNameInput.setWidth(80, UNITS_PERCENTAGE);
//		projectNameInput.setHeight(30, UNITS_PIXELS);
		projectNameInput.setStyleName("projectNameInput");
		projectDescriptionInput.setWidth(80, UNITS_PERCENTAGE);
		projectDescriptionInput.setHeight(300, UNITS_PIXELS);
		projectDescriptionInput.setStyleName("projectNameInput");
		gap.setHeight("20px");
		secondGap.setSizeFull();
		
		projectNameInput.setValue("Geben Sie hier den Projektnamen ein.");
		projectDescriptionInput.setValue("Geben Sie hier eine Beschreibung des Projekts ein.");
		
		addComponent(projectNameInput);
		addComponent(gap);
		addComponent(projectDescriptionInput);
		
		projectNameInput.setCaption("Projektname");
		projectNameInput.setValue("Geben Sie hier den Projektnamen ein.");
		projectDescriptionInput.setCaption("Projektbeschreibung");
		projectDescriptionInput.setValue("Geben Sie hier eine Projektbeschreibung ein");
//		addComponent(secondGap);
//		setExpandRatio(secondGap, 1.0f);
	}
	
	/**
	 * wird von einer anderen View aufgerufen, um dieser View das initialScreenView Objekt
	 * zu übergeben. Die View übergibt es an den Presenter.
	 * ?Könnte auch durch Events ersetzt werden?
	 *
	 * @param view
	 * : Die InitialsScreenView der Anwendung
	 *
	 * @author Marco Glaser
	 */
	public void setInitialScreen(InitialScreenViewInterface view){
		presenter.setInitialScreenView(view);
	}
	
	public void showErrorMessage(String message) {
		Window.Notification notif = new Notification((String) "",
				message, Notification.TYPE_WARNING_MESSAGE);
		notif.setPosition(Window.Notification.POSITION_CENTERED_TOP);
		getWindow().showNotification(notif);
	}
	
	/**
	 * Diese Methode zieht die Werte aus den Inputfeldern und übergibt sie an den Presenter,
	 * um dort das Projekt anlegen zu lassen.
	 *
	 * @author Marco Glaser, Tobias Lindner
	 */
	public void addProject(int nextStep){
		String projectName = (String) projectNameInput.getValue();
		String projectDescription = (String) projectDescriptionInput.getValue();
		presenter.addProject(projectName, projectDescription, nextStep);
	}
	
	/**
	 * Diese Methode zieht die Werte aus den Inputfeldern und übergibt sie an den Presenter,
	 * um dort das Projekt bearbeiten zu lassen.
	 *
	 * @author Marco Glaser, Tobias Lindner
	 */
	public void editProject(int nextStep){
		String projectName = (String) projectNameInput.getValue();
		String projectDescription = (String) projectDescriptionInput.getValue();
		Project project = projectProxy.getSelectedProject();
		presenter.editProject(project, projectName, projectDescription, nextStep);
	}
	
	/**
	 * Diese Methode setzt Name und Beschreibung, des aktuell in der Projektliste ausgewählten
	 * Projektes in die Inputfelder. Wird aufgerufen wenn die View zum Bearbeiten eines Projektes
	 * verwendet wird.
	 *
	 * @author Marco Glaser
	 */
	public void setProjectData(){
		Project project = projectProxy.getSelectedProject();
		projectNameInput.setValue(project.getName());
		projectDescriptionInput.setValue(project.getDescription());
	}
	
	/**
	 * Diese Methode setzt Initialwerte in die Inputfelder. Wird aufgerufen wenn die View
	 * zum Erstellen eines neuen Projektes verwendet wird.
	 *
	 * @author Marco Glaser
	 */
	public void clearProjectData(){
		projectNameInput.setValue("Geben sie hier den Projektname ein");
		projectDescriptionInput.setValue("Geben sie hier eine Beschreibung des Projekts ein");
	}

}
