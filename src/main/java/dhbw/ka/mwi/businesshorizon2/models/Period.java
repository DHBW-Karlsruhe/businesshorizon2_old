package dhbw.ka.mwi.businesshorizon2.models;

import java.io.Serializable;

public class Period implements Serializable, Comparable<Period> {
	private static final long serialVersionUID = 1L;
	
	private int year;

	public Period(int year) {
		this.year = year;
	}

	public int getYear() {
		return year;
	}

	@Override
	public String toString() {
		return String.valueOf(getYear());
	}

	@Override
	public int compareTo(Period o) {
		return o.getYear() - this.getYear();
	}
}
