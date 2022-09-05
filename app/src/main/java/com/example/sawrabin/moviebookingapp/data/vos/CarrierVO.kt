package com.example.sawrabin.moviebookingapp.data.vos

import androidx.room.*
import com.example.sawrabin.moviebookingapp.persistence.typeconverters.SnackPaymentTypeConverter
import com.google.gson.annotations.SerializedName
import java.text.SimpleDateFormat
import java.util.*

@Entity(tableName = "booking_data")
@TypeConverters(
    SnackPaymentTypeConverter::class
)
data class CarrierVO(
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    var id: Int=0,
    @SerializedName("runtime")
    @ColumnInfo(name = "runtime")
    var runtime: String? = null,
    @SerializedName("poster_path")
    @ColumnInfo(name = "poster_path")
    var posterPath: String? = null,
    @SerializedName("movie_id")
    @ColumnInfo(name = "movie_id")
    var movie_id: Int? = null,

    @SerializedName("name")
    @ColumnInfo(name = "name")
    var name: String? = null,
    @SerializedName("cinema_id")
    @ColumnInfo(name = "cinema_id")
    var cinemaId: Int? = null,
    @SerializedName("cinema_name")
    @ColumnInfo(name = "cinema_name")
    var cinemaName: String? = null,
    @SerializedName("book_date")
    @ColumnInfo(name = "book_date")
    var bookDate: String? = null,
    @SerializedName("timeslot")
    @ColumnInfo(name = "timeslot")
    var timeslot: Int? = null,
    @SerializedName("timeslot_time")
    @ColumnInfo(name = "timeslot_time")
    var timeslotTime: String? = null,
    @SerializedName("total_price")
    @ColumnInfo(name = "total_price")
    var totalPrice: Int? = null,
    @SerializedName("seat_number")
    @ColumnInfo(name = "seat_number")
    var seatNumber: String? = null,
    @SerializedName("row")
    @ColumnInfo(name = "row")
    var row: String? = null,
    @SerializedName("snack")
    @ColumnInfo(name = "snack")
    var snack: List<SnackVO>? = null,
    @SerializedName("booking_no")
    @ColumnInfo(name = "booking_no")
    var bookingNo: String? = null
) {
    fun formatDate(): String {
        val time1 = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(bookDate)
        return SimpleDateFormat("dd MMMM", Locale.ENGLISH).format(time1)
    }
}
