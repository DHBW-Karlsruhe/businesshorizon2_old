package dhbw.ka.mwi.businesshorizon2.methods.discountedCashflow;

import dhbw.ka.mwi.businesshorizon2.models.StochasticResultContainer;

/**
 * Diese Klasse stellt die Oberklasse für Bewertungsmethoden der errechnet
 * CashFlows dar.
 * 
 * @author Kai Westerholz
 * 
 */

public abstract class RatingMethods {

	public abstract CompanyValues calculateCompanyValue(
			StochasticResultContainer container);
}
