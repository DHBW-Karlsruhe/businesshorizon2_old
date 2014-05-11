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
* Diese Klasse bildet eine Periode der indirekten Cashflow Ermittlung ab.
*
* @author Marcel Rosenberger
*
*/

public class GesamtkostenVerfahrenCashflowPeriod extends Period {

        private static final long serialVersionUID = 1L;


        /**
         * Umsatzerlöse
         */
        private boolean umsatzerlöseSet;
        private double umsatzerlöse;
        
        /**
         * Erhöhung des Bestandes an fertigen Erzeugnissen
         */
        private boolean bestandserhöhungSet;
        private double bestandserhöhung;
        
        /**
         * Verminderung des Bestandes an fertigen Erzeugnissen
         */
        private boolean bestandsverminderungSet;
        private double bestandsverminderung;
        
        /**
         * Materialaufwand
         */
        private boolean materialaufwandSet;
        private double materialaufwand;
        
        /**
         * Löhne/Gehälter
         */
        private boolean löhneSet;
        private double löhne;
        
        /**
         * Einstellungs-/Entlassungskosten
         */
        private boolean einstellungskostenSet;
        private double einstellungskosten;
        
        /**
         * Pensionsrückstellungen
         */
        private boolean pensionsrückstellungenSet;
        private double pensionsrückstellungen;
        
        /**
         * Sonstige Personalkosten
         */
        private boolean sonstigepersonalkostenSet;
        private double sonstigepersonalkosten;
        
        /**
         *Abschreibungen
         */
        private boolean abschreibungenSet;
        private double abschreibungen;
        
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
         * Der Konstruktor erstellt eine Methode für das Jahr year
         *
         * @param year
         * Jahr der Periode
         * @author Marcel Rosenberger
         *
         */

        public GesamtkostenVerfahrenCashflowPeriod(int year) {
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
		 * @return the bestandserhöhungSet
		 */
		public boolean isBestandserhöhungSet() {
			return bestandserhöhungSet;
		}

		/**
		 * @param bestandserhöhungSet the bestandserhöhungSet to set
		 */
		public void setBestandserhöhungSet(boolean bestandserhöhungSet) {
			this.bestandserhöhungSet = bestandserhöhungSet;
		}

		/**
		 * @return the bestandserhöhung
		 */
		public double getBestandserhöhung() {
			return bestandserhöhung;
		}

		/**
		 * @param bestandserhöhung the bestandserhöhung to set
		 */
		public void setBestandserhöhung(double bestandserhöhung) {
			this.bestandserhöhung = bestandserhöhung;
			this.setBestandserhöhungSet(true);
		}


		/**
		 * @return the bestandsverminderung
		 */
		public double getBestandsverminderung() {
			return bestandsverminderung;
		}


		/**
		 * @param bestandsverminderung the bestandsverminderung to set
		 */
		public void setBestandsverminderung(double bestandsverminderung) {
			this.bestandsverminderung = bestandsverminderung;
			this.setBestandsverminderungSet(true);
		}


		/**
		 * @return the bestandsverminderungSet
		 */
		public boolean isBestandsverminderungSet() {
			return bestandsverminderungSet;
		}


		/**
		 * @param bestandsverminderungSet the bestandsverminderungSet to set
		 */
		public void setBestandsverminderungSet(boolean bestandsverminderungSet) {
			this.bestandsverminderungSet = bestandsverminderungSet;
		}


		/**
		 * @return the materialaufwandSet
		 */
		public boolean isMaterialaufwandSet() {
			return materialaufwandSet;
		}


		/**
		 * @param materialaufwandSet the materialaufwandSet to set
		 */
		public void setMaterialaufwandSet(boolean materialaufwandSet) {
			this.materialaufwandSet = materialaufwandSet;
		}


		/**
		 * @return the materialaufwand
		 */
		public double getMaterialaufwand() {
			return materialaufwand;
		}


		/**
		 * @param materialaufwand the materialaufwand to set
		 */
		public void setMaterialaufwand(double materialaufwand) {
			this.materialaufwand = materialaufwand;
			this.setMaterialaufwandSet(true);
		}


		/**
		 * @return the löhneSet
		 */
		public boolean isLöhneSet() {
			return löhneSet;
		}


		/**
		 * @param löhneSet the löhneSet to set
		 */
		public void setLöhneSet(boolean löhneSet) {
			this.löhneSet = löhneSet;
		}


		/**
		 * @return the löhne
		 */
		public double getLöhne() {
			return löhne;
		}


		/**
		 * @param löhne the löhne to set
		 */
		public void setLöhne(double löhne) {
			this.löhne = löhne;
			this.setLöhneSet(true);
		}


		/**
		 * @return the einstellungskostenSet
		 */
		public boolean isEinstellungskostenSet() {
			return einstellungskostenSet;
		}


		/**
		 * @param einstellungskostenSet the einstellungskostenSet to set
		 */
		public void setEinstellungskostenSet(boolean einstellungskostenSet) {
			this.einstellungskostenSet = einstellungskostenSet;
		}


		/**
		 * @return the einstellungskosten
		 */
		public double getEinstellungskosten() {
			return einstellungskosten;
		}


		/**
		 * @param einstellungskosten the einstellungskosten to set
		 */
		public void setEinstellungskosten(double einstellungskosten) {
			this.einstellungskosten = einstellungskosten;
			this.setEinstellungskostenSet(true);
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


		/**
		 * @return the sonstigepersonalkostenSet
		 */
		public boolean isSonstigepersonalkostenSet() {
			return sonstigepersonalkostenSet;
		}


		/**
		 * @param sonstigepersonalkostenSet the sonstigepersonalkostenSet to set
		 */
		public void setSonstigepersonalkostenSet(boolean sonstigepersonalkostenSet) {
			this.sonstigepersonalkostenSet = sonstigepersonalkostenSet;
		}


		/**
		 * @return the sonstigepersonalkosten
		 */
		public double getSonstigepersonalkosten() {
			return sonstigepersonalkosten;
		}


		/**
		 * @param sonstigepersonalkosten the sonstigepersonalkosten to set
		 */
		public void setSonstigepersonalkosten(double sonstigepersonalkosten) {
			this.sonstigepersonalkosten = sonstigepersonalkosten;
			this.setSonstigepersonalkostenSet(true);
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



}
