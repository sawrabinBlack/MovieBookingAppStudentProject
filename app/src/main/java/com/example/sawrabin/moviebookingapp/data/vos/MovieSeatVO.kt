package com.example.sawrabin.moviebookingapp.data.vos

import com.google.gson.annotations.SerializedName

data class MovieSeatVO(
    var isSelected: Boolean?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("type")
    val type: String?,
    @SerializedName("seat_name")
    val seatName: String?,
    @SerializedName("symbol")
    val symbol: String?,
    @SerializedName("price")
    val price: Int?,

    ) {

    fun isMovieSeatAvailable(): Boolean {
        return type == SEAT_TYPE_AVAILABLE
    }

    fun isMovieSeatTaken(): Boolean {
        return type == SEAT_TYPE_TAKEN
    }

    fun isMovieSeatTitle(): Boolean {
        return type == SEAT_TYPE_TEXT
    }

}

const val SEAT_TYPE_AVAILABLE = "available"
const val SEAT_TYPE_TAKEN = "taken"
const val SEAT_TYPE_EMPTY = "space"
const val SEAT_TYPE_TEXT = "text"