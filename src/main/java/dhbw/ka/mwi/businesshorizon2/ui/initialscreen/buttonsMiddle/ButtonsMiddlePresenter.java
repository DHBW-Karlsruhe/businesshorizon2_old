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
package dhbw.ka.mwi.businesshorizon2.ui.initialscreen.buttonsMiddle;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.mvplite.event.EventBus;
import com.mvplite.event.EventHandler;
import com.mvplite.presenter.Presenter;

import dhbw.ka.mwi.businesshorizon2.models.InputType;
import dhbw.ka.mwi.businesshorizon2.models.Project;
import dhbw.ka.mwi.businesshorizon2.services.proxies.ProjectProxy;
import dhbw.ka.mwi.businesshorizon2.ui.initialscreen.ShowProcessStepEvent;
import dhbw.ka.mwi.businesshorizon2.ui.initialscreen.ShowProcessStepEvent.screen;
import dhbw.ka.mwi.businesshorizon2.ui.initialscreen.description.ShowDescriptionEvent;
import dhbw.ka.mwi.businesshorizon2.ui.initialscreen.projectlist.ShowProjectEvent;
import dhbw.ka.mwi.businesshorizon2.ui.parameterScreen.ShowParameterScreenViewEvent;
import dhbw.ka.mwi.businesshorizon2.ui.parameterScreen.description.ShowParameterDescriptionViewEvent;
import dhbw.ka.mwi.businesshorizon2.ui.parameterScreen.input.ValidationEvent;
import dhbw.ka.mwi.businesshorizon2.ui.parameterScreen.input.ShowParameterInputViewEvent;
import dhbw.ka.mwi.businesshorizon2.ui.process.ShowNavigationStepEvent;
import dhbw.ka.mwi.businesshorizon2.ui.process.ShowProcessViewEvent;
import dhbw.ka.mwi.businesshorizon2.ui.process.navigation.NavigationSteps;
import dhbw.ka.mwi.businesshorizon2.ui.process.period.ShowPeriodViewEvent;

/**
 * Dies ist der Presenter der mittleren View. Er ist für die Anpassung der ButtonLayouts, sowie der TopBarButtons zuständig.
 * 
 * @author Tobias Lindner, Marco Glaser
 * 
 */
public class ButtonsMiddlePresenter extends Presenter<ButtonsMiddleViewInterface> {
	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger("ButtonsMiddlePresenter.class");

	@Autowired
	private EventBus eventBus;
	
	@Autowired
	private ProjectProxy projectProxy;
	
	private Project project;
	
	private InputType inputType;

	/**
	 * Dies ist der Konstruktor, der von Spring nach der Initialierung der
	 * Dependencies aufgerufen wird. Er registriert sich selbst als einen
	 * EventHandler.
	 * 
	 * @author Tobias Lindner
	 */
	@PostConstruct
	private void init() {
		eventBus.addHandler(this);
		logger.debug("Eventhandler Hinzugefügt");
	}
	
	@EventHandler
	public void onShowParameterScreen (ShowParameterScreenViewEvent event) {
		project = projectProxy.getSelectedProject();
		logger.debug("Project TypMethod: " + project.getTypMethod());
		
		if (project.getProjectInputType().isStochastic()) {
			getView().setStochasticParameter();
			logger.debug("Stochastische Buttons gesetzt");
		}
		
		else {
			getView().setDeterministicParameter();
			logger.debug ("Deterministische Buttons gesetzt");
		}
	}
	
	@EventHandler
	public void onShowMethodScreen (ShowProcessStepEvent event) {
		if (event.getScreen().equals(screen.METHODSELECTION)) {
			getView().setInitialButtons();
			logger.debug("Initial Buttons gesetzt");
		}
		
	}
	
	@EventHandler
	public void onShowPeriodScreen (ShowProcessStepEvent event) {
		if (event.getScreen().equals(screen.PERIODS)) {
			project = projectProxy.getSelectedProject();
			
			if (project.getProjectInputType().isDeterministic()) {
				inputType = project.getProjectInputType().getDeterministicInput();
			}
			
			else if(project.getProjectInputType().isStochastic()) {
				inputType = project.getProjectInputType().getStochasticInput();
			}
			
			switch (inputType) {
				case DIRECT:
					getView().setFCFButton();
					break;
				
				case GESAMTKOSTENVERFAHREN:
					getView().setGKVButton();
					break;
					
				case UMSATZKOSTENVERFAHREN:
					getView().setUKVButton();
					break;
			}
			
		}
	}
	
	@EventHandler
	public void onShowScenarioScreen (ShowProcessStepEvent event) {
		if (event.getScreen().equals(screen.SCENARIOS)) {
			getView().setScenarioButton();
		}
	}
	
	@EventHandler
	public void onParameterValidation (ValidationEvent event) {
		if (event.getValid()) {
			getView().enableNext();
		}
		
		else {
			getView().disableNext();
		}
	}
	
	@EventHandler
	public void onShowResultScreen (ShowProcessStepEvent event) {
		if (event.getScreen().equals(screen.RESULT)) {
			getView().setResultButton();
		}
	}

	public void showParameterScreen() {
		eventBus.fireEvent(new ShowProcessStepEvent(screen.PARAMETER));
	}
	
	public void showMethodScreen() {
		eventBus.fireEvent(new ShowProcessStepEvent(screen.METHODSELECTION));
	}
	
	public void showPeriodScreen() {
		eventBus.fireEvent(new ShowProcessStepEvent(screen.PERIODS));
	}
	
	public void showScenarioScreen() {
		eventBus.fireEvent(new ShowProcessStepEvent(screen.SCENARIOS));
	}
	
	public void showResultScreen() {
		eventBus.fireEvent(new ShowProcessStepEvent(screen.RESULT));
	}
	
	public void showDescription () {
		eventBus.fireEvent(new ShowDescriptionEvent());
	}

}
