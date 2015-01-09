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
package dhbw.ka.mwi.businesshorizon2.ui.initialscreen.description;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.mvplite.event.EventBus;
import com.mvplite.event.EventHandler;
import com.mvplite.presenter.Presenter;

import dhbw.ka.mwi.businesshorizon2.ui.initialscreen.ShowProcessStepEvent;
import dhbw.ka.mwi.businesshorizon2.ui.initialscreen.ShowProcessStepEvent.screen;
import dhbw.ka.mwi.businesshorizon2.ui.initialscreen.buttonsMiddle.ButtonsMiddleViewInterface;

/**
 * 
 *  
 * @author Tobias Lindner
 * 
 */
public class DescriptionPresenter extends Presenter<DescriptionViewInterface> {
	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger("DescriptionPresenter.class");

	@Autowired
	private EventBus eventBus;
	
	private screen actualScreen;

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
	
	/**
	 * Diese Methode hinterlegt immer den aktuellen Screen in einer Variable, sodass später immer die zum aktuellen
	 * Schritt passende Beschreibung angezeigt wird.
	 * 
	 * @author Tobias Lindner
	 * 
	 * @param event
	 * 		ShowProcessStepEvent
	 */
	@EventHandler
	public void setActualScreen (ShowProcessStepEvent event) {
		this.actualScreen = event.getScreen();
		logger.debug("actualScreen im DescriptionPresenter gesetzt");
	}
	
	/**
	 * Diese Methode sorgt für die Anpassung der Texte durch Aufruf der entsprechenden View Methode.
	 * 
	 * @param event
	 * 		ShowDescriptionEvent
	 */
	@EventHandler
	public void showDescription (ShowDescriptionEvent event) {
		getView().setTexts(actualScreen);
	}

}
