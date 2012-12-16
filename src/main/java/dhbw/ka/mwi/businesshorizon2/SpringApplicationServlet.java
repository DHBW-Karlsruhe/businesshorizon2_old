package dhbw.ka.mwi.businesshorizon2;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.vaadin.Application;
import com.vaadin.terminal.gwt.server.AbstractApplicationServlet;

/**
 * Diese Klasse ist fuer das Initialisieren von neuen BHApplication-Instanzen zustaendig. Dabei wird das
 * Spring dependency injection framework verwendet um alle Abhaengigkeiten hinzuzufuegen.
 * 
 * @author Christian Gahlert
 *
 */
public class SpringApplicationServlet extends AbstractApplicationServlet {
	private static final long serialVersionUID = 1L;
	
	private String applicationBean;
	private WebApplicationContext applicationContext;
	private Class<? extends Application> applicationClass;

	/**
	 * Diese Methode ist dafuer verantwortlich die Klasse der Applikation zurueckzugeben - also die
	 * Klasse der vaadinApp-Bean. (Diese Methode wird vom Vaadin Framework verwendet.)
	 * 
	 * @author Christian Gahlert
	 */
	@Override
	protected Class<? extends Application> getApplicationClass()
			throws ClassNotFoundException {
		return applicationClass;
	}
	 
	/**
	 * Diese Methode wird einmal beim Initialisieren aufgerufen. Dort werden die Konfigurationseinstellungen
	 * aus der web.xml gelesen. Darueberhinaus wird ein Spring-Application-Context aus der servletConfig
	 * geholt, mit dem es moeglich ist, Beans mit Session-Scope zu erzeugen.
	 * 
	 * @author Christian Gahlert
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void init(ServletConfig servletConfig) throws ServletException {
		super.init(servletConfig);
		
		applicationBean = servletConfig.getInitParameter("applicationBean");
		
		if(applicationBean == null) {
			throw new ServletException("ApplicationBean not specified in servlet parameters");
		}
		
		applicationContext = WebApplicationContextUtils.getWebApplicationContext(servletConfig.getServletContext());
		applicationClass = (Class<? extends Application>) applicationContext.getType(applicationBean);
	}
	
	/**
	 * Diese Methode gibt eine neue Instanz der vaadinApp-Bean zurueck. Dabei werden die Dependencies anhand
	 * des Session-Scopes neu erstellt oder wieder verwendet.
	 * 
	 * Zusaetzlich ist noch eine Ueberpruefung vorhanden, ob die aufgerufene URL ein ?restartApplication am Ende
	 * enthaelt. Dies ermoeglicht das manuelle Neu-Starten der Vaadin-Applikation. Da hierbei jedoch die
	 * HTTP-Session nicht neu gestartet wird, bleiben die Spring-Beans erhalten. Um dies zu umgehen, wird
	 * hier die HTTP-Session geloescht, falls der ?restartApplication-Parameter mitgegeben wurde.
	 * 
	 * @author Christian Gahlert
	 */
	@Override
	protected Application getNewApplication(HttpServletRequest request)
			throws ServletException {
		
		if (request.getParameter("restartApplication") != null) {
			try {
				request.getSession().invalidate();
			} catch(Throwable t) {
			}
		}
		
		return (Application) applicationContext.getBean(applicationBean);
	}
	
}
