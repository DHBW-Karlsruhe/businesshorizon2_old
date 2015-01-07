package dhbw.ka.mwi.businesshorizon2.ui.initialscreen.projectdetails;

import com.mvplite.view.View;

public interface ProjectDetailsViewInterface extends View {
	
	public void setProjectDetails(String projectName, String projectDetails, String projectDescription, String lastChanged);
	
	public void clearProjectDetails();

}
