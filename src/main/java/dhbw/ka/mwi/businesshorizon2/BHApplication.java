
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
package dhbw.ka.mwi.businesshorizon2;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.mvplite.event.EventBus;
import com.mvplite.event.EventHandler;
import com.vaadin.Application;
import com.vaadin.terminal.ExternalResource;

import dhbw.ka.mwi.businesshorizon2.ui.initialscreen.InitialScreenViewImpl;
import dhbw.ka.mwi.businesshorizon2.ui.initialscreen.ShowInitialScreenViewEvent;
import dhbw.ka.mwi.businesshorizon2.ui.initialscreen.projectlist.ShowProjectEvent;
import dhbw.ka.mwi.businesshorizon2.ui.login.LogInScreenViewImplv2;
import dhbw.ka.mwi.businesshorizon2.ui.login.LogInScreenViewInterface;
import dhbw.ka.mwi.businesshorizon2.ui.login.LogoutEvent;
import dhbw.ka.mwi.businesshorizon2.ui.login.ShowLogInScreenEvent;
import dhbw.ka.mwi.businesshorizon2.ui.login.ShowUserEvent;
import dhbw.ka.mwi.businesshorizon2.ui.parameterScreen.ParameterScreenViewImpl;
import dhbw.ka.mwi.businesshorizon2.ui.parameterScreen.ShowParameterScreenViewEvent;
import dhbw.ka.mwi.businesshorizon2.ui.process.ProcessViewImpl;
import dhbw.ka.mwi.businesshorizon2.ui.process.ShowProcessViewEvent;

/**
 * Das ist die Haupt-Einstiegsklasse der Anwendung. Es ist vergleichbar mit der
 * statischen main()- Methode in normalen Java-Anwendungen. Die Initialisierung
 * erfolgt in der Klasse SpringApplicationServlet. Dabei werden auch die
 * Autowired-Annotations aufgeloest.
 * 
 * @author Christian Gahlert, Marcel Rosenberger
 */
public class BHApplication extends Application {
	private static final long serialVersionUID = 1L;
	
	private static final Logger logger = Logger.getLogger("BHApplication.class");

	@Autowired
	private ProcessViewImpl processView;
	
	@Autowired
	private InitialScreenViewImpl initialScreenView;
	
	@Autowired
	private LogInScreenViewImplv2 logInScreenView;
	
	@Autowired
	private ParameterScreenViewImpl parameterScreenView;


	@Autowired
	private EventBus eventBus;
	
	/**
	 * Der Konstruktor. Hier wird zunaechst das Theme gesetzt, so dass das Stylesheet 
	 * 		WebContent/VAADIN/themes/bh2/styles.css
	 * verwendet wird. 
	 * 
	 * @author Christian Gahlert
	 */
	public BHApplication() {
		setTheme("bh2");
	}
	
	/**
	 * Diese Methode ist dafuer verantwortlich, das Haupt-Fenster zu laden und
	 * den ShowInitialScreenViewEvent abzusetzen. Durch diesen werden die Listener
	 * darueber informiert, dass das Hauptfenster angezeigt wird.
	 * 
	 * @author Christian Gahlert
	 */
	@Override
	public void init() {
		eventBus.addHandler(this);
		
		setMainWindow(logInScreenView);
		eventBus.fireEvent(new ShowLogInScreenEvent());
		logger.debug("ShowLogInScreenEvent gefeuert");
	}

	/**
	 * Die Methode triggert die Anzeige der Projektuebersichtsseite, sobald der User
	 * sich erfolgreich eingeloggt hat. 
	 * 
	 * 
	 * @author Julius Hacker, Marcel Rosenberger
	 * @param event Der ausgeloeste ShowUserEvent
	 */
	@EventHandler
	public void showInitialView(ShowUserEvent event) {
		this.removeWindow(processView);
		this.removeWindow(initialScreenView);
		this.removeWindow(parameterScreenView);
		initialScreenView.setName("overview");
		addWindow(initialScreenView);
		setMainWindow(initialScreenView);
		logInScreenView.open(new ExternalResource(initialScreenView.getURL()));

		eventBus.fireEvent(new ShowInitialScreenViewEvent(event.getUser()));
		logger.debug("ShowInitialScreenViewEvent gefeuert");
	}
	
	/**
	 * Die Methode triggert die Anzeige des ParameterScreen entspricht Schritt 2.
	 * 
	 * 
	 * @author Tobias Lindner
	 * @param event Der ausgeloest ShowParameterScreenViewEvent
	 */
	@EventHandler
	public void showParameterScreenView (ShowParameterScreenViewEvent event) {
		this.removeWindow(processView);
		this.removeWindow(logInScreenView);
		parameterScreenView.setName("parameterScreen");
		addWindow(parameterScreenView);
		setMainWindow(parameterScreenView);
		initialScreenView.open(new ExternalResource(parameterScreenView.getURL()));
		logger.debug("ParameterScreenView gesetzt");
	}

	/**
	 * Die Methode triggert die Anzeige der Prozessansicht, sobald an einer
	 * Stelle des Programmes ein Projekt angezeigt wurde. 
	 * 
	 * @author Julius Hacker, Marcel Rosenberger
	 * @param event
	 *            Der ausgeloeste ShowProjectEvent
	 */
	@EventHandler
	public void showProcessView(ShowProjectEvent event) {
		//
			this.removeWindow(parameterScreenView);
		//
		
		processView.setName("process");
		addWindow(processView);
		setMainWindow(processView);
		initialScreenView.open(new ExternalResource(processView.getURL()));

		eventBus.fireEvent(new ShowProcessViewEvent());
	}
	
	/**
	 * Die Methode triggert die Anzeige der Loginview, sobald an einer
	 * Stelle des Programmes (entweder InitialScreen oder in den ProcessViews) 
	 * der Logout-Button betätigt wurde. 
	 * Nachdem der Login-Screen als Main-Window gesetzt wurde müssen noch 
	 * Initialscreen und ProcessView gelöscht werden.
	 * 
	 * @author Marcel Rosenberger
	 * @param event
	 *            Das ausgeloeste LogoutEvent
	 */
	@EventHandler
	public void showLoginView(LogoutEvent event) {
		this.removeWindow(logInScreenView);
		logInScreenView.setName("login");
		addWindow(logInScreenView);
		setMainWindow(logInScreenView);
		logInScreenView.open(new ExternalResource(logInScreenView.getURL()));
		this.removeWindow(processView);
		this.removeWindow(initialScreenView);
		eventBus.fireEvent(new ShowLogInScreenEvent());
		logger.debug("ShowLogInScreenEvent gefeuert");
	}
}
