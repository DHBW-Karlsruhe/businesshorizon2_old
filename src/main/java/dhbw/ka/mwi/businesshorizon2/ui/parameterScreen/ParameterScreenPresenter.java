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
package dhbw.ka.mwi.businesshorizon2.ui.parameterScreen;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.mvplite.event.EventBus;
import com.mvplite.event.EventHandler;
import com.mvplite.presenter.Presenter;

import dhbw.ka.mwi.businesshorizon2.models.Project;
import dhbw.ka.mwi.businesshorizon2.models.User;
import dhbw.ka.mwi.businesshorizon2.services.authentication.AuthenticationServiceInterface;
import dhbw.ka.mwi.businesshorizon2.services.authentication.UserNotLoggedInException;
import dhbw.ka.mwi.businesshorizon2.services.persistence.PersistenceServiceInterface;
import dhbw.ka.mwi.businesshorizon2.services.proxies.UserProxy;
import dhbw.ka.mwi.businesshorizon2.ui.initialscreen.InitialScreenViewInterface;
import dhbw.ka.mwi.businesshorizon2.ui.initialscreen.ShowInitialScreenViewEvent;
import dhbw.ka.mwi.businesshorizon2.ui.initialscreen.buttonsMiddle.ButtonsMiddleViewInterface;
import dhbw.ka.mwi.businesshorizon2.ui.initialscreen.description.DescriptionViewInterface;
import dhbw.ka.mwi.businesshorizon2.ui.initialscreen.infos.InfosViewInterface;
import dhbw.ka.mwi.businesshorizon2.ui.initialscreen.infos.ShowInfosEvent;
import dhbw.ka.mwi.businesshorizon2.ui.initialscreen.projectlist.ProjectListViewInterface;
import dhbw.ka.mwi.businesshorizon2.ui.initialscreen.projectlist.ShowProjectListEvent;
import dhbw.ka.mwi.businesshorizon2.ui.login.LogoutEvent;
import dhbw.ka.mwi.businesshorizon2.ui.login.ShowLogInScreenEvent;
import dhbw.ka.mwi.businesshorizon2.ui.login.ShowUserEvent;
import dhbw.ka.mwi.businesshorizon2.ui.parameterScreen.description.ShowParameterDescriptionViewEvent;
import dhbw.ka.mwi.businesshorizon2.ui.parameterScreen.input.ParameterInputViewInterface;
import dhbw.ka.mwi.businesshorizon2.ui.parameterScreen.input.ShowParameterInputViewEvent;

/**
* Dieser Presenter stellt die Eingangseite der Applikation darf. Er ist dafuer
* verantwortlich, die jeweils anzuzeigenden Fenster korrekt in der View zu
* setzen. Somit ist es notwenig, dass er fuer jedes Anzuzeigende (Teil-)Fenster
* einen entsprechenden EventHandler fuer den jeweiligen Show*Event registriert.
*
* @author Christian Scherer, Marcel Rosenberger
*
*/

public class ParameterScreenPresenter extends Presenter<ParameterScreenViewInterface> {
	private static final long serialVersionUID = 1L;
	
	private User user;
	
	@Autowired
	private UserProxy userProxy;
	
	private static final Logger logger = Logger.getLogger("ParameterScreenPresenter.class");
	
	@Autowired
	private EventBus eventBus;
	
	@Autowired
	private Project project;
	
	@Autowired
	private ButtonsMiddleViewInterface parameterButtonsMiddleView;
	
	@Autowired
	private ParameterInputViewInterface parameterInputView;
	
	@Autowired
	private DescriptionViewInterface parameterDescriptionView;
	
	@Autowired
	private AuthenticationServiceInterface authenticationService;
	
	@Autowired
	private PersistenceServiceInterface persistenceService;
	
	/**
	* Dies ist der Konstruktor, der von Spring nach der Initialierung der
	* Dependencies aufgerufen wird. Er registriert lediglich sich selbst als
	* einen EventHandler.
	*
	* @author Christian Scherer
	*/
	@PostConstruct
	public void init() {
		eventBus.addHandler(this);
		logger.debug("Eventhandler Hinzugefügt");
	
	}

	/**
	* Dieser Event wird zu Beginn von der BHApplication (nach dem Einloggen)
	* abgesetzt. Dabei wird in auf der linken Seite die Projekt-Liste und auf
	* der rechten Seite die Anwenderinformationen dargestellt. Der Projektlsite
	* wird dabei das angemeldete User-Objekt übergeben.
	*
	* @author Christian Scherer
	* @param event
	* das ShowInitialScreenViewEvent, welches das angemeldete
	* User-Objekt beinhaltet
	*/
	@EventHandler
	public void onShowInitialScreen(ShowInitialScreenViewEvent event) {
		logger.debug("ShowInitialScreenViewEvent empfangen");
		user = userProxy.getSelectedUser();
		//getView().showUserData(user.getFullName());
		getView().showView(parameterButtonsMiddleView, parameterInputView);
		logger.debug("Views mit parameterButtonsMiddleView und parameterInputView geladen");
	}
	
	@EventHandler
	public void onShowParameterDescription (ShowParameterDescriptionViewEvent event) {
		getView().showView(parameterButtonsMiddleView, parameterDescriptionView);
		logger.debug("parameterDescriptionView gesetzt");
	}
	
	@EventHandler
	public void onShowParameterInput (ShowParameterInputViewEvent event) {
		getView().showView(parameterButtonsMiddleView, parameterInputView);
		logger.debug("parameterInputView gesetzt");
	}
	
	public void abbrechen () {
		eventBus.fireEvent(new ShowUserEvent());
		logger.debug("abbrechen: ShowUserEvent gefeuert");
	}

	//wird durch den Click-Listener des Logout-Button in der InitinalScreen-View aufgerufen
	public void doLogout() {
	//speichert die Projekte in der externen Datei
		persistenceService.saveProjects();
		logger.debug("Projekte gespeichert");
		try {
			//ruft doLogout im Authentication Service auf und entfernt User aus allen eingeloggten Usern
			authenticationService.doLogout(userProxy.getSelectedUser());
			logger.debug("LogoutEvent gefeuert");
			eventBus.fireEvent(new LogoutEvent());	
		} catch (UserNotLoggedInException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	}


