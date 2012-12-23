package dhbw.ka.mwi.businesshorizon2.ui.contentcontainer;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.mvplite.event.EventBus;
import com.mvplite.event.EventHandler;
import com.mvplite.presenter.Presenter;
import dhbw.ka.mwi.businesshorizon2.ui.main.ShowNavigationStepEvent;
import dhbw.ka.mwi.businesshorizon2.ui.method.MethodView;
import dhbw.ka.mwi.businesshorizon2.ui.output.OutputView;
import dhbw.ka.mwi.businesshorizon2.ui.parameter.ParameterView;
import dhbw.ka.mwi.businesshorizon2.ui.period.PeriodView;
import dhbw.ka.mwi.businesshorizon2.ui.processing.ProcessingView;

public class ContentContainerPresenter extends Presenter<ContentContainerView> {
	private static final long serialVersionUID = 1L;

	@Autowired
	private EventBus eventBus;

	@Autowired
	private MethodView methodView;
	
	@Autowired
	private PeriodView periodView;
	
	@Autowired
	private OutputView outputView;
	
	@Autowired
	private ParameterView parameterView;
	
	@Autowired
	private ProcessingView processingView;

	
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
	public void onShowNavigationStep(ShowNavigationStepEvent event) {
		ContentView newView = null;
		
		switch(event.getStep()) {
		case METHOD:
			newView = methodView;
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
		
		getView().showContentView(newView);
	}
	
}
