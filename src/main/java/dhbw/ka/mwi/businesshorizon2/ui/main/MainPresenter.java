package dhbw.ka.mwi.businesshorizon2.ui.main;

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
	
	@PostConstruct
	public void init() {
//		eventBus.addHandler(this);
	}

	public Set<Period> getPeriods() {
		return project.getPeriods();
	}
	
}
