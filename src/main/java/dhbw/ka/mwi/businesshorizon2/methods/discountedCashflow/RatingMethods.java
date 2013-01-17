package dhbw.ka.mwi.businesshorizon2.methods.discountedCashflow;

import dhbw.ka.mwi.businesshorizon2.models.StochasticResultContainer;
import dhbw.ka.mwi.businesshorizon2.models.Szenario;
import dhbw.ka.mwi.businesshorizon2.models.CompanyValue.CompanyValue;

/**
 * Diese Klasse stellt die Oberklasse für Bewertungsmethoden der errechnet
 * CashFlows dar.
 * 
 * @author Kai Westerholz
 * 
 */

public abstract class RatingMethods {
	protected StochasticResultContainer container;
	protected Szenario szenario;

	public RatingMethods(StochasticResultContainer container, Szenario szenario) {
		this.container = container;
		this.szenario = szenario;
	}

	public abstract CompanyValue calculateCompanyValue();
}
