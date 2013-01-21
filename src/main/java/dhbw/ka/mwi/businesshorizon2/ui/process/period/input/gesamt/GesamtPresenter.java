package dhbw.ka.mwi.businesshorizon2.ui.process.period.input.gesamt;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.mvplite.event.EventBus;
import com.mvplite.event.EventHandler;

import dhbw.ka.mwi.businesshorizon2.models.Period.AggregateCostMethodPeriod;
import dhbw.ka.mwi.businesshorizon2.ui.process.ScreenPresenter;
import dhbw.ka.mwi.businesshorizon2.ui.process.ShowErrorsOnScreenEvent;
import dhbw.ka.mwi.businesshorizon2.ui.process.ValidateContentStateEvent;
import dhbw.ka.mwi.businesshorizon2.ui.process.period.input.ShowGesamtViewEvent;

/**
 * Der Presenter fuer die Maske des Prozessschrittes zur Eingabe der Perioden.
 * 
 * @author Daniel Dengler
 * 
 */

public class GesamtPresenter extends ScreenPresenter<GesamtViewInterface> {
	private static final long serialVersionUID = 1L;
	Logger logger = Logger.getLogger(GesamtPresenter.class);

	AggregateCostMethodPeriod period;

	String[] shownProperties = { "immaterialFortune", "propertyValue",
			"financialValue", "equity", "provisions", "suplies", "claims",
			"stocks", "cashAssets", "borrowedCapital", "salesRevenue",
			"otherBusinessRevenue", "internallyProducedAndCapializedAssets",
			"materialCosts", "humanCapitalCosts", "writeDowns",
			"otherBusinessCosts", "interestAndOtherCosts" };

	@Autowired
	EventBus eventBus;

	/**
	 * Dies ist der Konstruktor, der von Spring nach der Initialierung der
	 * Dependencies aufgerufen wird. Er registriert lediglich sich selbst als
	 * einen EventHandler.
	 * 
	 * @author Daniel Dengler
	 */

	@PostConstruct
	public void init() {
		eventBus.addHandler(this);
	}

	@Override
	public boolean isValid() {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * Fängt das ShowEvent ab und sorgt dafür das die View die benötigten
	 * Eingabefelder erstellt und mit den bisherigen Daten befüllt.
	 * <p>
	 * Hierzu wird die Periode aus dem Event genommen und auf ihre Propertys mit
	 * vorhandenen Gettern&Settern geprüft. Die gefundenen Propertys werden als
	 * Eingabefelder zur verfügung gestellt.
	 * <p>
	 * Wichtig ist das Stringarray "shownProperties". Dieses enthält die Namen
	 * der anzuzeigenden Felder.
	 * 
	 * @param event
	 */
	
	@EventHandler
	public void onShowEvent(ShowGesamtViewEvent event) {
		logger.debug("ShowDirektViewEvent erhalten");
		period = event.getPeriod();
		getView().initForm();
		try {
			for (PropertyDescriptor pd : Introspector.getBeanInfo(
					period.getClass(), Object.class).getPropertyDescriptors()) {
				if (Arrays.asList(shownProperties)
						.contains(pd.getDisplayName())) {

					try {
						getView().addInputField(pd.getDisplayName(),
								(double) pd.getReadMethod().invoke(period));
					} catch (IllegalAccessException | IllegalArgumentException
							| InvocationTargetException e) {
						e.printStackTrace();
					}
				}
			}
		} catch (IntrospectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void validate(ValidateContentStateEvent event) {
		// TODO Auto-generated method stub

	}

	@Override
	public void handleShowErrors(ShowErrorsOnScreenEvent event) {
		// TODO Auto-generated method stub

	}
	
	/**
	 * Sorgt dafür, dass Änderungen in das Periodenobjekt geschrieben werden.
	 * Überprüft die Benutzereingabe auf ihre Konvertierbarkeit in eine
	 * Doublevariable und gibt im Fehlerfall eine Fehlermeldung an den User
	 * zurück.
	 * 
	 * @param newContent
	 *            Inhalt des Textfeldes das in das Periodenobjekt geschrieben
	 *            werden soll
	 * @param textFieldColumn
	 *            Spalte des GridLayouts wo das Textfeld liegt
	 * @param textFieldRow
	 *            Reihe des GridLayouts wo das Textfeld liegt
	 * @param destination
	 *            Name der Property in welche newContent geschrieben werden soll
	 */
	public void validateChange(String newContent, int textFieldColumn,
			int textFieldRow, String destination) {
		logger.debug("" + newContent);
		try {
			Double.parseDouble(newContent);
		} catch (Exception e) {
			getView().setWrong(textFieldColumn, textFieldRow, true);
		}
		getView().setWrong(textFieldColumn, textFieldRow, false);

		try {
			for (PropertyDescriptor pd : Introspector.getBeanInfo(
					period.getClass(), Object.class).getPropertyDescriptors()) {
				if (Arrays.asList(shownProperties).contains(destination)) {
					if (pd.getDisplayName().equals(destination)) {
						try {
							pd.getWriteMethod();
							period.toString();
							Double.parseDouble(newContent);

							pd.getWriteMethod().invoke(
									period,
									new Object[] { Double
											.parseDouble(newContent) });
							logger.debug("Content should be written: "
									+ (double) pd.getReadMethod()
											.invoke(period));
						} catch (IllegalAccessException
								| IllegalArgumentException
								| InvocationTargetException e) {
							e.printStackTrace();
						}
					}
				}
			}
		} catch (IntrospectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
