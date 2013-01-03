package dhbw.ka.mwi.businesshorizon2.ui.process;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.mvplite.event.EventBus;
import com.mvplite.event.EventHandler;
import com.mvplite.presenter.Presenter;

import dhbw.ka.mwi.businesshorizon2.ui.process.contentcontainer.ContentContainerView;
import dhbw.ka.mwi.businesshorizon2.ui.process.navigation.NavigationSteps;
import dhbw.ka.mwi.businesshorizon2.ui.process.navigation.NavigationViewInterface;
import dhbw.ka.mwi.businesshorizon2.ui.process.navigation.ShowNavigationEvent;
import dhbw.ka.mwi.businesshorizon2.models.Project;

/**
 * Dieser Presenter ist das Kernstueck der Prozesssicht. Er ist dafuer verantwortlich,
 * die Navigation sowie die gerade ausgewaehlte Maske anzuzeigen. 
 * @author Julius Hacker
 *
 */
public class ProcessPresenter extends Presenter<ProcessViewInterface>{
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private EventBus eventBus;
	
	@Autowired
	private Project project;
	
	@Autowired
	private NavigationViewInterface navigationView;
	
	@Autowired
	private ContentContainerView contentContainerView;
	
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

	/**
	 * Dieser Event wird zu Beginn der Prozesssicht abgesetzt.
	 * Dabei wird oben die Navigation und unten die Methodenauswahl angezeigt.
	 * 
	 * @author Julius Hacker
	 * @param event
	 */
	@EventHandler
	public void onShowProcess(ShowProcessViewEvent event) {
		getView().showView(navigationView, contentContainerView);
		eventBus.fireEvent(new ShowNavigationEvent());
		eventBus.fireEvent(new ShowNavigationStepEvent(NavigationSteps.METHOD));
	}
	
}
