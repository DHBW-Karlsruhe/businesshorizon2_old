/*******************************************************************************
 * BusinessHorizon2
 *
 * Copyright (C) 
 * 2012-2013 Christian Gahlert, Florian Stier, Kai Westerholz,
 * Timo Belz, Daniel Dengler, Katharina Huber, Christian Scherer, Julius Hacker
 * 2013-2014 Marcel Rosenberger, Mirko Göpfrich, Annika Weis, Katharina Narlock, 
 * Volker Meier
 * 
 *
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package dhbw.ka.mwi.businesshorizon2.ui.parameterScreen.input;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.terminal.UserError;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window.Notification;

/**
 * Diese Klasse implementiert das GUI fuer den Prozessschritt "Parameter" in
 * Vaadin.
 * 
 * @author Julius Hacker, Christian Scherer, Tobias Lindner
 * 
 */
public class ParameterInputViewImpl extends VerticalLayout implements ParameterInputViewInterface {
	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger("ParameterViewImpl.class");
	
	private GridLayout gridLayout;

	private Label labelNumPeriods;
	private Label labelNumPeriods_deterministic; // Annika Weis
	private Label labelIterations;
	private Label labelNumPastPeriods;
	private Label labelNumSpecifiedPastPeriods;
	private Label labelBasisYear;
	
	private Embedded questionIconBasisYear;	
	private Embedded questionIconNumPeriods;
	private Embedded questionIconIterations;
	private Embedded questionIconNumPastPeriods;
	private Embedded questionIconNumSpecifiedPastPeriods;
	private Embedded questionIconNumPeriods_deterministic;

	private TextField textfieldNumPeriodsToForecast;
	private TextField textfieldNumPeriodsToForecast_deterministic;
	private TextField textfieldNumPastPeriods;
	private TextField textfieldNumSpecifiedPastPeriods;
	private TextField textfieldBasisYear;
	private TextField textfieldIterations;

	private String toolTipBasisYear;
	private String toolTipIterations;
	private String toolTipNumPeriodsToForecast;
	private String toolTipNumPeriodsToForecast_deterministic;
	private String toolTipNumPastPeriods;
	private String toolTipNumSpecifiedPastPeriods;

	@Autowired
	private ParameterInputPresenter presenter;

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
	 * @author Christian Scherer, Tobias Lindner
	 */
	private void setTooltips() {
		toolTipBasisYear = "Hier wird das Basisjahr angegeben, auf welches die k\u00fcnftigen Cashflows abgezinst werden. Der Unternehmenswert wird zu dem hier angegebenen Zeitpunkt bestimmt.";
		toolTipIterations = "Hier k\u00f6nnen Sie sich entscheiden, wie oft sie die Berechnung der Prognosewerte durchf\u00fchren wollen. Info: Je mehr Wiederholungen Sie durchf\u00fchren lassen, desto genauer werden die Prognosewerte, aber desto l\u00e4nger wird die Berechnung.";
		toolTipNumPeriodsToForecast = "Hier tragen Sie die Anzahl der zu prognostizierenden Methoden ein. Info: Haben Sie sich zus\u00e4tzlich für die deterministische Angabe entschieden, entspricht die hier eingetragene Zahl auch der Anzahl der Perioden, die sie deterministisch angeben m\u00fcssen.";
		toolTipNumPeriodsToForecast_deterministic = "Hier tragen Sie die Anzahl der zu prognostizierenden deterministischen Methoden ein. Info: Haben Sie sich zus\u00e4tzlich für die deterministische Angabe entschieden, entspricht die hier eingetragene Zahl auch der Anzahl der Perioden, die sie deterministisch angeben m\u00fcssen.";
		toolTipNumPastPeriods = "Hier geben Sie an, wie viele vergangene Perioden für die Berechnung des Prognosewert gewichtet werden sollen. Info: Für die Berechnung m\u00fcssen Sie im n\u00e4chsten Prozessschritt immer eine Periode mehr angeben, als Sie hier eingeben. Bitte beachten Sie, dass in dem Reiter Perioden immer eine Periode mehr angegeben werden muss. Diese zusätzliche Periode wird bei einem Berechnungsverfahren der Zeitreihenanalyse benötigt.";
		toolTipNumSpecifiedPastPeriods ="Bitte beachten Sie, dass die Anzahl anzugebender Perioden immer um mindestens eins größer sein muss als die Anzahl der einbezogenen Perioden. Diese zusätzliche Periode wird bei einem Berechnungsverfahren der Zeitreihenanalyse benötigt.";
	}

