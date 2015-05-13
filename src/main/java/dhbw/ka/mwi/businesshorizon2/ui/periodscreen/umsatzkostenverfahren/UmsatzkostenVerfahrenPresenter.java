/*******************************************************************************
 * BusinessHorizon2
 *
 * Copyright (C) 
 * 2012-2013 Christian Gahlert, Florian Stier, Kai Westerholz,
 * Timo Belz, Daniel Dengler, Katharina Huber, Christian Scherer, Julius Hacker
 * 2013-2014 Marcel Rosenberger, Mirko Göpfrich, Annika Weis, Katharina Narlock, 
 * Volker Meier
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
import dhbw.ka.mwi.businesshorizon2.models.Period.CashFlowPeriod;
import dhbw.ka.mwi.businesshorizon2.models.Period.GesamtkostenVerfahrenCashflowPeriod;
import dhbw.ka.mwi.businesshorizon2.models.Period.UmsatzkostenVerfahrenCashflowPeriod;
import dhbw.ka.mwi.businesshorizon2.models.PeriodContainer.CashFlowPeriodContainer;
import dhbw.ka.mwi.businesshorizon2.models.PeriodContainer.GesamtkostenVerfahrenCashflowPeriodContainer;
import dhbw.ka.mwi.businesshorizon2.models.PeriodContainer.UmsatzkostenVerfahrenCashflowPeriodContainer;
import dhbw.ka.mwi.businesshorizon2.services.proxies.ProjectProxy;
import dhbw.ka.mwi.businesshorizon2.ui.periodscreen.ShowGKVEvent;
import dhbw.ka.mwi.businesshorizon2.ui.periodscreen.ShowUKVEvent;

/**
 * Der Presenter fuer die Maske des Prozessschrittes zur Eingabe der Perioden.
 * 
 * @author Marcel Rosenberger
 * 
 */

public class UmsatzkostenVerfahrenPresenter extends Presenter<UmsatzkostenVerfahrenViewInterface> {
	private static final long serialVersionUID = 1L;
	
	public enum Type {
		FREMDKAPITAL, UMSATZERLOESE, HERSTELLKOSTEN, VERTRIEBSKOSTEN, FORSCHUNGSKOSTEN, VERWALTUNGSKOSTEN, PENSIONRUECKSTELLUNG, ABSCHREIBUNGEN, SONSTIGAUFWAND, SONSTIGERTRAG, WERTPAPIERERTRAG, ZINSAUFWAND, AUSSERORDERTRAG, AUSSERORDAUFWAND
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
	 * @author Marcel Rosenberger
	 */

	@PostConstruct
	public void init() {
		eventBus.addHandler(this);
		logger.debug("Logger initialisiert");
	}
	
	public String getHelp(){
		return "Der Richtige!";
	}
	
	@EventHandler
	public void onShowUKVEvent(ShowUKVEvent event) {
		//                processEvent(event);
		logger.debug("ShowUKVEvent abgefangen");
		getView().generateTable();
	}
	
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
	
	public void setForschungskosten(double value, int year){
		setValue(value, year, Type.FORSCHUNGSKOSTEN);
	}
	
	public void setVerwaltungskosten(double value, int year){
		setValue(value, year, Type.VERWALTUNGSKOSTEN);
	}
	
	public void setPensionrueckstellungen(double value, int year){
		setValue(value, year, Type.PENSIONRUECKSTELLUNG);
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
	
	public void setAusserordentlichErtrag(double value, int year){
		setValue(value, year, Type.AUSSERORDERTRAG);
	}
	
	public void setAusserordentlichAufwand(double value, int year){
		setValue(value, year, Type.AUSSERORDAUFWAND);
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
	
	public String getForschungskosten(int year){
		return getValue(year, Type.FORSCHUNGSKOSTEN);
	}
	
	public String getVerwaltungskosten(int year){
		return getValue(year, Type.VERWALTUNGSKOSTEN);
	}
	
	public String getPensionsrueckstellungen(int year){
		return getValue(year, Type.PENSIONRUECKSTELLUNG);
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
	
	public String getAusserordentlichErtrag(int year){
		return getValue(year, Type.AUSSERORDERTRAG);
	}
	
	public String getAusserordentlichAufwand(int year){
		return getValue(year, Type.AUSSERORDAUFWAND);
	}
	
	public String getValue(int year, Type typ) {
		double value = 0.0;
		if(project == null){
		project = projectProxy.getSelectedProject();
		stochastic = project.getProjectInputType().isStochastic();
		}
		if(stochastic){
			logger.debug(project.getProjectInputType().getStochasticInput());
			periodContainer = (UmsatzkostenVerfahrenCashflowPeriodContainer) project.getStochasticPeriods();
		}
		else{
			logger.debug(project.getProjectInputType().getDeterministicInput());
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
						
					case FORSCHUNGSKOSTEN:
						value = period.getForschungskosten();
						break;
					
					case VERWALTUNGSKOSTEN:
						value = period.getVerwaltungskosten();
						break;
						
					case PENSIONRUECKSTELLUNG:
						value = period.getPensionsrückstellungen();
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
					}
					break;
				}
			}
		}
		return String.valueOf(value);
	}

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
					
				case FORSCHUNGSKOSTEN:
					period.setForschungskosten(value);
					break;
				
				case VERWALTUNGSKOSTEN:
					period.setVerwaltungskosten(value);
					break;
					
				case PENSIONRUECKSTELLUNG:
					period.setPensionsrückstellungen(value);
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
				
			case FORSCHUNGSKOSTEN:
				period.setForschungskosten(value);
				break;
			
			case VERWALTUNGSKOSTEN:
				period.setVerwaltungskosten(value);
				break;
				
			case PENSIONRUECKSTELLUNG:
				period.setPensionsrückstellungen(value);
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

}
