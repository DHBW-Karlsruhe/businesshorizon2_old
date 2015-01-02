package dhbw.ka.mwi.businesshorizon2.ui.initialscreen.projectcreation;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.terminal.Sizeable;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class ProjectCreationViewImpl extends VerticalLayout implements ProjectCreationViewInterface{

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private ProjectCreationPresenter presenter;

	private TextField projectNameInput;

	private TextField projectDescriptionInput;

	private Label gap;
	
	private static final Logger logger = Logger.getLogger("ProjectCreationPresenter.class");
	
	@PostConstruct
	public void init(){
		presenter.setView(this);
		generateUi();
		logger.debug("Initialisierung beendet");
	}
	
	public void generateUi(){
		setWidth(95, UNITS_PERCENTAGE);
		setHeight(Sizeable.SIZE_UNDEFINED, 0);
		setStyleName("projectDetailsLayout");
		
		projectNameInput = new TextField("Projektname");
		projectDescriptionInput = new TextField("Projektbeschreibung");
		gap = new Label();
		
		projectNameInput.setWidth(80, UNITS_PERCENTAGE);
		projectNameInput.setHeight(SIZE_UNDEFINED, 0);
		projectNameInput.setStyleName("projectNameInput");
		projectDescriptionInput.setWidth(80, UNITS_PERCENTAGE);
		projectDescriptionInput.setHeight(300, UNITS_PIXELS);
		projectDescriptionInput.setStyleName("projectDescriptionInput");
		gap.setHeight("10px");
		
		projectNameInput.setValue("Geben sie hier den Projektname ein");
		projectDescriptionInput.setValue("Geben sie hier eine Beschreibung des Projekts ein");
		
		addComponent(projectNameInput);
		addComponent(gap);
		addComponent(projectDescriptionInput);
	}

}
