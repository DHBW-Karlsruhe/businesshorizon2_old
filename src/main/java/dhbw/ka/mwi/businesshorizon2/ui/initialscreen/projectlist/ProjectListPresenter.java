package dhbw.ka.mwi.businesshorizon2.ui.initialscreen.projectlist;

import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.mvplite.event.EventBus;
import com.mvplite.event.EventHandler;
import com.mvplite.presenter.Presenter;

import dhbw.ka.mwi.businesshorizon2.models.Project;
import dhbw.ka.mwi.businesshorizon2.models.User;

/**
 * 
 * Dies ist der Presenter zur Auflistung, dem Hinzufuegen und dem Loeschen von
 * Perioden. Die Liste der Perioden wird dabei der Spring-Injected Project- Bean
 * entnommen, die lediglich einmal pro Session existiert, und in dem
 * project-Property gespeichert wird.
 * 
 * Todo: Variablenerklärung Eventbus: User: Benutzerobjekt maxProjects: Anzahl
 * Projkete die auf einer Seite angezeigt werden
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
	 * Dependencies aufgerufen wird. Er registriert lediglich sich selbst als
	 * einen EventHandler.
	 * 
	 * @author Christian Scherer
	 */
	@PostConstruct
	private void init() {
		eventBus.addHandler(this);
		user = new User(); // TODO Hier sollte der User des
		// Authetifizierungsprozesses ausgewählt werden
	}

	/**
	 * Diese Methode wird von der View aufgerufen, wenn eine Projekt ausgewaehlt
	 * wurde. Somit muss nun der Wizard für dieses Projekt und mit den
	 * gespeicherten Daten aufgerufen werden. Das Event ShowProject wird mit dem
	 * ausgewählten Objekt ausgelöst. Somit kann der Wizard ausgeführt werden.
	 * 
	 * @author Christian Scherer
	 * @param project
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
	 * derzeit keine Funktion hinterlegt.
	 * 
	 * @author Christian Scherer
	 * @param event
	 */
	@EventHandler
	public void onShowProjectList(ShowProjectListEvent event) {
		// derzeit keine Funktion hinterlegt

	}

	/**
	 * Aufruf aus der Impl mit übergabe des zu löschenden Projekts in der Liste
	 * der Projekte des Anwenders
	 * 
	 * @author Christian Scherer
	 * @param Project
	 *            - Zu löschendes Projekt
	 */
	public void removeProject(Project project) {
		user.removeProject(project);
		eventBus.fireEvent(new ProjectRemoveEvent(project));
		logger.debug("ProjekteRemove Event gefeuert");

	}

	/**
	 * Gibt die Projekte des Users zurück.
	 * 
	 * @author Christian Scherer
	 * @return Projektliste des Users
	 */
	public List<Project> getProjects() {
		logger.debug("Projekte des Users werden abgerufen");
		return user.getProjects();
	}

	/**
	 * Diese Method fügt ein neu erstelltes Projekt(Project) der
	 * Projektliste(ArrayList<Project>) des Anwenders(User) hinzu. Anschliessend
	 * wird das dazugehörige Event gefeuert
	 * 
	 * @author Christian Scherer
	 * @param Das
	 *            neue Projekt-Objekt, welches in die Liste hinzugefügt werden
	 *            soll
	 */
	public void addProject(Project project) {
		user.addProject(project);
		logger.debug("Neues Projekt wurde dem User hinzugefügt");
		eventBus.fireEvent(new ProjectAddEvent(project));
		logger.debug("ShowAddEvent gefeuert");

	}

}
