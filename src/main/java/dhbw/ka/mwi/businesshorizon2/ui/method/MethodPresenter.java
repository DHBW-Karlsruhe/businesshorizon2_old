package dhbw.ka.mwi.businesshorizon2.ui.method;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.mvplite.presenter.Presenter;

import dhbw.ka.mwi.businesshorizon2.methods.Method;
import dhbw.ka.mwi.businesshorizon2.methods.MethodRunner;
import dhbw.ka.mwi.businesshorizon2.models.Project;

public class MethodPresenter extends Presenter<MethodView> {
	private static final long serialVersionUID = 1L;

	@Autowired
	private Project project;
	
	private Method currentMethod;

	private MethodRunner methodRunner;
	
	@PostConstruct
	public void init() {
		
	}

	public Method getCurrentMethod() {
		return currentMethod;
	}

	public void setCurrentMethod(Method currentMethod) {
		this.currentMethod = currentMethod;
	}

	public void calculate(MethodRunner.Callback callback) {
		methodRunner = new MethodRunner(currentMethod, project.getPeriods(), callback);
		methodRunner.start();
	}

	public void interruptCalculation() {
		if(methodRunner != null) {
			methodRunner.interrupt();
		}
	}
	
}
