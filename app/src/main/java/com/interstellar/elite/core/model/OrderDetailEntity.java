package com.interstellar.elite.core.model;

import android.os.Parcel;
import android.os.Parcelable;

public  class OrderDetailEntity implements Parcelable {
    /**
     * order_id : 167
     * customer_name : prd00
     * product_name :  Ownership Transfer In Vehicle Registration Certificate
     * amount : 4200.00
     * payment_status : 0
     * status : 1
     * order_status : Pending
     * payment_date : 2018-08-10 15:38:08
     * product_id : 109
     * userdoccount : 0
     * doccount : 9
     * DocumentPending : 1
     */

    private int order_id;
    private String display_order_id;
    private String customer_name;
    private String product_name;
    private String amount;
    private String payment_status;
    private String status;

    private String order_status;
    private String payment_date;
    private String product_id;
    private int userdoccount;
    private int doccount;
    private int DocumentPending;
    private String logo;
    private String rating;
    private String receipt;
    private String chat_count;


    public OrderDetailEntity() {
    }
    public String getDisplay_order_id() {
        return display_order_id;
    }

    public void setDisplay_order_id(String display_order_id) {
        this.display_order_id = display_order_id;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getPayment_status() {
        return payment_status;
    }

    public void setPayment_status(String payment_status) {
        this.payment_status = payment_status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOrder_status() {
        return order_status;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }

    public String getPayment_date() {
        return payment_date;
    }

    public void setPayment_date(String payment_date) {
        this.payment_date = payment_date;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public int getUserdoccount() {
        return userdoccount;
    }

    public void setUserdoccount(int userdoccount) {
        this.userdoccount = userdoccount;
    }

    public int getDoccount() {
        return doccount;
    }

    public void setDoccount(int doccount) {
        this.doccount = doccount;
    }

    public int getDocumentPending() {
        return DocumentPending;
    }

    public void setDocumentPending(int documentPending) {
        DocumentPending = documentPending;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getReceipt() {
        return receipt;
    }

    public void setReceipt(String receipt) {
        this.receipt = receipt;
    }

    public String getChat_count() {
        return chat_count;
    }

    public void setChat_count(String chat_count) {
        this.chat_count = chat_count;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.order_id);
        dest.writeString(this.display_order_id);
        dest.writeString(this.customer_name);
        dest.writeString(this.product_name);
        dest.writeString(this.amount);
        dest.writeString(this.payment_status);
        dest.writeString(this.status);
        dest.writeString(this.order_status);
        dest.writeString(this.payment_date);
        dest.writeString(this.product_id);
        dest.writeInt(this.userdoccount);
        dest.writeInt(this.doccount);
        dest.writeInt(this.DocumentPending);
        dest.writeString(this.logo);
        dest.writeString(this.rating);
        dest.writeString(this.receipt);
        dest.writeString(this.chat_count);
    }



    protected OrderDetailEntity(Parcel in) {
        this.order_id = in.readInt();
        this.display_order_id = in.readString();
        this.customer_name = in.readString();
        this.product_name = in.readString();
        this.amount = in.readString();
        this.payment_status = in.readString();
        this.status = in.readString();
        this.order_status = in.readString();
        this.payment_date = in.readString();
        this.product_id = in.readString();
        this.userdoccount = in.readInt();
        this.doccount = in.readInt();
        this.DocumentPending = in.readInt();
        this.logo = in.readString();
        this.rating = in.readString();
        this.receipt = in.readString();
        this.chat_count = in.readString();
    }

    public static final Creator<OrderDetailEntity> CREATOR = new Creator<OrderDetailEntity>() {
        @Override
        public OrderDetailEntity createFromParcel(Parcel source) {
            return new OrderDetailEntity(source);
        }

        @Override
        public OrderDetailEntity[] newArray(int size) {
            return new OrderDetailEntity[size];
        }
    };
}