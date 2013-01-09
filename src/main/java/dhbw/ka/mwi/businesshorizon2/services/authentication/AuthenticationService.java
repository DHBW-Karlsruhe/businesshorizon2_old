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

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;

import dhbw.ka.mwi.businesshorizon2.models.User;

/**
 * Authentication Service der die Authentifizierung über eine einfache .dat
 * Datei durchführt. Die Klasse ist als Singleton implementiert, damit sie nur
 * einmal in der gesamten Applikation existiert.
 * 
 * @author Florian Stier
 */

public class AuthenticationService implements AuthenticationServiceInterface {

	/**
	 * Default Serial Version ID
	 */
	private static final long serialVersionUID = 1L;

	private File file;

	private static final String DIRECTORY = System.getProperty("user.home") + "\\Business Horizon";
	private static final String FILENAME = "C:\\Users\\Florian Stier\\Business Horizon\\users.dat";

	private List<User> allUsers;
	private Map<String, User> loggedInUsers;

	private static Logger logger = Logger.getLogger(AuthenticationService.class);

	/**
	 * Methode zur Initialisierung des AuthenticationService durch Spring
	 */
	@PostConstruct
	public void init() {

		file = new File(FILENAME);

		if (!file.exists()) {
			try {
				file.createNewFile();
				logger.debug("New file created at: " + file.getAbsolutePath());
			} catch (IOException e) {
				logger.error("Could not create a new file at: " + file.getAbsolutePath());
			}
		}

		initializeUserMaps();
		logger.debug("AuthenticationService successfully initialized");
	}

	/**
	 * Die Methode initialisiert die Map, in welcher alle existierenden User
	 * gespeichert werden. Außerdem wird eine zweite Map initialisiert, die nur
	 * User enthält, welche eingeloggt sind.
	 */
	private synchronized void initializeUserMaps() {

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
			logger.error("Initialization: An IOException occured: " + e.getMessage());
		} catch (ClassNotFoundException e) {
			logger.error("A ClassNotFoundException occured: " + e.getMessage());
		}

	}

	public synchronized User doLogin(String emailAdress, String password) throws UserNotFoundException,
			WrongPasswordException {

		for (User user : allUsers) {
			if (user.getEmailAdress().equals(emailAdress)) {
				if (user.getPassword().equals(password)) {
					loggedInUsers.put(user.getEmailAdress(), user);
					logger.debug("User " + emailAdress + " successfully logged in.");
					return user;
				} else {
					logger.debug("Wrong password for user " + emailAdress);
					throw new WrongPasswordException("Wrong password for user " + emailAdress + " submitted");
				}
			}
		}

		throw new UserNotFoundException("User " + emailAdress + " doesn't exist.");

	}

	public synchronized void doLogout(User user) throws UserNotLoggedInException {

		if (loggedInUsers.containsKey(user.getEmailAdress())) {
			loggedInUsers.remove(user.getEmailAdress());
			logger.debug("User " + user.getEmailAdress() + " successfully logged out.");
		} else {
			logger.error("User " + user.getEmailAdress() + " is already logged out.");
			throw new UserNotLoggedInException("The user " + user.getEmailAdress() + " is not logged in");
		}

	}

	/**
	 * Beim Registrieren eines neuen Users wird dieser zum einen zur Liste aller
	 * existierender User hinzugefügt und des Weiteren die Datei mit den
	 * Userinformationen neu geschrieben.
	 */
	public synchronized void registerNewUser(String emailAdress, String password, String firstName, String lastName,
			String company) {
		User user = new User(firstName, lastName, company, emailAdress, password);
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

	public synchronized void deleteUser(User user) {

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

	@Override
	public void forgotPassword(String email) {
		// DUMMY
	}

}
