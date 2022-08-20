package com.example.sawrabin.moviebookingapp.network

import com.example.sawrabin.moviebookingapp.data.vos.MovieVO
import com.example.sawrabin.moviebookingapp.network.responses.GetActorListResponse
import com.example.sawrabin.moviebookingapp.network.responses.GetImdbRatingResponse
import com.example.sawrabin.moviebookingapp.network.responses.GetMovieListResponse
import com.example.sawrabin.moviebookingapp.utils.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {
    @GET(API_GET_NOW_SHOWING)
    fun getNowShowing(
        @Query(PARAM_APIKEY) apiKey: String = MOVIE_API_KEY,
    ): Call<GetMovieListResponse>

    @GET(API_GET_UPCOMING)
    fun getUpcoming(
        @Query(PARAM_APIKEY) apiKey: String = MOVIE_API_KEY,
    ): Call<GetMovieListResponse>

    @GET("$API_GET_MOVIE_DETAIL/{movie_id}")
    fun getMovieDetail(
        @Path("movie_id") movieId: String,
        @Query(PARAM_APIKEY) apiKey: String = MOVIE_API_KEY
    ): Call<MovieVO>

    @GET("$API_GET_CREDIT/{movie_id}/credits")
    fun getCredits(
        @Path("movie_id") movieId: String,
        @Query(PARAM_APIKEY) apiKey: String = MOVIE_API_KEY
    ): Call<GetActorListResponse>

    @GET("$API_GET_IMDB/{imdb_id}")
    fun getImdbRating(
        @Path("imdb_id") imdbId: String ,
        @Query(PARAM_APIKEY) apiKey: String = MOVIE_API_KEY,
        @Query(PARAM_EXTERNAL_SOURCE) externalSource: String = "imdb_id"
    ): Call<GetImdbRatingResponse>
}