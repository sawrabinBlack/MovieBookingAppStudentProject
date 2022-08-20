package com.example.sawrabin.moviebookingapp.network.responses

import com.example.sawrabin.moviebookingapp.data.vos.ActorVO
import com.google.gson.annotations.SerializedName

data class GetActorListResponse(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("cast")
    val cast: List<ActorVO>?,
    @SerializedName("crew")
    val crew: List<ActorVO>?,
) {
}