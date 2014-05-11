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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;

import org.apache.log4j.Logger;

import dhbw.ka.mwi.businesshorizon2.models.User;

/**
 * Klasse zur Initialisierung einer einfachen .dat Datei zum Speichern von
 * Userdaten. Mit Hilfer dieser Klasse kann die Datei users.dat initial mit
 * Nutzerdaten gefüllt werden. Die Eingabe der Daten erfolgt Konsolengesteuert
 * 
 * @author Florian Stier
 * 
 */

public class AuthenticationDataInitializer {

	private static final Logger logger = Logger.getLogger(AuthenticationDataInitializer.class);

	private static File file;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			file = new File(System.getProperty("user.home") + "\\Business Horizon");
			file.mkdir();
			file = new File("C:\\Users\\Florian Stier\\Business Horizon\\users.dat");
			if (!file.exists()) {
				file.createNewFile();
			}
			FileOutputStream outputStream = new FileOutputStream(file);
			ObjectOutputStream objectOut = new ObjectOutputStream(outputStream);
			BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));

			if (file.length() != 0) { // wenn die Datei schon existiert, soll
										// sie neu geschrieben werden
				file.delete();
				file.createNewFile();
			}

			System.out.println("New File created");
			System.out.println("Insert number of users to create: ");
			int numberOfUsers = Integer.parseInt(stdIn.readLine());

			objectOut.writeInt(numberOfUsers);

			for (int i = 1; i <= numberOfUsers; i++) {

				System.out.println("Insert data for User " + i + ":");
				System.out.println("E-Mail address: ");
				String emailAdress = stdIn.readLine();
				System.out.println("Password: ");
				String password = stdIn.readLine();
				System.out.println("First Name: ");
				String firstName = stdIn.readLine();
				System.out.println("Last Name: ");
				String lastName = stdIn.readLine();
				System.out.println("Company: ");
				String company = stdIn.readLine();

				User user = new User(firstName, lastName, company, emailAdress, password);

				objectOut.writeObject(user);

			}

			System.out.println("Successfully initialized User data file");

			objectOut.close();
			outputStream.close();

		} catch (FileNotFoundException e) {
			logger.error("FileNotFoundException occured: The given file does not exist." + file.getAbsolutePath());
		} catch (IOException e) {
			logger.error("IOException occured: Could not create file." + file.getAbsolutePath());
		}
	}
}
