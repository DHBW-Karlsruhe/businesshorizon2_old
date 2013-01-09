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
	private String password;

	protected ArrayList<Project> projects = new ArrayList<Project>();

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

	public User() {
		// TODO Auto-generated constructor stub
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
	public String getPassword() {
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
		this.password = password;
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
	 * Fügt ein Projekt der Liste hinzu
	 * 
	 * @author Christian Scherer
	 * @param das
	 *            hinzuzufügende Projekt
	 */
	public void addProject(Project project) {
		projects.add(project);
	}

	/**
	 * Fügt ein Projekt der Liste hinzu
	 * 
	 * @author Christian Scherer
	 * @param das
	 *            zu löschende Projekt
	 */
	public void removeProject(Project project) {
		projects.remove(project);
	}

}
