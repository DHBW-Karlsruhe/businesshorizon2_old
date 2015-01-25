package dhbw.ka.mwi.businesshorizon2.ui.initialscreen;

import com.mvplite.event.Event;

public class ShowProcessStepEvent extends Event {

	private static final long serialVersionUID = 1L;
	
	public enum screen {
		METHODSELECTION, PARAMETER, PERIODS, SCENARIOS, RESULT
	}
	
	private screen screen;
	
	public ShowProcessStepEvent(screen screen){
		this.screen = screen;
	}

	public screen getScreen() {
		return this.screen;
	}
	
}
