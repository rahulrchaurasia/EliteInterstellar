package com.interstellar.elite.core.model;

public  class CompleteOrderEntity {
        /**
         * order_id : 273
         * display_order_id : EL0273
         * product_name : Obtaining Duplicate RC for existing vehicle
         * logo : http://elite.rupeeboss.com//images/icons/RTO/Ownership-Transfer-In-Vehicle-Registration-Certificate.png
         */

        private int order_id;
        private String display_order_id;
        private String product_name;
        private String logo;

        public int getOrder_id() {
            return order_id;
        }

        public void setOrder_id(int order_id) {
            this.order_id = order_id;
        }

        public String getDisplay_order_id() {
            return display_order_id;
        }

        public void setDisplay_order_id(String display_order_id) {
            this.display_order_id = display_order_id;
        }

        public String getProduct_name() {
            return product_name;
        }

        public void setProduct_name(String product_name) {
            this.product_name = product_name;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }
    }