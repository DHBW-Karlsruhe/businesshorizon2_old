package dhbw.ka.mwi.businesshorizon2.ui.process.parameter;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.mvplite.event.EventBus;
import com.mvplite.event.EventHandler;

import dhbw.ka.mwi.businesshorizon2.ui.process.ScreenPresenter;
import dhbw.ka.mwi.businesshorizon2.ui.process.ScreenSelectableEvent;
import dhbw.ka.mwi.businesshorizon2.ui.process.ShowErrorsOnScreenEvent;
import dhbw.ka.mwi.businesshorizon2.ui.process.ValidStateEvent;
import dhbw.ka.mwi.businesshorizon2.ui.process.ValidateContentStateEvent;
import dhbw.ka.mwi.businesshorizon2.ui.process.navigation.NavigationSteps;

/**
 * Der Presenter fuer die Maske des Prozessschrittes zur Eingabe der Parameter.
 * 
 * @author Julius Hacker
 * 
 */

public class ParameterPresenter extends ScreenPresenter<ParameterViewInterface> {
	private static final long serialVersionUID = 1L;

	private Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private EventBus eventBus;

	/**
	 * Dies ist der Konstruktor, der von Spring nach der Initialierung der
	 * Dependencies aufgerufen wird. Er registriert lediglich sich selbst als
	 * einen EventHandler.
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
	public void validate(ValidateContentStateEvent event) {
		eventBus.fireEvent(new ValidStateEvent(NavigationSteps.PARAMETER));
	}

	@EventHandler
	public void handleShowView(ShowParameterViewEvent event) {
		eventBus.fireEvent(new ScreenSelectableEvent(NavigationSteps.PARAMETER, true));
		logger.debug("ShowParameterViewEvent handled");
	}

	@Override
	public void handleShowErrors(ShowErrorsOnScreenEvent event) {
		// TODO Auto-generated method stub

	}
}
