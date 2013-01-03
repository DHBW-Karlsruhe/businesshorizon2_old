package dhbw.ka.mwi.businesshorizon2.services.authentication;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import dhbw.ka.mwi.businesshorizon2.models.User;

public class AuthenticationService implements AuthenticationServiceInterface {

	private static AuthenticationService INSTANCE;

	private File file;
	private static final String FILENAME = "users.dat";

	private List<User> allUsers;
	private Map<String, User> loggedInUsers;

	private static Logger logger = Logger.getLogger(AuthenticationServiceInterface.class);

	/**
	 * Authentication Service der die Authentifizierung über eine einfache .dat
	 * Datei durchführt. Die Klasse ist als Singleton implementiert, damit sie
	 * nur einmal in der gesamten Applikation existiert.
	 * 
	 * @author Florian Stier
	 */

	/**
	 * Singleton Konstruktor.
	 */
	private AuthenticationService() {
		super();

		file = new File(FILENAME);

		initializeUserMaps();
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

	/**
	 * Die Methode initialisiert die Map, in welcher alle existierenden User
	 * gespeichert werden. Außerdem wird eine zweite Map initialisiert, die nur
	 * User enthält, welche eingeloggt sind.
	 */
	private void initializeUserMaps() {

		FileInputStream fileInput;
		ObjectInputStream userInput;

		try {
			fileInput = new FileInputStream(file);
			userInput = new ObjectInputStream(fileInput);

			int nrOfUsers = userInput.readInt();

			allUsers = new ArrayList<User>();

			for (int i = 1; i <= nrOfUsers; i++) {
				User user = (User) userInput.readObject();
				allUsers.add(user);
			}

			fileInput.close();
			userInput.close();

			loggedInUsers = new LinkedHashMap<String, User>();

		} catch (FileNotFoundException e) {
			logger.error("The specified file could not be found");
		} catch (IOException e) {
			logger.error("An IOException occured: " + e.getMessage());
		} catch (ClassNotFoundException e) {
			logger.error("A ClassNotFoundException occured: " + e.getMessage());
		}

	}

	public User doLogin(String username, String password) throws UserNotFoundException {

		for (User user : allUsers) {
			if (user.getUsername().equals(username)) {
				loggedInUsers.put(user.getUsername(), user);
				logger.debug("User " + username + " successfully logged in.");
				return user;
			}
		}

		throw new UserNotFoundException("User " + username + " doesn't exist.");

	}

	public void doLogout(User user) throws UserNotLoggedInException {

		if (loggedInUsers.containsKey(user.getUsername())) {
			loggedInUsers.remove(user.getUsername());
			logger.debug("User " + user.getUsername() + " successfully logged out.");
		} else {
			logger.error("User " + user.getUsername() + " is already logged out.");
			throw new UserNotLoggedInException("The user " + user.getUsername() + " is not logged in");
		}

	}

	/**
	 * Beim Registrieren eines neuen Users wird dieser zum einen zur Liste aller
	 * existierender User hinzugefügt und des Weiteren die Datei mit den
	 * Userinformationen neu geschrieben.
	 */
	public void registerNewUser(String username, String password, String firstName, String lastName) {
		User user = new User(username, password, firstName, lastName);
		allUsers.add(user);

		try {
			// eventuell muss file vorher gelöscht werden???
			FileOutputStream fileOutput = new FileOutputStream(file);
			ObjectOutputStream objectOutput = new ObjectOutputStream(fileOutput);

			objectOutput.writeInt(allUsers.size());
			for (User userToSave : allUsers) {
				objectOutput.writeObject(userToSave);
			}

			fileOutput.close();
			objectOutput.close();

		} catch (IOException e) {
			logger.error("An IOException occured: " + e.getMessage());
		}
	}

	public void deleteUser(User user) {

		allUsers.remove(user);

		try {
			// eventuell muss file vorher gelöscht werden???
			FileOutputStream fileOutput = new FileOutputStream(file);
			ObjectOutputStream objectOutput = new ObjectOutputStream(fileOutput);

			objectOutput.writeInt(allUsers.size());
			for (User userToSave : allUsers) {
				objectOutput.writeObject(userToSave);
			}

			fileOutput.close();
			objectOutput.close();

		} catch (IOException e) {
			logger.error("An IOException occured: " + e.getMessage());
		}
	}

}
