package com.interstellar.elite.core.requestmodel.miscNonRto;

/**
 * Created by Nilesh Birhade on 14-12-2018.
 */

public class ProductPriceRequestEntity {

    /**
     * vehicleno : KA04ML6747
     * cityid : 1276
     * product_id : 162
     * userid : 62
     * productcode : 1.1
     * make : SKODA
     * model : RAPID
     */

    private String vehicleno;
    private String cityid;
    private String product_id;
    private String userid;
    private String productcode;
    private String make;
    private String model;

    public String getVehicleno() {
        return vehicleno;
    }

    public void setVehicleno(String vehicleno) {
        this.vehicleno = vehicleno;
    }

    public String getCityid() {
        return cityid;
    }

    public void setCityid(String cityid) {
        this.cityid = cityid;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getProductcode() {
        return productcode;
    }

    public void setProductcode(String productcode) {
        this.productcode = productcode;
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
