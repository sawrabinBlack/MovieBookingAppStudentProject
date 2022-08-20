package com.example.sawrabin.moviebookingapp.network.responses

import com.example.sawrabin.moviebookingapp.data.vos.CardVO
import com.example.sawrabin.moviebookingapp.utils.NETWORK_REQUEST_OK
import com.google.gson.annotations.SerializedName

data class CreateNewCardResponse(

    @SerializedName("code")
    val code: Int?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("data")
    val data: List<CardVO>?
){
    fun isRequestSuccessful():Boolean{
        return code == NETWORK_REQUEST_OK
    }
}
