package dhbw.ka.mwi.businesshorizon2.methods.timeseries;

public class LinearRegression {
	
	public LinearRegression(){
		
	}

	public double[] getLinearRegressionParameters(double[] t, double[] y){
		double[] parameters = new double[2];
		double sumT = 0;
		double sumY = 0;
		double sumYT = 0;
		double sumT2 = 0;
		for (int i = 0; i<t.length; i++){
			sumT += t[i];
			sumY += y[i];
			sumYT += (y[i]*t[i]);
			sumT2 += (t[i]*t[i]);	
		}
		parameters[1] = ((t.length*sumYT) - (sumY*sumT))/((t.length*sumT2)-(sumT*sumT));
		
		parameters[0] = (sumY/t.length) - (parameters[1]*(sumT/t.length));
		
		return parameters;
		
		
	}
}
