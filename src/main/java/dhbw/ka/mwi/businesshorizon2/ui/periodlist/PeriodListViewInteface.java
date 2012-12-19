package dhbw.ka.mwi.businesshorizon2.ui.periodlist;

import java.util.List;
import java.util.NavigableSet;

import com.mvplite.view.View;

import dhbw.ka.mwi.businesshorizon2.models.Period;

/**
 * Dieses Interface zeigt die von der View zur Verfuegung stehenden Methoden,
 * mit denen der Presenter mit der View kommunizieren kann.
 * 
 * @author Christian Gahlert
 *
 */
public interface PeriodListViewInteface extends View {

	/**
	 * Gibt an, ob der "Periode hinzufuegen"-Button angezeigt werden sollte oder nicht.
	 * 
	 * @author Christian Gahlert
	 * @param flag true = Anzeigen, false = Nicht anzeigen
	 */
	public void setShowAddPeriodButton(boolean flag);
	
	/**
	 * Gibt an, ob der "Periode entfernen"-Button angezeigt werden sollte oder nicht.
	 * 
	 * @author Christian Gahlert
	 * @param flag true = Anzeigen, false = Nicht anzeigen
	 */
	public void setShowRemovePeriodButton(boolean flag);

	/**
	 * Diese Methode zeigt die Perioden periods an und selektiert selected. Falls
	 * selected null ist, wird nichts selektiert.
	 * 
	 * @author Christian Gahlert
	 * @param periods Die Anzuzeigenden Perioden
	 * @param selected Die selektierte Periode oder null
	 */
	public void setPeriods(NavigableSet<Period> periods, Period selected);

	/**
	 * Uebergibt die beim Hinzufuegen einer Periode zur Verfuegung stehenden Jahre.
	 * 
	 * @author Christian Gahlert
	 * @param availableYears
	 */
	public void setAvailableYears(List<Integer> availableYears);

}
