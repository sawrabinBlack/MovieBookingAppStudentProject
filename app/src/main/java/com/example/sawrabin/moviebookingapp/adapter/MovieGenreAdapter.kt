package com.example.sawrabin.moviebookingapp.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sawrabin.moviebookingapp.R
import com.example.sawrabin.moviebookingapp.data.vos.GenreVO
import com.example.sawrabin.moviebookingapp.viewholder.MovieGenreViewHolder

class MovieGenreAdapter : RecyclerView.Adapter<MovieGenreViewHolder>() {
    private var mGenreList: List<GenreVO> = listOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieGenreViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_holder_movie_genre, parent, false)
        return MovieGenreViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieGenreViewHolder, position: Int) {
        if (mGenreList.isNotEmpty()) {
            holder.bindData(mGenreList[position])
        }
    }

    override fun getItemCount(): Int {
        return mGenreList.count()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setNewData(genreList: List<GenreVO>) {
        mGenreList = genreList
        notifyDataSetChanged()
    }
}