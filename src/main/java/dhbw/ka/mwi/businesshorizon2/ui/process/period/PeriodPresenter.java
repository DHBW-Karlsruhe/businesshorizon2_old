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

package dhbw.ka.mwi.businesshorizon2.ui.process.period;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.mvplite.event.EventBus;
import com.mvplite.event.EventHandler;
import com.mvplite.view.View;
import com.vaadin.ui.Component;

import dhbw.ka.mwi.businesshorizon2.models.Project;
import dhbw.ka.mwi.businesshorizon2.ui.process.ScreenPresenter;
import dhbw.ka.mwi.businesshorizon2.ui.process.ScreenSelectableEvent;
import dhbw.ka.mwi.businesshorizon2.ui.process.ShowErrorsOnScreenEvent;
import dhbw.ka.mwi.businesshorizon2.ui.process.ValidStateEvent;
import dhbw.ka.mwi.businesshorizon2.ui.process.ValidateContentStateEvent;
import dhbw.ka.mwi.businesshorizon2.ui.process.navigation.NavigationSteps;
import dhbw.ka.mwi.businesshorizon2.ui.process.period.input.ShowDirektViewEvent;
import dhbw.ka.mwi.businesshorizon2.ui.process.period.input.ShowGesamtViewEvent;
import dhbw.ka.mwi.businesshorizon2.ui.process.period.input.ShowUmsatzViewEvent;
import dhbw.ka.mwi.businesshorizon2.ui.process.period.input.direkt.DirektPresenter;
import dhbw.ka.mwi.businesshorizon2.ui.process.period.input.direkt.DirektViewInterface;
import dhbw.ka.mwi.businesshorizon2.ui.process.period.input.gesamt.GesamtPresenter;
import dhbw.ka.mwi.businesshorizon2.ui.process.period.input.gesamt.GesamtViewInterface;
import dhbw.ka.mwi.businesshorizon2.ui.process.period.input.umsatz.UmsatzPresenter;
import dhbw.ka.mwi.businesshorizon2.ui.process.period.input.umsatz.UmsatzViewInterface;
import dhbw.ka.mwi.businesshorizon2.ui.process.period.timeline.TimelinePresenter;
import dhbw.ka.mwi.businesshorizon2.ui.process.period.timeline.TimelineViewInterface;

/**
 * Der Presenter fuer die Maske des Prozessschrittes zur Eingabe der Perioden.
 * 
 * @author Daniel Dengler
 */

public class PeriodPresenter extends ScreenPresenter<PeriodViewInterface> {
	private static final long serialVersionUID = 1L;

	private View currentInput = null;

	private Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private TimelineViewInterface timelineView;

	@Autowired
	private UmsatzViewInterface umsatzView;

	@Autowired
	private DirektViewInterface direktView;

	@Autowired
	private GesamtViewInterface gesamtView;

	@Autowired
	private TimelinePresenter timelinePresenter;

	@Autowired
	private UmsatzPresenter umsatzPresenter;

	@Autowired
	private DirektPresenter direktPresenter;

	@Autowired
	private GesamtPresenter gesamtPresenter;

	@Autowired
	private EventBus eventBus;

	@Autowired
	private Project project;

	/**
	 * Dies ist der Konstruktor, der von Spring nach der Initialierung der
	 * Dependencies aufgerufen wird. Er registriert lediglich sich selbst als
	 * einen EventHandler.
	 * 
	 * @author Julius Hacker
	 */
	@PostConstruct
	public void init() {
		eventBus.addHandler(this);
	}

	@EventHandler
	public void onShowEvent(ShowPeriodViewEvent event) {
		logger.debug("DirektVieEvent gefeuert");
		getView().showView(timelineView, currentInput);
	}

	@EventHandler
	public void onShowEvent(ShowGesamtViewEvent event) {
		currentInput = gesamtView;
		getView().showView(timelineView, currentInput);

	}

	@EventHandler
	public void onShowEvent(ShowDirektViewEvent event) {
		logger.debug("ShowDirektViewEvent erhalten");
		currentInput = direktView;
		getView().showView(timelineView, currentInput);

		
	}

	@EventHandler
	public void onShowEvent(ShowUmsatzViewEvent event) {
		currentInput = umsatzView;
		getView().showView(timelineView, currentInput);
	}

	@Override
	public boolean isValid() {
		return true;
	}

	@Override
	@EventHandler
	public void validate(ValidateContentStateEvent event) {
		eventBus.fireEvent(new ValidStateEvent(NavigationSteps.PERIOD));
		logger.debug("Presenter valid, ValidStateEvent fired");
	}

	@EventHandler
	public void handleShowView(ShowPeriodViewEvent event) {
		eventBus.fireEvent(new ScreenSelectableEvent(NavigationSteps.PERIOD,
				true));
		logger.debug("ShowPeriodViewEvent handled");
	}

	@Override
	public void handleShowErrors(ShowErrorsOnScreenEvent event) {
		// TODO Auto-generated method stub

	}

}
