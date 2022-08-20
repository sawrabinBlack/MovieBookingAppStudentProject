package com.example.sawrabin.moviebookingapp.network.responses

import com.example.sawrabin.moviebookingapp.data.vos.DataVO
import com.example.sawrabin.moviebookingapp.utils.NETWORK_REQUEST_OK
import com.google.gson.annotations.SerializedName

data class RegisterResponse(
    @SerializedName("code")
    val code: Int?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("data")
    val data: DataVO?,
    @SerializedName("token")
    val token: String?
){
    fun isRequestSuccessful():Boolean{
        return code == NETWORK_REQUEST_OK
    }
}
