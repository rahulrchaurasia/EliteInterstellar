package com.interstellar.elite.core.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class RTOServiceEntity implements Parcelable {

    private int id;
    private String name;

    private String catg_id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCatg_id() {
        return catg_id;
    }

    public void setCatg_id(String catg_id) {
        this.catg_id = catg_id;
    }

    public String getProduct_logo() {
        return product_logo;
    }

    public void setProduct_logo(String product_logo) {
        this.product_logo = product_logo;
    }

    public String getProductcode() {
        return productcode;
    }

    public void setProductcode(String productcode) {
        this.productcode = productcode;
    }

    public int getParent_id() {
        return parent_id;
    }

    public void setParent_id(int parent_id) {
        this.parent_id = parent_id;
    }

    public List<RTOServiceEntity> getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(List<RTOServiceEntity> subcategory) {
        this.subcategory = subcategory;
    }

    private String product_logo;
    private String productcode;
    private int parent_id;
    private List<RTOServiceEntity> subcategory;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeString(this.catg_id);
        dest.writeString(this.product_logo);
        dest.writeString(this.productcode);
        dest.writeInt(this.parent_id);
        dest.writeList(this.subcategory);
    }

    public RTOServiceEntity() {
    }

    protected RTOServiceEntity(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.catg_id = in.readString();
        this.product_logo = in.readString();
        this.productcode = in.readString();
        this.parent_id = in.readInt();
        this.subcategory = new ArrayList<RTOServiceEntity>();
        in.readList(this.subcategory, RTOServiceEntity.class.getClassLoader());
    }

    public static final Creator<RTOServiceEntity> CREATOR = new Creator<RTOServiceEntity>() {
        @Override
        public RTOServiceEntity createFromParcel(Parcel source) {
            return new RTOServiceEntity(source);
        }

        @Override
        public RTOServiceEntity[] newArray(int size) {
            return new RTOServiceEntity[size];
        }
    };
}