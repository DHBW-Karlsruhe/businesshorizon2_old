package dhbw.ka.mwi.businesshorizon2.methods.timeseries;

import java.util.Collection;
import java.util.SortedSet;

import com.google.gwt.dev.util.collect.HashSet;

import dhbw.ka.mwi.businesshorizon2.methods.Method;
import dhbw.ka.mwi.businesshorizon2.methods.MethodRunner;
import dhbw.ka.mwi.businesshorizon2.methods.Result;
import dhbw.ka.mwi.businesshorizon2.models.Period;

public class Timeseries extends Method {

	@Override
	public String getName() {
		return "Zeitreihenanalyse";
	}

	@Override
	public int getOrderKey() {
		return 1;
	}

	@Override
	public Result calculate(SortedSet<Period> periods, MethodRunner.Callback callback) throws InterruptedException {
		for(int i = 0; i <= 100; i++) {
			Thread.sleep(100);
			callback.onProgressChange((float) i / 100);
		}
		
		return new Result();
	}

}
