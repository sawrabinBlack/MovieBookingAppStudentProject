package com.example.sawrabin.moviebookingapp.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sawrabin.moviebookingapp.R
import com.example.sawrabin.moviebookingapp.data.vos.CinemaDayVO
import com.example.sawrabin.moviebookingapp.delegate.MovieTimeDetailDelegate
import com.example.sawrabin.moviebookingapp.viewholder.MovieTImeViewHolder
import kotlinx.android.synthetic.main.activity_movie_time.view.*
import kotlinx.android.synthetic.main.view_holder_movie_time.view.*

class MovieTimeAdapter (private val mMovieTimeDelegate:MovieTimeDetailDelegate):
    RecyclerView.Adapter<MovieTImeViewHolder>(){
    private var mCinemaDayList: List<CinemaDayVO> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieTImeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_holder_movie_time, parent, false)
        return MovieTImeViewHolder(view,mMovieTimeDelegate)

    }

    override fun onBindViewHolder(holder: MovieTImeViewHolder, position: Int) {
        if (mCinemaDayList.isNotEmpty()) {
            holder.bindData(mCinemaDayList[position])
        }
    }

    override fun getItemCount(): Int {
        return mCinemaDayList.count()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setNewData(cinemaList: List<CinemaDayVO>) {
        mCinemaDayList = cinemaList
        notifyDataSetChanged()
    }
}