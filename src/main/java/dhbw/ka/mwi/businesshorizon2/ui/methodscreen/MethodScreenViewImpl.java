package dhbw.ka.mwi.businesshorizon2.ui.methodscreen;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.terminal.Sizeable;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.Form;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.VerticalLayout;

import dhbw.ka.mwi.businesshorizon2.methods.AbstractDeterministicMethod;
import dhbw.ka.mwi.businesshorizon2.models.InputType;
import dhbw.ka.mwi.businesshorizon2.models.Project;
import dhbw.ka.mwi.businesshorizon2.services.proxies.ProjectProxy;
import dhbw.ka.mwi.businesshorizon2.ui.initialscreen.projectlist.SelectProjectEvent;

/**
 * Diese View ist zuständig für das Anzeigen der Details zu einem Projekt.
 * Sie wird in den rechten Bereich des horizontalen SplitPanels der
 * initialScreenView eingefügt.
 *
 * @author Marco Glaser
 */
public class MethodScreenViewImpl extends VerticalLayout implements MethodScreenViewInterface{

	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger("MethodScreenViewImpl.class");

	@Autowired
	private MethodScreenPresenter presenter;

	@Autowired
	private ProjectProxy projectProxy;

	private Form prognoseForm;

	private Form eingabeForm;

	private Form berechnungForm;

	private OptionGroup prognoseGroup;

	private OptionGroup eingabeGroup;

	private OptionGroup berechnungGroup;

	private Label gap1;

	private Label gap2;

	private Label gap3; 

	private Project project;

	private boolean deterministic;

	private boolean stochastic;


	/**
	 * Dies ist der Konstruktor, der von Spring nach der Initialierung der
	 * Dependencies aufgerufen wird. Er registriert sich selbst beim Presenter
	 * und initialisiert die View-Komponenten.
	 *
	 * @author Marco Glaser
	 */
	@PostConstruct
	public void init(){
		presenter.setView(this);
		generateUi();
		logger.debug("Initialisierung beendet");
	}

	public void setProject(Project project){
		this.project = project;
	}

	/**
	 * Diese Methode erstellt das UI, bestehend aus den Labels für die Details zum Projekt:
	 * Name, Details, Beschreibung und zuletzt geändert.
	 *
	 * @author Marco Glaser
	 */
	private void generateUi(){
		setWidth(95, UNITS_PERCENTAGE);
		setHeight(100, UNITS_PERCENTAGE);
		setStyleName("projectDetailsLayout");

		prognoseForm = new Form();
		eingabeForm = new Form();
		berechnungForm = new Form();
		prognoseGroup = new OptionGroup();
		eingabeGroup = new OptionGroup();
		berechnungGroup = new OptionGroup();
		gap1 = new Label();
		gap2 = new Label();
		gap3 = new Label();

		gap1.setHeight("20px");
		gap2.setHeight("20px");
		gap3.setSizeFull();

		prognoseForm.setWidth(90, UNITS_PERCENTAGE);
		eingabeForm.setWidth(90, UNITS_PERCENTAGE);
		berechnungForm.setWidth(90, UNITS_PERCENTAGE);

		prognoseForm.setCaption("Prognosemethode");
		eingabeForm.setCaption("Eingabemethode");
		berechnungForm.setCaption("Berechnungsmethode");

		prognoseGroup.addItem("sto");
		prognoseGroup.addItem("det");
		prognoseGroup.setItemCaption("sto", "Stochastische Eingabe");
		prognoseGroup.setItemCaption("det", "Deterministische Eingabe");
//		prognoseGroup.setValue("sto");
		prognoseGroup.setImmediate(true);
		prognoseGroup.addListener(new Property.ValueChangeListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				logger.debug("ValueChangeEvent in Prognosemethode geworfen");
				String method = (String) event.getProperty().getValue();
				if(method.equals("sto")){
					stochastic = true;
					deterministic = false;
					presenter.setPrognoseMethode(deterministic); //Übergabewert ist so korrekt --> siehe implementierte Methode setPrognoseMethode
				}else if(method.equals("det")){
					stochastic = false;
					deterministic = true;
					presenter.setPrognoseMethode(deterministic);
				}
			}
		});
		eingabeGroup.addItem(InputType.DIRECT);
		eingabeGroup.addItem(InputType.UMSATZKOSTENVERFAHREN);
		eingabeGroup.addItem(InputType.GESAMTKOSTENVERFAHREN);
		eingabeGroup.setItemCaption(InputType.DIRECT, "FCF (Free Cash Flow)");
		eingabeGroup.setItemCaption(InputType.UMSATZKOSTENVERFAHREN, "UKV (Umsatzkostenverfahren)");
		eingabeGroup.setItemCaption(InputType.GESAMTKOSTENVERFAHREN, "GKV (Gesamtkostenverfahren)");
