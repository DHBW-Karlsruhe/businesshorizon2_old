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
import dhbw.ka.mwi.businesshorizon2.models.DeterministicResultContainer;
import dhbw.ka.mwi.businesshorizon2.models.InputType;
import dhbw.ka.mwi.businesshorizon2.models.Project;
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

	private AbstractStochasticMethod stochasticMethod;
	private AbstractCalculationMethod calculationMethod;
	
	//entfernen, da nur eine Methode pro Projekt
	private SortedSet<AbstractStochasticMethod> stochasticMethods;
	private SortedSet<AbstractCalculationMethod> calculationMethods;

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
		logger.debug("showMethodView aufgerufen");

		project = projectProxy.getSelectedProject();
		stochasticMethods = new TreeSet<AbstractStochasticMethod>();
		calculationMethods = new TreeSet<AbstractCalculationMethod>();

		//Hier werden die Methoden die zur Auswahl stehen sollen, auf dem Reiter angezeigt
		if (project.getCalculationMethods() == null) {
			calculationMethods.add(new APV());
			calculationMethods.add(new FTE());
			project.setCalculationMethods(calculationMethods);
		} else {
			calculationMethods = project.getCalculationMethods();
		}
		//deaktivieren
		if (project.getStochasticMethods() == null) {
			stochasticMethods.add(new RandomWalk());
			stochasticMethods.add(new TimeseriesCalculator());
			stochasticMethods.add(new Wiener());
			project.setStochasticMethods(stochasticMethods);
		} else {
			stochasticMethods = project.getStochasticMethods();
		}		
		
		if (project.getProjectInputType() == null) {
			projectInputType = new ProjectInputType();
			project.setProjectInputType(projectInputType);
		} else {
			projectInputType = project.getProjectInputType();
		}

		// Annika Weis
		for (AbstractCalculationMethod m : calculationMethods) {
			getView().showCalculationMethod(m);
		}
		
		//deaktivieren
		for (AbstractStochasticMethod m : stochasticMethods) {
			getView().showStochasticMethod(m);
		}
		
		//Setzt Zustand der CheckBoxes
		getView().setStochastic(projectInputType.getStochastic());
		getView().setDeterministic(projectInputType.getCalculation());

		Boolean CalculationState = projectInputType.getStochastic();

		//Stand 17:30
		if (CalculationState != null) {
			getView().enableStochasticMethodSelection(CalculationState);
		} else {
			projectInputType.setStochastic(false);
			getView().enableStochasticMethodSelection(false);
		}
		
		
		//Annika Weis
		Boolean DeterministicState = projectInputType.getCalculation();

		if (DeterministicState != null) {
			getView().enableCalculationMethodSelection(DeterministicState);
		} else {
			projectInputType.setCalculation(false);
			getView().enableCalculationMethodSelection(false);
		}
		

		getView().showInputMethodSelection(true,projectInputType.getStochastic());
		getView().showInputMethodSelection(false,projectInputType.getCalculation());
		
		//Annika Weis
		getView().showInputMethod_deterministicSelection(true,projectInputType.getCalculation());
		getView().showInputMethod_deterministicSelection(false,projectInputType.getStochastic());
		
		
		getView().selectInput(true, projectInputType.getStochasticInput());
		getView().selectInput(false, projectInputType.getDeterministicInput());

		eventBus.fireEvent(new ScreenSelectableEvent(NavigationSteps.METHOD,
				true));
		
		toggleCalculationMethodType(true,true);

	}

	@Override
	public boolean isValid() {
		boolean valid = false;
		if (projectInputType.getStochastic()) {

			for (AbstractStochasticMethod m : stochasticMethods) {
				if (m.getSelected()) {
					valid = true;
				}
			}

			if (showError) {
				getView().showErrorNoMethodSelected(valid);
			}

		} else if (projectInputType.getCalculation()) {

			valid = true;

			// Annika Weis
			valid = false;
			for (AbstractCalculationMethod m : calculationMethods) {
				if (m.getSelected()) {
					valid = true;
				}

			}

			if (showError) {
				// Annika Weis
				// getView().showErrorNothingSelected(valid);
				getView().showErrorNoMethodSelected(valid);
			}
		}

		return valid;
	}

	//Annika Weis
	public void toggleCalculationMethodType(Boolean deterministic, Boolean checked) {
		//logger.debug("toggleMethod_deterministicType " + deterministic +  " / " + checked);
		logger.debug("Panel fuer Berechnungsmethode anzeigen");
		eventBus.fireEvent(new CheckMethod_deterministicTypeEvent(true, true));

		//getView().showInputMethod_deterministicSelection(deterministic, checked);

		/**if (deterministic) {
			projectInputType.setCalculation(checked);
			getView().enableCalculationMethodSelection(checked);

		} else if (!deterministic) {
			projectInputType.setCalculation(checked);
		}*/
		
		getView().enableCalculationMethodSelection(checked);

		this.validate(new ValidateContentStateEvent());

	}
	
	public void toggleCashflowSource(boolean b, InputType selected) {
		// TODO Auto-generated method stub
		
	}
	
	public void toggleStochasticMethodType(Boolean stochastic, Boolean checked) {
		logger.debug("toggleMethodType");
		eventBus.fireEvent(new CheckMethodTypeEvent(stochastic, checked));

		getView().showInputMethodSelection(stochastic, checked);

		if (stochastic) {
			projectInputType.setStochastic(checked);
			getView().enableStochasticMethodSelection(checked);

		} else if (!stochastic) {
			projectInputType.setCalculation(checked);
		}

		this.validate(new ValidateContentStateEvent());

	}
	
	public void toggleCalculationMethod(AbstractCalculationMethod checkedMethod) {
		eventBus.fireEvent(new CheckCalculationMethod(checkedMethod));

		calculationMethod = checkedMethod;
		calculationMethod.setSelected(true);
		logger.debug("Berechnungsmethode ausgewaehlt: " + checkedMethod.toString());
		
		/**for (AbstractCalculationMethod m : calculationMethods) {
			m.setSelected(false);
			if (checkedMethod.contains(m)) {
				m.setSelected(true);
			}
		}*/
		
		//TODO: weitere Auswahlmöglichkeiten abhängig vom Berechnungstyp einblenden
		
		this.validate(new ValidateContentStateEvent());

	}
	

	public void toggleStochasticMethod(Set<AbstractStochasticMethod> checkedMethods) {
		eventBus.fireEvent(new CheckMethodEvent(checkedMethods));

		for (AbstractStochasticMethod m : stochasticMethods) {
			m.setSelected(false);
			if (checkedMethods.contains(m)) {
				m.setSelected(true);
			}

		}
		this.validate(new ValidateContentStateEvent());

	}
	

	//Annika Weis
	public void toggleDeterministicInput(Boolean deterministic, InputType newSelected) {
		eventBus.fireEvent(new InputTypeChangedEvent());
		if (deterministic) {
			projectInputType.setDeterministicInput(newSelected);
		} else {
			projectInputType.setStochasticInput(newSelected);
		}
	}

	public void toggleStochasticInput(Boolean stochastic, InputType newSelected) {
		eventBus.fireEvent(new InputTypeChangedEvent());
		if (stochastic) {
			projectInputType.setStochasticInput(newSelected);
		} else {
			projectInputType.setDeterministicInput(newSelected);
		}
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

	@EventHandler
	@Override
	public void handleShowErrors(ShowErrorsOnScreenEvent event) {

		if (event.getStep() == NavigationSteps.METHOD) {
			showError = true;
		}

	}

}
