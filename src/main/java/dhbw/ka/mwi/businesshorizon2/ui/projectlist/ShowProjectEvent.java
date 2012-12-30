package dhbw.ka.mwi.businesshorizon2.ui.projectlist;

import com.mvplite.event.Event;

import dhbw.ka.mwi.businesshorizon2.models.Project;

/**
 * Der Event, der zum Auswählen eins Projekts der vom ProjectListPresenter
 * abgesetzt wird. Es aknn danach im Wizard mit den Daten des Projekts
 * gearbeitet werden.
 * 
 * 
 * @author Christian Scherer
 * 
 */
public class ShowProjectEvent extends Event {
	private static final long serialVersionUID = 1L;

	private Project Project;

	/**
	 * Konstruktor für das anzuzeigende Projekt.
	 * 
	 * @author Christian Scherer
	 * @param Project
	 */
	public ShowProjectEvent(Project Project) {
		this.Project = Project;
	}

	/**
	 * Getter Methode für das anzuzeigende Projekt.
	 * 
	 * @author Christian Scherer
	 * @return
	 */
	public Project getProject() {
		return Project;
	}
}
