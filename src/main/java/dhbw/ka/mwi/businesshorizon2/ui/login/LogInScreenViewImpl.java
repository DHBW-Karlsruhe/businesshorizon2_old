/*******************************************************************************
 * BusinessHorizon2
 * 
 *     Copyright (C) 2012-2013  Christian Gahlert, Florian Stier, Kai Westerholz,
 *     Timo Belz, Daniel Dengler, Katharina Huber, Christian Scherer, Julius Hacker
 * 
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Affero General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 * 
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Affero General Public License for more details.
 * 
 *     You should have received a copy of the GNU Affero General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/


package dhbw.ka.mwi.businesshorizon2.ui.login;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.LoginForm;
import com.vaadin.ui.LoginForm.LoginEvent;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

/**
 * Dies ist die Vaadin-Implementierung des LogIn Screens. Diese stützt sich vor
 * allem auf das hinzufügen eines LogIn-Forms von Vaadin welches die Darstellung
 * wie auch Übermittlung der Werte Username und Password sicherstellt.
 * 
 * @author Christian Scherer
 * 
 */

public class LogInScreenViewImpl extends Window implements
		LogInScreenViewInterface, ClickListener {
	private static final long serialVersionUID = 1L;

	private Logger logger = Logger.getLogger("LogInScreenViewImpl.class");

	@Autowired
	private LogInScreenPresenter presenter;

	private Window regDialog;
	private FormLayout fl;
	private TextField textfieldEmailAdress;
	private TextField textfieldFirstName;
	private TextField textfieldLastName;
	private TextField textfieldCompany;
	private PasswordField passwordFieldPassword;
	private PasswordField passwordFieldPasswordRep;
	private Button dialogRegBtn;
	private Button registerBtn;
	private Button passwordForgotBtn;

	/**
	 * Dies ist der Konstruktor, der von Spring nach der Initialierung der
	 * Dependencies aufgerufen wird. Er registriert sich selbst beim Presenter
	 * und initialisiert die View-Komponenten.
	 * 
	 * @author Christian Scherer
	 */
	@PostConstruct
	public void init() {
		presenter.setView(this);
		generateUi();
	}

	/**
	 * Diese Methode setzt den Titel (im Browser-Fenster) zu
	 * "Business Horizon 2" und erstellt die LogIn Maske mit Listener. Der
	 * Listener prüft ruft die im LogIn Event gesammelten LogIn-Daten und
	 * übergibt sie dem presenter zur Kontrolle. Je nach ausgang der Konrolle
	 * wird dann eine Fehlermeldung aufgerufen. Zudem wird mittels dem
	 * "registrieren" Button und dessen Listener eine Dialogfenster
	 * bereitgestellt mit dessen sich ein neuer Anwender registrieren kann.
	 * 
	 * @author Christian Scherer
	 */
	private void generateUi() {
		setCaption("Business Horizon 2");
		logger.debug("Überschrift für Browser erstellt");

		LoginForm login = new LoginForm();
		//Zur Anmeldung muss die Mailadresse als Benutzername angegeben werden
		login.setUsernameCaption("Mailadresse");
		login.setPasswordCaption("Passwort");
		login.setWidth("100%");
		login.addListener(new LoginForm.LoginListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void onLogin(LoginEvent event) {
				presenter.doLogin(event.getLoginParameter("username"),
						event.getLoginParameter("password"));

			}
		});

		addComponent(login);

		registerBtn = new Button("Registrieren", this);

		passwordForgotBtn = new Button("Passwort vergessen", this);
		passwordForgotBtn.setEnabled(false);

		addComponent(registerBtn);
		addComponent(passwordForgotBtn);

		logger.debug("LogIn UI erstellt und Listener gesetzt");

	}

	/**
	 * Gibt eine Fehlermeldung an den Benutzer aus.
	 * 
	 * @author Christian Scherer
	 * @param message
	 *            Fehlermeldung die der Methode zur Ausgabe uebergeben wird
	 */
	public void showErrorMessage(String message) {
		getWindow().showNotification((String) "",
				message, Notification.TYPE_ERROR_MESSAGE);
	}

	/**
	 * Clicklistener für den Registrier-Button. Es wird ein Dialogfenster
	 * aufgerufen.
	 * 
	 * @author Christian Scherer
	 * @param event
	 *            das gefeuerte Clickevent
	 */
	@Override
	public void buttonClick(ClickEvent event) {
		if (event.getButton() == registerBtn) {
			presenter.registerUserDialog();
		} else if (event.getButton() == dialogRegBtn) {

			presenter.registerUser() ;

		} else if (event.getButton() == passwordForgotBtn) {
			presenter.passwordForgot();
			logger.debug("Password vergessen außgelöst.");
		}
	}

	/**
	 * Schließt das übergebene (Dialog)Fensters.
	 * 
	 * @author Christian Scherer
	 * @param window
	 *            Fenster das zu schliessen ist
	 */
	public void closeDialog(Window window) {
		getWindow().removeWindow(window);
	}

	/**
	 * Konkrete Erzeugung des Dialogfensters zur Registrierung eines neuen
	 * Users.
	 * 
	 * @author Christian Scherer, Marcel Rosenberger, Annika Weis
	 */
	public void showRegisterUserDialog() {

		regDialog = new Window("Benutzer bei Business Horizon registrieren");
		regDialog.setModal(true);
		regDialog.setWidth(400, UNITS_PIXELS);
		regDialog.setResizable(false);
		regDialog.setDraggable(false);
		regDialog.setCaption("Registrierung eines neuen Benutzers");
		fl = new FormLayout();
		fl.setSpacing(true);
		fl.setMargin(true);
		regDialog.addComponent(fl);
		
		//Vorname
		textfieldFirstName = new TextField();
		textfieldFirstName.setCaption("Bitte Vornamen angeben: ");
		textfieldFirstName.setRequired(true);
		textfieldFirstName.setRequiredError("Pflichtfeld");
		fl.addComponent(textfieldFirstName);
		
		//Nachname
		textfieldLastName = new TextField();
		textfieldLastName.setCaption("Bitte Nachnamen angeben: ");
		textfieldLastName.setRequired(true);
		textfieldLastName.setRequiredError("Pflichtfeld");
		fl.addComponent(textfieldLastName);

		//Unternehmen
		textfieldCompany = new TextField();
		textfieldCompany.setCaption("Bitte Unternehmen angeben: ");
		textfieldCompany.setRequired(true);
		textfieldCompany.setRequiredError("Pflichtfeld");
		fl.addComponent(textfieldCompany);

		//Mailadresse
		textfieldEmailAdress = new TextField();
		textfieldEmailAdress.setCaption("Bitte Mailadresse angeben: ");
		textfieldEmailAdress.setRequired(true);
		textfieldEmailAdress.setRequiredError("Pflichtfeld");
		fl.addComponent(textfieldEmailAdress);

		//Passwort
		passwordFieldPassword = new PasswordField("Bitte Passwort wählen: ");
		passwordFieldPassword.setRequired(true);
		passwordFieldPassword.setRequiredError("Pflichtfeld");
		fl.addComponent(passwordFieldPassword);

		//Passwort WDH
		passwordFieldPasswordRep = new PasswordField("Bitte Passwort wiederholen:");
		passwordFieldPasswordRep.setRequired(true);
		passwordFieldPasswordRep.setRequiredError("Pflichtfeld");		
		fl.addComponent(passwordFieldPasswordRep);

		VerticalLayout vl = new VerticalLayout();
		dialogRegBtn = new Button("Registrierung abschließen", this);
		vl.addComponent(dialogRegBtn);
		vl.setComponentAlignment(dialogRegBtn, Alignment.MIDDLE_CENTER);
		regDialog.addComponent(vl);

		getWindow().addWindow(regDialog);

		logger.debug("Registrier-Dialog erzeugt");
	}

	/**
	 * Getter Methode für die Mailadresse des Registrierungsdialogs
	 * 
	 * @author Christian Scherer
	 * @return mailadresse des Benutzers
	 */
	@Override
	public String getEmailAdress() {
		return (String) textfieldEmailAdress.getValue();
	}

	/**
	 * Getter Methode für das Passwort des Registrierungsdialogs
	 * 
	 * @author Christian Scherer
	 * @return Passwort des Benutzers
	 */
	@Override
	public String getPassword() {
		return (String) passwordFieldPassword.getValue();
	}

	/**
	 * Getter Methode für das Passwort-Wiederholung des Registrierungsdialogs
	 * 
	 * @author Christian Scherer
	 * @return Passwort-Wiedholung des Benutzers
	 */
	@Override
	public String getPasswordRep() {
		return (String) passwordFieldPasswordRep.getValue();
	}

	/**
	 * Getter Methode für den Vornamen des Registrierungsdialogs
	 * 
	 * @author Christian Scherer
	 * @return Vorname des Benutzers
	 */
	@Override
	public String getFirstName() {
		return (String) textfieldFirstName.getValue();
	}

	/**
	 * Getter Methode für den Unternehmensnamen des Registrierungsdialogs
	 * 
	 * @author Christian Scherer
	 * @return Unternehmensname des Benutzers
	 */
	@Override
	public String getCompany() {
		return (String) textfieldCompany.getValue();
	}

	/**
	 * Getter Methode für den Nachnamen des Registrierungsdialogs
	 * 
	 * @author Christian Scherer
	 * @return Nachname des Benutzers
	 */
	@Override
	public String getLastName() {
		return (String) textfieldLastName.getValue();
	}

	/**
	 * Getter Methode für das Dialogfenster
	 * 
	 * @author Christian Scherer
	 * @return Dialogfenster des Registrierungsdialog
	 */
	@Override
	public Window getRegDialog() {
		return this.regDialog;
	}

}
