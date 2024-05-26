//package com.finals.agrifund
//
//import android.net.Uri
//import android.os.Parcel
//import android.os.Parcelable
//
//data class Data_campaigns(
//    var data_img: Uri,
//    var data_title: String,
//    var data_amt: String,
//    var data_location: String,
//    var data_type: String,
//    var data_description: String,
//    var data_fullname: String
//) : Parcelable {
//    constructor(parcel: Parcel) : this(
//        parcel.readParcelable(Uri::class.java.classLoader)!!,
//        parcel.readString()!!,
//        parcel.readString()!!,
//        parcel.readString()!!,
//        parcel.readString()!!,
//        parcel.readString()!!,
//        parcel.readString()!!
//    )
//
//    override fun writeToParcel(parcel: Parcel, flags: Int) {
//        parcel.writeParcelable(data_img, flags)
//        parcel.writeString(data_title)
//        parcel.writeString(data_amt)
//        parcel.writeString(data_location)
//        parcel.writeString(data_type)
//        parcel.writeString(data_description)
//        parcel.writeString(data_fullname)
//    }
//
//    override fun describeContents(): Int {
//        return 0
//    }
//
//    companion object CREATOR : Parcelable.Creator<Data_campaigns> {
//        override fun createFromParcel(parcel: Parcel): Data_campaigns {
//            return Data_campaigns(parcel)
//        }
//
//        override fun newArray(size: Int): Array<Data_campaigns?> {
//            return arrayOfNulls(size)
//        }
//    }
//
//    // Convert the data class to a map for Firestore
//    fun toMap(): Map<String, Any?> {
//        return mapOf(
//            "data_img" to data_img.toString(), // Convert Uri to String for Firestore
//            "data_title" to data_title,
//            "data_amt" to data_amt,
//            "data_location" to data_location,
//            "data_type" to data_type,
//            "data_description" to data_description,
//            "data_fullname" to data_fullname
//        )
//    }
//}
//
//
package com.finals.agrifund
import android.net.Uri
data class Data_campaigns(
    val data_img: Uri, // Assuming this is for image URI
    val data_title: String,
    val data_amt: String, // Assuming this is for amount, adjust type if necessary
    val data_location: String,
    val data_type: String,
    val data_description: String,
    val data_fullname: String
)

