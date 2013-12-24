/**
 * 
 */
package dhbw.ka.mwi.businesshorizon2.services.persistence;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;


import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;

import dhbw.ka.mwi.businesshorizon2.models.Project;
import dhbw.ka.mwi.businesshorizon2.models.User;


/**
 * 
 * /** Klasse zur Persistierung von Projektdaten eines Users über die Session
 * eines Servers hinaus. Die Projekte werden in einer Datei gespeichert und dort
 * hinzugefügt, geändert und gelöscht.
 * 
 * @author Marcel Rosenberger
 * 
 */
public class PersistenceService implements PersistenceServiceInterface {

	private File file;

	private static final String DIRECTORY = System.getProperty("user.home")
			+ "\\Business Horizon";
	private static final String FILENAME = "\\projects.dat";

	private static final Logger logger = Logger.getLogger("PersistenceService.class");

	private ArrayList<Project> allProjects;

	/**
	 * 
	 * @author Marcel Rosenberger
	 * 
	 *         Methode zur Initialisierung des PersistenceService durch Spring.
	 *         Projektdatei wird erstellt.
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
				logger.error("Could not create a new file at: "
						+ file.getAbsolutePath());
			}
		}

		initializeProjectList();
		
	}

	/**
	 * Die Methode initialisiert die ArrayList, in welcher alle Projekte werden.
	 * 
	 * @author Marcel Rosenberger
	 * 
	 */
	private synchronized void initializeProjectList() {

		FileInputStream fileInput;
		ObjectInputStream projectInput;
		
		//Projektdatei auslesen und gespeicherte Objekte wieder herstellen
		try {
			fileInput = new FileInputStream(file);
			projectInput = new ObjectInputStream(fileInput);
			logger.debug("InputStreams erzeugt.");
			
			int nrOfProjects = projectInput.readInt();
			logger.debug("Anzahl Projekte gelesen.");
			allProjects = new ArrayList<Project>();
			
			for (int i = 1; i <= nrOfProjects; i++) {
				Project project = (Project) projectInput.readObject();
				logger.debug("Projekt eingelesen.");
				allProjects.add(project);
			}			
			projectInput.close();
			logger.debug("projectInput-Stream closed");
			fileInput.close();
			logger.debug("FileInput-Stream closed");
			logger.debug("PersistenceService successfully initialized");		
		} catch (FileNotFoundException e) {
			logger.error("The specified file could not be found");
		} catch (NotSerializableException e){
			logger.error("An NotSerializableException occured: "
					+ e.getMessage());
			e.printStackTrace();
		} catch (EOFException e) {
			logger.error("Projektdatei ist leer.");
		} catch (IOException e) {
			logger.error("Initialization: An IOException occured: "
					+ e.getMessage());
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			logger.error("A ClassNotFoundException occured: " + e.getMessage());
		}

	}

	/**
	 * Methode zum Laden aller Projektdaten des angemeldeten Users.
	 * 
	 * @author Marcel Rosenberger
	 * 
	 * @param user
	 *            User dessen Projektdaten geladen werden sollen
	 * @return Project Objekt mit allen Projektdaten
	 */
	public synchronized void loadProjects(User user) {
		ArrayList<Project> userProjects = new ArrayList<Project>();
		if (allProjects != null) {
			// lädt alle Projekte....
			for (Project project : allProjects) {
				// ...die von dem derzeit eingeloggten User angelegt wurden...
				if (project.getCreatedFrom().getEmailAdress()
						.equals(user.getEmailAdress())) {
					// ...fügt sie der ArrayList hinzu...
					userProjects.add(project);
				}
			}
		}
		// ...und speichert diese im aufrufenden Userobjekt
		user.setProjects(userProjects);
		logger.debug("Gespeicherte Projekte an den eingeloggten User übergeben.");
	}

