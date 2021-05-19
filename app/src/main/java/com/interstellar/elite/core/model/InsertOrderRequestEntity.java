package com.interstellar.elite.core.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by IN-RB on 10-08-2018.
 */

public class InsertOrderRequestEntity implements Parcelable {


    /**
     * amount : 4500
     * cityid : 1276
     * payment_status : 1
     * prodid : 142
     * pucexpirydate :
     * rto_id : 1
     * subscription :
     * transaction_id :
     * userid : 14
     * vehicleno :
     * extrarequest : {"MakeNo":"MH02DV3282","ModelNo":"87564KJGH87SA4"}
     */

    private String amount;
    private String cityid;
    private String payment_status;
    private String prodid;
    private String ProdName;
    private String pucexpirydate;
    private String rto_id;
    private String subscription;
    private String transaction_id;
    private String userid;
    private String vehicleno;
    private String extrarequest;


    public InsertOrderRequestEntity() {
    }

    protected InsertOrderRequestEntity(Parcel in) {
        amount = in.readString();
        cityid = in.readString();
        payment_status = in.readString();
        prodid = in.readString();
        ProdName = in.readString();
        pucexpirydate = in.readString();
        rto_id = in.readString();
        subscription = in.readString();
        transaction_id = in.readString();
        userid = in.readString();
        vehicleno = in.readString();
        extrarequest = in.readString();
    }

    public static final Creator<InsertOrderRequestEntity> CREATOR = new Creator<InsertOrderRequestEntity>() {
        @Override
        public InsertOrderRequestEntity createFromParcel(Parcel in) {
            return new InsertOrderRequestEntity(in);
        }

        @Override
        public InsertOrderRequestEntity[] newArray(int size) {
            return new InsertOrderRequestEntity[size];
        }
    };


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

    public String getProdName() {
        return ProdName;
    }

    public void setProdName(String prodName) {
        ProdName = prodName;
    }

    public String getPucexpirydate() {
        return pucexpirydate;
    }

    public void setPucexpirydate(String pucexpirydate) {
        this.pucexpirydate = pucexpirydate;
    }

    public String getRto_id() {
        return rto_id;
    }

    public void setRto_id(String rto_id) {
        this.rto_id = rto_id;
    }

    public String getSubscription() {
        return subscription;
    }

    public void setSubscription(String subscription) {
        this.subscription = subscription;
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

    public String getExtrarequest() {
        return extrarequest;
    }

    public void setExtrarequest(String extrarequest) {
        this.extrarequest = extrarequest;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(amount);
        dest.writeString(cityid);
        dest.writeString(payment_status);
        dest.writeString(prodid);
        dest.writeString(ProdName);
        dest.writeString(pucexpirydate);
        dest.writeString(rto_id);
        dest.writeString(subscription);
        dest.writeString(transaction_id);
        dest.writeString(userid);
        dest.writeString(vehicleno);
        dest.writeString(extrarequest);
    }
}
