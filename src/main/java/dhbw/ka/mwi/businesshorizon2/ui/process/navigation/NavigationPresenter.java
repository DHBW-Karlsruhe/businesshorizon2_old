
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

package dhbw.ka.mwi.businesshorizon2.ui.process.navigation;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.mvplite.event.EventBus;
import com.mvplite.event.EventHandler;
import com.mvplite.presenter.Presenter;

import dhbw.ka.mwi.businesshorizon2.models.User;
import dhbw.ka.mwi.businesshorizon2.services.proxies.ProjectProxy;
import dhbw.ka.mwi.businesshorizon2.ui.initialscreen.ShowInitialScreenViewEvent;
import dhbw.ka.mwi.businesshorizon2.ui.login.ShowUserEvent;
import dhbw.ka.mwi.businesshorizon2.ui.process.InvalidStateEvent;
import dhbw.ka.mwi.businesshorizon2.ui.process.ScreenSelectableEvent;
import dhbw.ka.mwi.businesshorizon2.ui.process.ShowNavigationStepEvent;
import dhbw.ka.mwi.businesshorizon2.ui.process.ValidStateEvent;

/**
 * Der Presenter zur Navigation der Prozessansicht.
 * 
 * @author Julius Hacker
 *
 */
public class NavigationPresenter extends Presenter<NavigationViewInterface> {
	private static final long serialVersionUID = 1L;

	@Autowired
	private EventBus eventBus;
	
	@Autowired
	private ProjectProxy projectProxy;
	
	/**
	 * Dies ist der Konstruktor, der von Spring nach der Initialierung der Dependencies 
	 * aufgerufen wird. Er registriert lediglich sich selbst als einen EventHandler.
	 * 
	 * @author Julius Hacker
	 */
	@PostConstruct
	public void init() {
		eventBus.addHandler(this);
	}
	
	@EventHandler
	public void showNavigation(ShowNavigationEvent event) {
		//getView().setButtonActive(NavigationSteps.METHOD, true);
		getView().showNavigation();
	}
	
	/**
	 * Diese Methode wird von der View nach der Auswahl einer neuen Prozessmaske aufgerufen.
	 * Sie feuert hierzu ein ShowNavigationStepEvent mit dem entsprechenden Prozessschritt.
	 * 
	 * @param step Anzuzeigende Prozessmaske
	 */
	public void showStep(NavigationSteps step) {
		eventBus.fireEvent(new ShowNavigationStepEvent(step));
	}
	
	@EventHandler
	public void handleInvalidState(InvalidStateEvent event) {
		if(event.isShowErrors()) {
			getView().setButtonToInvalid(event.getNavigationStep(), true);
		}
	}
	
	@EventHandler
	public void handleValidState(ValidStateEvent event) {
		getView().setButtonToInvalid(event.getNavigationStep(), false);
	}
	
	@EventHandler
	public void handleScreenSelectable(ScreenSelectableEvent event) {
		getView().setButtonActive(event.getNavigationStep(), event.isSelectable());
	}

	public void showProjectList() {
		eventBus.fireEvent(new ShowUserEvent());
	}

	public String getProjectName() {
		return this.projectProxy.getSelectedProject().getName();
	}
}
