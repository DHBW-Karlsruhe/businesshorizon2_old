package dhbw.ka.mwi.businesshorizon2.models;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.NavigableSet;
import java.util.SortedSet;
import java.util.TreeSet;

public class Project {

	protected NavigableSet<Period> periods = new TreeSet<Period>();

	public NavigableSet<Period> getPeriods() {
		return periods;
	}

	public void setPeriods(NavigableSet<Period> periods) {
		this.periods = periods;
	}
	
	public List<Integer> getAvailableYears() {
		ArrayList<Integer> years = new ArrayList<Integer>();
		
		int start = Calendar.getInstance().get(Calendar.YEAR);
		boolean contains;
		
		for(int i = start; i > start - 5; i--) {
			contains = false;
			
			for(Period period : periods) {
				if(period.getYear() == i) {
					contains = true;
					break;
				}
			}
			
			if(!contains) {
				years.add(i);
			}
		}
		
		return years;
	}
	
}
