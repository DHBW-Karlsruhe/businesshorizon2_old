package dhbw.ka.mwi.businesshorizon2.ui.methodlist;

import java.util.SortedSet;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.mvplite.event.EventBus;
import com.mvplite.event.EventHandler;
import com.mvplite.presenter.Presenter;

import dhbw.ka.mwi.businesshorizon2.methods.Method;
import dhbw.ka.mwi.businesshorizon2.ui.method.ShowMethodEvent;

/**
 * Dies ist der Presenter fuer die Auflistung der Berechnungsmethoden (wird 
 * im Hauptfenster links unten angezeigt). Die Liste der Methoden wird
 * dabei von Spring via Dependency Injection in das methods-Property
 * geschrieben. Es werden einfach alle Beans uebergeben, die eine Instanz
 * der abstrakten Method-Klasse sind.
 * 
 * @author Christian Gahlert
 *
 */
public class MethodListPresenter extends Presenter<MethodListView> {
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private EventBus eventBus;

	@Autowired
	private SortedSet<Method> methods;

	/**
	 * Dies ist der Konstruktor, der von Spring nach der Initialierung der Dependencies 
	 * aufgerufen wird. Er registriert lediglich sich selbst als einen EventHandler.
	 * 
	 * @author Christian Gahlert
	 */
	@PostConstruct
	public void init() {
		eventBus.addHandler(this);
	}
	
	/**
	 * Dieser Event wird vom Hauptfenster abgesetzt, um die View anzuweisen, 
	 * die verfuegbaren Methoden an die View zu uebergeben.
	 * 
	 * @author Christian Gahlert
	 * @param event
	 */
	@EventHandler
	public void onShowMethodList(ShowMethodListEvent event) {
		getView().setMethods(methods);
	}

	/**
	 * Diese Methode wird von der View aufgerufen, wenn eine Methode ausgewaehlt wurde.
	 * Dann wird ein ShowMethodEvent abgesetzt, der die Detail-Ansicht einer Methode
	 * aufruft.
	 * 
	 * @param method
	 */
	public void selectMethod(Method method) {
		eventBus.fireEvent(new ShowMethodEvent(method));
	}

}
