package dhbw.ka.mwi.businesshorizon2.ui.initialscreen.projectdetails;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.mvplite.event.EventBus;
import com.mvplite.event.EventHandler;
import com.mvplite.presenter.Presenter;
import com.vaadin.ui.Label;

import dhbw.ka.mwi.businesshorizon2.models.Project;
import dhbw.ka.mwi.businesshorizon2.models.User;
import dhbw.ka.mwi.businesshorizon2.services.persistence.PersistenceServiceInterface;
import dhbw.ka.mwi.businesshorizon2.ui.initialscreen.projectdetails.ProjectDetailsViewInterface;
import dhbw.ka.mwi.businesshorizon2.ui.initialscreen.projectlist.ShowProjectListEvent;

public class ProjectDetailsPresenter extends Presenter<ProjectDetailsViewInterface>{

	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger("InitialScreenPresenter.class");

	@Autowired
	private EventBus eventBus;

	private User user;

	@Autowired
	private PersistenceServiceInterface persistenceService;

	@Autowired
	public void init(){
		eventBus.addHandler(this);
		logger.debug("Eventhandler Hinzugefügt");
	}

	@EventHandler
	public void onShowProjectList(ShowProjectListEvent event) {

		this.user = event.getUser();
		persistenceService.loadProjects(this.user);
		List<Project> projects = user.getProjects();
		logger.debug("Projekte geladen. Anzahl: " + projects.size());

		Project firstProject = projects.get(0);
		String projectName =  firstProject.getName();
		String projectDetails;

		// String fuer saubere Periodenausgabe erstellen.
		int numbersOfPeriods;
		numbersOfPeriods = firstProject.getTotalPeriods();
		if (numbersOfPeriods == 0) {
			projectDetails = "Noch keine Perioden eingetragen";
		}
		else {	
			projectDetails = "" + numbersOfPeriods + " Perioden" ;
		}
		String typMethod;
		typMethod = firstProject.getTypMethod();
		projectDetails = typMethod + ": " + projectDetails;
		
		String projectDescription = firstProject.getDescription();
		String lastChanged;

		// String fuer Ausgabe des letzten Aenderungsdatum
		if (firstProject.getLastChanged() == null) {
			Date d = new Date();
			lastChanged = d.toString();
		} else {
			lastChanged = firstProject.getLastChanged().toString();
		}
		
		getView().setProjectDetails(projectName, projectDetails, projectDescription, lastChanged);

	}

}
