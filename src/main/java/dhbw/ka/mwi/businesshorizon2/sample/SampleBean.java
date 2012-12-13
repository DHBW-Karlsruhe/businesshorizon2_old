package dhbw.ka.mwi.businesshorizon2.sample;



public class SampleBean implements SampleBeanInterface {
	private static final long serialVersionUID = 1L;

	protected String text;
	
	public String get() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}

}
