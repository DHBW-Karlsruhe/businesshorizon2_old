package dhbw.ka.mwi.businesshorizon2.periodedit;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.mvplite.event.EventBus;
import com.mvplite.event.EventHandler;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import dhbw.ka.mwi.businesshorizon2.models.Period;

public class PeriodEditViewImpl extends VerticalLayout implements PeriodEditView {
	private static final long serialVersionUID = 1L;

	@Autowired
	private EventBus eventBus;
	
	@Autowired 
	private PeriodEditPresenter presenter;

	private TextField cashFlowText;

	private TextField companyValueText;

	private Label titleLabel;

	@PostConstruct
	public void init() {
		presenter.setView(this);
		eventBus.addHandler(this);
		generateUi();
	}
	
	private void generateUi() {
		setSpacing(true);
		setMargin(true);
		
		titleLabel = new Label();
		addComponent(titleLabel);
		
		cashFlowText = new TextField("Cash flow:");
		cashFlowText.setWidth(200, UNITS_PIXELS);
		addComponent(cashFlowText);
		
		companyValueText = new TextField("Cash flow:");
		companyValueText.setWidth(200, UNITS_PIXELS);
		addComponent(companyValueText);
	}
	
	@EventHandler
	public void onShowPeriodEdit(ShowPeriodEditEvent event) {
		BeanItem<Period> item = new BeanItem<Period>(event.getPeriod());

		titleLabel.setValue("Geschäftsjahr " + event.getPeriod().getYear());
		
		cashFlowText.setPropertyDataSource(item.getItemProperty("cashFlow"));
		companyValueText.setPropertyDataSource(item.getItemProperty("companyValue"));
	}

	
}
