package com.example.sawrabin.moviebookingapp.network

import com.example.sawrabin.moviebookingapp.network.responses.*
import com.example.sawrabin.moviebookingapp.utils.*
import retrofit2.Call
import retrofit2.http.*

interface MovieBookingApi {
    @POST(API_REGISTER)
    @FormUrlEncoded
    fun registerUser(
        @Field(PARAM_NAME) name: String,
        @Field(PARAM_EMAIL) email: String,
        @Field(PARAM_PHONE) phone: String,
        @Field(PARAM_PASSWORD) password: String

    ): Call<RegisterResponse>

    @POST(API_EMAIL_LOGIN)
    @FormUrlEncoded
    fun emailLoginUser(
        @Field(PARAM_EMAIL) email: String,
        @Field(PARAM_PASSWORD) password: String
    ): Call<RegisterResponse>

    @POST(API_USER_LOGOUT)
    fun userLogOut(
        @Header(PARAM_AUTHORIZATION) authorization: String
    ): Call<RegisterResponse>

    @GET(API_GET_PROFILE)
    fun getProfile(
        @Header(PARAM_AUTHORIZATION) authorization: String
    ): Call<RegisterResponse>

    @GET(API_GET_CINEMA_DAY_TIMESLOTS)
    fun getCinemaDayTimeSlots(
        @Header(PARAM_AUTHORIZATION) authorization: String,
        @Query(PARAM_MOVIE_ID) movieId: String,
        @Query(PARAM_DATE) date: String
    ): Call<GetCinemaTImeResponse>


    @GET(API_GET_SEATING_PLAN)
    fun getSeatingPlan(
        @Header(PARAM_AUTHORIZATION) authorization: String,
        @Query(PARAM_CINEMA_DAY_TIMESLOT_ID) cinemaTimeDaySlotId: String,
        @Query(PARAM_BOOKING_DATE) bookingDate: String,

        ): Call<GetMovieSeatResponse>

    @GET(API_GET_SNACK_LIST)
    fun getSnackList(
        @Header(PARAM_AUTHORIZATION) authorization: String
    ): Call<GetSnackResponse>

    @GET(API_GET_PAYMENT_METHODS)
    fun getPaymentMethods(
        @Header(PARAM_AUTHORIZATION) authorization: String
    ): Call<GetPaymentResponse>

    @POST(API_CREATE_CARD)
    @FormUrlEncoded
    fun createNewCard(
        @Header(PARAM_AUTHORIZATION) authorization: String,
        @Field(PARAM_CARD_NUMBER) cardNumber: String,
        @Field(PARAM_CARD_HOLDER) cardHolder: String,
        @Field(PARAM_EXPIRATION_DATE) ExpDate: String,
        @Field(PARAM_CVC) cvc: String,

        ): Call<CreateNewCardResponse>


    @POST(API_CHECK_OUT)
    fun checkOut(
        @Header(PARAM_AUTHORIZATION) authorization: String,
        @Body checkOutRequest: CheckOutRequest
    ): Call<CheckOutResponse>


}