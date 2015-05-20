/*******************************************************************************
 * BusinessHorizon2
 *
 * Copyright (C) 
 * 2012-2013 Christian Gahlert, Florian Stier, Kai Westerholz,
 * Timo Belz, Daniel Dengler, Katharina Huber, Christian Scherer, Julius Hacker
 * 2013-2014 Marcel Rosenberger, Mirko Göpfrich, Annika Weis, Katharina Narlock, 
 * Volker Meier
 * 2014-2015 Marco Glaser, Tobias Lindner
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

import java.util.Calendar;
import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.mvplite.event.EventBus;
import com.mvplite.event.EventHandler;
import com.mvplite.presenter.Presenter;

import dhbw.ka.mwi.businesshorizon2.services.proxies.ProjectProxy;
import dhbw.ka.mwi.businesshorizon2.ui.initialscreen.ShowProcessStepEvent;
import dhbw.ka.mwi.businesshorizon2.ui.initialscreen.ShowProcessStepEvent.screen;
import dhbw.ka.mwi.businesshorizon2.ui.initialscreen.buttonsMiddle.ValidationEvent;
import dhbw.ka.mwi.businesshorizon2.ui.parameterScreen.input.ParameterInputViewInterface;

/**
 * Der Presenter fuer die Maske zur Eingabe der Parameter.
 * 
 * @author Julius Hacker, Christian Scherer, Tobias Lindner
 * 
 */
public class ParameterInputPresenter extends Presenter<ParameterInputViewInterface> {
	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger("ParameterInputPresenter.class");

	@Autowired
	private EventBus eventBus;

	@Autowired
	private ProjectProxy projectProxy;

	
	private boolean iterationsValid;
	private boolean basisYearValid;
	private boolean periodsToForecastValid;
	private boolean periodsToForecast_deterministicValid; //Annika Weis
	private boolean relevantPastPeriodsValid;
	private boolean specifiedPastPeriodsValid;

	private boolean stochMethod;
	private boolean randomWalk;
	private boolean wienerProcess;
	private boolean timeSeries;
	
	private boolean detMethod;
	private boolean dcf;
	private boolean apv;
	

	private double cashFlowProbabilityOfRise;
	private double cashFlowStepRange;
	private double borrowedCapitalProbabilityOfRise;
	private double borrowedCapitalStepRange;

	private boolean firstCall;
	private boolean showError;

	private boolean cashFlowStepRangeValid;
	private boolean cashFlowProbabilityOfRiseValid;
	private boolean borrowedCapitalProbabilityOfRiseValid;
	private boolean borrowedCapitalStepRangeValid;
	
	private String errorMessageBasisYear;
	private String errorMessageCashFlowStepRange;
	private String errorMessageCashFlowProbabilityOfRise;
	private String errorMessageBorrowedCapitalStepRange;
	private String errorMessageBorrowedCapitalProbabilityOfRise;
	private String errorMessagePeriodsToForecast;
	private String errorMessagePeriodsToForecast_deterministic; //Annika Weis
	private String errorMessageSpecifiedPastPeriods;
	private String errorMessageRelevantPastPeriods;
	private String errorMessageIterations;

	/**
	 * Dies ist der Konstruktor, der von Spring nach der Initialierung der
	 * Dependencies aufgerufen wird. Er registriert sich selbst als einen
	 * EventHandler. 
	 * 
	 * @author Julius Hacker, Christian SCherer, Tobias Lindner
	 */
	@PostConstruct
	public void init() {

		logger.debug("Initialisierung beendet");
		eventBus.addHandler(this);
		
		initializeErrorStrings();
		initializeVariableBooleans();

		firstCall = true;
		showError = false;
	}

