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
* Diese Klasse bildet eine Periode der indirekten Cashflow Ermittlung ab.
*
* @author Marcel Rosenberger
*
*/

public class DirectCalculatedCashflowPeriod extends Period {

        private static final long serialVersionUID = 1L;

        /**
         * Umsatzerlöse
         */
        private double umsatzErlöse;
        private boolean umsatzErlöseSet;

        /**
         * Umsatzkosten
         */
        private double umsatzKosten;
        private boolean umsatzKostenSet;
        
        /**
         * Desinvestitionen
         */
        private double desinvestitionen;
        private boolean desinvestitionenSet;
        
        /**
         * Investitionen
         */
        private double investitionen;
        private boolean investitionenSet;
        
        /**
         * EBIT
         */
        private double ebit;
        private boolean ebitSet;



        /**
         * Der Konstruktor erstellt eine Methode für das Jahr year
         *
         * @param year
         * Jahr der Periode
         * @author Marcel Rosenberger
         *
         */

        public DirectCalculatedCashflowPeriod(int year) {
                super(year);
        }

        /**
         * @return the umsatzErlöse
         */
        public double getUmsatzErlöse() {
                return umsatzErlöse;
        }

        /**
         * @param umsatzErlöse the umsatzErlöse to set
         */
        public void setUmsatzErlöse(double umsatzErlöse) {
                this.umsatzErlöse = umsatzErlöse;
                umsatzErlöseSet = true;
        }

        /**
         * @return the umsatzErlöseSet
         */
        public boolean isUmsatzErlöseSet() {
                return umsatzErlöseSet;
        }

        /**
         * @param umsatzErlöseSet the umsatzErlöseSet to set
         */
        public void setUmsatzErlöseSet(boolean umsatzErlöseSet) {
                this.umsatzErlöseSet = umsatzErlöseSet;
        }

        /**
         * @return the umsatzKosten
         */
        public double getUmsatzKosten() {
                return umsatzKosten;
        }

        /**
         * @param umsatzKosten the umsatzKosten to set
         */
        public void setUmsatzKosten(double umsatzKosten) {
                this.umsatzKosten = umsatzKosten;
                umsatzKostenSet = true;
        }

        /**
         * @return the umsatzKostenSet
         */
        public boolean isUmsatzKostenSet() {
                return umsatzKostenSet;
        }

        /**
         * @param umsatzKostenSet the umsatzKostenSet to set
         */
        public void setUmsatzKostenSet(boolean umsatzKostenSet) {
                this.umsatzKostenSet = umsatzKostenSet;
        }

		/**
		 * @return the desinvestitionen
		 */
		public double getDesinvestitionen() {
			return desinvestitionen;
		}

		/**
		 * @param desinvestitionen the desinvestitionen to set
		 */
		public void setDesinvestitionen(double desinvestitionen) {
			this.desinvestitionen = desinvestitionen;
			desinvestitionenSet = true;
		}

		/**
		 * @return the desinvestitionenSet
		 */
		public boolean isDesinvestitionenSet() {
			return desinvestitionenSet;
		}

		/**
		 * @param desinvestitionenSet the desinvestitionenSet to set
		 */
		public void setDesinvestitionenSet(boolean desinvestitionenSet) {
			this.desinvestitionenSet = desinvestitionenSet;
		}

		/**
		 * @return the investitionen
		 */
		public double getInvestitionen() {
			return investitionen;
		}

		/**
		 * @param investitionen the investitionen to set
		 */
		public void setInvestitionen(double investitionen) {
			this.investitionen = investitionen;
			investitionenSet = true;
		}

		/**
		 * @return the investitionenSet
		 */
		public boolean isInvestitionenSet() {
			return investitionenSet;
		}

		/**
		 * @param investitionenSet the investitionenSet to set
		 */
		public void setInvestitionenSet(boolean investitionenSet) {
			this.investitionenSet = investitionenSet;
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
			Double CapitalStock = this.getCapitalStock();
			Double Desinvestitionen = this.getDesinvestitionen();
			Double Ebit = this.getEbit();
			Double Investitionen = this.getInvestitionen();
			Double UmsatzErlöse = this.getUmsatzErlöse();
			Double UmsatzKosten = this.getUmsatzKosten();
			if (CapitalStock.isNaN()) {
				valid = false;
			}
			if (Desinvestitionen.isNaN()) {
				valid = false;
			}
			if (Ebit.isNaN()) {
				valid = false;
			}
			if (Investitionen.isNaN()) {
				valid = false;
			}
			if (UmsatzErlöse.isNaN()) {
				valid = false;
			}
			if (UmsatzKosten.isNaN()) {
				valid = false;
			}
			return valid;
		}



}
