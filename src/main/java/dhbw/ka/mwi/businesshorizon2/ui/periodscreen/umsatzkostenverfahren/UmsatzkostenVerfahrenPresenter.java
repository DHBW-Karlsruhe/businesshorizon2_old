/*******************************************************************************
 * BusinessHorizon2
 *
 * Copyright (C) 
 * 2012-2013 Christian Gahlert, Florian Stier, Kai Westerholz,
 * Timo Belz, Daniel Dengler, Katharina Huber, Christian Scherer, Julius Hacker
 * 2013-2014 Marcel Rosenberger, Mirko Göpfrich, Annika Weis, Katharina Narlock, 
 * Volker Meier
 * 2014-2015 Marco Glaser, Tobias Lindner
 * 
 *
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/

package dhbw.ka.mwi.businesshorizon2.ui.periodscreen.umsatzkostenverfahren;

import java.util.Iterator;
import java.util.TreeSet;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.mvplite.event.EventBus;
import com.mvplite.event.EventHandler;
import com.mvplite.presenter.Presenter;

import dhbw.ka.mwi.businesshorizon2.models.Project;
import dhbw.ka.mwi.businesshorizon2.models.Period.UmsatzkostenVerfahrenCashflowPeriod;
import dhbw.ka.mwi.businesshorizon2.models.PeriodContainer.UmsatzkostenVerfahrenCashflowPeriodContainer;
import dhbw.ka.mwi.businesshorizon2.services.proxies.ProjectProxy;
import dhbw.ka.mwi.businesshorizon2.ui.parameterScreen.input.ValidationEvent;
import dhbw.ka.mwi.businesshorizon2.ui.periodscreen.ShowUKVEvent;

/**
 * Der Presenter fuer die Maske des Prozessschrittes zur Eingabe der Perioden.
 * 
 * @author Marco Glaser
 * 
 */

public class UmsatzkostenVerfahrenPresenter extends Presenter<UmsatzkostenVerfahrenViewInterface> {
	private static final long serialVersionUID = 1L;

	public enum Type {
		FREMDKAPITAL, UMSATZERLOESE, HERSTELLKOSTEN, VERTRIEBSKOSTEN, VERWALTUNGSKOSTEN, ABSCHREIBUNGEN, SONSTIGAUFWAND, SONSTIGERTRAG, WERTPAPIERERTRAG, BETEILIGUNGENERTRAG, ZINSERTRAG, ZINSAUFWAND, ABSCHREIBUNGENFINANZANLAGEN, AUSSERORDERTRAG, AUSSERORDAUFWAND, STEUERAUFWAND, BRUTTOINVESTITIONEN
	}

	@Autowired
	EventBus eventBus;

	private static final Logger logger = Logger.getLogger("UmsatzkostenVerfahrenPresenter.class");

	@Autowired
	private ProjectProxy projectProxy;

	private UmsatzkostenVerfahrenCashflowPeriodContainer periodContainer;

	private Project project;

	private Boolean stochastic;

	/**
	 * Dies ist der Konstruktor, der von Spring nach der Initialierung der
	 * Dependencies aufgerufen wird. Er registriert lediglich sich selbst als
	 * einen EventHandler.
	 * 
	 * @author Marco Glaser
	 */

	@PostConstruct
	public void init() {
		eventBus.addHandler(this);
		logger.debug("Initialisierung beendet");
	}

	/**
     * @author Marco Glaser
     */
	@EventHandler
	public void onShowUKVEvent(ShowUKVEvent event) {
		//                processEvent(event);
		logger.debug("ShowUKVEvent abgefangen");
		getView().generateTable();
	}

	/**
     * @author Marco Glaser
     */
	public void setFremdkapital(double value, int year){
		setValue(value, year, Type.FREMDKAPITAL);
	}

	public void setUmsatzerloese(double value, int year){
		setValue(value, year, Type.UMSATZERLOESE);
	}

	public void setHerstellkosten(double value, int year){
		setValue(value, year, Type.HERSTELLKOSTEN);
	}

