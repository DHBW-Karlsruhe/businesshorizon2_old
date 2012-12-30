package dhbw.ka.mwi.businesshorizon2.methods.timeseries;

import java.util.Random;

public class WhiteNoise {
	public int countOfNeededValues;
	public double deviation;
	public Random randomGenerator;

	public WhiteNoise(int count, double variance) {
		this.deviation = Math.sqrt(variance);
		this.countOfNeededValues = count;
		this.randomGenerator = new Random();
	}

	public double getWhiteNoiseValue() {
		return randomGenerator.nextGaussian() * deviation;
	}

	public static void main(String args[]) {
		WhiteNoise test = new WhiteNoise(100, 3.5);
		for (int i = 0; i <= 1000000; i++) {
			System.out.println(test.getWhiteNoiseValue());
		}
	}
}
