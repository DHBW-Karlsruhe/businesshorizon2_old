/**
 * 
 */
package dhbw.ka.mwi.businesshorizon2.methods.discountedCashflow;

import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

import dhbw.ka.mwi.businesshorizon2.methods.AbstractDeterministicMethod;
import dhbw.ka.mwi.businesshorizon2.methods.CallbackInterface;
import dhbw.ka.mwi.businesshorizon2.methods.DeterministicMethodException;
import dhbw.ka.mwi.businesshorizon2.models.DeterministicResultContainer;
import dhbw.ka.mwi.businesshorizon2.models.Project;
import dhbw.ka.mwi.businesshorizon2.models.Szenario;
import dhbw.ka.mwi.businesshorizon2.models.Period.CashFlowPeriod;
import dhbw.ka.mwi.businesshorizon2.models.Period.Period;
import dhbw.ka.mwi.businesshorizon2.models.PeriodContainer.AbstractPeriodContainer;
import dhbw.ka.mwi.businesshorizon2.models.PeriodContainer.CashFlowPeriodContainer;

/**
 * @author Annika Weis
 * @date 29.12.2013
 * 
 */
public class APV_2 extends AbstractDeterministicMethod {

	@Override
	public String getName() {

		return "APV";
	}

	@Override
	public int getOrderKey() {

		return 5;
	}

	@Override
	public Boolean getImplemented() {
		return true;
	}

	@Override
	public DeterministicResultContainer calculate(Project project,
			CallbackInterface callback) throws InterruptedException,
			DeterministicMethodException {
		TreeSet<CashFlowPeriodContainer> prognose = new TreeSet<CashFlowPeriodContainer>();
		DeterministicResultContainer drc = new DeterministicResultContainer(
				prognose);
		return drc;
	}

	public double calculateValues(double[] cashflow, double[] fremdkapital,
			Szenario szenario) {
		double gk = 0;
		double v = 0;
		double unternehmenswert = 0;
		double sSteuersatz;
		double sKS;
		double sZinsen;
		double sEK;

		Double first_period_cashflow = null;
		Double first_period_fremdkapital = null;
		Double period_cashflow;
		Double period_fremdkapital;
		Double lastPeriod_cashflow = null;
		Double lastPeriod_fremdkapital = null;
		double jahr = 1;

		sKS = szenario.getCorporateAndSolitaryTax() / 100;
		sSteuersatz = 0.75 * szenario.getBusinessTax() / 100 + sKS;
		sEK = szenario.getRateReturnEquity() / 100;
		sZinsen = szenario.getRateReturnCapitalStock() / 100;

		for (int durchlauf = 0; durchlauf < cashflow.length; durchlauf++) {
			period_cashflow = cashflow[durchlauf];
			period_fremdkapital = fremdkapital[durchlauf];

			if (durchlauf == 0) { // Basisjahr
				first_period_cashflow = cashflow[durchlauf];
				first_period_fremdkapital = fremdkapital[durchlauf];
			} else if (durchlauf + 1 == cashflow.length) { // letztes Jahr wird
															// nach der Schleife
															// extra berechnet
			} else {
				gk += abzinsen(cashflow[durchlauf], sEK, durchlauf);
				v += (sSteuersatz * sZinsen * fremdkapital[durchlauf])
						/ Math.pow(1 + sZinsen, durchlauf);
			}
			lastPeriod_cashflow = period_cashflow;
			lastPeriod_fremdkapital = period_fremdkapital;

			jahr = durchlauf;
		}
		// Berechnung des letzten Jahres
		gk = gk + lastPeriod_cashflow / (sEK * Math.pow(1 + sEK, jahr - 2));
		v = v + (sSteuersatz * sZinsen * lastPeriod_fremdkapital)
				/ (sZinsen * Math.pow(1 + sZinsen, jahr - 2));

		// Unternehmenswert gesamt berechnen
		unternehmenswert = gk + v - first_period_cashflow;
		System.out.println("Endergebnis APV:" + gk + " + " + v + " - "
				+ first_period_cashflow + " = " + unternehmenswert);

		return unternehmenswert;
	}

	/**
	 * @author Annika Weis
	 * @param wert
	 * @param zinssatz
	 * @param jahre
	 * @return Double, abgezinster Wert
	 */
	private double abzinsen(double wert, double zinssatz, int jahre) {
		return wert / Math.pow(1 + zinssatz, jahre);
	}
}
