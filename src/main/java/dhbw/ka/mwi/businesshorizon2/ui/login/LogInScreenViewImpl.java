package dhbw.ka.mwi.businesshorizon2.ui.login;

import javax.annotation.PostConstruct;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.LoginForm;
import com.vaadin.ui.LoginForm.LoginEvent;
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
		login.setUsernameCaption("Benutzername");
		login.setPasswordCaption("Passwort");
		login.setWidth("100%");
		// login.setHeight("300px");
		login.addListener(new LoginForm.LoginListener() {
			private static final long serialVersionUID = 1L;

			// TODO : GGF BESSER mit EXCEPTIONS ZU ARBEITEN!
			@Override
			public void onLogin(LoginEvent event) {
				getWindow().showNotification(
						"New Login",
						"Username: " + event.getLoginParameter("username")
								+ ", password: "
								+ event.getLoginParameter("password"));
				boolean logInCheck = presenter.checkLogin(
						event.getLoginParameter("username"),
						event.getLoginParameter("password"));
				if (!logInCheck) {
					logger.debug("LogIn fehlgeschlagen");
					getWindow()
							.showNotification(
									(String) "Anmeldung fehlgeschlagen",
									(String) "Bitte erneut mit gültigen Anmeldedaten probieren",
									Notification.TYPE_ERROR_MESSAGE);
				}
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
	 * Clicklistener für den Registier-Button. Es wird ein Dialogfenster
	 * aufgerufen.
	 * 
	 * @author Christian Scherer
	 * @param event
	 *            das gefeuerte Clickevent
	 */
	@Override
	public void buttonClick(ClickEvent event) {
		if (event.getButton() == registerBtn) {
			registerUserDialog();
		} else if (event.getButton() == dialogRegBtn) {

			Boolean samePassword = presenter.validatePassword(
					(String) passwordFieldPassword.getValue(),
					(String) passwordFieldPasswordRep.getValue());

			if (samePassword) {
				logger.debug("Passwordgleichheit gewaehrleistet");

				if (textfieldFirstName.isValid() && textfieldLastName.isValid()
						&& textfieldCompany.isValid()
						&& passwordFieldPassword.isValid()
						&& passwordFieldPasswordRep.isValid()) {
					
					logger.debug("Alle Eingabefelder wurden vom Anwender befuellt.");

					presenter.registerUser(
							(String) textfieldFirstName.getValue(),
							(String) textfieldLastName.getValue(),
							(String) textfieldCompany.getValue(),
							(String) textfieldEmailAdress.getValue(),
							(String) passwordFieldPassword.getValue());
					getWindow().removeWindow(regDialog);

				} else {
					getWindow().showNotification((String) "",
							(String) "Bitte füllen Sie alle Felder aus.  ",
							Notification.TYPE_ERROR_MESSAGE);
					logger.debug("Es wurden nicht alle Eingabefelder vom Anwender befüllt.");

				}
			} else {
				logger.debug("Die eingegebenen Passwoerter stimmen nicht mit ueberein.");
				
				getWindow().showNotification((String) "",
						(String) "Passwort und dessen Wiederholung stimmen nicht überein. Bitte überprüfen Sie Ihre eingabe",
						Notification.TYPE_ERROR_MESSAGE);
			}
		} else if (event.getButton()==passwordForgotBtn){
			presenter.passwordForgot();
			logger.debug("Password vergessen außgelöst.");
		}
	}

	/**
	 * Konkrete Erzeugung des Dialogfensters zur Registrierung eines neuen
	 * Users.
	 * 
	 * @author Christian Scherer
	 */
	private void registerUserDialog() {

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

		textfieldFirstName = new TextField();
		textfieldFirstName.setCaption("Bitte Vornahmen angeben: ");
		textfieldFirstName.setRequired(true);
		textfieldFirstName.setRequiredError("Pflichtfeld");
		fl.addComponent(textfieldFirstName);

		textfieldLastName = new TextField();
		textfieldLastName.setCaption("Bitte Nachnamen angeben: ");
		textfieldLastName.setRequired(true);
		textfieldLastName.setRequiredError("Pflichtfeld");
		fl.addComponent(textfieldLastName);

		textfieldCompany = new TextField();
		textfieldCompany.setCaption("Bitte Unternehmen angeben: ");
		textfieldCompany.setRequired(true);
		textfieldCompany.setRequiredError("Pflichtfeld");
		fl.addComponent(textfieldCompany);

		textfieldEmailAdress = new TextField();
		textfieldEmailAdress.setCaption("Bitte Mailadresse angeben: ");
		textfieldEmailAdress.setRequired(true);
		textfieldEmailAdress.setRequiredError("Pflichtfeld");
		fl.addComponent(textfieldEmailAdress);

		passwordFieldPassword = new PasswordField("Bitte Passwort wählen: ");
		passwordFieldPassword.setRequired(true);
		passwordFieldPassword.setRequiredError("Pflichtfeld");
		fl.addComponent(passwordFieldPassword);

		passwordFieldPasswordRep = new PasswordField(
				"Bitte Passwort wiederholen:");
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

}
