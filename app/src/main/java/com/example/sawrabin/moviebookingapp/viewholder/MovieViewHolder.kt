package com.example.sawrabin.moviebookingapp.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.sawrabin.moviebookingapp.data.vos.MovieVO
import com.example.sawrabin.moviebookingapp.delegate.MovieViewHolderDelegate
import com.example.sawrabin.moviebookingapp.utils.BASE_IMAGE_URL
import kotlinx.android.synthetic.main.view_holder_movie.view.*

class MovieViewHolder(
    itemView: View,
    private val mMovieViewHolderDelegate: MovieViewHolderDelegate
) : RecyclerView.ViewHolder(itemView) {
    private var mMovie: MovieVO? = null

    init {
        itemView.setOnClickListener {

            mMovie?.let {
                mMovieViewHolderDelegate.onTapMovie(it.id)
            }
        }
    }

    fun bindData(movie: MovieVO) {
        this.mMovie = movie
        Glide.with(itemView.context)
            .load("$BASE_IMAGE_URL${movie.posterPath}")
            .into(itemView.ivMovieImage)

        itemView.tvMovieName.text = movie.title ?: ""

    }
}