	/**
	 * Erstelle das GUI zum Prozessschritt "Parameter". Der Aufbau der View
	 * findet mit dem Gridlayout statt um zu garantieren, dass die Eingabefelder
	 * alle auf einer Hoeher auftauchen. 
	 * 
	 * @author Julius Hacker, Christian Scherer, Tobias Lindner
	 */

	@Override
	public void showParameterView() {
		this.removeAllComponents();
		generateUi();
	}

	private void generateUi() {
		setMargin(true);
		//setSizeFull();
		
		gridLayout = new GridLayout(3, 30);
		gridLayout.setSpacing(true);
		gridLayout.setSizeUndefined();
		gridLayout.setStyleName("parameter");

		addComponent(gridLayout);
		
		//Höhe des FragezeichenIcons
		String heightQuestionIcon = "20px";

		// Basisjahr

		labelBasisYear = new Label("Basisjahr");
		gridLayout.addComponent(labelBasisYear, 0, 1);
		
		textfieldBasisYear = new TextField();
		textfieldBasisYear.setImmediate(true);
		textfieldBasisYear.addListener(new Property.ValueChangeListener() {
			private static final long serialVersionUID = 1L;

			public void valueChange(ValueChangeEvent event) {
				presenter.basisYearChosen((String) textfieldBasisYear
						.getValue());
			}
		});
		gridLayout.addComponent(textfieldBasisYear, 1, 1);
		
		labelBasisYear.setStyleName("parameter");
		textfieldBasisYear.setStyleName("parameter");
		
		questionIconBasisYear = new Embedded (null, new ThemeResource("./images/icons/newIcons/1418765983_circle_help_question-mark-128.png"));
		questionIconBasisYear.setHeight(heightQuestionIcon);
		questionIconBasisYear.setStyleName("questionIcon");
		questionIconBasisYear.setDescription(toolTipBasisYear);
		
		gridLayout.addComponent(questionIconBasisYear, 2, 1);

		
		//Anzahl zu prognistizierender Perioden
		labelNumPeriods = new Label("Anzahl zu prognostizierender Perioden");
		gridLayout.addComponent(labelNumPeriods, 0, 2);
		
		textfieldNumPeriodsToForecast = new TextField();
		textfieldNumPeriodsToForecast.setImmediate(true);
		textfieldNumPeriodsToForecast
				.addListener(new Property.ValueChangeListener() {
					private static final long serialVersionUID = 1L;

					public void valueChange(ValueChangeEvent event) {
						presenter
								.numberPeriodsToForecastChosen((String) textfieldNumPeriodsToForecast
										.getValue());
					}
				});

		gridLayout.addComponent(textfieldNumPeriodsToForecast, 1, 2);
		
		labelNumPeriods.setStyleName("parameter");
		textfieldNumPeriodsToForecast.setStyleName("parameter");
		
		questionIconNumPeriods = new Embedded (null, new ThemeResource("./images/icons/newIcons/1418765983_circle_help_question-mark-128.png"));
		questionIconNumPeriods.setHeight(heightQuestionIcon);
		questionIconNumPeriods.setStyleName("questionIcon");
		questionIconNumPeriods.setDescription(toolTipNumPeriodsToForecast);
		
		gridLayout.addComponent(questionIconNumPeriods, 2,2);
		
		//Anzahl der Iterationen
		labelIterations = new Label("Anzahl der Iterationen");
		gridLayout.addComponent(labelIterations, 0, 3);
		
		textfieldIterations = new TextField();
		textfieldIterations.setImmediate(true);
		textfieldIterations.addListener(new Property.ValueChangeListener() {
			private static final long serialVersionUID = 1L;

			public void valueChange(ValueChangeEvent event) {
				logger.debug(textfieldIterations.getValue());
				presenter.iterationChosen((String) textfieldIterations
						.getValue());
			}
		});
		gridLayout.addComponent(textfieldIterations, 1, 3);
		
		labelIterations.setStyleName("parameter");
		textfieldIterations.setStyleName("parameter");
		
		questionIconIterations = new Embedded (null, new ThemeResource("./images/icons/newIcons/1418765983_circle_help_question-mark-128.png"));
		questionIconIterations.setHeight(heightQuestionIcon);
		questionIconIterations.setStyleName("questionIcon");
		questionIconIterations.setDescription(toolTipIterations);
		
		gridLayout.addComponent(questionIconIterations, 2, 3);
		
		
		//Anzahl einbezogener vergangener Perioden
		labelNumPastPeriods = new Label("Anzahl einbezogener vergangener Perioden");
		gridLayout.addComponent(labelNumPastPeriods, 0, 4);
		
		textfieldNumPastPeriods = new TextField();
		textfieldNumPastPeriods.setImmediate(true);
		// textfieldNumPastPeriods: Wert darf hier nicht gesetzt werden
		// -> über Event, sodass der Wert ins Projekt übernommen wird und nicht
		// nur einfach angezeigt wird ohne ausgewertet werden zu können
		// textfieldNumPastPeriods.setValue(5);
		textfieldNumPastPeriods.addListener(new Property.ValueChangeListener() {
			private static final long serialVersionUID = 1L;

			public void valueChange(ValueChangeEvent event) {
				presenter
						.relevantPastPeriodsChosen((String) textfieldNumPastPeriods
								.getValue());
			}
		});

		gridLayout.addComponent(textfieldNumPastPeriods, 1, 4);
		
		labelNumPastPeriods.setStyleName("parameter");
		textfieldNumPastPeriods.setStyleName("parameter");
		
		questionIconNumPastPeriods = new Embedded (null, new ThemeResource("./images/icons/newIcons/1418765983_circle_help_question-mark-128.png"));
		questionIconNumPastPeriods.setHeight(heightQuestionIcon);
		questionIconNumPastPeriods.setStyleName("questionIcon");
		questionIconNumPastPeriods.setDescription(toolTipNumPastPeriods);
		
		gridLayout.addComponent(questionIconNumPastPeriods, 2, 4);
		
		
		// Anzahl anzugebender, vergangener Perioden
//		labelNumSpecifiedPastPeriods = new Label("Anzahl anzugebender, vergangener Perioden");
//		gridLayout.addComponent(labelNumSpecifiedPastPeriods, 0, 5);
//		
//		textfieldNumSpecifiedPastPeriods = new TextField();
//		textfieldNumSpecifiedPastPeriods.setImmediate(true);
//		textfieldNumSpecifiedPastPeriods.addListener(new Property.ValueChangeListener() {
//			private static final long serialVersionUID = 1L;
//
//			public void valueChange(ValueChangeEvent event) {
//				logger.debug(textfieldNumSpecifiedPastPeriods.getValue());
//				presenter.specifiedPastPeriodsChosen((String) textfieldNumSpecifiedPastPeriods
//						.getValue());
//			}
//		});
//		
//		gridLayout.addComponent(textfieldNumSpecifiedPastPeriods, 1, 5);
//		
//		labelNumSpecifiedPastPeriods.setStyleName("parameter");
//		textfieldNumSpecifiedPastPeriods.setStyleName("parameter");
//		
//		questionIconNumSpecifiedPastPeriods = new Embedded (null, new ThemeResource("./images/icons/newIcons/1418765983_circle_help_question-mark-128.png"));
//		questionIconNumSpecifiedPastPeriods.setHeight(heightQuestionIcon);
//		questionIconNumSpecifiedPastPeriods.setStyleName("questionIcon");
//		questionIconNumSpecifiedPastPeriods.setDescription(toolTipNumSpecifiedPastPeriods);
//		
//		gridLayout.addComponent(questionIconNumSpecifiedPastPeriods, 2, 5);
		
		
		// Deterministische Parameter
		
		//Anzahl anzugebender Perioden
		labelNumPeriods_deterministic = new Label("Anzahl Perioden");
		gridLayout.addComponent(labelNumPeriods_deterministic, 0, 7);
		
		textfieldNumPeriodsToForecast_deterministic = new TextField();
		textfieldNumPeriodsToForecast_deterministic.setImmediate(true);
		textfieldNumPeriodsToForecast_deterministic.addListener(new Property.ValueChangeListener() {
			private static final long serialVersionUID = 1L;

			public void valueChange(ValueChangeEvent event) {
				presenter.numberPeriodsToForecastChosen_deterministic((String) textfieldNumPeriodsToForecast_deterministic
						.getValue());
			}
		});

		gridLayout.addComponent(textfieldNumPeriodsToForecast_deterministic, 1, 7);
		
		labelNumPeriods_deterministic.setStyleName("parameter");
		textfieldNumPeriodsToForecast_deterministic.setStyleName("parameter");
				
		questionIconNumPeriods_deterministic = new Embedded (null, new ThemeResource("./images/icons/newIcons/1418765983_circle_help_question-mark-128.png"));
		questionIconNumPeriods_deterministic.setHeight(heightQuestionIcon);
		questionIconNumPeriods_deterministic.setStyleName("questionIcon");	
		questionIconNumPeriods_deterministic.setDescription(toolTipNumPeriodsToForecast_deterministic);
				
		gridLayout.addComponent(questionIconNumPeriods_deterministic, 2, 7);
		
	}
	
	
	/**
	 * Die Methode entfernt die Felder, die bei einer deterministischen Berechnung nicht benötigt werden.
	 * 
	 * @author Tobias Lindner
	 */
	public void setDeterministicParameters () {
		gridLayout.removeComponent(labelNumPeriods);
		gridLayout.removeComponent(textfieldNumPeriodsToForecast);
		gridLayout.removeComponent(questionIconNumPeriods);
		
		gridLayout.removeComponent(labelIterations);
		gridLayout.removeComponent(textfieldIterations);
		gridLayout.removeComponent(questionIconIterations);
		
		gridLayout.removeComponent(labelNumPastPeriods);
		gridLayout.removeComponent(textfieldNumPastPeriods);
		gridLayout.removeComponent(questionIconNumPastPeriods);
		
	}
	
	
	/**
	 * Die Methode entfernt die Felder, die einer stochastischen Berechnung nicht benötigt werden.
	 * 
	 * @author Tobias Lindner
	 */
	public void setStochasticParameters () {
		gridLayout.removeComponent(labelNumPeriods_deterministic);
		gridLayout.removeComponent(textfieldNumPeriodsToForecast_deterministic);
		gridLayout.removeComponent(questionIconNumPeriods_deterministic);
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
				Notification.TYPE_WARNING_MESSAGE);
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
	 * Diese Methode graut das Textfeld
	 * 'textfieldNumPeriodsToForecast_deterministic' aus.
	 * 
	 * @author Annika Weis
	 * @param enabled
	 *            true aktiviert den Kombonenten, false deaktiviert (graut aus)
	 *            den Komponenten
	 */
	@Override
	public void activatePeriodsToForecast_deterministic(boolean enabled) {
		this.textfieldNumPeriodsToForecast_deterministic.setEnabled(enabled);

	}

