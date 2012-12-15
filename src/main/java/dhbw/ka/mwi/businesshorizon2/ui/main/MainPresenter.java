package dhbw.ka.mwi.businesshorizon2.ui.main;

import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.mvplite.event.EventBus;
import com.mvplite.event.EventHandler;
import com.mvplite.presenter.Presenter;
import com.vaadin.ui.Label;

import dhbw.ka.mwi.businesshorizon2.models.Period;
import dhbw.ka.mwi.businesshorizon2.models.Project;
import dhbw.ka.mwi.businesshorizon2.ui.method.MethodView;
import dhbw.ka.mwi.businesshorizon2.ui.method.MethodViewImpl;
import dhbw.ka.mwi.businesshorizon2.ui.method.ShowMethodEvent;
import dhbw.ka.mwi.businesshorizon2.ui.methodlist.MethodListView;
import dhbw.ka.mwi.businesshorizon2.ui.methodlist.MethodListViewImpl;
import dhbw.ka.mwi.businesshorizon2.ui.methodlist.ShowMethodListEvent;
import dhbw.ka.mwi.businesshorizon2.ui.periodedit.PeriodEditView;
import dhbw.ka.mwi.businesshorizon2.ui.periodedit.PeriodEditViewImpl;
import dhbw.ka.mwi.businesshorizon2.ui.periodedit.ShowPeriodEditEvent;
import dhbw.ka.mwi.businesshorizon2.ui.periodlist.PeriodListView;
import dhbw.ka.mwi.businesshorizon2.ui.periodlist.PeriodListViewImpl;
import dhbw.ka.mwi.businesshorizon2.ui.periodlist.ShowPeriodListEvent;

public class MainPresenter extends Presenter<MainView>{
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private EventBus eventBus;
	
	@Autowired
	private Project project;
	
	@Autowired
	private MethodListView methodListView;
	
	@Autowired
	private PeriodListView periodListView;
	
	@Autowired
	private PeriodEditView periodEditView;
	
	@Autowired
	private MethodView methodView;
	
	@PostConstruct
	public void init() {
		eventBus.addHandler(this);
	}

	@EventHandler
	public void onShowMain(ShowMainViewEvent event) {
		getView().showView(periodListView, methodListView, null);
		eventBus.fireEvent(new ShowMethodListEvent());
		eventBus.fireEvent(new ShowPeriodListEvent());
	}
	
	@EventHandler
	public void onShowPeriodEdit(ShowPeriodEditEvent event) {
		getView().showView(periodListView, methodListView, periodEditView);
	}
	
	@EventHandler
	public void onShowMethod(ShowMethodEvent event) {
		getView().showView(periodListView, methodListView, methodView);
	}
	
}
