/*******************************************************************************
 * BusinessHorizon2
 * 
 *     Copyright (C) 2012-2013  Christian Gahlert, Florian Stier, Kai Westerholz,
 *     Timo Belz, Daniel Dengler, Katharina Huber, Christian Scherer, Julius Hacker
 * 
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Affero General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 * 
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Affero General Public License for more details.
 * 
 *     You should have received a copy of the GNU Affero General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package dhbw.ka.mwi.businesshorizon2.ui.process.method;

import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.mvplite.event.EventBus;
import com.mvplite.event.EventHandler;

import dhbw.ka.mwi.businesshorizon2.methods.AbstractCalculationMethod;
import dhbw.ka.mwi.businesshorizon2.methods.AbstractStochasticMethod;

import dhbw.ka.mwi.businesshorizon2.methods.discountedCashflow.APV_alt;
import dhbw.ka.mwi.businesshorizon2.methods.discountedCashflow.APV;
import dhbw.ka.mwi.businesshorizon2.methods.discountedCashflow.FTE;

import dhbw.ka.mwi.businesshorizon2.methods.random.RandomWalk;
import dhbw.ka.mwi.businesshorizon2.methods.timeseries.TimeseriesCalculator;
import dhbw.ka.mwi.businesshorizon2.methods.wiener.Wiener;

import dhbw.ka.mwi.businesshorizon2.models.CashflowSource;
import dhbw.ka.mwi.businesshorizon2.models.DeterministicResultContainer;

import dhbw.ka.mwi.businesshorizon2.models.InputType;
import dhbw.ka.mwi.businesshorizon2.models.Project;
import dhbw.ka.mwi.businesshorizon2.models.ProjectCashflowSource;
import dhbw.ka.mwi.businesshorizon2.models.ProjectInputType;
import dhbw.ka.mwi.businesshorizon2.services.proxies.ProjectProxy;
import dhbw.ka.mwi.businesshorizon2.ui.process.InvalidStateEvent;
import dhbw.ka.mwi.businesshorizon2.ui.process.ScreenPresenter;
import dhbw.ka.mwi.businesshorizon2.ui.process.ScreenSelectableEvent;
import dhbw.ka.mwi.businesshorizon2.ui.process.ShowErrorsOnScreenEvent;
import dhbw.ka.mwi.businesshorizon2.ui.process.ValidStateEvent;
import dhbw.ka.mwi.businesshorizon2.ui.process.ValidateContentStateEvent;
import dhbw.ka.mwi.businesshorizon2.ui.process.navigation.NavigationSteps;

/**
 * Der Presenter fuer die Maske des Prozessschrittes zur Auswahl der
 * Berechnungsmethoden.
 * 
 * @author Julius Hacker, Annika Weis, Mirko Göpfrich
 * 
 */

public class MethodPresenter extends ScreenPresenter<MethodViewInterface> {

	private static final long serialVersionUID = 1L;

	private Logger logger = Logger.getLogger(MethodPresenter.class);

	private Boolean showError = false;

	@Autowired
	private EventBus eventBus;

	@Autowired
	private ProjectProxy projectProxy;

	private Project project;

	private AbstractCalculationMethod calculationMethod;
	private AbstractStochasticMethod stochasticMethod;
	
	//Liste für die Auswahloptionen
	//private SortedSet<AbstractCalculationMethod> calculationMethods;
	
	private ProjectCashflowSource projectCashflowSource;
	
	private ProjectInputType projectInputType;

