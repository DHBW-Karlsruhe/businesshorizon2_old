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
package dhbw.ka.mwi.businesshorizon2.ui.periodscreen;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.mvplite.event.EventBus;
import com.mvplite.event.EventHandler;
import com.mvplite.presenter.Presenter;
import com.mvplite.view.View;

import dhbw.ka.mwi.businesshorizon2.models.InputType;
import dhbw.ka.mwi.businesshorizon2.models.Project;
import dhbw.ka.mwi.businesshorizon2.services.proxies.ProjectProxy;
import dhbw.ka.mwi.businesshorizon2.ui.periodscreen.direkteeingabe.DirektPresenter;
import dhbw.ka.mwi.businesshorizon2.ui.periodscreen.direkteeingabe.DirektViewInterface;
import dhbw.ka.mwi.businesshorizon2.ui.periodscreen.gesamtkostenverfahren.GesamtkostenVerfahrenPresenter;
import dhbw.ka.mwi.businesshorizon2.ui.periodscreen.gesamtkostenverfahren.GesamtkostenVerfahrenViewInterface;
import dhbw.ka.mwi.businesshorizon2.ui.periodscreen.umsatzkostenverfahren.UmsatzkostenVerfahrenPresenter;
import dhbw.ka.mwi.businesshorizon2.ui.periodscreen.umsatzkostenverfahren.UmsatzkostenVerfahrenViewInterface;

/**
 * 
 * Der Presenter fuer die Maske des Prozessschrittes zur Eingabe der Perioden.
 * 
 * @author Daniel Dengler, Marcel Rosenberger
 */

public class PeriodScreenPresenter extends Presenter<PeriodScreenViewInterface> {
	private static final long serialVersionUID = 1L;

	private View currentInput = null;

	private static final Logger logger = Logger.getLogger("PeriodScreenPresenter.class");

	@Autowired
	private UmsatzkostenVerfahrenViewInterface ukvView;

	@Autowired
	private DirektViewInterface direktView;

	@Autowired
	private GesamtkostenVerfahrenViewInterface gkvView;

	@Autowired
	private UmsatzkostenVerfahrenPresenter umsatzkostenVerfahrenPresenter;

	@Autowired
	private DirektPresenter direktPresenter;

	@Autowired
	private GesamtkostenVerfahrenPresenter directCalculationPresenter;

	@Autowired
	private EventBus eventBus;

	@Autowired
	private ProjectProxy projectProxy;

	private Project project;

	private InputType inputType;

	private InputType type;

	private boolean stoch;

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
		getView().showView(currentInput);
//		eventBus.fireEvent(new ScreenSelectableEvent(NavigationSteps.PERIOD,
//				true));
	}

	@EventHandler
	public void onShowEvent(ShowGKVEvent event) {
		logger.debug("ShowGesamtViewEvent erhalten");
		currentInput = gkvView;
		getView().showView(gkvView);

	}

	@EventHandler
	public void onShowEvent(ShowDirektViewEvent event) {
		logger.debug("ShowDirektViewEvent erhalten");
		currentInput = direktView;
		getView().showView(direktView);

	}

	@EventHandler
	public void onShowEvent(ShowUKVEvent event) {
		logger.debug("ShowUmsatzViewEvent erhalten");
		currentInput = ukvView;
		getView().showView(ukvView);
	}

	//	@Override
	//	public boolean isValid() {
	//		return true;
	//	}
	//
	//	@Override
	//	@EventHandler
	//	public void validate(ValidateContentStateEvent event) {
	//		eventBus.fireEvent(new ValidStateEvent(NavigationSteps.PERIOD));
	//		logger.debug("Presenter valid, ValidStateEvent fired");
	//	}
	//
	//	@Override
	//	public void handleShowErrors(ShowErrorsOnScreenEvent event) {
	//		// TODO Auto-generated method stub
	//
	//	}

	public void setMethod() {
		project = projectProxy.getSelectedProject();
		if(project.getProjectInputType().isStochastic()){
			inputType = project.getProjectInputType().getStochasticInput();
			stoch = true;
		}else if(project.getProjectInputType().isDeterministic()){
			inputType = project.getProjectInputType().getDeterministicInput();
			stoch = false;
		}
		logger.debug("inputType: "+inputType.toString());
		type = inputType;
		switch (inputType) {
		case DIRECT:
			logger.debug("showView aufgerufen");
			getView().showView(direktView);
			direktView.setProject(project);
			eventBus.fireEvent(new ShowDirektViewEvent());
			break;
		case GESAMTKOSTENVERFAHREN:
			getView().showView(gkvView);
			gkvView.setProject(project);
			eventBus.fireEvent(new ShowGKVEvent());
			break;
		case UMSATZKOSTENVERFAHREN:
			getView().showView(ukvView);
			ukvView.setProject(project);
			eventBus.fireEvent(new ShowUKVEvent());
			break;

		default:
			break;
		}

	}

	public String getPageDescription() {
		String text = "";
		switch (type) {
		case DIRECT:
			if(stoch == true){
				text = "Stochastische Methode - FCF";
			}
			else{
				text = "Deterministische Methode - FCF";
			}
			break;
		case GESAMTKOSTENVERFAHREN:
			if(stoch == true){
				text = "Stochastische Methode - GKV";
			}
			else{
				text = "Deterministische Methode - GKV";
			}
			break;
		case UMSATZKOSTENVERFAHREN:
			if(stoch == true){
				text = "Stochastische Methode - UKV";
			}
			else{
				text = "Deterministische Methode - UKV";
			}
			break;

		default:
			break;
		}
		return text;
	}

}
