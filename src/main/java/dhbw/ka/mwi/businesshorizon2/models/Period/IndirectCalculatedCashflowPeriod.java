/*******************************************************************************
* BusinessHorizon2
*
* Copyright (C) 2012-2013 Christian Gahlert, Florian Stier, Kai Westerholz,
* Timo Belz, Daniel Dengler, Katharina Huber, Christian Scherer, Julius Hacker
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

public class IndirectCalculatedCashflowPeriod extends Period {


        private static final long serialVersionUID = 1L;
        
        

        /**
         * Jahresüberschuss
         */
        private boolean jahresÜberschussSet;
        private double jahresÜberschuss;

        /**
         * Zinsaufwand
         */
        private double zinsaufwand;
        private boolean zinsaufwandSet;
        
        /**
         * Fiktive Steuern
         */
        private double taxShield;
        private boolean taxShieldSet;

        /**
         * Nicht zahlungswirksame Aufwendungen
         */
        private double nichtZahlungswirksameAufwendungen;
        private boolean nichtZahlungswirksameAufwendungenSet;

        /**
         * Nicht zahlungswirksame Erträge
         */
        private double nichtZahlungswirksameErtraege;
        private boolean nichtZahlungswirksameErtraegeSet;
        
        /**
         * Brutto-Investitionen
         */
        private double bruttoInvestitionen;
        private boolean bruttoInvestitionenSet;
        
        /**
         * EBIT
         */
        private double ebit;
        private boolean ebitSet;
        
        


        public IndirectCalculatedCashflowPeriod(int year) {
                super(year);
        }

        /**
         * @return the jahresÜberschussSet
         */
        public boolean isJahresÜberschussSet() {
                return jahresÜberschussSet;
        }

        /**
         * @param jahresÜberschussSet the jahresÜberschussSet to set
         */
        public void setJahresÜberschussSet(boolean jahresÜberschussSet) {
                this.jahresÜberschussSet = jahresÜberschussSet;
        }

        /**
         * @return the jahresÜberschuss
         */
        public double getJahresÜberschuss() {
                return jahresÜberschuss;
        }

        /**
         * @param jahresÜberschuss the jahresÜberschuss to set
         */
        public void setJahresÜberschuss(double jahresÜberschuss) {
                this.jahresÜberschuss = jahresÜberschuss;
                jahresÜberschussSet = true;
        }

        /**
         * @return the taxShield
         */
        public double getTaxShield() {
                return taxShield;
        }

        /**
         * @param fiktiveSteuern the fiktiveSteuern to set
         */
        public void setTaxShield(double taxShield) {
                this.taxShield = taxShield;
                taxShieldSet = true;
        }

        /**
         * @return the fiktiveSteuernSet
         */
        public boolean isTaxShieldSet() {
                return taxShieldSet;
        }

        /**
         * @param fiktiveSteuernSet the fiktiveSteuernSet to set
         */
        public void setTaxShieldSet(boolean taxShieldSetSet) {
                this.taxShieldSet = taxShieldSetSet;
        }

        /**
         * @return the nichtZahlungswirksameAufwendungen
         */
        public double getNichtZahlungswirksameAufwendungen() {
                return nichtZahlungswirksameAufwendungen;
        }

        /**
         * @param nichtZahlungswirksameAufwendungen the nichtZahlungswirksameAufwendungen to set
         */
        public void setNichtZahlungswirksameAufwendungen(
                        double nichtZahlungswirksameAufwendungen) {
                this.nichtZahlungswirksameAufwendungen = nichtZahlungswirksameAufwendungen;
                nichtZahlungswirksameAufwendungenSet = true;
        }

        /**
         * @return the nichtZahlungswirksameAufwendungenSet
         */
        public boolean isNichtZahlungswirksameAufwendungenSet() {
                return nichtZahlungswirksameAufwendungenSet;
        }

        /**
         * @param nichtZahlungswirksameAufwendungenSet the nichtZahlungswirksameAufwendungenSet to set
         */
        public void setNichtZahlungswirksameAufwendungenSet(
                        boolean nichtZahlungswirksameAufwendungenSet) {
                this.nichtZahlungswirksameAufwendungenSet = nichtZahlungswirksameAufwendungenSet;
        }

        /**
         * @return the nichtZahlungswirksameErtraege
         */
        public double getNichtZahlungswirksameErtraege() {
                return nichtZahlungswirksameErtraege;
        }

        /**
         * @param nichtZahlungswirksameErtraege the nichtZahlungswirksameErtraege to set
         */
        public void setNichtZahlungswirksameErtraege(
                        double nichtZahlungswirksameErtraege) {
                this.nichtZahlungswirksameErtraege = nichtZahlungswirksameErtraege;
                nichtZahlungswirksameErtraegeSet = true;
        }

        /**
         * @return the nichtZahlungswirksameErtraegeSet
         */
        public boolean isNichtZahlungswirksameErtraegeSet() {
                return nichtZahlungswirksameErtraegeSet;
        }

        /**
         * @param nichtZahlungswirksameErtraegeSet the nichtZahlungswirksameErtraegeSet to set
         */
        public void setNichtZahlungswirksameErtraegeSet(
                        boolean nichtZahlungswirksameErtraegeSet) {
                this.nichtZahlungswirksameErtraegeSet = nichtZahlungswirksameErtraegeSet;
        }

        /**
         * @return the bruttoInvestitionen
         */
        public double getBruttoInvestitionen() {
                return bruttoInvestitionen;
        }

        /**
         * @param bruttoInvestitionen the bruttoInvestitionen to set
         */
        public void setBruttoInvestitionen(double bruttoInvestitionen) {
                this.bruttoInvestitionen = bruttoInvestitionen;
                bruttoInvestitionenSet = true;
        }

        /**
         * @return the bruttoInvestitionenSet
         */
        public boolean isBruttoInvestitionenSet() {
                return bruttoInvestitionenSet;
        }

        /**
         * @param bruttoInvestitionenSet the bruttoInvestitionenSet to set
         */
        public void setBruttoInvestitionenSet(boolean bruttoInvestitionenSet) {
                this.bruttoInvestitionenSet = bruttoInvestitionenSet;
        }

		/**
		 * @return the zinsaufwand
		 */
		public double getZinsaufwand() {
			return zinsaufwand;
		}

		/**
		 * @param zinsaufwand the zinsaufwand to set
		 */
		public void setZinsaufwand(double zinsaufwand) {
			this.zinsaufwand = zinsaufwand;
			zinsaufwandSet = true;
		}

		/**
		 * @return the zinsenSet
		 */
		public boolean isZinsaufwandSet() {
			return zinsaufwandSet;
		}

		/**
		 * @param zinsenSet the zinsenSet to set
		 */
		public void setZinsaufwandSet(boolean zinsaufwandSet) {
			this.zinsaufwandSet = zinsaufwandSet;
		}

		/**
		 * @return the ebit
		 */
		public double getEbit() {
			return ebit;
		}

		/**
		 * @param ebit the ebit to set
		 */
		public void setEbit(double ebit) {
			this.ebit = ebit;
			ebitSet = true;
		}

		/**
		 * @return the ebitSet
		 */
		public boolean isEbitSet() {
			return ebitSet;
		}

		/**
		 * @param ebitSet the ebitSet to set
		 */
		public void setEbitSet(boolean ebitSet) {
			this.ebitSet = ebitSet;
		}

		/**
		 * Gibt zurück, ob alle erforderlichen Parameter gesetzt sind
		 * 
		 * @author Annika Weis
		 */
		public boolean isValid() {
			Boolean valid = true;
			Double BruttoInvestitionen = this.getBruttoInvestitionen();
			Double CapitalStock = this.getCapitalStock();
			Double Ebit = this.getEbit();
			Double JahresÜberschuss = this.getJahresÜberschuss();
			Double NichtZahlungswirksameAufwendungen = this.getNichtZahlungswirksameAufwendungen();
			Double NichtZahlungswirksameErtraege = this.getNichtZahlungswirksameErtraege();
			Double TaxShield = this.getTaxShield();
			Double Zinsaufwand = this.getZinsaufwand();
			if (BruttoInvestitionen.isNaN()) {
				valid = false;
			}
			if (CapitalStock.isNaN()) {
				valid = false;
			}
			if (Ebit.isNaN()) {
				valid = false;
			}
			if (JahresÜberschuss.isNaN()) {
				valid = false;
			}
			if (NichtZahlungswirksameAufwendungen.isNaN()) {
				valid = false;
			}
			if (NichtZahlungswirksameErtraege.isNaN()) {
				valid = false;
			}
			if (TaxShield.isNaN()) {
				valid = false;
			}
			if (Zinsaufwand.isNaN()) {
				valid = false;
			}
			return valid;
		}

}
