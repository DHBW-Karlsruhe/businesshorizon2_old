
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

import dhbw.ka.mwi.businesshorizon2.methods.AbstractStochasticMethod;
import dhbw.ka.mwi.businesshorizon2.methods.random.RandomWalk;
import dhbw.ka.mwi.businesshorizon2.methods.timeseries.TimeseriesCalculator;
import dhbw.ka.mwi.businesshorizon2.methods.wiener.Wiener;
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

	private static final Logger logger = Logger.getLogger("MethodPresenter.class");

	private Boolean showError = false;

	@Autowired
	private EventBus eventBus;

	@Autowired
	private ProjectProxy projectProxy;

	private Project project;

	private SortedSet<AbstractStochasticMethod> methods;

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

			for (AbstractStochasticMethod m : methods) {
				if (m.getSelected()) {
					valid = true;
				}

			}

			if (showError) {
				getView().showErrorNoMethodSelected(valid);
			}

		} else if (projectInputType.getDeterministic()) {

			valid = true;

			if (showError) {
				getView().showErrorNothingSelected(valid);
			}
		}

		return valid;
	}

	public void toggleMethodType(Boolean stochastic, Boolean checked) {
		eventBus.fireEvent(new CheckMethodTypeEvent(stochastic, checked));

		getView().showInputMethodSelection(stochastic, checked);

		if (stochastic) {
			projectInputType.setStochastic(checked);
			getView().enableMethodSelection(checked);

		} else if (!stochastic) {
			projectInputType.setDeterministic(checked);
		}

		this.validate(new ValidateContentStateEvent());

	}

	public void toggleMethod(Set<AbstractStochasticMethod> checkedMethods) {
		eventBus.fireEvent(new CheckMethodEvent(checkedMethods));

		for (AbstractStochasticMethod m : methods) {
			m.setSelected(false);
			if (checkedMethods.contains(m)) {
				m.setSelected(true);
			}

		}
		this.validate(new ValidateContentStateEvent());

	}

	public void toggleMethodTypeInput(Boolean stochastic, InputType newSelected) {
		eventBus.fireEvent(new InputTypeChangedEvent());
		if (stochastic) {
			projectInputType.setStochasticInput(newSelected);
		} else {
			projectInputType.setDeterministicInput(newSelected);
		}
	}

	@EventHandler
	public void onShowMethod(ShowMethodViewEvent event) {
		getView().showMethodView();

		project = projectProxy.getSelectedProject();
		methods = new TreeSet<AbstractStochasticMethod>();

		if (project.getMethods() == null) {

			methods.add(new RandomWalk());
			methods.add(new TimeseriesCalculator());
			methods.add(new Wiener());

			project.setMethods(methods);
		} else {
			methods = project.getMethods();
		}

		if (project.getProjectInputType() == null) {
			projectInputType = new ProjectInputType();
			project.setProjectInputType(projectInputType);
		} else {
			projectInputType = project.getProjectInputType();
		}

		for (AbstractStochasticMethod m : methods) {
			getView().showMethod(m);
		}
		
		getView().setStochastic(projectInputType.getStochastic());
		getView().setDeterministic(projectInputType.getDeterministic());

		Boolean state = projectInputType.getStochastic();

		if (state != null) {
			getView().enableMethodSelection(state);
		} else {
			projectInputType.setStochastic(false);
			getView().enableMethodSelection(false);
		}

		getView().showInputMethodSelection(true,
				projectInputType.getStochastic());
		getView().showInputMethodSelection(false,
				projectInputType.getDeterministic());
		getView().selectInput(true,
				projectInputType.getStochasticInput());
		getView().selectInput(false,
				projectInputType.getDeterministicInput());

		eventBus.fireEvent(new ScreenSelectableEvent(NavigationSteps.METHOD,true));

	}

	@Override
	@EventHandler
	public void validate(ValidateContentStateEvent event) {
		if (!this.isValid()) {
			eventBus.fireEvent(new InvalidStateEvent(NavigationSteps.METHOD, showError));
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
