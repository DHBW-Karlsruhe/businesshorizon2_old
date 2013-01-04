package dhbw.ka.mwi.businesshorizon2.tests.ui.periodlist.assets;

import java.util.List;
import java.util.NavigableSet;

import dhbw.ka.mwi.businesshorizon2.models.Period;
import dhbw.ka.mwi.businesshorizon2.ui.periodlist.PeriodListViewInteface;

public class PeriodListViewMock implements PeriodListViewInteface {

	private NavigableSet<Period> periods;
	private Period selected;

	@Override
	public void setShowAddPeriodButton(boolean flag) {
		// TODO Auto-generated method stub
	}

	@Override
	public void setShowRemovePeriodButton(boolean flag) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setPeriods(NavigableSet<Period> periods, Period selected) {
		this.periods = periods;
		this.selected = selected;
	}

	@Override
	public void setAvailableYears(List<Integer> availableYears) {
		// TODO Auto-generated method stub

	}
	
	public NavigableSet<Period> getPeriods() {
		return periods;
	}

	public Period getSelected() {
		return selected;
	}
	
	

}
