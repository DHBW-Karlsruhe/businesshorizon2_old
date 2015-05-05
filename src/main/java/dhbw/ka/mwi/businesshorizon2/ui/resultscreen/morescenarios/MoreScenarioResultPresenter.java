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
package dhbw.ka.mwi.businesshorizon2.ui.resultscreen.morescenarios;

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
import com.vaadin.ui.Label;

import dhbw.ka.mwi.businesshorizon2.methods.AbstractDeterministicMethod;
import dhbw.ka.mwi.businesshorizon2.methods.AbstractStochasticMethod;
import dhbw.ka.mwi.businesshorizon2.methods.CallbackInterface;
import dhbw.ka.mwi.businesshorizon2.methods.MethodRunner;
import dhbw.ka.mwi.businesshorizon2.methods.discountedCashflow.APV;
import dhbw.ka.mwi.businesshorizon2.methods.discountedCashflow.FTE;
import dhbw.ka.mwi.businesshorizon2.methods.discountedCashflow.WACC;
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
import dhbw.ka.mwi.businesshorizon2.ui.resultscreen.MoreScenarioCalculationEvent;

/**
 * Der Presenter fuer die Maske des Prozessschrittes zur Ergebnisausgabe.
 * 
 * @author Florian Stier, Annika Weis, Marcel Rosenberger, Maurizio di Nunzio
 * 
 */

public class MoreScenarioResultPresenter extends ScreenPresenter<MoreScenarioResultViewInterface>
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
	
	@EventHandler
	public void onMoreScenarioCalculation (MoreScenarioCalculationEvent event) {
		
		calculateScenario (0, event.getProject());
		calculateScenario (1, event.getProject());
		
		if (event.anzScenarios()==3) {
			getView().addScenario3ToLayout();
			calculateScenario (2, event.getProject());
		}
	}
	
	public void calculateScenario (int numScenario, Project project) {
//		project = event.getProject();
		double[] cashflow;
		double[] fremdkapital;
		double dFremdkapital = 0;
		double unternehmenswert = 0;
		Szenario scenario = project.getIncludedScenarios().get(numScenario);
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
//		cc.setOption("title", "Kapitalstruktur");
		cc.setOption("width", 200);
		cc.setOption("height", 240);
		cc.setColors(new String[]{"#92D050", "#FFFF00"});
		cc.addXAxisLabel("Year");	
		//		cc.addColumn("Gesamtkapital");	
		cc.addColumn("Eigenkapital");	
		cc.addColumn("Fremdkapital");	
		// Values in double are Expenses, Sales, Stock	
		//		cc.add(String.valueOf(project.getBasisYear()), new double[]{100,200,320});	
		cc.add(String.valueOf(periods.last().getYear()), new double[]{Double.parseDouble(nfUS.format(unternehmenswert).replace(",", "")), dFremdkapital});		
		//		cc.setSizeFull();
		
		getView().addChartScenario(numScenario, cc);
	}
	
