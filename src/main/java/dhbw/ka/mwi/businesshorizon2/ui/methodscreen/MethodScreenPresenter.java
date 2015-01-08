package dhbw.ka.mwi.businesshorizon2.ui.methodscreen;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.mvplite.event.EventBus;
import com.mvplite.presenter.Presenter;

public class MethodScreenPresenter extends Presenter<MethodScreenViewInterface>{

	private static final long serialVersionUID = 1L;
	
	private static final Logger logger = Logger.getLogger("MethodScreenPresenter.class");
	
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
