package dhbw.ka.mwi.businesshorizon2.ui.methodlist;

import java.util.SortedSet;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.mvplite.event.EventBus;
import com.mvplite.event.EventHandler;
import com.mvplite.presenter.Presenter;

import dhbw.ka.mwi.businesshorizon2.methods.Method;

public class MethodListPresenter extends Presenter<MethodListView> {
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private EventBus eventBus;

	@Autowired
	private SortedSet<Method> methods;

	@PostConstruct
	public void init() {
		eventBus.addHandler(this);
	}
	
	@EventHandler
	public void onShowMethodList(ShowMethodListEvent event) {
		getView().setMethods(methods);
	}

}
