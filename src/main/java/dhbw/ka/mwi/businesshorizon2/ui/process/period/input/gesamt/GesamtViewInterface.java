package dhbw.ka.mwi.businesshorizon2.ui.process.period.input.gesamt;

import dhbw.ka.mwi.businesshorizon2.models.Period.AggregateCostMethodPeriod;
import dhbw.ka.mwi.businesshorizon2.ui.process.contentcontainer.ContentView;

public interface GesamtViewInterface extends ContentView {


	public void addInputField(String string, double initialContent);

	public void setWrong(int column,int row, boolean b);


	public void initForm();
	

}