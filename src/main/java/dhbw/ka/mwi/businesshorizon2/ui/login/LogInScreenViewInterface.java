package dhbw.ka.mwi.businesshorizon2.ui.login;

import com.mvplite.view.View;
import com.vaadin.ui.Window;

/**
 * Dieses Interface zeigt die von der View zur Verfuegung stehenden Methoden,
 * mit denen der Presenter mit der View kommunizieren kann.
 * 
 * @author Christian Scherer
 *
 */

/**
 * Methode zum Anzeigen einer Fehlermeldung bei fehlgeschlagenem Login.
 * 
 * @author Florian Stier
 * 
 */
public interface LogInScreenViewInterface extends View {

	void showErrorMessage(String message);

	String getEmailAdress();

	String getPassword();

	String getFirstName();

	String getCompany();

	String getLastName();

	String getPasswordRep();

	void showRegisterUserDialog();

	Window getRegDialog();

	void closeDialog(Window window);

}
