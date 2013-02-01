package dhbw.ka.mwi.businesshorizon2.ui.process.period.input;

import dhbw.ka.mwi.businesshorizon2.ui.process.contentcontainer.ContentView;

public interface InputViewInterface extends ContentView {

	public void addInputField(String string, double initialContent);
	public void addInputField(String string);

	public void setWrong(int column,int row, boolean b);


	public void initForm();

	void addHeader(int year);

}