	/**
	 * Diese Methode initialisiert die Validitaetsvariablen der Eingabefelder.
	 * Standardmaesig sind diese auf False gesetzt, damit leere Eingaben ebenso
	 * zu einer Fehlermeldung fuehren. Ausnahmen sind jene Eingabefelder die 
	 * einen Default Wert besitzen. Diese werden hier schon auf true gesetzt.
	 * 
	 * @author Christian Scherer
	 */
	private void initializeVariableBooleans() {
		basisYearValid = false;
		periodsToForecastValid = false;
		cashFlowStepRangeValid = false;
		cashFlowProbabilityOfRiseValid = false;
		borrowedCapitalStepRangeValid = false;
		borrowedCapitalProbabilityOfRiseValid = false;
		
		iterationsValid = true;
		relevantPastPeriodsValid = true;
		specifiedPastPeriodsValid = true;

	}

	/**
	 * Diese Methode initialisiert die Fehlermeldungen, die bei Falscheingabe
	 * oder leer lassen der Felder angezeigt wird.
	 * 
	 * @author Christian Scherer, Tobias Lindner
	 */
	private void initializeErrorStrings() {
		errorMessageBasisYear = "Bitte geben Sie ein g\u00FCltiges Jahr an, jedoch gr\u00f6ßer als 1900. Beispiel: 2013";
		errorMessageCashFlowStepRange = "Bitte geben Sie die Schrittweite der Cashflows g\u00f6\u00dfrer oder gleich 0 an. Beispiel: 100000";
		errorMessageCashFlowProbabilityOfRise = "Bitte geben Sie die Wahrscheinlichkeit f\u00fcr steigende Cashflowentwicklung zwischen 0 und 100 an. Beispiel: 50";
		errorMessageBorrowedCapitalStepRange = "Bitte geben Sie die Schrittweite des Fremdkapital g\u00f6\u00dfrer oder gleich 0 an. Beispiel: 100000";
		errorMessageBorrowedCapitalProbabilityOfRise = "Bitte geben Sie die Wahrscheinlichkeit f\u00fcr steigende Fremdkapitalentwicklung zwischen 0 und 100 an. Beispiel: 50";
		errorMessagePeriodsToForecast = "Bitte geben Sie die Anzahl vorherzusehender Perioden in einer Ganzzahl gr\u00F6\u00DFer 0 an. Beispiel: 5";
		errorMessagePeriodsToForecast_deterministic = "Bitte geben Sie die Anzahl Perioden (deterministische Verfahren) in einer Ganzzahl gr\u00F6\u00DFer 0 an. Beispiel: 5";
		errorMessageSpecifiedPastPeriods = "Bitte geben Sie die Anzahl der anzugebenden vergangenen Perioden in einer Ganzzahl gr\u00F6\u00DFer als 3 und als die Anzahl der einbezogenen vergangenen Perioden.";
		errorMessageRelevantPastPeriods = "Bitte geben Sie die Anzahl der relevanten vergangenen Perioden in einer Ganzzahl gr\u00F6\u00DFer 2 an.";
		errorMessageIterations = "Bitte w\u00E4hlen Sie die Anzahl der Wiederholungen als Ganzzahl zwischen 1000 und 100000 an. Beispiel: 10000";
	}

	/**
	 * Diese Methode wird beim Aufruf des Screens ausgeführt. Sie triggert u.a. das Auslesen von bereits gespeicherten Eingaben.
	 *  
	 * @author Tobias Lindner
	 */
	@EventHandler
	public void onShowParameterScreen(ShowProcessStepEvent event) {
		if (event.getScreen() == screen.PARAMETER) {
			
			getView().showParameterView();
	
			if (projectProxy.getSelectedProject().getBasisYear() == 0) {
				initializeBasisYear();
			}
	
			stochMethod = false;
			if (this.projectProxy.getSelectedProject().getProjectInputType() != null) {
				stochMethod = this.projectProxy.getSelectedProject()
						.getProjectInputType().isStochastic();
			} 
			
			detMethod = false;
			if (this.projectProxy.getSelectedProject().getProjectInputType() != null) {
				detMethod = this.projectProxy.getSelectedProject()
						.getProjectInputType().isDeterministic();
			} 
			
			this.setValues();
			this.setType();
			firstCall = false;
		}	
	}
	
