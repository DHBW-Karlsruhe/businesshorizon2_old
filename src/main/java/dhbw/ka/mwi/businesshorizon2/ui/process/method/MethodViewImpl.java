package dhbw.ka.mwi.businesshorizon2.ui.process.method;

import java.util.Set;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.terminal.UserError;
import com.vaadin.ui.Label;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.themes.Reindeer;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;

import dhbw.ka.mwi.businesshorizon2.methods.AbstractStochasticMethod;
import dhbw.ka.mwi.businesshorizon2.models.InputType;



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
	
	private Panel methodPanel = new Panel();

	
	private VerticalLayout methodList = new VerticalLayout();
	private VerticalLayout inputMethod = new VerticalLayout();

	private OptionGroup stochasticInput = new OptionGroup();
	private OptionGroup deterministicInput = new OptionGroup();
	
	private OptionGroup methods = new OptionGroup();
			
	private CheckBox stochastic = new CheckBox("Stochastische Berechnung");
	private CheckBox deterministic = new CheckBox("Deterministische Eingabe");
	
	private Panel stoInput = new Panel();
	private Panel detInput = new Panel();
	
	
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
		stochastic.setImmediate(true);
		deterministic.setImmediate(true);
		
		methodPanel.addStyleName(Reindeer.PANEL_LIGHT);
		
		stochastic.addListener(new Button.ClickListener() {
			
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				presenter.toggleMethodType(true,event.getButton().booleanValue());				
			}
		});
		deterministic.addListener(new Button.ClickListener() {
	
			
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				presenter.toggleMethodType(false,event.getButton().booleanValue());
				
			}
		});

		methods.setMultiSelect(true);
		methods.setImmediate(true);
		methods.addListener(new Property.ValueChangeListener() {
			
			private static final long serialVersionUID = 1L;

			@SuppressWarnings("unchecked")
			@Override
			public void valueChange(ValueChangeEvent event) {
				presenter.toggleMethod((Set<AbstractStochasticMethod>) methods.getValue());
			}
		});

		stochasticInput.setImmediate(true);
		stochasticInput.addListener(new Property.ValueChangeListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				InputType selected = (InputType) event.getProperty().getValue();
				presenter.toggleMethodTypeInput(true,selected);	
			}
		});
		
		deterministicInput.setImmediate(true);
		deterministicInput.addListener(new Property.ValueChangeListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				InputType selected = (InputType) event.getProperty().getValue();
				presenter.toggleMethodTypeInput(false,selected);				
			}
		});
		
		initOptionGroups();
		generateUi();
		inputMethod.setSizeFull();
	}

	private void initOptionGroups() {
		
		Label detCaption = new Label ("Zukünftige Perioden (deterministisch):");
		detInput.addComponent(detCaption);
		deterministicInput.addItem(InputType.DIRECT);
		deterministicInput.addItem(InputType.REVENUE);
		deterministicInput.addItem(InputType.TOTAL);
		detInput.addStyleName(Reindeer.PANEL_LIGHT);
		detInput.addComponent(deterministicInput);
		
		Label stoCaption= new Label ("Vergangene Perioden (stochastisch):");
		stoInput.addComponent(stoCaption);
		stochasticInput.addItem(InputType.DIRECT);
		stochasticInput.addItem(InputType.REVENUE);
		stochasticInput.addItem(InputType.TOTAL);
		stoInput.addStyleName(Reindeer.PANEL_LIGHT);
		stoInput.addComponent(stochasticInput);
	}

	/**
	 * Erstelle das GUI zum Prozessschritt "Methoden"
	 * 
	 * @author Julius Hacker
	 */
	
	
	private void generateUi() {
		
		this.setFirstComponent(methodList);
		this.setSecondComponent(inputMethod);
		
		methodPanel.addComponent(methods);
		Label methodCaption = new Label("Herkunft der Cashflows w\u00E4hlen:");
		methodList.addComponent(methodCaption);
		methodList.addComponent(stochastic);
		methodList.addComponent(methodPanel);		
		methodList.addComponent(deterministic);
		
		Label inputCaption = new Label("Eingabemethode der Cashflows w\u00E4hlen:");
		inputMethod.addComponent(inputCaption);
		inputMethod.addComponent(stoInput);
		inputMethod.addComponent(detInput);

		
	}

	@Override
	public void showMethod(AbstractStochasticMethod method) {
		
		methods.addItem(method);
		methods.setItemEnabled(method, method.getImplemented());
		
		if (method.getSelected()){
			methods.select(method);
		}
		


	}

	@Override
	public void enableOptions() {
		
		
	}

	@Override
	public void enableMethodSelection(Boolean state) {
		methods.setEnabled(state);
		
	}

	@Override
	public void showInputMethodSelection(Boolean stochasticBool, Boolean checked) {
	
		if (stochasticBool){
			stoInput.setVisible(checked);
		}
		else{
			detInput.setVisible(checked);
		}		
	}

	@Override
	public void selectInput(Boolean stochastic,InputType selected) {
		if (stochastic){
			stochasticInput.select(selected);
		}
		else{
			deterministicInput.select(selected);
		}
		
	}

	@Override
	public void showErrorNoMethodSelected(Boolean state) {
		if (!state){
		this.methods.setComponentError(new UserError("Wenn stochastisch gew\u00E4hlt wurde, muss mindestens eine Methode ausw\u00E4hlt werden"));
		}
		else {
			this.methods.setComponentError(null);
		}
	}

	@Override
	public void showErrorNothingSelected(Boolean state) {
		
		if (!state) {
			this.stochastic.setComponentError(new UserError("Es muss mindestens eine Cashflowherkunft ausgew\u00E4hlt werden"));
		}
		else {
			this.stochastic.setComponentError(null);
		}
		
	}
}
