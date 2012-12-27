package dhbw.ka.mwi.businesshorizon2.services.authentication;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

import org.apache.log4j.Logger;

import dhbw.ka.mwi.businesshorizon2.models.User;

public class AuthenticationService implements AuthenticationServiceInterface {

	private static AuthenticationService INSTANCE;

	private static Logger logger = Logger.getLogger(AuthenticationServiceInterface.class);

	/**
	 * Authentication Service der die Authentifizierung über eine einfache .dat
	 * Datei durchführt. Die Klasse ist als Singleton implementiert, damit sie
	 * nur einmal in der gesamten Applikation existiert.
	 * 
	 * @author Florian Stier
	 */

	/**
	 * privater standard Konstruktor zur Realisierung des Singletons
	 */
	private AuthenticationService() {
		super();
	}

	/**
	 * Liefert die Singleton Instanz des Authentication Service zurück
	 * 
	 * @return AuthenticationService Instanz
	 */
	public static AuthenticationService getInstance() {
		if (INSTANCE != null) {
			return INSTANCE;
		} else {
			INSTANCE = new AuthenticationService();
			logger.debug("New AuthenticationService instance initialized");
		}
		return INSTANCE;

	}

	public User doLogin(String username, String password) throws UserNotFoundException {
		FileInputStream fileInput;
		ObjectInputStream userInput;

		try {

			File file = new File("users.dat");
			fileInput = new FileInputStream(file);
			userInput = new ObjectInputStream(fileInput);

			int nrOfUsers = userInput.readInt();

			for (int i = 1; i <= nrOfUsers; i++) {
				User user = (User) userInput.readObject();
				if (user.getUsername() == username && user.getPassword() == password) {
					fileInput.close();
					userInput.close();
					return user;
				}
			}

			fileInput.close();
			userInput.close();

			throw new UserNotFoundException("User " + username + " doesn't exist");

		} catch (FileNotFoundException e) {
			logger.error("The specified file could not be found");
		} catch (IOException e) {
			logger.error("An IOException occured: " + e.getMessage());
		} catch (ClassNotFoundException e) {
			logger.error("A ClassNotFoundException occured: " + e.getMessage());
		}

		return null;

	}

	public void doLogout(User user) {

	}

	public void registerNewUser(String username, String password, String firstName, String lastName) {

	}

	@Override
	public void deleteUser(User user) {

	}

}
