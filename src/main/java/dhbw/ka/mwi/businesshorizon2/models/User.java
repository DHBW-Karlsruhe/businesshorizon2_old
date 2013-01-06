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

	protected ArrayList<Project> projects = new ArrayList<Project>();

	private String username;
	private String password;
	private String firstName;
	private String lastName;

	public User() {

	}

	public User(String username, String password, String firstName, String lastName) {
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

}
