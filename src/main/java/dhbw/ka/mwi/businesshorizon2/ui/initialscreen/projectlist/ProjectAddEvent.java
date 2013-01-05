package dhbw.ka.mwi.businesshorizon2.ui.initialscreen.projectlist;

import com.mvplite.event.Event;
import dhbw.ka.mwi.businesshorizon2.models.Project;

/**
 * Der Event, der nach dem Hinzufuegen eines Perojekts abgesetzt wird. Aktuell
 * gibt es dafuer noch keinen EventHandler.
 * 
 * @author Christian Scherer
 * 
 */
public class ProjectAddEvent extends Event {
	private static final long serialVersionUID = 1L;

	private Project project;

	/**
	 * Konstruktor für das hinzugefuegte Projekt
	 * 
	 * @author Christian Scherer
	 * @param project
	 */
	public ProjectAddEvent(Project project) {
		this.project = project;
	}

	/**
	 * Getter Methode für das hinzugefuegte Projekt
	 * 
	 * @author Christian Scherer
	 * @return
	 */
	public Project getProject() {
		return project;
	}
}
