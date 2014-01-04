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

import java.util.Iterator;
import java.util.TreeSet;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.mvplite.event.EventBus;
import com.mvplite.event.EventHandler;
import com.vaadin.ui.Label;

import dhbw.ka.mwi.businesshorizon2.methods.AbstractDeterministicMethod;
import dhbw.ka.mwi.businesshorizon2.methods.AbstractStochasticMethod;
import dhbw.ka.mwi.businesshorizon2.methods.CallbackInterface;
import dhbw.ka.mwi.businesshorizon2.methods.MethodRunner;
import dhbw.ka.mwi.businesshorizon2.methods.discountedCashflow.APV_2;
import dhbw.ka.mwi.businesshorizon2.methods.discountedCashflow.DCF_2;
import dhbw.ka.mwi.businesshorizon2.methods.timeseries.TimeseriesCalculator;
import dhbw.ka.mwi.businesshorizon2.models.DeterministicResultContainer;
import dhbw.ka.mwi.businesshorizon2.models.Project;
import dhbw.ka.mwi.businesshorizon2.models.StochasticResultContainer;
import dhbw.ka.mwi.businesshorizon2.models.Szenario;
import dhbw.ka.mwi.businesshorizon2.models.CompanyValue.CompanyValueStochastic;
import dhbw.ka.mwi.businesshorizon2.models.Period.CashFlowPeriod;
import dhbw.ka.mwi.businesshorizon2.models.Period.Period;
import dhbw.ka.mwi.businesshorizon2.models.PeriodContainer.AbstractPeriodContainer;
import dhbw.ka.mwi.businesshorizon2.models.PeriodContainer.CashFlowPeriodContainer;
import dhbw.ka.mwi.businesshorizon2.services.proxies.ProjectProxy;
import dhbw.ka.mwi.businesshorizon2.ui.process.ScreenPresenter;
import dhbw.ka.mwi.businesshorizon2.ui.process.ScreenSelectableEvent;
import dhbw.ka.mwi.businesshorizon2.ui.process.ShowErrorsOnScreenEvent;
import dhbw.ka.mwi.businesshorizon2.ui.process.ValidateContentStateEvent;
import dhbw.ka.mwi.businesshorizon2.ui.process.navigation.NavigationSteps;
import dhbw.ka.mwi.businesshorizon2.ui.process.output.charts.DeterministicLineChart;
import dhbw.ka.mwi.businesshorizon2.ui.process.output.charts.StochasticChartArea;

/**
 * Der Presenter fuer die Maske des Prozessschrittes zur Ergebnisausgabe.
 * 
 * @author Florian Stier, Annika Weis, Marcel Rosenberger, Maurizio di Nunzio
 * 
 */

