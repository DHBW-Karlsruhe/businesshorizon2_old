package dhbw.ka.mwi.businesshorizon2.main;

import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.mvplite.event.EventBus;
import com.mvplite.presenter.Presenter;

import dhbw.ka.mwi.businesshorizon2.models.Period;
import dhbw.ka.mwi.businesshorizon2.models.Project;

public class MainPresenter extends Presenter<MainView>{
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private EventBus eventBus;
	
	@Autowired
	private Project project;
	
	private MainView view;
	
	@PostConstruct
	public void init() {
//		eventBus.addHandler(this);
	}

	public void setView(MainView view) {
		this.view = view;
	}

	public Set<Period> getPeriods() {
		return project.getPeriods();
	}
	
}
