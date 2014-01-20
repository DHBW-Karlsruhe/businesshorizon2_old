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


package dhbw.ka.mwi.businesshorizon2.tests.methods.discountedCashflow;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.NavigableMap;
import java.util.TreeSet;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import dhbw.ka.mwi.businesshorizon2.methods.discountedCashflow.APV_alt;
import dhbw.ka.mwi.businesshorizon2.models.StochasticResultContainer;
import dhbw.ka.mwi.businesshorizon2.models.Szenario;
import dhbw.ka.mwi.businesshorizon2.models.CompanyValue.CompanyValueDeterministic;
import dhbw.ka.mwi.businesshorizon2.models.CompanyValue.CompanyValueDeterministic.Couple;
import dhbw.ka.mwi.businesshorizon2.models.Period.CashFlowPeriod;
import dhbw.ka.mwi.businesshorizon2.models.PeriodContainer.CashFlowPeriodContainer;

public class APVTest {

	private static CashFlowPeriod period1;
	private static CashFlowPeriod period2;
	private static CashFlowPeriod period3;
	private static CashFlowPeriod period4;
	private static CashFlowPeriod period5;
	private static CashFlowPeriod period6;

	private static CashFlowPeriodContainer container;

	private static StochasticResultContainer result;

	private static Szenario szenario;

	private static CompanyValueDeterministic compare;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

		period1 = new CashFlowPeriod(2013);
		period2 = new CashFlowPeriod(2014);
		period3 = new CashFlowPeriod(2015);
		period4 = new CashFlowPeriod(2016);
		period5 = new CashFlowPeriod(2017);
		period6 = new CashFlowPeriod(2018);

		period1.setFreeCashFlow(100);
		period1.setCapitalStock(200);

		period2.setFreeCashFlow(200);
		period2.setCapitalStock(300);

		period3.setFreeCashFlow(300);
		period3.setCapitalStock(400);

		period4.setFreeCashFlow(400);
		period4.setCapitalStock(500);

		period5.setFreeCashFlow(500);
		period5.setCapitalStock(600);

		period6.setFreeCashFlow(600);
		period6.setCapitalStock(700);

		container = new CashFlowPeriodContainer();

		container.addPeriod(period1);
		container.addPeriod(period2);
		container.addPeriod(period3);
		container.addPeriod(period4);
		container.addPeriod(period5);
		container.addPeriod(period6);

		TreeSet<CashFlowPeriodContainer> temp = new TreeSet<CashFlowPeriodContainer>();
		temp.add(container);

		result = new StochasticResultContainer(temp);
		szenario = new Szenario(14, 10, 3.5, 15, true);

		compare = new CompanyValueDeterministic();

		compare.addPeriod(2018, 3701.1268, 700, 600, 0, 0, 0, 0.1649, szenario);
		compare.addPeriod(2017, 3799.6279, 600, 500, 600, 4285.7143, 115.4125,
				0.1649, szenario);
		compare.addPeriod(2016, 3809.0472, 500, 400, 500, 4285.7143, 113.9136,
				0.1649, szenario);
		compare.addPeriod(2015, 3740.2809, 400, 300, 400, 4197.995, 111.0522,
				0.1649, szenario);
		compare.addPeriod(2014, 3602.8914, 300, 200, 300, 4033.3289, 106.952,
				0.1649, szenario);
		compare.addPeriod(2013, 3405.2701, 200, 100, 200, 3801.1657, 101.7257,
				0.1649, szenario);
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
	public void testCalculateCompanyValue() {
		APV_alt apv = new APV_alt(result, szenario);
		CompanyValueDeterministic companyValue = (CompanyValueDeterministic) apv
				.calculateCompanyValue();

		NavigableMap<Integer, Couple> map = companyValue.getCompanyValues();
		NavigableMap<Integer, Couple> mapCompare = compare.getCompanyValues();

		if (map.size() != mapCompare.size()) {
			fail("Die zwei Maps sind nicht gleich groß");
		}

		for (int i : map.descendingKeySet()) {
			System.out.println("******* " + map.get(i).toString());
			System.out.println("Compare " + mapCompare.get(i).toString());
			System.out.println();

			assertEquals(mapCompare.get(i).getYear(), map.get(i).getYear());

			assertEquals(mapCompare.get(i).getCompanyValue(), map.get(i)
					.getCompanyValue(), 0.00001);

			assertEquals(mapCompare.get(i).getCapitalStock(), map.get(i)
					.getCapitalStock(), 0.00001);

			assertEquals(mapCompare.get(i).getFreeCashFlow(), map.get(i)
					.getFreeCashFlow(), 0.00001);

			assertEquals(mapCompare.get(i).getFreeCashFlowT(), map.get(i)
					.getFreeCashFlowT(), 0.00001);

			assertEquals(mapCompare.get(i).getDebitFreeCompany(), map.get(i)
					.getDebitFreeCompany(), 0.00001);

			assertEquals(mapCompare.get(i).getTaxBenefits(), map.get(i)
					.getTaxBenefits(), 0.00001);

			assertEquals(mapCompare.get(i).getS(), map.get(i).getS(), 0.00001);
		}

	}

}
