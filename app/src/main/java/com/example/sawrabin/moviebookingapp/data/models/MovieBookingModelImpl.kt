package com.example.sawrabin.moviebookingapp.data.models

import android.content.Context
import android.util.Log
import com.example.sawrabin.moviebookingapp.data.vos.*
import com.example.sawrabin.moviebookingapp.network.CheckOutRequest
import com.example.sawrabin.moviebookingapp.network.dataagents.MovieBookingDataAgent
import com.example.sawrabin.moviebookingapp.network.dataagents.RetrofitMovieBookingDataImpl
import com.example.sawrabin.moviebookingapp.persistence.MovieBookingDatabase
import com.google.gson.Gson

object MovieBookingModelImpl : MovieBookingModel {
    private val mMovieBookingDataAgent: MovieBookingDataAgent = RetrofitMovieBookingDataImpl
    var mMovieBookingDatabase: MovieBookingDatabase? = null

    fun isLogin(): Boolean {
        return when (mMovieBookingDatabase?.userDataDao()?.getUserdata()) {
            null -> false
            else -> true
        }

    }

    fun initDatabase(context: Context) {
        mMovieBookingDatabase = MovieBookingDatabase.getInstance(context)
    }

    override fun storeMovieDetailData(runtime: String, posterPath: String, name: String) {
        mMovieBookingDatabase?.carrierDao()
            ?.updateBookingDataWithMovieDetail(runtime, posterPath, name)
    }

    override fun insertMovieId(movieId: Int) {
        val mCarrierVO = CarrierVO(movie_id = movieId)
        mMovieBookingDatabase?.carrierDao()?.insertCarrierData(mCarrierVO)
    }

    override fun storeTimeSlotData(
        cinemaId: Int,
        cinema_name: String,
        bookDate: String,
        timeslot: Int,
        timeslot_time: String
    ) {
        mMovieBookingDatabase?.carrierDao()?.updateBookingDataWithTimeSlot(
            cinemaId,
            cinema_name,
            bookDate,
            timeslot,
            timeslot_time
        )
    }

    override fun storeBookingNo(bookingNo: String) {
        mMovieBookingDatabase?.carrierDao()?.updateBookingDataWithBookingNo(bookingNo)
    }

    override fun storeMovieSeatData(row: String, totalPrice: Int, seatNumber: String) {
        mMovieBookingDatabase?.carrierDao()?.updateBookingDataWithTime(row, totalPrice, seatNumber)
    }

    override fun storeSnackData(snack: List<SnackVO>?, totalPrice: Int) {
        val snackJson = Gson().toJson(snack)
        mMovieBookingDatabase?.carrierDao()?.updateBookingDataWithSnack(snackJson, totalPrice)
    }

    override fun getBookingData(): CarrierVO? {
        return mMovieBookingDatabase?.carrierDao()?.getBookingData()
    }

    override fun loginWithGoogle(
        accessToken: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        mMovieBookingDataAgent.loginWithGoogle(
            accessToken = accessToken,onSuccess = {
                it.first.userToken = it.second
                mMovieBookingDatabase?.userDataDao()?.insertUser(it.first)
                onSuccess()
            },
            onFailure = onFailure
        )
    }

