package dhbw.ka.mwi.businesshorizon2.ui.process.period.input;

import java.util.ArrayList;

import com.mvplite.event.Event;

public class WrongFieldsEvent extends Event {
	ArrayList<String> wrongFields;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public WrongFieldsEvent(ArrayList<String> wrongFields) {
		this.wrongFields = wrongFields;
	}
	/**
	 * @return the wrongFields
	 */
	public ArrayList<String> getWrongFields() {
		return wrongFields;
	}

	/**
	 * @param wrongFields the wrongFields to set
	 */
	public void setWrongFields(ArrayList<String> wrongFields) {
		this.wrongFields = wrongFields;
	}

}
