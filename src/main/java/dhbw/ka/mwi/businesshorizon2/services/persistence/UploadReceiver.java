/*******************************************************************************
 * BusinessHorizon2
 *
 * Copyright (C) 
 * 2012-2013 Christian Gahlert, Florian Stier, Kai Westerholz,
 * Timo Belz, Daniel Dengler, Katharina Huber, Christian Scherer, Julius Hacker
 * 2013-2014 Marcel Rosenberger, Mirko Göpfrich, Annika Weis, Katharina Narlock, 
 * Volker Meier
 * 
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
package dhbw.ka.mwi.businesshorizon2.services.persistence;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.mvplite.event.EventBus;
import com.vaadin.ui.Upload;
import com.vaadin.ui.Upload.Receiver;
import com.vaadin.ui.Upload.SucceededListener;

/**
 * Reciever Klasse zum Upload von Projektdateien, die importiert werden sollen.
 * Ist gleichzeitig auch als Listenerklasse für das Upload.SuceededEvent (Abschluss des Uploads) registriert.
 * 
 * @author Tobias Lindner
 *
 */
public class UploadReceiver implements Receiver, SucceededListener {
	
	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger("UploadReceiver.class");
	
	public File file;
	
	private EventBus eventBus;
	
	public UploadReceiver (EventBus eventBus) {
		this.eventBus = eventBus;
	}

	@Override
	public OutputStream receiveUpload(String filename, String mimeType) {

		FileOutputStream fos = null;
		
		try {
			file = new File (PersistenceService.TMPDIRECTORY + PersistenceService.separator + filename);
			fos = new FileOutputStream (file);
			
		} catch (FileNotFoundException e) {
			
		}
		
		return fos;
		
	}
	/**
	 * Methode behandelt das Upload.SucceededEvent.
	 * Es wirft das für den EventBus geeignete ImportUploadFinishedEvent.
	 * 
	 * @author Tobias Lindner
	 */
	public void uploadSucceeded(Upload.SucceededEvent event) {
		eventBus.fireEvent(new ImportUploadFinishedEvent(event.getFilename()));
		logger.debug("UploadSucceeded");
    }

}
