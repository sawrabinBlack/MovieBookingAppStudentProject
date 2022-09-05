package com.example.sawrabin.moviebookingapp.network.dataagents

import android.util.Log
import com.example.sawrabin.moviebookingapp.data.vos.*
import com.example.sawrabin.moviebookingapp.network.CheckOutRequest
import com.example.sawrabin.moviebookingapp.network.MovieApi
import com.example.sawrabin.moviebookingapp.network.MovieBookingApi
import com.example.sawrabin.moviebookingapp.network.responses.*
import com.example.sawrabin.moviebookingapp.utils.BASE_URL_MOVIE
import com.example.sawrabin.moviebookingapp.utils.BASE_URL_MOVIE_BOOKING
import okhttp3.OkHttpClient
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object RetrofitMovieBookingDataImpl : MovieBookingDataAgent {

    private var mMovieBookingApi: MovieBookingApi? = null
    private var mMovieApi: MovieApi? = null

    init {
        val mOkHttpClient = OkHttpClient.Builder()
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .build()

        val retrofit = Retrofit.Builder()
            .client(mOkHttpClient)
            .baseUrl(BASE_URL_MOVIE_BOOKING)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        mMovieBookingApi = retrofit.create(MovieBookingApi::class.java)


        val retrofitMovieApi = Retrofit.Builder()
            .client(mOkHttpClient)
            .baseUrl((BASE_URL_MOVIE))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        mMovieApi = retrofitMovieApi.create(MovieApi::class.java)
    }

    override fun registerUser(
        name: String,
        email: String,
        phone: String,
        password: String,
        onSuccess: (Pair<UserDataVO, String>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mMovieBookingApi?.registerUser(
            name = name,
            email = email,
            phone = phone,
            password = password
        )?.enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        it.data?.let { dataVo ->
                            onSuccess(Pair(dataVo, it.token ?: ""))
                        }

                    }
                } else onFailure(response.message())
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                onFailure(t.message ?: "")
            }

        })
    }

    override fun emailLoginUser(
        email: String,
        password: String,
        onSuccess: (Pair<UserDataVO, String>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mMovieBookingApi?.emailLoginUser(email = email, password = password)
            ?.enqueue(object : Callback<RegisterResponse> {
                override fun onResponse(
                    call: Call<RegisterResponse>,
                    response: Response<RegisterResponse>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            it.data?.let { dataVo ->
                                onSuccess(Pair(dataVo, it.token ?: ""))
                            }
                        }

                    } else onFailure(response.message())
                }

                override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                    onFailure(t.message ?: " ")
                }

            })
    }

    override fun userLogOut(userToken: String, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        mMovieBookingApi?.userLogOut(userToken)?.enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        if (it.isRequestSuccessful()) {
                            onSuccess()
                        }
                    }
                } else onFailure(response.message())
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                onFailure(t.message ?: "")
            }

        })
    }

    override fun getProfile(
        userToken: String,
        onSuccess: (UserDataVO) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mMovieBookingApi?.getProfile(userToken)?.enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        it.data?.let { dataVO ->
                            onSuccess(dataVO)
                        }
                    }
                } else onFailure(response.message())
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                onFailure(t.message ?: "")
            }

        })
    }

    override fun getNowShowing(onSuccess: (List<MovieVO>) -> Unit, onFailure: (String) -> Unit) {
        mMovieApi?.getNowShowing()?.enqueue(object : Callback<GetMovieListResponse> {
            override fun onResponse(
                call: Call<GetMovieListResponse>,
                response: Response<GetMovieListResponse>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        onSuccess(it.results ?: listOf())
                    }
                } else onFailure(response.message())
            }

            override fun onFailure(call: Call<GetMovieListResponse>, t: Throwable) {
                onFailure(t.message ?: "")
            }

        }
        )
    }

    override fun getUpcoming(onSuccess: (List<MovieVO>) -> Unit, onFailure: (String) -> Unit) {
        mMovieApi?.getUpcoming()?.enqueue(object : Callback<GetMovieListResponse> {
            override fun onResponse(
                call: Call<GetMovieListResponse>,
                response: Response<GetMovieListResponse>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        onSuccess(it.results ?: listOf())
                    }
                } else onFailure(response.message())
            }

            override fun onFailure(call: Call<GetMovieListResponse>, t: Throwable) {
                onFailure(t.message ?: "")
            }

        })
    }

    override fun getMovieDetail(
        movieId: String,
        onSuccess: (MovieVO) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mMovieApi?.getMovieDetail(movieId = movieId)
            ?.enqueue(object : Callback<MovieVO> {
                override fun onResponse(call: Call<MovieVO>, response: Response<MovieVO>) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            onSuccess(it)
                        }
                    } else onFailure(response.message())
                }

                override fun onFailure(call: Call<MovieVO>, t: Throwable) {
                    onFailure(t.message ?: "")
                }

            })
    }

    override fun getCredits(
        movieId: String,
        onSuccess: (List<ActorVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mMovieApi?.getCredits(movieId)?.enqueue(object : Callback<GetActorListResponse> {
            override fun onResponse(
                call: Call<GetActorListResponse>,
                response: Response<GetActorListResponse>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        onSuccess(it.cast ?: listOf())
                    }
                } else onFailure(response.message())
            }

            override fun onFailure(call: Call<GetActorListResponse>, t: Throwable) {
                onFailure(t.message ?: "")
            }

        })
    }

    override fun getCinemaDayTimeslots(
        userToken: String,
        movieId: String,
        date: String,
        onSuccess: (List<CinemaDayVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mMovieBookingApi?.getCinemaDayTimeSlots(
            authorization = userToken,
            movieId = movieId,
            date = date
        )?.enqueue(
            object : Callback<GetCinemaTImeResponse> {
                override fun onResponse(
                    call: Call<GetCinemaTImeResponse>,
                    response: Response<GetCinemaTImeResponse>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            Log.println(Log.ERROR, "slot", it.toString())
                            onSuccess(it.data ?: listOf())
                        }
                    } else onFailure(response.message())

                }

                override fun onFailure(call: Call<GetCinemaTImeResponse>, t: Throwable) {
                    onFailure(t.message ?: "")
                }

            }
        )
    }

    override fun getMovieSeat(
        userToken: String,
        cinemaDayTimeslotId: String,
        bookDate: String,
        onSuccess: (List<MovieSeatVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mMovieBookingApi?.getSeatingPlan(
            authorization = userToken,
            cinemaTimeDaySlotId = cinemaDayTimeslotId,
            bookingDate = bookDate
        )?.enqueue(object : Callback<GetMovieSeatResponse> {
            override fun onResponse(
                call: Call<GetMovieSeatResponse>,
                response: Response<GetMovieSeatResponse>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        Log.println(Log.ERROR, "response", it.toString())
                        val seatingList = it.flattenedList() ?: listOf()

                        Log.println(Log.ERROR, "flatten", seatingList.toString())

                        onSuccess(seatingList)
                    }

                } else onFailure(response.message())
            }

            override fun onFailure(call: Call<GetMovieSeatResponse>, t: Throwable) {
                onFailure(t.message ?: "")
            }

        })
    }

    override fun getImdbRating(
        imdbId: String,
        onSuccess: (List<MovieVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mMovieApi?.getImdbRating(imdbId = imdbId)
            ?.enqueue(object : Callback<GetImdbRatingResponse> {
                override fun onResponse(
                    call: Call<GetImdbRatingResponse>,
                    response: Response<GetImdbRatingResponse>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            onSuccess(it.movieResults ?: listOf())
                            Log.println(Log.ERROR, "network", it.toString())
                        }
                    } else onFailure(response.message())
                }

                override fun onFailure(call: Call<GetImdbRatingResponse>, t: Throwable) {
                    onFailure(t.message ?: "")
                }

            })
    }

    override fun getSnackList(
        userToken: String,
        onSuccess: (List<SnackVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mMovieBookingApi?.getSnackList(userToken)
            ?.enqueue(object : Callback<GetSnackResponse> {
                override fun onResponse(
                    call: Call<GetSnackResponse>,
                    response: Response<GetSnackResponse>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            onSuccess(it.data ?: listOf())
                        }
                    } else onFailure(response.message())
                }

                override fun onFailure(call: Call<GetSnackResponse>, t: Throwable) {
                    onFailure(t.message ?: "")
                }

            })
    }

    override fun getPaymentMethods(
        userToken: String,
        onSuccess: (List<PaymentVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mMovieBookingApi?.getPaymentMethods(userToken)
            ?.enqueue(object : Callback<GetPaymentResponse> {
                override fun onResponse(
                    call: Call<GetPaymentResponse>,
                    response: Response<GetPaymentResponse>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            onSuccess(it.data ?: listOf())
                        }
                    } else onFailure(response.message())
                }

                override fun onFailure(call: Call<GetPaymentResponse>, t: Throwable) {
                    onFailure(t.message ?: "")
                }

            })
    }

    override fun createNewCard(
        userToken: String,
        cardNumber: String,
        cardHolder: String,
        expDate: String,
        cvc: String,
        onSuccess: (List<CardVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mMovieBookingApi?.createNewCard(
            authorization = userToken,
            cardNumber = cardNumber,
            cardHolder = cardHolder,
            ExpDate = expDate,
            cvc = cvc
        )?.enqueue(object : Callback<CreateNewCardResponse> {
            override fun onResponse(
                call: Call<CreateNewCardResponse>,
                response: Response<CreateNewCardResponse>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        if (it.isRequestSuccessful()) {
                            onSuccess(it.data ?: listOf())
                        }
                    }
                }
            }

            override fun onFailure(call: Call<CreateNewCardResponse>, t: Throwable) {

            }

        })
    }

    override fun checkOut(
        userToken: String,
        checkOutRequest: CheckOutRequest,
        onSuccess: (CheckOutSuccessVO) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mMovieBookingApi?.checkOut(checkOutRequest = checkOutRequest, authorization = userToken)
            ?.enqueue(object : Callback<CheckOutResponse> {
                override fun onResponse(
                    call: Call<CheckOutResponse>,
                    response: Response<CheckOutResponse>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            if (it.isRequestSuccessful()) {
                                it.data?.let { checkOutSuccess ->
                                    onSuccess(checkOutSuccess)
                                }
                            } else onFailure(response.message())
                        }
                    }

                }

                override fun onFailure(call: Call<CheckOutResponse>, t: Throwable) {
                    Log.println(Log.INFO, "checkOutFail", t.message ?: "")
                }

            })
    }


}