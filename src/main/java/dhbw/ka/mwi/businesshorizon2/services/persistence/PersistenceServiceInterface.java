/*******************************************************************************
 * BusinessHorizon2
 * 
 *     Copyright (C) 2012-2013  Christian Gahlert, Florian Stier, Kai Westerholz,
 *     Timo Belz, Daniel Dengler, Katharina Huber, Christian Scherer, Julius Hacker
 * 
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Affero General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 * 
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Affero General Public License for more details.
 * 
 *     You should have received a copy of the GNU Affero General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/


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
