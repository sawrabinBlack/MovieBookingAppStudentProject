package com.example.sawrabin.moviebookingapp.viewholder

import android.graphics.Color
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.sawrabin.moviebookingapp.R
import com.example.sawrabin.moviebookingapp.data.vos.DateVO
import com.example.sawrabin.moviebookingapp.delegate.MovieDateDelegate
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.view_holder_movie_date.view.*

class MovieDateViewHolder(itemView: View, private var mDelegate: MovieDateDelegate) :
    RecyclerView.ViewHolder(itemView) {
    private var mDate: DateVO? = null

    init {
        itemView.setOnClickListener {
            mDate?.let {
                mDelegate.onTapDate(it.dayFull ?: "")

            }


        }

    }


    fun bindData(date: DateVO) {

        Log.println(Log.INFO, "TapDate", date.isChosed.toString())
        mDate = date
        itemView.tvMovieDay.text = date.cinemaDate
        itemView.tvMovieDate.text = date.cinemaDay
        if (date.isChosed == true) {
            itemView.tvMovieDay.setTextColor(
                ContextCompat.getColor(
                    itemView.context,
                    R.color.white
                )
            )
            itemView.tvMovieDate.setTextColor(
                ContextCompat.getColor(
                    itemView.context,
                    R.color.white
                )
            )
        } else {
            itemView.tvMovieDay.setTextColor(
                ContextCompat.getColor(
                    itemView.context,
                    R.color.color_overlay
                )
            )
            itemView.tvMovieDate.setTextColor(
                ContextCompat.getColor(
                    itemView.context,
                    R.color.color_overlay
                )
            )

        }
    }
}



