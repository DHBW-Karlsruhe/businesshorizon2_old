package dhbw.ka.mwi.businesshorizon2.ui.methodscreen;

import com.mvplite.view.View;

import dhbw.ka.mwi.businesshorizon2.models.Project;

public interface MethodScreenViewInterface extends View {
	
	public void setRadioValues();
	
	public void setProject(Project project);
}
