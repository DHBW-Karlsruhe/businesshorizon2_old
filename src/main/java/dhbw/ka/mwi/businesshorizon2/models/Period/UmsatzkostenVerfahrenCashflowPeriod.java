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

package dhbw.ka.mwi.businesshorizon2.models.Period;

/**
* Diese Klasse bildet eine Periode der direkten Cashflow Ermittlung ab.
*
* @author Marcel Rosenberger
*
*/

public class UmsatzkostenVerfahrenCashflowPeriod extends Period {


        private static final long serialVersionUID = 1L;
        
        
        
        
        
        /**
         * Umsatzerlöse
         */
        private boolean umsatzerlöseSet;
        private double umsatzerlöse;
        
        /**
         * Herstellungskosten des Umsatzes
         */
        private boolean herstellungskostenSet;
        private double herstellungskosten;
        
        /**
         * Vertriebskosten
         */
        private boolean vertriebskostenSet;
        private double vertriebskosten;
        
        /**
         * Kosten F & E
         */
        private boolean forschungskostenSet;
        private double forschungskosten;
        
        /**
         * Verwaltungskosten
         */
        private boolean verwaltungskostenSet;
        private double verwaltungskosten;
        
        /**
         *Sonstiger Aufwand
         */
        private boolean sonstigeraufwandSet;
        private double sonstigeraufwand;
        
        /**
         *Sonstiger Ertrag
         */
        private boolean sonstigerertragSet;
        private double sonstigerertrag;
        
        /**
         *Erträge aus Wertpapieren
         */
        private boolean wertpapiererträgeSet;
        private double wertpapiererträge;
        
        /**
         *Zinsen und ähnliche Aufwendungen
         */
        private boolean zinsenundaufwendungenSet;
        private double zinsenundaufwendungen;
        
        /**
         *Außerordentliche Erträge
         */
        private boolean außerordentlicheerträgeSet;
        private double außerordentlicheerträge;
        
        /**
         *Außerordentliche Aufwendungen
         */
        private boolean außerordentlicheaufwändeSet;
        private double außerordentlicheaufwände;
        
        /**
         *Abschreibungen
         */
        private boolean abschreibungenSet;
        private double abschreibungen;
        
        /**
         *Pensionsrückstellungen
         */
        private boolean pensionsrückstellungenSet;
        private double pensionsrückstellungen;

       

        public UmsatzkostenVerfahrenCashflowPeriod(int year) {
                super(year);
        }

 
		/**
		 * @return the umsatzerlöseSet
		 */
		public boolean isUmsatzerlöseSet() {
			return umsatzerlöseSet;
		}

		/**
		 * @param umsatzerlöseSet the umsatzerlöseSet to set
		 */
		public void setUmsatzerlöseSet(boolean umsatzerlöseSet) {
			this.umsatzerlöseSet = umsatzerlöseSet;
		}

		/**
		 * @return the umsatzerlöse
		 */
		public double getUmsatzerlöse() {
			return umsatzerlöse;
		}

		/**
		 * @param umsatzerlöse the umsatzerlöse to set
		 */
		public void setUmsatzerlöse(double umsatzerlöse) {
			this.umsatzerlöse = umsatzerlöse;
			this.setUmsatzerlöseSet(true);
		}


		/**
		 * @return the herstellungskostenSet
		 */
		public boolean isHerstellungskostenSet() {
			return herstellungskostenSet;
		}


		/**
		 * @param herstellungskostenSet the herstellungskostenSet to set
		 */
		public void setHerstellungskostenSet(boolean herstellungskostenSet) {
			this.herstellungskostenSet = herstellungskostenSet;
		}


		/**
		 * @return the herstellungskosten
		 */
		public double getHerstellungskosten() {
			return herstellungskosten;
		}


		/**
		 * @param herstellungskosten the herstellungskosten to set
		 */
		public void setHerstellungskosten(double herstellungskosten) {
			this.herstellungskosten = herstellungskosten;
			this.setHerstellungskostenSet(true);
		}


		/**
		 * @return the vertriebskostenSet
		 */
		public boolean isVertriebskostenSet() {
			return vertriebskostenSet;
		}


		/**
		 * @param vertriebskostenSet the vertriebskostenSet to set
		 */
		public void setVertriebskostenSet(boolean vertriebskostenSet) {
			this.vertriebskostenSet = vertriebskostenSet;
		}


