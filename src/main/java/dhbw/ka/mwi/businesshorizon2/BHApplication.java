/*
 * Copyright 2009 IT Mill Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package dhbw.ka.mwi.businesshorizon2;

import com.vaadin.Application;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Window;

/**
 * Das ist die Haupt-Einstiegsklasse der Anwendung. Es ist vergleichbar mit der statischen main()-
 * Methode in normalen Java-Anwendungen.
 * 
 * @author Christian Gahlert
 */
@SuppressWarnings("serial")
public class BHApplication extends Application
{
    private Window window;

    /**
     * Diese Methode ist dafuer verantwortlich, das Haupt-Fenster zu laden und den gemanagten Windows
     * hinzuzufuegen.
     * 
     * @author Christian Gahlert
     */
    @Override
    public void init()
    {
        window = new Window("Business Horizon 2");
        setMainWindow(window);
        Button button = new Button("Click Me");
        button.addListener(new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {
                window.addComponent(new Label("Thank you for clicking!!!!"));
            }
        });
        window.addComponent(button);
        
    }
    
}
