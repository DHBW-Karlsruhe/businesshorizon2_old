/*******************************************************************************
 * BusinessHorizon2
 *
 * Copyright (C) 
 * 2012-2013 Christian Gahlert, Florian Stier, Kai Westerholz,
 * Timo Belz, Daniel Dengler, Katharina Huber, Christian Scherer, Julius Hacker
 * 2013-2014 Marcel Rosenberger, Mirko Göpfrich, Annika Weis, Katharina Narlock, 
 * Volker Meier
 * 
 *
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/


package dhbw.ka.mwi.businesshorizon2.methods.discountedCashflow;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeSet;

import dhbw.ka.mwi.businesshorizon2.models.StochasticResultContainer;
import dhbw.ka.mwi.businesshorizon2.models.Szenario;
import dhbw.ka.mwi.businesshorizon2.models.CompanyValue.CompanyValue;
import dhbw.ka.mwi.businesshorizon2.models.CompanyValue.CompanyValueDeterministic;
import dhbw.ka.mwi.businesshorizon2.models.CompanyValue.CompanyValueStochastic;
import dhbw.ka.mwi.businesshorizon2.models.CompanyValue.CompanyValueStochastic.Couple;
import dhbw.ka.mwi.businesshorizon2.models.Period.Period;
import dhbw.ka.mwi.businesshorizon2.models.PeriodContainer.AbstractPeriodContainer;

/**
 * 
 * @author kathie
 * 
 */
public class APV_alt extends RatingMethods {

	private final double s;

	public APV_alt(StochasticResultContainer container, Szenario szenario) {
		super(container, szenario);
		this.s = calculateS();
	}

	@Override
	public CompanyValue calculateCompanyValue() {

		CompanyValue companyValueObject;

		if (container.getPeriodContainers() == null || container.getPeriodContainers().size() == 0) {
			companyValueObject = null;
			return companyValueObject;

		} else if (container.getPeriodContainers().size() == 1) {
			companyValueObject = new CompanyValueDeterministic();
			calculateCompanyValue(companyValueObject);
			return (CompanyValue)companyValueObject;
		} else {
			companyValueObject = new CompanyValueStochastic();
			calculateCompanyValue(companyValueObject);
			
			Map.Entry<Double, Couple> first =((CompanyValueStochastic)companyValueObject).getCompanyValues().firstEntry();
			Map.Entry<Double, Couple> last = ((CompanyValueStochastic)companyValueObject).getCompanyValues().lastEntry();
			
			double delta = last.getValue().getCompanyValue() - first.getValue().getCompanyValue();
			delta = delta / 25;
			
			double limit = first.getValue().getCompanyValue() + delta;
			
			double average = 0;
			
			int count = 0;
			
			CompanyValueStochastic result = new CompanyValueStochastic();
			
			for (Map.Entry<Double, Couple> entry : ((CompanyValueStochastic)companyValueObject).getCompanyValues().entrySet()){
				if (entry.getValue().getCompanyValue() <= limit){
					average = average + entry.getValue().getCompanyValue() * entry.getValue().getCount();
					count = count  + entry.getValue().getCount();
				}else{
					result.addCompanyBalue(average/count, count);
					limit = entry.getValue().getCompanyValue() + delta;
					average = 0;
					count = 0;
				}
			}
			return (CompanyValue)result;
		}
	}

