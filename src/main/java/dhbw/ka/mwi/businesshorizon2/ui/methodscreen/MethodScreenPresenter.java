/*******************************************************************************
 * BusinessHorizon2
 *
 * Copyright (C) 
 * 2014-2015 Marco Glaser
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
package dhbw.ka.mwi.businesshorizon2.ui.methodscreen;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.mvplite.event.EventBus;
import com.mvplite.event.EventHandler;
import com.mvplite.presenter.Presenter;

import dhbw.ka.mwi.businesshorizon2.methods.discountedCashflow.APV;
import dhbw.ka.mwi.businesshorizon2.methods.discountedCashflow.FTE;
import dhbw.ka.mwi.businesshorizon2.methods.discountedCashflow.WACC;
import dhbw.ka.mwi.businesshorizon2.models.InputType;
import dhbw.ka.mwi.businesshorizon2.models.Project;
import dhbw.ka.mwi.businesshorizon2.models.PeriodContainer.CashFlowPeriodContainer;
import dhbw.ka.mwi.businesshorizon2.models.PeriodContainer.GesamtkostenVerfahrenCashflowPeriodContainer;
import dhbw.ka.mwi.businesshorizon2.models.PeriodContainer.UmsatzkostenVerfahrenCashflowPeriodContainer;
import dhbw.ka.mwi.businesshorizon2.services.proxies.ProjectProxy;
import dhbw.ka.mwi.businesshorizon2.ui.initialscreen.ShowProcessStepEvent;
import dhbw.ka.mwi.businesshorizon2.ui.initialscreen.ShowProcessStepEvent.screen;
import dhbw.ka.mwi.businesshorizon2.ui.initialscreen.projectlist.SelectProjectEvent;
import dhbw.ka.mwi.businesshorizon2.ui.parameterScreen.input.ValidationEvent;

public class MethodScreenPresenter extends Presenter<MethodScreenViewInterface>{

	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger("MethodScreenPresenter.class");

	@Autowired
	private EventBus eventBus;

	@Autowired 
	private ProjectProxy projectProxy;

	private Project project;

	/**
	 * Dies ist der Konstruktor, der von Spring nach der Initialierung der
	 * Dependencies aufgerufen wird. Er registriert lediglich sich selbst als
	 * einen EventHandler.
	 *
	 * @author Marco Glaser
	 */
	@PostConstruct
	public void init(){
		eventBus.addHandler(this);
		logger.debug("Eventhandler Hinzugefügt");
	}

	@EventHandler
	public void onProjectSelected(SelectProjectEvent event){
		project = projectProxy.getSelectedProject();
		getView().setProject(project);
	}
	
	@EventHandler
	public void onShowMethodScreen (ShowProcessStepEvent e) {
		if (e.getScreen().equals(screen.METHODSELECTION)) {
			eventBus.fireEvent(new ValidationEvent(true));
		}
	}

	public void setInputMethod(InputType value, boolean deterministic) {

		if(deterministic){
			project.getProjectInputType().setDeterministicInput(value);
				switch (value) {
				case DIRECT:
					project.setDeterministicPeriods(new CashFlowPeriodContainer());
					break;

				case GESAMTKOSTENVERFAHREN:

					project.setDeterministicPeriods(new GesamtkostenVerfahrenCashflowPeriodContainer());
					break;

				case UMSATZKOSTENVERFAHREN:

					project.setDeterministicPeriods(new UmsatzkostenVerfahrenCashflowPeriodContainer());
					break;

				default:
					break;
				}

		}
		else{
			project.getProjectInputType().setStochasticInput(value);
				switch (value) {
				case DIRECT:
					project.setStochasticPeriods(new CashFlowPeriodContainer());
					break;

				case GESAMTKOSTENVERFAHREN:

					project.setStochasticPeriods(new GesamtkostenVerfahrenCashflowPeriodContainer());
					break;

				case UMSATZKOSTENVERFAHREN:

					project.setStochasticPeriods(new UmsatzkostenVerfahrenCashflowPeriodContainer());
					break;

				default:
					break;
				}
		}

	}

	public void setPrognoseMethode(boolean deterministic){
		if(deterministic){
			project.getProjectInputType().setDeterministic(deterministic);;
			project.getProjectInputType().setStochastic(false);
		}
		else{
			project.getProjectInputType().setDeterministic(deterministic);
			project.getProjectInputType().setStochastic(true);
		}
	}

	public void setCalcMethod(String value) {
		switch (value) {
		case "fte":
			project.setCalculationMethod(new FTE());
			break;

		case "apv":
			project.setCalculationMethod(new APV());
			break;
			
		case "wac":
			project.setCalculationMethod(new WACC());
			break;
			
		default:
			break;
		}
	}

}
