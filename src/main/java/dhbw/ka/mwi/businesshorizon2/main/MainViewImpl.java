package dhbw.ka.mwi.businesshorizon2.main;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.mvplite.event.EventBus;
import com.mvplite.event.EventHandler;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;

public class MainViewImpl extends VerticalLayout implements MainView, Button.ClickListener {
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private EventBus eventBus;

	private Button btn;
	
	@PostConstruct
	public void init() {
		eventBus.addHandler(this);
		generateUi();
	}
	
	private void generateUi() {
		addComponent(new Label("Hello world"));
		btn = new Button("test", this);
		addComponent(btn);
	}
	
	@EventHandler
	public void onTestEvent(TestEvent event) {
		addComponent(new Label("click"));
	}

	@Override
	public void buttonClick(ClickEvent event) {
		if(event.getButton() == btn) {
			eventBus.fireEvent(new TestEvent());
		}
	}

}
