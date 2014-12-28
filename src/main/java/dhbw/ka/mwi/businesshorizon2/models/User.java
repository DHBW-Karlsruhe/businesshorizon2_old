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

package dhbw.ka.mwi.businesshorizon2.models;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Das User Objekt beinhaltet die für Loginzwecke zu speichernden Daten wie auch
 * seine zugehörigen Projekte
 * 
 * @author Christian Scherer
 * 
 */
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	private String firstName;
	private String lastName;
	private String company;
	private String emailAdress;
	private int password;

	protected ArrayList<Project> projects = new ArrayList<Project>();

	public User(){
		
	}
	
	/**
	 * Konstruktor des User Objekts
	 * 
	 * @author Christian Scherer
	 */
	public User(String firstName, String lastName, String company,
			String emailAdress, String password) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.company = company;
		this.emailAdress = emailAdress;
		this.setPassword(password);
	}

	/**
	 * Gibt den Vorname des User-Objekts zurück
	 * 
	 * @author Christian Scherer
	 * @return Vorname des Users
	 */
	public String getFirstName() {
		return this.firstName;
	}

	/**
	 * Setzt den Vornamen des User-Objekts
	 * 
	 * @author Christian Scherer
	 * @param firstName
	 *            Vornamen des Users
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Gibt den Nachname des User-Objekts zurück
	 * 
	 * @author Christian Scherer
	 * @return Nachname des Users
	 */
	public String getLastName() {
		return this.lastName;
	}

	/**
	 * Setzt den Nachnamen des User-Objekts
	 * 
	 * @author Christian Scherer
	 * @param lastName
	 *            Nachname des Users
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Gibt den kompletten Namen des Users zurück
	 * 
	 * @author Mirko Göpfrich
	 * @return Name des Users
	 */
	public String getFullName() {
		return this.firstName + " " + this.lastName;
	}
	
	/**
	 * Gibt das Unternehmen des User-Objekts zurück
	 * 
	 * @author Christian Scherer
	 * @return Unternehmen des Users
	 */
	public String getCompany() {
		return this.company;
	}

	/**
	 * Setzt das Unternehmen des User-Objekts
	 * 
	 * @author Christian Scherer
	 * @param company
	 *            Unternehmen des Users
	 */
	public void setCompany(String company) {
		this.company = company;
	}

	/**
	 * Gibt die Emailadresse des User-Objekts zurück
	 * 
	 * @author Christian Scherer
	 * @return Emailadresse des Users
	 */
	public String getEmailAdress() {
		return this.emailAdress;
	}

	/**
	 * Setzt die Emailadresse des User-Objekts
	 * 
	 * @author Christian Scherer
	 * @param emailAdress
	 *            Emailadresse des Users
	 */
	public void setEmailAdress(String emailAdress) {
		this.emailAdress = emailAdress;
	}

	/**
	 * Gibt das Passwort des User-Objekts zurück
	 * 
	 * @author Christian Scherer
	 * @return Passwort des Users
	 */
	public int getPassword() {
		return password;
	}

	/**
	 * Setzt das Passwort des User-Objekts
	 * 
	 * @author Christian Scherer
	 * @param password
	 *            Passwort des Users
	 */
	public void setPassword(String password) {
		this.password = password.hashCode();
	}

	/**
	 * Gibt die dem Benutzer zugehörigen Projekte zurück
	 * 
	 * @author Christian Scherer
	 * @return Die Projekte des Anwenders
	 */
	public ArrayList<Project> getProjects() {
		return projects;
	}
	
	/**
	 * Überschreibt die Nutzerprojekt-Liste
	 * 
	 * @author Marcel Rosenberger
	 * @param die neue Liste
	 */
	public void setProjects(ArrayList<Project> userProjects) {
		this.projects = userProjects;
	}
	

}