    override fun registerUser(
        name: String,
        email: String,
        phone: String,
        password: String,
        googleAccessToken: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {

        mMovieBookingDataAgent.registerUser(
            name = name,
            email = email,
            phone = phone,
            password = password,
            googleAccessToken = googleAccessToken,
            onSuccess = {
                it.first.userToken = it.second
                mMovieBookingDatabase?.userDataDao()?.insertUser(it.first)
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
                it.first.userToken = it.second
                mMovieBookingDatabase?.userDataDao()?.insertUser(it.first)
                onSuccess()
            },
            onFailure = onFailure
        )
    }

    override fun userLogOut(onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        val userString = mMovieBookingDatabase?.userDataDao()?.getToken()
        Log.println(Log.INFO, "user", userString.toString())
        userString?.transformBearerToken()?.let {
            mMovieBookingDataAgent.userLogOut(it, onSuccess = {
                mMovieBookingDatabase?.userDataDao()?.updateUserLogout()
                onSuccess()
            }, onFailure = onFailure)
        }


    }

    override fun getProfile(onSuccess: (UserDataVO) -> Unit, onFailure: (String) -> Unit) {

        val userdata = mMovieBookingDatabase?.userDataDao()?.getUserdata()
        userdata?.let {
            onSuccess(it)
        }
        userdata?.userToken?.transformBearerToken()?.let {
            mMovieBookingDataAgent.getProfile(
                userToken = it,
                onSuccess = { userDataVO ->
                    userDataVO.userToken = userdata.userToken
                    mMovieBookingDatabase?.userDataDao()?.insertUser(userDataVO)
                    onSuccess(
                        userDataVO
                    )
                },
                onFailure = onFailure
            )
        }
    }

    override fun getNowShowing(onSuccess: (List<MovieVO>) -> Unit, onFailure: (String) -> Unit) {
        onSuccess(
            mMovieBookingDatabase?.movieDao()?.getMoviesByType(type = NOW_SHOWING) ?: listOf()
        )
        mMovieBookingDataAgent.getNowShowing(onSuccess = {
            it.forEach { movie ->
                movie.type = NOW_SHOWING
            }
            mMovieBookingDatabase?.movieDao()?.insertUser(it)
            onSuccess(it)
        }, onFailure = onFailure)
    }

    override fun getUpcoming(onSuccess: (List<MovieVO>) -> Unit, onFailure: (String) -> Unit) {
        onSuccess(
            mMovieBookingDatabase?.movieDao()?.getMoviesByType(type = UPCOMING) ?: listOf()
        )
        mMovieBookingDataAgent.getUpcoming(onSuccess = {
            it.forEach { movie ->
                movie.type = UPCOMING
            }
            mMovieBookingDatabase?.movieDao()?.insertUser(it)
            onSuccess(it)
        }, onFailure = onFailure)
    }

    override fun getMovieDetail(
        movieId: String,
        onSuccess: (MovieVO) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mMovieBookingDatabase?.movieDao()?.getMovieByID(movieId.toInt())?.let { onSuccess(it) }
        mMovieBookingDataAgent.getMovieDetail(
            movieId = movieId,
            onSuccess = {
                val movie = mMovieBookingDatabase?.movieDao()?.getMovieByID(movieId.toInt())
                it.type = movie?.type
                mMovieBookingDatabase?.movieDao()?.insertSingleMovie(it)
                onSuccess(it)
            },
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
        mMovieBookingDatabase?.timeSlotDao()?.getTimeslots(date)?.let { onSuccess(it) }

        mMovieBookingDatabase?.userDataDao()?.getToken()?.let { userToken ->
            mMovieBookingDataAgent.getCinemaDayTimeslots(
                userToken = userToken.transformBearerToken(),
                movieId = movieId,
                date = date,
                onSuccess = {
                    it.forEach { cinemaDayVO ->
                        cinemaDayVO.date = date
                    }
                    mMovieBookingDatabase?.timeSlotDao()?.deleteCinemaTimeslots(date)
                    mMovieBookingDatabase?.timeSlotDao()?.insertTimeSlot(it)
                    onSuccess(it)
                },
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
        mMovieBookingDatabase?.userDataDao()?.getToken()?.transformBearerToken()?.let {
            mMovieBookingDataAgent.getMovieSeat(
                userToken = it,
                cinemaDayTimeslotId = cinemaDayTimeslotId,
                bookDate = bookDate, onSuccess = onSuccess, onFailure = onFailure
            )
        }

    }

    override fun getImdbRating(
        movieId: String,
        imdbId: String,
        onSuccess: (Double) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mMovieBookingDatabase?.movieDao()?.getMovieByID(movieId.toInt())?.imdbRating?.let {
            onSuccess(it)
        }
        mMovieBookingDataAgent.getImdbRating(imdbId = imdbId, onSuccess = { movieList ->
            val mMovieVO = movieList.firstOrNull()
            mMovieVO?.voteAverage?.let {
                mMovieBookingDatabase?.movieDao()?.updateImdbRating(it,movieId.toInt())
                onSuccess(it) }

        }, onFailure = onFailure)
    }

    override fun getSnackList(
        onSuccess: (List<SnackVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mMovieBookingDatabase?.snackDao()?.getSnacks()?.let { onSuccess(it) }
        mMovieBookingDatabase?.userDataDao()?.getToken()?.transformBearerToken()?.let {
            mMovieBookingDataAgent.getSnackList(
                userToken = it,
                onSuccess = { payments ->
                    mMovieBookingDatabase?.snackDao()?.insertSnack(payments)
                    onSuccess(payments)
                },
                onFailure = onFailure
            )
        }

    }

    override fun getPaymentMethods(
        onSuccess: (List<PaymentVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mMovieBookingDatabase?.paymentDao()?.getPayments()?.let { onSuccess(it) }
        mMovieBookingDatabase?.userDataDao()?.getToken()?.transformBearerToken()?.let {
            mMovieBookingDataAgent.getPaymentMethods(
                userToken = it,
                onSuccess = { payments ->
                    mMovieBookingDatabase?.paymentDao()?.insertPayments(payments)
                    onSuccess(payments)
                },
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
        mMovieBookingDatabase?.userDataDao()?.getToken()?.transformBearerToken()?.let {
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
        cardId: Int,
        onSuccess: (CheckOutSuccessVO) -> Unit,
        onFailure: (String) -> Unit
    ) {
        val mCarrierData = getBookingData()

        val mCheckOutRequest = CheckOutRequest(
            mCarrierData?.timeslot,
            mCarrierData?.row,
            mCarrierData?.seatNumber,
            mCarrierData?.bookDate,
            mCarrierData?.movie_id,
            cardId,
            mCarrierData?.cinemaId,
            mCarrierData?.snack
        )
        mMovieBookingDatabase?.userDataDao()?.getToken()?.transformBearerToken()?.let {
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