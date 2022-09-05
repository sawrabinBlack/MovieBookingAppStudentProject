package com.example.sawrabin.moviebookingapp.network.responses

import com.example.sawrabin.moviebookingapp.data.vos.PaymentVO
import com.google.gson.annotations.SerializedName

data class GetPaymentResponse(
    @SerializedName("data")
    val data : List<PaymentVO>?
) {
}