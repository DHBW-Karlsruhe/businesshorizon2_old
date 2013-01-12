package dhbw.ka.mwi.businesshorizon2.methods.discountedCashflow;

import java.util.Iterator;
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

	private final double s;

	public APV(StochasticResultContainer container, Szenario szenario) {
		super(container, szenario);
		this.s = calculateS();
	}

	@Override
	public CompanyValues calculateCompanyValue() {
		CompanyValues companyValues = new CompanyValues();

		for (AbstractPeriodContainer i : container.getPeriodContainers()) {

			TreeSet<? extends PeriodInterface> periods = i.getPeriods();

			Iterator<? extends PeriodInterface> iter = periods
					.descendingIterator();

			boolean T = true;

			double companyValue = 0;
			double freeCashFlow = 0;
			double debitFreeCompany = 0;
			double taxBenefits = 0;
			while (iter.hasNext()) {
				PeriodInterface period = iter.next();

				if (T) {
					debitFreeCompany = calculateDebitFreeCompanyT(
							period.getFreeCashFlow(),
							szenario.getRateReturnEquity());
					taxBenefits = calculateTaxBenefitsT(s,
							szenario.getRateReturnCapitalStock(),
							period.getBorrowedCapital());

					freeCashFlow = period.getFreeCashFlow();
					T = false;

				} else {

					debitFreeCompany = calculateDebitFreeCompanyt(freeCashFlow,
							debitFreeCompany, szenario.getRateReturnEquity());
					taxBenefits = calculateTaxBenefitst(s,
							szenario.getRateReturnCapitalStock(),
							period.getBorrowedCapital(), taxBenefits);

					freeCashFlow = period.getFreeCashFlow();
				}

				companyValue = calculate(debitFreeCompany, taxBenefits,
						period.getBorrowedCapital());
			}

			companyValues.addCompanyValue(companyValue);
		}
		return companyValues;
	}

	private double calculateS() {
		return ((0.5 * szenario.getBusinessTax() * (1 - szenario
				.getCorporateAndSolitaryTax())) + szenario
				.getCorporateAndSolitaryTax());
	}

	private double calculate(double debitFreeCompany, double taxBenefits,
			double capitalStock) {
		return debitFreeCompany + taxBenefits - capitalStock;
	}

	private double calculateDebitFreeCompanyT(double freeCashFlow,
			double rateReturnEquity) {
		return (freeCashFlow / rateReturnEquity);
	}

	private double calculateTaxBenefitsT(double s,
			double rateReturnCapitalStock, double capitalStock) {
		return ((s * rateReturnCapitalStock * capitalStock) / rateReturnCapitalStock);
	}

	private double calculateDebitFreeCompanyt(double freeCashFlow,
			double debitFreeCompnay, double rateReturnEquity) {
		return (freeCashFlow + debitFreeCompnay) / (1 + rateReturnEquity);
	}

	private double calculateTaxBenefitst(double s,
			double rateReturnCapitalStock, double capitalStock,
			double taxBenefit) {
		return ((s * rateReturnCapitalStock * capitalStock + taxBenefit) / (1 + rateReturnCapitalStock));
	}

}