	/*
	 * private void calculateCompanyValueStochastic( CompanyValueStochastic
	 * companyValues) {
	 * 
	 * for (AbstractPeriodContainer i : container.getPeriodContainers()) {
	 * 
	 * TreeSet<? extends PeriodInterface> periods = i.getPeriods();
	 * 
	 * Iterator<? extends PeriodInterface> iter = periods .descendingIterator();
	 * 
	 * boolean T = true;
	 * 
	 * double companyValue = 0; double freeCashFlow = 0; double debitFreeCompany
	 * = 0; double taxBenefits = 0; while (iter.hasNext()) { PeriodInterface
	 * period = iter.next();
	 * 
	 * if (T) { debitFreeCompany = calculateDebitFreeCompanyT(
	 * period.getFreeCashFlow(), szenario.getRateReturnEquity()); taxBenefits =
	 * calculateTaxBenefitsT(s, szenario.getRateReturnCapitalStock(),
	 * period.getBorrowedCapital());
	 * 
	 * freeCashFlow = period.getFreeCashFlow(); T = false;
	 * 
	 * } else {
	 * 
	 * debitFreeCompany = calculateDebitFreeCompanyt(freeCashFlow,
	 * debitFreeCompany, szenario.getRateReturnEquity()); taxBenefits =
	 * calculateTaxBenefitst(s, szenario.getRateReturnCapitalStock(),
	 * period.getBorrowedCapital(), taxBenefits);
	 * 
	 * freeCashFlow = period.getFreeCashFlow(); }
	 * 
	 * companyValue = calculate(debitFreeCompany, taxBenefits,
	 * period.getBorrowedCapital()); }
	 * 
	 * companyValues.addCompanyValue(companyValue); }
	 * 
	 * }
	 */

	private void calculateCompanyValue(CompanyValue companyValueObject) {

		for (AbstractPeriodContainer i : container.getPeriodContainers()) {

			TreeSet<? extends Period> periods = i.getPeriods();

			Iterator<? extends Period> iter = periods.descendingIterator();

			boolean T = true;

			double companyValue = 0;
			double freeCashFlow = 0;
			double freeCashFlowT = 0;
			double debitFreeCompany = 0;
			double debitFreeCompanyT = 0;
			double taxBenefits = 0;
			double taxBenefitsT = 0;
			while (iter.hasNext()) {
				Period period = iter.next();

				if (T) {

					freeCashFlow = period.getFreeCashFlow();

					debitFreeCompany = calculateDebitFreeCompanyT(period.getFreeCashFlow(),
							this.getRateReturnEquity());

					taxBenefits = calculateTaxBenefitsT(s,
							this.getRateReturnCapitalStock(),period.getCapitalStock());

					T = false;

				} else {

					freeCashFlow = period.getFreeCashFlow();

					debitFreeCompany = calculateDebitFreeCompanyt(freeCashFlowT, debitFreeCompany,
							this.getRateReturnEquity());

					taxBenefits = calculateTaxBenefitst(s,
							this.getRateReturnCapitalStock(),
							period.getCapitalStock(), taxBenefits);

				}

				companyValue = calculate(debitFreeCompany, taxBenefits,
						period.getCapitalStock());

				if (companyValueObject instanceof CompanyValueDeterministic) {

					((CompanyValueDeterministic) companyValueObject).addPeriod(
							period.getYear(), companyValue,
							period.getCapitalStock(),
							period.getFreeCashFlow(), freeCashFlowT,
							debitFreeCompanyT, taxBenefitsT, this.s, szenario);
				}

				freeCashFlowT = freeCashFlow;
				debitFreeCompanyT = debitFreeCompany;
				taxBenefitsT = taxBenefits;
			}

			if (companyValueObject instanceof CompanyValueStochastic) {
				((CompanyValueStochastic) companyValueObject).addCompanyValue(companyValue);
			}

		}

	}

	private double calculateS() {
		return ((0.5 * this.getBusinessTax() * (1 - this.getCorporateAndSolitaryTax())) + this
				.getCorporateAndSolitaryTax());
	}

	private double calculate(double debitFreeCompany, double taxBenefits, double capitalStock) {
		return debitFreeCompany + taxBenefits - capitalStock;
	}

	private double calculateDebitFreeCompanyT(double freeCashFlow, double rateReturnEquity) {
		return (freeCashFlow / rateReturnEquity);
	}

	private double calculateTaxBenefitsT(double s, double rateReturnCapitalStock, double capitalStock) {
		return ((s * rateReturnCapitalStock * capitalStock) / rateReturnCapitalStock);
	}

	private double calculateDebitFreeCompanyt(double freeCashFlow, double debitFreeCompnay, double rateReturnEquity) {
		return (freeCashFlow + debitFreeCompnay) / (1 + rateReturnEquity);
	}

	private double calculateTaxBenefitst(double s, double rateReturnCapitalStock, double capitalStock, double taxBenefit) {
		return ((s * rateReturnCapitalStock * capitalStock + taxBenefit) / (1 + rateReturnCapitalStock));
	}
}