	/**
	 * Methode zum Hinzufügen eines Projekts für einen User
	 * 
	 * 
	 * @author Marcel Rosenberger
	 * @param user
	 *            der User, für den ein Projekt hinzugefügt werden soll
	 * @param project
	 *            das Projekt, dessen Daten hinzugefügt werden sollen
	 */
	public synchronized void addProject(User user, Project project)
			throws ProjectAlreadyExistException {
		//Nutzerprojekte in temporärer Liste abspeichern
		ArrayList<Project> userProjects;
		userProjects = user.getProjects();

		if (allProjects == null) {
			allProjects = new ArrayList<Project>();
			logger.debug("allProjects == null");
		}

		// Prüfung ob Projektname bei diesem Nutzer schon beutzt wird
		for (Project projektName : allProjects) {
			if (projektName.getCreatedFrom().getEmailAdress()
					.equals(user.getEmailAdress())) {
				if (projektName.getName().equals(project.getName())) {
					throw new ProjectAlreadyExistException(
							"Projekt mit dem Namen " + project.getName()
									+ " existiert bereits.");
				}
			}
		}
		logger.debug("Projektname wird noch nicht genutzt.");
		//Projekt zu temporärer Liste hinzufügen
		userProjects.add(project);
		//Mit Temporärer Liste die Projekte des Nutzers überschreiben
		user.setProjects(userProjects);
		logger.debug("Projekt zu Nutzerprojekten hinzufügen.");
		allProjects.add(project);
		logger.debug("Projekt zu allen Projekten hinzugefügt.");
		
		//Projektdatei aktualisieren
		try {

			FileOutputStream fileOutput = new FileOutputStream(file);
			ObjectOutputStream objectOutput = new ObjectOutputStream(fileOutput);
			logger.debug("OutputStreams erzeugt.");

			objectOutput.writeInt(allProjects.size());
			for (Project projectToSave : allProjects) {
				objectOutput.writeObject(projectToSave);
			}
			logger.debug("Projektdatei aktualisiert");

			fileOutput.close();
			objectOutput.close();
			logger.debug("OutputStreams geschlossen.");

		} catch (NotSerializableException e){
			logger.error("An NotSerializableException occured: "
					+ e.getMessage());
		} catch (IOException e) {
			logger.error("An IOException occured: " + e.getMessage());
		} 
	}

	/**
	 * Methode zum Entfernen eines Projekts für einen User
	 * 
	 * @author Marcel Rosenberger
	 * @param user
	 *            der User, für den ein Projekt entfernt werden soll
	 * @param project
	 *            das Projekt,das entfernt werden sollen
	 */
	public synchronized void removeProject(User user, Project project){
		//Nutzerprojekte in temporärer Liste abspeichern
		ArrayList<Project> userProjects;
		userProjects = user.getProjects();
		//Projekt aus der temporären Liste löschen
		userProjects.remove(project);
		//mit der temporären Liste die Nutzer-Projekte überschreiben
		user.setProjects(userProjects);
		logger.debug("Projekt aus Nutzerprojekten gelöscht");
		allProjects.remove(project);
		logger.debug("Projekt aus allen Projekten gelöscht.");
		
		//Projektdatei überschreiben
		try {

			FileOutputStream fileOutput = new FileOutputStream(file);
			ObjectOutputStream objectOutput = new ObjectOutputStream(fileOutput);
			logger.debug("OutputStreams erzeugt.");

			objectOutput.writeInt(allProjects.size());
			for (Project projectToSave : allProjects) {
				objectOutput.writeObject(projectToSave);
			}
			logger.debug("Projektdatei aktualisiert");

			fileOutput.close();
			objectOutput.close();
			logger.debug("Projekt erfolgreich gelöscht.");

		} catch (NotSerializableException e){
			logger.error("An NotSerializableException occured: "
					+ e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			logger.error("An IOException occured: " + e.getMessage());
		} 
	}

	/**
	 * Methode zum Speichern der bearbeiteten Projekte.
	 * Überschreibt die Projekt-Datei mit der aktuellen ArrayList allProjects.
	 * 
	 * @author Marcel Rosenberger
	 *
	 */
	public synchronized void saveProjects() {
		//Projektdatei überschreiben
				try {
					FileOutputStream fileOutput = new FileOutputStream(file);
					ObjectOutputStream objectOutput = new ObjectOutputStream(fileOutput);
					logger.debug("OutputStreams erzeugt.");

					objectOutput.writeInt(allProjects.size());
					for (Project projectToSave : allProjects) {
						objectOutput.writeObject(projectToSave);
					}
					logger.debug("Projektdatei aktualisiert");

					fileOutput.close();
					objectOutput.close();
					logger.debug("Projekt erfolgreich gespeichert.");

				} catch (NotSerializableException e){
					logger.error("An NotSerializableException occured: "
							+ e.getMessage());
					e.printStackTrace();
				} catch (IOException e) {
					logger.error("An IOException occured: " + e.getMessage());
				} 
	}

}
