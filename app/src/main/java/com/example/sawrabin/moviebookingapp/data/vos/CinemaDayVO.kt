package com.example.sawrabin.moviebookingapp.data.vos

import com.google.gson.annotations.SerializedName

data class CinemaDayVO(
    @SerializedName("cinema_id")
    val cinemaId:Int?,
    @SerializedName("cinema")
    val cinema:String?,
    @SerializedName("timeslots")
    val timeslots:List<TimeSlotVO>?
) {
}