//	@SuppressWarnings("unchecked")
//	@EventHandler
//	public void onShowOutputView(ShowOutputViewEvent event) {
//
//		getView().showOutputView();
//
//		project = projectProxy.getSelectedProject();
//
//		if (project.getProjectInputType().isDeterministic()) {
//			TreeSet<AbstractPeriodContainer> periodContainer = new TreeSet<AbstractPeriodContainer>();
//			AbstractPeriodContainer apc = project.getDeterministicPeriods();
//			periodContainer.add(apc);
//			DeterministicResultContainer drContainer = new DeterministicResultContainer(
//					periodContainer);
//
//			// Annika Weis
//			for (AbstractDeterministicMethod method_deterministic : project
//					.getMethods_deterministic()) {
//				// alle Szenarios durchlaufen
//				int counter = 0;
//				for (Szenario scenario : project.getIncludedScenarios()) {
//					onProgressChange((float) 0.5);
//
//					counter++;
//
//					// Cashflows berechnen falls notwendig
//					for (AbstractPeriodContainer container : drContainer
//							.getPeriodContainers()) {
//						if (container instanceof CashFlowPeriodContainer) {
//							logger.debug("Cashflows wurden direkt eingegeben");
//						} else if (container instanceof GesamtkostenVerfahrenCashflowPeriodContainer) {
//							logger.debug("Cashflows werden nach GKV berechnet");
//							CashFlowCalculator.calculateCashflows(drContainer,
//									scenario);
//						} else if (container instanceof UmsatzkostenVerfahrenCashflowPeriodContainer) {
//							logger.debug("Cashflows werden nach UKV berechnet");
//							CashFlowCalculator.calculateCashflows(drContainer,
//									scenario);
//						}
//					}
//
//					if (method_deterministic.getSelected()) {
//						double unternehmenswert = 0;
//						double[] cashflow;
//						double[] fremdkapital;
//						int i;
//						Period period;
//
//						// für jedenPeriod-Container, der im
//						// Deterministic-Result-Container enthalten ist,
//						// wird die Schleife je einmal durchlaufen
//						// dadurch werden zuerst die Cashflow und Fremdkapital
//						// Arrays befüllt
//						for (AbstractPeriodContainer abstractPeriodContainer : drContainer
//								.getPeriodContainers()) {
//							// holt pro Cashflow-Period-Container die
//							// enthaltenen Perioden
//							// und legt sie in einem TreeSet ab
//							TreeSet<? extends Period> periods = abstractPeriodContainer
//									.getPeriods();
//							// ein Iterator zum durchlaufen des TreeSet wird
//							// erstellt.
//							Iterator<? extends Period> periodenIterator = periods
//									.iterator();
//							// Zähler, Cashflow- und Fremdkapital-Arrays werden
//							// zurückgesetzt
//							cashflow = new double[periods.size()];
//							fremdkapital = new double[periods.size()];
//							i = 0;
//							// pro Periode sollen nun die Werte ausgelesen und
//							// ein
//							// Unternehmenswert berechnet werden
//							while (periodenIterator.hasNext()) {
//								period = periodenIterator.next();
//								cashflow[i] = period.getFreeCashFlow();
//								fremdkapital[i] = period.getCapitalStock();
//								i++;
//							}
//
//							if (method_deterministic.getName() == "Adjusted-Present-Value (APV)") {
//								double steuervorteile = 0;
//								double fremdkapitalout = 0;
//								double uwsteuerfrei = 0;
//
//								APV apv_2 = new APV();
//								// berechnet den Unternehmenswert des
//								// betrachteten
//								// Period-Container
//								unternehmenswert = apv_2.calculateValues(
//										cashflow, fremdkapital, scenario);
//								uwsteuerfrei = apv_2.getUwsteuerfrei();
//								steuervorteile = apv_2.getSteuervorteile();
//								fremdkapitalout = apv_2.getFremdkapital();
//
//								DeterministicChartArea deterministicarea = new DeterministicChartArea(
//										uwsteuerfrei, steuervorteile,
//										unternehmenswert, fremdkapitalout,
//										method_deterministic.getName(),
//										drContainer, scenario);
//
//								getView().addDeterministicChartArea(
//										deterministicarea, counter);
//
//								/*
//								 * Alte Implementierung
//								 * 
//								 * 
//								 * CompanyValueDeterministic
//								 * companyValueDeterministic =
//								 * (CompanyValueDeterministic) apv
//								 * .calculateCompanyValue(); for (Entry<Integer,
//								 * Couple> companyValue :
//								 * companyValueDeterministic.getCompanyValues
//								 * ().entrySet()) { DeterministicChartArea
//								 * deterministicChartArea = new
//								 * DeterministicChartArea
//								 * (companyValue.getValue()
//								 * .getDebitFreeCompany(),
//								 * companyValue.getValue().getTaxBenefits(),
//								 * companyValue.getValue() .getCompanyValue(),
//								 * companyValue.getValue().getCapitalStock());
//								 * getView ().addDeterministicChartArea(
//								 * deterministicChartArea ); }
//								 */
//
//							}
//							if (method_deterministic.getName() == "Flow-to-Equity (FTE)") {
//								FTE dcf_2 = new FTE();
//								unternehmenswert = dcf_2.calculateValues(
//										drContainer.getCashflows(), scenario);
//
//								DeterministicChartArea deterministicarea = new DeterministicChartArea(
//										unternehmenswert,
//										method_deterministic.getName(),
//										drContainer, scenario);
//
//								getView().addDeterministicChartArea(
//										deterministicarea, counter);
//
//								/*
//								 * DeterministicChartArea chart = new
//								 * DeterministicChartArea(
//								 * method_deterministic.getName(),
//								 * unternehmenswert, "Cashflows",
//								 * drContainer.getJahre(),
//								 * drContainer.getCashflows());
//								 * getView().addDeterministicChartArea(chart);
//								 * // chart
//								 * 
//								 * }
//								 */
//
//							}
//						}
//					}
//					onProgressChange((float) 1);
//
//					/*
//					 * for (Szenario scenario : project.getScenarios()) {
//					 * onProgressChange((float) 0.5); CashFlowPeriodContainer
//					 * cfPeriodContainer = (CashFlowPeriodContainer) project
//					 * .getDeterministicPeriods();
//					 * 
//					 * TreeSet<AbstractPeriodContainer> periodContainer = new
//					 * TreeSet<>(); periodContainer.add(cfPeriodContainer);
//					 * StochasticResultContainer srContainer = new
//					 * StochasticResultContainer( periodContainer);
//					 * 
//					 * APV apv = new APV(srContainer, scenario); // Annika Weis
//					 * APV_2 apv_2 = new APV_2(); apv_2.calculateValues(new
//					 * DeterministicResultContainer( periodContainer),
//					 * scenario);// project, scenario DCF_2 dcf_2 = new DCF_2();
//					 * dcf_2.calculateValues(new DeterministicResultContainer(
//					 * periodContainer), scenario);// srContainer
//					 * CompanyValueDeterministic companyValueDeterministic =
//					 * (CompanyValueDeterministic) apv .calculateCompanyValue();
//					 * for (Entry<Integer, Couple> companyValue :
//					 * companyValueDeterministic .getCompanyValues().entrySet())
//					 * { DeterministicChartArea deterministicChartArea = new
//					 * DeterministicChartArea(
//					 * companyValue.getValue().getDebitFreeCompany(),
//					 * companyValue.getValue().getTaxBenefits(),
//					 * companyValue.getValue().getCompanyValue(),
//					 * companyValue.getValue().getCapitalStock());
//					 * getView().addDeterministicChartArea
//					 * (deterministicChartArea); } onProgressChange((float) 1);
//					 * }
//					 */
//
//				}
//			}
//		}
//
//		if (project.getProjectInputType().isStochastic()) {
//			TreeSet<AbstractPeriodContainer> periodContainer = new TreeSet<AbstractPeriodContainer>();
//			AbstractPeriodContainer apc = project.getStochasticPeriods();
//			periodContainer.add(apc);
//			StochasticResultContainer srContainer = new StochasticResultContainer(
//					periodContainer);
//
//			// pro Szenario werden die Cashflows berechnet
//			for (Szenario scenario : project.getIncludedScenarios()) {
//
//				// Cashflows berechnen falls notwendig
//				for (AbstractPeriodContainer container : srContainer
//						.getPeriodContainers()) {
//					if (container instanceof CashFlowPeriodContainer) {
//						logger.debug("Cashflows wurden direkt eingegeben");
//					} else if (container instanceof GesamtkostenVerfahrenCashflowPeriodContainer) {
//						logger.debug("Cashflows werden direkt berechnet");
//						CashFlowCalculator.calculateCashflows(srContainer,
//								scenario);
//					} else if (container instanceof UmsatzkostenVerfahrenCashflowPeriodContainer) {
//						logger.debug("Cashflows werden indirekt berechnet");
//						CashFlowCalculator.calculateCashflows(srContainer,
//								scenario);
//					}
//				}
//			}
//			for (AbstractStochasticMethod method : project.getMethods()) {
//				if (method.getSelected()) {
//					methodRunner = new MethodRunner(method, project, this);
//					methodRunner.start();
//				}
//			}
//		}
//
//	}
	
