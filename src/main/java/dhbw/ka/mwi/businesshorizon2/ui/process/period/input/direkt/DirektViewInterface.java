package dhbw.ka.mwi.businesshorizon2.ui.process.period.input.direkt;

import dhbw.ka.mwi.businesshorizon2.ui.process.contentcontainer.ContentView;

public interface DirektViewInterface extends ContentView {
	

	public void addInputField(String string, double initialContent);

	public void setWrong(int column,int row, boolean b);


	public void initForm();

	void addHeader(int year);

}