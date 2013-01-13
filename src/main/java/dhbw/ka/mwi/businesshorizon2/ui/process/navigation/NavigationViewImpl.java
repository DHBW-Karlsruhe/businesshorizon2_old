package dhbw.ka.mwi.businesshorizon2.ui.process.navigation;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.terminal.UserError;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;

/**
 * Diese View stellt die Vaadin-Implementierung der Navigation zur Prozessansicht dar.
 * Sie nutzt hierzu insbesondere Vaadings Buttons.
 * 
 * @author Julius Hacker
 *
 */
public class NavigationViewImpl extends HorizontalLayout implements NavigationViewInterface {
	private static final long serialVersionUID = -6649221675778809749L;

	private Logger logger = Logger.getLogger("NavigationViewImpl.class");
	
	@Autowired
	private NavigationPresenter presenter;
	
	private HorizontalLayout layout;
	private HorizontalLayout innerlayout;
	
	private Map<NavigationSteps, Button> navigationButtons = new HashMap<NavigationSteps, Button>();
	
	/**
	 * Dies ist der Konstruktor, der von Spring nach der Initialierung der Dependencies 
	 * aufgerufen wird. Er registriert sich selbst beim Presenter und initialisiert die 
	 * View-Komponenten.
	 * 
	 * @author Julius Hacker
	 */
	@PostConstruct
	public void init() {
		presenter.setView(this);
		generateUi();
	}

	/**
	 * Diese Methode baut die Navigationsoberflaeche auf.
	 * 
	 * @author Julius Hacker
	 */
	private void generateUi() {
		setSizeFull();
		
		this.layout = new HorizontalLayout();
		this.layout.setSizeFull();
		
		this.innerlayout = new HorizontalLayout();
		
		this.addNavigationButton(NavigationSteps.METHOD);
		this.addNavigationButton(NavigationSteps.PARAMETER);
		this.addNavigationButton(NavigationSteps.PERIOD);
		this.addNavigationButton(NavigationSteps.SCENARIO);
		this.addNavigationButton(NavigationSteps.OUTPUT);
		
		layout.addComponent(innerlayout);
		layout.setComponentAlignment(innerlayout, Alignment.BOTTOM_CENTER);
		this.addComponent(layout);
	}

	/**
	 * Diese Methode fuegt der Navigation einen Navigationsbutton hinzu und registriert
	 * einen passenden ClickListener auf ihn.
	 * 
	 * @param value Der Prozessschritt, der durch den Navigationsbutton repraesentiert werden soll.
	 * @author Julius Hacker
	 */
	@Override
	public void addNavigationButton(final NavigationSteps navigationStep) {
		Button navigationButton = new Button(navigationStep.getCaption());
		this.navigationButtons.put(navigationStep, navigationButton);
		
		navigationButton.addListener(new Button.ClickListener() {
			private static final long serialVersionUID = 7411091035775152765L;

			@Override
			public void buttonClick(ClickEvent event) {
				presenter.showStep(navigationStep);
			}
		});
		
		navigationButton.setEnabled(false);
		
		this.innerlayout.addComponent(navigationButton);
		this.innerlayout.setComponentAlignment(navigationButton, Alignment.BOTTOM_CENTER);
		
	}
	
	public void setButtonToInvalid(NavigationSteps navigationStep, boolean invalid) {
		logger.debug("Setze Fehlerzustand von Navigationsbutton");
		if(invalid) {
			this.navigationButtons.get(navigationStep).setComponentError(new UserError("Ungueltige Daten - Aenderungen noetig!"));
		}
		else {
			this.navigationButtons.get(navigationStep).setComponentError(null);
		}
	}
	
	public void setButtonActive(NavigationSteps navigationStep, boolean active) {
			this.navigationButtons.get(navigationStep).setEnabled(active);
	}

}
