package dhbw.ka.mwi.businesshorizon2.ui.process.scenario;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.mvplite.event.EventBus;

import dhbw.ka.mwi.businesshorizon2.ui.process.ScreenPresenter;
import dhbw.ka.mwi.businesshorizon2.ui.process.scenario.ScenarioViewInterface;

/**
 * Der Presenter fuer die Maske des Prozessschrittes zur Eingabe des Berechnungsszenarios.
 * 
 * @author Julius Hacker
 *
 */

public class ScenarioPresenter extends ScreenPresenter<ScenarioViewInterface> {
	private static final long serialVersionUID = 1L;

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
	}

	@Override
	public boolean isValid() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isSelectable() {
		// TODO Auto-generated method stub
		return false;
	}
	
}
