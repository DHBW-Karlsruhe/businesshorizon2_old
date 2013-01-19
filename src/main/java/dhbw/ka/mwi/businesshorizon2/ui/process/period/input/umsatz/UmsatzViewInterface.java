package dhbw.ka.mwi.businesshorizon2.ui.process.period.input.umsatz;

import dhbw.ka.mwi.businesshorizon2.models.Period.CostOfSalesMethodPeriod;
import dhbw.ka.mwi.businesshorizon2.ui.process.contentcontainer.ContentView;

public interface UmsatzViewInterface extends ContentView {
	

	public void addInputField(String string, double initialContent);

	public void setWrong(int column,int row, boolean b);


	public void initForm();
}