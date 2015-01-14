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

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.NotSerializableException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import dhbw.ka.mwi.businesshorizon2.models.Project;
import dhbw.ka.mwi.businesshorizon2.models.User;

/**
 * Interface zur Persistierung von Projektdaten eines Users. Implementierende
 * Klassen können entweder die Kommunikation mit einer Datebank herstellen oder
 * Daten aus/in einer einfachen Datei lesen/schreiben.
 * 
 * @author Marcel Rosenberger, Tobias Lindner
 * 
 */
public interface PersistenceServiceInterface {
	/**
	 * Methode zum Laden aller Projektdaten des angemeldeten Users.
	 * 
	 * @author Marcel Rosenberger
	 * 
	 * @param user
	 *            User dessen Projektdaten geladen werden sollen
	 * @return Project Objekt mit allen Projektdaten
	 */
	public void loadProjects(User user);

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
	public  void addProject(User user, Project project)
			throws ProjectAlreadyExistsException;
	

	/**
	 * Methode zum Entfernen eines Projekts für einen User
	 * 
	 * @author Marcel Rosenberger
	 * @param user
	 *            der User, für den ein Projekt entfernt werden soll
	 * @param project
	 *            das Projekt,das entfernt werden sollen
	 */
	public  void removeProject(User user, Project project);
		

	/**
	 * Methode zum Speichern der bearbeiteten Projekte.
	 * 
	 * @author Marcel Rosenberger
	 *
	 */
	public  void saveProjects();
	
	/**
	 * Methode zum Importieren von Projekten.
	 * 
	 * @param user
	 * 			der akutelle User, zu dessen Projekte die Projekte importiert werden sollen.
	 * @param fileName
	 * 			der Dateiname der hochgeladen Datei
	 * @return String
	 * 			String mit den Namen der Projekt, die nicht importiert werden konnten
	 * @autor Tobias Lindner
	 */
	public String importAllProjects(User user, String fileName) ;
	
	/**
	 * Methode zum Exportieren der Projekte des aktuellen Users.
	 *
	 * @param user
	 * 			der akutelle User, dessen Projekte exportiert werden sollen.
	 * @return String
	 * 			Pfad zur erzeugten Export-Datei. Von diesem Pfad wird die Datei gedownloaded.
	 * @author Tobias Lindner
	 */
	public String exportUserProjects (User user);
		
}
