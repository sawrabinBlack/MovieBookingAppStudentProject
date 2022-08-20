package com.example.sawrabin.moviebookingapp.data.vos

import com.google.gson.annotations.SerializedName

data class SnackPaymentVO(
    var isSelected: Boolean?,
    @SerializedName("quantity")
    var quantity: Int= 0,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("price")
    val price: Int?,

    )
