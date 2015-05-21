/*******************************************************************************
 * BusinessHorizon2
 *
 * Copyright (C) 
 * 2012-2013 Christian Gahlert, Florian Stier, Kai Westerholz,
 * Timo Belz, Daniel Dengler, Katharina Huber, Christian Scherer, Julius Hacker
 * 2013-2014 Marcel Rosenberger, Mirko Göpfrich, Annika Weis, Katharina Narlock, 
 * Volker Meier
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

package dhbw.ka.mwi.businesshorizon2.ui.resultscreen;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.mvplite.view.View;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.ProgressIndicator;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window.Notification;

/**
 * Diese Klasse implementiert das GUI fuer den Prozessschritt "Ausgabe" in
 * Vaadin.
 * 
 * @author Florian Stier, Mirko Göpfrich, Tobias Lindner
 * 
 */
public class ResultScreenViewImpl extends VerticalLayout implements ResultScreenViewInterface {
	private static final long serialVersionUID = 1L;
	
	private static final Logger logger = Logger.getLogger("ResultScreenViewImpl.class");

	@Autowired
	private ResultScreenPresenter presenter;
	
	private ProgressIndicator progressIndicator;

	/**
	 * Dies ist der Konstruktor, der von Spring nach der Initialierung der
	 * Dependencies aufgerufen wird. Er registriert sich selbst beim Presenter
	 * und initialisiert die View-Komponenten.
	 * 
	 * @author Florian Stier,
	 */
	@PostConstruct
	public void init() {
		presenter.setView(this);
		logger.debug("init beendet");
	}

	/**
	 * Erstelle das GUI zum Prozessschritt "Ausgabe"
	 * 
	 * @author Florian Stier
	 */
	private void generateUi() {

		logger.debug("ui generiert");
		
		progressIndicator = new ProgressIndicator();
		progressIndicator.setIndeterminate(true);
		progressIndicator.setEnabled(true);
		progressIndicator.setStyleName("bar");
		progressIndicator.setCaption("Berechne..");
		
		
		addComponent(progressIndicator);
		setComponentAlignment(progressIndicator, Alignment.MIDDLE_CENTER);
		setStyleName("borderless light");
		setWidth(95, UNITS_PERCENTAGE);
		setHeight(100, UNITS_PERCENTAGE);
		setStyleName("projectDetailsLayout");

	}
	
	/**
	 * Diese Methode entfernt den Style projectDetailsLayout, damit die volle Breite genutzt werden kann.
	 * @author Tobias Lindner
	 */
	public void removeStyle () {
		removeStyleName("projectDetailsLayout");
	}
	
	@Override

	public void showOutputView() {
		this.removeAllComponents();
		generateUi();
		
	}
	
	public void showView(View view){
		addComponent((Component)view);
		logger.debug ("ResultScreenview gesetzt: " + view.toString());
	}

	@Override
	public void showErrorMessge(String message) {
		getWindow().showNotification((String) "Berechnung fehlgeschlagen", message, Notification.TYPE_ERROR_MESSAGE);

	}

	@Override
	public void changeProgress(float progress) {
		if (progress == 1) {
			progressIndicator.setEnabled(false);
			removeComponent(progressIndicator);
		} else {
			progressIndicator.setEnabled(true);
			
		}

	}
	
}
