package com.example.sawrabin.moviebookingapp.data.vos

import com.google.gson.annotations.SerializedName

data class SnackVO(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("quantity")
    val quantity: Int?
)
