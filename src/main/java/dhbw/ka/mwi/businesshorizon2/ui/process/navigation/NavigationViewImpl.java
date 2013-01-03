package dhbw.ka.mwi.businesshorizon2.ui.process.navigation;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

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
	
	@Autowired
	private NavigationPresenter presenter;
	
	private HorizontalLayout layout;
	
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
		//setSpacing(true);
		//setMargin(true);
		
		this.layout = new HorizontalLayout();
		this.layout.setSizeFull();
		
		this.addNavigationButton(NavigationSteps.METHOD);
		this.addNavigationButton(NavigationSteps.PERIOD);
		this.addNavigationButton(NavigationSteps.PARAMETER);
		this.addNavigationButton(NavigationSteps.SCENARIO);
		this.addNavigationButton(NavigationSteps.OUTPUT);
		
		this.addComponent(layout);
		
		//this.setComponentAlignment(this.layout, Alignment.MIDDLE_CENTER);
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
		
		navigationButton.addListener(new Button.ClickListener() {
			private static final long serialVersionUID = 7411091035775152765L;

			@Override
			public void buttonClick(ClickEvent event) {
				presenter.showStep(navigationStep);
			}
		});
		
		this.layout.addComponent(navigationButton);
		this.layout.setComponentAlignment(navigationButton, Alignment.MIDDLE_CENTER);
	}

}