		/**
		 * @return the vertriebskosten
		 */
		public double getVertriebskosten() {
			return vertriebskosten;
		}


		/**
		 * @param vertriebskosten the vertriebskosten to set
		 */
		public void setVertriebskosten(double vertriebskosten) {
			this.vertriebskosten = vertriebskosten;
			this.setVertriebskostenSet(true);
		}


		/**
		 * @return the forschungskostenSet
		 */
		public boolean isForschungskostenSet() {
			return forschungskostenSet;
		}


		/**
		 * @param forschungskostenSet the forschungskostenSet to set
		 */
		public void setForschungskostenSet(boolean forschungskostenSet) {
			this.forschungskostenSet = forschungskostenSet;
		}


		/**
		 * @return the forschungskosten
		 */
		public double getForschungskosten() {
			return forschungskosten;
		}


		/**
		 * @param forschungskosten the forschungskosten to set
		 */
		public void setForschungskosten(double forschungskosten) {
			this.forschungskosten = forschungskosten;
			this.setForschungskostenSet(true);
		}


		/**
		 * @return the verwaltungskostenSet
		 */
		public boolean isVerwaltungskostenSet() {
			return verwaltungskostenSet;
		}


		/**
		 * @param verwaltungskostenSet the verwaltungskostenSet to set
		 */
		public void setVerwaltungskostenSet(boolean verwaltungskostenSet) {
			this.verwaltungskostenSet = verwaltungskostenSet;
		}


		/**
		 * @return the verwaltungskosten
		 */
		public double getVerwaltungskosten() {
			return verwaltungskosten;
		}


		/**
		 * @param verwaltungskosten the verwaltungskosten to set
		 */
		public void setVerwaltungskosten(double verwaltungskosten) {
			this.verwaltungskosten = verwaltungskosten;
			this.setVerwaltungskostenSet(true);
		}


		/**
		 * @return the sonstigeraufwand
		 */
		public double getSonstigeraufwand() {
			return sonstigeraufwand;
		}


		/**
		 * @param sonstigeraufwand the sonstigeraufwand to set
		 */
		public void setSonstigeraufwand(double sonstigeraufwand) {
			this.sonstigeraufwand = sonstigeraufwand;
			this.setSonstigeraufwandSet(true);
		}


		/**
		 * @return the sonstigeraufwandSet
		 */
		public boolean isSonstigeraufwandSet() {
			return sonstigeraufwandSet;
		}


		/**
		 * @param sonstigeraufwandSet the sonstigeraufwandSet to set
		 */
		public void setSonstigeraufwandSet(boolean sonstigeraufwandSet) {
			this.sonstigeraufwandSet = sonstigeraufwandSet;
		}


		/**
		 * @return the sonstigerertragSet
		 */
		public boolean isSonstigerertragSet() {
			return sonstigerertragSet;
		}


		/**
		 * @param sonstigerertragSet the sonstigerertragSet to set
		 */
		public void setSonstigerertragSet(boolean sonstigerertragSet) {
			this.sonstigerertragSet = sonstigerertragSet;
		}


		/**
		 * @return the sonstigerertrag
		 */
		public double getSonstigerertrag() {
			return sonstigerertrag;
		}


		/**
		 * @param sonstigerertrag the sonstigerertrag to set
		 */
		public void setSonstigerertrag(double sonstigerertrag) {
			this.sonstigerertrag = sonstigerertrag;
			this.setSonstigerertragSet(true);
		}


		/**
		 * @return the wertpapiererträgeSet
		 */
		public boolean isWertpapiererträgeSet() {
			return wertpapiererträgeSet;
		}


		/**
		 * @param wertpapiererträgeSet the wertpapiererträgeSet to set
		 */
		public void setWertpapiererträgeSet(boolean wertpapiererträgeSet) {
			this.wertpapiererträgeSet = wertpapiererträgeSet;
		}


		/**
		 * @return the wertpapiererträge
		 */
		public double getWertpapiererträge() {
			return wertpapiererträge;
		}


		/**
		 * @param wertpapiererträge the wertpapiererträge to set
		 */
		public void setWertpapiererträge(double wertpapiererträge) {
			this.wertpapiererträge = wertpapiererträge;
			this.setWertpapiererträgeSet(true);
		}


