package dhbw.ka.mwi.businesshorizon2.ui.process.contentcontainer;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.mvplite.event.EventBus;
import com.mvplite.event.EventHandler;
import com.mvplite.presenter.Presenter;

import dhbw.ka.mwi.businesshorizon2.ui.process.InvalidStateEvent;
import dhbw.ka.mwi.businesshorizon2.ui.process.ShowErrorsOnScreenEvent;
import dhbw.ka.mwi.businesshorizon2.ui.process.ShowNavigationStepEvent;
import dhbw.ka.mwi.businesshorizon2.ui.process.ValidStateEvent;
import dhbw.ka.mwi.businesshorizon2.ui.process.ValidateContentStateEvent;
import dhbw.ka.mwi.businesshorizon2.ui.process.method.MethodViewInterface;
import dhbw.ka.mwi.businesshorizon2.ui.process.method.ShowMethodViewEvent;
import dhbw.ka.mwi.businesshorizon2.ui.process.navigation.NavigationSteps;
import dhbw.ka.mwi.businesshorizon2.ui.process.output.OutputViewInterface;
import dhbw.ka.mwi.businesshorizon2.ui.process.output.ShowOutputViewEvent;
import dhbw.ka.mwi.businesshorizon2.ui.process.parameter.ParameterViewInterface;
import dhbw.ka.mwi.businesshorizon2.ui.process.parameter.ShowParameterViewEvent;
import dhbw.ka.mwi.businesshorizon2.ui.process.period.PeriodViewInterface;
import dhbw.ka.mwi.businesshorizon2.ui.process.period.ShowPeriodViewEvent;
import dhbw.ka.mwi.businesshorizon2.ui.process.scenario.ScenarioViewInterface;
import dhbw.ka.mwi.businesshorizon2.ui.process.scenario.ShowScenarioViewEvent;

/**
 * Die Klasse ist kuemmert sich als Container darum, dass in der Prozesssicht
 * die richtige Maske angezeigt wird.
 * 
 * @author Julius Hacker
 *
 */
public class ContentContainerPresenter extends Presenter<ContentContainerView> {
	private static final long serialVersionUID = 1L;

	private Logger logger = Logger.getLogger(this.getClass());

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

	private boolean isActualViewValid = false;

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
			eventBus.fireEvent(new ShowPeriodViewEvent());
			break;
		case PARAMETER:
			newView = parameterView;
			eventBus.fireEvent(new ShowParameterViewEvent());
			break;
		case SCENARIO:
			newView = processingView;
			eventBus.fireEvent(new ShowScenarioViewEvent());
			break;
		case OUTPUT:
			newView = outputView;
			eventBus.fireEvent(new ShowOutputViewEvent());
			break;
		default:
			newView = null;
			break;
		}

		this.stepNumber = event.getStep().getNumber();

		// Feuere event, um die ScreenPresenter anzuweisen, ihren Zustand zu validieren und dem
		// User gegebenenfalls einen Fehlerhinweis zu geben
		eventBus.fireEvent(new ValidateContentStateEvent());

		getView().showContentView(newView);

		logger.debug("Prozesschritt " + event.getStep().getCaption() + " wird angezeigt");

		// (De-)Aktiviere je nachdem, ob ein vorheriger bzw. nachfolgender Prozessschritt existiert
		// die entsprechenden Buttons.
		this.switchStepButtons();
	}

	/**
	 * Diese Methode wird von der View beim Click des Weiter-Buttons aufgerufen. Sie kuemmert sich darum,
	 * den naechsten Screen zu ermitteln und zu ihm weiterzuleiten. Sie prueft hierbei, ob die Eingaben des
	 * aktuellen Screens valide sind. Sollte dies nicht der Fall sein, wird auch nicht zum naechsten Screen
	 * weitergeleitet.
	 * 
	 * @author Julius Hacker
	 */
	public void showNextStep() {
		if(this.isActualViewValid) {
			NavigationSteps actualScreen = NavigationSteps.getByNumber(this.stepNumber);
			NavigationSteps nextScreen = NavigationSteps.getByNumber(this.stepNumber + 1);
			this.eventBus.fireEvent(new ShowNavigationStepEvent(nextScreen));
			this.eventBus.fireEvent(new ShowErrorsOnScreenEvent(actualScreen));

			logger.debug("Event fuer Anzeige des Prozesschritt " + nextScreen.getCaption() + " wurde getriggert");
		}


	}

	/**
	 * Diese Methode wird von der View beim Click des Zurueck-Buttons aufgerufen. Sie kuemmert sich darum,
	 * den vorherigen Screen zu ermitteln und zu ihm weiterzuleiten.
	 * 
	 * @author Julius Hacker
	 */
	public void showPreviousStep() {
		NavigationSteps actualScreen = NavigationSteps.getByNumber(this.stepNumber);
		NavigationSteps previousScreen = NavigationSteps.getByNumber(this.stepNumber - 1);
		this.eventBus.fireEvent(new ShowNavigationStepEvent(previousScreen));
		this.eventBus.fireEvent(new ShowErrorsOnScreenEvent(actualScreen));

		logger.debug("Event fuer Anzeige des Prozesschritt " + previousScreen.getCaption() + " wurde getriggert");


	}

	/**
	 * Diese Methode aktualisiert die Aktivierungszustaende der Weiter- und Zurueck-Buttons. Dies ist insofern
	 * relevant, als das im ersten Screen nicht noch weiter zurueck und im letzten Screen nicht noch weiter gegangen
	 * werden kann.
	 * 
	 * @author Julius Hacker
	 */
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

	/**
	 * Diese Methode hoert auf InvalidStateEvents, die bei der Validierung der Screens erzeugt werden.
	 * Kommt ein solches InvalidStateEvent fuer den aktuellen Screen an, wird intern vermerkt, dass der
	 * Screen derzeit invalid ist. Dies ist fuer den Weiter-Button wichtig, der nur zum naechsten Screen
	 * weiterleiten soll, wenn die aktuelle View valid ist.
	 * 
	 * @param event Das gefeuerte InvalidStateEvent
	 * @author Julius Hacker
	 */
	@EventHandler
	public void handleInvalidState(InvalidStateEvent event) {
		if(event.getNavigationStep() == NavigationSteps.getByNumber(this.stepNumber)) {
			this.isActualViewValid = false;
		}
	}

	/**
	 * Diese Methode hoert auf ValidStateEvents, die bei der Validierung der Screens erzeugt werden.
	 * Kommt ein solches ValidStateEvent fuer den aktuellen Screen an, wird intern vermerkt, dass der
	 * Screen derzeit valid ist. Dies ist fuer den Weiter-Button wichtig, der nur zum naechsten Screen
	 * weiterleiten soll, wenn die aktuelle View valid ist.
	 * 
	 * @param event Das gefeuerte ValidStateEvent
	 * @author Julius Hacker
	 */
	@EventHandler
	public void handleValidState(ValidStateEvent event) {
		logger.debug("ValidStateEvent fuer Screen " + event.getNavigationStep() + " empfangen. Aktueller Screen: " + this.stepNumber);
		if(event.getNavigationStep() == NavigationSteps.getByNumber(this.stepNumber)) {
			this.isActualViewValid = true;
		}
	}
}