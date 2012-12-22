package dhbw.ka.mwi.businesshorizon2.methods.wiener;

import java.util.SortedSet;

import dhbw.ka.mwi.businesshorizon2.methods.AbstractMethod;
import dhbw.ka.mwi.businesshorizon2.methods.MethodRunner.Callback;
import dhbw.ka.mwi.businesshorizon2.methods.Result;
import dhbw.ka.mwi.businesshorizon2.models.Period;

/**
 * Dies ist bisher nur eine Beispiel-Klasse um die Verwendung von verschiedenen Berechnungs-
 * Methoden zu verdeutlichen.
 * 
 * @author Christian Gahlert
 *
 */
public class Wiener extends AbstractMethod {
	private static final long serialVersionUID = 1L;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getName() {
		return "Wiener-Ding";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getOrderKey() {
		return 2;
	}

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public Result calculate(SortedSet<Period> periods, Callback callback)
			throws InterruptedException {
		return new Result();
	}
}
