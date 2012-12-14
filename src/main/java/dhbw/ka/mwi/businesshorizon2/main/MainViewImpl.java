package dhbw.ka.mwi.businesshorizon2.main;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.mvplite.event.EventBus;
import com.mvplite.event.EventHandler;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalSplitPanel;
import com.vaadin.ui.Window;

import dhbw.ka.mwi.businesshorizon2.periodedit.PeriodEditViewImpl;
import dhbw.ka.mwi.businesshorizon2.periodedit.ShowPeriodEditEvent;
import dhbw.ka.mwi.businesshorizon2.periodlist.PeriodListViewImpl;
import dhbw.ka.mwi.businesshorizon2.periodlist.ShowPeriodListEvent;

public class MainViewImpl extends Window implements MainView {
	private static final long serialVersionUID = 1L;

	@Autowired
	private EventBus eventBus;
	
	@Autowired
	private PeriodListViewImpl periodListView;
	
	@Autowired
	private MainPresenter presenter;
	
	@Autowired
	private PeriodEditViewImpl periodEditView;
	
	private VerticalSplitPanel verticalPanel;

	private HorizontalSplitPanel horizontalPanel;

	private HorizontalLayout toolbar;

	private VerticalSplitPanel leftVerticalPanel;

	@PostConstruct
	public void init() {
		presenter.setView(this);
		eventBus.addHandler(this);
		generateUi();
	}
	
	private void generateUi() {
		verticalPanel = new VerticalSplitPanel();
		verticalPanel.setSizeFull();
		verticalPanel.setSplitPosition(100, UNITS_PIXELS);
		
		
		horizontalPanel = new HorizontalSplitPanel();
		horizontalPanel.setSizeFull();
		horizontalPanel.setSplitPosition(330, UNITS_PIXELS);
		verticalPanel.setSecondComponent(horizontalPanel);
		
		leftVerticalPanel = new VerticalSplitPanel();
		leftVerticalPanel.setSizeFull();
		leftVerticalPanel.setSplitPosition(60, UNITS_PERCENTAGE);
		horizontalPanel.setFirstComponent(leftVerticalPanel);
		
		toolbar = new HorizontalLayout();
		toolbar.addComponent(new Label("Toolbar"));
		
		setContent(verticalPanel);
	}
	
	@EventHandler
	public void onShowMainView(ShowMainViewEvent event) {
		verticalPanel.setFirstComponent(toolbar);
		
		leftVerticalPanel.setFirstComponent(periodListView);
		eventBus.fireEvent(new ShowPeriodListEvent(presenter.getPeriods()));
		
		horizontalPanel.setSecondComponent(new Label("Hallo"));
	}
	
	@EventHandler
	public void onShowPeriodEdit(ShowPeriodEditEvent event) {
		horizontalPanel.setSecondComponent(periodEditView);
	}

}
