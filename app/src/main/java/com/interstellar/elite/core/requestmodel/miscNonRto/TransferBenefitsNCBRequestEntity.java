package com.interstellar.elite.core.requestmodel.miscNonRto;

/**
 * Created by Nilesh Birhade on 17-12-2018.
 */

public class TransferBenefitsNCBRequestEntity {


    /**
     * amount : 0
     * cityid : 1852
     * payment_status : 1
     * prodid : 132
     * rto_id : MH18
     * transaction_id :
     * userid : 22
     * vehicleno : MH03AH8764
     * pincode : 400070
     * make : honda
     * insure_company_name : ZB
     */

    private String amount;
    private String cityid;
    private String payment_status;
    private String prodid;

    private String rto_id;
    private String transaction_id;
    private String userid;
    private String vehicleno;

    private String pincode;
    private String make;
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

    public String getVehicleno() {
        return vehicleno;
    }

    public void setVehicleno(String vehicleno) {
        this.vehicleno = vehicleno;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getInsure_company_name() {
        return insure_company_name;
    }

    public void setInsure_company_name(String insure_company_name) {
        this.insure_company_name = insure_company_name;
    }
}
