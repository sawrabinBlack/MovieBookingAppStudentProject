package com.example.sawrabin.moviebookingapp.data.vos

import com.google.gson.annotations.SerializedName

data class CheckOutSuccessVO(
    @SerializedName("booking_no")
    val bookingNo:String?,

) {
}