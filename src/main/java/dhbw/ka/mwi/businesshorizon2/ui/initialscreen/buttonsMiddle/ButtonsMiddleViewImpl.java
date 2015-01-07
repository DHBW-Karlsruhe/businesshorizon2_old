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

import java.io.File;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.terminal.FileResource;
import com.vaadin.terminal.Sizeable;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import dhbw.ka.mwi.businesshorizon2.ui.ButtonMiddle;

/**
 * Dies ist die Vaadin-Implementierung der ParameterButtonsMiddleView. Sie stellt das mittlere Layout des Parameterscreens dar.
 * 
 * @author Tobias Lindner
 * 
 */
public class ButtonsMiddleViewImpl extends VerticalLayout implements
		ButtonsMiddleViewInterface {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger("ParameterButtonsMiddleViewImpl.class");

	@Autowired
	private ButtonsMiddlePresenter presenter;
	
	private HorizontalLayout layoutButton1;
	private HorizontalLayout layoutButton2;
	private HorizontalLayout layoutButton3;
	
	private Embedded methodIcon;
	
	private Label methodLabel;
	private Button method;
	private Button description;
	private Button next;
	private Label gap;
	
	private ButtonMiddle button1;
	private ButtonMiddle button2;
	private ButtonMiddle button3;
	
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
		
		setWidth(85, UNITS_PERCENTAGE);
		setHeight(100, UNITS_PERCENTAGE);
		
		button1 = new ButtonMiddle ("./images/icons/newIcons/1418831298_common_calendar_month-128.png", "Parameter", new LayoutClickListener() {

			@Override
			public void layoutClick(LayoutClickEvent event) {
				button2.setDeactivated();
				button1.setActivated();
			}
			
		});
		addComponent(button1);
		
		button2 = new ButtonMiddle ("./images/icons/newIcons/1418766020_editor_documents_files-128.png", "Beschreibung", new LayoutClickListener() {

			@Override
			public void layoutClick(LayoutClickEvent event) {
				button1.setDeactivated();
				button2.setActivated();
			}
			
		});
		addComponent(button2);
		
		gap = new Label ();
		gap.setSizeFull();
		addComponent(gap);
		setExpandRatio(gap, 1.0f);
		
		button3 = new ButtonMiddle ("./images/icons/newIcons/1418766041_circle_arrow-forward_next-128.png", "Weiter zu Schritt 3", new LayoutClickListener () {

			@Override
			public void layoutClick(LayoutClickEvent event) {

			}
			
		});
		
		addComponent(button3);

	}
	
	public void setStochasticParameter () {
		button1.setDetails("./images/icons/newIcons/1418831298_common_calendar_month-128.png", "Stochastische", new LayoutClickListener() {

			@Override
			public void layoutClick(LayoutClickEvent event) {
				// TODO Auto-generated method stub
			}
			
		});
		
		button2.setListener(new LayoutClickListener() {
			
			@Override
			public void layoutClick(LayoutClickEvent event) {
				// TODO Auto-generated method stub
			}
		});
		
		logger.debug("View: setStochasticParameter");
	}
	
	public void setDeterministicParameter () {
		button1.setDetails("./images/icons/newIcons/1418831298_common_calendar_month-128.png", "Deterministische", new LayoutClickListener () {

			@Override
			public void layoutClick(LayoutClickEvent event) {
				
			}
			
		});
		

		logger.debug ("View: setDeterministicParameter");
	}
	
}
