package com.example.sawrabin.moviebookingapp.data.vos

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
@Entity(tableName = "cards")
data class CardVO(
    var isSelected: Boolean? = false,
    @SerializedName("id")
    @PrimaryKey
    val id: Int?,
    @SerializedName("card_holder")
    @ColumnInfo(name = "card_holder")
    val cardHolder: String?,
    @SerializedName("card_number")
    @ColumnInfo(name = "card_number")
    val cardNumber: String?,
    @SerializedName("expiration_date")
    @ColumnInfo(name = "expiration_date")
    val expirationDate: String?,
    @SerializedName("card_type")
    @ColumnInfo(name = "card_type")
    val cardType: String?,
) {

    fun formatCreditCardStyle(): String {
        return cardNumber?.chunked(4)?.joinToString(" ") ?: ""
    }
}