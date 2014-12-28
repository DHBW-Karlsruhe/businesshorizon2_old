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
 * @author Florian Stier, Marcel Rosenberger, Annika Weis, Mirko Göpfrich
 */

public class AuthenticationService implements AuthenticationServiceInterface {

	/**
	 * Default Serial Version ID
	 */
	private static final long serialVersionUID = 1L;

	private File file;
	
	private static final String separator = System.getProperties().getProperty("file.separator");

	private static final String DIRECTORY = System.getProperty("user.home") + separator + separator + "Business Horizon";
	private static final String FILENAME = separator + separator + "users.dat";

	private List<User> allUsers;
	private Map<String, User> loggedInUsers;

	private static final Logger logger = Logger.getLogger(AuthenticationService.class);

	/**
	 * Methode zur Initialisierung des AuthenticationService durch Spring
	 */
	@PostConstruct
	public void init() {

		file = new File(DIRECTORY);

		if (!file.exists()) {
			file.mkdir();
			logger.debug("New directory created at: " + file.getAbsolutePath());
		}

		file = new File(DIRECTORY + FILENAME);

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
		if (allUsers != null) {
			for (User user : allUsers) {
				if (user.getEmailAdress().toUpperCase().equals(emailAdress.toUpperCase())) {
					if (user.getPassword() == password.hashCode()) {
						loggedInUsers.put(user.getEmailAdress(), user);
						logger.debug("User " + emailAdress + " successfully logged in.");
						return user;
					} else {
						logger.debug("Wrong password for user " + emailAdress);
						throw new WrongPasswordException("Wrong password for user " + emailAdress + " submitted");
					}
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
	 * 
	 * Zur Validierung der Anmeldedaten werden folgende Prüfungen durchgeführt:
	 * - Mailadresse noch nicht verwendet
	 * - Vorname gültig (Muss mit Großbuchstaben beginnen, nur Buchstaben und Trennzeichen erlaubt. Keine Accents o.ä. und maximal 20 Buchstaben lang.)
	 * - Nachname gültig (Muss mit Großbuchstaben beginnen, nur Buchstaben und Trennzeichen erlaubt. Keine Accents o.ä. und maximal 20 Buchstaben lang.)
	 * - Regex (regulärer Ausdruck) zum überprüfen der Mail-Adresse
	 * - Passwort zwischen 6-20 Zeichen, mind. 1 Zahl, Groß- und Kleinbuchstaben, mind. 1 Sonderzeichen
	 * 
	 * @throws UserAlreadyExistsException
	 * @throws InvalidMailAdressException 
	 * @throws InvalidLastNameException 
	 * @throws InvalidFirstNameException 
	 * @throws TrivialPasswordException 
	 */
	public synchronized void registerNewUser(String emailAdress, String password, String firstName, String lastName,
			String company) throws UserAlreadyExistsException {
		User user = new User(firstName, lastName, company, emailAdress, password);

		if (allUsers == null) {
			allUsers = new ArrayList<User>();
			loggedInUsers = new LinkedHashMap<String, User>();
		}
		
		//Prüfung ob Mailadresse bereits registriert wurde.
		for (User existingUser : allUsers) {
			if (emailAdress.equals(existingUser.getEmailAdress())) {
				throw new UserAlreadyExistsException("Ein Benutzer mit der Mail-Adresse " + emailAdress + " existiert bereits.");
			}
		}
		
		allUsers.add(user);

		
		try {

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
