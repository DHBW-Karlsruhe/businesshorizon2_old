/*******************************************************************************
 * BusinessHorizon2
 *
 * Copyright (C) 
 * 2014-2015 Marco Glaser
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
package dhbw.ka.mwi.businesshorizon2.ui.initialscreen.projectcreation;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.mvplite.event.EventBus;
import com.mvplite.presenter.Presenter;

/**
 * Dieser Presenter ist für die Darstellung des Projekterstellungs Screens zuständig.
 * Der Projekterstellungs Screen wird auch für das Bearbeiten eines Projektes verwendet.
 *
 * @author Marco Glaser
 */
public class StartCalculationButtonPresenter extends Presenter<StartCalculationButtonViewInterface>{

	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger("StartCalcukationButtonPresenter.class");

	@Autowired
	private EventBus eventBus;
	
	/**
	 * Dies ist der Konstruktor, der von Spring nach der Initialierung der
	 * Dependencies aufgerufen wird. Er registriert lediglich sich selbst als
	 * einen EventHandler.
	 *
	 * @author Marco Glaser
	 */
	@PostConstruct
	public void init(){
//		eventBus.addHandler(this);
		logger.debug("Eventhandler Hinzugefügt");
		
	}

}
