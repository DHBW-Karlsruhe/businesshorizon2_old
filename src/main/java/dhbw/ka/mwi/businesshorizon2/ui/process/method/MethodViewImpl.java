package dhbw.ka.mwi.businesshorizon2.ui.process.method;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

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
	
	private VerticalLayout methodList;
	private VerticalLayout inputMethod;
	private OptionGroup methods;
	private CheckBox stochastic;
	private CheckBox deterministic;
	
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
		
		methodList = new VerticalLayout();
		inputMethod = new VerticalLayout();	
		stochastic = new CheckBox("Stochastische Berechnung");
		deterministic = new CheckBox("Deterministische Eingabe");
		
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
		
		methods = new OptionGroup();
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
		
	}

	@Override
	public void showMethod(String methodName,Boolean implemented) {
	
	methodList.addComponent(stochastic);
	methods.addItem(methodName);
	if (!implemented){
		methods.setItemEnabled(methodName, false);
	}

	methodList.addComponent(methods);
	methodList.addComponent(deterministic);	
	}

	@Override
	public void enableOptions() {
		
		
	}
}