	public void setVertriebskosten(double value, int year){
		setValue(value, year, Type.VERTRIEBSKOSTEN);
	}

	public void setVerwaltungskosten(double value, int year){
		setValue(value, year, Type.VERWALTUNGSKOSTEN);
	}

	public void setAbschreibungen(double value, int year){
		setValue(value, year, Type.ABSCHREIBUNGEN);
	}

	public void setSonstigAufwand(double value, int year){
		setValue(value, year, Type.SONSTIGAUFWAND);
	}

	public void setSonstigErtrag(double value, int year){
		setValue(value, year, Type.SONSTIGERTRAG);
	}

	public void setWertpapierErtrag(double value, int year){
		setValue(value, year, Type.WERTPAPIERERTRAG);
	}

	public void setZinsaufwand(double value, int year){
		setValue(value, year, Type.ZINSAUFWAND);
	}

	public void setZinsertrag(double value, int year){
		setValue(value, year, Type.ZINSERTRAG);
	}

	public void setBeteiligungenErtrag(double value, int year){
		setValue(value, year, Type.BETEILIGUNGENERTRAG);
	}

	public void setAbschreibungenFinanzanlagen(double value, int year){
		setValue(value, year, Type.ABSCHREIBUNGENFINANZANLAGEN);
	}

	public void setSteueraufwand(double value, int year){
		setValue(value, year, Type.STEUERAUFWAND);
	}

	public void setAusserordentlichErtrag(double value, int year){
		setValue(value, year, Type.AUSSERORDERTRAG);
	}

	public void setAusserordentlichAufwand(double value, int year){
		setValue(value, year, Type.AUSSERORDAUFWAND);
	}

	public void setBruttoinvestitionen(double value, int year){
		setValue(value, year, Type.BRUTTOINVESTITIONEN);
	}

	public String getFremdkapital(int year){
		return getValue(year, Type.FREMDKAPITAL);
	}

	public String getUmsatzerloese(int year){
		return getValue(year, Type.UMSATZERLOESE);
	}

	public String getHerstellkosten(int year){
		return getValue(year, Type.HERSTELLKOSTEN);
	}

	public String getVertriebskosten(int year){
		return getValue(year, Type.VERTRIEBSKOSTEN);
	}

	public String getVerwaltungskosten(int year){
		return getValue(year, Type.VERWALTUNGSKOSTEN);
	}

	public String getAbschreibungen(int year){
		return getValue(year, Type.ABSCHREIBUNGEN);
	}

	public String getSonstigAufwand(int year){
		return getValue(year, Type.SONSTIGAUFWAND);
	}

	public String getSonstigErtrag(int year){
		return getValue(year, Type.SONSTIGERTRAG);
	}

	public String getWertpapierErtrag(int year){
		return getValue(year, Type.WERTPAPIERERTRAG);
	}

	public String getZinsaufwand(int year){
		return getValue(year, Type.ZINSAUFWAND);
	}

	public String getZinsertrag(int year){
		return getValue(year, Type.ZINSERTRAG);
	}

	public String getBeteiligungenErtrag(int year){
		return getValue(year, Type.BETEILIGUNGENERTRAG);
	}

	public String getAbschreibungenFinanzanlagen(int year){
		return getValue(year, Type.ABSCHREIBUNGENFINANZANLAGEN);
	}

	public String getSteueraufwand(int year){
		return getValue(year, Type.STEUERAUFWAND);
	}

	public String getAusserordentlichErtrag(int year){
		return getValue(year, Type.AUSSERORDERTRAG);
	}

	public String getAusserordentlichAufwand(int year){
		return getValue(year, Type.AUSSERORDAUFWAND);
	}

	public String getBruttoinvestitionen(int year){
		return getValue(year, Type.BRUTTOINVESTITIONEN);
	}