	/**
	 * Diese Methode graut das Textfeld 'textfieldNumSpecifiedPastPeriods' aus.
	 * 
	 * @author Marcel Rosenberger
	 * @param enabled
	 *            true aktiviert die Komponente, false deaktiviert (graut aus)
	 *            die Komponenten
	 */
	@Override
	public void activateSpecifiedPastPeriods(boolean enabled) {
		this.textfieldNumSpecifiedPastPeriods.setEnabled(enabled);
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
	public void setComponentError(boolean setError, String component, String message) {
		if (component.equals("periodsToForecast")) {
			if (setError) {
				this.textfieldNumPeriodsToForecast
						.setComponentError(new UserError(message));
			} else {
				this.textfieldNumPeriodsToForecast.setComponentError(null);
			}
		}else if (component.equals("specifiedPastPeriods")) {
			if (setError) {
				this.textfieldNumSpecifiedPastPeriods.setComponentError(new UserError(
						message));
			} else {
				this.textfieldNumSpecifiedPastPeriods.setComponentError(null);
			}
		} else if (component.equals("relevantPastPeriods")) {
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
				this.textfieldIterations.setComponentError(new UserError(
						message));
			} else {
				this.textfieldIterations.setComponentError(null);
			}

		} else if (component.equals("periodsToForecast_deterministic")) {
			if (setError) {
				this.textfieldNumPeriodsToForecast_deterministic
						.setComponentError(new UserError(message));
			} else {
				this.textfieldNumPeriodsToForecast_deterministic
						.setComponentError(null);
			}
		}

	}

