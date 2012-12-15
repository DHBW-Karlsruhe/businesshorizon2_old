package dhbw.ka.mwi.businesshorizon2.ui.method;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.mvplite.event.EventBus;
import com.mvplite.event.EventHandler;
import com.mvplite.presenter.Presenter;

import dhbw.ka.mwi.businesshorizon2.methods.Method;
import dhbw.ka.mwi.businesshorizon2.methods.MethodRunner;
import dhbw.ka.mwi.businesshorizon2.methods.Result;
import dhbw.ka.mwi.businesshorizon2.models.Project;

public class MethodPresenter extends Presenter<MethodView> {
	private static final long serialVersionUID = 1L;

	@Autowired
	private EventBus eventBus;
	
	@Autowired
	private Project project;
	
	private Method currentMethod;

	private MethodRunner methodRunner;
	
	@PostConstruct
	public void init() {
		eventBus.addHandler(this);
	}

	public Method getCurrentMethod() {
		return currentMethod;
	}

	public void setCurrentMethod(Method currentMethod) {
		this.currentMethod = currentMethod;
	}

	public void calculate() {
		getView().showProgress();
		
		methodRunner = new MethodRunner(currentMethod, project.getPeriods(), new MethodRunner.Callback() {
			@Override
			public void onProgressChange(float progress) {
				getView().setProgress(progress);
			}
			
			@Override
			public void onComplete(Result result) {
				getView().hideProgress();

				if(result != null) {
					getView().showResult(result);
				}
			}
		});
		methodRunner.start();
	}

	public void interruptCalculation() {
		if(methodRunner != null) {
			methodRunner.interrupt();
		}
		
		getView().hideProgress();
	}
	
	@EventHandler
	public void onShowMethod(ShowMethodEvent event) {
		currentMethod = event.getMethod();
		getView().showMethod(event.getMethod());
	}
	
}
