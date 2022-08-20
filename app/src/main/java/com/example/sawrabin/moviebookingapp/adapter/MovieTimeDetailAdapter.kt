package com.example.sawrabin.moviebookingapp.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sawrabin.moviebookingapp.R
import com.example.sawrabin.moviebookingapp.data.vos.TimeSlotVO
import com.example.sawrabin.moviebookingapp.delegate.MovieTimeDetailDelegate
import com.example.sawrabin.moviebookingapp.viewholder.MovieTimeDetailViewHolder

class MovieTimeDetailAdapter (val mDelegate:MovieTimeDetailDelegate): RecyclerView.Adapter<MovieTimeDetailViewHolder>() {
    private var mTimeSlot: List<TimeSlotVO> = listOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieTimeDetailViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_holder_movie_time_detail, parent, false)
        return MovieTimeDetailViewHolder(view,mDelegate)
    }

    override fun onBindViewHolder(holder: MovieTimeDetailViewHolder, position: Int) {

        if (mTimeSlot.isNotEmpty()) {
            holder.bindData(mTimeSlot[position])
        }

    }

    override fun getItemCount(): Int {
        return mTimeSlot.count()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setNewData(timeslotList: List<TimeSlotVO>) {
        mTimeSlot = timeslotList
        notifyDataSetChanged()
    }
}