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

	@Override
	protected Class<? extends Application> getApplicationClass()
			throws ClassNotFoundException {
		return applicationClass;
	}
	 
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
