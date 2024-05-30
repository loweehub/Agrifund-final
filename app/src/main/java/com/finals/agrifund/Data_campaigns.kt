
package com.finals.agrifund
import android.net.Uri
data class Data_campaigns(
    val data_img: Uri, // Assuming this is for image URI
    val data_title: String,
    val data_amt: String, // Assuming this is for amount, adjust type if necessary
    val data_location: String,
    val data_type: String,
    val data_description: String,
    val data_fullname: String,
    val data_accumulated: Long,
    val documentId: String
)

