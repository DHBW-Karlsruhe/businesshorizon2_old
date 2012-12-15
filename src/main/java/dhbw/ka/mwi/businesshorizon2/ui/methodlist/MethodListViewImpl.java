package dhbw.ka.mwi.businesshorizon2.ui.methodlist;

import java.util.SortedSet;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.mvplite.event.EventBus;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

import dhbw.ka.mwi.businesshorizon2.methods.Method;
import dhbw.ka.mwi.businesshorizon2.ui.method.ShowMethodEvent;

public class MethodListViewImpl extends VerticalLayout implements MethodListView {
	private static final long serialVersionUID = 1L;

	@Autowired
	private EventBus eventBus;
	
	@Autowired
	private MethodListPresenter presenter;

	private VerticalLayout methodPanel;
	
	@PostConstruct
	public void init() {
		presenter.setView(this);
		generateUi();
	}

	private void generateUi() {
		setSpacing(true);
		setMargin(true);

		addComponent(new Label("Berechnungsmethoden"));
		
		methodPanel = new VerticalLayout();
		methodPanel.setSpacing(true);
		addComponent(methodPanel);
	}

	@Override
	public void setMethods(SortedSet<Method> methods) {
		methodPanel.removeAllComponents();
		
		for(final Method method : methods) {
			Button btn = new Button(method.getName());
			btn.setSizeFull();
			
			btn.addListener(new Button.ClickListener() {
				private static final long serialVersionUID = 1L;

				@Override
				public void buttonClick(ClickEvent event) {
					eventBus.fireEvent(new ShowMethodEvent(method));
				}
			});
			
			methodPanel.addComponent(btn);
		}
	}
}
