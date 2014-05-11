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
		return false;
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
	public StochasticResultContainer calculate(Project project, CallbackInterface callback)
			throws InterruptedException, StochasticMethodException {

		TreeSet<CashFlowPeriodContainer> prognose = new TreeSet<CashFlowPeriodContainer>();
		for (int iteration = 0; iteration < project.getIterations(); iteration++) {
			CashFlowPeriodContainer cFPContainer = new CashFlowPeriodContainer();

			CashFlowPeriod lastPeriod = null;
			;
			for (int forecast = 1; forecast <= project.getPeriodsToForecast(); forecast++) {
				double previousValueCF;
				double previousValueBC;
				CashFlowPeriod period = new CashFlowPeriod(project.getBasisYear() + forecast);
				if (forecast == 1) {
					previousValueCF = project.getStochasticPeriods().getPeriods().last().getFreeCashFlow();
					previousValueBC = project.getStochasticPeriods().getPeriods().last().getCapitalStock();
				} else {
					previousValueCF = lastPeriod.getFreeCashFlow();
					previousValueBC = lastPeriod.getCapitalStock();
				}
				period.setFreeCashFlow(calculateRandomNumber(project.getCashFlowProbabilityOfRise())
						* project.getCashFlowStepRange() + previousValueCF);
				period.setCapitalStock(calculateRandomNumber(project.getBorrowedCapitalProbabilityOfRise())
						* project.getBorrowedCapitalStepRange() + previousValueBC);
				lastPeriod = period;
				cFPContainer.addPeriod(period);
			}
			prognose.add(cFPContainer);
			if (callback != null && iteration % 200 == 0) {
				// Alle 200 Iterationen ein Update für das Callback
				callback.onProgressChange((iteration * project.getPeriodsToForecast())
						/ (project.getIterations() * project.getPeriodsToForecast()));
				Thread.interrupted();
			}
		}
		StochasticResultContainer src = new StochasticResultContainer(prognose);
		return src;

	}
}