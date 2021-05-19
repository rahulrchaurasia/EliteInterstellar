package com.interstellar.elite.core.requestmodel.Rto;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Nilesh Birhade on 18-12-2018.
 */

public class AdditionHypothecationRequestEntity implements Parcelable {


    /**
     * amount : 0
     * cityid : 1852
     * payment_status : 1
     * prodid : 132
     * rto_id : MH12
     * transaction_id : 1232
     * userid : 22
     * vehicleno : MH03AH8764
     * pincode : 400070
     * vehicle_finance_form : honda
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
    private String vehicle_finance_form;
    private String ProdName;

    protected AdditionHypothecationRequestEntity(Parcel in) {
        amount = in.readString();
        cityid = in.readString();
        payment_status = in.readString();
        prodid = in.readString();
        rto_id = in.readString();
        transaction_id = in.readString();
        userid = in.readString();
        vehicleno = in.readString();
        pincode = in.readString();
        vehicle_finance_form = in.readString();
        ProdName = in.readString();
    }

    public static final Creator<AdditionHypothecationRequestEntity> CREATOR = new Creator<AdditionHypothecationRequestEntity>() {
        @Override
        public AdditionHypothecationRequestEntity createFromParcel(Parcel in) {
            return new AdditionHypothecationRequestEntity(in);
        }

        @Override
        public AdditionHypothecationRequestEntity[] newArray(int size) {
            return new AdditionHypothecationRequestEntity[size];
        }
    };

    public String getProdName() {
        return ProdName;
    }

    public void setProdName(String prodName) {
        ProdName = prodName;
    }




    public AdditionHypothecationRequestEntity() {
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

    public String getVehicle_finance_form() {
        return vehicle_finance_form;
    }

    public void setVehicle_finance_form(String vehicle_finance_form) {
        this.vehicle_finance_form = vehicle_finance_form;
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
        dest.writeString(rto_id);
        dest.writeString(transaction_id);
        dest.writeString(userid);
        dest.writeString(vehicleno);
        dest.writeString(pincode);
        dest.writeString(vehicle_finance_form);
        dest.writeString(ProdName);
    }
}
