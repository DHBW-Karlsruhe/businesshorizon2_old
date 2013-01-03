package dhbw.ka.mwi.businesshorizon2.ui.initialscreen.projectlist;

import com.mvplite.event.Event;

import dhbw.ka.mwi.businesshorizon2.models.Project;

public class ProjectRemoveEvent extends Event {
	private static final long serialVersionUID = 1L;

	private Project project;

	/**
	 * Konstruktor mit dem geloeschten Projekt
	 * 
	 * @author Christian Scherer
	 * @param project
	 */
	public ProjectRemoveEvent(Project project) {
		this.project = project;
	}

	/**
	 * Getter Methode für das geloeschte Projekt
	 * 
	 * @author Christian Scherer
	 * @return das zu löschende Projekt
	 */
	public Project getProject() {
		return project;
	}
}
