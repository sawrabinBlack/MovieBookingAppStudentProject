package com.example.sawrabin.moviebookingapp.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sawrabin.moviebookingapp.R
import com.example.sawrabin.moviebookingapp.adapter.MovieDateAdapter
import com.example.sawrabin.moviebookingapp.adapter.MovieTimeAdapter
import com.example.sawrabin.moviebookingapp.data.models.MovieBookingModel
import com.example.sawrabin.moviebookingapp.data.models.MovieBookingModelImpl
import com.example.sawrabin.moviebookingapp.data.vos.CarrierVO
import com.example.sawrabin.moviebookingapp.data.vos.CinemaDayVO
import com.example.sawrabin.moviebookingapp.data.vos.DateVO
import com.example.sawrabin.moviebookingapp.delegate.MovieDateDelegate
import com.example.sawrabin.moviebookingapp.delegate.MovieTimeDetailDelegate
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_movie_time.*
import java.text.SimpleDateFormat
import java.util.*

class MovieTimeActivity : AppCompatActivity(), MovieTimeDetailDelegate, MovieDateDelegate {
    private var mMovieBooking: MovieBookingModel = MovieBookingModelImpl
    private var mCinemaDayList: List<CinemaDayVO> = listOf()
    private var mDateList: List<DateVO> = listOf()
    private var mDataParsed: CarrierVO? = null
    private var mMovieId: Int? = null
    private var mSelectedTimeSlot: Int = 0
    private var mBookDate: String = ""
    private var carrierString: CarrierVO? = null
    private var mCinemaName: String? = ""
    private var mStartTime: String? = ""
    private var mTimeSlot: Int? = null
    private var isSelectedTimeSlot: Boolean = false
    private var mCinemaId: Int = 0


    companion object {
        const val EXTRA_MOVIE_ID = "EXTRA_MOVIE_ID"
        fun newIntent(context: Context, data: String): Intent {
            val intent = Intent(context, MovieTimeActivity::class.java)
            intent.putExtra(EXTRA_MOVIE_ID, data)
            return intent
        }
    }

    private lateinit var mMovieDateAdapter: MovieDateAdapter
    private lateinit var mMovieTimeAdapter: MovieTimeAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_time)
        generateTwoWeeks()
        setUpRecycleView()
        getDataFromIntent()
        todayDateSelect()
        setUpOnClickListener()

    }


    private fun todayDateSelect() {
        val mToday = mDateList.firstOrNull()?.dayFull
        onTapDate(mToday ?: "")
    }

    private fun getDataFromIntent() {
        val mData = intent?.getStringExtra(EXTRA_MOVIE_ID)
        mDataParsed = Gson().fromJson(mData, CarrierVO::class.java)
    }

    private fun setUpOnClickListener() {
        tvNextMovieTime.setOnClickListener {
            mDataParsed?.let {
                it.timeslot=mTimeSlot
                it.bookDate=mBookDate
                it.cinemaId=mCinemaId
                it.cinemaName=mCinemaName
                it.timeslotTime=mStartTime

            }
            if (isSelectedTimeSlot) {
                val carrierDataJson = Gson().toJson(mDataParsed)
                startActivity(MovieSeatActivity.newIntent(this, carrierDataJson))
                Log.println(Log.INFO,"movieTimeString",mDataParsed.toString())
            } else showError("Please Select Movie Show Time")

        }

//        ivBackMovieTime.setOnClickListener {
//            startActivity(MovieDetailsActivity.newIntent(this))
//        }
    }

    private fun setUpRecycleView() {
        //Movie Date Recycler View
        mMovieDateAdapter = MovieDateAdapter(this)
        rvMovieDate.adapter = mMovieDateAdapter
        rvMovieDate.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        mMovieDateAdapter.setNewData(mDateList)
        //Movie Time Recycler View
        mMovieTimeAdapter = MovieTimeAdapter(this)
        rvMovieTime.adapter = mMovieTimeAdapter
        rvMovieTime.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

    }

    override fun onTapTimeSlot(timeslotId: Int) {
        mSelectedTimeSlot = timeslotId
        for (cinemaDay in mCinemaDayList) {
            for (timeslot in cinemaDay.timeslots ?: listOf()) {
                if (timeslot.cinemaDayTimeslotId == timeslotId) {
                    timeslot.isSelected = true
                    mCinemaId = cinemaDay.cinemaId ?: 0
                    mCinemaName = cinemaDay.cinema ?: ""
                    mStartTime = timeslot.startTime ?: ""
                    mTimeSlot = timeslot.cinemaDayTimeslotId
                    isSelectedTimeSlot = true
                    Log.println(Log.INFO, "c1", carrierString.toString())
                } else timeslot.isSelected = false
            }
        }
        mMovieTimeAdapter.setNewData(mCinemaDayList)

    }

    override fun onTapDate(dateSelected: String) {
        mBookDate = dateSelected
        isSelectedTimeSlot = false

        // Update UI
        for (dateList in mDateList) {
            dateList.isChosed = dateList.dayFull == dateSelected
        }
        mMovieDateAdapter.setNewData(mDateList)
        //NetworkCall For TimeSlot
        mMovieBooking.getCinemaDayTimeslots(
            movieId = mMovieId.toString(), date = dateSelected, onSuccess = { dayList ->
                mMovieTimeAdapter.setNewData(dayList)
                mCinemaDayList = dayList

            }, onFailure = {
                showError(it)
            }
        )


    }

    private fun showError(error: String) {
        Toast.makeText(this,error,Toast.LENGTH_SHORT).show()
    }

    private fun generateTwoWeeks() {
        val twoWeeksData = mutableListOf<DateVO>()
        for (day in 0..14) {
            val c1 = Calendar.getInstance()
            c1.add(Calendar.DATE, day)
            val time = c1.time
            val day1 = SimpleDateFormat("EEE", Locale.ENGLISH).format(time)
            val date = SimpleDateFormat("dd", Locale.ENGLISH).format(time)
            val dateFull = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(time)
            twoWeeksData.add(
                DateVO(
                    cinemaDay = date, cinemaDate = day1, dayFull = dateFull
                )
            )


        }
        mDateList = twoWeeksData
        Log.println(Log.INFO, "generatedDate", mDateList.toString())


    }
}