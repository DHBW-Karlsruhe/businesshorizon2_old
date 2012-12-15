package dhbw.ka.mwi.businesshorizon2.ui.method;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.mvplite.event.EventBus;
import com.mvplite.event.EventHandler;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.ProgressIndicator;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import dhbw.ka.mwi.businesshorizon2.methods.Method;
import dhbw.ka.mwi.businesshorizon2.methods.MethodRunner;
import dhbw.ka.mwi.businesshorizon2.methods.Result;

public class MethodViewImpl extends VerticalLayout implements MethodView, Button.ClickListener {
	private static final long serialVersionUID = 1L;

	@Autowired
	private MethodPresenter presenter;

	private Panel methodPanel;

	private ProgressIndicator progressBar;

	private Window progressWindow;
	
	@PostConstruct
	public void init() {
		presenter.setView(this);
		generateUi();
	}

	private void generateUi() {
		setSpacing(true);
		setMargin(true);
		
		Panel scenarioPanel = new Panel("Szenario");
		scenarioPanel.addComponent(new Label("Steuersaetze und so"));
		addComponent(scenarioPanel);
		
		methodPanel = new Panel();
		methodPanel.addComponent(new Label("Spezifische optionen"));
		addComponent(methodPanel);
		
		Button calcBtn = new Button("Berechnen", this);
		addComponent(calcBtn);
	}

	@Override
	public void buttonClick(ClickEvent event) {
		presenter.calculate();
	}
	
	public void showProgress() {
		progressWindow = new Window("Bitte warten");
		progressWindow.setWidth(400, UNITS_PIXELS);
		progressWindow.setModal(true);
		progressWindow.setResizable(false);
		progressWindow.setClosable(false);
		getWindow().addWindow(progressWindow);
		
		
		HorizontalLayout layout = new HorizontalLayout();
		layout.setWidth(100, UNITS_PERCENTAGE);
		layout.setMargin(true);
		layout.setSpacing(true);
		progressWindow.setContent(layout);
		
		
		progressBar = new ProgressIndicator();
		progressBar.setSizeFull();
		progressBar.setPollingInterval(1000);
		layout.addComponent(progressBar);
		layout.setExpandRatio(progressBar, 1);
		
		
		Button abortBtn = new Button("Abbrechen");
		abortBtn.addListener(new ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				presenter.interruptCalculation();
			}
		});
		layout.addComponent(abortBtn);
		layout.setExpandRatio(abortBtn, 0);
	}

	@Override
	public void showMethod(Method method) {
		methodPanel.setCaption(method.getName());
	}

	@Override
	public void setProgress(float progress) {
		synchronized (getApplication()) {
			if(progressBar != null) {
				progressBar.setValue(progress);
			}
		}
	}

	@Override
	public void hideProgress() {
		synchronized (getApplication()) {
			if(progressWindow != null) {
				getWindow().removeWindow(progressWindow);
				progressWindow = null;
				progressBar = null;
			}
		}
	}

	@Override
	public void showResult(Result result) {
		getWindow().addWindow(new MethodViewResultWindow(result));
	}
}