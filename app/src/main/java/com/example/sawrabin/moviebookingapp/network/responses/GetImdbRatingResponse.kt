package com.example.sawrabin.moviebookingapp.network.responses

import com.example.sawrabin.moviebookingapp.data.vos.MovieResultVO
import com.example.sawrabin.moviebookingapp.data.vos.MovieVO
import com.google.gson.annotations.SerializedName

data class GetImdbRatingResponse(
    @SerializedName("movie_results")
    val movieResults: List<MovieVO>?,
    @SerializedName("person_results")
    val personResults: List<MovieVO>?,
    @SerializedName("tv_results")
    val tvResults: List<MovieVO>?,
    @SerializedName("tv_season_results")
    val tvSeasonResults: List<MovieVO>?,
)
