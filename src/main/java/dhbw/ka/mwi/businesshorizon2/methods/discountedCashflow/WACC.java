package dhbw.ka.mwi.businesshorizon2.methods.discountedCashflow;

import java.util.TreeSet;

import org.apache.log4j.Logger;

import dhbw.ka.mwi.businesshorizon2.methods.AbstractDeterministicMethod;
import dhbw.ka.mwi.businesshorizon2.methods.CallbackInterface;
import dhbw.ka.mwi.businesshorizon2.methods.DeterministicMethodException;
import dhbw.ka.mwi.businesshorizon2.models.DeterministicResultContainer;
import dhbw.ka.mwi.businesshorizon2.models.Project;
import dhbw.ka.mwi.businesshorizon2.models.Szenario;
import dhbw.ka.mwi.businesshorizon2.models.PeriodContainer.CashFlowPeriodContainer;

/**
 * Die Klasse WACC stellt die Methoden zur Berechnung des Unternehmenswerts mittels des Wacc-Ansatzes zur Verfuegung.
 *
 * @author Tobias Blasy
 * @date 09.01.2015
 */
public class WACC extends AbstractDeterministicMethod {

	private static final Logger logger = Logger.getLogger("WACC.class");
	private double unternehmenswert;
	private double fremdkapital;

	@Override
	public String getName() {
		return "Weighted-Average-Cost-of-Capital (WACC)";
	}
	
	// ToDo anderen Rückgabewert angeben
	@Override
	public int getOrderKey() {
		return 5;
	}

	@Override
	public Boolean getImplemented() {
		return true;
	}
	
	@Override
	public DeterministicResultContainer calculate(Project project, CallbackInterface callback) throws InterruptedException, DeterministicMethodException {
		TreeSet<CashFlowPeriodContainer> prognose = new TreeSet<CashFlowPeriodContainer>();
		DeterministicResultContainer drc = new DeterministicResultContainer(prognose);
		return drc;
	}
	
	/**
	 * berechnet das Eigenkapital der vorletzten Periode (wenn man die ewige Rente als letzte Periode nimmt)
	 * 
	 * @author Tobias Blasy
	 * @param double fcfEwigeRente
	 * @param double eigenkapitalRenditeUnverschuldet
	 * @param double steuersatz
	 * @param double fremdkapital
	 * @return double ekLetztePeriode
	 */
	public double berechneEkVorletztePeriode(double fcfEwigeRente, double eigenkapitalRenditeUnverschuldet, double steuersatz, double fremdkapital) {
		return ((fcfEwigeRente - fremdkapital * eigenkapitalRenditeUnverschuldet * (1 - steuersatz))/eigenkapitalRenditeUnverschuldet);
	}

	/**
	 * berechnet das Eigenkapital der übrigen Perioden 0 bis T-2 (wenn man die ewige Rente als letzte Periode nimmt)
	 * 
	 * @author Tobias Blasy
	 * @param int t
	 * @param int indexT
	 * @param double gesamtkapitalIndextPlusEins
	 * @param double fcfIndextPlusEins
	 * @param double fremdkapitalRendite
	 * @param double steuersatz 
	 * @param double eigenkapitalRenditeUnverschuldet
	 * @param double[] fremdkapital
	 * @return double ekRestlichePerioden
	 */
	public double berechneEkRestlichePerioden(int t, int indexT, double gesamtkapitalIndextPlusEins, double fcfIndextPlusEins, double fremdkapitalRendite, double steuersatz, double eigenkapitalRenditeUnverschuldet, double[] fremdkapital) {
		return ((gesamtkapitalIndextPlusEins + fcfIndextPlusEins - fremdkapital[t] * (1 + fremdkapitalRendite * (1 - steuersatz)) - (eigenkapitalRenditeUnverschuldet - fremdkapitalRendite) * (fremdkapital[t] - berechneSumme(t, indexT, steuersatz, fremdkapitalRendite, fremdkapital) - (steuersatz * fremdkapital[indexT]) / (Math.pow(1 + fremdkapitalRendite, indexT - t))))
				/
				(1 + eigenkapitalRenditeUnverschuldet));
	}

	/**
	 * berechnet eine Summe die für die Berechnung des Eigenkapitals der restlichen Perioden benötigt wird
	 * 
	 * @author Tobias Blasy
	 * @param int t
	 * @param int indexT
	 * @param double steuersatz
	 * @param double fremdkapitalRendite
	 * @param double[] fremdkapital
	 * @return double summe
	 */
	public double berechneSumme(int t, int indexT, double steuersatz, double fremdkapitalRendite, double[] fremdkapital) {
		double sum = 0;
		for(int j = t+1; j <= indexT; j++) {
			sum += ((steuersatz * fremdkapitalRendite * fremdkapital[j-1])/(Math.pow(1 + fremdkapitalRendite, j-t)));
		}
		return sum;
	}
	
	/**
	 * ermittelt aus den übergebenen Cashflows, den Fremdkapitalangaben und dem Szenario auf rekursivem Weg die Unternehmenswerte und gibt den entsprechenden Wert dieser (der 0.) Periode zurück
	 *
	 * @author Tobias Blasy
	 * @param double[] cashflow
	 * @param double[] fremdkapital,
	 * @param Szenario szenario
	 * @return double unternehmenswert
	 */
	public double calculateValues(double[] cashflow, double[] fremdkapital,	Szenario szenario) {

		double fremdkapitalRendite = szenario.getRateReturnCapitalStock() / 100;
		double eigenkapitalRenditeUnverschuldet = szenario.getRateReturnEquity() / 100;
		// ToDo die Berechnung des Steuersatzes muss noch überprüft werden
		double steuersatz = 0.75 * szenario.getBusinessTax() / 100 + szenario.getCorporateAndSolitaryTax() / 100;
		double fremdkapitalIndexT = fremdkapital[fremdkapital.length-2];
		double gesamtkapitalIndextPlusEins;
		double[] eigenkapitalwerte = new double[cashflow.length];
		int indexT = eigenkapitalwerte.length-2;
		
		eigenkapitalwerte[indexT] = berechneEkVorletztePeriode(cashflow[indexT+1], eigenkapitalRenditeUnverschuldet, steuersatz, fremdkapitalIndexT);
		eigenkapitalwerte[indexT+1] = eigenkapitalwerte[indexT];
		
		for(int t = indexT-1; t >= 0; t--) {
			gesamtkapitalIndextPlusEins = fremdkapital[t+1] + eigenkapitalwerte[t+1];
			eigenkapitalwerte[t] = berechneEkRestlichePerioden(t, indexT, gesamtkapitalIndextPlusEins, cashflow[t+1], fremdkapitalRendite, steuersatz, eigenkapitalRenditeUnverschuldet, fremdkapital);
		}
		this.setFremdkapital(fremdkapital[0]);
		this.setUnternehmenswert(eigenkapitalwerte[0]);
		return eigenkapitalwerte[0];
	}
	
	public double getUnternehmenswert() {
		return this.unternehmenswert;
	}
	
	public void setUnternehmenswert(double unternehmenswert) {
		this.unternehmenswert = unternehmenswert;
	}
	
	public double getFremdkapital() {
		return this.fremdkapital;
	}
	
	public void setFremdkapital(double fremdkapital) {
		this.fremdkapital = fremdkapital;
	}	

}