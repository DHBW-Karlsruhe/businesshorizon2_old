package dhbw.ka.mwi.businesshorizon2.periodlist;

import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.mvplite.event.EventBus;
import com.vaadin.data.Container;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import dhbw.ka.mwi.businesshorizon2.models.Period;
import dhbw.ka.mwi.businesshorizon2.periodedit.ShowPeriodEditEvent;

public class PeriodListViewImpl extends VerticalLayout implements PeriodListView, Button.ClickListener, Property.ValueChangeListener {
	private static final long serialVersionUID = 1L;

	@Autowired
	private PeriodListPresenter presenter;
	
	@Autowired
	private EventBus eventBus;

	private ListSelect listSelect;

	private Button addPeriodBtn;

	private Button removePeriodBtn;

	@PostConstruct
	public void init() {
		presenter.setView(this);
		generateUi();
	}
	
	private void generateUi() {
		setSizeFull();
		setSpacing(true);
		setMargin(true);

		Label titleLabel = new Label("Perioden");
		addComponent(titleLabel);
		setExpandRatio(titleLabel, 0);
		
		listSelect = new ListSelect();
		listSelect.setImmediate(true);
		listSelect.setSizeFull();
		listSelect.setNullSelectionAllowed(false);
		listSelect.addListener(this);
		addComponent(listSelect);
		setExpandRatio(listSelect, 1);
		

		HorizontalLayout buttonLayout = new HorizontalLayout();
		buttonLayout.setSpacing(true);
		addComponent(buttonLayout);
		setExpandRatio(buttonLayout, 0);
		
		addPeriodBtn = new Button("Periode hinzufügen", this);
		buttonLayout.addComponent(addPeriodBtn);
		
		removePeriodBtn = new Button("Periode entfernen", this);
		buttonLayout.addComponent(removePeriodBtn);
	}

	@Override
	public void buttonClick(ClickEvent event) {
		if(event.getButton() == addPeriodBtn) {
			final Window addDialog = new Window("Periode hinzufügen");
			addDialog.setModal(true);
			addDialog.setWidth(300, UNITS_PIXELS);
			addDialog.setResizable(false);
			addDialog.setDraggable(false);
			
			HorizontalLayout layout = new HorizontalLayout();
			layout.setSpacing(true);
			layout.addComponent(new Label("Bitte Jahr wählen: "));
			addDialog.addComponent(layout);
			
			List<Integer> years = presenter.getAvailableYears();
			final NativeSelect yearSelect = new NativeSelect(null, years);
			yearSelect.setNullSelectionAllowed(false);
			yearSelect.setValue((int) years.get(0));
			layout.addComponent(yearSelect);
			
			final Button addBtn = new Button("Hinzufügen", new Button.ClickListener() {
				private static final long serialVersionUID = 1L;

				@Override
				public void buttonClick(ClickEvent event) {
					presenter.addPeriod((int) yearSelect.getValue());
					getWindow().removeWindow(addDialog);
				}
			});
			layout.addComponent(addBtn);
			
			getWindow().addWindow(addDialog);
		} else if(event.getButton() == removePeriodBtn) {
			presenter.removePeriod((Period) listSelect.getValue());
		}
	}

	@Override
	public void setShowAddPeriodButton(boolean flag) {
		addPeriodBtn.setEnabled(flag);
	}

	@Override
	public void valueChange(ValueChangeEvent event) {
		if(listSelect.getValue() != null) {
			eventBus.fireEvent(new ShowPeriodEditEvent((Period) listSelect.getValue()));
			removePeriodBtn.setEnabled(true);
		} else {
			removePeriodBtn.setEnabled(false);
		}
	}

	@Override
	public void setPeriods(Set<Period> periods, Period selected) {
		final Container c = new IndexedContainer();
        for (Period period : periods) {
            c.addItem(period);
        }
        listSelect.setContainerDataSource(c);
        listSelect.select(selected);
	}
}
