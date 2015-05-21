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
package dhbw.ka.mwi.businesshorizon2.ui.periodscreen.gesamtkostenverfahren;

import java.util.Iterator;
import java.util.TreeSet;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.mvplite.event.EventBus;
import com.mvplite.event.EventHandler;
import com.mvplite.presenter.Presenter;

import dhbw.ka.mwi.businesshorizon2.models.Project;
import dhbw.ka.mwi.businesshorizon2.models.Period.GesamtkostenVerfahrenCashflowPeriod;
import dhbw.ka.mwi.businesshorizon2.models.PeriodContainer.GesamtkostenVerfahrenCashflowPeriodContainer;
import dhbw.ka.mwi.businesshorizon2.services.proxies.ProjectProxy;
import dhbw.ka.mwi.businesshorizon2.ui.initialscreen.buttonsMiddle.ValidationEvent;
import dhbw.ka.mwi.businesshorizon2.ui.periodscreen.ShowGKVEvent;

/**
 * Der Presenter fuer die Maske des Prozessschrittes zur Eingabe der Perioden.
 * 
 * @author Marco Glaser, Tobias Lindner
 * 
 */

public class GesamtkostenVerfahrenPresenter extends Presenter<GesamtkostenVerfahrenViewInterface> {
	private static final long serialVersionUID = 1L;
	
	public enum Type {
		FREMDKAPITAL, UMSATZERLOESE, BESTANDERHOEHUNG, BESTANDMINDERUNG, AKTIVEIGENLEISTUNG, MATERIALAUFWAND, PERSONALAUFWAND, ABSCHREIBUNGEN, SONSTIGAUFWAND, SONSTIGERTRAG, WERTPAPIERERTRAG, BETEILIGUNGENERTRAG, ZINSERTRAG, ZINSAUFWAND, ABSCHREIBUNGENFINANZANLAGEN, AUSSERORDERTRAG, AUSSERORDAUFWAND, STEUERAUFWAND, BRUTTOINVESTITIONEN
	}

	@Autowired
	private EventBus eventBus;
	
	private static final Logger logger = Logger.getLogger("GesamtkostenVerfahrenPresenter.class");
	
	@Autowired
	private ProjectProxy projectProxy;


