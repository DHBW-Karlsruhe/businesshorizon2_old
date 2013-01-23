package dhbw.ka.mwi.businesshorizon2.models.CompanyValue;

import java.util.TreeMap;

/**
 * Diese Klasse speichert die Unternehmenswerte und ihre jeweiligen Häufigkeiten
 * in einer Map ab.
 * 
 * @author kathie
 * 
 */
public class CompanyValueStochastic extends CompanyValue {

	public class Couple {
		private final double companyValue;
		private int count;

		public Couple(double companyValue) {
			this.companyValue = companyValue;
			count = 1;
		}

		public Couple(double companyValue, int count) {
			this.companyValue = companyValue;
			this.count = count;
		}

		public double getCompanyValue() {
			return companyValue;
		}

		public int getCount() {
			return count;
		}

		private void increaseCount() {
			count++;
		}

		private void increaseCount(int count) {
			this.count = this.count + count;
		}
	}

	private final TreeMap<Double, Couple> map;

	public CompanyValueStochastic() {
		super();
		map = new TreeMap<>();
	}

	public void addCompanyValue(double companyValue) {

		companyValue = this.roundToDecimalPlaces(companyValue, 0, false);

		if (map.containsKey(companyValue)) {
			map.get(companyValue).increaseCount();
		} else {
			map.put(companyValue, new Couple(companyValue));
		}
	}

	public void addCompanyBalue(double companyValue, int count) {

		companyValue = this.roundToDecimalPlaces(companyValue, 0, false);

		if (map.containsKey(companyValue)) {
			map.get(companyValue).increaseCount(count);
		} else {
			map.put(companyValue, new Couple(companyValue, count));
		}
	}

	/**
	 * Gibt eine Map mit den einzelnen Unternehmenswerten und deren Häufigkeiten
	 * zurück. Die Map kann beispielsweise folgendermaßen ausgelesen werden:
	 * 
	 * <pre>
	 * {@code
	 * for (Map.Entry<Double, Couple> entry : map.entrySet()) {
	 * 			entry.getValue().getCompanyValue();
	 * 			entry.getValue().getCount();
	 * 		}
	 * </pre>
	 * 
	 * @return Ein Objekt der Klasse TreeMap mit den Unternehmenswerten und den
	 *         jeweiligen Häufigkeiten, wobei die TreeMap nach den
	 *         Unternehmenswerten sortiert ist.
	 */
	public TreeMap<Double, Couple> getCompanyValues() {
		return map;
	}
}
