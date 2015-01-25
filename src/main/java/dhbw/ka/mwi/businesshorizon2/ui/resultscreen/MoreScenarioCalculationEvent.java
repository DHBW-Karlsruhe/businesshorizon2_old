package dhbw.ka.mwi.businesshorizon2.ui.resultscreen;

import com.mvplite.event.Event;

import dhbw.ka.mwi.businesshorizon2.models.Project;

public class MoreScenarioCalculationEvent extends Event {

	private static final long serialVersionUID = 1L;
	private Project project;
	
	private int anzScenarios;

	public MoreScenarioCalculationEvent(Project project, int anzScenarios) {
		this.project = project;
		this.anzScenarios = anzScenarios;
	}

	public Project getProject() {
		return project;
	}
	
	public int anzScenarios () {
		return anzScenarios;
	}

}
