package com.example.sawrabin.moviebookingapp.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.sawrabin.moviebookingapp.R
import com.example.sawrabin.moviebookingapp.adapter.CastAdapter
import com.example.sawrabin.moviebookingapp.adapter.MovieGenreAdapter
import com.example.sawrabin.moviebookingapp.data.models.MovieBookingModel
import com.example.sawrabin.moviebookingapp.data.models.MovieBookingModelImpl
import com.example.sawrabin.moviebookingapp.data.vos.CarrierVO
import com.example.sawrabin.moviebookingapp.data.vos.MovieVO
import com.example.sawrabin.moviebookingapp.utils.BASE_IMAGE_URL
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_movie_details.*

class MovieDetailsActivity : AppCompatActivity() {
    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, MovieDetailsActivity::class.java)
        }
    }

    private lateinit var mCastAdapter: CastAdapter
    private lateinit var mMovieGenreAdapter: MovieGenreAdapter
    private val mMovieBookingModel: MovieBookingModel = MovieBookingModelImpl
    private var mMovieName: String = ""
    private var mImdbId: String? = null
    private var movieId: Int? = 0
    private var mMovieDuration: String = ""
    private var mPosterPath: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        movieId = mMovieBookingModel.getBookingData()?.movie_id
        setContentView(R.layout.activity_movie_details)
        setUpOnClickListener()
        setUpMovieGenreRecyclerView()
        setUpCastRecyclerView()

        movieId?.let {
            requestData(it)
        }


    }

    private fun requestData(movieId: Int) {
        //Network Call For Movie Detail
        mMovieBookingModel.getMovieDetail(movieId = movieId.toString(), onSuccess = { movieDetail ->
            bindData(movieDetail)
            mMovieGenreAdapter.setNewData(movieDetail.genres ?: listOf())
            mImdbId = movieDetail.imdbId ?: ""
            //Network Call For IMDB Rating
            mImdbId?.let { imdbID ->
                getImdbRating(imdbID)
            }
            mMovieName = movieDetail.title.toString()
        }, onFailure = { showError(it) })

        //Network Call For Cast List
        mMovieBookingModel.getCredits(
            movieId = movieId.toString(),
            onSuccess = {
                mCastAdapter.setNewData(it)

            },
            onFailure = { showError(it) })


    }

    private fun getImdbRating(imdbID: String) {
        mMovieBookingModel.getImdbRating(movieId = movieId.toString(),
            imdbId = imdbID, onSuccess = { rating ->
                "IMDB $rating".also { tvImdbRating.text = it }
                rbMovieDetail.rating = rating.div(2).toFloat() ?: 0.0F
            }, onFailure = {

            }
        )
    }

    private fun bindData(movie: MovieVO) {
        mPosterPath = movie.posterPath ?: ""
        tvMovieNameDetail.text = movie.title ?: ""
        Glide.with(this)
            .load("$BASE_IMAGE_URL${movie.posterPath}")
            .into(ivMovieDetail)
        tvPlotSummary.text = movie.overview ?: ""
        mMovieDuration = movie.runTimeFormattedInHourAndMin()
        tvMovieDetailDuration.text = movie.runTimeFormattedInHourAndMin()
        this.mMovieName = movie.title ?: ""
        clMovieDetail.title = movie.title
    }

    private fun setUpOnClickListener() {
        tvGetYourTicket.setOnClickListener {
            mMovieBookingModel.storeMovieDetailData(
                runtime = mMovieDuration, name = mMovieName, posterPath = mPosterPath
            )
            startActivity(MovieTimeActivity.newIntent(this))

        }

        ivBackMovieDetail.setOnClickListener {
            startActivity(HomeActivity.newIntent(this))
        }
    }

    private fun setUpMovieGenreRecyclerView() {
        mMovieGenreAdapter = MovieGenreAdapter()
        rvMovieGenre.adapter = mMovieGenreAdapter
        rvMovieGenre.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

    }

    private fun setUpCastRecyclerView() {
        mCastAdapter = CastAdapter()
        rvCast.adapter = mCastAdapter
        rvCast.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    }


    private fun showError(error: String) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
    }
}