package dhbw.ka.mwi.businesshorizon2.ui.process.navigation;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.mvplite.event.EventBus;
import com.mvplite.event.EventHandler;
import com.mvplite.presenter.Presenter;

import dhbw.ka.mwi.businesshorizon2.ui.process.InvalidStateEvent;
import dhbw.ka.mwi.businesshorizon2.ui.process.ScreenSelectableEvent;
import dhbw.ka.mwi.businesshorizon2.ui.process.ShowNavigationStepEvent;
import dhbw.ka.mwi.businesshorizon2.ui.process.ValidStateEvent;

/**
 * Der Presenter zur Navigation der Prozessansicht.
 * 
 * @author Julius Hacker
 *
 */
public class NavigationPresenter extends Presenter<NavigationViewInterface> {
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
		eventBus.addHandler(this);
	}
	
	@EventHandler
	public void showNavigation(ShowNavigationEvent event) {
		//getView().setButtonActive(NavigationSteps.METHOD, true);
	}
	
	/**
	 * Diese Methode wird von der View nach der Auswahl einer neuen Prozessmaske aufgerufen.
	 * Sie feuert hierzu ein ShowNavigationStepEvent mit dem entsprechenden Prozessschritt.
	 * 
	 * @param step Anzuzeigende Prozessmaske
	 */
	public void showStep(NavigationSteps step) {
		eventBus.fireEvent(new ShowNavigationStepEvent(step));
	}
	
	@EventHandler
	public void handleInvalidState(InvalidStateEvent event) {
		if(event.isShowErrors()) {
			getView().setButtonToInvalid(event.getNavigationStep(), true);
		}
	}
	
	@EventHandler
	public void handleValidState(ValidStateEvent event) {
		getView().setButtonToInvalid(event.getNavigationStep(), false);
	}
	
	@EventHandler
	public void handleScreenSelectable(ScreenSelectableEvent event) {
		getView().setButtonActive(event.getNavigationStep(), event.isSelectable());
	}
}
