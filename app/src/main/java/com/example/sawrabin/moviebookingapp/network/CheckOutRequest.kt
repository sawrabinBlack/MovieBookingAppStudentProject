package com.example.sawrabin.moviebookingapp.network

import com.example.sawrabin.moviebookingapp.data.vos.SnackVO
import com.google.gson.annotations.SerializedName

class CheckOutRequest(
    @SerializedName("cinema_day_timeslot_id")
    val cinemaDayTimeslotId:Int?,
    @SerializedName("row")
    val row:String?,
    @SerializedName("seat_number")
    val seatNumber:String?,
    @SerializedName("booking_date")
    val bookingDate :String?,
    @SerializedName("movie_id")
    val movieId:Int?,
    @SerializedName("card_id")
    val cardId:Int?,
    @SerializedName("cinema_id")
    val cinemaId:Int?,
    @SerializedName("snacks")
    val snacks:List<SnackVO>?
) {
}



