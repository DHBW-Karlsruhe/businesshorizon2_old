/**
 * 
 */
package dhbw.ka.mwi.businesshorizon2.services.persistence;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;

import dhbw.ka.mwi.businesshorizon2.models.Project;
import dhbw.ka.mwi.businesshorizon2.models.User;
import dhbw.ka.mwi.businesshorizon2.services.authentication.AuthenticationService;

/**
 * 
 * /**
 * Klasse zur Persistierung von Projektdaten eines Users über die Session eines Servers hinaus.
 * Die Projekte werden in einer Datei gespeichert und dort hinzugefügt, geändert und gelöscht.
 *
 * @author Marcel Rosenberger
 *
 */
public class PersistenceService implements PersistenceServiceInterface {

	private File file;
	
	private static final String DIRECTORY = System.getProperty("user.home") + "\\Business Horizon";
	private static final String FILENAME = "\\projects.dat";
	
	private static Logger logger = Logger.getLogger(PersistenceService.class);
	
	private ArrayList<Project> allProjects;
	
	/**
	 * Methode zur Initialisierung des PersistenceService durch Spring.
	 * Projektdatei wird erstellt.
	 */
	@PostConstruct
	public void init() {

		file = new File(DIRECTORY);

		if (!file.exists()) {
			file.mkdir();
			logger.debug("New directory created at: " + file.getAbsolutePath());
		}

		file = new File(DIRECTORY + FILENAME);

		if (!file.exists()) {
			try {
				file.createNewFile();
				logger.debug("New file created at: " + file.getAbsolutePath());
			} catch (IOException e) {
				logger.error("Could not create a new file at: " + file.getAbsolutePath());
			}
		}
	
		initializeProjectList();
		logger.debug("PersistenceService successfully initialized");
	}
	
	/**
	 * Die Methode initialisiert die ArrayList, in welcher alle Projekte werden. 
	 * 
	 * @author: Marcel Rosenberger
	 * 
	 */
	private synchronized void initializeProjectList() {

		FileInputStream fileInput;
		ObjectInputStream projectInput;

		try {
			fileInput = new FileInputStream(file);
			projectInput = new ObjectInputStream(fileInput);

			int nrOfProjects = projectInput.readInt();

			allProjects = new ArrayList<Project>();

			for (int i = 1; i <= nrOfProjects; i++) {
				Project project = (Project) projectInput.readObject();
				allProjects.add(project);
			}

			fileInput.close();
			projectInput.close();

		} catch (FileNotFoundException e) {
			logger.error("The specified file could not be found");
		} catch (IOException e) {
			logger.error("Initialization: An IOException occured: " + e.getMessage());
		} catch (ClassNotFoundException e) {
			logger.error("A ClassNotFoundException occured: " + e.getMessage());
		}

	}
	
	@Override
	/**
	 * Methode zum Laden aller Projektdaten des angemeldeten Users.
	 * 
	 * @param user
	 *            User dessen Projektdaten geladen werden sollen
	 * @return Project Objekt mit allen Projektdaten
	 */
	public ArrayList<Project> loadProjects(User user) {
		return null;
	}

	/**
	 * Methode zum Hinzufügen eines Projekts für einen User
	 * 
	 * @param user
	 *            der User, für den ein Projekt hinzugefügt werden soll
	 * @param project
	 *            das Projekt, dessen Daten hinzugefügt werden sollen
	 */
	public void addProject(User user, Project project) {
	}
	
	/**
	 * Methode zum Entfernen eines Projekts für einen User
	 * 
	 * @param user
	 *            der User, für den ein Projekt entfernt werden soll
	 * @param project
	 *            das Projekt,das entfernt werden sollen
	 */
	public void removeProject(User user, Project project) {
	}
	
	/**
	 * Methode zum Ändern eines Projekts für einen User
	 * 
	 * @param user
	 *            der User, für den ein Projekt geändert werden soll
	 * @param project
	 *            das Projekt, dessen Daten geändert werden sollen
	 */
	public void changeProject(User user, Project project) {
	}

}
