///*******************************************************************************
// * BusinessHorizon2
// *
// * Copyright (C) 
// * 2012-2013 Christian Gahlert, Florian Stier, Kai Westerholz,
// * Timo Belz, Daniel Dengler, Katharina Huber, Christian Scherer, Julius Hacker
// * 2013-2014 Marcel Rosenberger, Mirko Göpfrich, Annika Weis, Katharina Narlock, 
// * Volker Meier
// * 
// *
// *
// * This program is free software: you can redistribute it and/or modify
// * it under the terms of the GNU Affero General Public License as published by
// * the Free Software Foundation, either version 3 of the License, or
// * (at your option) any later version.
// *
// * This program is distributed in the hope that it will be useful,
// * but WITHOUT ANY WARRANTY; without even the implied warranty of
// * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
// * GNU Affero General Public License for more details.
// *
// * You should have received a copy of the GNU Affero General Public License
// * along with this program. If not, see <http://www.gnu.org/licenses/>.
// ******************************************************************************/
//package dhbw.ka.mwi.businesshorizon2.ui.process.parameter;
//
//import javax.annotation.PostConstruct;
//
//import org.apache.log4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import com.vaadin.data.Property;
//import com.vaadin.data.Property.ValueChangeEvent;
//import com.vaadin.terminal.UserError;
//import com.vaadin.ui.Button.ClickListener;
//import com.vaadin.ui.CheckBox;
//import com.vaadin.ui.ComboBox;
//import com.vaadin.ui.GridLayout;
//import com.vaadin.ui.HorizontalSplitPanel;
//import com.vaadin.ui.Label;
//import com.vaadin.ui.TextField;
//import com.vaadin.ui.VerticalLayout;
//import com.vaadin.ui.Button.ClickEvent;
//import com.vaadin.ui.Window.Notification;
//
///**
// * Diese Klasse implementiert das GUI fuer den Prozessschritt "Parameter" in
// * Vaadin.
// * 
// * @author Julius Hacker, Christian Scherer
// * 
// */
//public class ParameterViewImpl extends HorizontalSplitPanel implements
//		ParameterViewInterface {
//	private static final long serialVersionUID = 1L;
//
//	private static final Logger logger = Logger
//			.getLogger("ParameterViewImpl.class");
//	
//	private GridLayout gridLayout;
//
//	private Label labelHeadingCommon;
//	private Label labelHeadingMethDet;
//	private Label labelHeadingTimeSeries;
//	private Label labelHeadingRandomWalk;
//	private Label labelHeadingWienerProcess;
//	private Label labelHeadingDeterministicCommon; // Annika Weis
//	private Label labelHeadingDCF; // Annika Weis
//	private Label labelHeadingAPV; // Annika Weis
//
//	private Label labelNumPeriods;
//	private Label labelNumPeriods_deterministic; // Annika Weis
//	private Label labelIterations;
//	private Label labelNumPastPeriods;
//	private Label labelNumSpecifiedPastPeriods;
//	private Label labelBasisYear;
//	private Label labelProbability;
//	private Label labelCashFlowStepRange;
//	private Label labelStepRange;
//	private Label labelCashFlowProbabilityOfRise;
//	private Label labelBorrowedCapitalProbabilityOfRise;
//	private Label labelBorrowedCapitalStepRange;
//	private Label labelStepsPerPeriod;
//	private Label labelRiseOfPeriods;
//	private Label labelDeviation;
//
//	private Label labelUnitMonetaryUnit;
//	private Label labelUnitPercentage;
//	private Label labelUnitQuantity;
//
//	private TextField textfieldNumPeriodsToForecast;
//	private TextField textfieldNumPeriodsToForecast_deterministic;
//	private TextField textfieldNumPastPeriods;
//	private TextField textfieldNumSpecifiedPastPeriods;
//	private TextField textfieldBasisYear;
//	private TextField textfieldIterations;
//	private TextField textfieldProbability;
//	private TextField textfieldStepRange;
//	private TextField textfieldCashFlowStepRange;
//	private TextField textfieldCashFlowProbabilityOfRise;
//	private TextField textfieldBorrowedCapitalProbabilityOfRise;
//	private TextField textfieldBorrowedCapitalStepRange;
//	private TextField textfieldStepsPerPeriod;
//	private TextField textfieldRiseOfPeriods;
//	private TextField textfieldDeviation;
//
//	private ComboBox comboBoxRepresentatives;
//	private CheckBox checkboxIndustryRepresentative;
//	private CheckBox checkboxCalculateStepRange;
//	private CheckBox checkboxRiseOfPeriods;
//	private CheckBox checkboxDeviationOfPeriods;
//
//	private String toolTipBasisYear;
//	private String toolTipIterations;
//	private String toolTipNumPeriodsToForecast;
//	private String toolTipNumPeriodsToForecast_deterministic;
//	private String toolTipNumPastPeriods;
//	private String toolTipNumSpecifiedPastPeriods;
//	private String toolTipStepsPerPeriod;
//	private String toolTipIndustryRepresentatives;
//	private String toolTipCashFlowStepRange;
//	private String toolTipCashFlowProbabilityOfRise;
//	private String toolTipBorrowedCapitalProbabilityOfRise;
//	private String toolTipBorrowedCapitalStepRange;
//	private String toolTipCheckBoxRiseOfPeriods;
//	private String toolTipRiseOfPeriods;
//	private String toolTipDeviationCheckbox;
//	private String toolTipDeviation;
//	private String toolTipProbability;
//	private String toolTipCheckBoxCalculateStepRange;
//	private String toolTipStepRange;
//
//	@Autowired
//	private ParameterPresenter presenter;
//
//	/**
//	 * Dies ist der Konstruktor, der von Spring nach der Initialierung der
//	 * Dependencies aufgerufen wird. Er registriert sich selbst beim Presenter
//	 * und initialisiert die View-Komponenten. Es werden Methoden zur
//	 * Initialisierung des Basisjahrs und der Iteraionsschritte aufgerufen,
//	 * sowie weitere Methoden zur UI generierung und zum Ausgrauen unrelevanter
//	 * Felder.
//	 * 
//	 * @author Julius Hacker, Christian Scherer
//	 */
//	@PostConstruct
//	public void init() {
//
//		presenter.setView(this);
//
//		setTooltips();
//		generateUi();
//
//		logger.debug("Ui erstellt");
//	}
//
//	/**
//	 * Befuellt die Strings der Tooltips fuer die einzelnen Eingabefelder,
//	 * welche dann in generateUi() verwendet werden.
//	 * 
//	 * @author Christian Scherer
//	 */
//	private void setTooltips() {
//		toolTipBasisYear = "Hier wird das Basisjahr angegeben, auf welches die k\u00fcnftigen Cashflows abgezinst werden. Der Unternehmenswert wird zu dem hier angegebenen Zeitpunkt bestimmt.";
//		toolTipIterations = "Hier k\u00f6nnen Sie sich entscheiden, wie oft sie die Berechnung der Prognosewerte durchf\u00fchren wollen. Info: Je mehr Wiederholungen Sie durchf\u00fchren lassen, desto genauer werden die Prognosewerte, aber desto l\u00e4nger wird die Berechnung.";
//		toolTipNumPeriodsToForecast = "Hier tragen Sie die Anzahl der zu prognostizierenden Methoden ein. Info: Haben Sie sich zus\u00e4tzlich für die deterministische Angabe entschieden, entspricht die hier eingetragene Zahl auch der Anzahl der Perioden, die sie deterministisch angeben m\u00fcssen.";
//		toolTipNumPeriodsToForecast_deterministic = "Hier tragen Sie die Anzahl der zu prognostizierenden deterministischen Methoden ein. Info: Haben Sie sich zus\u00e4tzlich für die deterministische Angabe entschieden, entspricht die hier eingetragene Zahl auch der Anzahl der Perioden, die sie deterministisch angeben m\u00fcssen.";
//		toolTipNumPastPeriods = "Hier geben Sie an, wie viele vergangene Perioden für die Berechnung des Prognosewert gewichtet werden sollen. Info: Für die Berechnung m\u00fcssen Sie im n\u00e4chsten Prozessschritt immer eine Periode mehr angeben, als Sie hier eingeben.";
//		toolTipNumSpecifiedPastPeriods ="Bitte beachten Sie, dass die Anzahl anzugebender Perioden immer um mindestens eins größer sein muss als die Anzahl der einbezogenen Perioden. Diese zusätzliche Periode wird bei einem Berechnungsverfahren der Zeitreihenanalyse benötigt.";
//		toolTipStepsPerPeriod = "";
//		toolTipIndustryRepresentatives = "Als Vergleichswert zu den prognostizierten Cashflows k\u00f6nnen Sie branchenspezifischen Vertreter mit einbeziehen. Dazu müssen Sie die Checkbox aktivieren und in der Dropdown-Liste die gew\u00fcnschte Branche ausw\u00e4hlen.";
//		toolTipCashFlowStepRange = "";
//		toolTipCashFlowProbabilityOfRise = "";
//		toolTipBorrowedCapitalProbabilityOfRise = "";
//		toolTipBorrowedCapitalStepRange = "";
//		toolTipCheckBoxRiseOfPeriods = "";
//		toolTipRiseOfPeriods = "";
//		toolTipDeviationCheckbox = "";
//		toolTipDeviation = "";
//		toolTipProbability = "";
//		toolTipCheckBoxCalculateStepRange = "";
//		toolTipStepRange = "";
//
//	}
//
//	/**
//	 * Erstelle das GUI zum Prozessschritt "Parameter". Der Aufbau des Screens
//	 * findet mit dem Gridlayout statt um zu garantieren, dass die Eingabefelder
//	 * alle auf einer Hoeher auftauchen. branchendaten
//	 * 
//	 * @author Julius Hacker, Christian Scherer
//	 */
//
//	@Override
//	public void showParameterView() {
//		this.removeAllComponents();
//		generateUi();
//
//	}
//
//	private void generateUi() {
//
//		setMargin(true);
//		//setSizeFull();
//		setLocked(true);
//		setStyleName("small");
//
//		// TODO: Zeilenanzahl anpassen
//		gridLayout = new GridLayout(3, 30);
//		gridLayout.setMargin(true);
//		gridLayout.setSpacing(true);
//		//gridLayout.setSizeFull();
//		
//		VerticalLayout infoBox = new VerticalLayout();
//		infoBox.setMargin(true);
//		Label infoText1 = new Label ("<h3>Deterministische Parameter:</h3>");
//		infoText1.setContentMode(Label.CONTENT_XHTML);
//		Label infoText2 = new Label ("Hier geben Sie das Basisjahr an, auf welches die künftigen Cashflows abgezinst werden. "
//				+ "Zusätzlich müssen Sie auch die Anzahl der einzubeziehenden zukünftigen Perioden ange-ben. "
//				+ "Beachten Sie hierbei, dass Sie nur so viele Perioden angeben, "
//				+ "wie Sie Daten über die Cashflows des Unternehmens haben.");
//		Label infoText3 = new Label  ("<h3>Stochastische Parameter:</h3>");
//		infoText3.setContentMode(Label.CONTENT_XHTML);
//		Label infoText4 = new Label ("Hier geben Sie das Basisjahr an, auf welches die künftigen Cashflows abgezinst werden. "
//				+ "Zusätzlich müssen Sie auch die Anzahl der zu prognostizierenden Perioden angeben. "
//				+ "Die Prognose erfolgt auf Basis vergangener Werte, daher müssen Sie auch die Anzahl der ver-gangenen Perioden angeben. "
//				+ "Beachten Sie hierbei, dass Sie nur so viele Perioden angeben, wie Sie Daten über die Cashflows des Unternehmens haben. "
//				+ "Des Weiteren haben Sie die Möglichkeit die Anzahl der Wiederholungen anzugeben. "
//				+ "Info: Je mehr Iterationen Sie durchführen lassen, desto genauer werden die Prognosewerte, aber desto länger dauert die Berechnung.");
//		
//		infoBox.addComponent(infoText1);
//		infoBox.addComponent(infoText2);
//		infoBox.addComponent(infoText3);
//		infoBox.addComponent(infoText4);
//		
//		setFirstComponent(gridLayout);
//		setSecondComponent(infoBox);
//
//		// Heading 1
//		labelHeadingCommon = new Label("Allgemein");
//		//gridLayout.addComponent(labelHeadingCommon, 0, 0);
//
//		labelBasisYear = new Label("Basisjahr");
//		gridLayout.addComponent(labelBasisYear, 0, 1);
//
//		textfieldBasisYear = new TextField();
//		textfieldBasisYear.setImmediate(true);
//		textfieldBasisYear.setDescription(toolTipBasisYear);
//		textfieldBasisYear.addListener(new Property.ValueChangeListener() {
//			private static final long serialVersionUID = 1L;
//
//			public void valueChange(ValueChangeEvent event) {
//				presenter.basisYearChosen((String) textfieldBasisYear
//						.getValue());
//			}
//		});
//		gridLayout.addComponent(textfieldBasisYear, 1, 1);
//
//		// Heading 2
//		labelHeadingMethDet = new Label("Stochastische Parameter:");
//		gridLayout.addComponent(labelHeadingMethDet, 0, 3);
//
//		labelNumPeriods = new Label("Anzahl zu prognostizierender Perioden");
//		gridLayout.addComponent(labelNumPeriods, 0, 4);
//
//		textfieldNumPeriodsToForecast = new TextField();
//		textfieldNumPeriodsToForecast.setImmediate(true);
//		textfieldNumPeriodsToForecast
//				.setDescription(toolTipNumPeriodsToForecast);
//		textfieldNumPeriodsToForecast
//				.addListener(new Property.ValueChangeListener() {
//					private static final long serialVersionUID = 1L;
//
//					public void valueChange(ValueChangeEvent event) {
//						presenter
//								.numberPeriodsToForecastChosen((String) textfieldNumPeriodsToForecast
//										.getValue());
//					}
//				});
//		gridLayout.addComponent(textfieldNumPeriodsToForecast, 1, 4);
//
//		labelUnitQuantity = new Label("Anzahl");
//		gridLayout.addComponent(labelUnitQuantity, 2, 4);
//
//		labelIterations = new Label("Durchläufe / Iterationen");
//		gridLayout.addComponent(labelIterations, 0, 5);
//
//		textfieldIterations = new TextField();
//		textfieldIterations.setImmediate(true);
//		// textfieldIterations.setValue(10000);
//		textfieldIterations.setDescription(toolTipIterations);
//		textfieldIterations.addListener(new Property.ValueChangeListener() {
//			private static final long serialVersionUID = 1L;
//
//			public void valueChange(ValueChangeEvent event) {
//				logger.debug(textfieldIterations.getValue());
//				presenter.iterationChosen((String) textfieldIterations
//						.getValue());
//			}
//		});
//		gridLayout.addComponent(textfieldIterations, 1, 5);
//
//		labelUnitQuantity = new Label("Anzahl");
//		gridLayout.addComponent(labelUnitQuantity, 2, 5);
//		
//		
//		
//		labelNumSpecifiedPastPeriods = new Label(
//				"Anzahl anzugebender, vergangener Perioden");
//		gridLayout.addComponent(labelNumSpecifiedPastPeriods, 0, 6);
//		
//		textfieldNumSpecifiedPastPeriods = new TextField();
//		textfieldNumSpecifiedPastPeriods.setImmediate(true);
//		// textfieldIterations.setValue(10000);
//		textfieldNumSpecifiedPastPeriods.setDescription(toolTipNumSpecifiedPastPeriods);
//		textfieldNumSpecifiedPastPeriods.addListener(new Property.ValueChangeListener() {
//			private static final long serialVersionUID = 1L;
//
//			public void valueChange(ValueChangeEvent event) {
//				logger.debug(textfieldNumSpecifiedPastPeriods.getValue());
//				presenter.specifiedPastPeriodsChosen((String) textfieldNumSpecifiedPastPeriods
//						.getValue());
//			}
//		});
//		gridLayout.addComponent(textfieldNumSpecifiedPastPeriods, 1, 6);
//
//		labelUnitQuantity = new Label("Anzahl");
//		gridLayout.addComponent(labelUnitQuantity, 2, 6);
//		
//		/**
//		 * Auskommentiert, da nicht für Zeitreihenanalyse benötigt
//		 * 
//		labelStepsPerPeriod = new Label("Schritte pro Periode");
//		gridLayout.addComponent(labelStepsPerPeriod, 0, 6);
//
//		
//		textfieldStepsPerPeriod = new TextField();
//		textfieldStepsPerPeriod.setImmediate(true);
//		textfieldStepsPerPeriod.setDescription(toolTipStepsPerPeriod);
//		textfieldStepsPerPeriod.addListener(new Property.ValueChangeListener() {
//			private static final long serialVersionUID = 1L;
//
//			public void valueChange(ValueChangeEvent event) {
//				presenter.stepsPerPeriodChosen((String) textfieldStepsPerPeriod
//						.getValue());
//			}
//		});
//		gridLayout.addComponent(textfieldStepsPerPeriod, 1, 6);
//		
//		labelUnitQuantity = new Label("Anzahl");
//		gridLayout.addComponent(labelUnitQuantity, 2, 6);
//		 */
//		// Heading 3
//		
//		labelHeadingTimeSeries = new Label("Stochastisch: Zeitreihenanalyse");
//		//gridLayout.addComponent(labelHeadingTimeSeries, 0, 8);
//
//		labelNumPastPeriods = new Label(
//				"Anzahl einbezogener, vergangener Perioden");
//		gridLayout.addComponent(labelNumPastPeriods, 0, 9);
//
//		textfieldNumPastPeriods = new TextField();
//		textfieldNumPastPeriods.setImmediate(true);
//		// textfieldNumPastPeriods: Wert darf hier nicht gesetzt werden
//		// -> über Event, sodass der Wert ins Projekt übernommen wird und nicht
//		// nur einfach angezeigt wird ohne ausgewertet werden zu können
//		// textfieldNumPastPeriods.setValue(5);
//		textfieldNumPastPeriods
//				.setDescription(toolTipNumPastPeriods
//						+ " Bitte beachten Sie, dass in dem Reiter Perioden immer eine Periode mehr angegeben werden muss. Diese zusätzliche Periode wird bei einem Berechnungsverfahren der Zeitreihenanalyse benötigt.");
//		textfieldNumPastPeriods.addListener(new Property.ValueChangeListener() {
//			private static final long serialVersionUID = 1L;
//
//			public void valueChange(ValueChangeEvent event) {
//				presenter
//						.relevantPastPeriodsChosen((String) textfieldNumPastPeriods
//								.getValue());
//			}
//		});
//		gridLayout.addComponent(textfieldNumPastPeriods, 1, 9);
//
//		labelUnitQuantity = new Label("Anzahl");
//		gridLayout.addComponent(labelUnitQuantity, 2, 9);
//
//		checkboxIndustryRepresentative = new CheckBox();
//		checkboxIndustryRepresentative
//				.setCaption("Branchenstellvertreter einbeziehen");
//		checkboxIndustryRepresentative
//				.setDescription(toolTipIndustryRepresentatives);
//		checkboxIndustryRepresentative.addListener(new ClickListener() {
//			/**
//			 * Derzeit unbenutzt, da die Funkionalitaet in der Berechnung noch
//			 * nicht hinterlegt ist.
//			 * 
//			 * @see init()-Methode dieser Klasse
//			 */
//			private static final long serialVersionUID = 1L;
//
//			@Override
//			public void buttonClick(ClickEvent event) {
//				presenter
//						.industryRepresentativeCheckBoxSelected(checkboxIndustryRepresentative
//								.booleanValue());
//			}
//		});
//		gridLayout.addComponent(checkboxIndustryRepresentative, 0, 10);
//
//		comboBoxRepresentatives = new ComboBox();
//		comboBoxRepresentatives.setImmediate(true);
//		comboBoxRepresentatives.setInputPrompt("Branche ausw\u00e4hlen");
//		comboBoxRepresentatives.setNullSelectionAllowed(false);
//		comboBoxRepresentatives.addListener(new Property.ValueChangeListener() {
//			/**
//			 * Derzeit unbenutzt, da die Funkionalitaet in der Berechnung auf
//			 * Basis von Branchenverreter in dieser Softareversion noch nicht
//			 * hinterlegt ist.
//			 * 
//			 * @see init()-Methode dieser Klasse
//			 */
//			private static final long serialVersionUID = 1L;
//
//			@Override
//			public void valueChange(ValueChangeEvent event) {
//				presenter.industryRepresentativeListItemChosen((String) event
//						.getProperty().getValue());
//				logger.debug("Branche " + event + " gewaehlt");
//			}
//		});
//
//		gridLayout.addComponent(comboBoxRepresentatives, 1, 10);
//
//		// Heading 4
//		
//		// Heading 6
//		labelHeadingMethDet = new Label("Deterministische Parameter:");
//		gridLayout.addComponent(labelHeadingMethDet, 0, 26);
//		labelNumPeriods_deterministic = new Label(
//				"Anzahl anzugebender Perioden");
//		gridLayout.addComponent(labelNumPeriods_deterministic, 0, 27);
//
//		textfieldNumPeriodsToForecast_deterministic = new TextField();
//		textfieldNumPeriodsToForecast_deterministic.setImmediate(true);
//		textfieldNumPeriodsToForecast_deterministic
//				.setDescription(toolTipNumPeriodsToForecast_deterministic);
//		textfieldNumPeriodsToForecast_deterministic
//				.addListener(new Property.ValueChangeListener() {
//					private static final long serialVersionUID = 1L;
//
//					public void valueChange(ValueChangeEvent event) {
//						presenter
//								.numberPeriodsToForecastChosen_deterministic((String) textfieldNumPeriodsToForecast_deterministic
//										.getValue());
//					}
//				});
//		gridLayout.addComponent(textfieldNumPeriodsToForecast_deterministic, 1,
//				27);
//
//		labelUnitQuantity = new Label("Anzahl");
//		gridLayout.addComponent(labelUnitQuantity, 2, 27);
//
//	}
//
//	/**
//	 * Gibt eine Fehlermeldung an den Benutzer aus.
//	 * 
//	 * @author Christian Scherer
//	 * @param message
//	 *            Fehlermeldung die der Methode zur Ausgabe uebergeben wird
//	 */
//	@Override
//	public void showErrorMessage(String message) {
//		getWindow().showNotification((String) "", message,
//				Notification.TYPE_ERROR_MESSAGE);
//	}
//
//	/**
//	 * Diese Methode graut das Textfeld 'textfieldNumPeriods' aus.
//	 * 
//	 * @author Christian Scherer
//	 * @param enabled
//	 *            true aktiviert den Kombonenten, false deaktiviert (graut aus)
//	 *            den Komponenten
//	 */
//	@Override
//	public void activatePeriodsToForecast(boolean enabled) {
//		this.textfieldNumPeriodsToForecast.setEnabled(enabled);
//
//	}
//
//	/**
//	 * Diese Methode graut das Textfeld
//	 * 'textfieldNumPeriodsToForecast_deterministic' aus.
//	 * 
//	 * @author Annika Weis
//	 * @param enabled
//	 *            true aktiviert den Kombonenten, false deaktiviert (graut aus)
//	 *            den Komponenten
//	 */
//	@Override
//	public void activatePeriodsToForecast_deterministic(boolean enabled) {
//		this.textfieldNumPeriodsToForecast_deterministic.setEnabled(enabled);
//
//	}
//
//	/**
//	 * Diese Methode graut das Textfeld 'textfieldNumSpecifiedPastPeriods' aus.
//	 * 
//	 * @author Marcel Rosenberger
//	 * @param enabled
//	 *            true aktiviert die Komponente, false deaktiviert (graut aus)
//	 *            die Komponenten
//	 */
//	@Override
//	public void activateSpecifiedPastPeriods(boolean enabled) {
//		this.textfieldNumSpecifiedPastPeriods.setEnabled(enabled);
//	}
//	
//	/**
//	 * Diese Methode graut das Textfeld 'textfieldNumPastPeriods' aus.
//	 * 
//	 * @author Christian Scherer
//	 * @param enabled
//	 *            true aktiviert den Kombonenten, false deaktiviert (graut aus)
//	 *            den Komponenten
//	 */
//	@Override
//	public void activateRelevantPastPeriods(boolean enabled) {
//		this.textfieldNumPastPeriods.setEnabled(enabled);
//	}
//
//	/**
//	 * Diese Methode graut die ComboBox 'comboBoxIteraions' aus.
//	 * 
//	 * @author Christian Scherer
//	 * @param enabled
//	 *            true aktiviert den Kombonenten, false deaktiviert (graut aus)
//	 *            den Komponenten
//	 */
//	@Override
//	public void activateIterations(boolean enabled) {
//		this.textfieldIterations.setEnabled(enabled);
//
//	}
//
//	/**
//	 * Diese Methode graut die Checkbox fuer die Branchenstellvertreter aus, da
//	 * diese Funktionalitaet noch nicht gegeben ist.
//	 * 
//	 * @author Christian Scherer
//	 * @param enabled
//	 *            true aktiviert den Kombonenten, false deaktiviert (graut aus)
//	 *            den Komponenten
//	 */
//	@Override
//	public void activateCheckboxIndustryRepresentative(boolean enabled) {
//		this.checkboxIndustryRepresentative.setEnabled(enabled);
//	}
//
//	/**
//	 * Diese Methode graut die ComboBox (DropDown-Liste) fuer die
//	 * Branchenstellvertreter aus, da diese Funktionalitaet noch nicht gegeben
//	 * ist.
//	 * 
//	 * @author Christian Scherer
//	 * @param enabled
//	 *            true aktiviert den Kombonenten, false deaktiviert (graut aus)
//	 *            den Komponenten
//	 */
//	@Override
//	public void activateComboBoxRepresentatives(boolean enabled) {
//		this.comboBoxRepresentatives.setEnabled(enabled);
//	}
//
//	/**
//	 * Setzt eine Fehleranzeige an das Entsprechende Feld bzw. entfernt diese
//	 * wieder je nach Parametriesierung
//	 * 
//	 * @author Christian Scherer
//	 * @param setError
//	 *            true, wenn eine Fehleranzeige gezeigt werden soll und false,
//	 *            wenn die Fehleranzeige geloescht werden soll
//	 * @param component
//	 *            Identifiziert den Componenten, bei dem die Fehleranzeige
//	 *            angezeigt bzw. entfernt werden soll
//	 * @param message
//	 *            Fehlermeldung die neben dem Componenten gezeigt werden soll
//	 */
//	@Override
//	public void setComponentError(boolean setError, String component,
//			String message) {
//		if (component.equals("periodsToForecast")) {
//			if (setError) {
//				this.textfieldNumPeriodsToForecast
//						.setComponentError(new UserError(message));
//			} else {
//				this.textfieldNumPeriodsToForecast.setComponentError(null);
//			}
//		}else if (component.equals("specifiedPastPeriods")) {
//			if (setError) {
//				this.textfieldNumSpecifiedPastPeriods.setComponentError(new UserError(
//						message));
//			} else {
//				this.textfieldNumSpecifiedPastPeriods.setComponentError(null);
//			}
//		} else if (component.equals("relevantPastPeriods")) {
//			if (setError) {
//				this.textfieldNumPastPeriods.setComponentError(new UserError(
//						message));
//			} else {
//				this.textfieldNumPastPeriods.setComponentError(null);
//			}
//		} else if (component.equals("basisYear")) {
//			if (setError) {
//				this.textfieldBasisYear
//						.setComponentError(new UserError(message));
//			} else {
//				this.textfieldBasisYear.setComponentError(null);
//			}
//
//		} else if (component.equals("iterations")) {
//			if (setError) {
//				this.textfieldIterations.setComponentError(new UserError(
//						message));
//			} else {
//				this.textfieldIterations.setComponentError(null);
//			}
//
//		} else if (component.equals("cashFlowStepRange")) {
//			if (setError) {
//				this.textfieldCashFlowStepRange
//						.setComponentError(new UserError(message));
//			} else {
//				this.textfieldCashFlowStepRange.setComponentError(null);
//			}
//
//		} else if (component.equals("cashFlowProbabilityOfRise")) {
//			if (setError) {
//				this.textfieldCashFlowProbabilityOfRise
//						.setComponentError(new UserError(message));
//			} else {
//				this.textfieldCashFlowProbabilityOfRise.setComponentError(null);
//			}
//
//		} else if (component.equals("borrowedCapitalStepRange")) {
//			if (setError) {
//				this.textfieldBorrowedCapitalStepRange
//						.setComponentError(new UserError(message));
//			} else {
//				this.textfieldBorrowedCapitalStepRange.setComponentError(null);
//			}
//
//		} else if (component.equals("borrowedCapitalProbabilityOfRise")) {
//			if (setError) {
//				this.textfieldBorrowedCapitalProbabilityOfRise
//						.setComponentError(new UserError(message));
//			} else {
//				this.textfieldBorrowedCapitalProbabilityOfRise
//						.setComponentError(null);
//			}
//			// Annika Weis
//		} else if (component.equals("periodsToForecast_deterministic")) {
//			if (setError) {
//				this.textfieldNumPeriodsToForecast_deterministic
//						.setComponentError(new UserError(message));
//			} else {
//				this.textfieldNumPeriodsToForecast_deterministic
//						.setComponentError(null);
//			}
//		}
//
//	}
//
//	/**
//	 * Diese Methode graut das Textfeld 'textfieldCashFlowStepRange' aus.
//	 * 
//	 * @author Christian Scherer
//	 * @param enabled
//	 *            true aktiviert den Kombonenten, false deaktiviert (graut aus)
//	 *            den Komponenten
//	 */
//	@Override
//	public void activateCashFlowStepRang(boolean enabled) {
//		this.textfieldCashFlowStepRange.setEnabled(enabled);
//
//	}
//
//	/**
//	 * Diese Methode graut das Textfeld 'textfieldCashFlowProbabilityOfRise'
//	 * aus.
//	 * 
//	 * @author Christian Scherer
//	 * @param enabled
//	 *            true aktiviert den Kombonenten, false deaktiviert (graut aus)
//	 *            den Komponenten
//	 */
//	@Override
//	public void activateCashFlowProbabilityOfRise(boolean enabled) {
//		this.textfieldCashFlowProbabilityOfRise.setEnabled(enabled);
//
//	}
//
//	/**
//	 * Diese Methode graut das Textfeld
//	 * 'textfieldBorrowedCapitalProbabilityOfRise' aus.
//	 * 
//	 * @author Christian Scherer
//	 * @param enabled
//	 *            true aktiviert den Kombonenten, false deaktiviert (graut aus)
//	 *            den Komponenten
//	 */
//	@Override
//	public void activateBorrowedCapitalProbabilityOfRise(boolean enabled) {
//		this.textfieldBorrowedCapitalProbabilityOfRise.setEnabled(enabled);
//
//	}
//
//	/**
//	 * Diese Methode graut das Textfeld 'textfieldBorrowedCapitalStepRange' aus.
//	 * 
//	 * @author Christian Scherer
//	 * @param enabled
//	 *            true aktiviert den Kombonenten, false deaktiviert (graut aus)
//	 *            den Komponenten
//	 */
//	@Override
//	public void activateBorrowedCapitalStepRange(boolean enabled) {
//		this.textfieldBorrowedCapitalStepRange.setEnabled(enabled);
//
//	}
//
//	/**
//	 * Diese Methode graut das Textfeld 'textfieldRiseOfPeriods' aus.
//	 * 
//	 * @author Christian Scherer
//	 * @param enabled
//	 *            true aktiviert den Kombonenten, false deaktiviert (graut aus)
//	 *            den Komponenten
//	 */
//	@Override
//	public void activateRiseOfPeriods(boolean enabled) {
//		this.textfieldRiseOfPeriods.setEnabled(enabled);
//
//	}
//
//	/**
//	 * Diese Methode graut die Checkbox 'checkboxRiseOfPeriods' aus.
//	 * 
//	 * @author Christian Scherer
//	 * @param enabled
//	 *            true aktiviert den Kombonenten, false deaktiviert (graut aus)
//	 *            den Komponenten
//	 */
//	@Override
//	public void activateRiseOfPeriodsCheckbox(boolean enabled) {
//		this.checkboxRiseOfPeriods.setEnabled(enabled);
//
//	}
//
//	/**
//	 * Diese Methode graut das Textfeld 'textfieldDeviaton' aus.
//	 * 
//	 * @author Christian Scherer
//	 * @param enabled
//	 *            true aktiviert den Kombonenten, false deaktiviert (graut aus)
//	 *            den Komponenten
//	 */
//	@Override
//	public void activateDeviation(boolean enabled) {
//		this.textfieldDeviation.setEnabled(enabled);
//
//	}
//
//	/**
//	 * Diese Methode graut die Checkbos 'checkboxDeviationOfPeriods' aus.
//	 * 
//	 * @author Christian Scherer
//	 * @param enabled
//	 *            true aktiviert den Kombonenten, false deaktiviert (graut aus)
//	 *            den Komponenten
//	 */
//	@Override
//	public void activateDeviationCheckbox(boolean enabled) {
//		this.checkboxDeviationOfPeriods.setEnabled(enabled);
//
//	}
//
//	/**
//	 * Diese Methode graut das Textfeld 'textfieldStepsPerPeriod' aus.
//	 * 
//	 * @author Christian Scherer
//	 * @param enabled
//	 *            true aktiviert den Kombonenten, false deaktiviert (graut aus)
//	 *            den Komponenten
//	 */
//	@Override
//	public void activateStepsPerPeriod(boolean enabled) {
//		this.textfieldStepsPerPeriod.setEnabled(false);
//	}
//
//	/**
//	 * Diese Methode graut das Textfeld 'Schrittweite' aus.
//	 * 
//	 * @author Christian Scherer
//	 * @param enabled
//	 *            true aktiviert den Kombonenten, false deaktiviert (graut aus)
//	 *            den Komponenten
//	 */
//	@Override
//	public void activateStepRange(boolean enabled) {
//		this.textfieldStepRange.setEnabled(enabled);
//	}
//
//	/**
//	 * Diese Methode graut das Textfeld 'Wahrscheinlichkeit' aus.
//	 * 
//	 * @author Christian Scherer
//	 * @param enabled
//	 *            true aktiviert den Kombonenten, false deaktiviert (graut aus)
//	 *            den Komponenten
//	 */
//	@Override
//	public void activateProbability(boolean enabled) {
//		this.textfieldProbability.setEnabled(enabled);
//	}
//
//	/**
//	 * Diese Methode graut das Textfeld 'Steigung aus angegebenen Perioden
//	 * ermitteln' aus.
//	 * 
//	 * @author Christian Scherer
//	 * @param enabled
//	 *            true aktiviert den Kombonenten, false deaktiviert (graut aus)
//	 *            den Komponenten
//	 */
//	@Override
//	public void activateCalculateStepRange(boolean enabled) {
//		this.checkboxCalculateStepRange.setEnabled(enabled);
//	}
//
//	/**
//	 * Setzt den Wert des Texfelds 'Basisjahr'
//	 * 
//	 * @author Christian Scherer
//	 * @param basisYear
//	 *            Das Jahr, das Basis-Jahr, auf das die Cashflows abgezinst
//	 *            werden
//	 */
//	@Override
//	public void setValueBasisYear(String basisYear) {
//		this.textfieldBasisYear.setValue(basisYear);
//	}
//
//	/**
//	 * Setzt den Wert des Texfelds 'Anzahl zu prognostizierender Perioden'
//	 * 
//	 * @author Christian Scherer
//	 * @param periodsToForecast
//	 *            Anzahl zu prognostizierender Perioden
//	 */
//	@Override
//	public void setPeriodsToForecast(String periodsToForecast) {
//		this.textfieldNumPeriodsToForecast.setValue(periodsToForecast);
//	}
//
//	/**
//	 * Setzt den Wert des Texfelds 'Anzahl zu prognostizierender Perioden' bei
//	 * den deterministischen Verfahren
//	 * 
//	 * @author Annika Weis
//	 * @param periodsToForecast_deterministic
//	 *            Anzahl zu prognostizierender Perioden (deterministisch)
//	 */
//	@Override
//	public void setPeriodsToForecast_deterministic(
//			String periodsToForecast_deterministic) {
//		this.textfieldNumPeriodsToForecast_deterministic
//				.setValue(periodsToForecast_deterministic);
//
//	}
//
//	/**
//	 * Setzt den Wert des Texfelds 'Anzahl Wiederholungen'
//	 * 
//	 * @author Christian Scherer
//	 * @param iterations
//	 *            Anzahl Wiederholungen
//	 */
//	@Override
//	public void setIterations(String iterations) {
//		this.textfieldIterations.setValue(iterations);
//	}
//	
//	/**
//	 * Setzt den Wert des Texfelds 'Anzahl anzugebender, vergangener Perioden'
//	 * 
//	 * @author Marcel Rosenberger
//	 * @param specifiedPastPeriods
//	 *            Anzahl einbezogener, vergangener Perioden
//	 */
//	@Override
//	public void setSpecifiedPastPeriods(String specifiedPastPeriods) {
//		this.textfieldNumSpecifiedPastPeriods.setValue(specifiedPastPeriods);
//	}
//
//	/**
//	 * Setzt den Wert des Texfelds 'Anzahl einbezogener, vergangener Perioden'
//	 * 
//	 * @author Christian Scherer
//	 * @param relevantPastPeriods
//	 *            Anzahl einbezogener, vergangener Perioden
//	 */
//	@Override
//	public void setRelevantPastPeriods(String relevantPastPeriods) {
//		this.textfieldNumPastPeriods.setValue(relevantPastPeriods);
//	}
//
//	/**
//	 * Setzt den Wert des Texfelds 'Schrittweite Cashflows'
//	 * 
//	 * @author Christian Scherer
//	 * @param cashFlowStepRange
//	 *            Schrittweite der Cashflows
//	 */
//	@Override
//	public void setCashFlowStepRange(String cashFlowStepRange) {
//		this.textfieldCashFlowStepRange.setValue(cashFlowStepRange);
//	}
//
//	/**
//	 * Setzt den Wert des Texfelds 'Wahrscheinlichkeit für steigende
//	 * Fremdkaptialentwicklung'
//	 * 
//	 * @author Christian Scherer
//	 * @param borrowedCapitalProbabilityOfRise
//	 *            Wahrscheinlichkeit für steigende Fremdkaptialentwicklung
//	 */
//	@Override
//	public void setBorrowedCapitalProbabilityOfRise(
//			String borrowedCapitalProbabilityOfRise) {
//		this.textfieldBorrowedCapitalProbabilityOfRise
//				.setValue(borrowedCapitalProbabilityOfRise);
//	}
//
//	/**
//	 * Setzt den Wert des Texfelds 'Schrittweite Fremdkapital'
//	 * 
//	 * @author Christian Scherer
//	 * @param borrowedCapitalStepRange
//	 *            Schrittweite Fremdkapital
//	 */
//	@Override
//	public void setBorrowedCapitalStepRange(String borrowedCapitalStepRange) {
//		this.textfieldBorrowedCapitalStepRange
//				.setValue(borrowedCapitalStepRange);
//	}
//
//	/**
//	 * Setzt den Wert des Texfelds 'Wahrscheinlichkeit für steigende
//	 * Cashflowentwicklung'
//	 * 
//	 * @author Christian Scherer
//	 * @param cashFlowProbabilityOfRise
//	 *            Wahrscheinlichkeit für steigende Cashflowentwicklung
//	 */
//	@Override
//	public void setCashFlowProbabilityOfRise(String cashFlowProbabilityOfRise) {
//		this.textfieldCashFlowProbabilityOfRise
//				.setValue(cashFlowProbabilityOfRise);
//	}
//
//}
