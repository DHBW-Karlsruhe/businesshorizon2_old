package dhbw.ka.mwi.businesshorizon2.ui.process.period.input.gesamt;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.mvplite.event.EventBus;
import com.mvplite.event.EventHandler;

import dhbw.ka.mwi.businesshorizon2.models.Period.AggregateCostMethodPeriod;
import dhbw.ka.mwi.businesshorizon2.ui.process.ScreenPresenter;
import dhbw.ka.mwi.businesshorizon2.ui.process.ShowErrorsOnScreenEvent;
import dhbw.ka.mwi.businesshorizon2.ui.process.ValidateContentStateEvent;
import dhbw.ka.mwi.businesshorizon2.ui.process.period.input.ShowGesamtViewEvent;

/**
 * Der Presenter fuer die Maske des Prozessschrittes zur Eingabe der Perioden.
 * 
 * @author Julius Hacker
 *
 */

public class GesamtPresenter extends ScreenPresenter<GesamtViewInterface> {
	private static final long serialVersionUID = 1L;
	Logger logger = Logger.getLogger(GesamtPresenter.class);
	@Autowired
	EventBus eventBus;
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
	public void onShowEvent(ShowGesamtViewEvent event){
		logger.debug("ShowGesantViewEvent erhalten");
		AggregateCostMethodPeriod period = event.getPeriod();
		
		getView().setForm(period);
		
	}


	@Override
	public boolean isValid() {
		// TODO Auto-generated method stub
		return false;
	}




	@Override
	public void validate(ValidateContentStateEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleShowErrors(ShowErrorsOnScreenEvent event) {
		// TODO Auto-generated method stub
		
	}
	
}
