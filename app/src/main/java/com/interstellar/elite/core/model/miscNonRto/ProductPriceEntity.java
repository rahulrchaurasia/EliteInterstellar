package com.interstellar.elite.core.model.miscNonRto;

public class ProductPriceEntity {
        /**
         * city_id : 898
         * price : 4200
         * TAT : 60
         * rto_id : 1145
         */

        private String city_id;
        private String price;
        private String TAT;
        private String rto_id;

        public String getCity_id() {
            return city_id;
        }

        public void setCity_id(String city_id) {
            this.city_id = city_id;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getTAT() {
            return TAT;
        }

        public void setTAT(String TAT) {
            this.TAT = TAT;
        }

        public String getRto_id() {
            return rto_id;
        }

        public void setRto_id(String rto_id) {
            this.rto_id = rto_id;
        }
    }