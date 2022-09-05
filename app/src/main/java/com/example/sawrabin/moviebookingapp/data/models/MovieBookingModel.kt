package com.example.sawrabin.moviebookingapp.data.models

import com.example.sawrabin.moviebookingapp.data.vos.*

interface MovieBookingModel {

    fun registerUser(
        name: String,
        email: String,
        phone: String,
        password: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    )

    fun emailLoginUser(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    )

    fun userLogOut(
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    )

    fun getProfile(
        onSuccess: (UserDataVO) -> Unit,
        onFailure: (String) -> Unit
    )

    fun getNowShowing(
        onSuccess: (List<MovieVO>) -> Unit,
        onFailure: (String) -> Unit

    )

    fun getUpcoming(
        onSuccess: (List<MovieVO>) -> Unit,
        onFailure: (String) -> Unit

    )

    fun getMovieDetail(
        movieId: String,
        onSuccess: (MovieVO) -> Unit,
        onFailure: (String) -> Unit
    )

    fun getCredits(
        movieId: String,
        onSuccess: (List<ActorVO>) -> Unit,
        onFailure: (String) -> Unit
    )

    fun getCinemaDayTimeslots(
        movieId: String,
        date: String,
        onSuccess: (List<CinemaDayVO>) -> Unit,
        onFailure: (String) -> Unit
    )

    fun getMovieSeat(
        cinemaDayTimeslotId: String,
        bookDate: String,
        onSuccess: (List<MovieSeatVO>) -> Unit,
        onFailure: (String) -> Unit
    )

    fun getImdbRating(
        movieId: String,
        imdbId: String,
        onSuccess: (Double) -> Unit,
        onFailure: (String) -> Unit
    )

    fun getSnackList(
        onSuccess: (List<SnackVO>) -> Unit,
        onFailure: (String) -> Unit
    )

    fun getPaymentMethods(
        onSuccess: (List<PaymentVO>) -> Unit,
        onFailure: (String) -> Unit
    )

    fun createNewCard(
        cardNumber: String,
        cardHolder: String,
        expDate: String,
        cvc: String,
        onSuccess: (List<CardVO>) -> Unit,
        onFailure: (String) -> Unit
    )

    fun checkOut(
        cardId: Int,
        onSuccess: (CheckOutSuccessVO) -> Unit,
        onFailure: (String) -> Unit
    )

}