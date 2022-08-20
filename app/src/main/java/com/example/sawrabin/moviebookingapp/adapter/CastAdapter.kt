package com.example.sawrabin.moviebookingapp.adapter

import android.annotation.SuppressLint
import android.media.audiofx.AcousticEchoCanceler
import android.text.Layout
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sawrabin.moviebookingapp.R
import com.example.sawrabin.moviebookingapp.data.vos.ActorVO
import com.example.sawrabin.moviebookingapp.viewholder.CastViewHolder

class CastAdapter : RecyclerView.Adapter<CastViewHolder>() {
    private var mActorList: List<ActorVO> = listOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.view_holder_cast, parent, false)
        return CastViewHolder(view)
    }

    override fun onBindViewHolder(holder: CastViewHolder, position: Int) {
        if (mActorList.isNotEmpty()) {
            holder.bindData(mActorList[position])
        }
    }

    override fun getItemCount(): Int {
        return mActorList.count()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setNewData(actorList:List<ActorVO>){
        mActorList=actorList
        notifyDataSetChanged()
    }
}