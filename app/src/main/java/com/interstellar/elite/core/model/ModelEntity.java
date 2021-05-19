package com.interstellar.elite.core.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class ModelEntity implements Parcelable {
    /**
     * Model : BUS
     * ModelID : 15
     * Variant : [{"Variant":"ALPSV 4/88","VariantID":12},{"Variant":"VIKING ALPSV 4/186","VariantID":13},{"Variant":"VIKING ALPSV 4/185","VariantID":3602}]
     */

    private String Model;
    private int ModelID;
    private List<VariantEntity> Variant;

    protected ModelEntity(Parcel in) {
        Model = in.readString();
        ModelID = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Model);
        dest.writeInt(ModelID);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ModelEntity> CREATOR = new Creator<ModelEntity>() {
        @Override
        public ModelEntity createFromParcel(Parcel in) {
            return new ModelEntity(in);
        }

        @Override
        public ModelEntity[] newArray(int size) {
            return new ModelEntity[size];
        }
    };

    public String getModel() {
        return Model;
    }

    public void setModel(String Model) {
        this.Model = Model;
    }

    public int getModelID() {
        return ModelID;
    }

    public void setModelID(int ModelID) {
        this.ModelID = ModelID;
    }

    public List<VariantEntity> getVariant() {
        return Variant;
    }

    public void setVariant(List<VariantEntity> Variant) {
        this.Variant = Variant;
    }

}