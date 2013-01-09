package dhbw.ka.mwi.businesshorizon2.models;

import java.util.SortedSet;

public class PeriodenContainer<T extends Period> {

	private SortedSet<T> perioden;

	public PeriodenContainer(SortedSet<T> perioden) {
		this.perioden = perioden;
	}

	public SortedSet<T> getPeriods() {
		return perioden;
	}

}
