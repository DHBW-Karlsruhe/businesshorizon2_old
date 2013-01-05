package dhbw.ka.mwi.businesshorizon2.ui.process.contentcontainer;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.mvplite.event.EventBus;
import com.mvplite.event.EventHandler;
import com.mvplite.presenter.Presenter;

import dhbw.ka.mwi.businesshorizon2.ui.process.ShowNavigationStepEvent;
import dhbw.ka.mwi.businesshorizon2.ui.process.method.MethodViewInterface;

import dhbw.ka.mwi.businesshorizon2.ui.process.method.ShowMethodViewEvent;

import dhbw.ka.mwi.businesshorizon2.ui.process.navigation.NavigationSteps;

import dhbw.ka.mwi.businesshorizon2.ui.process.output.OutputViewInterface;
import dhbw.ka.mwi.businesshorizon2.ui.process.parameter.ParameterViewInterface;
import dhbw.ka.mwi.businesshorizon2.ui.process.period.PeriodViewInterface;
import dhbw.ka.mwi.businesshorizon2.ui.process.scenario.ScenarioViewInterface;

/**
 * Die Klasse ist kuemmert sich als Container darum, dass in der Prozesssicht
 * die richtige Maske angezeigt wird.
 * 
 * @author Julius Hacker
 *
 */
public class ContentContainerPresenter extends Presenter<ContentContainerView> {
	private static final long serialVersionUID = 1L;

	private Logger logger = Logger.getLogger("ContentContainerPresenter.class");

	@Autowired
	private EventBus eventBus;
	
	@Autowired
	private PeriodViewInterface periodView;
	
	@Autowired
	private OutputViewInterface outputView;
	
	@Autowired
	private ParameterViewInterface parameterView;
	
	@Autowired
	private ScenarioViewInterface processingView;

	@Autowired
	private MethodViewInterface methodView;

	private int stepNumber;
	
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
	 * Diese Methode faengt die ShownavigationStepEvents ab und kuemmert sich darum,
	 * dass die entsprechende Maske angezeigt wird.
	 * 
	 * @param event Der ausgeloeste ShowNavigationStepEvent
	 * @autor Julius Hacker
	 */
	@EventHandler
	public void onShowNavigationStep(ShowNavigationStepEvent event) {
		ContentView newView = null;
		
		switch(event.getStep()) {
		case METHOD:
			newView = methodView;
			eventBus.fireEvent(new ShowMethodViewEvent());
			break;
		case PERIOD:
			newView = periodView;
			break;
		case PARAMETER:
			newView = parameterView;
			break;
		case SCENARIO:
			newView = processingView;
			break;
		case OUTPUT:
			newView = outputView;
			break;
		default:
			newView = null;
			break;
		}
		
		this.stepNumber = event.getStep().getNumber();
		
		getView().showContentView(newView);
		
		logger.debug("Prozesschritt " + event.getStep().getCaption() + " wird angezeigt");
		
		this.switchStepButtons();
	}
	
	public void showNextStep() {
		NavigationSteps nextScreen = NavigationSteps.getByNumber(this.stepNumber + 1);
		this.eventBus.fireEvent(new ShowNavigationStepEvent(nextScreen));
		
		logger.debug("Event fuer Anzeige des Prozesschritt " + nextScreen.getCaption() + " wurde getriggert");
	
		
	}
	
	public void showPreviousStep() {
		NavigationSteps previousScreen = NavigationSteps.getByNumber(this.stepNumber - 1);
		this.eventBus.fireEvent(new ShowNavigationStepEvent(previousScreen));
		
		logger.debug("Event fuer Anzeige des Prozesschritt " + previousScreen.getCaption() + "wurde getriggert");
	
		
	}
	
	public void switchStepButtons() {
		logger.debug(this.stepNumber);
		logger.debug(NavigationSteps.getStepCount());
		
		if(this.stepNumber < 2) {
			getView().activateBack(false);
		}
		else {
			getView().activateBack(true);
		}
		
		if(this.stepNumber >= NavigationSteps.getStepCount()) {
			getView().activateNext(false);
		}
		else {
			getView().activateNext(true);
		}
	}
}
