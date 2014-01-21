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

import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.TreeSet;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.mvplite.event.EventBus;
import com.mvplite.event.EventHandler;
import com.vaadin.ui.Label;

import dhbw.ka.mwi.businesshorizon2.methods.AbstractCalculationMethod;
import dhbw.ka.mwi.businesshorizon2.methods.AbstractStochasticMethod;
import dhbw.ka.mwi.businesshorizon2.methods.CallbackInterface;
import dhbw.ka.mwi.businesshorizon2.methods.MethodRunner;
import dhbw.ka.mwi.businesshorizon2.methods.discountedCashflow.APV;
import dhbw.ka.mwi.businesshorizon2.methods.discountedCashflow.FTE;
import dhbw.ka.mwi.businesshorizon2.methods.timeseries.TimeseriesCalculator;
import dhbw.ka.mwi.businesshorizon2.models.DeterministicResultContainer;
import dhbw.ka.mwi.businesshorizon2.models.Project;
import dhbw.ka.mwi.businesshorizon2.models.StochasticResultContainer;
import dhbw.ka.mwi.businesshorizon2.models.Szenario;
import dhbw.ka.mwi.businesshorizon2.models.CompanyValue.CompanyValueStochastic;
import dhbw.ka.mwi.businesshorizon2.models.Period.CashFlowCalculator;
import dhbw.ka.mwi.businesshorizon2.models.Period.CashFlowPeriod;
import dhbw.ka.mwi.businesshorizon2.models.Period.IndirectCalculatedCashflowPeriod;
import dhbw.ka.mwi.businesshorizon2.models.Period.Period;
import dhbw.ka.mwi.businesshorizon2.models.PeriodContainer.AbstractPeriodContainer;
import dhbw.ka.mwi.businesshorizon2.models.PeriodContainer.CashFlowPeriodContainer;
import dhbw.ka.mwi.businesshorizon2.models.PeriodContainer.DirectCalculatedCashflowPeriodContainer;
import dhbw.ka.mwi.businesshorizon2.models.PeriodContainer.IndirectCalculatedCashflowPeriodContainer;
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

	private TreeSet<CashFlowPeriod> expectedValues;
	private double validierung;

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

		if (project.getProjectInputType().isDeterministic()) {
			TreeSet<AbstractPeriodContainer> periodContainer = new TreeSet<AbstractPeriodContainer>();
			AbstractPeriodContainer apc = project.getDeterministicPeriods();
			periodContainer.add(apc);
			DeterministicResultContainer drContainer= new DeterministicResultContainer(
					periodContainer);
			
			//Cashflows berechnen falls notwendig
			for (AbstractPeriodContainer container : drContainer.getPeriodContainers()){
				if (container instanceof CashFlowPeriodContainer){
					logger.debug("Cashflows wurden direkt eingegeben");
				} else if(container instanceof DirectCalculatedCashflowPeriodContainer){
					logger.debug("Cashflows werden direkt berechnet");
					CashFlowCalculator.calculateCashflows(drContainer);
				} else if(container instanceof IndirectCalculatedCashflowPeriodContainer){
					logger.debug("Cashflows werden indirekt berechnet");
					CashFlowCalculator.calculateCashflows(drContainer);
				}
			}

				// alle Szenarios durchlaufen
				for (Szenario scenario : project.getIncludedScenarios()) {
					onProgressChange((float) 0.5);

					
					if (project.getCalculationMethod().isSelected()) {
					
						double unternehmenswert = 0;

						double[] cashflow;
						double[] fremdkapital;
						int i;
						Period period;
						
						// für jedenPeriod-Container, der im
						// Deterministic-Result-Container enthalten ist,
						// wird die Schleife je einmal durchlaufen
						//dadurch werden zuerst die Cashflow und Fremdkapital Arrays befüllt
						for (AbstractPeriodContainer abstractPeriodContainer : drContainer
								.getPeriodContainers()) {
							// holt pro Cashflow-Period-Container die enthaltenen Perioden
							// und legt sie in einem TreeSet ab
							TreeSet<? extends Period> periods = abstractPeriodContainer
									.getPeriods();
							// ein Iterator zum durchlaufen des TreeSet wird erstellt.
							Iterator<? extends Period> periodenIterator = periods
									.iterator();
							// Zähler, Cashflow- und Fremdkapital-Arrays werden
							// zurückgesetzt
							cashflow = new double[periods.size()];
							fremdkapital = new double[periods.size()];
							i = 0;
							// pro Periode sollen nun die Werte ausgelesen und ein
							// Unternehmenswert berechnet werden
							while (periodenIterator.hasNext()) {
								period = periodenIterator.next();
								cashflow[i] = period.getFreeCashFlow();
								fremdkapital[i] = period.getCapitalStock();
								i++;
							}
							
						
						if (project.getCalculationMethod().getName() == "Adjusted-Present-Value (APV)") {
							double steuervorteile = 0;							
							double fremdkapitalout = 0;
							double uwsteuerfrei = 0;

							APV apv = new APV();
							// berechnet den Unternehmenswert des betrachteten
							// Period-Container
							unternehmenswert = apv.calculateValues(cashflow, fremdkapital,
									scenario);

							uwsteuerfrei = apv.getUwsteuerfrei();
							steuervorteile = apv.getSteuervorteile();
							fremdkapitalout = apv.getFremdkapital();

							DeterministicChartArea deterministicarea = new DeterministicChartArea(
									uwsteuerfrei, steuervorteile,

									unternehmenswert, fremdkapitalout,
									project.getCalculationMethod().getName(),
									drContainer, scenario);

							getView().addDeterministicChartArea(
									deterministicarea);
							
						}


				

						}
						if (project.getCalculationMethod().getName() == "Flow-to-Equity (FTE)") {
							FTE dcf_2 = new FTE();
							unternehmenswert = dcf_2.calculateValues(
									drContainer.getCashflows(), scenario);
							
							DeterministicChartArea deterministicarea = new DeterministicChartArea(
									unternehmenswert, project.getCalculationMethod().getName(),
									drContainer, scenario);

							getView().addDeterministicChartArea(
									deterministicarea);



						}
					}
				}
				onProgressChange((float) 1);

			}
		
	
		



		if (project.getProjectInputType().isStochastic()) {
			
				
			TreeSet<AbstractPeriodContainer> periodContainer = new TreeSet<AbstractPeriodContainer>();
			AbstractPeriodContainer apc = project.getStochasticPeriods();
			periodContainer.add(apc);
			StochasticResultContainer srContainer= new StochasticResultContainer(
					periodContainer);
			
			//Cashflows berechnen falls notwendig
			for (AbstractPeriodContainer container : srContainer.getPeriodContainers()){
				if (container instanceof CashFlowPeriodContainer){
					logger.debug("Cashflows wurden direkt eingegeben");
				} else if(container instanceof DirectCalculatedCashflowPeriodContainer){
					logger.debug("Cashflows werden direkt berechnet");
					CashFlowCalculator.calculateCashflows(srContainer);
				} else if(container instanceof IndirectCalculatedCashflowPeriodContainer){
					logger.debug("Cashflows werden indirekt berechnet");
					CashFlowCalculator.calculateCashflows(srContainer);
				}
			}
				if (project.getStochasticMethod().getSelected()) {
					methodRunner = new MethodRunner(project.getStochasticMethod(), project, this);

					methodRunner.start();
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
	public void onComplete(StochasticResultContainer result,
			AbstractStochasticMethod method) {

		StochasticChartArea stochasticChartArea;
		int szenariozähler = 1;
		// pro Szenario werden die Unternehmenswerte berechnet
		for (Szenario scenario : project.getIncludedScenarios()) {
			logger.debug("Szenariozähler: " + szenariozähler);
			szenariozähler++;

			CompanyValueStochastic companyValues = new CompanyValueStochastic();
			APV apv = new APV();

			// Bei Verwendung der Zeitreihenanalyse sollen
			// zusätzlich
			// die Erwartungswerte der Cashflows berechnet werden

			// Temporäre Variablen werden erzeugt, die später für die Schleife
			// benötigt werden
			Period period;
			double[] cashflow = null;
			double[] fremdkapital = null;
			int i;
			double unternehmenswert;

			// für jeden Cashflow-Period-Container, der im
			// Stochastic-Result-Container enthalten ist,
			// wird die Schleife je einmal durchlaufen (=Anzahl der Iterationen
			// in der Zeitreihenanalyse)
			for (AbstractPeriodContainer abstractPeriodContainer : result
					.getPeriodContainers()) {	
				// holt pro Cashflow-Period-Container die enthaltenen Perioden
				// und legt sie in einem TreeSet ab
				TreeSet<? extends Period> periods = abstractPeriodContainer
						.getPeriods();
				// ein Iterator zum durchlaufen des TreeSet wird erstellt.
				Iterator<? extends Period> periodenIterator = periods
						.iterator();
				// Zähler, Cashflow- und Fremdkapital-Arrays werden
				// zurückgesetzt
				cashflow = new double[periods.size()];
				fremdkapital = new double[periods.size()];
				i = 0;
				// pro Periode sollen nun die Werte ausgelesen und ein
				// Unternehmenswert berechnet werden
				while (periodenIterator.hasNext()) {
					period = periodenIterator.next();
					cashflow[i] = period.getFreeCashFlow();
					fremdkapital[i] = period.getCapitalStock();
					i++;
				}
				// berechnet den Unternehmenswert des betrachteten
				// Cashflow-Period-Container
				unternehmenswert = apv.calculateValues(cashflow, fremdkapital,
						scenario);
				// fügt den Unternehmenswert der Sammelklasse aller
				// Unternehmenswert hinzu
				companyValues.addCompanyValue(unternehmenswert);

			}
			logger.debug("Unternehmenswerte berechnet und in Sammelklasse einzugefügt.");

			// Erwartete Cashflows und Fremdkapitalwerte laden (sind nicht im
			// StochasticResultContainer)
			TimeseriesCalculator timeseriesCalculator = (TimeseriesCalculator) method;

			StochasticResultContainer src = timeseriesCalculator
					.getExpectedValues();

			expectedValues = (TreeSet<CashFlowPeriod>) src
					.getPeriodContainers().first().getPeriods();

			validierung = timeseriesCalculator.getModellabweichung();
			logger.debug("Modellabweichung: " + validierung);

			if (method.getName().equalsIgnoreCase("zeitreihenanalyse")) {
				stochasticChartArea = new StochasticChartArea(method.getName(),
						expectedValues, companyValues.getGradedCompanyValues(),
						validierung, scenario);
			} else {
				stochasticChartArea = new StochasticChartArea(method.getName(),
						null, companyValues.getGradedCompanyValues(),
						validierung, scenario);
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
