package dhbw.ka.mwi.businesshorizon2.methods;

import dhbw.ka.mwi.businesshorizon2.models.AbstractPeriodContainer;

/**
 * Diese Klasse stellt die Oberklasse für Bewertungsmethoden der errechnet
 * CashFlows dar.
 * 
 * @author Kai Westerholz
 * 
 */

public abstract class RatingMethods {
	abstract double[] calculateCompanyValue(
			AbstractPeriodContainer futurePeriods);
}
