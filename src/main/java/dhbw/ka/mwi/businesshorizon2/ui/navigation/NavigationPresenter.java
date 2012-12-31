package dhbw.ka.mwi.businesshorizon2.ui.navigation;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.mvplite.event.EventBus;
import com.mvplite.presenter.Presenter;

import dhbw.ka.mwi.businesshorizon2.ui.main.ShowNavigationStepEvent;

public class NavigationPresenter extends Presenter<NavigationView> {
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
	
	public void showStep(NavigationSteps step) {
		eventBus.fireEvent(new ShowNavigationStepEvent(step));
	}
}
