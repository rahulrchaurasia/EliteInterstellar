package com.interstellar.elite.core.model

import android.os.Parcel
import android.os.Parcelable

data class DashProductEntity (

    var ProductId: Int,
    var ProductCode: String = "",
    var img: Int,
    var title: String
    ) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readString().toString(),
            parcel.readInt(),
            parcel.readString().toString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(ProductId)
        parcel.writeString(ProductCode)
        parcel.writeInt(img)
        parcel.writeString(title)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<DashProductEntity> {
        override fun createFromParcel(parcel: Parcel): DashProductEntity {
            return DashProductEntity(parcel)
        }

        override fun newArray(size: Int): Array<DashProductEntity?> {
            return arrayOfNulls(size)
        }
    }
}

