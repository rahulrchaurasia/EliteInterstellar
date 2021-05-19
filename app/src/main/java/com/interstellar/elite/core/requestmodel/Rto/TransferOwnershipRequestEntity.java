package com.interstellar.elite.core.requestmodel.Rto;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Nilesh Birhade on 18-12-2018.
 */

public class TransferOwnershipRequestEntity implements Parcelable {


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
     * new_owner : honda
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
    private String new_owner;
    private String ProdName;

    public TransferOwnershipRequestEntity() {
    }

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

    public String getNew_owner() {
        return new_owner;
    }

    public void setNew_owner(String new_owner) {
        this.new_owner = new_owner;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.amount);
        dest.writeString(this.cityid);
        dest.writeString(this.payment_status);
        dest.writeString(this.prodid);
        dest.writeString(this.rto_id);
        dest.writeString(this.transaction_id);
        dest.writeString(this.userid);
        dest.writeString(this.vehicleno);
        dest.writeString(this.pincode);
        dest.writeString(this.new_owner);
        dest.writeString(this.ProdName);
    }

    protected TransferOwnershipRequestEntity(Parcel in) {
        this.amount = in.readString();
        this.cityid = in.readString();
        this.payment_status = in.readString();
        this.prodid = in.readString();
        this.rto_id = in.readString();
        this.transaction_id = in.readString();
        this.userid = in.readString();
        this.vehicleno = in.readString();
        this.pincode = in.readString();
        this.new_owner = in.readString();
        this.ProdName = in.readString();
    }

    public static final Creator<TransferOwnershipRequestEntity> CREATOR = new Creator<TransferOwnershipRequestEntity>() {
        @Override
        public TransferOwnershipRequestEntity createFromParcel(Parcel source) {
            return new TransferOwnershipRequestEntity(source);
        }

        @Override
        public TransferOwnershipRequestEntity[] newArray(int size) {
            return new TransferOwnershipRequestEntity[size];
        }
    };
}
