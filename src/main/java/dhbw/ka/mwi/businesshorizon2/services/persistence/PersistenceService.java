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
import java.text.SimpleDateFormat;
import java.util.ArrayList;





import java.util.Date;

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
 * @author Marcel Rosenberger, Mirko Göpfrich
 * 
 */
public class PersistenceService implements PersistenceServiceInterface {

	private File file;
	
	private File importFile;
	
	private File exportFile;
	
	static final String separator = System.getProperties().getProperty("file.separator");

	private static final String DIRECTORY = System.getProperty("user.home")
			+ separator + separator + "Business Horizon";
	private static final String FILENAMESAVEFILE = separator + separator + "projects.dat";
	
	private static final String FILENAMEIMPORTFILE = separator + separator + "projectsImport.dat";
	
	private static final String FILENAMEEXPORTFILE = separator + separator + "projectsExport.dat";
	
	static final String TMPDIRECTORY = DIRECTORY + separator + "tmp";

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
		File tmpdir = new File (TMPDIRECTORY);
		
		if (!tmpdir.exists()) {
			tmpdir.mkdir();
			logger.debug("New directory created at: " + file.getAbsolutePath());
		}

		if (!file.exists()) {
			file.mkdir();
			logger.debug("New directory created at: " + file.getAbsolutePath());
		}

		file = new File(DIRECTORY + FILENAMESAVEFILE);
		
		importFile = new File (DIRECTORY + FILENAMEIMPORTFILE);
		exportFile = new File (DIRECTORY + FILENAMEEXPORTFILE);

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
			throws ProjectAlreadyExistsException {
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
					throw new ProjectAlreadyExistsException(
							"Projekt mit dem Namen " + project.getName()
									+ " existiert bereits.");
				}
			}
		}
		logger.debug("Projektname wird noch nicht genutzt.");
		//Projekt zu temporärer Liste hinzufügen
		userProjects.add(0, project);
		//Mit Temporärer Liste die Projekte des Nutzers überschreiben
		user.setProjects(userProjects);
		logger.debug("Projekt zu Nutzerprojekten hinzufügen.");
		allProjects.add(0, project);
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
		// Prüfung ob Projektname bei diesem Nutzer schon beutzt wird
					
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
	
	/**
	 * Methode zum Importieren von Projekten aus einer externen Projects.dat. Allen Projekten wird der aktuelle User zugeordnet.
	 * 
	 * @param user
	 *		der akutelle User, zu dessen Projekte die Projekte importiert werden sollen.
	 * @param fileName
	 * 			der Dateiname der hochgeladen Datei
	 * @return String
	 * 			String mit den Namen der Projekt, die nicht importiert werden konnten
	 * 
	 * @author Tobias Lindner
	 */
	public synchronized String importAllProjects (User user, String fileName) {
		ArrayList<Project> importProjects = new ArrayList<Project>();
		FileInputStream fileInput;
		ObjectInputStream projectInput;
		
		String notImportedProjects = null; //Speicher-String mit den Namen der Projekte, die nicht importiert werden konnten.
		
		//Zu importierende Projektdatei auslesen und in einer ArrayList ablegen
		try {
			fileInput = new FileInputStream(TMPDIRECTORY + separator + fileName);
			projectInput = new ObjectInputStream(fileInput);
			logger.debug("Import InputStreams erzeugt.");
			
			int nrOfImportedProjects = projectInput.readInt();
			logger.debug("Anzahl zu importierender Projekte gelesen.");
			
			for (int i = 1; i <= nrOfImportedProjects; i++) {
				Project project = (Project) projectInput.readObject();
				logger.debug("Import: Projekt eingelesen.");
				importProjects.add(project);
			}			
			projectInput.close();
			logger.debug("Import: projectInput-Stream closed");
			fileInput.close();
			logger.debug("Import: FileInput-Stream closed");
			
			File f = new File (TMPDIRECTORY + fileName);
			f.delete();
			logger.debug("Uploaded File deleted");
	
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
		
		//Hinzufügen der ArrayList mit Projecten zum aktuellen User
				
		for (Project projectI : importProjects) {
			try {
				projectI.setCreatedFrom(user);
				addProject(user, projectI);
				logger.debug ("Import: Projekt hinzugefügt.");
				
			} catch (ProjectAlreadyExistsException e) {
				if (notImportedProjects == null) {
					notImportedProjects = "Folgende Projekte konnten nicht importiert werden: ";
				}
				else {
					notImportedProjects = notImportedProjects + ", ";
				}
				logger.debug ("Import: ProjectAlreadyExistsException: Folgendes Projekt existiert bereits: " + projectI.getName());
				notImportedProjects = notImportedProjects + projectI.getName();
			}
		}
		
		return notImportedProjects;
				
	}
	
	/**
	 * Methode zum exportieren der Projecte des aktuell angemeldeten Users.
	 * 
	 * @param user
	 * 			der akutelle User, dessen Projekte exportiert werden sollen.
	 * @return String
	 * 			Pfad zur erzeugten Export-Datei. Von diesem Pfad wird die Datei gedownloaded.
	 * 
	 * @author Tobias Lindner
	 */
	public synchronized String exportUserProjects(User user) {
		saveProjects();
		String exportFileName = null;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss-SSS");
		
		//Bereits vorhandenes Export-File des Users löschen
		File tmpdir = new File (TMPDIRECTORY);
		File[] tmpFiles = tmpdir.listFiles();
		
		for (int i = 0; i<tmpFiles.length;i++) {
			if (tmpFiles [i].getName().contains(user.getEmailAdress())) {
				tmpFiles[i].delete();
				logger.debug("Bereits vorhandene Export Datei gelöscht.");
			}
		}
		
		//Export-File generieren			
		try {
			exportFileName = TMPDIRECTORY + separator + "ProjectExport_" + user.getEmailAdress() +"_" + df.format(new Date ()) +".dat";
			FileOutputStream fileOutput = new FileOutputStream(exportFileName);
			ObjectOutputStream objectOutput = new ObjectOutputStream(fileOutput);
			logger.debug("Export: OutputStreams erzeugt.");

			objectOutput.writeInt(user.getProjects().size());
			for (Project projectToSave : user.getProjects()) {
				objectOutput.writeObject(projectToSave);
			}
			logger.debug("ExportDatei geschrieben");

			fileOutput.close();
			objectOutput.close();
			logger.debug("Projekt erfolgreich exportiert.");

		} catch (NotSerializableException e){
			logger.error("An NotSerializableException occured: "
					+ e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			logger.error("An IOException occured: " + e.getMessage());
		} 
		
		return exportFileName;
	}

}
