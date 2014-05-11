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
package dhbw.ka.mwi.businesshorizon2.ui.process.period;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.mvplite.event.EventBus;
import com.mvplite.event.EventHandler;
import com.mvplite.view.View;

import dhbw.ka.mwi.businesshorizon2.models.Project;
import dhbw.ka.mwi.businesshorizon2.ui.process.ScreenPresenter;
import dhbw.ka.mwi.businesshorizon2.ui.process.ScreenSelectableEvent;
import dhbw.ka.mwi.businesshorizon2.ui.process.ShowErrorsOnScreenEvent;
import dhbw.ka.mwi.businesshorizon2.ui.process.ValidStateEvent;
import dhbw.ka.mwi.businesshorizon2.ui.process.ValidateContentStateEvent;
import dhbw.ka.mwi.businesshorizon2.ui.process.navigation.NavigationSteps;
import dhbw.ka.mwi.businesshorizon2.ui.process.period.input.ShowDirektViewEvent;
import dhbw.ka.mwi.businesshorizon2.ui.process.period.input.ShowGKVEvent;
import dhbw.ka.mwi.businesshorizon2.ui.process.period.input.ShowUKVEvent;
import dhbw.ka.mwi.businesshorizon2.ui.process.period.input.direkteeingabe.DirektPresenter;
import dhbw.ka.mwi.businesshorizon2.ui.process.period.input.direkteeingabe.DirektViewInterface;
import dhbw.ka.mwi.businesshorizon2.ui.process.period.input.gesamtkostenverfahren.GesamtkostenVerfahrenPresenter;
import dhbw.ka.mwi.businesshorizon2.ui.process.period.input.gesamtkostenverfahren.GesamtkostenVerfahrenViewInterface;
import dhbw.ka.mwi.businesshorizon2.ui.process.period.input.umsatzkostenverfahren.UmsatzkostenVerfahrenPresenter;
import dhbw.ka.mwi.businesshorizon2.ui.process.period.input.umsatzkostenverfahren.UmsatzkostenVerfahrenViewInterface;
import dhbw.ka.mwi.businesshorizon2.ui.process.period.timeline.TimelinePresenter;
import dhbw.ka.mwi.businesshorizon2.ui.process.period.timeline.TimelineViewInterface;

/**
 * 
 * Der Presenter fuer die Maske des Prozessschrittes zur Eingabe der Perioden.
 * 
 * @author Daniel Dengler, Marcel Rosenberger
 */

public class PeriodPresenter extends ScreenPresenter<PeriodViewInterface> {
	private static final long serialVersionUID = 1L;

	private View currentInput = null;

	private static final Logger logger = Logger
			.getLogger("PeriodPresenter.class");

	@Autowired
	private TimelineViewInterface timelineView;

	@Autowired
	private UmsatzkostenVerfahrenViewInterface indirectCalcView;

	@Autowired
	private DirektViewInterface direktView;

	@Autowired
	private GesamtkostenVerfahrenViewInterface directCalcView;

	@Autowired
	private TimelinePresenter timelinePresenter;

	@Autowired
	private UmsatzkostenVerfahrenPresenter indirectCalculationPresenter;

	@Autowired
	private DirektPresenter direktPresenter;

	@Autowired
	private GesamtkostenVerfahrenPresenter directCalculationPresenter;

	@Autowired
	private EventBus eventBus;

	/**
	 * Dies ist der Konstruktor, der von Spring nach der Initialierung der
	 * Dependencies aufgerufen wird. Er registriert lediglich sich selbst als
	 * einen EventHandler.
	 * 
	 * @author Daniel Dengler
	 */
	@PostConstruct
	public void init() {
		eventBus.addHandler(this);
	}

	@EventHandler
	public void onShowEvent(ShowPeriodViewEvent event) {
		logger.debug("DirektViewEvent gefeuert");
		getView().showView(timelineView, currentInput);
		eventBus.fireEvent(new ScreenSelectableEvent(NavigationSteps.PERIOD,
				true));
	}

	@EventHandler
	public void onShowEvent(ShowGKVEvent event) {
		logger.debug("ShowDirectCalcEvent erhalten");
		currentInput = directCalcView;
		getView().showView(timelineView, currentInput);

	}

	@EventHandler
	public void onShowEvent(ShowDirektViewEvent event) {
		logger.debug("ShowDirektViewEvent erhalten");
		currentInput = direktView;
		getView().showView(timelineView, currentInput);

	}

	@EventHandler
	public void onShowEvent(ShowUKVEvent event) {
		logger.debug("ShowUmsatzViewEvent erhalten");
		currentInput = indirectCalcView;
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

	@Override
	public void handleShowErrors(ShowErrorsOnScreenEvent event) {
		// TODO Auto-generated method stub

	}

}
