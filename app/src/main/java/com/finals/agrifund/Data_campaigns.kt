package com.finals.agrifund

import android.net.Uri
import android.os.Parcel
import android.os.Parcelable

data class Data_campaigns(
    var data_img: Uri,
    var data_title: String,
    var data_amt: Long,
    var data_location: String,
    var data_type: String,
    var data_description: String,
    var data_fullname: String

) :Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readParcelable(Uri::class.java.classLoader)!!,
        parcel.readString()!!,
        parcel.readLong(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(data_img, flags)
        parcel.writeString(data_title)
        parcel.writeLong(data_amt)
        parcel.writeString(data_location)
        parcel.writeString(data_type)
        parcel.writeString(data_description)
        parcel.writeString(data_fullname)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Data_campaigns> {
        override fun createFromParcel(parcel: Parcel): Data_campaigns {
            return Data_campaigns(parcel)
        }

        override fun newArray(size: Int): Array<Data_campaigns?> {
            return arrayOfNulls(size)
        }
    }
}
