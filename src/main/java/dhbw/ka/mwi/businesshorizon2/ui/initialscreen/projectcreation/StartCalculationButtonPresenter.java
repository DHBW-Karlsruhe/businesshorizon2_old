package dhbw.ka.mwi.businesshorizon2.ui.initialscreen.projectcreation;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.mvplite.event.EventBus;
import com.mvplite.presenter.Presenter;

import dhbw.ka.mwi.businesshorizon2.ui.initialscreen.ShowProcessStepEvent;
import dhbw.ka.mwi.businesshorizon2.ui.initialscreen.ShowProcessStepEvent.screen;

/**
 * Dieser Presenter ist für die Darstellung des Projekterstellungs Screens zuständig.
 * Der Projekterstellungs Screen wird auch für das Bearbeiten eines Projektes verwendet.
 *
 * @author Marco Glaser
 */
public class StartCalculationButtonPresenter extends Presenter<StartCalculationButtonViewInterface>{

	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger("InitialScreenPresenter.class");

	@Autowired
	private EventBus eventBus;
	
	/**
	 * Dies ist der Konstruktor, der von Spring nach der Initialierung der
	 * Dependencies aufgerufen wird. Er registriert lediglich sich selbst als
	 * einen EventHandler.
	 *
	 * @author Marco Glaser
	 */
	@PostConstruct
	public void init(){
//		eventBus.addHandler(this);
		logger.debug("Eventhandler Hinzugefügt");
		
	}

}
