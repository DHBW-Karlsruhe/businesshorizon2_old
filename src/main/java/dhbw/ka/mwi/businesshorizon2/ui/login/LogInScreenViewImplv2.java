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

package dhbw.ka.mwi.businesshorizon2.ui.login;



import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.terminal.Sizeable;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout.MarginInfo;
import com.vaadin.ui.LoginForm;
import com.vaadin.ui.LoginForm.LoginEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.VerticalSplitPanel;
import com.vaadin.ui.Window;

/**
 * Dies ist die Vaadin-Implementierung des LogIn Screens. Diese stützt sich vor
 * allem auf das hinzufügen eines LogIn-Forms von Vaadin welches die Darstellung
 * wie auch Übermittlung der Werte Username und Password sicherstellt.
 * 
 * Es wird das neue LogIn auf Basis der Implementierung der Vorgänger aufgebaut.
 * Um die alten Strukture als Fallback-Lösung zu erhalten, wurde die Version2
 * angelegt.
 * 
 * @author Felix Schlosser
 */

public class LogInScreenViewImplv2 extends Window implements
		LogInScreenViewInterface, ClickListener {
	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger("LogInScreenViewImpl.class");

	@Autowired
	private LogInScreenPresenter presenter;
	
	private HorizontalLayout horizontal;
	
	private HorizontalLayout welcomeLayout, textLayout, regLayout;
	
	private VerticalLayout verticalTop;
	
	private VerticalLayout registerBtnLayout, dialogRegBtnLayout, registerAbortBtnLayout, loginBtnLayout;
	
	private VerticalSplitPanel vSplitPanel;
	
	
	private Label welcome, welcomeText, iconLabel;
	
	
	private TextField textfieldEmailAdress;
	private TextField textfieldFirstName;
	private TextField textfieldLastName;
	private TextField textfieldCompany;
	private TextField loginEmail;
	private PasswordField loginPassword;
	private PasswordField passwordFieldPassword;
	private PasswordField passwordFieldPasswordRep;
	private Button dialogRegBtn;
	private Button registerBtn;
	private Button passwordForgotBtn;
	private Button registerAbortBtn; 
	private Button loginBtn;
	private LoginForm login;
	private Label registerBtnLabel;
	private Label dialogRegBtnLabel;
	private Label registerAbortBtnLabel;
	private Label loginBtnLabel;
	
	
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
		setCaption("Business Horizon 3");
		logger.debug("Überschrift für Browser erstellt");

		horizontal = new HorizontalLayout();
		
		vSplitPanel = new VerticalSplitPanel();
		vSplitPanel.setSplitPosition(70, Sizeable.UNITS_PERCENTAGE);
		vSplitPanel.setLocked(true);
		
		verticalTop = new VerticalLayout();
		verticalTop.setSizeFull();
		verticalTop.setMargin(true, true, true, true);
		verticalTop.setStyleName("loginTop");
		
		//Erzeugt ein Label mit dem Willkommens-Text neben dem Logo
		welcome = new Label("Willkommen bei");
		welcome.setStyleName("welcomeSlogan");
		
		//Erezeugt ein Label mit dem Beschreibungstext
		welcomeText = new Label("Mithilfe dieser Software können Sie Ihre zukünftige Unternehmenswerte berechnen lassen. Hierzu stehen Ihnen verschiedene Methoden zur Verfügung, die Ihnen unterschiedliche Herangehensweisen ermöglichen – je nachdem, welche Daten Ihnen zur Verfügung stehen. ");
		welcomeText.setStyleName("welcomeText");
		welcomeText.setSizeFull();
		
		textLayout = new HorizontalLayout();
		textLayout.setWidth(50,Sizeable.UNITS_PERCENTAGE);
		textLayout.addComponent(welcomeText);
		textLayout.setComponentAlignment(welcomeText, Alignment.TOP_RIGHT);
		
		iconLabel = new Label();
		iconLabel.setIcon(new ThemeResource("images/Logo_businesshorizon.png"));
		iconLabel.setWidth(40, Sizeable.UNITS_PERCENTAGE);
		iconLabel.setStyleName("logo");
		
		welcomeLayout = new HorizontalLayout();
		welcomeLayout.setSizeFull();
		welcomeLayout.addComponent(welcome);
		welcomeLayout.setComponentAlignment(welcome, Alignment.BOTTOM_CENTER);
		
		welcomeLayout.addComponent(iconLabel);
		welcomeLayout.setComponentAlignment(iconLabel, Alignment.BOTTOM_RIGHT);
		
		//Fügt den Beschreibungs-Text dem Bildschirm hinzu
		verticalTop.addComponent(textLayout);
		verticalTop.setComponentAlignment(textLayout, Alignment.TOP_RIGHT);
				
		verticalTop.addComponent(welcomeLayout);
		verticalTop.setComponentAlignment(welcomeLayout, Alignment.BOTTOM_RIGHT);
		
		login = new LoginForm();
		//Zur Anmeldung muss die Mailadresse als Benutzername angegeben werden
		login.setUsernameCaption("Mailadresse");
		login.setPasswordCaption("Passwort");
		login.setWidth(null);
		login.setStyleName("login_form");
		login.addListener(new LoginForm.LoginListener() {
		private static final long serialVersionUID = 1L;

			@Override
			public void onLogin(LoginEvent event) {
				presenter.doLogin(event.getLoginParameter("username"),
						event.getLoginParameter("password"));

					}
		});

		//VerticalLayout login = generateLogin();
		
		horizontal.addComponent(login);
		horizontal.setComponentAlignment(login, Alignment.TOP_CENTER);

		HorizontalLayout landingBtnLayout = new HorizontalLayout();
		
		loginBtnLayout = new VerticalLayout();
		loginBtn = new Button("", this);
		loginBtn.setWidth(100, Sizeable.UNITS_PIXELS);
		loginBtn.setHeight(100, Sizeable.UNITS_PIXELS);
		loginBtn.addStyleName("loginBtn");
		
		loginBtnLabel = new Label("Login");
		loginBtnLabel.setWidth(100, Sizeable.UNITS_PIXELS);
		loginBtnLabel.addStyleName("loginBtnLabel");
		
		loginBtnLayout.addComponent(loginBtn);
		loginBtnLayout.addComponent(loginBtnLabel);
		
//		landingBtnLayout.addComponent(loginBtnLayout);
		
		//horizontal.addComponent(loginBtnLayout);
		//horizontal.setComponentAlignment(loginBtnLayout, Alignment.TOP_RIGHT);
		
		registerBtnLayout = new VerticalLayout();
		registerBtnLayout.setSizeUndefined();
		
		registerBtn = new Button("", this);
		registerBtn.setSizeUndefined();
		registerBtn.setHeight(100, Sizeable.UNITS_PIXELS);
		registerBtn.setWidth(100, Sizeable.UNITS_PIXELS);
		registerBtn.addStyleName("registerBtn");
		
		registerBtnLabel = new Label("Registrieren");
		registerBtnLabel.setWidth(100, Sizeable.UNITS_PIXELS);
		addStyleName("registerBtnLabel");
		
		registerBtnLayout.addComponent(registerBtn);
		registerBtnLayout.addComponent(registerBtnLabel);
		
		landingBtnLayout.addComponent(registerBtnLayout);
		
		
		passwordForgotBtn = new Button("Passwort vergessen", this);
		passwordForgotBtn.setEnabled(false);
		
		horizontal.addComponent(landingBtnLayout);
		horizontal.setComponentAlignment(landingBtnLayout, Alignment.TOP_RIGHT);
		horizontal.setMargin(new MarginInfo(true, true, true, true));
		horizontal.setSizeFull();
		//vertical.addComponent(passwordForgotBtn);
		//vertical.setComponentAlignment(passwordForgotBtn, Alignment.MIDDLE_CENTER);

		logger.debug("LogIn UI erstellt und Listener gesetzt");
		
		vSplitPanel.setFirstComponent(verticalTop);
		vSplitPanel.setSecondComponent(horizontal);
		
		setContent(vSplitPanel);
	}
	
	/**
	 * Erzeugt die Felder, die für die Regisitrierung eines neuen Users benötigt werden
	 * Gibt diese im Richtigen Layout zurück
	 * @author Felix Schlosser
	 * @return HorizontalLayout
	 */
	private HorizontalLayout generateRegisterLayout() {
		
		//Erstellen der LayoutContainer für den Registrierungsdialog
		HorizontalLayout registerComponent = new HorizontalLayout();
		registerComponent.setSizeFull();
		registerComponent.setMargin(true, true, true, true);
		
		HorizontalLayout registerFields = new HorizontalLayout();
		HorizontalLayout buttonLayout = new HorizontalLayout();
		
		VerticalLayout credentials = new VerticalLayout();
		VerticalLayout required = new VerticalLayout();
		VerticalLayout optional = new VerticalLayout();
		
		//Erstellen des Email-Feld
		textfieldEmailAdress = new TextField("Emailadresse eingeben");
		textfieldEmailAdress.setRequired(true);
		textfieldEmailAdress.setRequiredError("Pflichtfeld");
		
		//Erstellen des Passwort-Feld
		passwordFieldPassword = new PasswordField("Passwort eingeben");
		passwordFieldPassword.setRequired(true);
		passwordFieldPassword.setRequiredError("Pflichtfeld");
		passwordFieldPassword.setDescription("Geben Sie bitte ein Passwort (8-12 Zeichen) ein, das folgende Kriterien erfüllt: Mindestens ein Groß- und ein Kleinbuchstabe, Zahl, Sonderzeichen.");
		
		//Erstellen des Passwort-Wiederholen Feld
		passwordFieldPasswordRep = new PasswordField("Passwort wiederholen");
		passwordFieldPasswordRep.setRequired(true);
		passwordFieldPasswordRep.setRequiredError("Pflichtfeld");
		
		// Felder zur Layoutkomponente hinzufügen
		credentials.addComponent(textfieldEmailAdress);
		credentials.addComponent(passwordFieldPassword);
		credentials.addComponent(passwordFieldPasswordRep);
		
		//Erstellen des Vorname-Feld
		textfieldFirstName = new TextField("Vorname eingeben");
		textfieldFirstName.setRequired(true);
		textfieldFirstName.setRequiredError("Pflichtfeld");
		
		//Erstellen des Nachname-Feld
		textfieldLastName = new TextField("Nachname eingeben");
		textfieldLastName.setRequired(true);
		textfieldLastName.setRequiredError("Pflichtfeld");
		
		//Felder zur Layoutkomponente hinzufügen
		required.addComponent(textfieldFirstName);
		required.addComponent(textfieldLastName);
				
		//Erstellen Firmenname-Feld
		textfieldCompany = new TextField("Firmenname eingeben");
		textfieldCompany.setRequired(true);
		textfieldCompany.setRequiredError("Pflichtfeld");
		
		//Feld zur Layoutkomponente hinzufügen
		optional.addComponent(textfieldCompany);
		
		//Registrieren-Button erstellen
		
		dialogRegBtnLayout = new VerticalLayout();
		
		dialogRegBtn = new Button("", this);
		dialogRegBtn.setWidth(100, Sizeable.UNITS_PIXELS);
		dialogRegBtn.setHeight(100, Sizeable.UNITS_PIXELS);
		dialogRegBtn.addStyleName("dialogRegBtn");
		
		dialogRegBtnLabel = new Label("Registrieren");
		dialogRegBtnLabel.setWidth(100, Sizeable.UNITS_PIXELS);
		dialogRegBtnLabel.addStyleName("dialogRegBtnLabel");
		
		dialogRegBtnLayout.addComponent(dialogRegBtn);
		dialogRegBtnLayout.addComponent(dialogRegBtnLabel);
		dialogRegBtnLayout.setSizeUndefined();
		//Abbrechen-Button erstellen

		registerAbortBtnLayout = new VerticalLayout();
	
		registerAbortBtn = new Button("", this);
		registerAbortBtn.setWidth(100, Sizeable.UNITS_PIXELS);
		registerAbortBtn.setHeight(100, Sizeable.UNITS_PIXELS);
		registerAbortBtn.addStyleName("registerAbortBtn");
		
		registerAbortBtnLabel = new Label("Abbrechen");
		registerAbortBtnLabel.setWidth(100, Sizeable.UNITS_PIXELS);
		
		registerAbortBtnLayout.addComponent(registerAbortBtn);
		registerAbortBtnLayout.addComponent(registerAbortBtnLabel);
		
		buttonLayout.addComponent(dialogRegBtnLayout);
		buttonLayout.addComponent(registerAbortBtnLayout);
		buttonLayout.setMargin(true, true, true, true);
		
		registerFields.addComponent(credentials);
		registerFields.addComponent(required);
		registerFields.addComponent(optional);
		registerFields.setMargin(true);
		
		registerComponent.addComponent(registerFields);
		
		registerComponent.addComponent(buttonLayout);
		registerComponent.setComponentAlignment(buttonLayout, Alignment.TOP_RIGHT);
		
		
		return registerComponent;
	}

	private VerticalLayout generateLogin() {
		VerticalLayout loginForm = new VerticalLayout();
		
		loginEmail = new TextField();
		loginEmail.setCaption("Emailadresse");
				
		loginPassword = new PasswordField();
		loginPassword.setCaption("Passwort");
		
		loginForm.addComponent(loginEmail);
		loginForm.addComponent(loginPassword);
		
		
		return loginForm;
	}
	/**
	 * Gibt eine Fehlermeldung an den Benutzer aus.
	 * 
	 * @author Christian Scherer
	 * @param message
	 *            Fehlermeldung die der Methode zur Ausgabe uebergeben wird
	 */
	public void showErrorMessage(String message) {
		Window.Notification notif = new Notification((String) "",
				message, Notification.TYPE_WARNING_MESSAGE);
		notif.setPosition(Window.Notification.POSITION_CENTERED_BOTTOM);
		getWindow().showNotification(notif);
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
			vSplitPanel.removeComponent(horizontal);
			regLayout = generateRegisterLayout();
			vSplitPanel.setSecondComponent(regLayout);
			
		} else if (event.getButton() == dialogRegBtn) {

			if (presenter.registerUser()){	
				showNotification("Registrierung erfolgreich abgeschlossen");
				vSplitPanel.removeComponent(regLayout);
				vSplitPanel.setSecondComponent(horizontal);
			} else {
				//this.showErrorMessage("Bitte alle Pflichtfelder ausfüllen");
			}
			
			
		} else if (event.getButton() == passwordForgotBtn) {
			presenter.passwordForgot();
			logger.debug("Password vergessen außgelöst.");
		} else if (event.getButton() == registerAbortBtn) {
			vSplitPanel.removeComponent(regLayout);
			vSplitPanel.setSecondComponent(horizontal);
		} else if (event.getButton() == loginBtn) {
			presenter.doLogin(getLoginEmail(), getLoginPassword());
		}
			
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
	
	@Override
	public String getLoginEmail() {
		return (String) loginEmail.getValue();
	}
	
	@Override
	public String getLoginPassword() {
		return (String) loginPassword.getValue();
	}


}
