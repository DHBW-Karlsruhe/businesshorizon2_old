package dhbw.ka.mwi.businesshorizon2.models;

public enum InputType {
	DIRECT("Direkte Eingabe"),REVENUE("Umsatzkostenverfahren"),TOTAL("Gesamtkostenverfahren");
	
	private String caption;
	

	private InputType(String caption){
		this.caption = caption;
	}
	
	public String getCaption() {
		return caption;
	}
	
	@Override
	public String toString(){
		
		return this.getCaption();
	}

}
