package dhbw.ka.mwi.businesshorizon2.ui.process.output;

import java.util.Map.Entry;
import java.util.Map;
import java.util.TreeSet;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.gwt.dev.jjs.impl.RemoveEmptySuperCalls;
import com.mvplite.event.EventBus;
import com.mvplite.event.EventHandler;

import dhbw.ka.mwi.businesshorizon2.methods.AbstractStochasticMethod;
import dhbw.ka.mwi.businesshorizon2.methods.CallbackInterface;
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

	private Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private EventBus eventBus;

	@Autowired
	private ProjectProxy projectProxy;

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

						method.calculate(project, this);
					}
				} catch (StochasticMethodException e) {
					getView().showErrorMessge(e.getMessage());
				} catch (InterruptedException e) {
					getView().showErrorMessge(e.getMessage());
				}
			}

		}

		if (project.getProjectInputType().getDeterministic()) {
			for (Szenario scenario : project.getScenarios()) {
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
	public void onComplete(StochasticResultContainer result) {
		for (Szenario scenario : project.getScenarios()) {
			APV apv = new APV(result, scenario);
			CompanyValueStochastic companyValue = (CompanyValueStochastic) apv.calculateCompanyValue();
			StochasticChartArea stochasticChartArea = new StochasticChartArea(expectedCashFlows,
					companyValue.getCompanyValues());
			getView().changeProgress(1);
			getView().addStochasticChartArea(stochasticChartArea);
			
			//TODO Testausgabe wieder entfernen
			for (Map.Entry<Double,CompanyValueStochastic.Couple> entry: companyValue.getCompanyValues().entrySet()){
				System.out.println("Key: " + entry.getKey() + " CompanyValue: " + entry.getValue().getCompanyValue() + " Count: " + entry.getValue().getCount());
			}

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

