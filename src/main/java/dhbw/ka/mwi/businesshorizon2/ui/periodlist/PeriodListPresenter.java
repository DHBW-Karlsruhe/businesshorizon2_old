package dhbw.ka.mwi.businesshorizon2.ui.periodlist;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.mvplite.event.EventBus;
import com.mvplite.event.EventHandler;
import com.mvplite.presenter.Presenter;

import dhbw.ka.mwi.businesshorizon2.models.Period;
import dhbw.ka.mwi.businesshorizon2.models.Project;
import dhbw.ka.mwi.businesshorizon2.ui.method.ShowMethodEvent;
import dhbw.ka.mwi.businesshorizon2.ui.periodedit.ShowPeriodEditEvent;

public class PeriodListPresenter extends Presenter<PeriodListView> {
	private static final long serialVersionUID = 1L;

	@Autowired
	private EventBus eventBus;
	
	@Autowired
	private Project project;
	
	private Period currentPeriod = null;
	
	@PostConstruct
	private void init() {
		eventBus.addHandler(this);
	}
	
	public void updateShowAddButton() {
		if(project.getAvailableYears().size() > 0) {
			getView().setShowAddPeriodButton(true);
		} else {
			getView().setShowAddPeriodButton(false);
		}
	}
	
	public void addPeriod(int year) {
		Period period = new Period(year);
		project.getPeriods().add(period);
		
		eventBus.fireEvent(new PeriodAddEvent(period));
		
		currentPeriod = period;
		updatePeriods();
		updateShowAddButton();
	}

	public void removePeriod(Period period) {
		project.getPeriods().remove(period);
		eventBus.fireEvent(new PeriodRemoveEvent(period));
		
		currentPeriod = null;
		updatePeriods();
		updateShowAddButton();
	}
	
	private void updatePeriods() {
		getView().setAvailableYears(project.getAvailableYears());
		getView().setPeriods(project.getPeriods(), currentPeriod);
	}

	public void selectPeriod(Period period) {
		currentPeriod = period;
		eventBus.fireEvent(new ShowPeriodEditEvent(period));
	}
	
	@EventHandler
	public void onShowPeriodList(ShowPeriodListEvent event) {
		updatePeriods();
	}
	
	@EventHandler
	public void onShowMethod(ShowMethodEvent event) {
		currentPeriod = null;
		updatePeriods();
	}
	
}
