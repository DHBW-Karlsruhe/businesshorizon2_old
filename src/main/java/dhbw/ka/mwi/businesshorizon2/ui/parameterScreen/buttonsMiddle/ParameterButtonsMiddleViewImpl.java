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

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.VerticalLayout;

/**
 * Dies ist die Vaadin-Implementierung der ParameterButtonsMiddleView. Sie stellt das mittlere Layout des Parameterscreens dar.
 * 
 * @author Tobias Lindner
 * 
 */
public class ParameterButtonsMiddleViewImpl extends VerticalLayout implements
		ParameterButtonsMiddleViewInterface {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger("ParameterButtonsMiddleViewImpl.class");

	@Autowired
	private ParameterButtonsMiddlePresenter presenter;
	
	Button detMeth;
	Button description;
	Button next;

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
		setSpacing(true);
		
		float hoehe = 95;
		float breite = 240;		
		
		detMeth = new Button ("deterministische Methode");
		detMeth.setStyleName("paramButton");
		detMeth.setHeight(hoehe, UNITS_PIXELS);
		detMeth.setWidth(breite, UNITS_PIXELS);
		detMeth.addListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				presenter.showInput();
			}
			
		});
		addComponent(detMeth);
		setComponentAlignment(detMeth, Alignment.TOP_CENTER);

		description = new Button ("Kurzbeschreibung");
		description.setStyleName("paramButton");
		description.setHeight(hoehe, UNITS_PIXELS);
		description.setWidth(breite, UNITS_PIXELS);
		description.addListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				presenter.showDescription();
			}
			
		});
		addComponent(description);
		setComponentAlignment(description, Alignment.MIDDLE_CENTER);
		
		next = new Button ("Weiter zu Schritt 3");
		next.setStyleName("paramButton");
		next.setHeight(hoehe, UNITS_PIXELS);
		next.setWidth(breite, UNITS_PIXELS);
		next.addListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				presenter.showStep3();
			}
			
		});
		addComponent(next);
		setComponentAlignment(next, Alignment.BOTTOM_CENTER);

	}
	
	public void setMethodButtonActivated () {
		detMeth.setStyleName("paramButtonActivated");
		description.setStyleName("paramButton");
		detMeth.requestRepaint();
		description.requestRepaint();
	}
	
	public void setDescriptionButtonActivated () {
		detMeth.setStyleName("paramButton");
		description.setStyleName("paramButtonActivated");
		detMeth.requestRepaint();
		description.requestRepaint();
	}
	
}
