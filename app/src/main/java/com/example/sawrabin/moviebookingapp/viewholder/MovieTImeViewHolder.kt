package com.example.sawrabin.moviebookingapp.viewholder

import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sawrabin.moviebookingapp.adapter.MovieTimeDetailAdapter
import com.example.sawrabin.moviebookingapp.data.vos.CinemaDayVO
import com.example.sawrabin.moviebookingapp.data.vos.TimeSlotVO
import com.example.sawrabin.moviebookingapp.delegate.MovieTimeDetailDelegate
import kotlinx.android.synthetic.main.activity_movie_time.view.*
import kotlinx.android.synthetic.main.view_holder_movie_time.view.*

class MovieTImeViewHolder(itemView: View, private var mDelegate: MovieTimeDetailDelegate) :
    RecyclerView.ViewHolder(itemView) {
    private var mTimeSlotList: List<TimeSlotVO> = listOf()


    fun bindData(cinema: CinemaDayVO) {
        this.mTimeSlotList = cinema.timeslots ?: listOf()
        itemView.tvCinemaName.text = cinema.cinema
        val mMovieTimeDetailAdapter = MovieTimeDetailAdapter(mDelegate)
        itemView.rvMovieTimeDetail.adapter = mMovieTimeDetailAdapter
        itemView.rvMovieTimeDetail.layoutManager = GridLayoutManager(itemView.context, 3)
        mMovieTimeDetailAdapter.setNewData(mTimeSlotList)
    }
}