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

package dhbw.ka.mwi.businesshorizon2.ui.parameterScreen.description;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.ui.VerticalLayout;

import dhbw.ka.mwi.businesshorizon2.ui.parameterScreen.buttonsMiddle.ParameterButtonsMiddlePresenter;
import dhbw.ka.mwi.businesshorizon2.ui.parameterScreen.buttonsMiddle.ParameterButtonsMiddleViewInterface;

/**
 * Dies ist die Vaadin-Implementierung der ParameterDescrition Bereicht. Sie stellt den rechten Bereich mit der Beschreibung dar.
 * 
 * @author Tobias Lindner
 * 
 */
public class ParameterDescriptionViewImpl extends VerticalLayout implements
		ParameterDescriptionViewInterface {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger("ParameterDescriptionViewImpl.class");

	@Autowired
	private ParameterDescriptionPresenter presenter;

	/**
	 * Dies ist der Konstruktor, der von Spring nach der Initialierung der
	 * Dependencies aufgerufen wird. Er registriert sich selbst beim Presenter
	 * und initialisiert die View-Komponenten. 
	 * 
	 * @author Tobias Lindner
	 */
	@PostConstruct
	public void init() {
		presenter.setView(this);
		generateUI();
		logger.debug("Initialisierung beendet");
	}

	/**
	 * Konkrete Ausprogrammierung der UI-Elemente.
	 * 
	 * @author Tobias Lindner
	 */
	private void generateUI() {
		
	}
	
}
