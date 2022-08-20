package com.example.sawrabin.moviebookingapp.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sawrabin.moviebookingapp.R
import com.example.sawrabin.moviebookingapp.data.vos.MovieVO
import com.example.sawrabin.moviebookingapp.delegate.MovieViewHolderDelegate
import com.example.sawrabin.moviebookingapp.viewholder.MovieViewHolder


class MovieAdapter(val mMovieViewHolderDelegate: MovieViewHolderDelegate) :
    RecyclerView.Adapter<MovieViewHolder>() {

    private var mMovieList: List<MovieVO> = listOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.view_holder_movie, parent, false)
        return MovieViewHolder(view, mMovieViewHolderDelegate)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        if (mMovieList.isNotEmpty()) {
            holder.bindData(mMovieList[position])
        }

    }

    override fun getItemCount(): Int {
        return mMovieList.count()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setNewData(movieList: List<MovieVO>) {
        mMovieList = movieList
        notifyDataSetChanged()
    }
}