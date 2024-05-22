package com.finals.agrifund

sealed class Data_Item {
    data class Details(
        var data_img: Int,
        var data_amt: Long,
        var data_title: String,
        var data_location: String,
        var data_type: String,
        var data_description: String
        ): Data_Item()

    data class User_Info(
        var user_lastname: String,
        var user_firstname: String,
        var user_occupation: String,
        var user_address: String
    ): Data_Item()
}