//	@EventHandler
//	public void onShowResult(ShowOutputViewEvent event){
//		getView().showOutputView();
//		project = projectProxy.getSelectedProject();
//		if(project.getProjectInputType().isStochastic()){
//			
//		}
//		if(project.getProjectInputType().isDeterministic()){
//			for(Szenario scenario : project.getIncludedScenarios()){
//				onProgressChange(0.5f);
//				
//				AbstractPeriodContainer periods = project.getDeterministicPeriods();
//				if(periods instanceof CashFlowPeriodContainer){
//					
//				}
//				
//				AbstractDeterministicMethod method = project.getCalculationMethod();
//				if(method.getName().equals("Flow-to-Equity (FTE)")){
//					FTE fte = new FTE();
//					
//				}else if(method.getName().equals("Adjusted-Present-Value (APV)")){
//					
//				}else{	//method.getName().equals("WACC")
//					
//				}
//			}
//		}
//		
//	}

	@Override
	public boolean isValid() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void validate(ValidateContentStateEvent event) {
		// TODO Auto-generated method stub

	}

//	@EventHandler
//	public void handleShowView(ShowOutputViewEvent event) {
////		eventBus.fireEvent(new ScreenSelectableEvent(NavigationSteps.OUTPUT,
////				true));
//		logger.debug("ShowOutputViewEvent handled");
//	}

	@Override
	public void handleShowErrors(ShowErrorsOnScreenEvent event) {
		// TODO Auto-generated method stub

	}

	/**
	 * Wenn die Berechnung der stochastisch vorhergesagten Perioden erfolgreich
	 * durchlaufen wurde, dann kann der Unternehmenswert berechnet werden
	 * 
	 * 
	 * 
	 * @author Marcel Rosenberger, Maurizio di Nunzio
	 * 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void onComplete(StochasticResultContainer result,
			AbstractStochasticMethod method) {

		StochasticChartArea stochasticChartArea;

		int counter = 0;
		for (Szenario scenario : project.getIncludedScenarios()) {
			CompanyValueStochastic companyValues = new CompanyValueStochastic();
			APV apv = new APV();

			counter++;
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
			try {
				for (AbstractPeriodContainer abstractPeriodContainer : result
						.getPeriodContainers()) {
					// holt pro Cashflow-Period-Container die enthaltenen
					// Perioden
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
					unternehmenswert = apv.calculateValues(cashflow,
							fremdkapital, scenario);
					// fügt den Unternehmenswert der Sammelklasse aller
					// Unternehmenswert hinzu
					companyValues.addCompanyValue(unternehmenswert);

				}
				logger.debug("Unternehmenswerte berechnet und in Sammelklasse einzugefügt.");
			} catch (NullPointerException e) {
				getView()
						.showErrorMessge(
								"Entweder alle Cashflows oder alle Fremdkapital-Werte sind gleich groß. In diesem Fall ist die Zeitreihenanalyse aus mathematischen Gründen nicht durchführbar.");
			}

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
			getView().addStochasticChartArea(stochasticChartArea, counter);

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