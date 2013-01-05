package dhbw.ka.mwi.businesshorizon2.methods;

import dhbw.ka.mwi.businesshorizon2.models.PeriodenContainer;

public abstract class RatingMethods {
	abstract double[] calculateCompanyValue(PeriodenContainer<?> futurePeriods);
}