	/**
     * @author Marco Glaser
     */
	public String getValue(int year, Type typ) {
		double value = 0.0;
		if(project == null){
			project = projectProxy.getSelectedProject();
			stochastic = project.getProjectInputType().isStochastic();
		}
		if(stochastic){
			
			periodContainer = (UmsatzkostenVerfahrenCashflowPeriodContainer) project.getStochasticPeriods();
		}
		else{
			
			periodContainer = (UmsatzkostenVerfahrenCashflowPeriodContainer) project.getDeterministicPeriods();
		}

		if(periodContainer != null){
			UmsatzkostenVerfahrenCashflowPeriod period;
			TreeSet<UmsatzkostenVerfahrenCashflowPeriod> periods = periodContainer.getPeriods();
			Iterator<UmsatzkostenVerfahrenCashflowPeriod> it = periods.iterator();
			while(it.hasNext()){
				period = it.next();
				if(period.getYear() == year){
					switch (typ) {
					case FREMDKAPITAL:
						value = period.getCapitalStock();
						break;

					case UMSATZERLOESE:
						value = period.getUmsatzerlöse();
						break;

					case HERSTELLKOSTEN:
						value = period.getHerstellungskosten();
						break;

					case VERTRIEBSKOSTEN:
						value = period.getVertriebskosten();
						break;

					case VERWALTUNGSKOSTEN:
						value = period.getVerwaltungskosten();
						break;

					case ABSCHREIBUNGEN:
						value = period.getAbschreibungen();
						break;

					case SONSTIGAUFWAND:
						value = period.getSonstigeraufwand();
						break;

					case SONSTIGERTRAG:
						value = period.getSonstigerertrag();
						break;

					case WERTPAPIERERTRAG:
						value = period.getWertpapiererträge();
						break;

					case ZINSAUFWAND:
						value = period.getZinsenundaufwendungen();
						break;

					case ZINSERTRAG:
						value = period.getZinsertraege();
						break;

					case BETEILIGUNGENERTRAG:
						value = period.getBeteiligungenErtraege();
						break;

					case ABSCHREIBUNGENFINANZANLAGEN:
						value = period.getAbschreibungenFinanzanlagen();
						break;

					case STEUERAUFWAND:
						value = period.getSteueraufwand();
						break;

					case AUSSERORDERTRAG:
						value = period.getAußerordentlicheerträge();
						break;

					case AUSSERORDAUFWAND:
						value = period.getAußerordentlicheaufwände();
						break;

					case BRUTTOINVESTITIONEN:
						value = period.getBruttoinvestitionen();
						break;
					}
					break;
				}
			}
		}
		return String.valueOf(value);
	}

