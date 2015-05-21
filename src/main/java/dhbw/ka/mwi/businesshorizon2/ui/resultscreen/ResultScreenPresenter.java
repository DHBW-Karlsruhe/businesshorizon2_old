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
package dhbw.ka.mwi.businesshorizon2.ui.resultscreen;

import java.util.Iterator;
import java.util.TreeSet;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.mvplite.event.EventBus;
import com.mvplite.event.EventHandler;
import com.mvplite.presenter.Presenter;

import dhbw.ka.mwi.businesshorizon2.methods.AbstractStochasticMethod;
import dhbw.ka.mwi.businesshorizon2.methods.CallbackInterface;
import dhbw.ka.mwi.businesshorizon2.methods.MethodRunner;
import dhbw.ka.mwi.businesshorizon2.methods.discountedCashflow.APV;
import dhbw.ka.mwi.businesshorizon2.methods.timeseries.TimeseriesCalculator;
import dhbw.ka.mwi.businesshorizon2.models.Project;
import dhbw.ka.mwi.businesshorizon2.models.StochasticResultContainer;
import dhbw.ka.mwi.businesshorizon2.models.Szenario;
import dhbw.ka.mwi.businesshorizon2.models.CompanyValue.CompanyValueStochastic;
import dhbw.ka.mwi.businesshorizon2.models.Period.CashFlowPeriod;
import dhbw.ka.mwi.businesshorizon2.models.Period.Period;
import dhbw.ka.mwi.businesshorizon2.models.PeriodContainer.AbstractPeriodContainer;
import dhbw.ka.mwi.businesshorizon2.services.proxies.ProjectProxy;
import dhbw.ka.mwi.businesshorizon2.ui.resultscreen.morescenarios.MoreScenarioResultViewImpl;
import dhbw.ka.mwi.businesshorizon2.ui.resultscreen.onescenario.OneScenarioResultViewImpl;

/**
 * Der Presenter fuer die Maske des Prozessschrittes zur Ergebnisausgabe.
 * 
 * @author Florian Stier, Annika Weis, Marcel Rosenberger, Maurizio di Nunzio
 * 
 */

public class ResultScreenPresenter extends Presenter<ResultScreenViewInterface>
		implements CallbackInterface {
	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger
			.getLogger("ResultScreenPresenter.class");

	@Autowired
	private EventBus eventBus;

	@Autowired
	private ProjectProxy projectProxy;
	
	@Autowired
	private OneScenarioResultViewImpl oneScenarioView;
	
	@Autowired
	private MoreScenarioResultViewImpl moreScenarioView;

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
		logger.debug("init beendet");
	}
	
	/**
	 * Diese Methode gewährleistet, dass der entweder der Screen für mehrere oder für ein Szenario angezeigt wird.
	 * 
	 * @author Marco Glaser, Tobias Lindner
	 */
	@EventHandler
	public void onShowResult(ShowOutputViewEvent event){
		getView().showOutputView();
		project = projectProxy.getSelectedProject();
			
			onProgressChange(0.5f);
			if(project.getIncludedScenarios().size() == 1){

				logger.debug("OneScenarioCalculationEvent gefeuert");
				eventBus.fireEvent(new OneScenarioCalculationEvent(project));
				getView().showView(oneScenarioView);
			}else if (project.getIncludedScenarios().size() == 2){
				eventBus.fireEvent(new MoreScenarioCalculationEvent(project, 2));
				logger.debug("MoreScenarioCalculationEvent (2 Scenarios) fired");
				getView().showView(moreScenarioView);
				getView().removeStyle();
			}
			else {
				eventBus.fireEvent(new MoreScenarioCalculationEvent(project, 3));
				logger.debug("MoreScenarioCalculationEvent (3 Scenarios) fired");
				getView().showView(moreScenarioView);	
				getView().removeStyle();
			}

			onProgressChange(1f);
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

//		StochasticChartArea stochasticChartArea;

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


			if (method.getName().equalsIgnoreCase("zeitreihenanalyse")) {
//				stochasticChartArea = new StochasticChartArea(method.getName(),
//						expectedValues, companyValues.getGradedCompanyValues(),
//						validierung, scenario);
			} else {
//				stochasticChartArea = new StochasticChartArea(method.getName(),
//						null, companyValues.getGradedCompanyValues(),
//						validierung, scenario);
			}
			getView().changeProgress(1);
//			getView().addStochasticChartArea(stochasticChartArea, counter);

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