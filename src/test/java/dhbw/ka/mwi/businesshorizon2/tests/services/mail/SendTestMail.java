package dhbw.ka.mwi.businesshorizon2.tests.services.mail;

import static org.junit.Assert.*;
import junit.framework.TestCase;

import org.junit.Test;

import dhbw.ka.mwi.businesshorizon2.services.mail.SendMail;

public class SendTestMail extends TestCase{

	@Test
	public void testSendMail() {
		SendMail.sendMail("Tobias.Lindner@live.de", "Test", "TestNachricht");
		assertEquals(1, 1);
	}

}
