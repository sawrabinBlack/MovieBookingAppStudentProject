package com.example.sawrabin.moviebookingapp.viewholder

import android.graphics.Color
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.sawrabin.moviebookingapp.R
import com.example.sawrabin.moviebookingapp.data.vos.TimeSlotVO
import com.example.sawrabin.moviebookingapp.delegate.MovieTimeDetailDelegate
import kotlinx.android.synthetic.main.view_holder_movie_time_detail.view.*

class MovieTimeDetailViewHolder(itemView: View, private val mDelegate: MovieTimeDetailDelegate) :
    RecyclerView.ViewHolder(itemView) {


    fun bindData(timeslot: TimeSlotVO) {
        itemView.tvMovieTime.text = timeslot.startTime
        itemView.setOnClickListener {
            timeslot.cinemaDayTimeslotId?.let {
                mDelegate.onTapTimeSlot(it)
            }
        }

        if (timeslot.isSelected == true){
            itemView.tvMovieTime.setTextColor(Color.WHITE)
            itemView.setBackgroundColor(ContextCompat.getColor(itemView.context,R.color.colorAccent))
        }


    }
}