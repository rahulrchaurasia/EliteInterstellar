package com.interstellar.elite.core.model;

public  class FeedBackDisplayEntity {
        /**
         * request_id : 364
         * display_request_id : EL0364
         * feedback_comment : test
         */

        private String request_id;
        private String display_request_id;
        private String feedback_comment;

        public String getRequest_id() {
            return request_id;
        }

        public void setRequest_id(String request_id) {
            this.request_id = request_id;
        }

        public String getDisplay_request_id() {
            return display_request_id;
        }

        public void setDisplay_request_id(String display_request_id) {
            this.display_request_id = display_request_id;
        }

        public String getFeedback_comment() {
            return feedback_comment;
        }

        public void setFeedback_comment(String feedback_comment) {
            this.feedback_comment = feedback_comment;
        }
    }