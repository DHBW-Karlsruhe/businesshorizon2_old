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
package dhbw.ka.mwi.businesshorizon2.ui.parameterScreen.buttonsMiddle;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.mvplite.event.EventBus;
import com.mvplite.event.EventHandler;
import com.mvplite.presenter.Presenter;

import dhbw.ka.mwi.businesshorizon2.ui.initialscreen.projectlist.ShowProjectEvent;
import dhbw.ka.mwi.businesshorizon2.ui.parameterScreen.description.ShowParameterDescriptionViewEvent;
import dhbw.ka.mwi.businesshorizon2.ui.parameterScreen.input.ShowParameterInputViewEvent;
import dhbw.ka.mwi.businesshorizon2.ui.process.ShowNavigationStepEvent;
import dhbw.ka.mwi.businesshorizon2.ui.process.ShowProcessViewEvent;
import dhbw.ka.mwi.businesshorizon2.ui.process.navigation.NavigationSteps;
import dhbw.ka.mwi.businesshorizon2.ui.process.period.ShowPeriodViewEvent;

/**
 * 
 * Dies ist der Presenter zur Auflistung, dem Hinzufuegen und dem Loeschen von
 * Perioden. Die Liste der Perioden wird dabei der Spring-Injected Project- Bean
 * entnommen, die lediglich einmal pro Session existiert, und in dem
 * project-Property gespeichert wird.
 * 
 * 
 * @author Christian Scherer
 * 
 */
public class ParameterButtonsMiddlePresenter extends Presenter<ParameterButtonsMiddleViewInterface> {
	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger("ParameterButtonsMiddlePresenter.class");

	@Autowired
	private EventBus eventBus;

	/**
	 * Dies ist der Konstruktor, der von Spring nach der Initialierung der
	 * Dependencies aufgerufen wird. Er registriert sich selbst als einen
	 * EventHandler.
	 * 
	 * @author Tobias Lindner
	 */
	@PostConstruct
	private void init() {
		//eventBus.addHandler(this);
		logger.debug("Eventhandler Hinzugefügt");
	}
	
	public void showDescription () {
		eventBus.fireEvent(new ShowParameterDescriptionViewEvent());
		logger.debug("ShowParameterDescriptionViewEvent geworfen");
		getView().setDescriptionButtonActivated();
		logger.debug ("Button aktualisiert");
	}
	
	public void showInput () {
		eventBus.fireEvent(new ShowParameterInputViewEvent());
		logger.debug("ShowParameterInputViewEvent geworfen");
		getView().setMethodButtonActivated();
		logger.debug("Button aktualisiert");
	}
	
	public void showStep3 () {
		eventBus.fireEvent(new ShowProjectEvent());
		logger.debug("ShowProcessViewEvent geworfen");
		eventBus.fireEvent(new ShowNavigationStepEvent(NavigationSteps.getByNumber(3)));
		logger.debug("ShowPeriodViewEvent geworfen");
	}


}
