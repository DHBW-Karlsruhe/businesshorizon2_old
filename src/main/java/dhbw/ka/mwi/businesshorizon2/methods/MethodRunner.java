package dhbw.ka.mwi.businesshorizon2.methods;

import java.security.InvalidParameterException;
import java.util.HashSet;
import java.util.SortedSet;
import java.util.logging.Logger;

import dhbw.ka.mwi.businesshorizon2.models.Period;

public class MethodRunner extends Thread {

	public static interface Callback {
		public void onComplete(Result result);
		public void onProgressChange(float progress);
	}
	
	private Method method;
	
	private SortedSet<Period> periods;
	
	private Callback callback;
	
	private Result result = null;
	
	public MethodRunner(Method method, SortedSet<Period> periods, Callback callback) {
		if(method == null || periods == null || callback == null) {
			throw new InvalidParameterException("No null parameters are allowed here");
		}
		
		this.method = method;
		this.periods = periods;
		this.callback = callback;
	}

	@Override
	public void run() {
		try {
			result = method.calculate(periods, callback);
			
			callback.onComplete(result);
		} catch (InterruptedException e) {
			callback.onComplete(null);
		}
	}
}