	/**
	 * Diese Methode ersetzt entfernt nicht benötigte Felder aus der View.
	 * 
	 * @author Tobias Lindner
	 */
	private void setType () {
		if (stochMethod) {
			getView().setStochasticParameters();
		}
		
		else {
			getView().setDeterministicParameters();
		}
	}

	/**
	 * In dieser Methode werden die Werte des Objekts (falls nicht null)
	 * gelesen und in die Eingabefelder gesetzt.
	 * 
	 * @author Christian Scherer, Tobias LIndner
	 */
	private void setValues() {
		
		if(this.projectProxy.getSelectedProject().getBasisYear()!=0){
			getView().setValueBasisYear(""+this.projectProxy.getSelectedProject().getBasisYear());
		}
		if(this.projectProxy.getSelectedProject().getPeriodsToForecast()!=0){
			getView().setPeriodsToForecast(""+this.projectProxy.getSelectedProject().getPeriodsToForecast());
		}
		if(this.projectProxy.getSelectedProject().getIterations()!=0){
			getView().setIterations(""+this.projectProxy.getSelectedProject().getIterations());
		}
		if(this.projectProxy.getSelectedProject().getRelevantPastPeriods()!=0){
			getView().setRelevantPastPeriods(""+this.projectProxy.getSelectedProject().getRelevantPastPeriods());
		}
		if(this.projectProxy.getSelectedProject().getPeriodsToForecast_deterministic()!=0){
			getView().setPeriodsToForecast_deterministic(""+this.projectProxy.getSelectedProject().getPeriodsToForecast_deterministic());
		}	
	}
	
	/**
	 * Methode die sich nach der Auswahl der Iteration um die davon abhaengigen
	 * Objekte kuemmert.  Konkret wird aus dem String des
	 * Eingabefelds der Integer-Wert gezogen und geprueft ob der eingegebene
	 * Wert groesser 1990 ist. Ist einer der beiden Kriterien nicht erfuellt wird
	 * eine ClassCastException geworfen, die zu einer Fehlermeldung auf der
	 * Benutzeroberflaecher fuehrt.
	 * 
	 * 
	 * @author Christian Scherer
	 * @param iterations
	 *            Anzahl der ausgewaehlten Wiederholungen(Iterationen)
	 */
	public void iterationChosen(String iterationsString) {
	
		int iterations;
		try {
			iterations = Integer.parseInt(iterationsString);
			if (iterations >= 1000 && iterations <= 100000) {
				iterationsValid = true;
				getView().setComponentError(false, "iterations", "");
				this.projectProxy.getSelectedProject().setIterations(iterations);
				eventBus.fireEvent(new ValidationEvent(true));


			} else {
				throw new NumberFormatException();
			}
		} catch (NumberFormatException nfe) {
			iterationsValid = false;
			getView()
					.setComponentError(
							true,
							"iterations", errorMessageIterations);
			getView()
					.showErrorMessage(errorMessageIterations);
			eventBus.fireEvent(new ValidationEvent(false));

		}
	}

	/**
	 * Methode die sich nach der Auswahl der zu Vorherzusagenden Perioden um die
	 * davon abhaengigen Objekte kuemmert. Konkret wird aus dem String des
	 * Eingabefelds der Integer-Wert gezogen und geprueft ob der eingegebene
	 * Wert groesser 0 ist. Ist einer der beiden Kriterien nicht erfuellt wird
	 * eine ClassCastException geworfen, die zu einer Fehlermeldung auf der
	 * Benutzeroberflaecher fuehrt.
	 * 
	 * @author Christian Scherer, Tobias Lindner
	 * @param numberPeriodsToForecast
	 *            Anzahl der Perioden die in die Vorhergesagt werden sollen
	 */
	public void numberPeriodsToForecastChosen(String periodsToForecast) {


		int periodsToForecastInt;
		try {
			periodsToForecastInt = Integer.parseInt(periodsToForecast);
			if (periodsToForecastInt > 0) {
				periodsToForecastValid = true;
				getView().setComponentError(false, "periodsToForecast", "");
				this.projectProxy.getSelectedProject().setPeriodsToForecast(
						periodsToForecastInt);
				eventBus.fireEvent(new ValidationEvent(true));
				
			} else {
				throw new NumberFormatException();
			}
		} catch (NumberFormatException nfe) {
			periodsToForecastValid = false;
			getView()
					.setComponentError(
							true,
							"periodsToForecast",
							errorMessagePeriodsToForecast);
			getView()
					.showErrorMessage(errorMessagePeriodsToForecast);
			eventBus.fireEvent(new ValidationEvent(false));
			
		}
	}

