package dhbw.ka.mwi.businesshorizon2.ui.periodedit;

import com.mvplite.view.View;

import dhbw.ka.mwi.businesshorizon2.models.Period;

/**
 * Dieses Interface zeigt die von der View zur Verfuegung stehenden Methoden,
 * mit denen der Presenter mit der View kommunizieren kann.
 * 
 * @author Christian Gahlert
 *
 */
public interface PeriodEditView extends View {

	/**
	 * Uebergibt die anzuzeigende Periode.
	 * 
	 * @author Christian Gahlert
	 * @param period
	 */
	public void setPeriod(Period period);

}
