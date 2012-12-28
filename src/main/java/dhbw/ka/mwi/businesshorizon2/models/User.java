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

	/**
	 * Gibt die dem Benutzer zugehörigen Projekte zurück
	 * 
	 * @author Christian Scherer
	 * @return Die Projekte
	 */
	public ArrayList<Project> getProjects() {
		return projects;
	}

}
