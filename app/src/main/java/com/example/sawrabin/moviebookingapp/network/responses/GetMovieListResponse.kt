package com.example.sawrabin.moviebookingapp.network.responses

import com.example.sawrabin.moviebookingapp.data.vos.MovieVO
import com.google.gson.annotations.SerializedName

data class GetMovieListResponse(
    @SerializedName("page")
    val page:Int?,
    @SerializedName("results")
    val results:List<MovieVO>?
)
