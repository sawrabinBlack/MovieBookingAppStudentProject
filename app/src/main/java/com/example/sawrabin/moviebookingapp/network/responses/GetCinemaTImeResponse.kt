package com.example.sawrabin.moviebookingapp.network.responses

import com.example.sawrabin.moviebookingapp.data.vos.CinemaDayVO
import com.google.gson.annotations.SerializedName

data class GetCinemaTImeResponse(
    @SerializedName("data")
    val data : List<CinemaDayVO>?
) {
}