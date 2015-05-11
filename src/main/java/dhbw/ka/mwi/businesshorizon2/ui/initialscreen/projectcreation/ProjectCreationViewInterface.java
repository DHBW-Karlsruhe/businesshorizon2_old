package dhbw.ka.mwi.businesshorizon2.ui.initialscreen.projectcreation;

import com.mvplite.view.View;

import dhbw.ka.mwi.businesshorizon2.ui.initialscreen.InitialScreenViewInterface;

public interface ProjectCreationViewInterface extends View {
	
	public void setInitialScreen(InitialScreenViewInterface view);

	public void showErrorMessage(String message);
	
	public void addProject(int nextStep);
	
	public void editProject(int nextStep);
	
	public void setProjectData();
	
	public void clearProjectData();

}
