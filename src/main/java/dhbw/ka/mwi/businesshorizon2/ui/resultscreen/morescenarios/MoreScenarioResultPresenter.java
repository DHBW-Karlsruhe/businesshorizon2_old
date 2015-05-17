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
package dhbw.ka.mwi.businesshorizon2.ui.resultscreen.morescenarios;

import java.text.NumberFormat;
import java.util.Iterator;
import java.util.Locale;
import java.util.TreeSet;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.vaadinvisualizations.ColumnChart;

import com.mvplite.event.EventBus;
import com.mvplite.event.EventHandler;
import com.mvplite.presenter.Presenter;

import dhbw.ka.mwi.businesshorizon2.methods.AbstractDeterministicMethod;
import dhbw.ka.mwi.businesshorizon2.methods.AbstractStochasticMethod;
import dhbw.ka.mwi.businesshorizon2.methods.CallbackInterface;
import dhbw.ka.mwi.businesshorizon2.methods.StochasticMethodException;
import dhbw.ka.mwi.businesshorizon2.methods.discountedCashflow.APV;
import dhbw.ka.mwi.businesshorizon2.methods.discountedCashflow.FTE;
import dhbw.ka.mwi.businesshorizon2.methods.discountedCashflow.WACC;
import dhbw.ka.mwi.businesshorizon2.methods.timeseries.ConsideredPeriodsOfPastException;
import dhbw.ka.mwi.businesshorizon2.methods.timeseries.TimeseriesCalculator;
import dhbw.ka.mwi.businesshorizon2.methods.timeseries.VarianceNegativeException;
import dhbw.ka.mwi.businesshorizon2.models.Project;
import dhbw.ka.mwi.businesshorizon2.models.StochasticResultContainer;
import dhbw.ka.mwi.businesshorizon2.models.Szenario;
import dhbw.ka.mwi.businesshorizon2.models.Period.CashFlowCalculator;
import dhbw.ka.mwi.businesshorizon2.models.Period.Period;
import dhbw.ka.mwi.businesshorizon2.models.PeriodContainer.AbstractPeriodContainer;
import dhbw.ka.mwi.businesshorizon2.models.PeriodContainer.CashFlowPeriodContainer;
import dhbw.ka.mwi.businesshorizon2.models.PeriodContainer.GesamtkostenVerfahrenCashflowPeriodContainer;
import dhbw.ka.mwi.businesshorizon2.models.PeriodContainer.UmsatzkostenVerfahrenCashflowPeriodContainer;
import dhbw.ka.mwi.businesshorizon2.services.proxies.ProjectProxy;
import dhbw.ka.mwi.businesshorizon2.ui.resultscreen.MoreScenarioCalculationEvent;

/**
 * Der Presenter für die View des Prozessschrittes zur Ergebnisausgabe mehrerer Szenarien.
 * 
 * @author Tobias Lindner
 * 
 */

