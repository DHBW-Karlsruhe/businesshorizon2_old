package dhbw.ka.mwi.businesshorizon2.ui.process.period;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.mvplite.event.EventBus;
import com.mvplite.event.EventHandler;

import dhbw.ka.mwi.businesshorizon2.ui.process.ScreenPresenter;
import dhbw.ka.mwi.businesshorizon2.ui.process.ScreenSelectableEvent;
import dhbw.ka.mwi.businesshorizon2.ui.process.ValidateContentStateEvent;
import dhbw.ka.mwi.businesshorizon2.ui.process.navigation.NavigationSteps;
import dhbw.ka.mwi.businesshorizon2.ui.process.parameter.ShowParameterViewEvent;
import dhbw.ka.mwi.businesshorizon2.ui.process.period.PeriodViewInterface;

/**
 * Der Presenter fuer die Maske des Prozessschrittes zur Eingabe der Perioden.
 * 
 * @author Julius Hacker
 *
 */

public class PeriodPresenter extends ScreenPresenter<PeriodViewInterface> {
	private static final long serialVersionUID = 1L;
	
	private Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private EventBus eventBus;

	
	/**
	 * Dies ist der Konstruktor, der von Spring nach der Initialierung der Dependencies 
	 * aufgerufen wird. Er registriert lediglich sich selbst als einen EventHandler.
	 * 
	 * @author Julius Hacker
	 */
	@PostConstruct
	public void init() {
		eventBus.addHandler(this);
	}


	@Override
	public boolean isValid() {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean isNextScreenSelectable() {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public void validate(ValidateContentStateEvent event) {
		// TODO Auto-generated method stub
		
	}
	
	@EventHandler
	public void handleShowView(ShowPeriodViewEvent event) {
		eventBus.fireEvent(new ScreenSelectableEvent(NavigationSteps.PERIOD, true));
		logger.debug("ShowPeriodViewEvent handled");
	}
	
}
