package com.interstellar.elite.core.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class MakeEntity implements Parcelable {
    /**
     * Make : ACCURA
     * MakeID : 1
     * Model : [{"Model":"GENX","ModelID":1,"Variant":[{"Variant":"STD","VariantID":1}]},{"Model":"YADI","ModelID":2,"Variant":[{"Variant":"STD","VariantID":2}]}]
     */

    private String Make;
    private int MakeID;
    private List<ModelEntity> Model;

    protected MakeEntity(Parcel in) {
        Make = in.readString();
        MakeID = in.readInt();
        Model = in.createTypedArrayList(ModelEntity.CREATOR);
    }

    public static final Creator<MakeEntity> CREATOR = new Creator<MakeEntity>() {
        @Override
        public MakeEntity createFromParcel(Parcel in) {
            return new MakeEntity(in);
        }

        @Override
        public MakeEntity[] newArray(int size) {
            return new MakeEntity[size];
        }
    };

    public String getMake() {
        return Make;
    }

    public void setMake(String Make) {
        this.Make = Make;
    }

    public int getMakeID() {
        return MakeID;
    }

    public void setMakeID(int MakeID) {
        this.MakeID = MakeID;
    }

    public List<ModelEntity> getModel() {
        return Model;
    }

    public void setModel(List<ModelEntity> Model) {
        this.Model = Model;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(Make);
        parcel.writeInt(MakeID);
        parcel.writeTypedList(Model);
    }
}