	private GesamtkostenVerfahrenCashflowPeriodContainer periodContainer;

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
		logger.debug("init beendet");
	}
	

	/**
	 * Faengt das ShowEvent ab und sorgt dafuecr das die View die benoetigten
	 * Eingabefelder erstellt und mit den bisherigen Daten befuellt.
	 * 
	 * @author Marco Glaser
	 * @param event
	 */
	@EventHandler
	public void onShowEvent(ShowGKVEvent event) {
		logger.debug("ShowGKVEvent abgefangen");
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
	
	public void setBestanderhoehung(double value, int year){
		setValue(value, year, Type.BESTANDERHOEHUNG);
	}

	public void setBestandverminderung(double value, int year){
		setValue(value, year, Type.BESTANDMINDERUNG);
	}
	
	public void setMaterialaufwand(double value, int year){
		setValue(value, year, Type.MATERIALAUFWAND);
	}
	
	public void setPersonalaufwand(double value, int year){
		setValue(value, year, Type.PERSONALAUFWAND);
	}
	
	public void setAktivEigenleistung(double value, int year){
		setValue(value, year, Type.AKTIVEIGENLEISTUNG);
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
	
	public String getUmsatzerloese(int year){
		return getValue(year, Type.UMSATZERLOESE);
	}
	
	public String getBestanderhoehung(int year){
		return getValue(year, Type.BESTANDERHOEHUNG);
	}
	
	public String getBestandminderung(int year){
		return getValue(year, Type.BESTANDMINDERUNG);
	}
	
	public String getMaterialaufwand(int year){
		return getValue(year, Type.MATERIALAUFWAND);
	}
	
	public String getPersonalaufwand(int year){
		return getValue(year, Type.PERSONALAUFWAND);
	}
	
	public String getAktivEigenleistung(int year){
		return getValue(year, Type.AKTIVEIGENLEISTUNG);
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
	
	public String getFremdkapital(int year){
		return getValue(year, Type.FREMDKAPITAL);
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

			periodContainer = (GesamtkostenVerfahrenCashflowPeriodContainer) project.getStochasticPeriods();
		}
		else{
			
			periodContainer = (GesamtkostenVerfahrenCashflowPeriodContainer) project.getDeterministicPeriods();
		}

		if(periodContainer != null){
			GesamtkostenVerfahrenCashflowPeriod period;
			TreeSet<GesamtkostenVerfahrenCashflowPeriod> periods = periodContainer.getPeriods();
			Iterator<GesamtkostenVerfahrenCashflowPeriod> it = periods.iterator();
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

					case BESTANDERHOEHUNG:
						value = period.getBestandserhöhung();
						break;
						
					case BESTANDMINDERUNG:
						value = period.getBestandsverminderung();
						break;
						
					case MATERIALAUFWAND:
						value = period.getMaterialaufwand();
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
						
					case AUSSERORDERTRAG:
						value = period.getAußerordentlicheerträge();
						break;
						
					case AUSSERORDAUFWAND:
						value = period.getAußerordentlicheaufwände();
						break;
						
					case ABSCHREIBUNGENFINANZANLAGEN:
						value = period.getAbschreibungenFinanzanlagen();
						break;
						
					case AKTIVEIGENLEISTUNG:
						value = period.getAktivEigenleistung();
						break;
						
					case BETEILIGUNGENERTRAG:
						value = period.getBeteiligungenErtraege();
						break;
						
					case BRUTTOINVESTITIONEN:
						value = period.getBruttoinvestitionen();
						break;
						
					case PERSONALAUFWAND:
						value = period.getPersonalaufwand();
						break;
						
					case STEUERAUFWAND:
						value = period.getSteueraufwand();
						break;
						
					case ZINSERTRAG:
						value = period.getZinsertraege();
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
     */
	private void setValue(double value, int year, Type typ) {
		if(project == null){
			project = projectProxy.getSelectedProject();
			stochastic = project.getProjectInputType().isStochastic();
		}

		if(stochastic){
			periodContainer = (GesamtkostenVerfahrenCashflowPeriodContainer) project.getStochasticPeriods();
		}
		else{
			periodContainer = (GesamtkostenVerfahrenCashflowPeriodContainer) project.getDeterministicPeriods();
		}
		
		if(periodContainer == null){
			periodContainer = new GesamtkostenVerfahrenCashflowPeriodContainer();
		}
		GesamtkostenVerfahrenCashflowPeriod period = null;
		boolean isNew = true;
		TreeSet<GesamtkostenVerfahrenCashflowPeriod> periods = periodContainer.getPeriods();
		Iterator<GesamtkostenVerfahrenCashflowPeriod> it = periods.iterator();
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

				case BESTANDERHOEHUNG:
					period.setBestandserhöhung(value);
					break;
					
				case BESTANDMINDERUNG:
					period.setBestandsverminderung(value);
					break;
					
				case MATERIALAUFWAND:
					period.setMaterialaufwand(value);
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
					
				case AUSSERORDERTRAG:
					period.setAußerordentlicheerträge(value);
					break;
					
				case AUSSERORDAUFWAND:
					period.setAußerordentlicheaufwände(value);
					break;
					
				case ABSCHREIBUNGENFINANZANLAGEN:
					period.setAbschreibungenFinanzanlagen(value);
					break;
					
				case AKTIVEIGENLEISTUNG:
					period.setAktivEigenleistung(value);
					break;
					
				case BETEILIGUNGENERTRAG:
					period.setBeteiligungenErtraege(value);
					break;
					
				case BRUTTOINVESTITIONEN:
					period.setBruttoinvestitionen(value);
					break;
					
				case PERSONALAUFWAND:
					period.setPersonalaufwand(value);
					break;
					
				case STEUERAUFWAND:
					period.setSteueraufwand(value);
					break;
					
				case ZINSERTRAG:
					period.setZinsertraege(value);
					break;
				}
				periodContainer.removePeriod(period);
				isNew = false;
				break;
			}
		}
		if(isNew){
			period = new GesamtkostenVerfahrenCashflowPeriod(year);
			switch (typ) {
			case FREMDKAPITAL:
				period.setCapitalStock(value);
				break;
			
			case UMSATZERLOESE:
				period.setUmsatzerlöse(value);
				break;

			case BESTANDERHOEHUNG:
				period.setBestandserhöhung(value);
				break;
				
			case BESTANDMINDERUNG:
				period.setBestandsverminderung(value);
				break;
				
			case MATERIALAUFWAND:
				period.setMaterialaufwand(value);
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
				
			case AUSSERORDERTRAG:
				period.setAußerordentlicheerträge(value);
				break;
				
			case AUSSERORDAUFWAND:
				period.setAußerordentlicheaufwände(value);
				break;
				
			case ABSCHREIBUNGENFINANZANLAGEN:
				period.setAbschreibungenFinanzanlagen(value);
				break;
				
			case AKTIVEIGENLEISTUNG:
				period.setAktivEigenleistung(value);
				break;
				
			case BETEILIGUNGENERTRAG:
				period.setBeteiligungenErtraege(value);
				break;
				
			case BRUTTOINVESTITIONEN:
				period.setBruttoinvestitionen(value);
				break;
				
			case PERSONALAUFWAND:
				period.setPersonalaufwand(value);
				break;
				
			case STEUERAUFWAND:
				period.setSteueraufwand(value);
				break;
				
			case ZINSERTRAG:
				period.setZinsertraege(value);
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
	public boolean validateGKVInput () {
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
