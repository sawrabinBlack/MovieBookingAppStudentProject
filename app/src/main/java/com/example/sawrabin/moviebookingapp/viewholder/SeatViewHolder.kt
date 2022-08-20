package com.example.sawrabin.moviebookingapp.viewholder

import android.graphics.Color
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.sawrabin.moviebookingapp.R
import com.example.sawrabin.moviebookingapp.data.vos.MovieSeatVO
import com.example.sawrabin.moviebookingapp.data.vos.SEAT_TYPE_AVAILABLE
import com.example.sawrabin.moviebookingapp.data.vos.SEAT_TYPE_TAKEN
import com.example.sawrabin.moviebookingapp.data.vos.SEAT_TYPE_TEXT
import com.example.sawrabin.moviebookingapp.delegate.MovieSeatDelegate
import kotlinx.android.synthetic.main.activity_movie_seat_activity.view.*
import kotlinx.android.synthetic.main.view_holder_seat.view.*
import kotlinx.android.synthetic.main.view_holder_seat.view.tvMovieSeatTitle

class SeatViewHolder(itemView: View, private var mDelegate: MovieSeatDelegate) :
    RecyclerView.ViewHolder(itemView) {
    private var mMovieSeatVO: MovieSeatVO? = null
    fun bindData(data: MovieSeatVO) {
        mMovieSeatVO = data
        when {
            (data.isMovieSeatAvailable()) -> {
                itemView.tvMovieSeatTitle.visibility = View.GONE
                itemView.background = ContextCompat.getDrawable(
                    itemView.context,
                    R.drawable.background_seat_avaliable
                )
            }
            (data.isMovieSeatTaken()) -> {
                itemView.tvMovieSeatTitle.visibility = View.GONE
                itemView.background = ContextCompat.getDrawable(
                    itemView.context,
                    R.drawable.background_seat_taken
                )
            }
            (data.isMovieSeatTitle()) -> {
                itemView.tvMovieSeatTitle.visibility = View.VISIBLE
                itemView.tvMovieSeatTitle.text = data.symbol
                itemView.tvMovieSeatTitle.textSize = 15F
                itemView.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.white))

            }
            else -> {
                itemView.tvMovieSeatTitle.visibility = View.GONE
                itemView.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.white))
            }


        }

        itemView.setOnClickListener {

            mMovieSeatVO?.let {
                mDelegate.onTapMovieSeat(
                    seatName = it.seatName ?: "",
                )
            }

        }

        if (data.isSelected == true) {
            itemView.tvMovieSeatTitle.visibility = View.VISIBLE
            itemView.tvMovieSeatTitle.text = data.seatName
            itemView.tvMovieSeatTitle.setTextColor(Color.WHITE)
            itemView.background = ContextCompat.getDrawable(
                itemView.context,
                R.drawable.background_selected_color
            )
            itemView.tvMovieSeatTitle.textSize = 6F
        }
    }
}