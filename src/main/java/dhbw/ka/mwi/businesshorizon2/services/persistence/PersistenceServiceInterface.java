package dhbw.ka.mwi.businesshorizon2.services.persistence;

import dhbw.ka.mwi.businesshorizon2.models.Project;
import dhbw.ka.mwi.businesshorizon2.models.User;

/**
 * Interface zur Persistierung von Projektdaten eines Users. Implementierende
 * Klassen können entweder die Kommunikation mit einer Datebank herstellen oder
 * Daten aus/in einer einfachen Datei lesen/schreiben.
 * 
 * @author Florian Stier
 * 
 */
public interface PersistenceServiceInterface {
	/**
	 * Methode zum Laden aller Projektdaten des angemeldeten Users.
	 * 
	 * @param user
	 *            User dessen Projektdaten geladen werden sollen
	 * @return Project Objekt mit allen Projektdaten
	 */
	public Project loadProjectsForUser(User user);

	/**
	 * Methode zum Speichern eines Projekts für einen User
	 * 
	 * @param user
	 *            der User, für den ein Projekt gespeichert werden soll
	 * @param project
	 *            das Projekt, dessen Daten gespeichert werden sollen
	 */
	public void saveProjectForUser(User user, Project project);
}
