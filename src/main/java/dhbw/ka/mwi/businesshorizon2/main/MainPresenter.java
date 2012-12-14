package dhbw.ka.mwi.businesshorizon2.main;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.mvplite.event.EventBus;

public class MainPresenter {

	@Autowired
	private EventBus eventBus;
	
	@Autowired
	private MainView view;
	
	@PostConstruct
	public void init() {
//		eventBus.addHandler(this);
	}

	public MainView getView() {
		return view;
	}
	
}
