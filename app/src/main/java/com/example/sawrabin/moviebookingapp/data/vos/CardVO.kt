package com.example.sawrabin.moviebookingapp.data.vos

import com.google.gson.annotations.SerializedName

data class CardVO(
    var isSelected: Boolean? = false,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("card_holder")
    val cardHolder: String?,
    @SerializedName("card_number")
    val cardNumber: String?,
    @SerializedName("expiration_date")
    val expirationDate: String?,
    @SerializedName("card_type")
    val cardType: String?,
) {

    fun formatCreditCardStyle(): String {
        return cardNumber?.chunked(4)?.joinToString(" ") ?: ""
    }
}