//		eingabeGroup.setValue(InputType.DIRECT);
		eingabeGroup.setImmediate(true);
		eingabeGroup.addListener(new Property.ValueChangeListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				logger.debug("ValueChangeEvent in Eingabemethode geworfen");
				presenter.setInputMethod((InputType)event.getProperty().getValue(), deterministic);

			}
		});

		berechnungGroup.addItem("apv");
		berechnungGroup.addItem("fte");
		berechnungGroup.addItem("wac");
		berechnungGroup.setItemCaption("apv", "APV (Adjusted Present Value)");
		berechnungGroup.setItemCaption("fte", "FTE (Flow to Equity)");
		berechnungGroup.setItemCaption("wac", "WACC (Weighted Average Cost of Capital)");
//		berechnungGroup.setValue("apv");
		berechnungGroup.setImmediate(true);
		berechnungGroup.addListener(new Property.ValueChangeListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				logger.debug("ValueChangeEvent in Berechnungsmethode geworfen");
				switchRadiobuttonEnablement(event);
				presenter.setCalcMethod((String)event.getProperty().getValue());

			}
		});

		prognoseForm.addField("prognoseGroup", prognoseGroup);
		eingabeForm.addField("eingabeGroup", eingabeGroup);
		berechnungForm.addField("berechnungGroup", berechnungGroup);

		addComponent(prognoseForm);
		addComponent(gap1);
		addComponent(berechnungForm);
		addComponent(gap2);
		addComponent(eingabeForm);
		addComponent(gap3);
		setExpandRatio(gap3, 1.0f);
	}

	public void setPrognose(){
		if(project.getProjectInputType().isDeterministic()){
			prognoseGroup.setValue("det");
			deterministic = true;
			stochastic = false;
		}
		else if(project.getProjectInputType().isStochastic()){
			prognoseGroup.setValue("sto");
			deterministic = false;
			stochastic = true;
		}
	}

	public void setEingabe(){
		InputType inp = null;
		if(deterministic){
			inp = project.getProjectInputType().getDeterministicInput();
		}else if(stochastic){
			inp = project.getProjectInputType().getStochasticInput();
		}
		logger.debug ("inp=" + inp);
		if(inp != null){
			switch (inp) {
			case DIRECT :
				eingabeGroup.setValue(InputType.DIRECT);
				break;
			case GESAMTKOSTENVERFAHREN:
				eingabeGroup.setValue(InputType.GESAMTKOSTENVERFAHREN);
				break;
			case UMSATZKOSTENVERFAHREN:
				eingabeGroup.setValue(InputType.UMSATZKOSTENVERFAHREN);
				break;

			default:
				break;
			}
		}
	}

	public void setBerechnung(){
		logger.debug("Methode setzen der Default Werte für die Berechnungaufgerufen");
		AbstractDeterministicMethod method = project.getCalculationMethod();
		if(method != null){
			logger.debug ("Im Projekt Objekt ist eine Methode angegeben");
			if(method.getName().equals("Flow-to-Equity (FTE)")){
				berechnungGroup.setValue("fte");
			}else if(method.getName().equals("Adjusted-Present-Value (APV)")){
				berechnungGroup.setValue("apv");
			}else if(method.getName().equals("Weighted-Average-Cost-of-Capital (WACC)")){	//method.getName().equals("WACC")
				berechnungGroup.setValue("wac");
			}
		}
	}

	public void setRadioValues(){
		setPrognose();
		setBerechnung();
		setEingabe();
	}

	public void switchRadiobuttonEnablement(ValueChangeEvent event) {
		if(event.getProperty().getValue().equals("fte")){
			eingabeGroup.setItemEnabled(InputType.UMSATZKOSTENVERFAHREN, false);
			eingabeGroup.setItemEnabled(InputType.GESAMTKOSTENVERFAHREN, false);
			eingabeGroup.setValue(InputType.DIRECT);
//			presenter.setInputMethod(InputType.DIRECT, deterministic);
		}else{
			eingabeGroup.setItemEnabled(InputType.UMSATZKOSTENVERFAHREN, true);
			eingabeGroup.setItemEnabled(InputType.GESAMTKOSTENVERFAHREN, true);
		}
	}

}
