package com.interstellar.elite.core.model;

public  class NotificationEntity {
        /**
         * orderid : 
         * notifyid : 24
         * title : Document Rejected
         * body : Document rejected with flooding reason : gfgfg
         * imgurl : http://i.stack.imgur.com/CE5lz.png
         * userid : 62
         * notifyflag : DR
         * createddate : 19/11/2018
         */

        private String orderid;
        private String notifyid;
        private String title;
        private String body;
        private String imgurl;
        private String userid;
        private String notifyflag;
        private String createddate;

        public String getOrderid() {
            return orderid;
        }

        public void setOrderid(String orderid) {
            this.orderid = orderid;
        }

        public String getNotifyid() {
            return notifyid;
        }

        public void setNotifyid(String notifyid) {
            this.notifyid = notifyid;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }

        public String getImgurl() {
            return imgurl;
        }

        public void setImgurl(String imgurl) {
            this.imgurl = imgurl;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public String getNotifyflag() {
            return notifyflag;
        }

        public void setNotifyflag(String notifyflag) {
            this.notifyflag = notifyflag;
        }

        public String getCreateddate() {
            return createddate;
        }

        public void setCreateddate(String createddate) {
            this.createddate = createddate;
        }
    }