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
package dhbw.ka.mwi.businesshorizon2.ui.process.output;


import java.util.Map.Entry;
import java.util.TreeSet;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.mvplite.event.EventBus;
import com.mvplite.event.EventHandler;

import dhbw.ka.mwi.businesshorizon2.methods.AbstractStochasticMethod;
import dhbw.ka.mwi.businesshorizon2.methods.CallbackInterface;
import dhbw.ka.mwi.businesshorizon2.methods.MethodRunner;
import dhbw.ka.mwi.businesshorizon2.methods.StochasticMethodException;
import dhbw.ka.mwi.businesshorizon2.methods.discountedCashflow.APV;
import dhbw.ka.mwi.businesshorizon2.methods.timeseries.TimeseriesCalculator;
import dhbw.ka.mwi.businesshorizon2.models.Project;
import dhbw.ka.mwi.businesshorizon2.models.StochasticResultContainer;
import dhbw.ka.mwi.businesshorizon2.models.Szenario;
import dhbw.ka.mwi.businesshorizon2.models.CompanyValue.CompanyValueDeterministic;
import dhbw.ka.mwi.businesshorizon2.models.CompanyValue.CompanyValueDeterministic.Couple;
import dhbw.ka.mwi.businesshorizon2.models.CompanyValue.CompanyValueStochastic;
import dhbw.ka.mwi.businesshorizon2.models.Period.CashFlowPeriod;
import dhbw.ka.mwi.businesshorizon2.models.PeriodContainer.AbstractPeriodContainer;
import dhbw.ka.mwi.businesshorizon2.models.PeriodContainer.CashFlowPeriodContainer;
import dhbw.ka.mwi.businesshorizon2.services.proxies.ProjectProxy;
import dhbw.ka.mwi.businesshorizon2.ui.process.ScreenPresenter;
import dhbw.ka.mwi.businesshorizon2.ui.process.ScreenSelectableEvent;
import dhbw.ka.mwi.businesshorizon2.ui.process.ShowErrorsOnScreenEvent;
import dhbw.ka.mwi.businesshorizon2.ui.process.ValidateContentStateEvent;
import dhbw.ka.mwi.businesshorizon2.ui.process.navigation.NavigationSteps;
import dhbw.ka.mwi.businesshorizon2.ui.process.output.charts.DeterministicChartArea;
import dhbw.ka.mwi.businesshorizon2.ui.process.output.charts.StochasticChartArea;

/**
 * Der Presenter fuer die Maske des Prozessschrittes zur Ergebnisausgabe.
 * 
 * @author Florian Stier
 * 
 */

public class OutputPresenter extends ScreenPresenter<OutputViewInterface> implements CallbackInterface {
	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger("OutputPresenter.class");

	@Autowired
	private EventBus eventBus;

	@Autowired
	private ProjectProxy projectProxy;

	private MethodRunner methodRunner;

	private Project project;

	private TreeSet<CashFlowPeriod> expectedCashFlows;

	/**
	 * Dies ist der Konstruktor, der von Spring nach der Initialierung der
	 * Dependencies aufgerufen wird. Er registriert lediglich sich selbst als
	 * einen EventHandler.
	 * 
	 * @author Florian Stier
	 */
	@PostConstruct
	public void init() {
		eventBus.addHandler(this);
	}

	@SuppressWarnings("unchecked")
	@EventHandler
	public void onShowOutputView(ShowOutputViewEvent event) {
		
		getView().showOutputView();
		
		project = projectProxy.getSelectedProject();

		if (project.getProjectInputType().getDeterministic()) {

			for (Szenario scenario : project.getScenarios()) {
				onProgressChange((float) 0.5);
				CashFlowPeriodContainer cfPeriodContainer = (CashFlowPeriodContainer) project.getDeterministicPeriods();

				TreeSet<AbstractPeriodContainer> periodContainer = new TreeSet<>();
				periodContainer.add(cfPeriodContainer);
				StochasticResultContainer srContainer = new StochasticResultContainer(periodContainer);

				APV apv = new APV(srContainer, scenario);
				CompanyValueDeterministic companyValueDeterministic = (CompanyValueDeterministic) apv
						.calculateCompanyValue();
				for (Entry<Integer, Couple> companyValue : companyValueDeterministic.getCompanyValues().entrySet()) {
					DeterministicChartArea deterministicChartArea = new DeterministicChartArea(companyValue.getValue()
							.getDebitFreeCompany(), companyValue.getValue().getTaxBenefits(), companyValue.getValue()
							.getCompanyValue(), companyValue.getValue().getCapitalStock());
					getView().addDeterministicChartArea(deterministicChartArea);
				}
				onProgressChange((float) 1);
			}
		}

		if (project.getProjectInputType().getStochastic()) {
			for (AbstractStochasticMethod method : project.getMethods()) {
				try {
					if (method.getSelected()) {

						// Bei Verwendung der Zeitreihenanalyse sollen
						// zusätzlich
						// die Erwartungswerte der Cashflows berechnet werden
						if (method.getName() == "Zeitreihenanalyse") {
							TimeseriesCalculator timeseriesCalculator = (TimeseriesCalculator) method;
							StochasticResultContainer src = timeseriesCalculator.calculateExpectedValues(project);

							expectedCashFlows = (TreeSet<CashFlowPeriod>) src.getPeriodContainers().first()
									.getPeriods();
						}

						methodRunner = new MethodRunner(method, project, this);
						methodRunner.start();
					}
				} catch (StochasticMethodException e) {
					getView().showErrorMessge(e.getMessage());
				}
			}

		}

	}

	@Override
	public boolean isValid() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void validate(ValidateContentStateEvent event) {
		// TODO Auto-generated method stub

	}

	@EventHandler
	public void handleShowView(ShowOutputViewEvent event) {
		eventBus.fireEvent(new ScreenSelectableEvent(NavigationSteps.OUTPUT, true));
		logger.debug("ShowOutputViewEvent handled");
	}

	@Override
	public void handleShowErrors(ShowErrorsOnScreenEvent event) {
		// TODO Auto-generated method stub

	}

	/**
	 * Wenn die Berechnung der stochastisch vorhergesagten Perioden erfolgreich
	 * durchlaufen wurde, dann kann der Unternehmenswert berechnet werden
	 * 
	 */
	@Override
	public void onComplete(StochasticResultContainer result, String methodName) {

		StochasticChartArea stochasticChartArea;

		for (Szenario scenario : project.getScenarios()) {
			APV apv = new APV(result, scenario);
			CompanyValueStochastic companyValue = (CompanyValueStochastic) apv.calculateCompanyValue();
			if (methodName.equalsIgnoreCase("zeitreihenanalyse")) {
				stochasticChartArea = new StochasticChartArea(methodName, expectedCashFlows,
						companyValue.getCompanyValues());
			} else {
				stochasticChartArea = new StochasticChartArea(methodName, null, companyValue.getCompanyValues());
			}
			getView().changeProgress(1);
			getView().addStochasticChartArea(stochasticChartArea);
		}

	}

	@Override
	public void onProgressChange(float progress) {
		getView().changeProgress(progress);

	}

	@Override
	public void onError(Throwable t) {
		// TODO Auto-generated method stub

	}

}

