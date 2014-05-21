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

package dhbw.ka.mwi.businesshorizon2.ui.initialscreen.infos;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

/**
 * Dies ist die Vaadin-Implementierung der InfoView mit Rich Text für eine adequate Ausgabe.
 * 
 * @author Christian Scherer
 *
 */
public class InfosViewImpl extends VerticalLayout implements InfosViewInterface {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger("InfosViewImpl.class");
	
	@Autowired
	private InfosPresenter presenter;
	
	private VerticalLayout infoContent;

	private Label textLabel;
	private Label title;
	
	/**
	 * Dies ist der Konstruktor, der von Spring nach der Initialierung der
	 * Dependencies aufgerufen wird. Er registriert sich selbst beim Presenter
	 * und initialisiert die View-Komponenten.
	 * 
	 * @author Christian Scherer
	 */
	@PostConstruct
	public void init() {
		presenter.setView(this);
		logger.debug("View durch Presenter gesetzt");
		generateUi();

	}

	public void generateUi() {
		
		infoContent = new VerticalLayout();
		this.addComponent(infoContent);
		
		infoContent.setSizeFull();
		setSpacing(true);
		setMargin(true);
		
		title = new Label("<h1>Willkommen</h1>");
		title.setContentMode(Label.CONTENT_XHTML);
		infoContent.addComponent(title);
		logger.debug("Ueberschrift erstellt");
		
		textLabel = new Label("Willkommen auf der Startseite von Business Horizon!</br></br>"
				+" Mithilfe dieser Software können Sie Ihren zukünftigen Unternehmenswert berechenen lassen. "
				+ "Hierzu stehen Ihnen verschiedene Methoden zur Verfügung, welche im jeweiligen Prozessschritt erläutert werden.</br></br>"
				+ "Im linken Bereich können Sie Ihre bisher angelegten Projekte verwalten oder neue Projekte hinzufügen.</br></br>"
				+ "Durch einen Klick auf das Projektfenster gelangen Sie in das jeweilige Projekt.</br>");
		textLabel.setContentMode(Label.CONTENT_XHTML);
		infoContent.addComponent(textLabel);
		logger.debug("Rich text erzeugt");


	}

}
