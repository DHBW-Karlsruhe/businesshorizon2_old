package dhbw.ka.mwi.businesshorizon2.ui.process.period.input;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.text.DecimalFormat;
import java.text.ParseException;
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

public abstract class AbstractInputPresenter<T extends InputViewInterface> extends ScreenPresenter<T> {
	private static final long serialVersionUID = 1L;

	 Period period;
	 protected Logger logger;
	
	
	 DecimalFormat df = new DecimalFormat(",##0.00");

	protected String[] shownProperties;

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
		eventBus.addHandler(this);
	}

	@Override
	public boolean isValid() {
		// TODO Auto-generated method stub
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
				if (Arrays.asList(shownProperties).contains(pd.getDisplayName())) {

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

	public void validateChange(String newContent, int textFieldColumn,
			int textFieldRow, String destination) {
		logger.debug("" + newContent);
		try {df.parse(newContent).doubleValue();
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

						pd.getWriteMethod()
								.invoke(period,
										new Object[] { df.parse(newContent).doubleValue() });
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
