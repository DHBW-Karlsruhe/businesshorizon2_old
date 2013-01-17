package dhbw.ka.mwi.businesshorizon2.ui.process.period.input.direkt;

import java.util.Arrays;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Form;
import com.vaadin.ui.VerticalLayout;

import dhbw.ka.mwi.businesshorizon2.models.Period.CashFlowPeriod;
import dhbw.ka.mwi.businesshorizon2.ui.process.period.input.periodFormFactory;

/**
 * Diese Klasse implementiert das GUI fuer den Prozessschritt "Methoden" in Vaadin.
 * 
 * @author Julius Hacker
 *
 */
public class DirektViewImpl extends VerticalLayout implements DirektViewInterface {
	private static final long serialVersionUID = 1L;

	@Autowired
	private DirektPresenter presenter;
	
	private VerticalLayout panel = new VerticalLayout();
	
	private Logger logger = Logger.getLogger(DirektViewImpl.class);
	

	/**
	 * Dies ist der Konstruktor, der von Spring nach der Initialierung der Dependencies 
	 * aufgerufen wird. Er registriert sich selbst beim Presenter und initialisiert die 
	 * View-Komponenten.
	 * 
	 * @author Daniel Dengler
	 */
	@PostConstruct
	public void init() {
		presenter.setView(this);
		generateUi();
	}

	/**
	 * Erstelle das GUI zum zur Eingabe
	 * 
	 * @author Daniel Dengler
	 */
	private void generateUi() {
		
	}

	@Override
	public void setForm(CashFlowPeriod period) {
		this.removeAllComponents();
		panel.removeAllComponents();
		this.addComponent(panel);
		
		BeanItem<CashFlowPeriod> periodItem = new BeanItem<CashFlowPeriod>(period);
		final Form periodForm = new Form();
		periodForm.setCaption("Direkte Eingabe");
		
		periodForm.setInvalidCommitted(false);
		periodForm.setWriteThrough(true);
		
		periodForm.setFormFieldFactory(new periodFormFactory());
		periodForm.setItemDataSource(periodItem);
		
		for(Object i :periodItem.getItemPropertyIds())
			logger.debug(i.toString());
		
		periodForm.setVisibleItemProperties(Arrays.asList(new String[] {
				"freeCashFlow","borrowedCapital"
		}));
		panel.addComponent(periodForm);
		logger.debug("Form hinzugefügt");
		
		 Button apply = new Button("Übernehmen", new Button.ClickListener() {
	            public void buttonClick(ClickEvent event) {
	                try {
	                	periodForm.commit();
	                } catch (Exception e) {
	                    // Ignored, we'll let the Form handle the errors
	                }
	            }
	        });
		 
		 periodForm.getFooter().addComponent(apply);
		 logger.debug("Button erstellt und hinzugefügt");
		
	}
}
