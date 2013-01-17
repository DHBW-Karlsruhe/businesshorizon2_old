package dhbw.ka.mwi.businesshorizon2.ui.process.period;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.mvplite.event.EventBus;
import com.mvplite.event.EventHandler;
import com.mvplite.view.View;

import dhbw.ka.mwi.businesshorizon2.models.Project;
import dhbw.ka.mwi.businesshorizon2.ui.process.ScreenPresenter;
import dhbw.ka.mwi.businesshorizon2.ui.process.ScreenSelectableEvent;
import dhbw.ka.mwi.businesshorizon2.ui.process.ShowErrorsOnScreenEvent;
import dhbw.ka.mwi.businesshorizon2.ui.process.ValidStateEvent;
import dhbw.ka.mwi.businesshorizon2.ui.process.ValidateContentStateEvent;
import dhbw.ka.mwi.businesshorizon2.ui.process.navigation.NavigationSteps;
import dhbw.ka.mwi.businesshorizon2.ui.process.period.input.ShowDirektViewEvent;
import dhbw.ka.mwi.businesshorizon2.ui.process.period.input.ShowGesamtViewEvent;
import dhbw.ka.mwi.businesshorizon2.ui.process.period.input.ShowUmsatzViewEvent;
import dhbw.ka.mwi.businesshorizon2.ui.process.period.input.direkt.DirektViewInterface;
import dhbw.ka.mwi.businesshorizon2.ui.process.period.input.gesamt.GesamtViewInterface;
import dhbw.ka.mwi.businesshorizon2.ui.process.period.input.umsatz.UmsatzViewInterface;
import dhbw.ka.mwi.businesshorizon2.ui.process.period.timeline.TimelineViewInterface;

/**
 * Der Presenter fuer die Maske des Prozessschrittes zur Eingabe der Perioden.
 * 
 * @author Daniel Dengler
 *
 */

public class PeriodPresenter extends ScreenPresenter<PeriodViewInterface> {
	private static final long serialVersionUID = 1L;

	


	
	private View currentInput = null;

	@Autowired
	private TimelineViewInterface timelineView;
	
	@Autowired
	private UmsatzViewInterface umsatzView;
	
	@Autowired
	private DirektViewInterface direktView;
	
	@Autowired
	private GesamtViewInterface gesamtView;
	
	@Autowired
	private EventBus eventBus;
	
	@Autowired
	private Project project;
	
	private Logger logger = Logger.getLogger(PeriodPresenter.class);
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
	public void onShowEvent(ShowPeriodViewEvent event){
		logger.debug("DirektVieEvent gefeuert");
		getView().showView(timelineView, currentInput);
	}
	
	@EventHandler
	public void onShowEvent(ShowGesamtViewEvent event){
		currentInput = gesamtView;
		getView().showView(timelineView, currentInput);
	}
	
	@EventHandler
	public void onShowEvent(ShowDirektViewEvent event){
		logger.debug("ShowDirektViewEvent erhalten");
		currentInput = direktView;
		getView().showView(timelineView,currentInput);
	}
	
	@EventHandler
	public void onShowEvent(ShowUmsatzViewEvent event){
		currentInput = umsatzView;
		getView().showView(timelineView, currentInput);
	}

	@Override
	public boolean isValid() {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	@EventHandler
	public void validate(ValidateContentStateEvent event) {
		eventBus.fireEvent(new ValidStateEvent(NavigationSteps.PERIOD));
		logger.debug("Presenter valid, ValidStateEvent fired");
	}
	
	@EventHandler
	public void handleShowView(ShowPeriodViewEvent event) {
		eventBus.fireEvent(new ScreenSelectableEvent(NavigationSteps.PERIOD, true));
		logger.debug("ShowPeriodViewEvent handled");
	}


	@Override
	public void handleShowErrors(ShowErrorsOnScreenEvent event) {
		// TODO Auto-generated method stub
		
	}
	
}
