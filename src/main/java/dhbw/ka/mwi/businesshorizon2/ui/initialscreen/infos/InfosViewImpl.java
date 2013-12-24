/*******************************************************************************
 * BusinessHorizon2
 * 
 *     Copyright (C) 2012-2013  Christian Gahlert, Florian Stier, Kai Westerholz,
 *     Timo Belz, Daniel Dengler, Katharina Huber, Christian Scherer, Julius Hacker
 * 
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Affero General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 * 
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Affero General Public License for more details.
 * 
 *     You should have received a copy of the GNU Affero General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
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

	private Label textLabel;
	
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
		
		setSizeFull();
		setSpacing(true);
		setMargin(true);
		
		textLabel = new Label("<h1>Good to know...</h1>"
                + "<p>Diese <b>brandneue</b> Applikation <sup>funktioniert</sup> folgendermaßen:</p>"
                + "<p> blabla <i>bla</i>!</p>");
		textLabel.setContentMode(Label.CONTENT_XHTML);
		addComponent(textLabel);
		logger.debug("Rich text erzeugt");


	}

}
