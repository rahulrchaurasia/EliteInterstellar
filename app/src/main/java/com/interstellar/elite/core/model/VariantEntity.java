package com.interstellar.elite.core.model;

import android.os.Parcel;
import android.os.Parcelable;

public class VariantEntity implements Parcelable {
    /**
     * Variant : ALPSV 4/88
     * VariantID : 12
     */

    private String Variant;
    private int VariantID;

    protected VariantEntity(Parcel in) {
        Variant = in.readString();
        VariantID = in.readInt();
    }

    public static final Creator<VariantEntity> CREATOR = new Creator<VariantEntity>() {
        @Override
        public VariantEntity createFromParcel(Parcel in) {
            return new VariantEntity(in);
        }

        @Override
        public VariantEntity[] newArray(int size) {
            return new VariantEntity[size];
        }
    };

    public String getVariant() {
        return Variant;
    }

    public void setVariant(String Variant) {
        this.Variant = Variant;
    }

    public int getVariantID() {
        return VariantID;
    }

    public void setVariantID(int VariantID) {
        this.VariantID = VariantID;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(Variant);
        parcel.writeInt(VariantID);
    }
}