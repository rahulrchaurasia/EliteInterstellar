package com.interstellar.elite.core.requestmodel.Rto;

import android.os.Parcel;
import android.os.Parcelable;

public class RCRequestEntity implements Parcelable {


    /**
     * amount : 0
     * cityid : 1852
     * payment_status : 1
     * prodid : 132
     * rto_id :
     * transaction_id :
     * userid : 22
     * vehicleno : MH03AH8764
     * pincode : 400070
     * make : honda
     * model : ZB
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
    private String model;
    private String ProdName;

    protected RCRequestEntity(Parcel in) {
        amount = in.readString();
        cityid = in.readString();
        payment_status = in.readString();
        prodid = in.readString();
        rto_id = in.readString();
        transaction_id = in.readString();
        userid = in.readString();
        vehicleno = in.readString();
        pincode = in.readString();
        make = in.readString();
        model = in.readString();
        ProdName = in.readString();
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
        dest.writeString(make);
        dest.writeString(model);
        dest.writeString(ProdName);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<RCRequestEntity> CREATOR = new Creator<RCRequestEntity>() {
        @Override
        public RCRequestEntity createFromParcel(Parcel in) {
            return new RCRequestEntity(in);
        }

        @Override
        public RCRequestEntity[] newArray(int size) {
            return new RCRequestEntity[size];
        }
    };

    public String getProdName() {
        return ProdName;
    }

    public void setProdName(String prodName) {
        ProdName = prodName;
    }




    public RCRequestEntity() {
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

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }


}
