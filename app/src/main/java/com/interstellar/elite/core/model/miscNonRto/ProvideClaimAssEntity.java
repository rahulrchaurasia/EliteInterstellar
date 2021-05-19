package com.interstellar.elite.core.model.miscNonRto;

public class ProvideClaimAssEntity {
    /**
     * id : 316
     * prod_id : 132
     * user_id : 22
     * mobile : 9773113793
     * email : testvc@gmail.com
     * name : testvc
     * amount : 111
     * displaymessage : Thanks for the payment.Your request id is: 316. Now you can view your request in Request Service Status. Our service executive team will contact you soon for further process.
     */

    private int id;
    private String prod_id;
    private String user_id;
    private String mobile;
    private String email;
    private String name;
    private String amount;
    private String displaymessage;

    public String getReceipt() {
        return receipt;
    }

    public void setReceipt(String receipt) {
        this.receipt = receipt;
    }

    private String receipt;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProd_id() {
        return prod_id;
    }

    public void setProd_id(String prod_id) {
        this.prod_id = prod_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDisplaymessage() {
        return displaymessage;
    }

    public void setDisplaymessage(String displaymessage) {
        this.displaymessage = displaymessage;
    }
}