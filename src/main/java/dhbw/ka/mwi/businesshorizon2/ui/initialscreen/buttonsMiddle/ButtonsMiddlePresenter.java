/*******************************************************************************
 * BusinessHorizon2
 *
 * Copyright (C) 
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
	
//	@EventHandler
//	public void onShowParameterScreen (ShowParameterScreenViewEvent event) {
//		project = projectProxy.getSelectedProject();
//		logger.debug("Project TypMethod: " + project.getTypMethod());
//		
//		if (project.getProjectInputType().isStochastic()) {
//			getView().setStochasticParameter();
//			logger.debug("Stochastische Buttons gesetzt");
//		}
//		
//		else {
//			getView().setDeterministicParameter();
//			logger.debug ("Deterministische Buttons gesetzt");
//		}
//	}
	
//	@EventHandler
//	public void onShowMethodScreen (ShowProcessStepEvent event) {
//		if (event.getScreen().equals(screen.METHODSELECTION)) {
//			getView().setInitialButtons();
//			logger.debug("Initial Buttons gesetzt");
//		}
//		
//	}
	
	/**
	 * Dieser EventHandler passt die Button nach den Erfordernissen des Periodscreen an.
	 * 
	 * @author Tobias Lindner
	 * 
	 */
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
	
	/**
	 * Dieser EventHandler passt die Button nach den Erfordernissen des Scenariosscreens an.
	 * 
	 * @author Tobias Lindner
	 * 
	 */
	@EventHandler
	public void onShowScenarioScreen (ShowProcessStepEvent event) {
		if (event.getScreen().equals(screen.SCENARIOS)) {
			getView().setScenarioButton();
		}
	}
	
	/**
	 * Diese Eventhandler setzt den Weiterbutton ggfs. auf Disabled bzw. Enabled.
	 * @author Tobias Lindner
	 *
	 */
	@EventHandler
	public void onParameterValidation (ValidationEvent event) {
		if (event.getValid()) {
			getView().enableNext();
		}
		
		else {
			getView().disableNext();
		}
	}
	
	/**
	 * Dieser EventHandler passt die Button nach den Erfordernissen des Resultscreens an.
	 * 
	 * @author Tobias Lindner
	 * 
	 */
	@EventHandler
	public void onShowResultScreen (ShowProcessStepEvent event) {
		if (event.getScreen().equals(screen.RESULT)) {
			getView().setResultButton();
		}
	}

	/**
	 * Methode für den Listener des obersten Buttons.
	 * @author Tobias Lindner
	 */
	public void showParameterScreen() {
		eventBus.fireEvent(new ShowProcessStepEvent(screen.PARAMETER));
	}
	
	/**
	 * Methode für den Listener des obersten Buttons.
	 * @author Tobias Lindner
	 */
	public void showMethodScreen() {
		eventBus.fireEvent(new ShowProcessStepEvent(screen.METHODSELECTION));
	}
	
	/**
	 * Methode für den Listener des obersten Buttons.
	 * @author Tobias Lindner
	 */
	public void showPeriodScreen() {
		eventBus.fireEvent(new ShowProcessStepEvent(screen.PERIODS));
	}
	
	/**
	 * Methode für den Listener des obersten Buttons.
	 * @author Tobias Lindner
	 */
	public void showScenarioScreen() {
		eventBus.fireEvent(new ShowProcessStepEvent(screen.SCENARIOS));
	}
	
	/**
	 * Methode für den Listener des obersten Buttons.
	 * @author Tobias Lindner
	 */
	public void showResultScreen() {
		eventBus.fireEvent(new ShowProcessStepEvent(screen.RESULT));
	}
	
	/**
	 * Methode für den Listener des obersten Buttons.
	 * @author Tobias Lindner
	 */
	public void showDescription () {
		eventBus.fireEvent(new ShowDescriptionEvent());
	}

}
