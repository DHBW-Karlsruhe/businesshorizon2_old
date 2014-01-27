package dhbw.ka.mwi.businesshorizon2.ui;

import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Label;

public class InfoButton extends Label {
	
	

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InfoButton(String beschreibung){
		super();
		this.setIcon(new ThemeResource("images/InfoButton.jpg"));
		this.setDescription(beschreibung);
		
	}
	
	

	
	
}
