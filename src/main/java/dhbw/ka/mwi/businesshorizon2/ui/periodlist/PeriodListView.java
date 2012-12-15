package dhbw.ka.mwi.businesshorizon2.ui.periodlist;

import java.util.Set;

import com.mvplite.view.View;

import dhbw.ka.mwi.businesshorizon2.models.Period;

public interface PeriodListView extends View {

	void setShowAddPeriodButton(boolean flag);

	void setPeriods(Set<Period> periods, Period selected);

}
