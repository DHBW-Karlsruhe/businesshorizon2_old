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

import dhbw.ka.mwi.businesshorizon2.methods.AbstractDeterministicMethod;
import dhbw.ka.mwi.businesshorizon2.methods.AbstractStochasticMethod;
import dhbw.ka.mwi.businesshorizon2.models.StochasticResultContainer;
import dhbw.ka.mwi.businesshorizon2.models.Szenario;
import dhbw.ka.mwi.businesshorizon2.models.CompanyValue.CompanyValue;
import dhbw.ka.mwi.businesshorizon2.models.Period.CashFlowCalculator;

/**
 * Diese Klasse stellt die Oberklasse für Bewertungsmethoden der errechnet
 * CashFlows dar.
 * 
 * @author Kai Westerholz
 * 
 */

public abstract class RatingMethods{
	protected StochasticResultContainer container;
	protected Szenario szenario;

	public RatingMethods(StochasticResultContainer container, Szenario szenario) {
		this.container = container;
		this.szenario = szenario;

		CashFlowCalculator.calculateCashflows(container, szenario);
	}
	
	protected double getRateReturnEquity(){
		return szenario.getRateReturnEquity() / 100;
	}
	
	protected double getRateReturnCapitalStock(){
		return szenario.getRateReturnCapitalStock() / 100;
	}
	
	protected double getBusinessTax(){
		return szenario.getBusinessTax() / 100;
	}
	
	protected double getCorporateAndSolitaryTax(){
		return szenario.getCorporateAndSolitaryTax() / 100;
	}

	public abstract CompanyValue calculateCompanyValue();
}
