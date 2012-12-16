package dhbw.ka.mwi.businesshorizon2.ui.method;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

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
import dhbw.ka.mwi.businesshorizon2.methods.Result;

/**
 * Dies ist die Vaadin-Implementierung der MethodView.
 * 
 * @author Christian Gahlert
 *
 */
public class MethodViewImpl extends VerticalLayout implements MethodView, Button.ClickListener {
	private static final long serialVersionUID = 1L;

	@Autowired
	private MethodPresenter presenter;

	private Panel methodPanel;

	private ProgressIndicator progressBar;

	private Window progressWindow;
	
	/**
	 * Dies ist der Konstruktor, der von Spring nach der Initialierung der Dependencies 
	 * aufgerufen wird. Er registriert sich selbst beim Presenter und initialisiert die 
	 * View-Komponenten.
	 * 
	 * @author Christian Gahlert
	 */
	@PostConstruct
	public void init() {
		presenter.setView(this);
		generateUi();
	}

	/**
	 * Erstelle das GUI. Dazu wird einfach ein Titel gesetzt.
	 * 
	 * Unten erscheint der "Berechnen"-Button.
	 * 
	 * @author Christian Gahlert
	 */
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

	/**
	 * Dies ist der ClickListener fuer den calcBtn. Er weist den Presenter an, die 
	 * Berechnung zu starten.
	 * 
	 * @author Christian Gahlert
	 */
	@Override
	public void buttonClick(ClickEvent event) {
		presenter.calculate();
	}
	
	/**
	 * {@inheritDoc}
	 * 
	 * Die Progressbar wird in Form eines Modal-Fensters angezeigt, dass nur ueber 
	 * einen "Abbrechen"-Button geschlossen werden kann.
	 * 
	 * Bei Klick auf den Button wird der Presenter angewiesen, die Berechnung zu
	 * unterbrechen.
	 * 
	 * @author Christian Gahlert
	 */
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

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void showMethod(Method method) {
		methodPanel.setCaption(method.getName());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setProgress(float progress) {
		synchronized (getApplication()) {
			if(progressBar != null) {
				progressBar.setValue(progress);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
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

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void showResult(Result result) {
		synchronized (getApplication()) {
			getWindow().addWindow(new MethodViewResultWindow(result));
		}
	}
}