	/**
	 * Dies ist der Konstruktor, der von Spring nach der Initialierung der
	 * Dependencies aufgerufen wird. Er registriert lediglich sich selbst als
	 * einen EventHandler.
	 * 
	 * @author Julius Hacker
	 */
	@PostConstruct
	public void init() {
		eventBus.addHandler(this);
	}

	
	@EventHandler
	public void onShowMethod(ShowMethodViewEvent event) {
		getView().showMethodView();

		project = projectProxy.getSelectedProject();
		
		getView().showCashflowSourcePanel(false);
		getView().hideInputPanels();
		
		//Falls bereits eine Auswahl gespeichert wurde, soll diese gesetzt werden, andernfalls neues erzeugen
		if (project.getCalculationMethod() != null){
			calculationMethod = project.getCalculationMethod();
			toggleCalculationMethod(calculationMethod);
			logger.debug("Berechnungsmethode bereits ausgewaehlt: " + project.getCalculationMethod());
		} else if (project.getCalculationMethod() == null) {
			project.setCalculationMethod(calculationMethod);
			logger.debug("Berechnungsmethode noch nicht ausgewaehlt");
		}

		//Falls bereits eine Herkunft-Auswahl gespeichert wurde, soll diese gesetzt werden, andernfalls neues erzeugen
		if (project.getProjectCashflowSource() != null) {
			projectCashflowSource = project.getProjectCashflowSource();
			toggleCashflowSource(projectCashflowSource.getCashflowSource());
			logger.debug("Herkunft der Cashflows bereits ausgewaehlt");
		} else if (project.getProjectCashflowSource() == null){
			projectCashflowSource = new ProjectCashflowSource();
			project.setProjectCashflowSource(projectCashflowSource);
			logger.debug("Herkunft der Cashflows noch nicht ausgewaehlt:");
		}
		
		//Falls bereits eine Auswahl gespeichert wurde, soll diese gesetzt werden, andernfalls neues erzeugen
		if (project.getProjectInputType() != null){
			projectInputType = project.getProjectInputType();
			if (projectInputType.isDeterministic()) {
				toggleDeterministicInput(projectInputType.getDeterministicInput());
				logger.debug("Deterministische Eingabemethode bereits ausgewaehlt");
			} else if (projectInputType.isStochastic()) {
				toggleStochasticInput(projectInputType.getStochasticInput());
				logger.debug("Stochastische Eingabemethode bereits ausgewaehlt");
			}
		} else if (project.getProjectInputType() == null) {
			projectInputType = new ProjectInputType();
			project.setProjectInputType(projectInputType);
			logger.debug("Eingabemethode noch nicht ausgewaehlt");
		}
			
		
		eventBus.fireEvent(new ScreenSelectableEvent(NavigationSteps.METHOD,
				true));
		
	}
	
	public void saveCalculationMethod(AbstractCalculationMethod newSelected){
		eventBus.fireEvent(new CheckCalculationMethod(newSelected));
		
		if (newSelected instanceof APV){
			calculationMethod = new APV();
			calculationMethod.setSelected(true);
			project.setCalculationMethod(calculationMethod);
			logger.debug("Berechnungsmethode im Projekt gespeichert: " + project.getCalculationMethod());
			
			toggleCalculationMethod(newSelected);
		}
		else if (newSelected instanceof FTE){
			calculationMethod = new FTE();
			calculationMethod.setSelected(true);
			project.setCalculationMethod(calculationMethod);
			logger.debug("Berechnungsmethode im Projekt gespeichert: " + project.getCalculationMethod());
			
			saveCashflowSource(CashflowSource.DETERMINISTIC);
			
		}
	}
	
	public void saveCashflowSource(CashflowSource newSelected){
		eventBus.fireEvent(new CashflowSourceChangedEvent());
		
		projectCashflowSource.setCashflowSource(newSelected);
		projectCashflowSource.setCashflowSelected(true);
		project.setProjectCashflowSource(projectCashflowSource);
		logger.debug("Herkunft der Cashflows im Projekt gespeichert: " + project.getProjectCashflowSource().getCashflowSource());
		
		if(newSelected.equals(CashflowSource.DETERMINISTIC)){
			projectInputType.setDeterministic(true);
			
			if (!(project.getCalculationMethod() instanceof FTE)){
				toggleCashflowSource(newSelected);
			}
			
		}
		else if(newSelected.equals(CashflowSource.STOCHASTIC)){
			projectInputType.setStochastic(true);
			
			saveStochasticMethod();
			toggleCashflowSource(newSelected);
			
		}
	}
	
