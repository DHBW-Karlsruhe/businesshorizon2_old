package dhbw.ka.mw.businesshorizon2.tests.services.mail;

import static org.junit.Assert.*;

import org.junit.Test;

import dhbw.ka.mwi.businesshorizon2.services.mail.SendMail;

public class SendTestMail {

	@Test
	public void testSendMail() {
		SendMail.sendMail("Tobias.Lindner@live.de", "Test", "TestNachricht");
	}

}
