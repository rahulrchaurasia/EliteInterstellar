package com.interstellar.elite.core.model;

/**
 * Created by Rahul on 05/02/2018.
 */

public class UpdateOrderRequestEntity {
    /**
     * order_id : 1
     * payment_datetime : 2018-02-13 00:00:00
     * remark : Payment Success
     * transaction_id : 1234567890
     * status : 1
     * code :
     * reason :
     */

    private int order_id;
    private String payment_datetime;
    private String remark;
    private String transaction_id;
    private int status;

    private String code;
    private String reason;

    private String pg_status;
    private String pg_order_id;
    private String pg_transaction_id;
    private String pg_paymentId;
    private String pg_token_value;

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public String getPayment_datetime() {
        return payment_datetime;
    }

    public void setPayment_datetime(String payment_datetime) {
        this.payment_datetime = payment_datetime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getPg_status() {
        return pg_status;
    }

    public void setPg_status(String pg_status) {
        this.pg_status = pg_status;
    }

    public String getPg_order_id() {
        return pg_order_id;
    }

    public void setPg_order_id(String pg_order_id) {
        this.pg_order_id = pg_order_id;
    }

    public String getPg_transaction_id() {
        return pg_transaction_id;
    }

    public void setPg_transaction_id(String pg_transaction_id) {
        this.pg_transaction_id = pg_transaction_id;
    }

    public String getPg_paymentId() {
        return pg_paymentId;
    }

    public void setPg_paymentId(String pg_paymentId) {
        this.pg_paymentId = pg_paymentId;
    }

    public String getPg_token_value() {
        return pg_token_value;
    }

    public void setPg_token_value(String pg_token_value) {
        this.pg_token_value = pg_token_value;
    }

}
