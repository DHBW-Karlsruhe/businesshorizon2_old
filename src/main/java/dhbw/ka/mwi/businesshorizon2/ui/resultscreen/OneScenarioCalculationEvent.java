package dhbw.ka.mwi.businesshorizon2.ui.resultscreen;

import com.mvplite.event.Event;

import dhbw.ka.mwi.businesshorizon2.models.Project;

public class OneScenarioCalculationEvent extends Event {
	
	private static final long serialVersionUID = 1L;
	private Project project;

	public OneScenarioCalculationEvent(Project project) {
		this.project = project;
	}
	
	public Project getProject() {
		return project;
	}

}
