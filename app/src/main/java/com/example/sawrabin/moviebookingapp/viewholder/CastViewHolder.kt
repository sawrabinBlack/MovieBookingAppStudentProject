package com.example.sawrabin.moviebookingapp.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.sawrabin.moviebookingapp.data.vos.ActorVO
import com.example.sawrabin.moviebookingapp.utils.BASE_IMAGE_URL
import kotlinx.android.synthetic.main.view_holder_cast.view.*

class CastViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bindData(actor: ActorVO) {
        Glide.with(itemView.context)
            .load("$BASE_IMAGE_URL${actor.profilePath}")
            .into(
                itemView.ivCastImage
            )
        itemView.tvCastName.text = actor.name ?: ""
    }
}