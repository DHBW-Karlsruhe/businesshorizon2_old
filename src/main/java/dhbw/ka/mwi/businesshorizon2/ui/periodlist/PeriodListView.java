package dhbw.ka.mwi.businesshorizon2.ui.periodlist;

import java.util.List;
import java.util.NavigableSet;
import java.util.Set;
import java.util.SortedSet;

import com.mvplite.view.View;

import dhbw.ka.mwi.businesshorizon2.models.Period;

public interface PeriodListView extends View {

	public void setShowAddPeriodButton(boolean flag);

	public void setPeriods(NavigableSet<Period> periods, Period selected);

	public void setAvailableYears(List<Integer> availableYears);

}
