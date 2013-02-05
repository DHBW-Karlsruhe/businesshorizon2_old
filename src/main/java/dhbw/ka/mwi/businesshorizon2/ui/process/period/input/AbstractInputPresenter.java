package dhbw.ka.mwi.businesshorizon2.ui.process.period.input;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.mvplite.event.EventBus;
import com.mvplite.event.EventHandler;

import dhbw.ka.mwi.businesshorizon2.models.Period.Period;
import dhbw.ka.mwi.businesshorizon2.ui.process.ScreenPresenter;
import dhbw.ka.mwi.businesshorizon2.ui.process.ShowErrorsOnScreenEvent;
import dhbw.ka.mwi.businesshorizon2.ui.process.ValidateContentStateEvent;
import dhbw.ka.mwi.businesshorizon2.ui.process.navigation.NavigationSteps;

public abstract class AbstractInputPresenter<T extends InputViewInterface>
		extends ScreenPresenter<T> {
	private static final long serialVersionUID = 1L;

	Period period;
	protected Logger logger;

	DecimalFormat df = new DecimalFormat(",##0.00");

	protected String[] shownProperties;
	protected String[] germanNamesProperties;

	@Autowired
	EventBus eventBus;

	/**
	 * Dies ist der Konstruktor, der von Spring nach der Initialierung der
	 * Dependencies aufgerufen wird. Er registriert lediglich sich selbst als
	 * einen EventHandler.
	 * 
	 * @author Julius Hacker
	 */

	@PostConstruct
	public void init() {
	}

	@Override
	public boolean isValid() {
		if (period != null) {

			try {
				ArrayList<String> wrongFields = new ArrayList<String>();
				boolean anyFalse = false;
				wrongFields.add(period.getYear()+"");
				for (PropertyDescriptor pdr : Introspector.getBeanInfo(
						period.getClass(), Object.class)
						.getPropertyDescriptors()) {
					if (Arrays.asList(shownProperties).contains(
							pdr.getDisplayName().substring(0, pdr.getDisplayName().length()-3))) {
						if (!(boolean) pdr.getReadMethod().invoke(period)){
							wrongFields.add(pdr.getDisplayName().substring(0, pdr.getDisplayName().length()-3));
							anyFalse=true;
						}
					}
				}
				if(anyFalse){
					eventBus.fireEvent(new WrongFieldsEvent(wrongFields));
					return false;
				}
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IntrospectionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return true;
		} else
			return false;
	}

	public void processEvent(ShowInputViewEventInterface event) {
		logger.debug("ShowDirektViewEvent erhalten");
		period = event.getPeriod();
		getView().initForm();
		getView().addHeader(period.getYear());

		try {
			for (PropertyDescriptor pd : Introspector.getBeanInfo(
					period.getClass(), Object.class).getPropertyDescriptors()) {
				logger.debug("Processing: "+pd.getName());
				if (Arrays.asList(shownProperties)
						.contains(pd.getDisplayName())) {
					try {
						String germanName;
						germanName= germanNamesProperties[Arrays.asList(shownProperties).indexOf(pd.getDisplayName())];
						boolean skipInitialContent = true;
						for (PropertyDescriptor pdr : Introspector.getBeanInfo(
								period.getClass(), Object.class)
								.getPropertyDescriptors()) {
							if ((pd.getDisplayName() + "Set").equals(pdr
									.getDisplayName())) {

								skipInitialContent = !(boolean) pdr
										.getReadMethod().invoke(period);
								logger.debug("method found and skipInitialContent set to "
										+ skipInitialContent);
							}
						}
						if (skipInitialContent) {
							getView().addInputField(germanName);
							logger.debug("initialContent skipped");

						} else {
							getView().addInputField(germanName,
									(double) pd.getReadMethod().invoke(period));
							logger.debug("initialContent written");
						}
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
		isValid();
	}

	@Override
	public void handleShowErrors(ShowErrorsOnScreenEvent event) {
		// not used here look at PeriodPresenter

	}
	
	/**
	 * Sorgt daf/u00fcr, dass /u00e4nderungen in das Periodenobjekt geschrieben werden.
	 * /u00fcberpr/u00fcft die Benutzereingabe auf ihre Konvertierbarkeit in eine
	 * Doublevariable und gibt im Fehlerfall eine Fehlermeldung an den User
	 * zur/u00fcck.
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
		destination = shownProperties[Arrays.asList(germanNamesProperties).indexOf(destination)];
		logger.debug("" + newContent);
		try {
			df.parse(newContent).doubleValue();
			df.parse(newContent).doubleValue();
		} catch (Exception e) {
			getView().setWrong(textFieldColumn, textFieldRow, true);
			return;
		}
		getView().setWrong(textFieldColumn, textFieldRow, false);

		for (PropertyDescriptor pd : BeanUtils.getPropertyDescriptors(period
				.getClass())) {
			if (Arrays.asList(shownProperties).contains(destination)) {
				if (pd.getDisplayName().equals(destination)) {
					try {
						pd.getWriteMethod();
						period.toString();

						pd.getWriteMethod().invoke(
								period,
								new Object[] { df.parse(newContent)
										.doubleValue() });
						logger.debug("Content should be written: "
								+ (double) pd.getReadMethod().invoke(period));
					} catch (IllegalAccessException | IllegalArgumentException
							| InvocationTargetException | ParseException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

}
