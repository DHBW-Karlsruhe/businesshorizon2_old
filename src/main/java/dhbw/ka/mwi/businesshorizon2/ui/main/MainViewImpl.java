package dhbw.ka.mwi.businesshorizon2.ui.main;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.mvplite.event.EventBus;
import com.mvplite.event.EventHandler;
import com.mvplite.view.View;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalSplitPanel;
import com.vaadin.ui.Window;

import dhbw.ka.mwi.businesshorizon2.ui.method.MethodViewImpl;
import dhbw.ka.mwi.businesshorizon2.ui.method.ShowMethodEvent;
import dhbw.ka.mwi.businesshorizon2.ui.methodlist.MethodListViewImpl;
import dhbw.ka.mwi.businesshorizon2.ui.methodlist.ShowMethodListEvent;
import dhbw.ka.mwi.businesshorizon2.ui.periodedit.PeriodEditViewImpl;
import dhbw.ka.mwi.businesshorizon2.ui.periodedit.ShowPeriodEditEvent;
import dhbw.ka.mwi.businesshorizon2.ui.periodlist.PeriodListViewImpl;
import dhbw.ka.mwi.businesshorizon2.ui.periodlist.ShowPeriodListEvent;

public class MainViewImpl extends Window implements MainView {
	private static final long serialVersionUID = 1L;

	@Autowired
	private MainPresenter presenter;
	
	private VerticalSplitPanel verticalPanel;

	private HorizontalSplitPanel horizontalPanel;

	private HorizontalLayout toolbar;

	private VerticalSplitPanel leftVerticalPanel;

	@PostConstruct
	public void init() {
		presenter.setView(this);
		generateUi();
	}
	
	private void generateUi() {
		verticalPanel = new VerticalSplitPanel();
		verticalPanel.setSizeFull();
		verticalPanel.setSplitPosition(100, UNITS_PIXELS);

		
		toolbar = new HorizontalLayout();
		toolbar.addComponent(new Label("Toolbar"));
		verticalPanel.setFirstComponent(toolbar);
		
		
		horizontalPanel = new HorizontalSplitPanel();
		horizontalPanel.setSizeFull();
		horizontalPanel.setSplitPosition(330, UNITS_PIXELS);
		verticalPanel.setSecondComponent(horizontalPanel);
		
		
		leftVerticalPanel = new VerticalSplitPanel();
		leftVerticalPanel.setSizeFull();
		leftVerticalPanel.setSplitPosition(60, UNITS_PERCENTAGE);
		horizontalPanel.setFirstComponent(leftVerticalPanel);
		
		
		setContent(verticalPanel);
	}

	@Override
	public void showView(View leftTopView, View leftBottomView, View rightView) {
		leftVerticalPanel.setFirstComponent((Component) leftTopView);
		leftVerticalPanel.setSecondComponent((Component) leftBottomView);
		horizontalPanel.setSecondComponent((Component) rightView);
	}

}
