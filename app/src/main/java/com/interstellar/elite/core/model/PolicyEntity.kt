package com.interstellar.elite.core.model

import android.os.Parcel
import android.os.Parcelable

data class PolicyEntity(

var PolicyNumber: String,
var ProductCode: String,
var RiskEndDate: String,
var RiskStartDate: String,

var InsuredName: String,
var VehicleNumber: String,
var Make: String,
var Model: String,
var PolicyStatus: String,
var ResponseStatus: String,
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(PolicyNumber)
        parcel.writeString(ProductCode)
        parcel.writeString(RiskEndDate)
        parcel.writeString(RiskStartDate)
        parcel.writeString(InsuredName)
        parcel.writeString(VehicleNumber)
        parcel.writeString(Make)
        parcel.writeString(Model)
        parcel.writeString(PolicyStatus)
        parcel.writeString(ResponseStatus)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PolicyEntity> {
        override fun createFromParcel(parcel: Parcel): PolicyEntity {
            return PolicyEntity(parcel)
        }

        override fun newArray(size: Int): Array<PolicyEntity?> {
            return arrayOfNulls(size)
        }
    }
}