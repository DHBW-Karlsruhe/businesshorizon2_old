/**
 * 
 */
package dhbw.ka.mwi.businesshorizon2.methods.discountedCashflow;

import java.util.Iterator;
import java.util.TreeSet;

import dhbw.ka.mwi.businesshorizon2.methods.AbstractDeterministicMethod;
import dhbw.ka.mwi.businesshorizon2.methods.CallbackInterface;
import dhbw.ka.mwi.businesshorizon2.methods.DeterministicMethodException;
import dhbw.ka.mwi.businesshorizon2.models.DeterministicResultContainer;
import dhbw.ka.mwi.businesshorizon2.models.Project;
import dhbw.ka.mwi.businesshorizon2.models.StochasticResultContainer;
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
public class DCF_2 extends AbstractDeterministicMethod {

	@Override
	public String getName() {

		return "DCF";
	}

	@Override
	public int getOrderKey() {

		return 4;
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

	public DeterministicResultContainer calculateValues(
			StochasticResultContainer srContainer, Szenario szenario) {
		TreeSet<CashFlowPeriodContainer> prognose = new TreeSet<CashFlowPeriodContainer>();

		double unternehmenswert = 0;
		double restwert = 0;
		double nbv = 0; // Nicht-betriebsnotwendiges Vermögen
		double sSteuersatz;
		double sKS;
		double sZinsen;
		double sEK;

		CashFlowPeriod first_period = null;
		CashFlowPeriod period;
		CashFlowPeriod lastPeriod = null;
		double jahr = 1;

		sKS = szenario.getCorporateAndSolitaryTax() / 100;
		sSteuersatz = 0.75 * szenario.getBusinessTax() / 100 + sKS;
		sEK = szenario.getRateReturnEquity() / 100;
		sZinsen = szenario.getRateReturnCapitalStock() / 100;

		for (AbstractPeriodContainer i : srContainer.getPeriodContainers()) {

			TreeSet<? extends Period> periods = i.getPeriods();
			Iterator<? extends Period> iter = periods.iterator();// descendingIterator();
			int durchlauf = 1;
			while (iter.hasNext()) {
				period = (CashFlowPeriod) iter.next();

				//zum Unternehmenswert einen weiteren abgezinsten Cashflow addieren
				unternehmenswert += abzinsen(period.getFreeCashFlow(), sEK, durchlauf);

				lastPeriod = period;

				durchlauf++;
				jahr = durchlauf;
			}
		}

		//Restwert berechnen
		restwert = lastPeriod.getFreeCashFlow() / sEK;
		//Restwert abzinsen
		restwert = abzinsen(restwert, sEK, (int) jahr-1);
		// Unternehmenswert gesamt berechnen
		unternehmenswert = unternehmenswert + restwert // / Math.pow(1 + sEK, jahr-1)
				 + nbv;
		System.out.println("Endergebnis DCF: " + unternehmenswert);

		DeterministicResultContainer drc = new DeterministicResultContainer(
				prognose);
		return drc;
	}

	/**
	 * @author Annika Weis
	 * @param wert
	 * @param zinssatz
	 * @param jahre
	 * @return
	 */
	private double abzinsen(double wert, double zinssatz, int jahre) {
		return wert
				/ Math.pow(1 + zinssatz, jahre);
	}
}
