package dhbw.ka.mwi.businesshorizon2.tests.ui.periodlist;


import javax.annotation.PostConstruct;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import dhbw.ka.mwi.businesshorizon2.tests.ui.AbstractPresenterTestCase;
import dhbw.ka.mwi.businesshorizon2.tests.ui.periodlist.assets.PeriodListViewMock;
import dhbw.ka.mwi.businesshorizon2.ui.periodlist.PeriodAddEvent;
import dhbw.ka.mwi.businesshorizon2.ui.periodlist.PeriodListPresenter;



public class PeriodListPresenterTest extends AbstractPresenterTestCase {

	@Autowired
	private PeriodListPresenter presenter;
	
	@Autowired
	private PeriodListViewMock view;
	
	@PostConstruct
	public void initPresenter() {
		presenter.setView(view);
	}
	
	@Test
	public void testUpdateButtons() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddPeriod() {
		presenter.addPeriod(2012);
		
		assertNotNull(view.getPeriods());
		assertTrue(view.getPeriods().size() == 1);
		assertNotNull(view.getSelected());
		assertEquals(2012, view.getPeriods().first().getYear());
		
		assertEventFired(PeriodAddEvent.class);
		assertEquals(view.getPeriods().first(), getEvent(PeriodAddEvent.class).getPeriod());
	}

	@Test
	public void testRemovePeriod() {
		fail("Not yet implemented");
	}

	@Test
	public void testSelectPeriod() {
		fail("Not yet implemented");
	}

	@Test
	public void testOnShowPeriodList() {
		fail("Not yet implemented");
	}

	@Test
	public void testOnShowMethod() {
		fail("Not yet implemented");
	}

}
