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
//import java.util.Calendar;
//import java.util.Iterator;
//import java.util.SortedSet;
//
//import javax.annotation.PostConstruct;
//
//import org.apache.log4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import com.mvplite.event.EventBus;
//import com.mvplite.event.EventHandler;
//
//import dhbw.ka.mwi.businesshorizon2.methods.AbstractDeterministicMethod;
//import dhbw.ka.mwi.businesshorizon2.methods.AbstractStochasticMethod;
//import dhbw.ka.mwi.businesshorizon2.services.proxies.ProjectProxy;
//import dhbw.ka.mwi.businesshorizon2.ui.process.InvalidStateEvent;
//import dhbw.ka.mwi.businesshorizon2.ui.process.ScreenPresenter;
//import dhbw.ka.mwi.businesshorizon2.ui.process.ScreenSelectableEvent;
//import dhbw.ka.mwi.businesshorizon2.ui.process.ShowErrorsOnScreenEvent;
//import dhbw.ka.mwi.businesshorizon2.ui.process.ValidStateEvent;
//import dhbw.ka.mwi.businesshorizon2.ui.process.ValidateContentStateEvent;
//import dhbw.ka.mwi.businesshorizon2.ui.process.navigation.NavigationSteps;
//import dhbw.ka.mwi.businesshorizon2.ui.process.parameter.ParameterViewInterface;
//
///**
// * Der Presenter fuer die Maske des Prozessschrittes zur Eingabe der Parameter.
// * 
// * @author Julius Hacker, Christian Scherer
// * 
// */
//
//public class ParameterPresenter extends ScreenPresenter<ParameterViewInterface> {
//	private static final long serialVersionUID = 1L;
//
//	private static final Logger logger = Logger.getLogger("ParameterPresenter.class");
//
//	@Autowired
//	private EventBus eventBus;
//
//	@Autowired
//	private ProjectProxy projectProxy;
//
//	
//	private boolean iterationsValid;
//	private boolean basisYearValid;
//	private boolean periodsToForecastValid;
//	private boolean periodsToForecast_deterministicValid; //Annika Weis
//	private boolean relevantPastPeriodsValid;
//	private boolean specifiedPastPeriodsValid;
//
//	private boolean stochMethod;
//	private boolean randomWalk;
//	private boolean wienerProcess;
//	private boolean timeSeries;
//	
//	private boolean detMethod;
//	private boolean dcf;
//	private boolean apv;
//	
//
//	private double cashFlowProbabilityOfRise;
//	private double cashFlowStepRange;
//	private double borrowedCapitalProbabilityOfRise;
//	private double borrowedCapitalStepRange;
//
//	private boolean firstCall;
//	private boolean showError;
//
//	private boolean cashFlowStepRangeValid;
//	private boolean cashFlowProbabilityOfRiseValid;
//	private boolean borrowedCapitalProbabilityOfRiseValid;
//	private boolean borrowedCapitalStepRangeValid;
//	
//	private String errorMessageBasisYear;
//	private String errorMessageCashFlowStepRange;
//	private String errorMessageCashFlowProbabilityOfRise;
//	private String errorMessageBorrowedCapitalStepRange;
//	private String errorMessageBorrowedCapitalProbabilityOfRise;
//	private String errorMessagePeriodsToForecast;
//	private String errorMessagePeriodsToForecast_deterministic; //Annika Weis
//	private String errorMessageSpecifiedPastPeriods;
//	private String errorMessageRelevantPastPeriods;
//	private String errorMessageIterations;
//
//	private SortedSet<AbstractStochasticMethod> methods;
//	private SortedSet<AbstractDeterministicMethod> methods_deterministic;//Annika Weis
//
//	private Iterator<AbstractStochasticMethod> methodIterator;
//	private Iterator<AbstractDeterministicMethod> method_deterministicIterator;//Annika Weis
//
//	/**
//	 * Dies ist der Konstruktor, der von Spring nach der Initialierung der
//	 * Dependencies aufgerufen wird. Er registriert sich selbst als einen
//	 * EventHandler. Zudem werden die Validitaeten der Felder zunaechst auf
//	 * false gesetzt. Desweiteren wird der Wert der firstCall Variable auf true
//	 * gesetzt, sodass die erste Pruefung des screens noch keine Fehlermeldung
//	 * wirft, da der Benutzer den Screen auch noch nicht geoeffnet hat und die
//	 * showError Methode auf false. Letzere soll verhindern, dass schon bei
//	 * erstem Betreten des Screens der Benutzer von Fehlermeldungen 'erschlagen'
//	 * wird.
//	 * 
//	 * @author Julius Hacker, Christian SCherer
//	 */
//	@PostConstruct
//	public void init() {
//
//		eventBus.addHandler(this);
//		
//		initializeErrorStrings();
//		initializeVariableBooleans();
//		
//
//		firstCall = true;
//		showError = false;
//
//	}
//
//	/**
//	 * Diese Methode initialisiert die Validitaetsvariablen der Eingabefelder.
//	 * Standardmaesig sind diese auf False gesetzt, damit leere Eingaben ebenso
//	 * zu einer Fehlermeldung fuehren. Ausnahmen sind jene Eingabefelder die 
//	 * einen Default Wert besitzen. Diese werden hier schon auf true gesetzt.
//	 * 
//	 * @author Christian Scherer
//	 */
//	private void initializeVariableBooleans() {
//		basisYearValid = false;
//		periodsToForecastValid = false;
//		cashFlowStepRangeValid = false;
//		cashFlowProbabilityOfRiseValid = false;
//		borrowedCapitalStepRangeValid = false;
//		borrowedCapitalProbabilityOfRiseValid = false;
//		
//		iterationsValid = true;
//		relevantPastPeriodsValid = true;
//		specifiedPastPeriodsValid = true;
//
//	}
//
//	/**
//	 * Diese Methode initialisiert die Fehlermeldungen, die bei Falscheingabe
//	 * oder leer lassen der Felder angezeigt wird.
//	 * 
//	 * @author Christian Scherer
//	 */
//	private void initializeErrorStrings() {
//		errorMessageBasisYear = "Bitte geben Sie ein g\u00FCltiges Jahr an, jedoch gr\u00f6ßer als 1900. Beispiel: 2013";
//		errorMessageCashFlowStepRange = "Bitte geben Sie die Schrittweite der Cashflows g\u00f6\u00dfrer oder gleich 0 an. Beispiel: 100000";
//		errorMessageCashFlowProbabilityOfRise = "Bitte geben Sie die Wahrscheinlichkeit f\u00fcr steigende Cashflowentwicklung zwischen 0 und 100 an. Beispiel: 50";
//		errorMessageBorrowedCapitalStepRange = "Bitte geben Sie die Schrittweite des Fremdkapital g\u00f6\u00dfrer oder gleich 0 an. Beispiel: 100000";
//		errorMessageBorrowedCapitalProbabilityOfRise = "Bitte geben Sie die Wahrscheinlichkeit f\u00fcr steigende Fremdkapitalentwicklung zwischen 0 und 100 an. Beispiel: 50";
//		errorMessagePeriodsToForecast = "Bitte geben Sie die Anzahl vorherzusehender Perioden in einer Ganzzahl gr\u00F6\u00DFer 0 an. Beispiel: 5";
//		errorMessagePeriodsToForecast_deterministic = "Bitte geben Sie die Anzahl vorherzusehender Perioden (deterministische Verfahren) in einer Ganzzahl gr\u00F6\u00DFer 0 an. Beispiel: 5";
//		errorMessageSpecifiedPastPeriods = "Bitte geben Sie die Anzahl der anzugebenden vergangenen Perioden in einer Ganzzahl gr\u00F6\u00DFer als 3 und als die Anzahl der einbezogenen vergangenen Perioden.";
//		errorMessageRelevantPastPeriods = "Bitte geben Sie die Anzahl der relevanten vergangenen Perioden in einer Ganzzahl gr\u00F6\u00DFer 2 und kleiner als die Anzahl der angegebenen vergangenen Perioden an.";
//		errorMessageIterations = "Bitte w\u00E4hlen Sie die Anzahl der Wiederholungen als Ganzzahl zwischen 1000 und 100000 an. Beispiel: 10000";
//	}
//
//	/**
//	 * Dies ist der Konstruktor, der von Spring nach der Initialierung der
//	 * Dependencies aufgerufen wird. Er registriert sich selbst als einen
//	 * EventHandler. Um zu pruefen welche Verfahren ausgewaehlt wurden, wird zum einen der Haken
//	 * am stochastischen Pfad ueberprueft und zum anderen die Haken an Zeitreihe,
//	 * Wiener Prozess und Random Walk. Je nach Vorbedingungen hier werden die 
//	 * Entsprechenden Felder durch die greyOut() Methode ausgegraut. Vor dem 
//	 * Ausgrauen werden jedoch noch die im Projekt-Objekt gespeicherten Werte 
//	 * gelesen und in die Felder gesetzt.
//	 * Desweiteren wird die firstCall Variable auf false gesetzt, sodass
//	 * ab jetzt bei jeder Validierungsanfrage alle Felder geprueft und ggf. als
//	 * unkorrekt mariert werden. Beim ersten Aufruf ist dies noch nicht
//	 * gewuenscht, da der Benutzer den Screen noch nicht geoffnet hatte. Als
//	 * letztes wird ein ScreenSelectable Event gefeuert, sodass gewaehrleistet
//	 * ist, dass der erste Durchgang zwar streng nach Reihenfloge geschieht,
//	 * danach aber jeder Screen frei angewaehlt werden kann.
//	 * 
//	 * 
//	 * @author Julius Hacker, Christian Scherer
//	 */
//	@EventHandler
//	public void onShowParameterScreen(ShowParameterViewEvent event) {
//		
//		getView().showParameterView();
//
//		if (projectProxy.getSelectedProject().getBasisYear() == 0) {
//			initializeBasisYear();
//		}
//
//		stochMethod = false;
//		if (this.projectProxy.getSelectedProject().getProjectInputType() != null) {
//			stochMethod = this.projectProxy.getSelectedProject()
//					.getProjectInputType().isStochastic();
//		} 
//		
//		
//		//Annika Weis
//		detMethod = false;
//		if (this.projectProxy.getSelectedProject().getProjectInputType() != null) {
//			detMethod = this.projectProxy.getSelectedProject()
//					.getProjectInputType().isDeterministic();
//		} 
//
//		randomWalk = false;
//		wienerProcess = false;
//		timeSeries = false;
//		methods = this.projectProxy.getSelectedProject().getMethods();
//		methodIterator = methods.iterator();
//		while (methodIterator.hasNext()) {
//			AbstractStochasticMethod m = (AbstractStochasticMethod) methodIterator
//					.next();
//			if (m.getName().equals("Random Walk") && m.getSelected()) {
//				randomWalk = true;
//			} else if (m.getName().equals("Wiener Prozess") && m.getSelected()){
//				wienerProcess = true;
//			} else if (m.getName().equals("Zeitreihenanalyse") && m.getSelected()){
//				timeSeries = true;
//			}
//		}
//		
//		/* 
//		 * Annika Weis
//		 * ausgewählte deterministische Methoden überprüfen
//		 */
//		dcf=false;
//		apv=false;
//		methods_deterministic = this.projectProxy.getSelectedProject().getMethods_deterministic();
//		method_deterministicIterator = methods_deterministic.iterator();
//		while (method_deterministicIterator.hasNext()) {
//			AbstractDeterministicMethod m_d = (AbstractDeterministicMethod) method_deterministicIterator
//					.next();
//			if (m_d.getName().equals("Flow-to-Equity (FTE)") && m_d.getSelected()) {
//				dcf = true;
//			} else if (m_d.getName().equals("Adjusted-Present-Value (APV)") && m_d.getSelected()){
//				apv = true;
//			}
//		}
//		
//		
//		this.setValues();
//		this.greyOut();
//		firstCall = false;
//		eventBus.fireEvent(new ScreenSelectableEvent(NavigationSteps.PARAMETER,
//				true));
//
//	}
//
//	/**
//	 * In dieser Methode werden die Werte des Objekts (falls nicht null)
//	 * gelesen und in die Eingabefelder gesetzt.
//	 * 
//	 * @author Christian Scherer
//	 */
//	private void setValues() {
//		
//		if(this.projectProxy.getSelectedProject().getBasisYear()!=0){
//			getView().setValueBasisYear(""+this.projectProxy.getSelectedProject().getBasisYear());
//		}
//		if(this.projectProxy.getSelectedProject().getPeriodsToForecast()!=0){
//			getView().setPeriodsToForecast(""+this.projectProxy.getSelectedProject().getPeriodsToForecast());
//		}
//		if(this.projectProxy.getSelectedProject().getIterations()!=0){
//			getView().setIterations(""+this.projectProxy.getSelectedProject().getIterations());
//		}
//		if(this.projectProxy.getSelectedProject().getSpecifiedPastPeriods()!=0){
//			getView().setSpecifiedPastPeriods(""+this.projectProxy.getSelectedProject().getSpecifiedPastPeriods());
//		}
//		if(this.projectProxy.getSelectedProject().getRelevantPastPeriods()!=0){
//			getView().setRelevantPastPeriods(""+this.projectProxy.getSelectedProject().getRelevantPastPeriods());
//		}
//		if(this.projectProxy.getSelectedProject().getCashFlowStepRange()!=0){
//			//Verhindern einer fehlerhaften Double-Konvertierung auf 4 Nachkommastellen genau
//			getView().setCashFlowStepRange(""+(((double)Math.round(10000*(this.projectProxy.getSelectedProject().getCashFlowStepRange())))/10000));
//		}
//		if(this.projectProxy.getSelectedProject().getCashFlowProbabilityOfRise()!=0){
//			//Rueckumwandlung des 0-1 Werts zu einem 0-100 % Wert und verhindern einer fehlerhaften Double-Konvertierung auf 4 Nachkommastellen genau
//			getView().setCashFlowProbabilityOfRise(""+(((double)Math.round(10000*(100*this.projectProxy.getSelectedProject().getCashFlowProbabilityOfRise())))/10000));
//		}
//		if(this.projectProxy.getSelectedProject().getBorrowedCapitalStepRange()!=0){
//			//Verhindern einer fehlerhaften Double-Konvertierung auf 4 Nachkommastellen genau
//			getView().setBorrowedCapitalStepRange(""+(((double)Math.round(10000*(this.projectProxy.getSelectedProject().getBorrowedCapitalStepRange())))/10000));
//		}
//		if(this.projectProxy.getSelectedProject().getBorrowedCapitalProbabilityOfRise()!=0){
//			//Rueckumwandlung des 0-1 Werts zu einem 0-100 % Wert und verhindern einer fehlerhaften Double-Konvertierung auf 4 Nachkommastellen genau
//			getView().setBorrowedCapitalProbabilityOfRise(""+(((double)Math.round(10000*(100*this.projectProxy.getSelectedProject().getBorrowedCapitalProbabilityOfRise())))/10000));
//		}
//		if(this.projectProxy.getSelectedProject().getPeriodsToForecast_deterministic()!=0){
//			getView().setPeriodsToForecast_deterministic(""+this.projectProxy.getSelectedProject().getPeriodsToForecast_deterministic());
//		}
//		
//	}
//
//	/**
//	 * In dieser Methode werden alle Eingabefelder auf Validitaet geprueft. Sie
//	 * wird auch von anderen Screens aufzurufen um sicherzustellen, dass bei
//	 * Aenderungen in anderen Screens diese Eingabe immer noch valide sind.
//	 * Falls der Screen nicht mehr valide ist, muss zudem geprueft werden welche
//	 * Felder nicht mehr gueltig sind und diese mit einem ComponentenError
//	 * (rotem Ausrufezeichen) markiert werden. Zudem wird der Sonderfall
//	 * behandelt, dass es der erste Aufruf ist, dann wird sofort true
//	 * zurueckgegeben, da der Nutzer noch nicht die Moeglichkeit hatte korrekte
//	 * Angaben einzugeben
//	 * 
//	 * @author Christian Scherer
//	 * @return Ob alle Validierungspruefungen der Eingabefelder positiv gelaufen
//	 *         verlaufen ist
//	 */
//	@Override
//	public boolean isValid() {
//		
//		
//		boolean valid = true;
//		
//		if (firstCall) {
//			return true;
//		}
//		
//		if (!basisYearValid) {
//			if (showError) {
//				getView()
//						.setComponentError(
//								true,
//								"basisYear",
//								errorMessageBasisYear);
//			}
//			valid = false;
//		}
//			
//		//falls mindestens eine stochastische Methode aktiv
//		if(stochMethod&&(timeSeries||randomWalk||wienerProcess)){
//			if(!periodsToForecastValid){
//				if (showError) {
//					getView()
//							.setComponentError(
//									true,
//									"periodsToForecast", errorMessagePeriodsToForecast);
//				}
//				valid = false;
//			} 
//			if (!iterationsValid){
//				if (showError) {
//					getView()
//							.setComponentError(true, "iterations", errorMessageIterations);
//				}
//				valid = false;
//			} 
//		}
//		
//		//falls Zeitreihe aktiv
//		if(timeSeries&&stochMethod){
//			if(!relevantPastPeriodsValid){
//				if (showError) {
//					getView()
//							.setComponentError(
//									true,
//									"relevantPastPeriods", errorMessageRelevantPastPeriods);
//				}
//				valid = false;
//			}
//			if(!specifiedPastPeriodsValid){
//				if (showError) {
//					getView()
//							.setComponentError(
//									true,
//									"specifiedPastPeriods", errorMessageSpecifiedPastPeriods);
//				}
//				valid = false;
//			}
//		}
//		
//		//falls RandomWalk aktiv
//		if(randomWalk&&stochMethod){
//			if (!cashFlowStepRangeValid) {
//				if (showError) {
//					getView()
//							.setComponentError(
//									true,
//									"cashFlowStepRange", errorMessageCashFlowStepRange);
//				}
//				valid = false;
//			}
//			if (!cashFlowProbabilityOfRiseValid) {
//				if (showError) {
//					getView()
//							.setComponentError(
//									true,
//									"cashFlowProbabilityOfRise", errorMessageCashFlowProbabilityOfRise);
//				}
//				valid = false;
//			}
//			if (!borrowedCapitalStepRangeValid) {
//				if (showError) {
//					getView()
//							.setComponentError(
//									true,
//									"borrowedCapitalStepRange", errorMessageCashFlowStepRange);
//				}
//				valid = false;
//			}
//			if (!borrowedCapitalProbabilityOfRiseValid) {
//				if (showError) {
//					getView()
//							.setComponentError(
//									true,
//									"borrowedCapitalProbabilityOfRise", errorMessageCashFlowProbabilityOfRise);							
//				}
//				valid = false;	
//			}
//		}
//		
//		
//		//falls mindestens eine deterministische Methode aktiv
//		// TODO
//		if(detMethod&&(dcf||apv)){
//			if(!periodsToForecast_deterministicValid){
//				if (showError) {
//					getView()
//							.setComponentError(
//									true,
//									"periodsToForecast_deterministic", errorMessagePeriodsToForecast_deterministic);
//				}
//				valid = false;
//			} 
//
//		}
//		
//		
//		return valid;
//	}
//
//	/**
//	 * Methode die sich nach der Auswahl der Iteration um die davon abhaengigen
//	 * Objekte kuemmert.  Konkret wird aus dem String des
//	 * Eingabefelds der Integer-Wert gezogen und geprueft ob der eingegebene
//	 * Wert groesser 1990 ist. Ist einer der beiden Kriterien nicht erfuellt wird
//	 * eine ClassCastException geworfen, die zu einer Fehlermeldung auf der
//	 * Benutzeroberflaecher fuehrt.
//	 * 
//	 * 
//	 * @author Christian Scherer
//	 * @param iterations
//	 *            Anzahl der ausgewaehlten Wiederholungen(Iterationen)
//	 */
//	public void iterationChosen(String iterationsString) {
//	
//		int iterations;
//		try {
//			iterations = Integer.parseInt(iterationsString);
//			if (iterations >= 1000 && iterations <= 100000) {
//				iterationsValid = true;
//				getView().setComponentError(false, "iterations", "");
//				this.projectProxy.getSelectedProject().setIterations(iterations);
//				logger.debug("Iterationen in Objekten gesetzt: "
//						+ this.projectProxy.getSelectedProject().getName());
//
//			} else {
//				throw new NumberFormatException();
//			}
//		} catch (NumberFormatException nfe) {
//			iterationsValid = false;
//			getView()
//					.setComponentError(
//							true,
//							"iterations", errorMessageIterations);
//			getView()
//					.showErrorMessage(errorMessageIterations);
//			logger.debug("Keine gueltige Eingabe in Feld 'Wiederholungen'");
//		}
//
//		eventBus.fireEvent(new ValidateContentStateEvent());
//	}
//
//	/**
//	 * Methode die sich nach der Auswahl der zu Vorherzusagenden Perioden um die
//	 * davon abhaengigen Objekte kuemmert. Konkret wird aus dem String des
//	 * Eingabefelds der Integer-Wert gezogen und geprueft ob der eingegebene
//	 * Wert groesser 0 ist. Ist einer der beiden Kriterien nicht erfuellt wird
//	 * eine ClassCastException geworfen, die zu einer Fehlermeldung auf der
//	 * Benutzeroberflaecher fuehrt.
//	 * 
//	 * @author Christian Scherer
//	 * @param numberPeriodsToForecast
//	 *            Anzahl der Perioden die in die Vorhergesagt werden sollen
//	 */
//	public void numberPeriodsToForecastChosen(String periodsToForecast) {
//		logger.debug("Anwender-Eingabe zu Perioden die vorherzusagen sind");
//
//		int periodsToForecastInt;
//		try {
//			periodsToForecastInt = Integer.parseInt(periodsToForecast);
//			if (periodsToForecastInt > 0) {
//				periodsToForecastValid = true;
//				getView().setComponentError(false, "periodsToForecast", "");
//				this.projectProxy.getSelectedProject().setPeriodsToForecast(
//						periodsToForecastInt);
//				logger.debug("Anzahl Perioden die vorherzusagen sind in das Projekt-Objekten gesetzt");
//			} else {
//				throw new NumberFormatException();
//			}
//		} catch (NumberFormatException nfe) {
//			periodsToForecastValid = false;
//			getView()
//					.setComponentError(
//							true,
//							"periodsToForecast",
//							errorMessagePeriodsToForecast);
//			getView()
//					.showErrorMessage(errorMessagePeriodsToForecast);
//			logger.debug("Keine gueltige Eingabe in Feld 'Anzahl zu prognostizierender Perioden'");
//		}
//
//		eventBus.fireEvent(new ValidateContentStateEvent());
//	}
//
//	
//	//Annika Weis
//	/**
//	 * Methode die sich nach der Auswahl der zu Vorherzusagenden Perioden um die
//	 * davon abhaengigen Objekte kuemmert. Konkret wird aus dem String des
//	 * Eingabefelds der Integer-Wert gezogen und geprueft ob der eingegebene
//	 * Wert groesser 0 ist. Ist einer der beiden Kriterien nicht erfuellt wird
//	 * eine ClassCastException geworfen, die zu einer Fehlermeldung auf der
//	 * Benutzeroberflaecher fuehrt.
//	 * 
//	 * @author Christian Scherer
//	 * @param numberPeriodsToForecast
//	 *            Anzahl der Perioden die in die Vorhergesagt werden sollen
//	 */
//	public void numberPeriodsToForecastChosen_deterministic(String periodsToForecast_deterministic) {
//		logger.debug("Anwender-Eingabe zu deterministischen Perioden die vorherzusagen sind");
//
//		int periodsToForecast_deterministicInt;
//		try {
//			periodsToForecast_deterministicInt = Integer.parseInt(periodsToForecast_deterministic);
//			if (periodsToForecast_deterministicInt > 0) {
//				periodsToForecast_deterministicValid = true;
//				getView().setComponentError(false, "periodsToForecast_deterministic", "");
//				this.projectProxy.getSelectedProject().setPeriodsToForecast_deterministic(
//						periodsToForecast_deterministicInt);
//				logger.debug("Anzahl Perioden die vorherzusagen sind in das Projekt-Objekten gesetzt");
//			} else {
//				throw new NumberFormatException();
//			}
//		} catch (NumberFormatException nfe) {
//			periodsToForecast_deterministicValid = false;
//			getView()
//					.setComponentError(
//							true,
//							"periodsToForecast_deterministic",
//							errorMessagePeriodsToForecast_deterministic);
//			getView()
//					.showErrorMessage(errorMessagePeriodsToForecast_deterministic);
//			logger.debug("Keine gueltige Eingabe in Feld 'Anzahl zu prognostizierender Perioden' bei den deterministischen Verfahren");
//		}
//
//		eventBus.fireEvent(new ValidateContentStateEvent());
//	}
//	
//	/**
//	 * Methode die sich nach der Auswahl der anzugebenden vergangenen
//	 * Perioden um die davon abhaengigen Objekte kuemmert. Diese muessen laut
//	 * Fachkonzept groesser 3 Perioden betragen und groesser als die Anzahl
//	 * einbezogener Perioden sein
//	 * 
//	 * @author Marcel Rosenberger
//	 * @param specifiedPastPeriods
//	 *            die Anzahl der Perioden der Vergangenheit die angegeben werden müssen
//	 */
//	public void specifiedPastPeriodsChosen(String specifiedPastPeriods) {
//		logger.debug("Anwender-Eingabe zu anzugebenden Perioden der Vergangenheit ");
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
//				logger.debug("Anzahl anzugebender Vergangenheits-Perioden sind in das Projekt-Objekten gesetzt");
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
//			logger.debug("Keine gueltige Eingabe in Feld 'Anzahl anzugebender, vergangener Perioden'");
//		}
//
//		eventBus.fireEvent(new ValidateContentStateEvent());
//	}
//	
//	/**
//	 * Methode die sich nach der Auswahl der zu beachtenenden vergangenen
//	 * Perioden um die davon abhaengigen Objekte kuemmert. Diese muessen laut
//	 * Fachkonzept groesser 2 Perioden betragen und kleiner als die 
//	 * Anzahl anzugebender Perioden sein.
//	 * 
//	 * @author Christian Scherer, Marcel Rosenberger
//	 * @param relevantPastPeriods
//	 *            die Anzahl der Perioden der Vergangenheit die einbezogen
//	 *            werden sollen
//	 */
//	public void relevantPastPeriodsChosen(String relevantPastPeriods) {
//		logger.debug("Anwender-Eingabe zu relevanter Perioden der Vergangenheit ");
//
//		int relevantPastPeriodsInt;
//		int specifiedPastPeriodsInt;
//		try {
//			relevantPastPeriodsInt = Integer.parseInt(relevantPastPeriods);
//			specifiedPastPeriodsInt = this.projectProxy.getSelectedProject().getSpecifiedPastPeriods(); 
//			if (relevantPastPeriodsInt > 2 && specifiedPastPeriodsInt > relevantPastPeriodsInt) {
//				relevantPastPeriodsValid = true;
//				getView().setComponentError(false, "relevantPastPeriods", "");
//				this.projectProxy.getSelectedProject().setRelevantPastPeriods(
//						relevantPastPeriodsInt);
//				logger.debug("Anzahl relevanter Perioden der Vergangenheit sind in das Projekt-Objekten gesetzt");
//			} else {
//				throw new NumberFormatException();
//			}
//		} catch (NumberFormatException nfe) {
//			relevantPastPeriodsValid = false;
//			getView()
//					.setComponentError(
//							true,
//							"relevantPastPeriods", errorMessageRelevantPastPeriods);
//			getView()
//					.showErrorMessage(errorMessageRelevantPastPeriods);
//			logger.debug("Keine gueltige Eingabe in Feld 'Anzahl einbezogener, vergangener Perioden'");
//		}
//
//		eventBus.fireEvent(new ValidateContentStateEvent());
//	}
//
//	/**
//	 * Methode die sich nach der Auswahl des Basisjahrs um die davon abhaengigen
//	 * Objekte kuemmert. Wenn ein int Wert vorliegt wird geprueft ob es sich bei
//	 * der Eingegebenen Zahl um ein Jahr groesser dem aktuellen Jahr-1 handelt
//	 * 
//	 * @author Christian Scherer
//	 * @param basisYear
//	 *            das Basis-Jahr, auf das die Cashflows abgezinst werden
//	 */
//	public void basisYearChosen(String basisYear) {
//		logger.debug("Anwender-Eingabe zu relevanter Perioden der Vergangenheit ");
//
//		int basisYearInt;
//		try {
//			basisYearInt = Integer.parseInt(basisYear);
//
//			if (basisYearInt > 1900) {
//				basisYearValid = true;
//				getView().setComponentError(false, "basisYear", "");
//				this.projectProxy.getSelectedProject().setBasisYear(
//						basisYearInt);
//				logger.debug("Basisjahr in das Projekt-Objekten gesetzt");
//			} else {
//				throw new NumberFormatException();
//			}
//		} catch (NumberFormatException nfe) {
//			basisYearValid = false;
//			getView()
//					.setComponentError(
//							true,
//							"basisYear", errorMessageBasisYear);
//			getView()
//					.showErrorMessage(errorMessageBasisYear);
//			logger.debug("Keine gueltige Eingabe in Feld 'Wahl des Basisjahr'");
//		}
//
//		eventBus.fireEvent(new ValidateContentStateEvent());
//	}
//
//	/**
//	 * Methode die sich nach der Aenderung des Wertes der Checkbox fuer die
//	 * Branchenstellvertreter um die weiter Logik kuemmert. Hier muesste nun die
//	 * Liste der Branchenvertreter aktiviert werden, die standardmaessig
//	 * ausgegrautist. Derzeit werden die Branchenvertreter jedoch nicht genutzt
//	 * und somit hier auch nicht weiter behandelt. Eine spaetere
//	 * ausimplementierung kann hier folgen
//	 * 
//	 * @author Christian Scherer
//	 * @param selected
//	 *            "true" wenn der Haken ausgewaehlt wurde, "false" wenn der
//	 *            Haken entfernt wurde
//	 */
//	public void industryRepresentativeCheckBoxSelected(boolean selected) {
//		if (selected) {
//			// Liste aktivieren
//		} else {
//			// Liste deaktivieren
//		}
//
//		eventBus.fireEvent(new ValidateContentStateEvent());
//	}
//
//	/**
//	 * Methode die sich nach der Auswahl in der Liste der Branchenvertreter um
//	 * die weiter Logik kuemmert. Derzeit nicht genutzt - kann jedoch spaeter
//	 * hier ausprogrammiert werden.
//	 * 
//	 * @author Christian Scherer
//	 * @param selected
//	 *            String mit Namen des gewaehlten Branchenvertreters
//	 */
//	public void industryRepresentativeListItemChosen(String selected) {
//		eventBus.fireEvent(new ValidateContentStateEvent());
//	}
//
//	/**
//	 * Methode die sich nach der Auswahl der Schrittgroesse fuer die Cashflows
//	 * kuemmert. Konkret wird aus dem String des Eingabefelds der Double-Wert
//	 * gezogen und geprueft ob dieser groesser 0 ist. Falls nicht wird eine
//	 * ClassCastException geworfen, die eine Fehlermeldung auf der
//	 * Benutzeroberflaecher angezeigt und ein ComponentError generiert.
//	 * 
//	 * @author Christian Scherer
//	 * @param cashFlowStepRangeString
//	 *            Schrittgröße der Cashflows fuer die RandomWalk Methode
//	 */
//	public void cashFlowStepRangeChosen(String cashFlowStepRangeString) {
//		logger.debug("Anwender-Eingabe zu Schrittweite Cashflow");
//
//		try {
//			cashFlowStepRange = Double.parseDouble(cashFlowStepRangeString);
//			if (cashFlowStepRange >= 0) {
//				cashFlowStepRangeValid = true;
//				getView().setComponentError(false, "cashFlowStepRange", "");
//				this.projectProxy.getSelectedProject().setCashFlowStepRange(
//						this.cashFlowStepRange);
//				logger.debug("Schrittweite des Cashflows in das Projekt-Objekten gesetzt");
//			} else {
//				throw new NumberFormatException();
//			}
//		} catch (NumberFormatException nfe) {
//			cashFlowStepRangeValid = false;
//			getView()
//					.setComponentError(
//							true,
//							"cashFlowStepRange", errorMessageCashFlowStepRange);
//			getView()
//					.showErrorMessage(errorMessageCashFlowStepRange);
//			logger.debug("Keine gueltige Eingabe in Feld 'Schrittweite Cashflows'");
//		}
//
//		eventBus.fireEvent(new ValidateContentStateEvent());
//	}
//
//	/**
//	 * Methode die sich nach der Auswahl der Wahrscheinlichkeit fuer eine
//	 * positive Cashflows-Entwicklung kuemmert. Konkret wird aus dem String des
//	 * Eingabefelds der Double-Wert gezogen und geprueft ob der Wert zwischen 0
//	 * und 100 liegt. Vor der Uebergabe wird der uebergebene an das Project-Objekt
//	 * wird der Wert noch durch 100 geteilt, da die Rechenlogig mit einem 
//	 * Wert zwischen 0 und 1 arbeitet. Falls nicht wird eine ClassCastException 
//	 * geworfen, die eine Fehlermeldung auf der Benutzeroberflaecher angezeigt und ein
//	 * ComponentError generiert. 
//	 * 
//	 * @author Christian Scherer
//	 * @param cashFlowProbabilityOfRiseString
//	 *            Wahrscheinlichkeit fuer eine positive Cashflows-Entwicklung
//	 *            fuer die RandomWalk Methode
//	 */
//	public void cashFlowProbabilityOfRiseChosen(
//			String cashFlowProbabilityOfRiseString) {
//		logger.debug("Anwender-Eingabe zu Wahrscheinlichkeit f\u00fcr steigende Cashflowentwicklung");
//
//		try {
//			cashFlowProbabilityOfRise = Double
//					.parseDouble(cashFlowProbabilityOfRiseString);
//			if (cashFlowProbabilityOfRise >= 0
//					&& cashFlowProbabilityOfRise <= 100) {
//				cashFlowProbabilityOfRiseValid = true;
//				getView().setComponentError(false, "cashFlowProbabilityOfRise",
//						"");
//				this.projectProxy.getSelectedProject()
//						.setCashFlowProbabilityOfRise(
//								(this.cashFlowProbabilityOfRise/100));
//				logger.debug("Wahrscheinlichkeit f\u00fcr steigende Cashflowentwicklung in das Projekt-Objekten gesetzt");
//			} else {
//				throw new NumberFormatException();
//			}
//		} catch (NumberFormatException nfe) {
//			cashFlowProbabilityOfRiseValid = false;
//			getView()
//					.setComponentError(
//							true,
//							"cashFlowProbabilityOfRise", errorMessageCashFlowProbabilityOfRise);
//			getView()
//					.showErrorMessage(errorMessageCashFlowProbabilityOfRise);
//			logger.debug("Keine gueltige Eingabe in Feld 'Wahrscheinlichkeit f\u00fcr steigende Cashflowentwicklung");
//		}
//
//		eventBus.fireEvent(new ValidateContentStateEvent());
//	}
//
//	/**
//	 * Methode die sich nach der Auswahl der Schrittgroesse fuer das
//	 * Fremdkapital kuemmert. Konkret wird aus dem String des Eingabefelds der
//	 * Double-Wert gezogen und ueberprueft ob dieser groesser oder gleich 0 ist.
//	 * Falls nicht wird eine ClassCastException geworfen, die eine Fehlermeldung
//	 * auf der Benutzeroberflaecher angezeigt und ein ComponentError generiert.
//	 * 
//	 * @author Christian Scherer
//	 * @param cashFlowStepRangeString
//	 *            Schrittgröße das Fremdkapital fuer die RandomWalk Methode
//	 */
//	public void borrowedCapitalStepRangeChosen(
//			String borrowedCapitalStepRangeString) {
//		logger.debug("Anwender-Eingabe zu Schrittweite Cashflow");
//
//		try {
//			borrowedCapitalStepRange = Double
//					.parseDouble(borrowedCapitalStepRangeString);
//			if (borrowedCapitalStepRange >= 0) {
//				borrowedCapitalStepRangeValid = true;
//				getView().setComponentError(false, "borrowedCapitalStepRange",
//						"");
//				this.projectProxy.getSelectedProject()
//						.setBorrowedCapitalStepRange(
//								this.borrowedCapitalStepRange);
//				logger.debug("Schrittweite des Fremdkapital in das Projekt-Objekten gesetzt");
//			} else {
//				throw new NumberFormatException();
//			}
//		} catch (NumberFormatException nfe) {
//			borrowedCapitalStepRangeValid = false;
//			getView()
//					.setComponentError(
//							true,
//							"borrowedCapitalStepRange",
//							 errorMessageBorrowedCapitalStepRange);
//			getView()
//					.showErrorMessage(errorMessageBorrowedCapitalStepRange);
//			logger.debug("Keine gueltige Eingabe in Feld 'Schrittweite Fremdkapital'");
//		}
//
//		eventBus.fireEvent(new ValidateContentStateEvent());
//	}
//
//	/**
//	 * Methode die sich nach der Auswahl der Wahrscheinlichkeit fuer eine
//	 * positive Fremdkapitalentwicklung kuemmert. Konkret wird aus dem String
//	 * des Eingabefelds der Double-Wert gezogen und geprueft ob der Wert
//	 * zwischen 0 und 100 liegt. Vor der Uebergabe wird der uebergebene an das 
//	 * Project-Objekt wird der Wert noch durch 100 geteilt, da die Rechenlogig 
//	 * mit einem Wert zwischen 0 und 1 arbeitet. Falls nicht wird eine ClassCastException
//	 * geworfen, die eine Fehlermeldung auf der Benutzeroberflaecher angezeigt
//	 * und ein ComponentError generiert.
//	 * 
//	 * @author Christian Scherer
//	 * @param borrowedCapitalProbabilityOfRiseString
//	 *            Wahrscheinlichkeit fuer eine positive Fremdkapitalentwicklung
//	 *            fuer die RandomWalk Methode
//	 */
//	public void borrowedCapitalProbabilityOfRiseChosen(
//			String borrowedCapitalProbabilityOfRiseString) {
//		logger.debug("Anwender-Eingabe zu Wahrscheinlichkeit f\u00fcr steigende Fremdkapitalentwicklung");
//
//		try {
//			borrowedCapitalProbabilityOfRise = Double
//					.parseDouble(borrowedCapitalProbabilityOfRiseString);
//			if (borrowedCapitalProbabilityOfRise >= 0
//					&& borrowedCapitalProbabilityOfRise <= 100) {
//				borrowedCapitalProbabilityOfRiseValid = true;
//				getView().setComponentError(false,
//						"borrowedCapitalProbabilityOfRise", "");
//				this.projectProxy.getSelectedProject()
//						.setBorrowedCapitalProbabilityOfRise(
//								(this.borrowedCapitalProbabilityOfRise/100));
//				logger.debug("Wahrscheinlichkeit f\u00fcr steigende Fremdkapitalentwicklung in das Projekt-Objekten gesetzt");
//			} else {
//				throw new NumberFormatException();
//			}
//		} catch (NumberFormatException nfe) {
//			borrowedCapitalProbabilityOfRiseValid = false;
//			getView()
//					.setComponentError(
//							true,
//							"borrowedCapitalProbabilityOfRise",errorMessageBorrowedCapitalProbabilityOfRise);
//			getView()
//					.showErrorMessage(errorMessageBorrowedCapitalProbabilityOfRise);
//			logger.debug("Keine gueltige Eingabe in Feld 'Wahrscheinlichkeit f\u00fcr steigende Fremdkapitalentwicklung");
//		}
//
//		eventBus.fireEvent(new ValidateContentStateEvent());
//	}
//
//	/**
//	 * Methode die sich um das Ausgrauen unrelevanter Komponenten. In unserem
//	 * Fall die Branchenstellvertreter und Wiener Prozess, die noch nicht implementiert 
//	 * sind. Zusaetzlich muessen je nach Eingabe die nicht ausgewaehlten Bereiche 
//	 * ausgegraut werden. Beim ausgrauen sind ggf. gesetzete ComponentErrors aufzuheben
//	 * 
//	 * @author Christian Scherer
//	 * 
//	 */
//	public void greyOut() {
//		//Branchenvertreter ausgrauen
//		getView().activateCheckboxIndustryRepresentative(false);
//		getView().activateComboBoxRepresentatives(false);
//		/**
//		//Wienerprozess ausgrauen
//		getView().activateRiseOfPeriods(false);
//		getView().activateRiseOfPeriodsCheckbox(false);
//		getView().activateDeviation(false);
//		getView().activateDeviationCheckbox(false);
//		//Bisher nicht verwendetes Feld
//		getView().activateStepsPerPeriod(false);
//		getView().activateStepRange(false);
//		getView().activateProbability(false);
//		getView().activateCalculateStepRange(false);
//		*/
//		
//		//Keine Stochastische Methode aktiv / mindestens eine aktiv
//		if(!stochMethod){
//			getView().activatePeriodsToForecast(false);
//			getView().activateIterations(false);
//			getView().setComponentError(false, "iterations", null);
//			getView().setComponentError(false, "periodsToForecast", null);
//		} else {
//			getView().activatePeriodsToForecast(true);
//			getView().activateIterations(true);
//		}
//		
//		//Zeitreihe nicht aktiv / aktiv
//		if(!stochMethod||!timeSeries){
//			getView().activateRelevantPastPeriods(false);
//			getView().activateSpecifiedPastPeriods(false);
//			getView().setComponentError(false, "relevantPastPeriods", null);	
//			getView().setComponentError(false, "specifiedPastPeriods", null);	
//		}else {
//			getView().activateRelevantPastPeriods(true);
//			getView().activateSpecifiedPastPeriods(true);
//		}
//		
//		/**
//		//RandomWalk nicht aktiv / aktiv
//		if(!stochMethod||!randomWalk){
//			getView().activateCashFlowStepRang(false);
//			getView().activateCashFlowProbabilityOfRise(false);
//			getView().activateBorrowedCapitalProbabilityOfRise(false);
//			getView().activateBorrowedCapitalStepRange(false);
//			getView().setComponentError(false, "borrowedCapitalProbabilityOfRise", null);
//			getView().setComponentError(false, "borrowedCapitalStepRange", null);
//			getView().setComponentError(false, "cashFlowStepRange", null);
//			getView().setComponentError(false, "cashFlowProbabilityOfRise", null);
//
//			
//		} else {
//			getView().activateCashFlowStepRang(true);
//			getView().activateCashFlowProbabilityOfRise(true);
//			getView().activateBorrowedCapitalProbabilityOfRise(true);
//			getView().activateBorrowedCapitalStepRange(true);
//		}
//		 */
//		
//		//Annika Weis
//		//Keine deterministische Methode aktiv / mindestens eine aktiv
//		if(!detMethod){
//			getView().activatePeriodsToForecast_deterministic(false);
//			getView().setComponentError(false, "iterations", null);
//			getView().setComponentError(false, "periodsToForecast_deterministic", null);
//		} else {
//			getView().activatePeriodsToForecast_deterministic(true);
//		}
//	}
//
//	/**
//	 * Initialisiert das Basisjahr mit dem aktuellem Jahr-1
//	 * 
//	 * @author Christian Scherer
//	 * 
//	 */
//	public void initializeBasisYear() {
//		Calendar now = Calendar.getInstance();
//		getView().setValueBasisYear("" + (now.get(Calendar.YEAR) - 1));
//		basisYearValid = true;
//		logger.debug("Initialjahr " + (now.get(Calendar.YEAR) - 1)
//				+ " gesetzt.");
//
//	}
//
//	
//	/**
//	 * 
//	 * Eventhandler der zuerst prueft ob sich Vorbedingungen geaendert haben,
//	 * die Auswierkungen auf den ParameterScreen haben. Daraufhin wird geprueft
//	 * ob es sich um den ersten Aufruf handelt, also der Anwender noch keine
//	 * Moeglichkeit hatte die Felder korrekt zu befuellen. Ist dem so wird der
//	 * Screen noch als valide gewertet. Erst nach dem ersten Aufrufen des
//	 * Screens wird dann die Pruefung bei falschen Eintraegen auch ein Invalid
//	 * Event feuern.
//	 * 
//	 * @author Christian Scherer
//	 */
//	@Override
//	@EventHandler
//	public void validate(ValidateContentStateEvent event) {
//
//		stochMethod = false;
//		if (this.projectProxy.getSelectedProject().getProjectInputType() != null) {
//			stochMethod = this.projectProxy.getSelectedProject()
//					.getProjectInputType().isStochastic();
//		} 
//
//		randomWalk = false;
//		wienerProcess = false;
//		timeSeries = false;
//		methods = this.projectProxy.getSelectedProject().getMethods();
//		methodIterator = methods.iterator();
//		while (methodIterator.hasNext()) {
//			AbstractStochasticMethod m = (AbstractStochasticMethod) methodIterator
//					.next();
//			if (m.getName().equals("Random Walk") && m.getSelected()) {
//				randomWalk = true;
//			} else if (m.getName().equals("Wiener Prozess") && m.getSelected()){
//				wienerProcess = true;
//			} else if (m.getName().equals("Zeitreihenanalyse") && m.getSelected()){
//				timeSeries = true;
//			}
//		}
//		
//		if (!firstCall && !isValid()) {
//			eventBus.fireEvent(new InvalidStateEvent(NavigationSteps.PARAMETER,
//					showError));
//			logger.debug("Parameter not valid, InvalidStateEvent fired");
//		} else {
//			eventBus.fireEvent(new ValidStateEvent(NavigationSteps.PARAMETER));
//			logger.debug("Parameter valid, ValidStateEvent fired");
//		}
//	}
//
//	/**
//	 * 
//	 * Eventhandler der zuerst prueft ob dieser Screen her angesprochen wird.
//	 * Falls ja soll die showError auf true gesetzt werden, die ermoeglicht,
//	 * dass die Fehlermeldungen in der isValid-Methode angezeigt werden.
//	 * 
//	 * @author Christian Scherer
//	 */
//	@Override
//	@EventHandler
//	public void handleShowErrors(ShowErrorsOnScreenEvent event) {
//		if (event.getStep() == NavigationSteps.PARAMETER) {
//			showError = true;
//		}
//	}
//
//	/**
//	 * Methode die sich nach der Auswahl der Schritte pro Periode
//	 * um die davon abhaengigen Objekte kuemmert. Derzeit nicht in 
//	 * Benutzung
//	 * 
//	 * @author Christian Scherer
//	 * @param relevantPastPeriods
//	 *            die Anzahl der Perioden der Vergangenheit die einbezogen
//	 *            werden sollen
//	 */
//	public void stepsPerPeriodChosen(String stepsPerPeriodString) {
//
////		int stepsPerPeriod;
////		try {
////			stepsPerPeriod = Integer.parseInt(stepsPerPeriodString);
////
////			if (stepsPerPeriod > 0) {
////				stepsPerPeriodValid = true;
////				getView().setComponentError(false, "stepsPerPeriod", "");
////				this.projectProxy.getSelectedProject().setStepsPerPeriod(
////						stepsPerPeriod);
////				logger.debug("Schritte pro Periode in das Projekt-Objekten gesetzt");
////			} else {
////				throw new NumberFormatException();
////			}
////		} catch (NumberFormatException nfe) {
////			stepsPerPeriodValid = false;
////			getView()
////					.setComponentError(
////							true,
////							"stepsPerPeriod", errorMessageStepsPerPeriod);
////			getView()
////					.showErrorMessage(errorMessageStepsPerPeriod);
////			logger.debug("Keine gueltige Eingabe in Feld 'Schritte pro Periode'");
////		}
////
////		eventBus.fireEvent(new ValidateContentStateEvent());
//	}
//
//	/**
//	 * 
//	 * Derzeit noch nicht im Einsatz und daher auch nicht ausprogrammiert.
//	 * 
//	 * @author Christian Scherer
//	 */
//	public void riseOfPeriodsCheckBoxSelected(boolean booleanValue) {
//		
//	}
//
//	/**
//	 * 
//	 * Derzeit noch nicht im Einsatz und daher auch nicht ausprogrammiert.
//	 * 
//	 * @author Christian Scherer
//	 */
//	public void riseOfPeriodsChosen(String value) {
//		
//	}
//
//	/**
//	 * 
//	 * Derzeit noch nicht im Einsatz und daher auch nicht ausprogrammiert.
//	 * 
//	 * @author Christian Scherer
//	 */
//	public void deviationOfPeriodsCheckBoxSelected(boolean booleanValue) {
//		
//	}
//
//	/**
//	 * 
//	 * Derzeit noch nicht im Einsatz und daher auch nicht ausprogrammiert.
//	 * 
//	 * @author Christian Scherer
//	 */
//	public void deviationChosen(String value) {
//		
//	}
//
//	/**
//	 * 
//	 * Derzeit noch nicht im Einsatz und daher auch nicht ausprogrammiert.
//	 * 
//	 * @author Christian Scherer
//	 * @param calculateStepRange
//	 * 				Ob das Errechnen der Schrittweise automatisch (=true) oder 
//	 *  			nicht (false) geschehen soll
//	 */
//	public void calculateStepRangeCheckBoxSelected(boolean calculateStepRange) {
//	
//	}
//
//	/**
//	 * 
//	 * Derzeit noch nicht im Einsatz und daher auch nicht ausprogrammiert.
//	 * 
//	 * @author Christian Scherer
//	 * @param probabiltiyString
//	 * 				Eingegebene Wahrscheinlichkeit
//	 */
//	public void probablityChosen(Object probabiltiyString) {
//	
//	}
//
//	/**
//	 * 
//	 * Derzeit noch nicht im Einsatz und daher auch nicht ausprogrammiert.
//	 * 
//	 * @author Christian Scherer
//	 * @param steRangeString
//	 * 				Eingegebene Schrittweite
//	 */
//	public void StepRangeChosen(String stepRangeString) {
//	
//	}
//}