	public void saveStochasticMethod(){
		stochasticMethod = new TimeseriesCalculator();
		project.setStochasticMethod(stochasticMethod);
		logger.debug("Stochastische Methode im Projekt gespeichert: " + project.getStochasticMethod());
	}
	
	public void saveDeterministicInput(InputType newSelected){
		eventBus.fireEvent(new InputTypeChangedEvent());
		
		projectInputType.setDeterministicInput(newSelected);
		projectInputType.isDeterministic();
		project.setProjectInputType(projectInputType);
		logger.debug("Deterministische Eingabe im Projekt gespeichert: " + project.getProjectInputType().getDeterministicInput());
		
		toggleDeterministicInput(newSelected);
		
	}
	
	public void saveStochasticInput(InputType newSelected) {
		eventBus.fireEvent(new InputTypeChangedEvent());
		
		projectInputType.setStochasticInput(newSelected);
		projectInputType.isStochastic();
		project.setProjectInputType(projectInputType);
		logger.debug("Stochastische Eingabe im Projekt gespeichert: " + project.getProjectInputType().getStochasticInput());
		
		toggleStochasticInput(newSelected);
		
	}
	
	public void toggleCalculationMethod(AbstractCalculationMethod newSelected) {
		
		getView().selectCalculation(newSelected);
		
		if (newSelected instanceof APV){
			//projectCashflowSource.setCashflowSource(null);
			//project.setProjectCashflowSource(projectCashflowSource);
			//logger.debug("CashflowSource des Projektes zurueckgesetzt");
			
			getView().showCashflowSourcePanel(true);
			getView().hideInputPanels();
			logger.debug("Panel fuer Herkunft der Cahslflows anzeigen");	
		}	

		else if (newSelected instanceof FTE){
			getView().showDeterministicInputPanel();
			getView().showCashflowSourcePanel(false);
			logger.debug("Panel fuer Auswahl der deterministischen Eingabe anzeigen");	
		}
		
		this.validate(new ValidateContentStateEvent());

	}
	
	public void toggleCashflowSource(CashflowSource newSelected) {
		
		getView().selectCashflow(newSelected);
		
		if(newSelected.equals(CashflowSource.DETERMINISTIC)){
			getView().showDeterministicInputPanel();
			logger.debug("Panel fuer Auswahl der deterministischen Eingabe anzeigen");
		}
		else if(newSelected.equals(CashflowSource.STOCHASTIC)){
			getView().showStochasticInputPanel();
			logger.debug("Panel fuer Auswahl der stochastischen Eingabe anzeigen");
			
		}
		
		this.validate(new ValidateContentStateEvent());
	}
	
	public void toggleDeterministicInput(InputType newSelected) {
		
		getView().selectInput(false, newSelected);
		
		this.validate(new ValidateContentStateEvent());
	}

	public void toggleStochasticInput(InputType newSelected) {
		
		getView().selectInput(true, newSelected);
		
		this.validate(new ValidateContentStateEvent());
	
	}
	

	@Override
	public boolean isValid() {
		boolean valid = false;
		
		if (project.getProjectInputType() != null) {
			valid = true;
		}
		
		
		if (showError) {
				
		}

		return valid;
	}
	
	@Override
	@EventHandler
	public void validate(ValidateContentStateEvent event) {
		if (!this.isValid()) {
			eventBus.fireEvent(new InvalidStateEvent(NavigationSteps.METHOD,
					showError));
		} else {
			eventBus.fireEvent(new ValidStateEvent(NavigationSteps.METHOD));
		}
	}

	@Override
	@EventHandler
	public void handleShowErrors(ShowErrorsOnScreenEvent event) {

		if (event.getStep() == NavigationSteps.METHOD) {
			showError = true;
		}

	}

}