	/**
	 * Methode die sich nach der Auswahl der zu Vorherzusagenden Perioden um die
	 * davon abhaengigen Objekte kuemmert. Konkret wird aus dem String des
	 * Eingabefelds der Integer-Wert gezogen und geprueft ob der eingegebene
	 * Wert groesser 0 ist. Ist einer der beiden Kriterien nicht erfuellt wird
	 * eine ClassCastException geworfen, die zu einer Fehlermeldung auf der
	 * Benutzeroberflaecher fuehrt.
	 * 
	 * @author Christian Scherer, Tobias Lindner
	 * @param numberPeriodsToForecast
	 *            Anzahl der Perioden die in die Vorhergesagt werden sollen
	 */
	public void numberPeriodsToForecastChosen_deterministic(String periodsToForecast_deterministic) {
		

		int periodsToForecast_deterministicInt;
		try {
			periodsToForecast_deterministicInt = Integer.parseInt(periodsToForecast_deterministic);
			if (periodsToForecast_deterministicInt > 0) {
				periodsToForecast_deterministicValid = true;
				getView().setComponentError(false, "periodsToForecast_deterministic", "");
				
				this.projectProxy.getSelectedProject().setPeriodsToForecast_deterministic(
						periodsToForecast_deterministicInt);
				eventBus.fireEvent(new ValidationEvent(true));
				
			} else {
				throw new NumberFormatException();
			}
		} catch (NumberFormatException nfe) {
			periodsToForecast_deterministicValid = false;
			getView()
					.setComponentError(
							true,
							"periodsToForecast_deterministic",
							errorMessagePeriodsToForecast_deterministic);
			getView()
					.showErrorMessage(errorMessagePeriodsToForecast_deterministic);
			eventBus.fireEvent(new ValidationEvent(false));
		
		}
	}
	
//	/**	
//		DIESES FELD IST IM NEUEN UI NICHT MEHR VORHANDEN!	
//	 * Methode die sich nach der Auswahl der anzugebenden vergangenen
//	 * Perioden um die davon abhaengigen Objekte kuemmert. Diese muessen laut
//	 * Fachkonzept groesser 3 Perioden betragen und groesser als die Anzahl
//	 * einbezogener Perioden sein
//	 * 
//	 * @author Marcel Rosenberger, Tobias Lindner
//	 * @param specifiedPastPeriods
//	 *            die Anzahl der Perioden der Vergangenheit die angegeben werden müssen
//	 */
//	public void specifiedPastPeriodsChosen(String specifiedPastPeriods) {
//		
//
//		int specifiedPastPeriodsInt;
//		int relevantPastPeriodsInt;
//		try {
//			specifiedPastPeriodsInt = Integer.parseInt(specifiedPastPeriods);
//			relevantPastPeriodsInt = this.projectProxy.getSelectedProject().getRelevantPastPeriods();
//			if (specifiedPastPeriodsInt > 3 && specifiedPastPeriodsInt > relevantPastPeriodsInt) {
//				specifiedPastPeriodsValid = true;
//				getView().setComponentError(false, "specifiedPastPeriods", "");
//				this.projectProxy.getSelectedProject().setSpecifiedPastPeriods(
//						specifiedPastPeriodsInt);
//				
//			} else {
//				throw new NumberFormatException();
//			}
//		} catch (NumberFormatException nfe) {
//			specifiedPastPeriodsValid = false;
//			getView()
//					.setComponentError(
//							true,
//							"specifiedPastPeriods", errorMessageSpecifiedPastPeriods);
//			getView()
//					.showErrorMessage(errorMessageSpecifiedPastPeriods);
//		
//		}
//	}
	
