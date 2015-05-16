package dhbw.ka.mwi.businesshorizon2.methods.timeseries;

/**
 * Berechnung der Modellparameter für die Zeitreihenanalyse
 * @author felix
 *
 */
public class LinearRegression {
	
	public LinearRegression(){
		
	}

	public double[] getLinearRegressionParameters(double[] t, double[] y){
		
		//Array mit den Modellparametern 
		double[] parameters = new double[2];
		
		//Summe aller T 
		double sumT = 0;
		
		//Summe aller Y
		double sumY = 0;
		
		//Summe aller Y*T
		double sumYT = 0;
		
		//Summe aller T*T
		double sumT2 = 0;
		
		//Summieren der übergebenen Werte
		for (int i = 0; i<t.length; i++){
			sumT += t[i];
			sumY += y[i];
			sumYT += (y[i]*t[i]);
			sumT2 += (t[i]*t[i]);	
		}
		//Parameter B berechnenen
		parameters[1] = ((t.length*sumYT) - (sumY*sumT))/((t.length*sumT2)-(sumT*sumT));
		
		//Parameter A berechnen
		parameters[0] = (sumY/t.length) - (parameters[1]*(sumT/t.length));
		
		return parameters;
		
		
	}
}
