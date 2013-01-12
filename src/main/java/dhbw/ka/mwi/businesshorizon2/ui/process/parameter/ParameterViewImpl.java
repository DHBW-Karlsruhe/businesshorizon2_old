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
	 * und initialisiert die View-Komponenten.
	 * 
	 * 
	 * Es werden eine Methode des Presenter zur Erzeugung der
	 * Iterationsschritt-Wahl, Ausgrauen unrelevanter Felder und die generateUi
	 * Methode angestossen
	 * 
	 * @author Julius Hacker, Christian Scherer
	 */
	@PostConstruct
	public void init() {

		presenter.setView(this);
		presenter.setIterations();
		generateUi();
		logger.debug("Ui erstellt");
		presenter.initializeBasisYear();
		presenter.greyOut();
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
		// setSizeFull();
		// setSpacing(true);

		gridLayout = new GridLayout(3, 8);
		// gridLayout.setSizeFull();
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
		for (int i = 0; i < numberIterations.length; i++) {
			comboBoxIterations.addItem(numberIterations[i]);
		}
		comboBoxIterations.setNullSelectionAllowed(false);
		comboBoxIterations.setImmediate(true);
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
				presenter.industryRepresentativeListItemChosen((String)event.getProperty().getValue());
				logger.debug("Branche " + event + " gewaehlt");
			}
		});
		

		gridLayout.addComponent(comboBoxRepresentatives, 0, 6);

		labelBasisYear = new Label("Wahl des Basisjahr");
		gridLayout.addComponent(labelBasisYear, 0, 7);

		textfieldBasisYear = new TextField();
		textfieldBasisYear.setImmediate(true);
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
	 * Diese Methode graut die Checkbox fuer die Branchenstellvertreter aus, da
	 * diese Funktionalitaet noch nicht gegeben ist.
	 * 
	 * @author Christian Scherer
	 */
	@Override
	public void greyOutCheckboxIndustryRepresentative() {
		this.checkboxIndustryRepresentative.setEnabled(false);
	}

	/**
	 * Diese Methode graut die ComboBox (DropDown-Liste) fuer die
	 * Branchenstellvertreter aus, da diese Funktionalitaet noch nicht gegeben
	 * ist.
	 * 
	 * @author Christian Scherer
	 */
	@Override
	public void greyOutComboBoxRepresentatives() {
		this.comboBoxRepresentatives.setEnabled(false);
	}

	/**
	 * Loescht den gesetzten Wert des Texfelds 'Anzahl zu prognostizierender
	 * Perioden'
	 * 
	 * @author Christian Scherer
	 */
	@Override
	public void clearTextFieldnumPeriodsToForecast() {
		this.textfieldNumPeriods.setValue("");
	}

	/**
	 * Loescht den gesetzten Wert des Texfelds 'Anzahl einbezogener, vergangener
	 * Perioden'
	 * 
	 * @author Christian Scherer
	 */
	@Override
	public void clearTextFieldNumPastPeriods() {
		this.textfieldNumPastPeriods.setValue("");
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

}
