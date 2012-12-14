package dhbw.ka.mwi.businesshorizon2.periodlist;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.mvplite.event.EventBus;
import com.mvplite.event.EventHandler;
import com.mvplite.presenter.Presenter;

import dhbw.ka.mwi.businesshorizon2.models.Period;
import dhbw.ka.mwi.businesshorizon2.models.Project;

public class PeriodListPresenter extends Presenter<PeriodListView> {
	private static final long serialVersionUID = 1L;

	@Autowired
	private EventBus eventBus;
	
	@Autowired
	private Project project;
	
	private PeriodListView view;
	
	private Period currentPeriod = null;
	
	@PostConstruct
	private void init() {
		eventBus.addHandler(this);
	}
	
	public void setView(PeriodListView view) {
		this.view = view;
	}

	public void updateShowAddButton() {
		if(project.getAvailableYears().size() > 0) {
			view.setShowAddPeriodButton(true);
		} else {
			view.setShowAddPeriodButton(false);
		}
	}
	
	public void addPeriod(int year) {
		Period period = new Period(year);
		project.getPeriods().add(period);
		eventBus.fireEvent(new PeriodAddEvent(period));
		setCurrentPeriod(period);
		updatePeriods(period);
		updateShowAddButton();
	}
	
	public Period getCurrentPeriod() {
		return currentPeriod;
	}

	public void setCurrentPeriod(Period currentPeriod) {
		this.currentPeriod = currentPeriod;
	}

	public List<Integer> getAvailableYears() {
		return project.getAvailableYears();
	}

	public void removePeriod(Period period) {
		project.getPeriods().remove(period);
		eventBus.fireEvent(new PeriodRemoveEvent(period));
		
		if(currentPeriod == period) {
			setCurrentPeriod(null);
		}
		
		updatePeriods(null);
		updateShowAddButton();
	}
	
	public void updatePeriods(Period selected) {
		view.setPeriods(project.getPeriods(), selected);
	}
	
	@EventHandler
	public void onShowPeriodList(ShowPeriodListEvent event) {
		updatePeriods(null);
	}
	
}
