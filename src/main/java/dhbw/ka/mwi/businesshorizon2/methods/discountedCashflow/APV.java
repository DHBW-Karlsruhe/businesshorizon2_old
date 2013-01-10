package dhbw.ka.mwi.businesshorizon2.methods.discountedCashflow;

import java.util.TreeSet;

import dhbw.ka.mwi.businesshorizon2.models.AbstractPeriodContainer;
import dhbw.ka.mwi.businesshorizon2.models.PeriodInterface;
import dhbw.ka.mwi.businesshorizon2.models.StochasticResultContainer;
import dhbw.ka.mwi.businesshorizon2.models.Szenario;

/**
 * 
 * @author kathie
 * 
 */
public class APV extends RatingMethods {

	@Override
	public CompanyValues calculateCompanyValue(
			StochasticResultContainer container, Szenario szenario) {

		for (AbstractPeriodContainer i : container.getPeriodContainers()) {

			TreeSet<? extends PeriodInterface> periods = i.getPeriods();
			PeriodInterface p = periods.last();

		}
		return null;
	}
}
