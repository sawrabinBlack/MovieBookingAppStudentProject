package com.example.sawrabin.moviebookingapp.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.example.sawrabin.moviebookingapp.R
import com.example.sawrabin.moviebookingapp.adapter.MovieSeatAdapter
import com.example.sawrabin.moviebookingapp.data.models.MovieBookingModel
import com.example.sawrabin.moviebookingapp.data.models.MovieBookingModelImpl
import com.example.sawrabin.moviebookingapp.data.vos.CarrierVO
import com.example.sawrabin.moviebookingapp.data.vos.MovieSeatVO
import com.example.sawrabin.moviebookingapp.data.vos.SEAT_TYPE_AVAILABLE
import com.example.sawrabin.moviebookingapp.delegate.MovieSeatDelegate
import kotlinx.android.synthetic.main.activity_movie_seat_activity.*

class MovieSeatActivity : AppCompatActivity(), MovieSeatDelegate {
    lateinit var mMovieSeatAdapter: MovieSeatAdapter
    private var mMovieBookingModel: MovieBookingModel = MovieBookingModelImpl
    private var mCarrierData: CarrierVO? = null
    private var mMovieSeatList: List<MovieSeatVO>? = null
    private var mSelectedSeat: String = ""
    private var mNumberTicket: Int = 0
    private var mTotalPrice = 0
    private var mRow: String = ""

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, MovieSeatActivity::class.java)
        }


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_seat_activity)
        mCarrierData = mMovieBookingModel.getBookingData()
        setUpSeatingPlanRecyclerView()
        setUpOnClickListener()

        mCarrierData?.let {
            requestData(it)
            setUpViewWithData(it)
        }


    }

    private fun setUpViewWithData(carrier: CarrierVO) {
        tvMovieSeatTitle.text = carrier.name
        tvMovieSeatCinema.text = carrier.cinemaName
        "${carrier.formatDate()}  -  ${carrier.timeslotTime}".also { tvMovieSeatDate.text = it }
    }

    private fun requestData(carrier: CarrierVO) {
        mMovieBookingModel.getMovieSeat(
            bookDate = carrier.bookDate ?: "",
            cinemaDayTimeslotId = carrier.timeslot.toString(),
            onFailure = {
                showError(it)
            },
            onSuccess = {
                Log.println(Log.ERROR, "Seat", it.toString())
                mMovieSeatAdapter.setNewData(it)
                mMovieSeatList = it

            })
    }

    private fun setUpOnClickListener() {
        ivBtnBackSeatPlan.setOnClickListener {
            startActivity(MovieTimeActivity.newIntent(this))
        }

        tvBuyTicket.setOnClickListener {
            if (mNumberTicket != 0) {
                mMovieBookingModel.storeMovieSeatData(mRow,mTotalPrice,mSelectedSeat)
                startActivity(SnackActivity.newIntent(this))
            } else showError("Please Select Movie Seat")

        }
    }

    private fun setUpSeatingPlanRecyclerView() {
        mMovieSeatAdapter = MovieSeatAdapter(mDelegate = this)
        rvSeatPlan.adapter = mMovieSeatAdapter
        rvSeatPlan.layoutManager = GridLayoutManager(this, 14)

    }

    override fun onTapMovieSeat(seatName: String) {
        for (seat in mMovieSeatList ?: listOf()) {
            if (seat.seatName == seatName && seat.type == SEAT_TYPE_AVAILABLE) {
                if (seat.isSelected == true) {
                    mNumberTicket--
                    seat.isSelected = false
                    mTotalPrice -= seat.price ?: 0

                } else {
                    seat.isSelected = true
                    mNumberTicket++
                    mTotalPrice += seat.price ?: 0
                }
            }
        }
        mMovieSeatAdapter.setNewData(mMovieSeatList ?: listOf())
        movieSeatDataUpdate()
    }

    private fun movieSeatDataUpdate() {
        tvNumberOfTickets.text = mNumberTicket.toString()
        "BUY TICKET FOR  $ $mTotalPrice".also { tvBuyTicket.text = it }
        val seatSelected = mMovieSeatList?.let { seatList ->
            seatList.filter { it.isSelected == true }
        }
        mSelectedSeat = seatSelected?.map {
            it.seatName
        }?.joinToString(",") ?: ""
        mRow = seatSelected?.map { it.symbol }?.distinct()?.joinToString(",") ?: ""
        tvSeatNumbers.text = mSelectedSeat
    }

    private fun showError(error: String) {
        Toast.makeText(this,error, Toast.LENGTH_SHORT).show()
    }
}