	/**
     * @author Marco Glaser
     * 
     */
	private void setValue(double value, int year, Type typ) {
		if(project == null){
			project = projectProxy.getSelectedProject();
			stochastic = project.getProjectInputType().isStochastic();
		}
		if(stochastic){
			periodContainer = (UmsatzkostenVerfahrenCashflowPeriodContainer) project.getStochasticPeriods();
		}
		else{
			periodContainer = (UmsatzkostenVerfahrenCashflowPeriodContainer) project.getDeterministicPeriods();
		}

		if(periodContainer == null){
			periodContainer = new UmsatzkostenVerfahrenCashflowPeriodContainer();
		}
		UmsatzkostenVerfahrenCashflowPeriod period = null;
		boolean isNew = true;
		TreeSet<UmsatzkostenVerfahrenCashflowPeriod> periods = periodContainer.getPeriods();
		Iterator<UmsatzkostenVerfahrenCashflowPeriod> it = periods.iterator();
		while(it.hasNext()){
			period = it.next();
			if(period.getYear() == year){
				switch (typ) {
				case FREMDKAPITAL:
					period.setCapitalStock(value);
					break;

				case UMSATZERLOESE:
					period.setUmsatzerlöse(value);
					break;

				case HERSTELLKOSTEN:
					period.setHerstellungskosten(value);
					break;

				case VERTRIEBSKOSTEN:
					period.setVertriebskosten(value);
					break;

				case VERWALTUNGSKOSTEN:
					period.setVerwaltungskosten(value);
					break;

				case ABSCHREIBUNGEN:
					period.setAbschreibungen(value);
					break;

				case SONSTIGAUFWAND:
					period.setSonstigeraufwand(value);
					break;

				case SONSTIGERTRAG:
					period.setSonstigerertrag(value);
					break;

				case WERTPAPIERERTRAG:
					period.setWertpapiererträge(value);
					break;

				case ZINSAUFWAND:
					period.setZinsenundaufwendungen(value);
					break;

				case ZINSERTRAG:
					period.setZinsertraege(value);
					break;

				case BETEILIGUNGENERTRAG:
					period.setBeteiligungenErtraege(value);
					break;

				case ABSCHREIBUNGENFINANZANLAGEN:
					period.setAbschreibungenFinanzanlagen(value);
					break;

				case STEUERAUFWAND:
					period.setSteueraufwand(value);
					break;

				case AUSSERORDERTRAG:
					period.setAußerordentlicheerträge(value);
					break;

				case AUSSERORDAUFWAND:
					period.setAußerordentlicheaufwände(value);
					break;

				case BRUTTOINVESTITIONEN:
					period.setBruttoinvestitionen(value);
					break;
				}
				periodContainer.removePeriod(period);
				isNew = false;
				break;
			}
		}
		if(isNew){
			period = new UmsatzkostenVerfahrenCashflowPeriod(year);
			switch (typ) {
			case FREMDKAPITAL:
				period.setCapitalStock(value);
				break;

			case UMSATZERLOESE:
				period.setUmsatzerlöse(value);
				break;

			case HERSTELLKOSTEN:
				period.setHerstellungskosten(value);
				break;

			case VERTRIEBSKOSTEN:
				period.setVertriebskosten(value);
				break;

			case VERWALTUNGSKOSTEN:
				period.setVerwaltungskosten(value);
				break;

			case ABSCHREIBUNGEN:
				period.setAbschreibungen(value);
				break;

			case SONSTIGAUFWAND:
				period.setSonstigeraufwand(value);
				break;

			case SONSTIGERTRAG:
				period.setSonstigerertrag(value);
				break;

			case WERTPAPIERERTRAG:
				period.setWertpapiererträge(value);
				break;

			case ZINSAUFWAND:
				period.setZinsenundaufwendungen(value);
				break;

			case ZINSERTRAG:
				period.setZinsertraege(value);
				break;

			case BETEILIGUNGENERTRAG:
				period.setBeteiligungenErtraege(value);
				break;

			case ABSCHREIBUNGENFINANZANLAGEN:
				period.setAbschreibungenFinanzanlagen(value);
				break;

			case STEUERAUFWAND:
				period.setSteueraufwand(value);
				break;

			case AUSSERORDERTRAG:
				period.setAußerordentlicheerträge(value);
				break;

			case AUSSERORDAUFWAND:
				period.setAußerordentlicheaufwände(value);
				break;

			case BRUTTOINVESTITIONEN:
				period.setBruttoinvestitionen(value);
				break;
			}
		}
		periodContainer.addPeriod(period);
		if(stochastic){
			project.setStochasticPeriods(periodContainer);
		}
		else{
			project.setDeterministicPeriods(periodContainer);
		}
	}

		/**
		 * Die Methode überrüft, ob die Eingaben in allen Textfeldern korrekt sind.
		 * @author Tobias Lindner
		 * 
		 * @return boolean
		 * 			Sind die Eingaben valide?
		 */
		public boolean validateUKVInput () {
			if (getView().isComponentError()) {
				eventBus.fireEvent(new ValidationEvent(false));
				logger.debug("ValidationEvent(false) geworfen"); 
				return false;
			}
			else {
				if (getView().oneTextFieldIsSet()) {
					eventBus.fireEvent(new ValidationEvent(true));
					logger.debug("ValidationEvent(true) geworfen"); 
					return true;
				}
				else {
					eventBus.fireEvent(new ValidationEvent(false));
					logger.debug("ValidationEvent(false) geworfen"); 
					return false;
				}
			}

		}


}
