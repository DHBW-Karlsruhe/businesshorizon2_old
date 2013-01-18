package dhbw.ka.mwi.businesshorizon2.methods.random;

import java.util.TreeSet;

import dhbw.ka.mwi.businesshorizon2.methods.AbstractStochasticMethod;
import dhbw.ka.mwi.businesshorizon2.methods.CallbackInterface;
import dhbw.ka.mwi.businesshorizon2.methods.StochasticMethodException;
import dhbw.ka.mwi.businesshorizon2.models.Project;
import dhbw.ka.mwi.businesshorizon2.models.StochasticResultContainer;
import dhbw.ka.mwi.businesshorizon2.models.Period.CashFlowPeriod;
import dhbw.ka.mwi.businesshorizon2.models.PeriodContainer.CashFlowPeriodContainer;

public class RandomWalk extends AbstractStochasticMethod {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String getName() {

		return "Random Walk";
	}

	@Override
	public int getOrderKey() {

		return 3;
	}

	@Override
	public Boolean getImplemented() {
		return true;
	}

	/**
	 * Diese Methode berechnet die Entwicklung des CashFlows. Dieser kann
	 * positiv oder negativ ausfallen. Das Ergebnis wird auf 1 oder -1 normiert.
	 * Sie ist abhängig von der übergebenen positive
	 * Entwicklungswahrscheinlichkeit.
	 * 
	 * @author Kai Westerholz
	 * 
	 * @param Entwicklungswahrscheinlichkeit
	 * @return Entwicklungsindikator
	 */
	private int calculateRandomNumber(double p) {
		int randomNumber;

		if ((Math.random() * (1 - 0) + 0) < p) {
			randomNumber = 1;
		} else {
			randomNumber = -1;
		}

		return randomNumber;
	}

	@Override
	public StochasticResultContainer calculate(Project project,
			CallbackInterface callback) throws InterruptedException,
			StochasticMethodException {

		TreeSet<CashFlowPeriodContainer> prognose = new TreeSet<CashFlowPeriodContainer>();
		for (int iteration = 0; iteration < project.getIterations(); iteration++) {
			CashFlowPeriodContainer cFPContainer = new CashFlowPeriodContainer();

			CashFlowPeriod lastPeriod = null;
			;
			for (int forecast = 1; forecast <= project.getPeriodsToForecast(); forecast++) {
				double previousValueCF;
				double previousValueBC;
				CashFlowPeriod period = new CashFlowPeriod(
						project.getBasisYear() + forecast);
				if (forecast == 1) {
					previousValueCF = project.getPeriods().last()
							.getFreeCashFlow();
					previousValueBC = project.getPeriods().last()
							.getBorrowedCapital();
				} else {
					previousValueCF = lastPeriod.getFreeCashFlow();
					previousValueBC = lastPeriod.getBorrowedCapital();
				}
				period.setFreeCashFlow(calculateRandomNumber(project
						.getCashFlowProbabilityOfRise())
						* project.getCashFlowStepRange() + previousValueCF);
				period.setBorrowedCapital(calculateRandomNumber(project
						.getBorrowedCapitalProbabilityOfRise())
						* project.getBorrowedCapitalStepRange()
						+ previousValueBC);
				lastPeriod = period;
				cFPContainer.addPeriod(period);
			}
			prognose.add(cFPContainer);
			if (callback != null && iteration % 200 == 0) {
				// Alle 200 Iterationen ein Update für das Callback
				callback.onProgressChange((iteration * project
						.getPeriodsToForecast())
						/ (project.getIterations() * project
								.getPeriodsToForecast()));
				Thread.interrupted();
			}
		}
		StochasticResultContainer src = new StochasticResultContainer(prognose);
		if (callback != null) {
			callback.onComplete(src);
		}
		return src;
	}

}
