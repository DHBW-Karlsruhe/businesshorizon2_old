package dhbw.ka.mwi.businesshorizon2.methods.wiener;

import java.util.SortedSet;

import dhbw.ka.mwi.businesshorizon2.methods.Method;
import dhbw.ka.mwi.businesshorizon2.methods.MethodRunner.Callback;
import dhbw.ka.mwi.businesshorizon2.methods.Result;
import dhbw.ka.mwi.businesshorizon2.models.Period;

public class Wiener extends Method {

	@Override
	public String getName() {
		return "Wiener-Ding";
	}

	@Override
	public int getOrderKey() {
		return 2;
	}

	@Override
	public Result calculate(SortedSet<Period> periods, Callback callback)
			throws InterruptedException {
		return new Result();
	}
}
