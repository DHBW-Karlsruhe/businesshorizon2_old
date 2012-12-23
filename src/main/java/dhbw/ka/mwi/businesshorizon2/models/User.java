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

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String username;
	private String password;
	private String firstName;
	private String lastName;

	protected ArrayList<Project> projects = new ArrayList<Project>();

	/**
	 * Gibt die dem Benutzer zugehörigen Projekte zurück
	 * 
	 * @author Christian Scherer
	 * @return Die Projekte
	 */
	public ArrayList<Project> getProjects() {
		return projects;
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
