package dhbw.ka.mwi.businesshorizon2.ui.process.contentcontainer;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

import dhbw.ka.mwi.businesshorizon2.ui.process.contentcontainer.ContentView;

/**
 * Dies ist die Vaadin-Implementierung der MainView (dem Haupt-Fenster).
 * 
 * @author Julius Hacker
 *
 */
public class ContentContainerViewImpl extends VerticalLayout implements ContentContainerView {
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private ContentContainerPresenter presenter;
	
	private VerticalLayout layout;
	private HorizontalLayout buttons;
	private Button backButton, nextButton;
	
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
		
		this.layout = new VerticalLayout();
		layout.setSizeFull();
		
		this.setSizeUndefined();
		this.setWidth(100, UNITS_PERCENTAGE);
		
		this.backButton = new Button("Vorheriger Schritt");
		this.nextButton = new Button("N\u00e4chster Schritt");
		
		this.buttons = new HorizontalLayout();
		this.buttons.addComponent(this.backButton);
		this.buttons.addComponent(this.nextButton);
		
		this.backButton.addListener(new ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				presenter.showPreviousStep();
			}
			
		});
		
		this.nextButton.addListener(new ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				presenter.showNextStep();
			}
			
		});
		
		this.addComponent(layout);
	}
	
	/**
	 * Diese Methode uebernimmt die Anzeige der vom Presenter vorgegebenen Maske
	 * in Vaadin.
	 * 
	 * @author Julius Hacker
	 */

	public void showContentView(ContentView contentView) {
		this.layout.removeAllComponents();
		this.layout.addComponent((Component) contentView);
		this.layout.addComponent(this.buttons);
		this.layout.setComponentAlignment(this.buttons, Alignment.TOP_RIGHT);
		this.layout.setSizeFull();
	}
	
	public void activateBack(boolean activate) {
		this.backButton.setEnabled(activate);
	}
	
	public void activateNext(boolean activate) {
		this.nextButton.setEnabled(activate);

	}
}
