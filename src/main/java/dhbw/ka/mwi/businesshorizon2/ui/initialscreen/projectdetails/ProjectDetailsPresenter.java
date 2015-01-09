package dhbw.ka.mwi.businesshorizon2.ui.initialscreen.projectdetails;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.mvplite.event.EventBus;
import com.mvplite.event.EventHandler;
import com.mvplite.presenter.Presenter;
import com.vaadin.ui.Label;

import dhbw.ka.mwi.businesshorizon2.models.Project;
import dhbw.ka.mwi.businesshorizon2.models.User;
import dhbw.ka.mwi.businesshorizon2.services.persistence.PersistenceServiceInterface;
import dhbw.ka.mwi.businesshorizon2.services.proxies.ProjectProxy;
import dhbw.ka.mwi.businesshorizon2.ui.initialscreen.ShowProcessStepEvent;
import dhbw.ka.mwi.businesshorizon2.ui.initialscreen.ShowProcessStepEvent.screen;
import dhbw.ka.mwi.businesshorizon2.ui.initialscreen.projectdetails.ProjectDetailsViewInterface;
import dhbw.ka.mwi.businesshorizon2.ui.initialscreen.projectlist.SelectProjectEvent;
import dhbw.ka.mwi.businesshorizon2.ui.initialscreen.projectlist.ShowProjectListEvent;

/**
 * Dieser Presenter ist für die Darstellung der Projektdetails zuständig.
 *
 * @author Marco Glaser
 */
public class ProjectDetailsPresenter extends Presenter<ProjectDetailsViewInterface>{

	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger("InitialScreenPresenter.class");

	@Autowired
	private EventBus eventBus;

	@Autowired
	private ProjectProxy projectProxy;

	private User user;

	@Autowired
	private PersistenceServiceInterface persistenceService;

	/**
	 * Dies ist der Konstruktor, der von Spring nach der Initialierung der
	 * Dependencies aufgerufen wird. Er registriert lediglich sich selbst als
	 * einen EventHandler.
	 *
	 * @author Marco Glaser
	 */
	@PostConstruct
	public void init(){
		eventBus.addHandler(this);
		logger.debug("Eventhandler Hinzugefügt");
	}

	/**
	 * Eventhandler für das ShowProjectListEvent. Dieses wird gefeuert, wenn die Projektliste angezeigt werden soll.
	 * Die Methode zeigt die Projektdetails des ersten Projekts in der Liste an. 
	 *
	 * @param event
	 * : ShowProjectListEvent
	 *
	 * @author Marco Glaser
	 */
	@EventHandler
	public void onShowProjectList(ShowProjectListEvent event) {

		this.user = event.getUser();
		persistenceService.loadProjects(this.user);
		List<Project> projects = user.getProjects();
		logger.debug("Projekte geladen. Anzahl: " + projects.size());

		if(projects.size() != 0){
			Project firstProject = projects.get(0);
			showProjectDetails(firstProject);
		}

	}

	/**
	 * Eventhandler für das ShowProjectDetailsEvent. Dieses wird gefeuert, wenn Details zu einem Projekt angezeigt
	 * werden sollen. Die Methode zeigt die Projektdetails zu diesem Projekt an. 
	 *
	 * @param event
	 * : ShowProjectDetailsEvent
	 *
	 * @author Marco Glaser
	 */
	@EventHandler
	public void onShowProjectDetails(ShowProjectDetailsEvent event){
		if(user.getProjects().size() == 0){
			getView().clearProjectDetails();
		}else{

			showProjectDetails(user.getProjects().get(0));
		}
	}

	/**
	 * Diese Methode baut die Strings für die Projektdetails zusammen. Die notwendigen Infos
	 * bekommt sie aus dem übergebenen Projektobjekt.
	 * Sie ruft dann die Methode in der View auf, um die Details anzuzeigen.
	 *
	 * @param project
	 * Das Projekt, von dem die Details angezeigt werden sollen
	 *
	 * @author Marco Glaser
	 */
	private void showProjectDetails(Project project) {
		String projectName =  project.getName();
		String projectDetails;

		// String fuer saubere Periodenausgabe erstellen.
		int numbersOfPeriods;
		numbersOfPeriods = project.getTotalPeriods();
		if (numbersOfPeriods == 0) {
			projectDetails = "Noch keine Perioden eingetragen";
		}
		else {	
			projectDetails = "" + numbersOfPeriods + " Perioden" ;
		}
		String typMethod;
		typMethod = project.getTypMethod();
		projectDetails = typMethod + ": " + projectDetails;

		String projectDescription = project.getDescription();
		String lastChanged;

		// String fuer Ausgabe des letzten Aenderungsdatum
		if (project.getLastChanged() == null) {
			Date d = new Date();
			lastChanged = d.toString();
		} else {
			lastChanged = project.getLastChanged().toString();
		}

		getView().setProjectDetails(projectName, projectDetails, projectDescription, lastChanged);
	}

	/**
	 * Eventhandler für das SelectProjectEvent. Dieses wird gefeuert, wenn ein Projekt aus der Liste
	 * ausgewählt wird. Die Methode zeigt die Projektdetails zu diesem Projekt an. 
	 *
	 * @param event
	 * : SelectProjectEvent
	 *
	 * @author Marco Glaser
	 */
	@EventHandler
	public void onSelectProject(SelectProjectEvent event){
		Project project = projectProxy.getSelectedProject();
		showProjectDetails(project);
	}

	public void showMethodselectionScreen() {
		eventBus.fireEvent(new ShowProcessStepEvent(screen.METHODSELECTION));
	}

}
