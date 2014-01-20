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
 * @author Julius Hacker
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

	public void toggleMethodType(Boolean stochastic, Boolean checked) {
		logger.debug("toggleMethodType");
		eventBus.fireEvent(new CheckMethodTypeEvent(stochastic, checked));

		getView().showInputMethodSelection(stochastic, checked);

		if (stochastic) {
			projectInputType.setStochastic(checked);
			getView().enableMethodSelection(checked);

		} else if (!stochastic) {
			projectInputType.setCalculation(checked);
		}

		this.validate(new ValidateContentStateEvent());

	}
	
	//Annika Weis
	public void toggleMethod_deterministicType(Boolean deterministic, Boolean checked) {
		logger.debug("toggleMethod_deterministicType " + deterministic +  " / " + checked);
		eventBus.fireEvent(new CheckMethod_deterministicTypeEvent(deterministic, checked));

		getView().showInputMethod_deterministicSelection(deterministic, checked);

		if (deterministic) {
			projectInputType.setCalculation(checked);
			getView().enableMethod_deterministicSelection(checked);

		} else if (!deterministic) {
			projectInputType.setCalculation(checked);
		}

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
	
	public void toggleCalculationMethod(Set<AbstractCalculationMethod> checkedMethods) {
		eventBus.fireEvent(new CheckMethod_deterministicEvent(checkedMethods));

		for (AbstractCalculationMethod m : calculationMethods) {
			m.setSelected(false);
			if (checkedMethods.contains(m)) {
				m.setSelected(true);
			}

		}
		this.validate(new ValidateContentStateEvent());

	}
	
	

	public void toggleStochasticInput(Boolean stochastic, InputType newSelected) {
		eventBus.fireEvent(new InputTypeChangedEvent());
		if (stochastic) {
			projectInputType.setStochasticInput(newSelected);
		} else {
			projectInputType.setDeterministicInput(newSelected);
		}
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

	@EventHandler
	public void onShowMethod(ShowMethodViewEvent event) {
		getView().showMethodView();

		project = projectProxy.getSelectedProject();
		stochasticMethods = new TreeSet<AbstractStochasticMethod>();
		calculationMethods = new TreeSet<AbstractCalculationMethod>();

		//Hier werden die Methoden die zur Auswahl stehen sollen, auf dem Reiter angezeigt
		if (project.getStochasticMethods() == null) {
			stochasticMethods.add(new RandomWalk());
			stochasticMethods.add(new TimeseriesCalculator());
			stochasticMethods.add(new Wiener());
			project.setStochasticMethods(stochasticMethods);
		} else {
			stochasticMethods = project.getStochasticMethods();
		}		
		//Annika Weis
		if (project.getCalculationMethods() == null) {
			calculationMethods.add(new FTE());
			calculationMethods.add(new APV());
			project.setCalculationMethods(calculationMethods);
		} else {
			calculationMethods = project.getCalculationMethods();
		}
		
		
		if (project.getProjectInputType() == null) {
			projectInputType = new ProjectInputType();
			project.setProjectInputType(projectInputType);
		} else {
			projectInputType = project.getProjectInputType();
		}

		for (AbstractStochasticMethod m : stochasticMethods) {
			getView().showStochasticMethod(m);
		}
		// Annika Weis
		for (AbstractCalculationMethod m : calculationMethods) {
			getView().showDerterministicMethod(m);
		}

		getView().setStochastic(projectInputType.getStochastic());
		getView().setDeterministic(projectInputType.getCalculation());

		Boolean state = projectInputType.getStochastic();

		if (state != null) {
			getView().enableMethodSelection(state);
		} else {
			projectInputType.setStochastic(false);
			getView().enableMethodSelection(false);
		}
		
		
		//Annika Weis
		Boolean state_deterministic = projectInputType.getCalculation();

		if (state_deterministic != null) {
			getView().enableMethod_deterministicSelection(state_deterministic);
		} else {
			projectInputType.setCalculation(false);
			getView().enableMethod_deterministicSelection(false);
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

	public void togglecashflowSource(boolean b, InputType selected) {
		// TODO Auto-generated method stub
		
	}

}
