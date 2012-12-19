package dhbw.ka.mwi.businesshorizon2.ui.method;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.mvplite.event.EventBus;
import com.mvplite.event.EventHandler;
import com.mvplite.presenter.Presenter;

import dhbw.ka.mwi.businesshorizon2.methods.AbstractMethod;
import dhbw.ka.mwi.businesshorizon2.methods.MethodRunner;
import dhbw.ka.mwi.businesshorizon2.methods.Result;
import dhbw.ka.mwi.businesshorizon2.models.Project;

/**
 * Der Presenter fuer die Detail-Ansicht einer Methode. 
 * 
 * @author Christian Gahlert
 *
 */
public class MethodPresenter extends Presenter<MethodViewInterface> implements MethodRunner.Callback {
	private static final long serialVersionUID = 1L;

	@Autowired
	private EventBus eventBus;
	
	@Autowired
	private Project project;
	
	private AbstractMethod currentMethod;

	private MethodRunner methodRunner;

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
	 * Diese Methode startet die eigentlich Berechnung mit Hilfe der jeweiligen Berechnungsmethode.
	 * Dabei wird eine Instanz des Presenter uebergeben, da er das Interface MethodRunner.Callback
	 * implementiert, in dem die Callback-Methoden fuer den Fortschritt sowie die Fertigstellung
	 * definiert sind.
	 * 
	 * Darueberhinaus wird der View mitgeteilt, dass die Progressbar jetzt angezeigt werden soll.
	 * 
	 * @author Christian Gahlert
	 */
	public void calculate() {
		getView().showProgress();
		
		methodRunner = new MethodRunner(currentMethod, project.getPeriods(), this);
		methodRunner.start();
	}

	/**
	 * Diese Methode unterbricht die zuvor in der calculate()-Methode gestartete Berechnung (z.B.
	 * weil der Nutzer auf "Abbrechen" geklickt hat.
	 * 
	 * Darueberhinaus wird der View mitgeteilt, dass die Progressbar jetzt wieder entfernt/versteckt
	 * werden kann.
	 * 
	 * @author Christian Gahlert
	 */
	public void interruptCalculation() {
		if(methodRunner != null) {
			methodRunner.interrupt();
		}
		
		getView().hideProgress();
	}
	
	/**
	 * Dieser Event wird von dem MethodListPresenter gefeuert, wenn auf eine Methode geklickt wurde.
	 * Er kuemmert sich darum, dass die im Event mitgegebene Methode an die View uebergeben wird.
	 * 
	 * @author Christian Gahlert
	 * @param event
	 */
	@EventHandler
	public void onShowMethod(ShowMethodEvent event) {
		currentMethod = event.getMethod();
		getView().showMethod(event.getMethod());
	}

	/**
	 * Diese Methode wird vom MethodRunner.Callback-Interface definiert. Sie wird aufgerufen, wenn
	 * die Berechnung abgeschlossen ist. Bei Erfolg wird das Result uebergeben, ansonsten wird
	 * null uebergeben.
	 * 
	 * Die View wird angewiesen die Progressbar wieder zu verstecken und (falls Vorhanden) das
	 * Ergebnis anzuzeigen.
	 * 
	 * @author Christian Gahlert
	 */
	@Override
	public void onComplete(Result result) {
		getView().hideProgress();

		if(result != null) {
			getView().showResult(result);
		}
	}

	/**
	 * Diese Methode wird innerhalb der jeweiligen Berechnungsmethode aufgerufen, um der View
	 * die Aktualisierung der Progressbar zu ermoeglichen.
	 * 
	 * @author Christian Gahlert
	 */
	@Override
	public void onProgressChange(float progress) {
		getView().setProgress(progress);
	}
	
}
