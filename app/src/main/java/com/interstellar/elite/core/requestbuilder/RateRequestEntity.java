package com.interstellar.elite.core.requestbuilder;

/**
 * Created by Rahul on 08/01/2019.
 */

public class RateRequestEntity {


    /**
     * request_id : 56
     * userid : 71
     * comment : Nice Service
     * rating : 2.5
     */

    private String request_id;
    private String userid;
    private String comment;
    private String rating;

    public String getRequest_id() {
        return request_id;
    }

    public void setRequest_id(String request_id) {
        this.request_id = request_id;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}
