package com.example.sawrabin.moviebookingapp.data.models

import com.example.sawrabin.moviebookingapp.data.vos.*
import com.example.sawrabin.moviebookingapp.network.CheckOutRequest
import com.example.sawrabin.moviebookingapp.network.dataagents.MovieBookingDataAgent
import com.example.sawrabin.moviebookingapp.network.dataagents.RetrofitMovieBookingDataImpl

object MovieBookingModelImpl : MovieBookingModel {
    var mToken: String? = null
    private val mMovieBookingDataAgent: MovieBookingDataAgent = RetrofitMovieBookingDataImpl
    override fun registerUser(
        name: String,
        email: String,
        phone: String,
        password: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        mMovieBookingDataAgent.registerUser(
            name = name,
            email = email,
            phone = phone,
            password = password,
            onSuccess = {
                val token = it.second
                this.mToken = token
                onSuccess()
            },
            onFailure = onFailure
        )
    }

    override fun emailLoginUser(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        mMovieBookingDataAgent.emailLoginUser(
            email = email,
            password = password,
            onSuccess = {
                this.mToken = it
                onSuccess()
            },
            onFailure = onFailure
        )
    }

    override fun userLogOut(onSuccess: () -> Unit, onFailure: (String) -> Unit) {

        mToken?.transformBearerToken()?.let {
            mMovieBookingDataAgent.userLogOut(it, onSuccess = {
                mToken = null
                onSuccess()
            }, onFailure = onFailure)
        }
    }

    override fun getProfile(onSuccess: (DataVO) -> Unit, onFailure: (String) -> Unit) {
        mToken?.transformBearerToken()?.let {
            mMovieBookingDataAgent.getProfile(
                userToken = it,
                onSuccess = onSuccess,
                onFailure = onFailure
            )
        }
    }

    override fun getNowShowing(onSuccess: (List<MovieVO>) -> Unit, onFailure: (String) -> Unit) {
        mMovieBookingDataAgent.getNowShowing(onSuccess = onSuccess, onFailure = onFailure)
    }

    override fun getUpcoming(onSuccess: (List<MovieVO>) -> Unit, onFailure: (String) -> Unit) {
        mMovieBookingDataAgent.getUpcoming(onSuccess = onSuccess, onFailure = onFailure)
    }

    override fun getMovieDetail(
        movieId: String,
        onSuccess: (MovieVO) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mMovieBookingDataAgent.getMovieDetail(
            movieId = movieId,
            onSuccess = onSuccess,
            onFailure = onFailure
        )
    }

    override fun getCredits(
        movieId: String,
        onSuccess: (List<ActorVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mMovieBookingDataAgent.getCredits(
            movieId = movieId,
            onSuccess = onSuccess,
            onFailure = onFailure
        )
    }

    override fun getCinemaDayTimeslots(
        movieId: String,
        date: String,
        onSuccess: (List<CinemaDayVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mToken?.transformBearerToken()?.let {
            mMovieBookingDataAgent.getCinemaDayTimeslots(
                userToken = it,
                movieId = movieId,
                date = date,
                onSuccess = onSuccess,
                onFailure = onFailure
            )
        }

    }

    override fun getMovieSeat(

        cinemaDayTimeslotId: String,
        bookDate: String,
        onSuccess: (List<MovieSeatVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mToken?.transformBearerToken()?.let {
            mMovieBookingDataAgent.getMovieSeat(
                userToken = it,
                cinemaDayTimeslotId = cinemaDayTimeslotId,
                bookDate = bookDate, onSuccess = onSuccess, onFailure = onFailure
            )
        }

    }

    override fun getImdbRating(
        imdbId: String,
        onSuccess: (MovieVO) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mMovieBookingDataAgent.getImdbRating(imdbId = imdbId, onSuccess = { movieList ->
            val mMovieVO = movieList.firstOrNull()
            mMovieVO?.let { onSuccess(it) }

        }, onFailure = onFailure)
    }

    override fun getSnackList(
        onSuccess: (List<SnackPaymentVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mToken?.transformBearerToken()?.let {
            mMovieBookingDataAgent.getSnackList(
                userToken = it,
                onSuccess = onSuccess,
                onFailure = onFailure
            )
        }

    }

    override fun getPaymentMethods(
        onSuccess: (List<SnackPaymentVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mToken?.transformBearerToken()?.let {
            mMovieBookingDataAgent.getPaymentMethods(
                userToken = it,
                onSuccess = onSuccess,
                onFailure = onFailure
            )
        }

    }

    override fun createNewCard(
        cardNumber: String,
        cardHolder: String,
        expDate: String,
        cvc: String,
        onSuccess: (List<CardVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mToken?.transformBearerToken()?.let {
            mMovieBookingDataAgent.createNewCard(
                userToken = it,
                cardNumber = cardNumber,
                cardHolder = cardHolder,
                expDate = expDate,
                cvc = cvc,
                onSuccess = onSuccess,
                onFailure = onFailure
            )
        }
    }

    override fun checkOut(
        cinemaDayTimeslotId: Int,
        row: String,
        seatNumber: String,
        bookingDate: String,
        movieId: Int,
        cardId: Int,
        cinemaId: Int,
        snacks: List<SnackPaymentVO>,
        onSuccess: (CheckOutSuccessVO) -> Unit,
        onFailure: (String) -> Unit
    ) {
        val mCheckOutRequest = CheckOutRequest(
            cinemaDayTimeslotId,
            row,
            seatNumber,
            bookingDate,
            movieId,
            cardId,
            cinemaId,
            snacks
        )
        mToken?.transformBearerToken()?.let {
            mMovieBookingDataAgent.checkOut(
                checkOutRequest = mCheckOutRequest,
                userToken = it,
                onSuccess = onSuccess,
                onFailure = onFailure
            )
        }

    }

//Extension Function for Bear Token
    private fun String.transformBearerToken(): String {
        return "Bearer $this"
    }
}