package com.example.sawrabin.moviebookingapp.network.responses

import com.example.sawrabin.moviebookingapp.data.vos.SnackPaymentVO
import com.google.gson.annotations.SerializedName

data class GetSnackAndPaymentResponse(
    @SerializedName("data")
    val data : List<SnackPaymentVO>?
)