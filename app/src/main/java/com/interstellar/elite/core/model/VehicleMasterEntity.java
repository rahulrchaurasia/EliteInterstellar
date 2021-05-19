package com.interstellar.elite.core.model;

import java.util.List;

public class VehicleMasterEntity {
    /**
     * Make : [{"Make":"ASHOK LEYLAND","MakeID":7,"Model":[{"Model":"BUS","ModelID":15,"Variant":[{"Variant":"ALPSV 4/88","VariantID":12},{"Variant":"VIKING ALPSV 4/186","VariantID":13},{"Variant":"VIKING ALPSV 4/185","VariantID":3602}]},{"Model":"CRANE","ModelID":16,"Variant":[{"Variant":"MOBILE CRANE","VariantID":14}]},{"Model":"DOST","ModelID":17,"Variant":[{"Variant":"LE","VariantID":15},{"Variant":"LS","VariantID":16},{"Variant":"LX","VariantID":17},{"Variant":"PICK UP VAN","VariantID":3577}]},{"Model":"ECOMET","ModelID":18,"Variant":[{"Variant":"1012 STRONG","VariantID":18},{"Variant":"1212 SMART","VariantID":19},{"Variant":"1212 STRONG","VariantID":20},{"Variant":"1214 STRONG","VariantID":21},{"Variant":"1611 4x2 HAULAGE","VariantID":22},{"Variant":"910 / 3075","VariantID":3639}]},{"Model":"MIXER","ModelID":19,"Variant":null},{"Model":"STILE","ModelID":20,"Variant":[{"Variant":"LE 7 STR","VariantID":23},{"Variant":"LE 8 STR","VariantID":24},{"Variant":"LS 7 STR","VariantID":25},{"Variant":"LS 8 STR","VariantID":26},{"Variant":"LX 7 STR","VariantID":27},{"Variant":"LX 7 STR ALLOY","VariantID":28},{"Variant":"LX 8 STR","VariantID":29}]},{"Model":"TRAILER","ModelID":21,"Variant":[{"Variant":"4019","VariantID":30}]},{"Model":"TRUCK","ModelID":22,"Variant":[{"Variant":"1613","VariantID":31},{"Variant":"2516 XL TUSKER SUPER 6x2 BSIII","VariantID":32},{"Variant":"3118","VariantID":33},{"Variant":"3518","VariantID":34},{"Variant":"3518 3 STR","VariantID":35},{"Variant":"3718","VariantID":36}]},{"Model":"BOSS 1212 LE","ModelID":997,"Variant":null},{"Model":"CARGO","ModelID":998,"Variant":null},{"Model":"DOST REFRESH","ModelID":999,"Variant":null},{"Model":"FIRE TANKER","ModelID":1000,"Variant":null},{"Model":"SPRAYER","ModelID":1001,"Variant":null},{"Model":"STLN","ModelID":1002,"Variant":null},{"Model":"TAURUS","ModelID":1003,"Variant":null},{"Model":"TRANSIT MIXER","ModelID":1004,"Variant":null},{"Model":"TUSKER","ModelID":1005,"Variant":null},{"Model":"TANKER","ModelID":1203,"Variant":null}]}]
     * VehicleTypeID : 2
     */

    private int VehicleTypeID;
    private List<MakeEntity> Make;

    public int getVehicleTypeID() {
        return VehicleTypeID;
    }

    public void setVehicleTypeID(int VehicleTypeID) {
        this.VehicleTypeID = VehicleTypeID;
    }

    public List<MakeEntity> getMake() {
        return Make;
    }

    public void setMake(List<MakeEntity> Make) {
        this.Make = Make;
    }

}