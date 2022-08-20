package com.example.sawrabin.moviebookingapp.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sawrabin.moviebookingapp.R
import com.example.sawrabin.moviebookingapp.data.vos.MovieSeatVO
import com.example.sawrabin.moviebookingapp.delegate.MovieSeatDelegate
import com.example.sawrabin.moviebookingapp.viewholder.SeatViewHolder

class MovieSeatAdapter(var mMovieSeats: List<MovieSeatVO> = listOf(),private var mDelegate:MovieSeatDelegate) :
    RecyclerView.Adapter<SeatViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeatViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.view_holder_seat, parent, false)
        return SeatViewHolder(view,mDelegate)
    }

    override fun onBindViewHolder(holder: SeatViewHolder, position: Int) {
        if (mMovieSeats.isNotEmpty()) {
            holder.bindData(mMovieSeats[position])
        }
    }

    override fun getItemCount(): Int {
        return mMovieSeats.count()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setNewData(movieSeats: List<MovieSeatVO>) {
        this.mMovieSeats = movieSeats
        notifyDataSetChanged()
    }
}