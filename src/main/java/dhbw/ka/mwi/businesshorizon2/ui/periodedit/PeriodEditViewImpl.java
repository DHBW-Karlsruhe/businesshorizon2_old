package dhbw.ka.mwi.businesshorizon2.ui.periodedit;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import dhbw.ka.mwi.businesshorizon2.models.Period;

/**
 * Dies ist die Vaadin-Implementierung der PeriodEditView.
 * 
 * @author Christian Gahlert
 *
 */
public class PeriodEditViewImpl extends VerticalLayout implements PeriodEditView {
	private static final long serialVersionUID = 1L;

	@Autowired 
	private PeriodEditPresenter presenter;

	private TextField cashFlowText;

	private TextField companyValueText;

	private Label titleLabel;

	/**
	 * Dies ist der Konstruktor, der von Spring nach der Initialierung der Dependencies 
	 * aufgerufen wird. Er registriert sich selbst beim Presenter und initialisiert die 
	 * View-Komponenten.
	 * 
	 * @author Christian Gahlert
	 */
	@PostConstruct
	public void init() {
		presenter.setView(this);
		generateUi();
	}
	
	/**
	 * Es wird ein einfaches Formular mit 2 Textfeldern erstellt.
	 * 
	 * @author Christian Gahlert
	 */
	private void generateUi() {
		setSpacing(true);
		setMargin(true);
		
		titleLabel = new Label();
		addComponent(titleLabel);
		
		cashFlowText = new TextField("Cash flow:");
		cashFlowText.setWidth(200, UNITS_PIXELS);
		addComponent(cashFlowText);
		
		companyValueText = new TextField("Bilanzwert FK:");
		companyValueText.setWidth(200, UNITS_PIXELS);
		addComponent(companyValueText);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * In diesem Fall wird die Period mit Hilfe von Data-Binding an den Wert der Textfelder 
	 * gebunden.
	 * 
	 * @author Christian Gahlert
	 */
	@Override
	public void setPeriod(Period period) {
		BeanItem<Period> item = new BeanItem<Period>(period);

		titleLabel.setValue("Geschäftsjahr " + period.getYear());
		
		cashFlowText.setPropertyDataSource(item.getItemProperty("cashFlow"));
		companyValueText.setPropertyDataSource(item.getItemProperty("companyValue"));
	}

}
