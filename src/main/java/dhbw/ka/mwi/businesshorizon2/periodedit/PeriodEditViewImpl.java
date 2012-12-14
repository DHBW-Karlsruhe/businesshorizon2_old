package dhbw.ka.mwi.businesshorizon2.periodedit;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.mvplite.event.EventBus;
import com.mvplite.event.EventHandler;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class PeriodEditViewImpl extends VerticalLayout implements PeriodEditView {
	private static final long serialVersionUID = 1L;

	@Autowired
	private EventBus eventBus;
	
	@Autowired 
	private PeriodEditPresenter presenter;

	private TextField testText;
	
	@PostConstruct
	public void init() {
		presenter.setView(this);
		eventBus.addHandler(this);
		generateUi();
	}
	
	private void generateUi() {
		setSizeFull();
		
		testText = new TextField();
		testText.setWidth(100, UNITS_PERCENTAGE);
		addComponent(testText);
	}
	
	@EventHandler
	public void onShowPeriodEdit(ShowPeriodEditEvent event) {
		testText.setValue(event.getPeriod().toString());
		presenter.setCurrentPeriod(event.getPeriod());
	}

	
}
