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

package dhbw.ka.mwi.businesshorizon2.ui.periodscreen.direkteeingabe;

import java.util.Iterator;
import java.util.TreeSet;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.mvplite.event.EventBus;
import com.mvplite.event.EventHandler;
import com.mvplite.presenter.Presenter;

import dhbw.ka.mwi.businesshorizon2.models.Project;
import dhbw.ka.mwi.businesshorizon2.models.Period.CashFlowPeriod;
import dhbw.ka.mwi.businesshorizon2.models.PeriodContainer.CashFlowPeriodContainer;
import dhbw.ka.mwi.businesshorizon2.services.proxies.ProjectProxy;
import dhbw.ka.mwi.businesshorizon2.ui.initialscreen.buttonsMiddle.ValidationEvent;
import dhbw.ka.mwi.businesshorizon2.ui.periodscreen.ShowDirektViewEvent;

/**
 * Der Presenter fuer die Maske des Prozessschrittes zur Eingabe der Perioden.
 *
 * @author Daniel Dengler, Tobias Lindner, Marco Glaser
 *
 */

public class DirektPresenter extends Presenter<DirektViewInterface> {
	private static final long serialVersionUID = 1L;
	
	private static final Logger logger = Logger.getLogger("DirektPresenter.class");

	@Autowired
	private EventBus eventBus;

	@Autowired
	private ProjectProxy projectProxy;


	private CashFlowPeriodContainer periodContainer;


	private Project project;


	private Boolean stochastic;


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
		logger.debug("Initialisierung beendet");
	}


	/**
	 * Faengt das ShowEvent ab und sorgt dafuer das die View die benoetigten
	 * Eingabefelder erstellt und mit den bisherigen Daten befuellt.
	 *
	 *@author Marco Glaser
	 */
	@EventHandler
	public void onShowEvent(ShowDirektViewEvent event) {
		getView().generateTable();
	}


	public void setCashFlowValue(double value, int year) {
		if(project == null){
			project = projectProxy.getSelectedProject();
			stochastic = project.getProjectInputType().isStochastic();
		}

		if(periodContainer == null){
			periodContainer = new CashFlowPeriodContainer();
		}
		CashFlowPeriod period = null;
		boolean isNew = true;
		TreeSet<CashFlowPeriod> periods = periodContainer.getPeriods();
		Iterator<CashFlowPeriod> it = periods.iterator();
		while(it.hasNext()){
			period = it.next();
			if(period.getYear() == year){
				period.setFreeCashFlow(value);
				periodContainer.removePeriod(period);
				isNew = false;
				break;
			}
		}
		if(isNew){
			period = new CashFlowPeriod(year);
			period.setFreeCashFlow(value);
		}
		periodContainer.addPeriod(period);
		if(stochastic){
			project.setStochasticPeriods(periodContainer);
		}
		else{
			project.setDeterministicPeriods(periodContainer);
		}
	}

	public void setCapitalStockValue(double value, int year){
		project = projectProxy.getSelectedProject();
		stochastic = project.getProjectInputType().isStochastic();
		if(stochastic){
			periodContainer = (CashFlowPeriodContainer) project.getStochasticPeriods();
		}
		else{
			periodContainer = (CashFlowPeriodContainer) project.getDeterministicPeriods();
		}
		CashFlowPeriod period = null;
		boolean isNew = true;
		TreeSet<CashFlowPeriod> periods = periodContainer.getPeriods();
		Iterator<CashFlowPeriod> it = periods.iterator();
		while(it.hasNext()){
			period = it.next();
			if(period.getYear() == year){
				period.setCapitalStock(value);
				periodContainer.removePeriod(period);
				isNew = false;
				break;
			}
		}
		if(isNew){
			period = new CashFlowPeriod(year);
			period.setCapitalStock(value);
		}
		periodContainer.addPeriod(period);
		if(stochastic){
			project.setStochasticPeriods(periodContainer);
		}
		else{
			project.setDeterministicPeriods(periodContainer);
		}
	}


	public String getCashFlow(int year) {
		double value = 0.0;
		project = projectProxy.getSelectedProject();
		stochastic = project.getProjectInputType().isStochastic();
		if(stochastic){
			periodContainer = (CashFlowPeriodContainer) project.getStochasticPeriods();
		}
		else{
			periodContainer = (CashFlowPeriodContainer) project.getDeterministicPeriods();
		}

		if(periodContainer != null){
			CashFlowPeriod period;
			TreeSet<CashFlowPeriod> periods = periodContainer.getPeriods();
			Iterator<CashFlowPeriod> it = periods.iterator();
			while(it.hasNext()){
				period = it.next();
				if(period.getYear() == year){
					value = period.getFreeCashFlow();
					break;
				}
			}
		}
		return String.valueOf(value);
	}


	public String getCapitalStock(int year) {
		double value = 0.0;
		project = projectProxy.getSelectedProject();
		stochastic = project.getProjectInputType().isStochastic();
		if(stochastic){
			periodContainer = (CashFlowPeriodContainer) project.getStochasticPeriods();
		}
		else{
			periodContainer = (CashFlowPeriodContainer) project.getDeterministicPeriods();
		}

		if(periodContainer != null){
			CashFlowPeriod period;
			TreeSet<CashFlowPeriod> periods = periodContainer.getPeriods();
			Iterator<CashFlowPeriod> it = periods.iterator();
			while(it.hasNext()){
				period = it.next();
				if(period.getYear() == year){
					value = period.getCapitalStock();
					break;
				}
			}
		}
		return String.valueOf(value);
	}
	
	/**
	 * Die Methode überrüft, ob die Eingaben in allen Textfeldern korrekt sind.
	 * @author Tobias Lindner
	 * 
	 * @return boolean
	 * 			Sind die Eingaben valide?
	 */
	public boolean validateDirectInput () {
		if (getView().isComponentError()) {
			eventBus.fireEvent(new ValidationEvent(false));
			logger.debug("ValidationEvent(false) geworfen"); 
			return false;
		}
		else {
			if (getView().oneTextFieldIsSet()) {
				eventBus.fireEvent(new ValidationEvent(true));
				logger.debug("ValidationEvent(true) geworfen"); 
				return true;
			}
			else {
				eventBus.fireEvent(new ValidationEvent(false));
				logger.debug("ValidationEvent(false) geworfen"); 
				return false;
			}
		}
	}
}





