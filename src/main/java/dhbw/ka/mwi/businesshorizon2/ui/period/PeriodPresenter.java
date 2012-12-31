package dhbw.ka.mwi.businesshorizon2.ui.period;

import javax.annotation.PostConstruct;

import com.mvplite.presenter.Presenter;
import dhbw.ka.mwi.businesshorizon2.ui.period.PeriodView;

public class PeriodPresenter extends Presenter<PeriodView> {
	private static final long serialVersionUID = 1L;

	
	/**
	 * Dies ist der Konstruktor, der von Spring nach der Initialierung der Dependencies 
	 * aufgerufen wird. Er registriert lediglich sich selbst als einen EventHandler.
	 * 
	 * @author Julius Hacker
	 */
	@PostConstruct
	public void init() {
	}
	
}
