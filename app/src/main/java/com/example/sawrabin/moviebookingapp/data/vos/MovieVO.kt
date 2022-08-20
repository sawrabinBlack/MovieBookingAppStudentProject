package com.example.sawrabin.moviebookingapp.data.vos

import com.google.gson.annotations.SerializedName

data class MovieVO(
    @SerializedName("adult")
    val adult: Boolean?,
    @SerializedName("backdrop_path")
    val backDropPath: String?,

    @SerializedName("genre_ids")
    val genreIds: List<Int>?,
//    @SerializedName("belongs_to_collection")
//    val belongsToCollectionVO: CollectionVO?,
    @SerializedName("budget")
    val budget: Int?,
    @SerializedName("genres")
    val genres: List<GenreVO>?,
    @SerializedName("homepage")
    val homepage: String?,
    @SerializedName("id")
    val id: Int=0,
    @SerializedName("imdb_id")
    val imdbId:String?,
    @SerializedName("original_language")
    val originalLanguage: String?,
    @SerializedName("original_title")
    val originalTitle: String?,
    @SerializedName("overview")
    val overview: String?,
    @SerializedName("popularity")
    val popularity: Double?,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("media_type")
    val media_type:String?,
    @SerializedName("release_date")
    val releaseDate: String?,
    @SerializedName("runtime")
    val runtime: Int?,
    @SerializedName("tagline")
    val tagline: String?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("video")
    val video: Boolean?,
    @SerializedName("vote_average")
    val voteAverage: Double?,
    @SerializedName("vote_count")
    val voteCount: Int?
){
    fun runTimeFormattedInHourAndMin() : String{
        return "${runtime?.div(60)} hr ${runtime?.rem(60)} mins "
    }
}