	/**
	 * Setzt den Wert des Texfelds 'Basisjahr'
	 * 
	 * @author Christian Scherer
	 * @param basisYear
	 *            Das Jahr, das Basis-Jahr, auf das die Cashflows abgezinst
	 *            werden
	 */
	@Override
	public void setValueBasisYear(String basisYear) {
		this.textfieldBasisYear.setValue(basisYear);
	}

	/**
	 * Setzt den Wert des Texfelds 'Anzahl zu prognostizierender Perioden'
	 * 
	 * @author Christian Scherer
	 * @param periodsToForecast
	 *            Anzahl zu prognostizierender Perioden
	 */
	@Override
	public void setPeriodsToForecast(String periodsToForecast) {
		this.textfieldNumPeriodsToForecast.setValue(periodsToForecast);
	}

	/**
	 * Setzt den Wert des Texfelds 'Anzahl zu prognostizierender Perioden' bei
	 * den deterministischen Verfahren
	 * 
	 * @author Annika Weis
	 * @param periodsToForecast_deterministic
	 *            Anzahl zu prognostizierender Perioden (deterministisch)
	 */
	@Override
	public void setPeriodsToForecast_deterministic(
			String periodsToForecast_deterministic) {
		this.textfieldNumPeriodsToForecast_deterministic
				.setValue(periodsToForecast_deterministic);

	}

	/**
	 * Setzt den Wert des Texfelds 'Anzahl Wiederholungen'
	 * 
	 * @author Christian Scherer
	 * @param iterations
	 *            Anzahl Wiederholungen
	 */
	@Override
	public void setIterations(String iterations) {
		this.textfieldIterations.setValue(iterations);
	}
	
	/**
	 * Setzt den Wert des Texfelds 'Anzahl anzugebender, vergangener Perioden'
	 * 
	 * @author Marcel Rosenberger
	 * @param specifiedPastPeriods
	 *            Anzahl einbezogener, vergangener Perioden
	 */
	@Override
	public void setSpecifiedPastPeriods(String specifiedPastPeriods) {
		this.textfieldNumSpecifiedPastPeriods.setValue(specifiedPastPeriods);
	}

	/**
	 * Setzt den Wert des Texfelds 'Anzahl einbezogener, vergangener Perioden'
	 * 
	 * @author Christian Scherer
	 * @param relevantPastPeriods
	 *            Anzahl einbezogener, vergangener Perioden
	 */
	@Override
	public void setRelevantPastPeriods(String relevantPastPeriods) {
		this.textfieldNumPastPeriods.setValue(relevantPastPeriods);
	}

}
