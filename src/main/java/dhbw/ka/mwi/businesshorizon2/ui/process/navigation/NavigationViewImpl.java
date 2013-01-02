package dhbw.ka.mwi.businesshorizon2.ui.process.navigation;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;

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
	 * @author Christian Gahlert
	 */
	@PostConstruct
	public void init() {
		presenter.setView(this);
		generateUi();
	}

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
