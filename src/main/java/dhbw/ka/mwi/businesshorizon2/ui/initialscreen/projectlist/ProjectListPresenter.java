/*******************************************************************************
 * BusinessHorizon2
 *
 * Copyright (C) 
 * 2012-2013 Christian Gahlert, Florian Stier, Kai Westerholz,
 * Timo Belz, Daniel Dengler, Katharina Huber, Christian Scherer, Julius Hacker
 * 2013-2014 Marcel Rosenberger, Mirko Göpfrich, Annika Weis, Katharina Narlock, 
 * Volker Meier
 * 
 *
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package dhbw.ka.mwi.businesshorizon2.ui.initialscreen.projectlist;

import java.io.File;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.mvplite.event.EventBus;
import com.mvplite.event.EventHandler;
import com.mvplite.presenter.Presenter;
import com.vaadin.ui.Upload;

import dhbw.ka.mwi.businesshorizon2.models.Project;
import dhbw.ka.mwi.businesshorizon2.models.User;
import dhbw.ka.mwi.businesshorizon2.services.persistence.ImportUploadFinishedEvent;
import dhbw.ka.mwi.businesshorizon2.services.persistence.PersistenceServiceInterface;
import dhbw.ka.mwi.businesshorizon2.services.persistence.ProjectAlreadyExistsException;
import dhbw.ka.mwi.businesshorizon2.services.proxies.ProjectProxy;
import dhbw.ka.mwi.businesshorizon2.services.proxies.UserProxy;

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

	private static final Logger logger = Logger.getLogger("ProjectListPresenter.class");

	@Autowired
	private EventBus eventBus;

	@Autowired
	private User user;

	@Autowired
	private ProjectProxy projectProxy;
	
	@Autowired
	private PersistenceServiceInterface persistenceService;
	
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
		projectProxy.setSelectedProject(project);
		
		eventBus.fireEvent(new ShowProjectEvent());
		logger.debug("ShowProjectEvent gefeuert");
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

		this.user = event.getUser();
		persistenceService.loadProjects(this.user);
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
		persistenceService.removeProject(this.user, project);
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
	public void addProject(String name, String description) {

		Project project = new Project(name, description);
		project.setLastChanged(new Date());
		project.setCreatedFrom(this.user);
		try {
			persistenceService.addProject(this.user, project);
		} catch (ProjectAlreadyExistsException e) {
			getView().showErrorMessage(e.getMessage());
			logger.debug("Projektname bereits vorhanden.");

		}
		logger.debug("Neues Projekt wurde dem User hinzugefuegt");

		getView().setProjects(user.getProjects());

		logger.debug("Neues Projekt an hinterster Stelle eingefuegt");

		eventBus.fireEvent(new ProjectAddEvent(project));
		logger.debug("ShowAddEvent gefeuert");

	}
	
	public boolean editProject(Project project, String name, String description) {
		

		try {
			//Wenn der Name beibehalten wurde, erfolgt keine Überprüfung.
			if (project.getName().equals(name)) {
			logger.debug("nur Projekt-Beschreibung geändert");
			}
			//Andernfalls muss überprüft werben, ob es den Namen bereits gibt.
			else {
				for (Project projektName : user.getProjects()) {
					if (projektName.getCreatedFrom().getEmailAdress()
							.equals(user.getEmailAdress())) {
						if (projektName.getName().equals(name)) {
							throw new ProjectAlreadyExistsException(
									"Projekt mit dem Namen " + name
											+ " existiert bereits.");
						}
					}
				}
			}
			project.setName(name);
			project.setDescription(description);
			project.setLastChanged(new Date());
			persistenceService.saveProjects();
			getView().setProjects(user.getProjects());
			return true;
		} catch (ProjectAlreadyExistsException e) {
			getView().showErrorMessage(e.getMessage());
			logger.debug("Projektname bereits vorhanden.");
			return false;
		}


		//eventBus.fireEvent(new ProjectEditEvent(project));
		//logger.debug("ShowEdditEvent gefeuert");

		

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

	/**
	 * Aufruf aus dem ClickListener der Impl. Es soll lediglich das Oeffnen des
	 * Projekt-Bearbeiten-Dialog eingeleutet der Impl angestossen werden.
	 * 
	 * @author Mirko Göpfrich
	 */
	public void editProjectDialog(Project project) {
		getView().showEditProjectDialog(project);
		
	}
	
	/**
	 * Diese Methode per Event nach dem Upload der zu importierenden Projektdatei aufgerufen. Sie löst das Auslesen der in der Datei enthaltenen Projektdaten und das Importieren aus.
	 * Nach dem Import wird das ShowProjectListEvent geworfen, um die angezeigte Liste zu aktualisieren.
	 * 
	 * @param event
	 * 		ImportUploadFinishedEvent, dass als Parameter den Dateinamen der hochgeladenen Datei enthält.
	 * 
	 * @author Tobias Lindner
	 */
	@EventHandler
	public void onUploadFinishedImport (ImportUploadFinishedEvent event) {
		String notImported = null; //in diesen String wird der Rückgabewert, der String mit den Projektnamen, die nicht importiert werden konnten gespeichert
		logger.debug("ImportUploadFinishedEvent empfangen");
		
		notImported = persistenceService.importAllProjects(user, event.getfileName());
		logger.debug ("PersistenceService Import-Funktion im Presenter aufgerufen");
		
		//Ausgabe der Fehlermeldung, falls nicht alle Projekte importiert werden konnten
		if (notImported != null) {
			getView().showErrorMessage(notImported);
		}
		
		//Aktualisieren der Antwort
		eventBus.fireEvent(new ShowProjectListEvent (user));
		logger.debug ("ShowProjectListEvent geworfen");
	}
	
	/**
	 * Aufruf aus dem ClickListener der Impl. Es wird die Erstellung der ExportDatei angestoßen.
	 * Das erzeugte File wird an die View zum Download zurückgeliefert.
	 * 
	 * @return exportFile
	 * 			Das erzeugte ExportFile wird zum Download durch den Client an die View zurückgeliefert.
	 * 
	 * @author Tobias Lindner
	 */
	public File exportProjects () {
		File exportFile;
		
		exportFile = new File (persistenceService.exportUserProjects(user));
		logger.debug("Presenter: Export-Datei erstellt");
		
		return exportFile;
	}
	
}
