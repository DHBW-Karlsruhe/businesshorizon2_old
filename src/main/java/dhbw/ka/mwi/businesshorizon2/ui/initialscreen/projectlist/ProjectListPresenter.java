package dhbw.ka.mwi.businesshorizon2.ui.initialscreen.projectlist;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.mvplite.event.EventBus;
import com.mvplite.event.EventHandler;
import com.mvplite.presenter.Presenter;

import dhbw.ka.mwi.businesshorizon2.models.Project;
import dhbw.ka.mwi.businesshorizon2.models.User;
import dhbw.ka.mwi.businesshorizon2.services.authentication.AuthenticationServiceInterface;
import dhbw.ka.mwi.businesshorizon2.services.authentication.UserNotFoundException;
import dhbw.ka.mwi.businesshorizon2.services.authentication.WrongPasswordException;

/**
 * 
 * Dies ist der Presenter zur Auflistung, dem Hinzufuegen und dem Loeschen von
 * Perioden. Die Liste der Perioden wird dabei der Spring-Injected Project- Bean
 * entnommen, die lediglich einmal pro Session existiert, und in dem
 * project-Property gespeichert wird.
 * 
 * 
 * @author Christian Scherer
 * 
 */
public class ProjectListPresenter extends Presenter<ProjectListViewInterface> {
	private static final long serialVersionUID = 1L;

	private Logger logger = Logger.getLogger("ProjectListPresenter.class");

	@Autowired
	private EventBus eventBus;

	@Autowired
	private User user;

	@Autowired
	private Project selectedProject = null;

	/**
	 * Dies ist der Konstruktor, der von Spring nach der Initialierung der
	 * Dependencies aufgerufen wird. Er registriert sich selbst als einen
	 * EventHandler und bestimmt den User, der fuer die Ermittlung der Projekte
	 * essentiell ist.
	 * 
	 * @author Christian Scherer
	 */
	@PostConstruct
	private void init() {
		eventBus.addHandler(this);
		logger.debug("Eventhandler Hinzugefügt");
	}

	/**
	 * Diese Methode wird von der View aufgerufen, wenn eine Projekt ausgewaehlt
	 * wurde. Somit muss nun der Wizard fuer dieses Projekt und mit den
	 * gespeicherten Daten aufgerufen werden. Das Event ShowProject wird mit dem
	 * ausgewÃ¤hlten Objekt ausgeloest. Somit kann der Wizard ausgefuehrt
	 * werden.
	 * 
	 * @author Christian Scherer
	 * @param project
	 *            das angeklickte Projekt
	 * 
	 */
	public void projectSelected(Project project) {
		selectedProject = project;

		if (selectedProject != null) {
			eventBus.fireEvent(new ShowProjectEvent(selectedProject));
			logger.debug("ShowProjectEvent gefeuert");

		}
	}

	/**
	 * 
	 * Aufruf nach der Initialisierung der ProjectListImpl von aussen. Durch das
	 * Event wird der eingeloggte User übergeben. Es werden die Projekte des
	 * Users ermittelt und der setProjects Methode der Impl Uebergeben um die
	 * UI-Erzeugung der Projekte anzustossen
	 * 
	 * @author Christian Scherer
	 * @param event
	 */
	@EventHandler
	public void onShowProjectList(ShowProjectListEvent event) {

		user = event.getUser();

		// 2 Dummyprojects die dem User hinzugefÃ¼gt werden
		addProject("Projekt 1");
		addProject("Projekt 2");
		logger.debug("2 Dummy-Projekte Erzeugt fÃ¼r erste Darstellung");

		List<Project> projects = user.getProjects();
		logger.debug("Projekte geladen. Anzahl: " + projects.size());

		getView().setProjects(projects);

	}

	/**
	 * Aufruf aus dem ClickListener der Impl mit Uebergabe des zu loeschenden
	 * Projekts. Hier wird nun das Projekt aus dem User-Objekt entfernt, als
	 * auch die Darstellung des projectListPanels mit der aktualisierten Liste
	 * angestossen. Anschliessend wird das dazugehoerige Event gefeuert.
	 * 
	 * @author Christian Scherer
	 * @param project
	 *            - Zu loeschendes Projekt
	 */
	public void removeProject(Project project) {
		user.removeProject(project);
		logger.debug("Projekt aus User entfernt");
		getView().setProjects(user.getProjects());
		eventBus.fireEvent(new ProjectRemoveEvent(project));
		logger.debug("ProjekteRemove Event gefeuert");

	}

	/**
	 * 
	 * Aufruf aus dem ClickListener der Impl mit Uebergabe des Namens des neuen
	 * Projekts. Hier wird nun das Projekt erzeugt, das aktuelle Datum als
	 * letzte Aenderung eingetragen und dem User-Objekt hinzugefuegt. Die
	 * Darstellung des projectListPanels mit der aktualisierten Liste wird durch
	 * das Aufrufen der setProject-Methode angestossen. Anschliessend wird das
	 * dazugehoerige Event gefeuert.
	 * 
	 * @author Christian Scherer
	 * @param name
	 *            Der Name des neue Projekt-Objekts, welches in die Liste
	 *            hinzugefuegt werden soll
	 */
	public void addProject(String name) {

		Project project = new Project(name);
		project.setLastChanged(new Date());
		user.addProject(project);
		logger.debug("Neues Projekt wurde dem User hinzugefuegt");

		getView().setProjects(user.getProjects());

		logger.debug("Neues Projekt an hinterster Stelle eingefuegt");

		eventBus.fireEvent(new ProjectAddEvent(project));
		logger.debug("ShowAddEvent gefeuert");

	}

	/**
	 * 
	 * Aufruf aus dem ClickListener der Impl. Es soll lediglich das Oeffnen des
	 * Projekt-Hinzufuege-Dialog eingeleutet der Impl angestossen werden.
	 * 
	 * @author Christian Scherer
	 */
	public void addProjectDialog() {
		getView().showAddProjectDialog();

	}

}
