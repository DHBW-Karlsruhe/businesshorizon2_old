package dhbw.ka.mwi.businesshorizon2.ui.method;

import com.mvplite.view.View;

import dhbw.ka.mwi.businesshorizon2.methods.Method;
import dhbw.ka.mwi.businesshorizon2.methods.Result;

public interface MethodView extends View {

	public void showMethod(Method method);
	
	public void showProgress();

	public void setProgress(float progress);

	public void hideProgress();

	public void showResult(Result result);

}
