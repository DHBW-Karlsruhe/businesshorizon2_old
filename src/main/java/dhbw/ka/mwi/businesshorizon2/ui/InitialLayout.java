package dhbw.ka.mwi.businesshorizon2.ui;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.VerticalSplitPanel;
import com.vaadin.terminal.Sizeable;
import com.vaadin.terminal.ThemeResource;

/**
* Diese Klasse dient dazu das initiale Layout für die Anwendung zu erstellen.
* Über die Getter Methoden können einzelne Elemente geholt werden, um mit diesen in einer View zu arbeiten.
* Die Klasse dient der Wiederverwendbarkeit des Layouts.
*
* @author Marco Glaser
*
*/

public class InitialLayout {
	
	private VerticalSplitPanel verticalSplitPanel;

	private HorizontalSplitPanel horizontalSplitPanel;

	private HorizontalSplitPanel horizontalSplitPanelRight;

	private Label leftL;

	private Label rightTopL;

	private Label rightBottomL;

	private Label leftBottomL;

	private VerticalLayout mainLayout = new VerticalLayout();
	private VerticalLayout leftLayout = new VerticalLayout();
	private VerticalLayout rightLayout = new VerticalLayout();
	private HorizontalLayout topRightLayout = new HorizontalLayout();
	private VerticalLayout bottomLeftLayout = new VerticalLayout();
	private VerticalLayout bottomRightLayout = new VerticalLayout();
	private VerticalLayout bottomLayout = new VerticalLayout();
	
	
	/**
	 * Im Konstruktor wird das Layout erstellt.
	 * 
	 * @author Marco Glaser
	 * 
	 */
	public InitialLayout(){
		mainLayout.setSizeFull();
		mainLayout.setStyleName("mainLayout");
		leftLayout.setSizeFull();
		leftLayout.setStyleName("leftContainer");
		rightLayout.setSizeFull();
		bottomLayout.setSizeFull();
		bottomLeftLayout.setSizeFull();

		horizontalSplitPanel = new HorizontalSplitPanel();
		horizontalSplitPanel.setSplitPosition(30, com.vaadin.terminal.Sizeable.UNITS_PERCENTAGE);
		horizontalSplitPanel.setLocked(true);
		horizontalSplitPanel.setStyleName("horizontalMain");
		verticalSplitPanel = new VerticalSplitPanel();
		verticalSplitPanel.setSplitPosition(15, com.vaadin.terminal.Sizeable.UNITS_PERCENTAGE);
		verticalSplitPanel.setLocked(true);
		verticalSplitPanel.setWidth(90, com.vaadin.terminal.Sizeable.UNITS_PERCENTAGE);
		verticalSplitPanel.setHeight(100, com.vaadin.terminal.Sizeable.UNITS_PERCENTAGE);
		horizontalSplitPanelRight = new HorizontalSplitPanel();
		horizontalSplitPanelRight.setSplitPosition(30, com.vaadin.terminal.Sizeable.UNITS_PERCENTAGE);
		horizontalSplitPanelRight.setLocked(true);
		horizontalSplitPanelRight.addStyleName("horizontalBottom");
		horizontalSplitPanelRight.setHeight(90, com.vaadin.terminal.Sizeable.UNITS_PERCENTAGE);
		horizontalSplitPanelRight.setWidth(100, com.vaadin.terminal.Sizeable.UNITS_PERCENTAGE);
		
		leftL = new Label("links");
		rightTopL = new Label("rechts Oben");
		rightBottomL = new Label("rechts Unten");
		leftBottomL = new Label("links Unten");
		
		leftLayout.addComponent(leftL);
		rightLayout.addComponent(verticalSplitPanel);
		topRightLayout.addComponent(rightTopL);
		bottomRightLayout.addComponent(rightBottomL);
//		bottomLeftLayout.addComponent(leftBottomL);
		bottomLayout.addComponent(horizontalSplitPanelRight);
		
		horizontalSplitPanel.addComponent(leftLayout);
		horizontalSplitPanel.addComponent(rightLayout);
		
		verticalSplitPanel.addComponent(topRightLayout);
		verticalSplitPanel.addComponent(bottomLayout);
		
		horizontalSplitPanelRight.addComponent(bottomLeftLayout);
		horizontalSplitPanelRight.addComponent(bottomRightLayout);
		
		rightLayout.setComponentAlignment(verticalSplitPanel, Alignment.MIDDLE_CENTER);
		bottomLayout.setComponentAlignment(horizontalSplitPanelRight, Alignment.MIDDLE_CENTER);
		
		mainLayout.addComponent(horizontalSplitPanel);
	}
	
	/**
	 * Diese Methode sollte nach dem initialisieren der Klasse aufgerufen werden.
	 * Der Return-Parameter sollte dann in der View an die setContent-Methode übergeben werden.
	 * Somit wird das Layout als Inhalt der View gesetzt.
	 * 
	 * @return mainLayout Das erstellte Layout zum Anzeigen in der View
	 * 
	 * @author Marco Glaser
	 * 
	 */
	public VerticalLayout getLayoutComponent(){
		return mainLayout;
	}
	
	public VerticalLayout getBottomHorizontalLayout(){
		return bottomLeftLayout;
	}

}
