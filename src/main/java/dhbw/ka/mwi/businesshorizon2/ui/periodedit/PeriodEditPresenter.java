package dhbw.ka.mwi.businesshorizon2.ui.periodedit;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.mvplite.event.EventBus;
import com.mvplite.event.EventHandler;
import com.mvplite.presenter.Presenter;
import com.vaadin.data.util.BeanItem;

import dhbw.ka.mwi.businesshorizon2.models.Period;
import dhbw.ka.mwi.businesshorizon2.ui.main.ShowMainViewEvent;
import dhbw.ka.mwi.businesshorizon2.ui.periodlist.PeriodRemoveEvent;


public class PeriodEditPresenter extends Presenter<PeriodEditView> {
	private static final long serialVersionUID = 1L;

	@Autowired
	private EventBus eventBus;
	
	private Period currentPeriod = null;
	
	@PostConstruct
	public void init() {
		eventBus.addHandler(this);
	}

	public Period getCurrentPeriod() {
		return currentPeriod;
	}

	public void setCurrentPeriod(Period currentPeriod) {
		this.currentPeriod = currentPeriod;
	}
	
	@EventHandler
	public void onPeriodRemove(PeriodRemoveEvent event) {
		if(currentPeriod == event.getPeriod()) {
			eventBus.fireEvent(new ShowMainViewEvent());
		}
	}
	
	@EventHandler
	public void onShowPeriodEdit(ShowPeriodEditEvent event) {
		currentPeriod = event.getPeriod();
		getView().setPeriod(event.getPeriod());
	}
}
