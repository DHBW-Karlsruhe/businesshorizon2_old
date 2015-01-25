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
package dhbw.ka.mwi.businesshorizon2.ui.resultscreen.onescenario;

import java.text.DecimalFormat;
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
import com.vaadin.ui.Label;

import dhbw.ka.mwi.businesshorizon2.methods.AbstractDeterministicMethod;
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
import dhbw.ka.mwi.businesshorizon2.models.Period.UmsatzkostenVerfahrenCashflowPeriod;
import dhbw.ka.mwi.businesshorizon2.models.Period.Period;
import dhbw.ka.mwi.businesshorizon2.models.PeriodContainer.AbstractPeriodContainer;
import dhbw.ka.mwi.businesshorizon2.models.PeriodContainer.CashFlowPeriodContainer;
import dhbw.ka.mwi.businesshorizon2.models.PeriodContainer.GesamtkostenVerfahrenCashflowPeriodContainer;
import dhbw.ka.mwi.businesshorizon2.models.PeriodContainer.UmsatzkostenVerfahrenCashflowPeriodContainer;
import dhbw.ka.mwi.businesshorizon2.services.proxies.ProjectProxy;
import dhbw.ka.mwi.businesshorizon2.ui.process.ScreenPresenter;
import dhbw.ka.mwi.businesshorizon2.ui.process.ScreenSelectableEvent;
import dhbw.ka.mwi.businesshorizon2.ui.process.ShowErrorsOnScreenEvent;
import dhbw.ka.mwi.businesshorizon2.ui.process.ValidateContentStateEvent;
import dhbw.ka.mwi.businesshorizon2.ui.process.navigation.NavigationSteps;
import dhbw.ka.mwi.businesshorizon2.ui.process.output.charts.DeterministicChartArea;
import dhbw.ka.mwi.businesshorizon2.ui.process.output.charts.StochasticChartArea;
import dhbw.ka.mwi.businesshorizon2.ui.resultscreen.OneScenarioCalculationEvent;

/**
 * Der Presenter fuer die Maske des Prozessschrittes zur Ergebnisausgabe.
 * 
 * @author Florian Stier, Annika Weis, Marcel Rosenberger, Maurizio di Nunzio
 * 
 */

public class OneScenarioResultPresenter extends Presenter<OneScenarioResultViewInterface> {
	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger
			.getLogger("OneScenarioResultPresenter.class");

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

	@EventHandler
	public void onCalculateOneScenario(OneScenarioCalculationEvent event){
		project = event.getProject();
		double[] cashflow;
		double[] fremdkapital;
		double unternehmenswert = 0;
		double dFremdkapital = 0;
		double gesamtkapital;
		Szenario scenario = project.getIncludedScenarios().get(0);
		AbstractPeriodContainer periodContainer = project.getDeterministicPeriods();
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
			unternehmenswert = fte.calculateValues(cashflow, scenario);
			dFremdkapital = fremdkapital[fremdkapital.length - 1];
			logger.debug("Unternehmenswert mit FTE berechnet: "+unternehmenswert);
		}else if(method.getName().equals("Adjusted-Present-Value (APV)")){
			APV apv = new APV();
			unternehmenswert = apv.calculateValues(cashflow, fremdkapital, scenario);
			dFremdkapital = apv.getFremdkapital();
			logger.debug("Unternehmenswert mit APV berechnet: "+unternehmenswert);
		}else{	//method.getName().equals("WACC")

		}
		NumberFormat nfUS = NumberFormat.getInstance(Locale.US);
		nfUS.setMinimumFractionDigits(2);
		nfUS.setMaximumFractionDigits(2);
		project.setCompanyValue(Double.parseDouble(nfUS.format(unternehmenswert).replace(",", "")));
		NumberFormat nfDE = NumberFormat.getInstance(Locale.GERMANY);
		nfDE.setMaximumFractionDigits(2);
		nfDE.setMaximumFractionDigits(2);
//		getView().setCompanyValue(String.valueOf(unternehmenswert));
//		getView().setScenarioValue(String.valueOf(scenario.getRateReturnEquity()), String.valueOf(scenario.getRateReturnCapitalStock()), String.valueOf(scenario.getBusinessTax()), String.valueOf(scenario.getCorporateAndSolitaryTax()));
		getView().setCompanyValue(nfDE.format(unternehmenswert));
		getView().setScenarioValue(nfDE.format(scenario.getRateReturnEquity()), nfDE.format(scenario.getRateReturnCapitalStock()), nfDE.format(scenario.getBusinessTax()), nfDE.format(scenario.getCorporateAndSolitaryTax()));
		
		gesamtkapital = unternehmenswert + dFremdkapital;
		
		ColumnChart cc = new ColumnChart();	
		cc.setOption("is3D", true);	
		cc.setOption("isStacked", true);	
		cc.addXAxisLabel("Year");	
//		cc.addColumn("Gesamtkapital");	
		cc.addColumn("Eigenkapital");	
		cc.addColumn("Fremdkapital");	
		// Values in double are Expenses, Sales, Stock	
//		cc.add(String.valueOf(project.getBasisYear()), new double[]{100,200,320});	
		cc.add(String.valueOf(periods.last().getYear()), new double[]{unternehmenswert, dFremdkapital});		
//		cc.setSizeFull();
		getView().setCapitalChart(cc);
	}

}