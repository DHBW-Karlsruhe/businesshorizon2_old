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

package dhbw.ka.mwi.businesshorizon2.ui.process;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.mvplite.event.EventBus;
import com.mvplite.event.EventHandler;
import com.mvplite.presenter.Presenter;

import dhbw.ka.mwi.businesshorizon2.models.Project;
import dhbw.ka.mwi.businesshorizon2.ui.process.contentcontainer.ContentContainerViewInterface;
import dhbw.ka.mwi.businesshorizon2.ui.process.navigation.NavigationSteps;
import dhbw.ka.mwi.businesshorizon2.ui.process.navigation.NavigationViewInterface;
import dhbw.ka.mwi.businesshorizon2.ui.process.navigation.ShowNavigationEvent;

/**
 * Dieser Presenter ist das Kernstueck der Prozesssicht. Er ist dafuer verantwortlich,
 * die Navigation sowie die gerade ausgewaehlte Maske anzuzeigen. 
 * @author Julius Hacker
 *
 */
public class ProcessPresenter extends Presenter<ProcessViewInterface>{
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private EventBus eventBus;
	
	@Autowired
	private Project project;
	
	@Autowired
	private NavigationViewInterface navigationView;
	
	@Autowired
	private ContentContainerViewInterface contentContainerView;
	
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

	/**
	 * Dieser Event wird zu Beginn der Prozesssicht abgesetzt.
	 * Dabei wird oben die Navigation und unten die Methodenauswahl angezeigt.
	 * 
	 * @author Julius Hacker
	 * @param event
	 */
	@EventHandler
	public void onShowProcess(ShowProcessViewEvent event) {
		getView().showView(navigationView, contentContainerView);
		eventBus.fireEvent(new ShowNavigationEvent());
		eventBus.fireEvent(new ShowNavigationStepEvent(NavigationSteps.METHOD));
	}
	
}
