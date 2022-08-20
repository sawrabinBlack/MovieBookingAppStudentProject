package com.example.sawrabin.moviebookingapp.data.vos

import com.google.gson.annotations.SerializedName
import java.text.SimpleDateFormat
import java.util.*

data class CarrierVO(

    @SerializedName("runtime")
    var runtime: String? = null,
    @SerializedName("poster_path")
    var posterPath: String? = null,
    @SerializedName("movie_id")
    var movie_id: Int? = null,
    @SerializedName("name")
    var name: String? = null,
    @SerializedName("cinema_id")
    var cinemaId: Int? = null,
    @SerializedName("cinema_name")
    var cinemaName: String? = null,
    @SerializedName("book_date")
    var bookDate: String? = null,
    @SerializedName("timeslot")
    var timeslot: Int? = null,
    @SerializedName("timeslot_time")
    var timeslotTime: String? = null,
    @SerializedName("total_price")
    var totalPrice: Int? = null,
    @SerializedName("seat_number")
    var seatNumber: String? = null,
    @SerializedName("row")
    var row: String? = null,
    @SerializedName("snack")
    var snack: List<SnackPaymentVO>? = null,
    @SerializedName("booking_no")
    var bookingNo: String? = null
) {
    fun formatDate(): String {
        val time1 = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(bookDate)
        return SimpleDateFormat("dd MMMM", Locale.ENGLISH).format(time1)
    }
}
