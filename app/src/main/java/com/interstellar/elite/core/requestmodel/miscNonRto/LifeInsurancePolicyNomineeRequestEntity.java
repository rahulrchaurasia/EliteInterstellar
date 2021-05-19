package com.interstellar.elite.core.requestmodel.miscNonRto;

/**
 * Created by Nilesh Birhade on 14-12-2018.
 */

public class LifeInsurancePolicyNomineeRequestEntity {


    /**
     * amount : 0
     * cityid : 1852
     * payment_status : 1
     * prodid : 132
     * rto_id :
     * transaction_id :
     * userid : 22
     * pincode : 400070
     * nominee_name : test
     * relation_with_nominee : xyz
     * insure_company_name : sai
     */

    private String amount;
    private String cityid;
    private String payment_status;
    private String prodid;

    private String rto_id;
    private String transaction_id;
    private String userid;
    private String pincode;

    private String nominee_name;
    private String relation_with_nominee;
    private String insure_company_name;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCityid() {
        return cityid;
    }

    public void setCityid(String cityid) {
        this.cityid = cityid;
    }

    public String getPayment_status() {
        return payment_status;
    }

    public void setPayment_status(String payment_status) {
        this.payment_status = payment_status;
    }

    public String getProdid() {
        return prodid;
    }

    public void setProdid(String prodid) {
        this.prodid = prodid;
    }

    public String getRto_id() {
        return rto_id;
    }

    public void setRto_id(String rto_id) {
        this.rto_id = rto_id;
    }

    public String getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getNominee_name() {
        return nominee_name;
    }

    public void setNominee_name(String nominee_name) {
        this.nominee_name = nominee_name;
    }

    public String getRelation_with_nominee() {
        return relation_with_nominee;
    }

    public void setRelation_with_nominee(String relation_with_nominee) {
        this.relation_with_nominee = relation_with_nominee;
    }

    public String getInsure_company_name() {
        return insure_company_name;
    }

    public void setInsure_company_name(String insure_company_name) {
        this.insure_company_name = insure_company_name;
    }
}
