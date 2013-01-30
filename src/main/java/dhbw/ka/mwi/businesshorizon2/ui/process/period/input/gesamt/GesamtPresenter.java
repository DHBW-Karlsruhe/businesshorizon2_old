/*******************************************************************************
 * BusinessHorizon2
 * 
 *     Copyright (C) 2012-2013  Christian Gahlert, Florian Stier, Kai Westerholz,
 *     Timo Belz, Daniel Dengler, Katharina Huber, Christian Scherer, Julius Hacker
 * 
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Affero General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 * 
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Affero General Public License for more details.
 * 
 *     You should have received a copy of the GNU Affero General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/


package dhbw.ka.mwi.businesshorizon2.ui.process.period.input.gesamt;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.text.DecimalFormat;
import java.text.ParseException;
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
 * @author Julius Hacker
 * 
 */

public class GesamtPresenter extends ScreenPresenter<GesamtViewInterface> {
	private static final long serialVersionUID = 1L;
	Logger logger = Logger.getLogger(GesamtPresenter.class);

	private DecimalFormat df = new DecimalFormat(",##0.00");

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

	@EventHandler
	public void onShowEvent(ShowGesamtViewEvent event) {
		logger.debug("ShowDirektViewEvent erhalten");
		period = event.getPeriod();
		getView().initForm();
		getView().addHeader(period.getYear()); 
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

	public void validateChange(String newContent, int textFieldColumn,
			int textFieldRow, String destination) {
		logger.debug("" + newContent);
		try {
			df.parse(newContent).doubleValue();
		} catch (Exception e) {
			getView().setWrong(textFieldColumn, textFieldRow, true);
			return;
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

							pd.getWriteMethod().invoke(
									period,
									new Object[] { df.parse(newContent).doubleValue() });
							logger.debug("Content should be written: "
									+ (double) pd.getReadMethod()
											.invoke(period));
						} catch (IllegalAccessException
								| IllegalArgumentException
								| InvocationTargetException | ParseException e) {
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
