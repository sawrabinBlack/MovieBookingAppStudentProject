package com.example.sawrabin.moviebookingapp.data.vos

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.sawrabin.moviebookingapp.persistence.typeconverters.TimeSlotTypeConverter
import com.google.gson.annotations.SerializedName
@Entity(tableName = "cinema_timeslots")
@TypeConverters(
    TimeSlotTypeConverter::class
)
data class CinemaDayVO(
    @PrimaryKey
    val id :Int?,
    @ColumnInfo(name = "date")
    var date:String?,
    @SerializedName("cinema_id")
    @ColumnInfo(name = "cinema_id")
    val cinemaId:Int?,
    @SerializedName("cinema")
    @ColumnInfo(name = "cinema")
    val cinema:String?,
    @SerializedName("timeslots")
    @ColumnInfo(name = "timeslots")
    val timeslots:List<TimeSlotVO>?
) {
}