public class MoreScenarioResultPresenter extends Presenter<MoreScenarioResultViewInterface> 
		implements CallbackInterface {
	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger("MoreScenarioResultPresenter.class");

	@Autowired
	private EventBus eventBus;

	@Autowired
	private ProjectProxy projectProxy;

	/**
	 * Der Konstruktor registriert sich selbst als EventHandler.
	 * 
	 * @author Tobias Lindner
	 */
	@PostConstruct
	public void init() {
		eventBus.addHandler(this);
	}
	
	/**
	 * Diese Methode triggert die Berechnung der unterschiedlichen Szenarien.
	 * 
	 * @author Tobias Lindner
	 * 
	 */
	@EventHandler
	public void onMoreScenarioCalculation (MoreScenarioCalculationEvent event) {
		
		try {
			calculateScenario (0, event.getProject());
			calculateScenario (1, event.getProject());
		} catch (StochasticMethodException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		if (event.anzScenarios()==3) {
			getView().addScenario3ToLayout();
			try {
				calculateScenario (2, event.getProject());
			} catch (StochasticMethodException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else {
			getView().removeScenario3FromLayout();
		}
	}
	
	/**
	 * Diese Methode für die Berechnung für ein Szenario durch.
	 * 
	 * @author Marco Glaser, Tobias Lindner
	 * 	 
	*/
	public void calculateScenario (int numScenario, Project project) throws ConsideredPeriodsOfPastException, VarianceNegativeException, StochasticMethodException, InterruptedException {
//		project = event.getProject();
		double[] cashflow;
		double[] fremdkapital;
		double dFremdkapital = 0;
		double unternehmenswert = 0;
		Szenario scenario = project.getIncludedScenarios().get(numScenario);
		AbstractPeriodContainer periodContainer = null;
		
		if (project.getProjectInputType().isStochastic()){
			logger.debug("Stochastische Berechnung");
			//periodContainer = project.getStochasticPeriods();
			TimeseriesCalculator tsCalc = new TimeseriesCalculator();
			StochasticResultContainer stochasticResults = tsCalc.calculate(project, this);
			periodContainer = stochasticResults.getPeriodContainers().first();
			logger.debug("StochasticPeriods:" + project.getStochasticPeriods().toString());
			//Stochastische Berechnung ausführen
			//Werte für Berechnung weitergeben
		}
		else {
			periodContainer = project.getDeterministicPeriods();
		}
		
		if(periodContainer instanceof CashFlowPeriodContainer){
	
		}else if(periodContainer instanceof UmsatzkostenVerfahrenCashflowPeriodContainer){
			CashFlowCalculator.calculateUKVCashflows((UmsatzkostenVerfahrenCashflowPeriodContainer)periodContainer, scenario);
		}else if(periodContainer instanceof GesamtkostenVerfahrenCashflowPeriodContainer){
			CashFlowCalculator.calculateGKVCashflows((GesamtkostenVerfahrenCashflowPeriodContainer)periodContainer, scenario);
		}
	
		TreeSet<? extends Period> periods = periodContainer.getPeriods();
		Iterator<? extends Period> it = periods.iterator();
		Period period;
		int counter = 0;
		cashflow = new double[periods.size()];
		fremdkapital = new double[periods.size()];
	
		while(it.hasNext()){
			period = it.next();
			cashflow[counter] = period.getFreeCashFlow();
			fremdkapital[counter] = period.getCapitalStock();
			counter++;
		}
	
		AbstractDeterministicMethod method = project.getCalculationMethod();
		
		if(method.getName().equals("Flow-to-Equity (FTE)")){
			FTE fte = new FTE();
			unternehmenswert = fte.calculateValues(cashflow, fremdkapital, scenario);
			dFremdkapital = fremdkapital[fremdkapital.length - 1];
			logger.debug("Unternehmenswert mit FTE berechnet: "+unternehmenswert);
		}else if(method.getName().equals("Adjusted-Present-Value (APV)")){
			APV apv = new APV();
			unternehmenswert = apv.calculateValues(cashflow, fremdkapital, scenario);
			dFremdkapital = apv.getFremdkapital();
			logger.debug("Unternehmenswert mit APV berechnet: "+unternehmenswert);
		}else if(method.getName().equals("Weighted-Average-Cost-of-Capital (WACC)")){	
			WACC wacc = new WACC();
			unternehmenswert = wacc.calculateValues(cashflow, fremdkapital, scenario);
			dFremdkapital = wacc.getFremdkapital();
			logger.debug("Unternehmenswert mit WACC berechnet: " + unternehmenswert);
	
		}
		NumberFormat nfUS = NumberFormat.getInstance(Locale.US);
		nfUS.setMinimumFractionDigits(2);
		nfUS.setMaximumFractionDigits(2);

		NumberFormat nfDE = NumberFormat.getInstance(Locale.GERMANY);
		nfDE.setMaximumFractionDigits(2);
		nfDE.setMinimumFractionDigits(1);
		
		getView().setScenarioValue(numScenario, nfDE.format(scenario.getRateReturnEquity()), nfDE.format(scenario.getRateReturnCapitalStock()), nfDE.format(scenario.getBusinessTax()), nfDE.format(scenario.getCorporateAndSolitaryTax()), nfDE.format(unternehmenswert));
		
		ColumnChart cc = new ColumnChart();	
		cc.setOption("is3D", true);	
		cc.setOption("isStacked", true);	
		cc.setOption("legend", "bottom");
		cc.setOption("width", 200);
		cc.setOption("height", 240);
		cc.setColors(new String[]{"#92D050", "#FFFF00"});
		cc.addXAxisLabel("Year");	
		cc.addColumn("Eigenkapital");	
		cc.addColumn("Fremdkapital");	
		cc.add(String.valueOf(periods.last().getYear()), new double[]{Double.parseDouble(nfUS.format(unternehmenswert).replace(",", "")), dFremdkapital});		
		
		getView().addCapitalChartScenario(numScenario, cc);
	}

	@Override
	public void onComplete(StochasticResultContainer result,
			AbstractStochasticMethod method) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProgressChange(float progress) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onError(Throwable t) {
		// TODO Auto-generated method stub
		
	}
	
}
