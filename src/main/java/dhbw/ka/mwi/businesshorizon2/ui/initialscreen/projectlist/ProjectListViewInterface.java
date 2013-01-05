package dhbw.ka.mwi.businesshorizon2.ui.initialscreen.projectlist;

import java.util.List;

import com.mvplite.view.View;

import dhbw.ka.mwi.businesshorizon2.models.Project;


/**
 * Dieses Interface zeigt die von der View zur Verfuegung stehenden Methoden,
 * mit denen der Presenter mit der View kommunizieren kann.
 * 
 * @author Christian Scherer
 * 
 */
public interface ProjectListViewInterface extends View {


	
	public void setProjects(List<Project> projects);
	
	public void showAddProjectDialog();

}

