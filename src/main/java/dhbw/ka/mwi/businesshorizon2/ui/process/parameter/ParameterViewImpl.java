package dhbw.ka.mwi.businesshorizon2.ui.process.parameter;

import javax.annotation.PostConstruct;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.terminal.UserError;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Window.Notification;

/**
 * Diese Klasse implementiert das GUI fuer den Prozessschritt "Parameter" in
 * Vaadin.
 * 
 * @author Julius Hacker, Christian Scherer
 * 
 */
public class ParameterViewImpl extends VerticalLayout implements
		ParameterViewInterface {
	private static final long serialVersionUID = 1L;

	private Logger logger = Logger.getLogger("ParameterViewImpl.class");

	private GridLayout gridLayout;

	private Label labelHeadingCommon;
	private Label labelHeadingMeth;
	private Label labelHeadingTimeSeries;
	private Label labelHeadingRandomWalk;
	private Label labelHeadingWienerProcess;
	
	private Label labelNumPeriods;
	private Label labelIterations;
	private Label labelNumPastPeriods;
	private Label labelBasisYear;
	private Label labelCashFlowStepRange;
	private Label labelCashFlowProbabilityOfRise;
	private Label labelBorrowedCapitalProbabilityOfRise;
	private Label labelBorrowedCapitalStepRange;
	private Label labelStepsPerPeriod;
	private Label labelRiseOfPeriods;
	private Label labelDeviation;

	
	private Label labelUnitMonetaryUnit;
	private Label labelUnitPercentage;
	private Label labelUnitQuantity;


	private TextField textfieldNumPeriodsToForecast;
	private TextField textfieldNumPastPeriods;
	private TextField textfieldBasisYear;
	private TextField textfieldIterations;
	private TextField textfieldCashFlowStepRange;
	private TextField textfieldCashFlowProbabilityOfRise;
	private TextField textfieldBorrowedCapitalProbabilityOfRise;
	private TextField textfieldBorrowedCapitalStepRange;
	private TextField textfieldStepsPerPeriod;
	private TextField textfieldRiseOfPeriods;
	private TextField textfieldDeviation;

	
	private ComboBox comboBoxRepresentatives;
	private CheckBox checkboxIndustryRepresentative;
	private CheckBox checkboxRiseOfPeriods;
	private CheckBox checkboxDeviationOfPeriods;
	
	private String toolTipBasisYear;
	private String toolTipIterations;
	private String toolTipNumPeriodsToForecast;
	private String toolTipNumPastPeriods;
	private String toolTipStepsPerPeriod;
	private String toolTipIndustryRepresentatives;
	private String toolTipCashFlowStepRange;
	private String toolTipCashFlowProbabilityOfRise;
	private String toolTipBorrowedCapitalProbabilityOfRise;
	private String toolTipBorrowedCapitalStepRange;
	private String toolTipCheckBoxRiseOfPeriods;
	private String toolTipRiseOfPeriods;
	private String toolTipDeviationCheckbox;
	private String toolTipDeviation;
	
	@Autowired
	private ParameterPresenter presenter;


	



	/**
	 * Dies ist der Konstruktor, der von Spring nach der Initialierung der
	 * Dependencies aufgerufen wird. Er registriert sich selbst beim Presenter
	 * und initialisiert die View-Komponenten. Es werden Methoden zur
	 * Initialisierung des Basisjahrs und der Iteraionsschritte aufgerufen,
	 * sowie weitere Methoden zur UI generierung und zum Ausgrauen unrelevanter
	 * Felder.
	 *  
	 * @author Julius Hacker, Christian Scherer
	 */
	@PostConstruct
	public void init() {

		presenter.setView(this);
		setTooltips();
		generateUi();
		logger.debug("Ui erstellt");
	}

	/**
	 * Befuellt die Strings der Tooltips fuer die einzelnen Eingabefelder,
	 * welche dann in generateUi() verwendet werden.
	 * 
	 * @author Christian Scherer
	 */
	private void setTooltips() {
		toolTipBasisYear = "Hier wird das Basisjahr angegeben, auf welches die k\u00fcnftigen Cashflows abgezinst werden. Der Unternehmenswert wird zu dem hier angegebenen Zeitpunkt bestimmt.";
		toolTipIterations = "Hier k\u00f6nnen Sie sich entscheiden, wie oft sie die Berechnung der Prognosewerte durchf\u00fchren wollen. Info: Je mehr Wiederholungen Sie durchf\u00fchren lassen, desto genauer werden die Prognosewerte, aber desto l\u00e4nger wird die Berechnung.";
		toolTipNumPeriodsToForecast = "Hier tragen Sie die Anzahl der zu prognostizierenden Methoden. Info: Haben Sie sich zus\u00e4tzlich für die deterministische Angabe entschieden, entspricht die hier eingetragene Zahl auch der Anzahl der Perioden, die sie deterministisch angeben m\u00fcssen.";
		toolTipNumPastPeriods = "Hier geben Sie an, wie viele vergangene Perioden für die Berechnung des Prognosewert gewichtet werden sollen. Info: Für die Berechnung m\u00fcssen Sie im n\u00e4chsten Prozessschritt immer eine Periode mehr angeben, als Sie hier eingeben.";
		toolTipStepsPerPeriod = "";
		toolTipIndustryRepresentatives = "Als Vergleichswert zu den prognostizierten Cashflows k\u00f6nnen Sie branchenspezifischen Vertreter mit einbeziehen. Dazu müssen Sie die Checkbox aktivieren und in der Dropdown-Liste die gew\u00fcnschte Branche ausw\u00e4hlen.";
		toolTipCashFlowStepRange = "";
		toolTipCashFlowProbabilityOfRise = "";
		toolTipBorrowedCapitalProbabilityOfRise = "";
		toolTipBorrowedCapitalStepRange = "";
		toolTipCheckBoxRiseOfPeriods = "";
		toolTipRiseOfPeriods = "";
		toolTipDeviationCheckbox = "";
		toolTipDeviation = "";
		
	}

	/**
	 * Erstelle das GUI zum Prozessschritt "Parameter". Der Aufbau des Screens
	 * findet mit dem Gridlayout statt um zu garantieren, dass die Eingabefelder
	 * alle auf einer Hoeher auftauchen. branchendaten
	 * 
	 * @author Julius Hacker, Christian Scherer
	 */
	private void generateUi() {

		setMargin(true);

		gridLayout = new GridLayout(3, 23);
		gridLayout.setMargin(true);
		gridLayout.setSpacing(true);
		addComponent(gridLayout);
		
		
		// Heading 1
		labelHeadingCommon = new Label("Allgemein");
		gridLayout.addComponent(labelHeadingCommon, 0, 0);
		
		labelBasisYear = new Label("Basisjahr");
		gridLayout.addComponent(labelBasisYear, 0, 1);
		
		textfieldBasisYear = new TextField();
		textfieldBasisYear.setImmediate(true);
		textfieldBasisYear
				.setDescription(toolTipBasisYear);
		textfieldBasisYear.addListener(new Property.ValueChangeListener() {
			private static final long serialVersionUID = 1L;

			public void valueChange(ValueChangeEvent event) {
				presenter.basisYearChosen((String) textfieldBasisYear
						.getValue());
			}
		});
		gridLayout.addComponent(textfieldBasisYear, 1, 1);
		
		//Heading 2
		labelHeadingMeth = new Label("Stochastische Methoden: Allgemeines");
		gridLayout.addComponent(labelHeadingMeth, 0, 3);

		labelNumPeriods = new Label("Anzahl zu prognostizierender Perioden");
		gridLayout.addComponent(labelNumPeriods, 0, 4);

		textfieldNumPeriodsToForecast = new TextField();
		textfieldNumPeriodsToForecast.setImmediate(true);
		textfieldNumPeriodsToForecast.setDescription(toolTipNumPeriodsToForecast);
		textfieldNumPeriodsToForecast.addListener(new Property.ValueChangeListener() {
			private static final long serialVersionUID = 1L;

			public void valueChange(ValueChangeEvent event) {
				presenter
						.numberPeriodsToForecastChosen((String) textfieldNumPeriodsToForecast
								.getValue());
			}
		});
		gridLayout.addComponent(textfieldNumPeriodsToForecast, 1, 4);
		
		labelUnitQuantity = new Label("Anzahl");
		gridLayout.addComponent(labelUnitQuantity, 2, 4);
		
		
		
		labelIterations = new Label("Anzahl Wiederholungen");
		gridLayout.addComponent(labelIterations, 0, 5);

		textfieldIterations = new TextField();
		textfieldIterations.setImmediate(true);
		textfieldIterations.setValue(10000);
		textfieldIterations.setDescription(toolTipIterations);
		textfieldIterations.addListener(new Property.ValueChangeListener() {
			private static final long serialVersionUID = 1L;

			public void valueChange(ValueChangeEvent event) {
				presenter
						.iterationChosen((String) textfieldIterations
								.getValue());
			}
		});
		gridLayout.addComponent(textfieldIterations, 1, 5);
		
		labelUnitQuantity = new Label("Anzahl");
		gridLayout.addComponent(labelUnitQuantity, 2, 5);
		
		labelStepsPerPeriod = new Label("Schritte pro Periode");
		gridLayout.addComponent(labelStepsPerPeriod, 0, 6);
		
		textfieldStepsPerPeriod = new TextField();
		textfieldStepsPerPeriod.setImmediate(true);
		textfieldStepsPerPeriod.setDescription(toolTipStepsPerPeriod);
		textfieldStepsPerPeriod.addListener(new Property.ValueChangeListener() {
			private static final long serialVersionUID = 1L;

			public void valueChange(ValueChangeEvent event) {
				presenter
						.stepsPerPeriodChosen((String) textfieldStepsPerPeriod
								.getValue());
			}
		});
		gridLayout.addComponent(textfieldStepsPerPeriod, 1, 6);
		
		labelUnitQuantity = new Label("Anzahl");
		gridLayout.addComponent(labelUnitQuantity, 2, 6);
		
		
		
		//Heading 3
		
		labelHeadingTimeSeries = new Label("Stochastisch: Zeitreihenanalyse");
		gridLayout.addComponent(labelHeadingTimeSeries, 0, 8);
		
		labelNumPastPeriods = new Label(
				"Anzahl einbezogener, vergangener Perioden");
		gridLayout.addComponent(labelNumPastPeriods, 0, 9);

		textfieldNumPastPeriods = new TextField();
		textfieldNumPastPeriods.setImmediate(true);
		textfieldNumPastPeriods.setValue(5);
		textfieldNumPastPeriods.setDescription(toolTipNumPastPeriods+" Bitte beachten Sie, dass in dem Reiter Perioden immer eine Periode mehr angegeben werden muss. Diese zusätzliche Periode wird bei einem Berechnungsverfahren der Zeitreihenanalyse benötigt.");
		textfieldNumPastPeriods.addListener(new Property.ValueChangeListener() {
			private static final long serialVersionUID = 1L;

			public void valueChange(ValueChangeEvent event) {
				presenter
						.relevantPastPeriodsChosen((String) textfieldNumPastPeriods
								.getValue());
			}
		});
		gridLayout.addComponent(textfieldNumPastPeriods, 1, 9);
		
		labelUnitQuantity = new Label("Anzahl");
		gridLayout.addComponent(labelUnitQuantity,2, 9);
		
		checkboxIndustryRepresentative = new CheckBox();
		checkboxIndustryRepresentative
				.setCaption("Branchenstellvertreter einbeziehen");
		checkboxIndustryRepresentative.setDescription(toolTipIndustryRepresentatives);
		checkboxIndustryRepresentative.addListener(new ClickListener() {
			/**
			 * Derzeit unbenutzt, da die Funkionalitaet in der Berechnung noch
			 * nicht hinterlegt ist.
			 * 
			 * @see init()-Methode dieser Klasse
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				presenter
						.industryRepresentativeCheckBoxSelected(checkboxIndustryRepresentative
								.booleanValue());
			}
		});
		gridLayout.addComponent(checkboxIndustryRepresentative, 0, 10);

		comboBoxRepresentatives = new ComboBox();
		comboBoxRepresentatives.setImmediate(true);
		comboBoxRepresentatives.setInputPrompt("Branche ausw\u00e4hlen");
		comboBoxRepresentatives.setNullSelectionAllowed(false);
		comboBoxRepresentatives.addListener(new Property.ValueChangeListener() {
			/**
			 * Derzeit unbenutzt, da die Funkionalitaet in der Berechnung auf
			 * Basis von Branchenverreter in dieser Softareversion noch nicht
			 * hinterlegt ist.
			 * 
			 * @see init()-Methode dieser Klasse
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				presenter.industryRepresentativeListItemChosen((String) event
						.getProperty().getValue());
				logger.debug("Branche " + event + " gewaehlt");
			}
		});

		gridLayout.addComponent(comboBoxRepresentatives, 1, 10);
		
		
		//Heading 4
		
		labelHeadingRandomWalk = new Label("Stochastisch: Random Walk (Free Cash Flow)");
		gridLayout.addComponent(labelHeadingRandomWalk,0,12);
			
		labelCashFlowStepRange = new Label("Schrittweite Cashflows");
		gridLayout.addComponent(labelCashFlowStepRange, 0, 13);
		
		textfieldCashFlowStepRange = new TextField();
		textfieldCashFlowStepRange.setImmediate(true);
		textfieldCashFlowStepRange
				.setDescription(toolTipCashFlowStepRange);
		textfieldCashFlowStepRange.addListener(new Property.ValueChangeListener() {
			private static final long serialVersionUID = 1L;

			public void valueChange(ValueChangeEvent event) {
				presenter.cashFlowStepRangeChosen((String) textfieldCashFlowStepRange
						.getValue());
			}
		});
		gridLayout.addComponent(textfieldCashFlowStepRange, 1, 13);
		
		labelUnitMonetaryUnit = new Label("GE");
		gridLayout.addComponent(labelUnitMonetaryUnit,2,13);
		
		labelCashFlowProbabilityOfRise = new Label("Wahrscheinlichkeit f\u00fcr steigende Cashflowentwicklung");
		gridLayout.addComponent(labelCashFlowProbabilityOfRise, 0, 14);
		
		textfieldCashFlowProbabilityOfRise = new TextField();
		textfieldCashFlowProbabilityOfRise.setImmediate(true);
		textfieldCashFlowProbabilityOfRise
				.setDescription(toolTipCashFlowProbabilityOfRise);
		textfieldCashFlowProbabilityOfRise.addListener(new Property.ValueChangeListener() {
			private static final long serialVersionUID = 1L;

			public void valueChange(ValueChangeEvent event) {
				presenter.cashFlowProbabilityOfRiseChosen((String) textfieldCashFlowProbabilityOfRise
						.getValue());
			}
		});
		gridLayout.addComponent(textfieldCashFlowProbabilityOfRise, 1, 14);
		
		labelUnitPercentage = new Label("%");
		gridLayout.addComponent(labelUnitPercentage,2,14);
		
		labelBorrowedCapitalStepRange = new Label("Schrittweite Fremdkapital");
		gridLayout.addComponent(labelBorrowedCapitalStepRange, 0, 15);
		
		textfieldBorrowedCapitalStepRange = new TextField();
		textfieldBorrowedCapitalStepRange.setImmediate(true);
		textfieldBorrowedCapitalStepRange
				.setDescription(toolTipBorrowedCapitalStepRange);
		textfieldBorrowedCapitalStepRange.addListener(new Property.ValueChangeListener() {
			private static final long serialVersionUID = 1L;

			public void valueChange(ValueChangeEvent event) {
				presenter.borrowedCapitalStepRangeChosen((String) textfieldBorrowedCapitalStepRange
						.getValue());
			}
		});
		gridLayout.addComponent(textfieldBorrowedCapitalStepRange, 1, 15);
		
		labelUnitMonetaryUnit = new Label("GE");
		gridLayout.addComponent(labelUnitMonetaryUnit,2,15);
		
		labelBorrowedCapitalProbabilityOfRise = new Label("Wahrscheinlichkeit f\u00fcr steigende Fremdkapitalentwicklung");
		gridLayout.addComponent(labelBorrowedCapitalProbabilityOfRise, 0, 16);
		
		textfieldBorrowedCapitalProbabilityOfRise = new TextField();
		textfieldBorrowedCapitalProbabilityOfRise.setImmediate(true);
		textfieldBorrowedCapitalProbabilityOfRise
				.setDescription(toolTipBorrowedCapitalProbabilityOfRise);
		textfieldBorrowedCapitalProbabilityOfRise.addListener(new Property.ValueChangeListener() {
			private static final long serialVersionUID = 1L;

			public void valueChange(ValueChangeEvent event) {
				presenter.borrowedCapitalProbabilityOfRiseChosen((String) textfieldBorrowedCapitalProbabilityOfRise
						.getValue());
			}
		});
		gridLayout.addComponent(textfieldBorrowedCapitalProbabilityOfRise, 1, 16);

		labelUnitPercentage = new Label("%");
		gridLayout.addComponent(labelUnitPercentage,2,16);
		
		//Heading 5
		
		labelHeadingWienerProcess = new Label("Stochastisch: Wiener-Prozess (Free Cash Flow)");
		gridLayout.addComponent(labelHeadingWienerProcess,0,18);
		
		checkboxRiseOfPeriods = new CheckBox();
		checkboxRiseOfPeriods.setCaption("Steigung aus angegebenen Perioden ermitteln");
		checkboxRiseOfPeriods.setDescription(toolTipCheckBoxRiseOfPeriods);
		checkboxRiseOfPeriods.addListener(new ClickListener() {
			/**
			 * Derzeit unbenutzt, da die Funkionalitaet in der Berechnung noch
			 * nicht hinterlegt ist.
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				presenter
					.riseOfPeriodsCheckBoxSelected(checkboxRiseOfPeriods
						.booleanValue());
			}
		});
		gridLayout.addComponent(checkboxRiseOfPeriods,0,19);
		
		labelRiseOfPeriods = new Label("Steigung");
		gridLayout.addComponent(labelRiseOfPeriods,0,20);
		
		textfieldRiseOfPeriods = new TextField();
		textfieldRiseOfPeriods.setImmediate(true);
		textfieldRiseOfPeriods
				.setDescription(toolTipRiseOfPeriods);
		textfieldRiseOfPeriods.addListener(new Property.ValueChangeListener() {
			private static final long serialVersionUID = 1L;

			public void valueChange(ValueChangeEvent event) {
				presenter.riseOfPeriodsChosen((String) textfieldRiseOfPeriods
						.getValue());
			}
		});
		gridLayout.addComponent(textfieldRiseOfPeriods,1,20);
		
		checkboxDeviationOfPeriods = new CheckBox();
		checkboxDeviationOfPeriods.setCaption("Standardabweichung aus angegebenen Perioden ermitteln");
		checkboxDeviationOfPeriods.setDescription(toolTipDeviationCheckbox);
		checkboxDeviationOfPeriods.addListener(new ClickListener() {
			/**
			 * Derzeit unbenutzt, da die Funkionalitaet in der Berechnung noch
			 * nicht hinterlegt ist.
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				presenter
					.deviationOfPeriodsCheckBoxSelected(checkboxDeviationOfPeriods
						.booleanValue());
			}
		});
		gridLayout.addComponent(checkboxDeviationOfPeriods,0,21);
		
		labelDeviation = new Label("Standardabweichung");
		gridLayout.addComponent(labelDeviation,0,22);
		textfieldDeviation = new TextField();
		textfieldDeviation.setImmediate(true);
		textfieldDeviation
				.setDescription(toolTipDeviation);
		textfieldDeviation.addListener(new Property.ValueChangeListener() {
			private static final long serialVersionUID = 1L;

			public void valueChange(ValueChangeEvent event) {
				presenter.deviationChosen((String) textfieldDeviation
						.getValue());
			}
		});
		gridLayout.addComponent(textfieldDeviation,1,22);
		
		

	}

	/**
	 * Gibt eine Fehlermeldung an den Benutzer aus.
	 * 
	 * @author Christian Scherer
	 * @param message
	 *            Fehlermeldung die der Methode zur Ausgabe uebergeben wird
	 */
	@Override
	public void showErrorMessage(String message) {
		getWindow().showNotification((String) "", message,
				Notification.TYPE_ERROR_MESSAGE);
	}

	/**
	 * Setzt den Wert des Texfelds 'Wahl des Basisjahr'
	 * 
	 * @author Christian Scherer
	 * @param basisYear
	 *            Das Jahr, das Basis-Jahr, auf das die Cashflows abgezinst
	 *            werden
	 */
	@Override
	public void setTextFieldValueBasisYear(String basisYear) {
		this.textfieldBasisYear.setValue(basisYear);
	}

	/**
	 * Diese Methode graut das Textfeld 'textfieldNumPeriods' aus.
	 * 
	 * @author Christian Scherer
	 * @param enabled
	 *            true aktiviert den Kombonenten, false deaktiviert (graut aus)
	 *            den Komponenten
	 */
	@Override
	public void activatePeriodsToForecast(boolean enabled) {
		this.textfieldNumPeriodsToForecast.setEnabled(enabled);

	}

	/**
	 * Diese Methode graut das Textfeld 'textfieldNumPastPeriods' aus.
	 * 
	 * @author Christian Scherer
	 * @param enabled
	 *            true aktiviert den Kombonenten, false deaktiviert (graut aus)
	 *            den Komponenten
	 */
	@Override
	public void activateRelevantPastPeriods(boolean enabled) {
		this.textfieldNumPastPeriods.setEnabled(enabled);
	}

	/**
	 * Diese Methode graut die ComboBox 'comboBoxIteraions' aus.
	 * 
	 * @author Christian Scherer
	 * @param enabled
	 *            true aktiviert den Kombonenten, false deaktiviert (graut aus)
	 *            den Komponenten
	 */
	@Override
	public void activateIterations(boolean enabled) {
		this.textfieldIterations.setEnabled(enabled);

	}

	/**
	 * Diese Methode graut die Checkbox fuer die Branchenstellvertreter aus, da
	 * diese Funktionalitaet noch nicht gegeben ist.
	 * 
	 * @author Christian Scherer
	 * @param enabled
	 *            true aktiviert den Kombonenten, false deaktiviert (graut aus)
	 *            den Komponenten
	 */
	@Override
	public void activateCheckboxIndustryRepresentative(boolean enabled) {
		this.checkboxIndustryRepresentative.setEnabled(enabled);
	}

	/**
	 * Diese Methode graut die ComboBox (DropDown-Liste) fuer die
	 * Branchenstellvertreter aus, da diese Funktionalitaet noch nicht gegeben
	 * ist.
	 * 
	 * @author Christian Scherer
	 * @param enabled
	 *            true aktiviert den Kombonenten, false deaktiviert (graut aus)
	 *            den Komponenten
	 */
	@Override
	public void activateComboBoxRepresentatives(boolean enabled) {
		this.comboBoxRepresentatives.setEnabled(enabled);
	}

	/**
	 * Setzt eine Fehleranzeige an das Entsprechende Feld bzw. entfernt diese
	 * wieder je nach Parametriesierung
	 * 
	 * @author Christian Scherer
	 * @param setError
	 *            true, wenn eine Fehleranzeige gezeigt werden soll und false,
	 *            wenn die Fehleranzeige geloescht werden soll
	 * @param component
	 *            Identifiziert den Componenten, bei dem die Fehleranzeige
	 *            angezeigt bzw. entfernt werden soll
	 * @param message
	 *            Fehlermeldung die neben dem Componenten gezeigt werden soll
	 */
	@Override
	public void setComponentError(boolean setError, String component,
			String message) {
		if (component.equals("periodsToForecast")) {
			if (setError) {
				this.textfieldNumPeriodsToForecast.setComponentError(new UserError(
						message));
			} else {
				this.textfieldNumPeriodsToForecast.setComponentError(null);
			}
		} else if (component.equals("pastPeriods")) {
			if (setError) {
				this.textfieldNumPastPeriods.setComponentError(new UserError(
						message));
			} else {
				this.textfieldNumPastPeriods.setComponentError(null);
			}
		} else if (component.equals("basisYear")) {
			if (setError) {
				this.textfieldBasisYear
						.setComponentError(new UserError(message));
			} else {
				this.textfieldBasisYear.setComponentError(null);
			}

		} else if (component.equals("iterations")) {
			if (setError) {
				this.textfieldIterations
						.setComponentError(new UserError(message));
			} else {
				this.textfieldIterations.setComponentError(null);
			}

		} else if (component.equals("cashFlowStepRange")) {
			if (setError) {
				this.textfieldCashFlowStepRange
						.setComponentError(new UserError(message));
			} else {
				this.textfieldCashFlowStepRange.setComponentError(null);
			}

		} else if (component.equals("cashFlowProbabilityOfRise")) {
			if (setError) {
				this.textfieldCashFlowProbabilityOfRise
						.setComponentError(new UserError(message));
			} else {
				this.textfieldCashFlowProbabilityOfRise.setComponentError(null);
			}

		} else if (component.equals("borrowedCapitalStepRange")) {
			if (setError) {
				this.textfieldBorrowedCapitalStepRange
						.setComponentError(new UserError(message));
			} else {
				this.textfieldBorrowedCapitalStepRange.setComponentError(null);
			}

		} else if (component.equals("borrowedCapitalProbabilityOfRise")) {
			if (setError) {
				this.textfieldBorrowedCapitalProbabilityOfRise
						.setComponentError(new UserError(message));
			} else {
				this.textfieldBorrowedCapitalProbabilityOfRise.setComponentError(null);
			}

		}

	}

	/**
	 * Diese Methode graut das Textfeld 'textfieldCashFlowStepRange' aus.
	 * 
	 * @author Christian Scherer
	 * @param enabled
	 *            true aktiviert den Kombonenten, false deaktiviert (graut aus)
	 *            den Komponenten
	 */
	@Override
	public void activateCashFlowStepRang(boolean enabled) {
		this.textfieldCashFlowStepRange.setEnabled(enabled);
		
	}

	/**
	 * Diese Methode graut das Textfeld 'textfieldCashFlowProbabilityOfRise' aus.
	 * 
	 * @author Christian Scherer
	 * @param enabled
	 *            true aktiviert den Kombonenten, false deaktiviert (graut aus)
	 *            den Komponenten
	 */
	@Override
	public void activateCashFlowProbabilityOfRise(boolean enabled) {
		this.textfieldCashFlowProbabilityOfRise.setEnabled(enabled);
		
	}

	/**
	 * Diese Methode graut das Textfeld 'textfieldBorrowedCapitalProbabilityOfRise' aus.
	 * 
	 * @author Christian Scherer
	 * @param enabled
	 *            true aktiviert den Kombonenten, false deaktiviert (graut aus)
	 *            den Komponenten
	 */
	@Override
	public void activateBorrowedCapitalProbabilityOfRise(boolean enabled) {
		this.textfieldBorrowedCapitalProbabilityOfRise.setEnabled(enabled);
		
	}

	/**
	 * Diese Methode graut das Textfeld 'textfieldBorrowedCapitalStepRange' aus.
	 * 
	 * @author Christian Scherer
	 * @param enabled
	 *            true aktiviert den Kombonenten, false deaktiviert (graut aus)
	 *            den Komponenten
	 */
	@Override
	public void activateBorrowedCapitalStepRange(boolean enabled) {
		this.textfieldBorrowedCapitalStepRange.setEnabled(enabled);

		
	}
	
	/**
	 * Diese Methode graut das Textfeld 'textfieldRiseOfPeriods' aus.
	 * 
	 * @author Christian Scherer
	 * @param enabled
	 *            true aktiviert den Kombonenten, false deaktiviert (graut aus)
	 *            den Komponenten
	 */
	@Override
	public void activateRiseOfPeriods(boolean enabled) {
		this.textfieldRiseOfPeriods.setEnabled(enabled);

		
	}
	
	/**
	 * Diese Methode graut die Checkbox 'checkboxRiseOfPeriods' aus.
	 * 
	 * @author Christian Scherer
	 * @param enabled
	 *            true aktiviert den Kombonenten, false deaktiviert (graut aus)
	 *            den Komponenten
	 */
	@Override
	public void activateRiseOfPeriodsCheckbox(boolean enabled) {
		this.checkboxRiseOfPeriods.setEnabled(enabled);

		
	}
	
	/**
	 * Diese Methode graut das Textfeld 'textfieldDeviaton' aus.
	 * 
	 * @author Christian Scherer
	 * @param enabled
	 *            true aktiviert den Kombonenten, false deaktiviert (graut aus)
	 *            den Komponenten
	 */
	@Override
	public void activateDeviation(boolean enabled) {
		this.textfieldDeviation.setEnabled(enabled);

		
	}
	
	/**
	 * Diese Methode graut die Checkbos 'checkboxDeviationOfPeriods' aus.
	 * 
	 * @author Christian Scherer
	 * @param enabled
	 *            true aktiviert den Kombonenten, false deaktiviert (graut aus)
	 *            den Komponenten
	 */
	@Override
	public void activateDeviationCheckbox(boolean enabled) {
		this.checkboxDeviationOfPeriods.setEnabled(enabled);

		
	}

	/**
	 * Diese Methode graut das Textfeld 'textfieldStepsPerPeriod' aus.
	 * 
	 * @author Christian Scherer
	 * @param enabled
	 *            true aktiviert den Kombonenten, false deaktiviert (graut aus)
	 *            den Komponenten
	 */
	@Override
	public void activateStepsPerPeriod(boolean enabled) {
		this.textfieldStepsPerPeriod.setEnabled(false);
	}

}
