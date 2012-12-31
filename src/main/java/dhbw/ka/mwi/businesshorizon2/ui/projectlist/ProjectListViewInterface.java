package dhbw.ka.mwi.businesshorizon2.ui.projectlist;

import com.mvplite.view.View;
import com.vaadin.ui.VerticalLayout;

import dhbw.ka.mwi.businesshorizon2.models.Project;

/**
 * Dieses Interface zeigt die von der View zur Verfuegung stehenden Methoden,
 * mit denen der Presenter mit der View kommunizieren kann.
 * 
 * @author Christian Scherer
 * 
 */
public interface ProjectListViewInterface extends View {

	/**
	 * Konkrete Ausprogrammierung der UI Elemente.
	 * 
	 * @author Christian Scherer
	 */
	public void generateUi();

	/**
	 * Konkrete Ausprogrammierung der der Darstellung eines einzlenen Projekts
	 * (Name, Anzahl Perioden mit Jahren, Änderungsdatum, Löschbutton). Diese
	 * wird sowohl bei der ersten Erstellung für jedes Projekt ausgeführt wie
	 * auch bei der Hinzufügung eines neuen Projekts
	 * 
	 * @author Christian Scherer
	 * @param project
	 *            das darzustellende Projekt und der aktuelle Index der Liste
	 * @return ein VerticalLayout Objekt, das zur Eingliederung in das UI dient
	 */
	public VerticalLayout generateSingleProjectUi(Project project, int i);

}
