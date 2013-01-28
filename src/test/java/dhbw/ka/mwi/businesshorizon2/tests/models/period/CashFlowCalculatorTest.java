/*******************************************************************************
 * BusinessHorizon2
 * 
 *     Copyright (C) 2012-2013  Christian Gahlert, Florian Stier, Kai Westerholz,
 *     Timo Belz, Daniel Dengler, Katharina Huber, Christian Scherer, Julius Hacker
 * 
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Affero General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 * 
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Affero General Public License for more details.
 * 
 *     You should have received a copy of the GNU Affero General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/


package dhbw.ka.mwi.businesshorizon2.tests.models.period;

import static org.junit.Assert.assertEquals;

import java.util.TreeSet;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import dhbw.ka.mwi.businesshorizon2.models.StochasticResultContainer;
import dhbw.ka.mwi.businesshorizon2.models.Szenario;
import dhbw.ka.mwi.businesshorizon2.models.Period.AggregateCostMethodPeriod;
import dhbw.ka.mwi.businesshorizon2.models.Period.CashFlowCalculator;
import dhbw.ka.mwi.businesshorizon2.models.PeriodContainer.AbstractPeriodContainer;
import dhbw.ka.mwi.businesshorizon2.models.PeriodContainer.AggregateCostMethodBalanceSheetPeriodContainer;

public class CashFlowCalculatorTest {

	private static StochasticResultContainer result;
	private static AggregateCostMethodBalanceSheetPeriodContainer container;
	private static AggregateCostMethodPeriod period1;
	private static AggregateCostMethodPeriod period2;
	private static Szenario szenario;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		period1 = new AggregateCostMethodPeriod(2009);
		period1.setCapitalStock(11513000000.0);
		period1.setCashAssets(1598000000.0);
		period1.setClaims(7948000000.0);
		period1.setEquity(11991000000.0);
		period1.setFinancialValue(11733000000.0);
		period1.setHumanCapitalCosts(4286000000.0);
		period1.setImmaterialFortune(315000000.0);
		period1.setInterestAndOtherCosts(5000000.0);
		period1.setInternallyProducedAndCapitalizedAssets(57000000.0);
		period1.setMaterialCosts(9138000000.0);
		period1.setOtherBusinessCosts(3447000000.0);
		period1.setOtherBusinessRevenue(2463000000.0);
		period1.setPropertyValue(2341000000.0);
		period1.setProvisions(6173000000.0);
		period1.setSalesRevenue(15111000000.0);
		period1.setStocks(4908000000.0);
		period1.setSuplies(8390000000.0);
		period1.setWriteDowns(724000000.0);

		period2 = new AggregateCostMethodPeriod(2010);
		period2.setCapitalStock(12613000000.0);
		period2.setCashAssets(2072000000.0);
		period2.setClaims(6306000000.0);
		period2.setEquity(12319000000.0);
		period2.setFinancialValue(12418000000.0);
		period2.setHumanCapitalCosts(4726000000.0);
		period2.setImmaterialFortune(314000000.0);
		period2.setInterestAndOtherCosts(280000000.0);
		period2.setInternallyProducedAndCapitalizedAssets(172000000);
		period2.setMaterialCosts(11592000000.0);
		period2.setOtherBusinessCosts(3889000000.0);
		period2.setOtherBusinessRevenue(2498000000.0);
		period2.setPropertyValue(2437000000.0);
		period2.setProvisions(6356000000.0);
		period2.setSalesRevenue(19218000000.0);
		period2.setStocks(6681000000.0);
		period2.setSuplies(1050000000.0);
		period2.setWriteDowns(706000000.0);

		container = new AggregateCostMethodBalanceSheetPeriodContainer();
		container.addPeriod(period1);
		container.addPeriod(period2);

		TreeSet<AbstractPeriodContainer> tree = new TreeSet<>();
		tree.add(container);

		result = new StochasticResultContainer(tree);

		szenario = new Szenario(0, 0, 0.035, 0.15, false);

	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCalculateCashflows() {
		CashFlowCalculator.calculateCashflows(result, szenario);
		assertEquals(-1746026250, period2.getFreeCashFlow(), 0.00001);
	}

}
