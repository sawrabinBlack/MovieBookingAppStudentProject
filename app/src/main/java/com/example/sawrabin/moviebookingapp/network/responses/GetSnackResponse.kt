package com.example.sawrabin.moviebookingapp.network.responses

import com.example.sawrabin.moviebookingapp.data.vos.SnackVO
import com.google.gson.annotations.SerializedName

data class GetSnackResponse(
    @SerializedName("data")
    val data : List<SnackVO>?
)