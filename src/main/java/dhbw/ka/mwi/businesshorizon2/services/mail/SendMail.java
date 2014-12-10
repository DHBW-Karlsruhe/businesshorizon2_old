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

package dhbw.ka.mwi.businesshorizon2.services.mail;

import java.util.Properties;

import org.apache.log4j.Logger;

import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;


/**
 * Diese Klasse stellt Methoden zum Versenden von E-Mails zur Verfügung.
 * 
 * @author Tobias Lindner
 *
 */

public class SendMail {

	private static final Logger logger = Logger.getLogger(SendMail.class);
	
	public static void sendMail (String empfaenger, String betreff, String msgText) {
		 // Empfänger MailAdresse
	      String to = empfaenger;

	      // Sender MailAdresse
	      String from = "localhost@wouf2014-2015.dhbw-karlsruhe.de";

	      // Host von dem gesendet wird
	      String host = "localhost";

	      // Auslesen der Systemseigenschaften
	      Properties properties = System.getProperties();

	      // Setup mail server
	      properties.setProperty("mail.smtp.host", host);

	      // Setzen der Standard Session
	      Session session = Session.getDefaultInstance(properties);

	      try{
	         // Erstellen des Standard Objects
	         MimeMessage message = new MimeMessage(session);

	         // Absender im Header setzen
	         message.setFrom(new InternetAddress(from));

	         // Empfänger im Header setzen
	         message.addRecipient(Message.RecipientType.TO,
	                                  new InternetAddress(to));

	         // Setzen des Betreffs
	         message.setSubject(betreff);

	         // Setzen des Nachrichtentextes
	         message.setText(msgText);

	         // Senden der Nachricht
	         Transport.send(message);
	         logger.debug("Mail versendet...");
	         
	      }catch (MessagingException mex) {
	         mex.printStackTrace();
	      }

	}
}
