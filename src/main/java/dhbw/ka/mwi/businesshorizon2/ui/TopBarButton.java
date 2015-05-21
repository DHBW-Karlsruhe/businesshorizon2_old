/*******************************************************************************
 * BusinessHorizon2
 *
 * Copyright (C) 
 * 2014-2015 Marco Glaser, Tobias Lindner
 *
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package dhbw.ka.mwi.businesshorizon2.ui;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

/**
 * Diese Klasse stellt einen Button für die Buttonleiste oben in der Anwendung dar.
 * Dadurch wird sichergestellt, dass jeder Knopf die gleichen Maße hat und gleich aussieht.
 * Das Bild lässt sich über den Konstruktor festsetzen.
 * 
 * @author Marco Glaser
 *
 */
public class TopBarButton extends VerticalLayout{

	private static final long serialVersionUID = 1L;
	
	private Button button;
	private Label label;
	private VerticalLayout labelLayout;
	private Label gap;
	
	/**
	 * Der Konstruktor erstellt die UI Komponenten und fügt sie zum Layout hinzu.
	 * Der styleName wird dem Button als Styleklasse übergeben. Also muss diese Styleklasse
	 * zusätzlich in der CSS-Datei der Anwendung angelegt werden. In dieser Klasse sollte
	 * das Hintergrundbild des Buttons festgelegt werden.
	 * Sollte ein Text zwei Zeilen benötigen, muss der Verwender die erste Zeile als sLabel übergeben
	 * und die zweite Zeile über die Methode addLabel.
	 * 
	 * @param styleName
	 * : Name der Styleklasse für den Button
	 * @param sLabel
	 * : Text, der unter dem Button steht
	 * 
	 * @author Marco Glaser
	 */
	public TopBarButton(String styleName, String sLabel){
		setWidth(150, com.vaadin.terminal.Sizeable.UNITS_PIXELS);
		setHeight(80, com.vaadin.terminal.Sizeable.UNITS_PIXELS);
		setStyleName("topBarButtonContainer");
		button = new Button();
		button.setWidth(30, com.vaadin.terminal.Sizeable.UNITS_PIXELS);
		button.setHeight(30, com.vaadin.terminal.Sizeable.UNITS_PIXELS);
		button.setStyleName(styleName);
		gap = new Label();
		gap.setHeight("5px");
		label = new Label(sLabel);
		label.setStyleName("topBarButtonLabel");
		label.setSizeUndefined();
		labelLayout = new VerticalLayout();
		labelLayout.setHeight(45, com.vaadin.terminal.Sizeable.UNITS_PIXELS);
		labelLayout.setWidth(100, UNITS_PERCENTAGE);
		addComponent(button);
		labelLayout.addComponent(label);
		addComponent(gap);
		addComponent(labelLayout);
		setComponentAlignment(button, Alignment.TOP_CENTER);
		labelLayout.setComponentAlignment(label, Alignment.MIDDLE_CENTER);
	}
	
	/**
	 * Diese Methode setzt die übergebene Breite in Pixeln für den Button.
	 * Notwendig, wenn das Bild des Buttons keine gleiche Höhe und Breite hat.
	 * 
	 * @param width
	 * : Breite des Buttons
	 * 
	 * @author Marco Glaser
	 */
	public TopBarButton setButtonWidth(float width){
		button.setWidth(width, UNITS_PIXELS);
		return this;
	}
	
	/**
	 * Gibt die konkrete Button Komponente zurück.
	 * 
	 * @return button : Die Komponente des Buttons
	 * 
	 * @author Marco Glaser
	 */
	public Button getButtonComponent(){
		return button;
	}
	
	/**
	 * Fügt eine zusätzliche Zeile Text als Label unter dem Button hinzu
	 * 
	 * @param sLabel
	 * : Text für das Label
	 * 
	 * @author Marco Glaser
	 */
	public void addLabel(String sLabel){
		Label secondLabel = new Label(sLabel);
		secondLabel.setStyleName("topBarButtonLabel");
		secondLabel.setSizeUndefined();
		labelLayout.addComponent(secondLabel);
		labelLayout.setComponentAlignment(secondLabel, Alignment.MIDDLE_CENTER);
	}
	
	public Button getButton() {
		return button;
	}

}