public class OutputPresenter extends ScreenPresenter<OutputViewInterface>
		implements CallbackInterface {
	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger
			.getLogger("OutputPresenter.class");

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
			// Annika Weis
			for (AbstractDeterministicMethod method_deterministic : project
					.getMethods_deterministic()) {
				// alle Szenarios durchlaufen
				for (Szenario scenario : project.getScenarios()) {
					onProgressChange((float) 0.5);
					CashFlowPeriodContainer cfPeriodContainer = (CashFlowPeriodContainer) project
							.getDeterministicPeriods();

					TreeSet<AbstractPeriodContainer> periodContainer = new TreeSet<>();
					periodContainer.add(cfPeriodContainer);
					DeterministicResultContainer drContainer = new DeterministicResultContainer(
							periodContainer);
					if (method_deterministic.getSelected()) {
						double uwert = 0;
						if (method_deterministic.getName() == "APV") {
							APV_2 apv_2 = new APV_2();
							uwert = apv_2
									.calculateValues(drContainer.getCashflows(),drContainer.getFremdkapitl() ,scenario);
							// TODO: Unternehmenswert ausgeben
							// Cashflows im Liniendiagramm ausgeben
							DeterministicLineChart chart = new DeterministicLineChart(
									method_deterministic.getName(), uwert,
									"Cashflows", drContainer.getJahre(),
									drContainer.getCashflows());
							getView().addDeterministicLineChartArea(chart); // chart

						}
						if (method_deterministic.getName() == "DCF") {
							DCF_2 dcf_2 = new DCF_2();
							uwert = dcf_2
									.calculateValues(drContainer.getCashflows(), scenario);

							DeterministicLineChart chart = new DeterministicLineChart(
									method_deterministic.getName(), uwert,
									"Cashflows", drContainer.getJahre(),
									drContainer.getCashflows());
							getView().addDeterministicLineChartArea(chart); // chart

						}
						Label labelUnternehmenswert = new Label(
								"Unternehmenswert: " + uwert);
						getView().addLabel(labelUnternehmenswert);

					}
				}
			}
			onProgressChange((float) 1);

			/*
			 * for (Szenario scenario : project.getScenarios()) {
			 * onProgressChange((float) 0.5); CashFlowPeriodContainer
			 * cfPeriodContainer = (CashFlowPeriodContainer) project
			 * .getDeterministicPeriods();
			 * 
			 * TreeSet<AbstractPeriodContainer> periodContainer = new
			 * TreeSet<>(); periodContainer.add(cfPeriodContainer);
			 * StochasticResultContainer srContainer = new
			 * StochasticResultContainer( periodContainer);
			 * 
			 * APV apv = new APV(srContainer, scenario); // Annika Weis APV_2
			 * apv_2 = new APV_2(); apv_2.calculateValues(new
			 * DeterministicResultContainer( periodContainer), scenario);//
			 * project, scenario DCF_2 dcf_2 = new DCF_2();
			 * dcf_2.calculateValues(new DeterministicResultContainer(
			 * periodContainer), scenario);// srContainer
			 * CompanyValueDeterministic companyValueDeterministic =
			 * (CompanyValueDeterministic) apv .calculateCompanyValue(); for
			 * (Entry<Integer, Couple> companyValue : companyValueDeterministic
			 * .getCompanyValues().entrySet()) { DeterministicChartArea
			 * deterministicChartArea = new DeterministicChartArea(
			 * companyValue.getValue().getDebitFreeCompany(),
			 * companyValue.getValue().getTaxBenefits(),
			 * companyValue.getValue().getCompanyValue(),
			 * companyValue.getValue().getCapitalStock());
			 * getView().addDeterministicChartArea(deterministicChartArea); }
			 * onProgressChange((float) 1); }
			 */
		}

		if (project.getProjectInputType().getStochastic()) {
			for (AbstractStochasticMethod method : project.getMethods()) {
				if (method.getSelected()) {

					

					methodRunner = new MethodRunner(method, project, this);
					methodRunner.start();
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
		eventBus.fireEvent(new ScreenSelectableEvent(NavigationSteps.OUTPUT,
				true));
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
	 * @author Marcel Rosenberger, Maurizio di Nunzio
	 * 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void onComplete(StochasticResultContainer result, AbstractStochasticMethod method) {

		StochasticChartArea stochasticChartArea;
		
		//pro Szenario werden die Unternehmenswerte berechnet
		for (Szenario scenario : project.getScenarios()) {
			
			CompanyValueStochastic companyValues = new CompanyValueStochastic();
			APV_2 apv = new APV_2();
			
			// Bei Verwendung der Zeitreihenanalyse sollen
			// zusätzlich
			// die Erwartungswerte der Cashflows berechnet werden
			
			
			//Temporäre Variablen werden erzeugt, die später für die Schleife benötigt werden
			CashFlowPeriod period;
			double[] cashflow = null;
			double[] fremdkapital = null;
			int i;
			double unternehmenswert;		
			
			//für jeden Cashflow-Period-Container, der im Stochastic-Result-Container enthalten ist,
			//wird die Schleife je einmal durchlaufen (=Anzahl der Iterationen in der Zeitreihenanalyse)
			for (AbstractPeriodContainer abstractPeriodContainer : result.getPeriodContainers()) {
				//holt pro Cashflow-Period-Container die enthaltenen Perioden und legt sie in einem TreeSet ab
				TreeSet<? extends Period> periods = abstractPeriodContainer.getPeriods();
				//ein Iterator zum durchlaufen des TreeSet wird erstellt.
				Iterator<? extends Period> periodenIterator = periods.iterator();
				//Zähler, Cashflow- und Fremdkapital-Arrays werden zurückgesetzt
				cashflow = new double[periods.size()];
				fremdkapital = new double[periods.size()];
				i = 0;
				//pro Periode sollen nun die Werte ausgelesen und ein Unternehmenswert berechnet werden
				while (periodenIterator.hasNext()) {
					period = (CashFlowPeriod) periodenIterator.next();
					cashflow[i] = period.getFreeCashFlow(); 					
					fremdkapital[i] = period.getCapitalStock();
					i++;				
				}
				//berechnet den Unternehmenswert des betrachteten Cashflow-Period-Container
				unternehmenswert = apv.calculateValues(cashflow, fremdkapital, scenario);
				//fügt den Unternehmenswert der Sammelklasse aller Unternehmenswert hinzu
				companyValues.addCompanyValue(unternehmenswert);
				logger.debug("Unternehmenswerte " + unternehmenswert + " hinzugefügt.");
			}
			logger.debug("Unternehmenswerte berechnet und in Sammelklasse einzugefügt.");
			
			if (method.getName() == "Zeitreihenanalyse") {
				TimeseriesCalculator timeseriesCalculator = (TimeseriesCalculator) method;
                StochasticResultContainer src = timeseriesCalculator
                                .getExpectedCashFlows();

                expectedCashFlows = (TreeSet<CashFlowPeriod>) src
                                .getPeriodContainers().first().getPeriods();
			}
			
			if (method.getName().equalsIgnoreCase("zeitreihenanalyse")) {
				stochasticChartArea = new StochasticChartArea(method.getName(),
						expectedCashFlows, companyValues.getCompanyValues());
			} else {
				stochasticChartArea = new StochasticChartArea(method.getName(), null,
						companyValues.getCompanyValues());
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
