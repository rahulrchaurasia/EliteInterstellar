package com.interstellar.elite.core.requestmodel.Rto;

import android.os.Parcel;
import android.os.Parcelable;

public class AssistanceObtainingRequestEntity implements Parcelable {


    /**
     * amount : 0
     * cityid : 1852
     * payment_status : 1
     * prodid : 132
     * rto_id :
     * transaction_id :
     * userid : 22
     * pincode : 400070
     * dl_type : ttttttt
     * dl_no : 12
     * dl_correct_name : patil
     * dl_dob : 28/04/1988
     * dl_address : test
     */

    private String amount;
    private String cityid;
    private String payment_status;
    private String prodid;


    private String ProdName;

    private String rto_id;
    private String transaction_id;
    private String userid;
    private String pincode;

    private String dl_type;
    private String dl_no;
    private String dl_correct_name;
    private String dl_dob;
    private String dl_address;



    public AssistanceObtainingRequestEntity() {
    }

    protected AssistanceObtainingRequestEntity(Parcel in) {
        amount = in.readString();
        cityid = in.readString();
        payment_status = in.readString();
        prodid = in.readString();
        ProdName = in.readString();
        rto_id = in.readString();
        transaction_id = in.readString();
        userid = in.readString();
        pincode = in.readString();
        dl_type = in.readString();
        dl_no = in.readString();
        dl_correct_name = in.readString();
        dl_dob = in.readString();
        dl_address = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(amount);
        dest.writeString(cityid);
        dest.writeString(payment_status);
        dest.writeString(prodid);
        dest.writeString(ProdName);
        dest.writeString(rto_id);
        dest.writeString(transaction_id);
        dest.writeString(userid);
        dest.writeString(pincode);
        dest.writeString(dl_type);
        dest.writeString(dl_no);
        dest.writeString(dl_correct_name);
        dest.writeString(dl_dob);
        dest.writeString(dl_address);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AssistanceObtainingRequestEntity> CREATOR = new Creator<AssistanceObtainingRequestEntity>() {
        @Override
        public AssistanceObtainingRequestEntity createFromParcel(Parcel in) {
            return new AssistanceObtainingRequestEntity(in);
        }

        @Override
        public AssistanceObtainingRequestEntity[] newArray(int size) {
            return new AssistanceObtainingRequestEntity[size];
        }
    };

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

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getDl_type() {
        return dl_type;
    }

    public void setDl_type(String dl_type) {
        this.dl_type = dl_type;
    }

    public String getDl_no() {
        return dl_no;
    }

    public void setDl_no(String dl_no) {
        this.dl_no = dl_no;
    }

    public String getDl_correct_name() {
        return dl_correct_name;
    }

    public void setDl_correct_name(String dl_correct_name) {
        this.dl_correct_name = dl_correct_name;
    }

    public String getDl_dob() {
        return dl_dob;
    }

    public void setDl_dob(String dl_dob) {
        this.dl_dob = dl_dob;
    }

    public String getDl_address() {
        return dl_address;
    }

    public void setDl_address(String dl_address) {
        this.dl_address = dl_address;
    }


}
