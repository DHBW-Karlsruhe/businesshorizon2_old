package dhbw.ka.mwi.businesshorizon2.ui.main;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.mvplite.event.EventBus;
import com.mvplite.event.EventHandler;
import com.mvplite.presenter.Presenter;

import dhbw.ka.mwi.businesshorizon2.ui.navigation.NavigationSteps;
import dhbw.ka.mwi.businesshorizon2.ui.navigation.ShowNavigationEvent;
import dhbw.ka.mwi.businesshorizon2.models.Project;

/**
 * Dieser Presenter ist das Kernstueck der Applikation. Er ist dafuer verantwortlich,
 * die jeweils anzuzeigenden Fenster korrekt in der View zu setzen. Somit ist es 
 * notwenig, dass er fuer jedes Anzuzeigende (Teil-)Fenster einen entsprechenden
 * EventHandler fuer den jeweiligen Show*Event registriert.
 * 
 * @author Christian Gahlert
 *
 */
public class MainPresenter extends Presenter<MainViewInterface>{
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private EventBus eventBus;
	
	@Autowired
	private Project project;
	
	/**
	 * Dies ist der Konstruktor, der von Spring nach der Initialierung der Dependencies 
	 * aufgerufen wird. Er registriert lediglich sich selbst als einen EventHandler.
	 * 
	 * @author Christian Gahlert
	 */
	@PostConstruct
	public void init() {
		eventBus.addHandler(this);
	}

	/**
	 * Dieser Event wird zu Beginn der Prozesssicht abgesetzt.
	 * Dabei wird oben die Navigation und unten die Methodenauswahl angezeigt.
	 * 
	 * @author Julius Hacker
	 * @param event
	 */
	@EventHandler
	public void onShowMain(ShowMainViewEvent event) {
		eventBus.fireEvent(new ShowNavigationEvent());
		eventBus.fireEvent(new ShowNavigationStepEvent(NavigationSteps.METHOD));
	}
	
}
