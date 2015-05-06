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
import dhbw.ka.mwi.businesshorizon2.models.Period.CashFlowPeriod;
import dhbw.ka.mwi.businesshorizon2.models.Period.GesamtkostenVerfahrenCashflowPeriod;
import dhbw.ka.mwi.businesshorizon2.models.PeriodContainer.CashFlowPeriodContainer;
import dhbw.ka.mwi.businesshorizon2.models.PeriodContainer.GesamtkostenVerfahrenCashflowPeriodContainer;
import dhbw.ka.mwi.businesshorizon2.services.proxies.ProjectProxy;
import dhbw.ka.mwi.businesshorizon2.ui.periodscreen.ShowGKVEvent;

/**
 * Der Presenter fuer die Maske des Prozessschrittes zur Eingabe der Perioden.
 * 
 * @author Marcel Rosenberger
 * 
 */

public class GesamtkostenVerfahrenPresenter extends Presenter<GesamtkostenVerfahrenViewInterface> {
	private static final long serialVersionUID = 1L;
	
	public enum Type {
		UMSATZERLOESE, BESTANDERHOEHUNG, BESTANDMINDERUNG, MATERIALAUFWAND, LOEHNE, EINSTELLUNGKOSTEN, PENSIONRUECKSTELLUNG, SONSTIGPERSONAL, ABSCHREIBUNGEN, SONSTIGAUFWAND, SONSTIGERTRAG, WERTPAPIERERTRAG, ZINSAUFWAND, AUSSERORDERTRAG, AUSSERORDAUFWAND
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
	 * @author Marcel Rosenberger
	 */

	@PostConstruct
	public void init() {
		eventBus.addHandler(this);
//		shownProperties = new String[] { "capitalStock", "umsatzerlöse",
//				"bestandserhöhung", "bestandsverminderung", "materialaufwand",
//				"löhne", "einstellungskosten", "pensionsrückstellungen",
//				"sonstigepersonalkosten", "abschreibungen", "sonstigerertrag",
//				"sonstigeraufwand", "wertpapiererträge",
//				"zinsenundaufwendungen", "außerordentlicheerträge",
//				"außerordentlicheaufwände" };
//		germanNamesProperties = new String[] { "Fremdkapital",
//				"Umsatzerl\u00f6se", "Bestandserh\u00f6hung",
//				"Bestandsverminderung", "Materialaufwand",
//				"L\u00f6hne und Geh\u00e4lter",
//				"Einstellungs-/Entlassungskosten",
//				"Pensionsr\u00fcckstellungen", "Sonstige Personalkosten",
//				"Abschreibungen", "Sonstiger Ertrag", "Sonstiger Aufwand",
//				"Ertr\u00e4ge aus Wertpapieren",
//				"Zinsen und \u00e4hnliche Aufwendungen",
//				"Außerordentliche Ertr\u00e4ge",
//				"Außerordentliche Aufwendungen" };
	}
	

	/**
	 * Faengt das ShowEvent ab und sorgt dafuecr das die View die benoetigten
	 * Eingabefelder erstellt und mit den bisherigen Daten befuellt.
	 * <p>
	 * Hierzu wird die Periode aus dem Event genommen und auf ihre Propertys mit
	 * vorhandenen Gettern&Settern geprueft. Die gefundenen Propertys werden als
	 * Eingabefelder zur verfuegung gestellt.
	 * <p>
	 * Wichtig ist das Stringarray "shownProperties". Dieses enthaelt die Namen
	 * der anzuzeigenden Felder.
	 * 
	 * @param event
	 */
	@EventHandler
	public void onShowEvent(ShowGKVEvent event) {
		//                processEvent(event);
		logger.debug("ShowGKVEvent abgefangen");
		getView().generateTable();
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
	
	public void setLoehne(double value, int year){
		setValue(value, year, Type.LOEHNE);
	}
	
	public void setEinstellungskosten(double value, int year){
		setValue(value, year, Type.EINSTELLUNGKOSTEN);
	}
	
	public void setPensionrueckstellungen(double value, int year){
		setValue(value, year, Type.PENSIONRUECKSTELLUNG);
	}
	
	public void setSonstigPersonal(double value, int year){
		setValue(value, year, Type.SONSTIGPERSONAL);
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

	private void setValue(double value, int year, Type typ) {
		if(project == null){
			project = projectProxy.getSelectedProject();
			stochastic = project.getProjectInputType().isStochastic();
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
				
				case LOEHNE:
					period.setLöhne(value);
					break;
					
				case EINSTELLUNGKOSTEN:
					period.setEinstellungskosten(value);
					break;
					
				case PENSIONRUECKSTELLUNG:
					period.setPensionsrückstellungen(value);
					break;
					
				case SONSTIGPERSONAL:
					period.setSonstigepersonalkosten(value);
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
			period = new GesamtkostenVerfahrenCashflowPeriod(year);
			switch (typ) {
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
			
			case LOEHNE:
				period.setLöhne(value);
				break;
				
			case EINSTELLUNGKOSTEN:
				period.setEinstellungskosten(value);
				break;
				
			case PENSIONRUECKSTELLUNG:
				period.setPensionsrückstellungen(value);
				break;
				
			case SONSTIGPERSONAL:
				period.setSonstigepersonalkosten(value);
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
