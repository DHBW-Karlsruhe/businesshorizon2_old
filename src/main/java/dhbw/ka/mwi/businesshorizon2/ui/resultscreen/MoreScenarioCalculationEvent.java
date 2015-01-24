package dhbw.ka.mwi.businesshorizon2.ui.resultscreen;

import com.mvplite.event.Event;

import dhbw.ka.mwi.businesshorizon2.models.Project;

public class MoreScenarioCalculationEvent extends Event {

	private static final long serialVersionUID = 1L;
	private Project project;

	public MoreScenarioCalculationEvent(Project project) {
		this.project = project;
	}

	public Project getProject() {
		return project;
	}

}
