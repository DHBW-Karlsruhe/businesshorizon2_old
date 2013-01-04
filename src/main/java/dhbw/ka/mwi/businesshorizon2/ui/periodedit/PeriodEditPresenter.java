package dhbw.ka.mwi.businesshorizon2.ui.periodedit;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.mvplite.event.EventBus;
import com.mvplite.event.EventHandler;
import com.mvplite.presenter.Presenter;

import dhbw.ka.mwi.businesshorizon2.models.Period;
import dhbw.ka.mwi.businesshorizon2.ui.main.ShowMainViewEvent;
import dhbw.ka.mwi.businesshorizon2.ui.periodlist.PeriodRemoveEvent;

/**
 * Dies ist der Presenter zur Bearbeitung einer Periode.
 * 
 * @author Christian Gahlert
 *
 */
public class PeriodEditPresenter extends Presenter<PeriodEditViewInteface> {
	private static final long serialVersionUID = 1L;

	@Autowired
	private EventBus eventBus;
	
	private Period currentPeriod = null;

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
	 * Dieser Event wird von dem PeriodListPresenter beim Entfernen einer Periode abgesetzt.
	 * Sollte die entfernte Periode gleich der aktuell angezeigten sein, wird zur Start-
	 * Ansicht gewechselt.
	 * 
	 * @author Christian Gahlert
	 * @param event
	 */
	@EventHandler
	public void onPeriodRemove(PeriodRemoveEvent event) {
		if(currentPeriod == event.getPeriod()) {
			eventBus.fireEvent(new ShowMainViewEvent());
		}
	}
	
	/**
	 * Dieser Event wird vom PeriodListPresenter nach Auswahl einer Methode abgesetzt. Er 
	 * uebergibt dabei der View die anzuzeigende Periode.
	 * 
	 * @author Christian Gahlert
	 * @param event
	 */
	@EventHandler
	public void onShowPeriodEdit(ShowPeriodEditEvent event) {
		currentPeriod = event.getPeriod();
		getView().setPeriod(currentPeriod);
	}
}
