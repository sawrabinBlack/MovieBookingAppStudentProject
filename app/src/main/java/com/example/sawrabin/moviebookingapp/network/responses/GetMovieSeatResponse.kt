package com.example.sawrabin.moviebookingapp.network.responses

import com.example.sawrabin.moviebookingapp.data.vos.MovieSeatVO
import com.google.gson.annotations.SerializedName

data class GetMovieSeatResponse(
    @SerializedName("data")
    val data: List<List<MovieSeatVO>>?
) {

    fun flattenedList(): List<MovieSeatVO>? {
        return data?.flatten()
    }


}