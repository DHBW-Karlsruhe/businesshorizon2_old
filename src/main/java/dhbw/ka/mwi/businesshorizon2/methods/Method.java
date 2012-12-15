package dhbw.ka.mwi.businesshorizon2.methods;

import java.io.Serializable;
import java.util.SortedSet;

import dhbw.ka.mwi.businesshorizon2.models.Period;

abstract public class Method implements Comparable<Method>, Serializable {
	
	abstract public String getName();
	
	abstract public int getOrderKey();
	
	abstract public Result calculate(SortedSet<Period> periods, MethodRunner.Callback callback) throws InterruptedException;
	
	
	@Override
	public int compareTo(Method o) {
		return this.getOrderKey() - o.getOrderKey();
	}
	
}
