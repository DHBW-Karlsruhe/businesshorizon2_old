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

package dhbw.ka.mwi.businesshorizon2.ui.process.contentcontainer;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.VerticalSplitPanel;

/**
 * Dies ist die Vaadin-Implementierung der MainView (dem Haupt-Fenster).
 * 
 * @author Julius Hacker
 *
 */
public class ContentContainerViewImpl extends VerticalSplitPanel implements ContentContainerViewInterface {
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private ContentContainerPresenter presenter;
	
	private VerticalLayout content;
	private HorizontalLayout buttons;
	private Button backButton, nextButton;
	
	/**
	 * Dies ist der Konstruktor, der von Spring nach der Initialierung der Dependencies 
	 * aufgerufen wird. Er registriert sich selbst beim Presenter und initialisiert die 
	 * View-Komponenten.
	 * 
	 * @author Julius Hacker
	 */
	@PostConstruct
	public void init() {
		presenter.setView(this);
		
		this.setSizeFull();
		this.setSplitPosition(60, UNITS_PIXELS, true);
		this.setLocked(true);
		this.addStyleName("small");
		
		content = new VerticalLayout();
		
		backButton = new Button("Vorheriger Schritt");
		nextButton = new Button("N\u00e4chster Schritt");
		
		buttons = new HorizontalLayout();
		buttons.addComponent(backButton);
		buttons.addComponent(nextButton);
		
		backButton.addListener(new ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				presenter.showPreviousStep();
			}
			
		});
		
		nextButton.addListener(new ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				presenter.showNextStep();
			}
			
		});
		
		this.setFirstComponent(content);


	}
	
	/**
	 * Diese Methode uebernimmt die Anzeige der vom Presenter vorgegebenen Maske
	 * in Vaadin.
	 * 
	 * @author Julius Hacker
	 */

	public void showContentView(ContentView contentView) {
		this.content.removeAllComponents();
		this.content.addComponent((Component) contentView);
		this.setSecondComponent(buttons);
		this.content.setSizeFull();
	}
	
	public void activateBack(boolean activate) {
		this.backButton.setEnabled(activate);
	}
	
	public void activateNext(boolean activate) {
		this.nextButton.setEnabled(activate);

	}
}