		/**
		 * @return the zinsenundaufwendungenSet
		 */
		public boolean isZinsenundaufwendungenSet() {
			return zinsenundaufwendungenSet;
		}


		/**
		 * @param zinsenundaufwendungenSet the zinsenundaufwendungenSet to set
		 */
		public void setZinsenundaufwendungenSet(boolean zinsenundaufwendungenSet) {
			this.zinsenundaufwendungenSet = zinsenundaufwendungenSet;
		}


		/**
		 * @return the zinsenundaufwendungen
		 */
		public double getZinsenundaufwendungen() {
			return zinsenundaufwendungen;
		}


		/**
		 * @param zinsenundaufwendungen the zinsenundaufwendungen to set
		 */
		public void setZinsenundaufwendungen(double zinsenundaufwendungen) {
			this.zinsenundaufwendungen = zinsenundaufwendungen;
			this.setZinsenundaufwendungenSet(true);
		}


		/**
		 * @return the außerordentlicheerträgeSet
		 */
		public boolean isAußerordentlicheerträgeSet() {
			return außerordentlicheerträgeSet;
		}


		/**
		 * @param außerordentlicheerträgeSet the außerordentlicheerträgeSet to set
		 */
		public void setAußerordentlicheerträgeSet(boolean außerordentlicheerträgeSet) {
			this.außerordentlicheerträgeSet = außerordentlicheerträgeSet;
		}


		/**
		 * @return the außerordentlicheerträge
		 */
		public double getAußerordentlicheerträge() {
			return außerordentlicheerträge;
		}


		/**
		 * @param außerordentlicheerträge the außerordentlicheerträge to set
		 */
		public void setAußerordentlicheerträge(double außerordentlicheerträge) {
			this.außerordentlicheerträge = außerordentlicheerträge;
			this.setAußerordentlicheerträgeSet(true);
		}


		/**
		 * @return the außerordentlicheaufwände
		 */
		public double getAußerordentlicheaufwände() {
			return außerordentlicheaufwände;
		}


		/**
		 * @param außerordentlicheaufwände the außerordentlicheaufwände to set
		 */
		public void setAußerordentlicheaufwände(double außerordentlicheaufwände) {
			this.außerordentlicheaufwände = außerordentlicheaufwände;
			this.setAußerordentlicheaufwändeSet(true);
		}


		/**
		 * @return the außerordentlicheaufwändeSet
		 */
		public boolean isAußerordentlicheaufwändeSet() {
			return außerordentlicheaufwändeSet;
		}


		/**
		 * @param außerordentlicheaufwändeSet the außerordentlicheaufwändeSet to set
		 */
		public void setAußerordentlicheaufwändeSet(
				boolean außerordentlicheaufwändeSet) {
			this.außerordentlicheaufwändeSet = außerordentlicheaufwändeSet;
		}


		/**
		 * @return the abschreibungenSet
		 */
		public boolean isAbschreibungenSet() {
			return abschreibungenSet;
		}


		/**
		 * @param abschreibungenSet the abschreibungenSet to set
		 */
		public void setAbschreibungenSet(boolean abschreibungenSet) {
			this.abschreibungenSet = abschreibungenSet;
		}


		/**
		 * @return the abschreibungen
		 */
		public double getAbschreibungen() {
			return abschreibungen;
		}


		/**
		 * @param abschreibungen the abschreibungen to set
		 */
		public void setAbschreibungen(double abschreibungen) {
			this.abschreibungen = abschreibungen;
			this.setAbschreibungenSet(true);
		}


		/**
		 * @return the pensionsrückstellungenSet
		 */
		public boolean isPensionsrückstellungenSet() {
			return pensionsrückstellungenSet;
		}


		/**
		 * @param pensionsrückstellungenSet the pensionsrückstellungenSet to set
		 */
		public void setPensionsrückstellungenSet(boolean pensionsrückstellungenSet) {
			this.pensionsrückstellungenSet = pensionsrückstellungenSet;
		}


		/**
		 * @return the pensionsrückstellungen
		 */
		public double getPensionsrückstellungen() {
			return pensionsrückstellungen;
		}


		/**
		 * @param pensionsrückstellungen the pensionsrückstellungen to set
		 */
		public void setPensionsrückstellungen(double pensionsrückstellungen) {
			this.pensionsrückstellungen = pensionsrückstellungen;
			this.setPensionsrückstellungenSet(true);
		}



}