	/**
	 * Methode die sich nach der Auswahl der zu beachtenenden vergangenen
	 * Perioden um die davon abhaengigen Objekte kuemmert. Diese muessen laut
	 * Fachkonzept groesser 2 Perioden betragen und kleiner als die 
	 * Anzahl anzugebender Perioden sein.
	 * 
	 * @author Christian Scherer, Marcel Rosenberger, Tobias Lindner
	 * @param relevantPastPeriods
	 *            die Anzahl der Perioden der Vergangenheit die einbezogen
	 *            werden sollen
	 */
	public void relevantPastPeriodsChosen(String relevantPastPeriods) {
		
		int relevantPastPeriodsInt;
		int specifiedPastPeriodsInt;
		try {
			relevantPastPeriodsInt = Integer.parseInt(relevantPastPeriods);
			specifiedPastPeriodsInt = this.projectProxy.getSelectedProject().getSpecifiedPastPeriods(); 
			if (relevantPastPeriodsInt > 2) {
				relevantPastPeriodsValid = true;
				getView().setComponentError(false, "relevantPastPeriods", "");
				this.projectProxy.getSelectedProject().setRelevantPastPeriods(
						relevantPastPeriodsInt);
				this.projectProxy.getSelectedProject().setSpecifiedPastPeriods( //Feld specifiedPastPeriods nicht mehr vorhanden
						relevantPastPeriodsInt);
				eventBus.fireEvent(new ValidationEvent(true));
			
			} else {
				throw new NumberFormatException();
			}
		} catch (NumberFormatException nfe) {
			relevantPastPeriodsValid = false;
			getView()
					.setComponentError(
							true,
							"relevantPastPeriods", errorMessageRelevantPastPeriods);
			getView()
					.showErrorMessage(errorMessageRelevantPastPeriods);
			eventBus.fireEvent(new ValidationEvent(false));
			
		}
	}

	/**
	 * Methode die sich nach der Auswahl des Basisjahrs um die davon abhaengigen
	 * Objekte kuemmert. Wenn ein int Wert vorliegt wird geprueft ob es sich bei
	 * der Eingegebenen Zahl um ein Jahr groesser dem aktuellen Jahr-1 handelt
	 * 
	 * @author Christian Scherer, Tobias Lindner
	 * @param basisYear
	 *            das Basis-Jahr, auf das die Cashflows abgezinst werden
	 */
	public void basisYearChosen(String basisYear) {
		int basisYearInt;
		try {
			basisYearInt = Integer.parseInt(basisYear);

			if (basisYearInt > 1900) {
				basisYearValid = true;
				getView().setComponentError(false, "basisYear", "");
				this.projectProxy.getSelectedProject().setBasisYear(
						basisYearInt);
				eventBus.fireEvent(new ValidationEvent(true));
		
			} else {
				throw new NumberFormatException();
			}
		} catch (NumberFormatException nfe) {
			basisYearValid = false;
			getView()
					.setComponentError(
							true,
							"basisYear", errorMessageBasisYear);
			getView()
					.showErrorMessage(errorMessageBasisYear);
			eventBus.fireEvent(new ValidationEvent(false));
		
		}
	}

	/**
	 * Initialisiert das Basisjahr mit dem aktuellem Jahr-1
	 * 
	 * @author Christian Scherer
	 * 
	 */
	public void initializeBasisYear() {
		Calendar now = Calendar.getInstance();
		getView().setValueBasisYear("" + (now.get(Calendar.YEAR) - 1));
		basisYearValid = true;
	}
}
