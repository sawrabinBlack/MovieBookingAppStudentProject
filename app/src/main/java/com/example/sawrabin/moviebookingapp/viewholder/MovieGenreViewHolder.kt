package com.example.sawrabin.moviebookingapp.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.sawrabin.moviebookingapp.data.vos.GenreVO
import kotlinx.android.synthetic.main.view_holder_movie_genre.view.*

class MovieGenreViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bindData(genre: GenreVO) {
        itemView.tvMovieGenre.text = genre.name ?: ""
    }
}