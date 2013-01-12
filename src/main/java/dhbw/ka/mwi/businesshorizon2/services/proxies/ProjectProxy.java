package dhbw.ka.mwi.businesshorizon2.services.proxies;

import dhbw.ka.mwi.businesshorizon2.models.Project;

public class ProjectProxy {
	private Project selectedProject;
	
	public ProjectProxy() {
		super();
	}

	public Project getSelectedProject() {
		return selectedProject;
	}

	public void setSelectedProject(Project selectedProject) {
		this.selectedProject = selectedProject;
	}
	
	
}
