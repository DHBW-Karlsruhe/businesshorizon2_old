package dhbw.ka.mwi.businesshorizon2.ui.process.method;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.ui.Label;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;


/**
 * Diese Klasse implementiert das GUI fuer den Prozessschritt "Methoden" in Vaadin.
 * 
 * @author Julius Hacker
 *
 */

public class MethodViewImpl extends HorizontalSplitPanel implements MethodViewInterface {
	private static final long serialVersionUID = 1L;

	@Autowired
	private MethodPresenter presenter;	
	private VerticalLayout methodList = new VerticalLayout();
	private VerticalLayout inputMethod = new VerticalLayout();
	private OptionGroup methods = new OptionGroup();
	private CheckBox stochastic = new CheckBox("Stochastische Berechnung");
	private CheckBox deterministic = new CheckBox("Deterministische Eingabe");
	
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
		
		stochastic.addListener(new Button.ClickListener() {
			
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				presenter.activateMethodType();
				
			}
		});
		deterministic.addListener(new Button.ClickListener() {
	
			
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				presenter.activateMethodType();
				
			}
		});
		methods.setMultiSelect(true);
		
		generateUi();
	}

	/**
	 * Erstelle das GUI zum Prozessschritt "Methoden"
	 * 
	 * @author Julius Hacker
	 */
	
	
	private void generateUi() {
		
		this.setFirstComponent((Component)methodList);
		this.setSecondComponent((Component)inputMethod);
		
		methodList.addComponent(stochastic);
		methodList.addComponent(methods);		
		methodList.addComponent(deterministic);
		
	}

	@Override
	public void showMethod(String methodName,Boolean implemented, Boolean selected) {
		
		methods.addItem(methodName);
		methods.setItemEnabled(methodName, implemented);
		methods.select(methodName);


	}

	@Override
	public void enableOptions() {
		
		
	}
}
