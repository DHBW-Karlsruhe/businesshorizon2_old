
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

package dhbw.ka.mwi.businesshorizon2.ui.initialscreen;

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
import dhbw.ka.mwi.businesshorizon2.services.proxies.UserProxy;
import dhbw.ka.mwi.businesshorizon2.ui.initialscreen.infos.InfosViewInterface;
import dhbw.ka.mwi.businesshorizon2.ui.initialscreen.infos.ShowInfosEvent;
import dhbw.ka.mwi.businesshorizon2.ui.initialscreen.projectlist.ProjectListViewInterface;
import dhbw.ka.mwi.businesshorizon2.ui.initialscreen.projectlist.ShowProjectListEvent;
import dhbw.ka.mwi.businesshorizon2.ui.login.LogoutEvent;
import dhbw.ka.mwi.businesshorizon2.ui.login.ShowLogInScreenEvent;

/**
 * Dieser Presenter stellt die Eingangseite der Applikation darf. Er ist dafuer
 * verantwortlich, die jeweils anzuzeigenden Fenster korrekt in der View zu
 * setzen. Somit ist es notwenig, dass er fuer jedes Anzuzeigende (Teil-)Fenster
 * einen entsprechenden EventHandler fuer den jeweiligen Show*Event registriert.
 * 
 * @author Christian Scherer, Marcel Rosenberger
 * 
 */

public class InitialScreenPresenter extends Presenter<InitialScreenViewInterface> {
	private static final long serialVersionUID = 1L;

	private User user;
	
	@Autowired
	private UserProxy userProxy;

	private Logger logger = Logger.getLogger("InitialScreenPresenter.class");

	@Autowired
	private EventBus eventBus;

	@Autowired
	private Project project;

	@Autowired
	private ProjectListViewInterface projectListView;

	@Autowired
	private InfosViewInterface infosView;
	
	@Autowired
	private AuthenticationServiceInterface authenticationService;

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
	 *            das ShowInitialScreenViewEvent, welches das angemeldete
	 *            User-Objekt beinhaltet
	 */
	@EventHandler
	public void onShowInitialScreen(ShowInitialScreenViewEvent event) {
		logger.debug("ShowInitialScreenViewEvent empfangen");
		user = userProxy.getSelectedUser();
		getView().showUserData(user.getCompany());
		getView().showView(projectListView, infosView);
		logger.debug("Views mit Projekt und Infoview geladen");
		eventBus.fireEvent(new ShowProjectListEvent(user));
		logger.debug("ShowProjectListEvent gefeuert");
		eventBus.fireEvent(new ShowInfosEvent());
		logger.debug("ShowInfosEvent gefeuert");

	}

	//wird durch den Click-Listener des Logout-Button in der InitinalScreen-View aufgerufen
	public void doLogout() {
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


