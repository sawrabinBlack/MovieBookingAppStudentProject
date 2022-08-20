package com.example.sawrabin.moviebookingapp.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.sawrabin.moviebookingapp.R
import com.example.sawrabin.moviebookingapp.data.vos.DateVO
import com.example.sawrabin.moviebookingapp.delegate.MovieDateDelegate
import com.example.sawrabin.moviebookingapp.viewholder.MovieDateViewHolder

class MovieDateAdapter(private var mDelegate: MovieDateDelegate) :
    RecyclerView.Adapter<MovieDateViewHolder>() {
    var selected_item: Int = 0
    private var mDateList: List<DateVO> = listOf()
    private var selectedItemPosition: Int = 0
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieDateViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_holder_movie_date, parent, false)
        return MovieDateViewHolder(view, mDelegate)
    }

    override fun onBindViewHolder(holder: MovieDateViewHolder, position: Int) {
        selectedItemPosition = holder.bindingAdapterPosition
        if (mDateList.isNotEmpty()) {
            holder.bindData(mDateList[position])

        }


    }

    override fun getItemCount(): Int {
        return mDateList.count()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setNewData(dateList: List<DateVO>) {
        mDateList = dateList
        Log.println(Log.INFO, "TapSetNewData", mDateList.toString())
        notifyDataSetChanged()
    }
}