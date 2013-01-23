package dhbw.ka.mwi.businesshorizon2.tests.ui.initialscreen.projectlist.assets;

import java.util.List;

import dhbw.ka.mwi.businesshorizon2.models.Project;
import dhbw.ka.mwi.businesshorizon2.ui.initialscreen.projectlist.ProjectListViewInterface;

public class ProjectListViewMock implements ProjectListViewInterface {

	private List<Project> projects;
	private boolean projectAddDialogShown = false;

	public boolean isProjectAddDialogShown() {
		return projectAddDialogShown;
	}


	@Override
	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}

	
	
	public List<Project> getProjects() {
		return projects;
	}



	@Override
	public void showAddProjectDialog() {

		projectAddDialogShown  = true;
	}

}
