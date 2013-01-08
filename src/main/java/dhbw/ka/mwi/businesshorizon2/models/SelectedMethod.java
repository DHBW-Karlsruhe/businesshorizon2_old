package dhbw.ka.mwi.businesshorizon2.models;

import java.io.Serializable;
import java.util.SortedSet;

/**
 * Diese Klasse enthält die Methoden die für ein Projekt ausgewählt sind.
 * 
 * @author Timo Belz
 * @param Methods enthält die Namen der ausgewählten Methoden
 * @param balanceSheet false steht für direkte Casfloweingabe, true steht für die Eingabe von Bilanzwerten
 * 
 */

public class SelectedMethod implements Serializable {
	
	private Boolean stochastic;
	private SortedSet<String> Methods;
	private Boolean inputType;
	
	
	public Boolean getStochastic() {
		return stochastic;
	}

	public SortedSet<String> getMethodNames() {
		return Methods;
	}
	
	public void addMethod(String methodName) {
	//	TODO: Addmethode hinzufügen
	}
	
	public void removeMethod(String methodName) {
	//	TODO: Removemethode hinzufügen
	}

	public Boolean getInputType() {
		return inputType;
	}

	public void setInputType(Boolean inputType) {
		this.inputType = inputType;
	}

	

}
