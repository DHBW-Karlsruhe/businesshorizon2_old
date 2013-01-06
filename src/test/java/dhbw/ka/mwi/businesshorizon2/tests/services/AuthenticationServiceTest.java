package dhbw.ka.mwi.businesshorizon2.tests.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import dhbw.ka.mwi.businesshorizon2.models.User;
import dhbw.ka.mwi.businesshorizon2.services.authentication.AuthenticationService;
import dhbw.ka.mwi.businesshorizon2.services.authentication.UserNotFoundException;
import dhbw.ka.mwi.businesshorizon2.services.authentication.UserNotLoggedInException;

public class AuthenticationServiceTest {

	AuthenticationService authenticationService;

	@Before
	public void setUp() throws Exception {
		authenticationService = AuthenticationService.getInstance();
	}

	@Test
	public void testInitializeService() {
		assertNotNull(authenticationService);
	}

	@Test
	public void testDoLoginWithValidUserData() {
		User testUser;
		try {
			// Der User mit den entsprechenden Login Daten muss zurückgeliefert
			// werden
			testUser = authenticationService.doLogin("test@testuser.com", "Initial1");
			assertNotNull(testUser);
			// später müssen User erst angelegt werden, Test User muss nicht
			// immer vorhanden sein
			assertEquals(testUser.getFirstName(), "Test");
			assertEquals(testUser.getLastName(), "User");

			// Der User muss der Liste eingeloggter User hinzugefügt worden sein

			Field privateLoggedInUsers = authenticationService.getClass().getDeclaredField("loggedInUsers");

			privateLoggedInUsers.setAccessible(true);

			Map<String, User> loggedInUsers = (Map<String, User>) privateLoggedInUsers.get(authenticationService);

			User loggedInUser = loggedInUsers.get("test@testuser.com");
			assertNotNull(loggedInUser);
			assertEquals(loggedInUser.getFirstName(), "Test");
			assertEquals(loggedInUser.getLastName(), "User");

		} catch (UserNotFoundException e) {
			fail(e.getMessage());
		} catch (SecurityException e) {
			fail(e.getMessage());
		} catch (IllegalArgumentException e) {
			fail(e.getMessage());
		} catch (NoSuchFieldException e) {
			fail(e.getMessage());
		} catch (IllegalAccessException e) {
			fail(e.getMessage());
		}

	}

	@Test
	public void testDoLoginWithInvalidUserData() {
		User testUser;
		try {
			// Der User mit den entsprechenden Login Daten muss zurückgeliefert
			// werden
			testUser = authenticationService.doLogin("abc@def.gh", "Initial1");

			// Der User muss der Liste eingeloggter User hinzugefügt worden sein

			Field privateLoggedInUsers = authenticationService.getClass().getDeclaredField("loggedInUsers");

			privateLoggedInUsers.setAccessible(true);

			Map<String, User> loggedInUsers = (Map<String, User>) privateLoggedInUsers.get(authenticationService);

			User loggedInUser = loggedInUsers.get("test@testuser.com");
			assertNull(loggedInUser);

		} catch (UserNotFoundException e) {

		} catch (SecurityException e) {
			fail(e.getMessage());
		} catch (IllegalArgumentException e) {
			fail(e.getMessage());
		} catch (NoSuchFieldException e) {
			fail(e.getMessage());
		} catch (IllegalAccessException e) {
			fail(e.getMessage());
		}

	}

	@Test
	public void testDoLogout() {
		try {
			authenticationService.doLogin("test@testuser.com", "Initial1");

			Field privateLoggedInUsers = authenticationService.getClass().getDeclaredField("loggedInUsers");

			privateLoggedInUsers.setAccessible(true);

			Map<String, User> loggedInUsers = (Map<String, User>) privateLoggedInUsers.get(authenticationService);

			User loggedInUser = loggedInUsers.get("test@testuser.com");
			assertNotNull(loggedInUser);
			assertEquals(loggedInUser.getFirstName(), "Test");
			assertEquals(loggedInUser.getLastName(), "User");

			authenticationService.doLogout(new User("test@testuser.com", "Initial1", "Test", "User"));

			loggedInUser = loggedInUsers.get("test@testuser.com");
			assertNull(loggedInUser);

		} catch (UserNotFoundException e) {
			fail(e.getMessage());
		} catch (NoSuchFieldException e) {
			fail(e.getMessage());
		} catch (SecurityException e) {
			fail(e.getMessage());
		} catch (IllegalArgumentException e) {
			fail(e.getMessage());
		} catch (IllegalAccessException e) {
			fail(e.getMessage());
		} catch (UserNotLoggedInException e) {
			fail(e.getMessage());
		}

	}

	@Test
	public void testDoLogoutWithAlreadyLoggedOutUser() {
		try {
			Field privateLoggedInUsers = authenticationService.getClass().getDeclaredField("loggedInUsers");

			privateLoggedInUsers.setAccessible(true);

			Map<String, User> loggedInUsers = (Map<String, User>) privateLoggedInUsers.get(authenticationService);

			authenticationService.doLogout(new User("test@testuser.com", "Initial1", "Test", "User"));

			User loggedInUser = loggedInUsers.get("test@testuser.com");
			assertNull(loggedInUser);

		} catch (NoSuchFieldException e) {
			fail(e.getMessage());
		} catch (SecurityException e) {
			fail(e.getMessage());
		} catch (IllegalArgumentException e) {
			fail(e.getMessage());
		} catch (IllegalAccessException e) {
			fail(e.getMessage());
		} catch (UserNotLoggedInException e) {

		}

	}

	@Test
	public void testRegisterNewUser() {
		try {
			authenticationService.registerNewUser("james.bond@mi6.com", "Initial123", "James", "Bond");

			Field privateAllUsers = authenticationService.getClass().getDeclaredField("allUsers");
			privateAllUsers.setAccessible(true);

			List<User> allUsers = (List<User>) privateAllUsers.get(authenticationService);

			assertNotNull(allUsers);

			// TODO Test, ob das File auch wirklich aktualisiert wird

		} catch (NoSuchFieldException e) {
			fail(e.getMessage());
		} catch (SecurityException e) {
			fail(e.getMessage());
		} catch (IllegalArgumentException e) {
			fail(e.getMessage());
		} catch (IllegalAccessException e) {
			fail(e.getMessage());
		}

	}

}
