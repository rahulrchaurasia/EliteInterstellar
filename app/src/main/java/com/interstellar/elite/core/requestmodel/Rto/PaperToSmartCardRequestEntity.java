package com.interstellar.elite.core.requestmodel.Rto;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Nilesh Birhade on 18-12-2018.
 */

public class PaperToSmartCardRequestEntity implements Parcelable {



    /**
     * amount : 0
     * cityid : 1852
     * payment_status : 1
     * prodid : 132
     * rto_id : MH12
     * transaction_id : 1232
     * vehicleno : MH12SH3454
     * userid : 22
     * pincode : 400070
     * DL_No : 0989
     * DL_Correct_name : test
     * DL_DOB : 13/09/1994
     */
    private String ProdName;
    private String amount;
    private String cityid;
    private String payment_status;
    private String prodid;
    private String rto_id;
    private String transaction_id;
    private String vehicleno;
    private String userid;

    private String pincode;
    private String DL_No;
    private String DL_Correct_name;
    private String DL_DOB;

    public String getProdName() {
        return ProdName;
    }

    public void setProdName(String prodName) {
        ProdName = prodName;
    }

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

    public String getVehicleno() {
        return vehicleno;
    }

    public void setVehicleno(String vehicleno) {
        this.vehicleno = vehicleno;
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

    public String getDL_No() {
        return DL_No;
    }

    public void setDL_No(String DL_No) {
        this.DL_No = DL_No;
    }

    public String getDL_Correct_name() {
        return DL_Correct_name;
    }

    public void setDL_Correct_name(String DL_Correct_name) {
        this.DL_Correct_name = DL_Correct_name;
    }

    public String getDL_DOB() {
        return DL_DOB;
    }

    public void setDL_DOB(String DL_DOB) {
        this.DL_DOB = DL_DOB;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.ProdName);
        dest.writeString(this.amount);
        dest.writeString(this.cityid);
        dest.writeString(this.payment_status);
        dest.writeString(this.prodid);
        dest.writeString(this.rto_id);
        dest.writeString(this.transaction_id);
        dest.writeString(this.vehicleno);
        dest.writeString(this.userid);
        dest.writeString(this.pincode);
        dest.writeString(this.DL_No);
        dest.writeString(this.DL_Correct_name);
        dest.writeString(this.DL_DOB);
    }

    public PaperToSmartCardRequestEntity() {
    }

    protected PaperToSmartCardRequestEntity(Parcel in) {
        this.ProdName = in.readString();
        this.amount = in.readString();
        this.cityid = in.readString();
        this.payment_status = in.readString();
        this.prodid = in.readString();
        this.rto_id = in.readString();
        this.transaction_id = in.readString();
        this.vehicleno = in.readString();
        this.userid = in.readString();
        this.pincode = in.readString();
        this.DL_No = in.readString();
        this.DL_Correct_name = in.readString();
        this.DL_DOB = in.readString();
    }

    public static final Creator<PaperToSmartCardRequestEntity> CREATOR = new Creator<PaperToSmartCardRequestEntity>() {
        @Override
        public PaperToSmartCardRequestEntity createFromParcel(Parcel source) {
            return new PaperToSmartCardRequestEntity(source);
        }

        @Override
        public PaperToSmartCardRequestEntity[] newArray(int size) {
            return new PaperToSmartCardRequestEntity[size];
        }
    };
}
