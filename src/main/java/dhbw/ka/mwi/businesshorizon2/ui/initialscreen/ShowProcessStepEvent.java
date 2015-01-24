package dhbw.ka.mwi.businesshorizon2.ui.initialscreen;

import com.mvplite.event.Event;

public class ShowProcessStepEvent extends Event {

	private static final long serialVersionUID = 1L;
	
	public enum screen {
		METHODSELECTION, PARAMETER, PERIODS, SCENARIOS, RESULT
	}
	
	private screen screen;
	private int sumScenarios;
	
	public ShowProcessStepEvent(screen screen){
		this.screen = screen;
	}
	
	public ShowProcessStepEvent (screen screen, int anz) {
		this(screen);
		this.sumScenarios = anz;
	}

	public screen getScreen() {
		return this.screen;
	}
	
	public int getSumScenarios () {
		return this.sumScenarios;
	}

}
