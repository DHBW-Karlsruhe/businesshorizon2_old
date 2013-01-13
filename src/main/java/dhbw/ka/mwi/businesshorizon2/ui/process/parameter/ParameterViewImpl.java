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

	private Label labelStochMeth;
	private Label labelNumPeriods;
	private Label labelIterations;
	private Label labelNumPastPeriods;
	private Label labelBasisYear;
	private TextField textfieldNumPeriods;
	private TextField textfieldNumPastPeriods;
	private TextField textfieldBasisYear;
	private ComboBox comboBoxIterations;
	private ComboBox comboBoxRepresentatives;
	private CheckBox checkboxIndustryRepresentative;

	private int[] numberIterations;

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
		//presenter.setIterations();
		generateUi();
		logger.debug("Ui erstellt");
		//presenter.initializeBasisYear();
		//presenter.greyOut();
		logger.debug("Init-Methode beendet");
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

		gridLayout = new GridLayout(2, 8);
		gridLayout.setMargin(true);
		gridLayout.setSpacing(true);
		addComponent(gridLayout);

		labelStochMeth = new Label("Stochastische Methoden: Allgemeines");
		gridLayout.addComponent(labelStochMeth, 0, 0);

		labelNumPeriods = new Label("Anzahl zu prognostizierender Perioden:");
		gridLayout.addComponent(labelNumPeriods, 0, 1);

		textfieldNumPeriods = new TextField();
		textfieldNumPeriods.setImmediate(true);
		textfieldNumPeriods.addListener(new Property.ValueChangeListener() {
			private static final long serialVersionUID = 1L;

			public void valueChange(ValueChangeEvent event) {
				presenter
						.numberPeriodsToForecastChosen((String) textfieldNumPeriods
								.getValue());
			}
		});
		gridLayout.addComponent(textfieldNumPeriods, 1, 1);

		labelIterations = new Label("Anzahl Wiederholungen");
		gridLayout.addComponent(labelIterations, 0, 2);

		comboBoxIterations = new ComboBox();
		numberIterations = presenter.getNumberIterations();
		for (int i = 0; i < numberIterations.length; i++) {
			comboBoxIterations.addItem(numberIterations[i]);
		}
		comboBoxIterations.setNullSelectionAllowed(false);
		comboBoxIterations.setImmediate(true);
		comboBoxIterations
				.setDescription("Je h\u00F6her die Anzahl der Wiederholungen, desto genauer wird die Vorhersage.");
		comboBoxIterations.addListener(new Property.ValueChangeListener() {

			/**
			 * Listener der Wiederholungs(Iterations)-Combobox. Es wird
			 * daraufhin mit dem gewaehlten Wert die entsprechende
			 * Presnter-Methode gerufen
			 * 
			 * @author Christian Scherer
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				presenter.iterationChosen((int) event.getProperty().getValue());
			}
		});
		gridLayout.addComponent(comboBoxIterations, 1, 2);

		labelStochMeth = new Label("Stochastisch: Zeitreihenanalyse");
		gridLayout.addComponent(labelStochMeth, 0, 3);

		labelNumPastPeriods = new Label(
				"Anzahl einbezogener, vergangener Perioden:");
		gridLayout.addComponent(labelNumPastPeriods, 0, 4);

		textfieldNumPastPeriods = new TextField();
		textfieldNumPastPeriods.setImmediate(true);
		textfieldNumPastPeriods.addListener(new Property.ValueChangeListener() {
			private static final long serialVersionUID = 1L;

			public void valueChange(ValueChangeEvent event) {
				presenter
						.relevantPastPeriodsChosen((String) textfieldNumPastPeriods
								.getValue());
			}
		});
		gridLayout.addComponent(textfieldNumPastPeriods, 1, 4);

		checkboxIndustryRepresentative = new CheckBox();
		checkboxIndustryRepresentative
				.setCaption("Branchenstellvertreter mit einbeziehen");
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
		gridLayout.addComponent(checkboxIndustryRepresentative, 0, 5);

		comboBoxRepresentatives = new ComboBox();
		comboBoxRepresentatives.setImmediate(true);
		comboBoxRepresentatives.setInputPrompt("Bitte Branche wählen");
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

		gridLayout.addComponent(comboBoxRepresentatives, 0, 6);

		labelBasisYear = new Label("Wahl des Basisjahr");
		gridLayout.addComponent(labelBasisYear, 0, 7);

		textfieldBasisYear = new TextField();
		textfieldBasisYear.setImmediate(true);
		textfieldBasisYear
				.setDescription("Das Basisjahr ist das Jahr, auf das die Cashflows abgezinst werden und somit den Unternehmenswert darstellen. Bedenken Sie, dass Sie alle Perioden bis zu diesem Jahr ausf\u00FCllen m\u00FCssen!");
		textfieldBasisYear.addListener(new Property.ValueChangeListener() {
			private static final long serialVersionUID = 1L;

			public void valueChange(ValueChangeEvent event) {
				presenter.basisYearChosen((String) textfieldBasisYear
						.getValue());
			}
		});
		gridLayout.addComponent(textfieldBasisYear, 1, 7);

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
	 * Diese Methode setzt das Array der Iteraionsschritte, welches spaeter fuer
	 * die Benutzerangebe noetig ist
	 * 
	 * @author Christian Scherer
	 * @param numberIterations
	 *            Das Iteraionsarray mit Werte 1.000 10.000 und 1000.000
	 */
	@Override
	public void setIterations(int[] numberIterations) {
		this.numberIterations = numberIterations;

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
		this.textfieldNumPeriods.setEnabled(enabled);

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
		this.comboBoxIterations.setEnabled(enabled);

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
				this.textfieldNumPeriods.setComponentError(new UserError(
						message));
			} else {
				this.textfieldNumPeriods.setComponentError(null);
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
				this.comboBoxIterations
						.setComponentError(new UserError(message));
			} else {
				this.comboBoxIterations.setComponentError(null);
			}

		}

	}

}
