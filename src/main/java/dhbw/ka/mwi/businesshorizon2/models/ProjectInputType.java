package dhbw.ka.mwi.businesshorizon2.models;

/**
 * Diese Klasse enthält die Methoden die für ein Projekt ausgewählt sind.
 * 
 * @author Timo Belz
 * @param Methods
 *            enthält die Namen der ausgewählten Methoden
 * @param balanceSheet
 *            false steht für direkte Casfloweingabe, true steht für die Eingabe
 *            von Bilanzwerten
 * 
 */

public class ProjectInputType {

	/**
	 * 
	 */

	private Boolean stochastic = false;
	private Boolean deterministic = false;
	private InputType stochasticInput = InputType.DIRECT;
	private InputType deterministicInput = InputType.DIRECT;

	public Boolean getStochastic() {
		return stochastic;
	}

	public void setStochastic(Boolean stochastic) {
		this.stochastic = stochastic;
	}

	public Boolean getDeterministic() {
		return deterministic;
	}

	public void setDeterministic(Boolean deterministic) {
		this.deterministic = deterministic;
	}

	public InputType getStochasticInput() {
		return stochasticInput;
	}

	public void setStochasticInput(InputType stochasticInput) {
		this.stochasticInput = stochasticInput;
	}

	public InputType getDeterministicInput() {
		return deterministicInput;
	}

	public void setDeterministicInput(InputType deterministicInput) {
		this.deterministicInput = deterministicInput;
	}
}
