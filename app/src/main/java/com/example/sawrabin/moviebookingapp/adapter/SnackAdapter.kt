package com.example.sawrabin.moviebookingapp.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sawrabin.moviebookingapp.R
import com.example.sawrabin.moviebookingapp.data.vos.SnackPaymentVO
import com.example.sawrabin.moviebookingapp.delegate.SnackDelegate
import com.example.sawrabin.moviebookingapp.viewholder.SnackViewHolder

class SnackAdapter (private var mDelegate:SnackDelegate): RecyclerView.Adapter<SnackViewHolder>() {
    private var mSnackList: List<SnackPaymentVO> = listOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SnackViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.view_holder_snack, parent, false)
        return SnackViewHolder(view,mDelegate)
    }

    override fun onBindViewHolder(holder: SnackViewHolder, position: Int) {
        if (mSnackList.isNotEmpty()) {
            holder.bindData(mSnackList[position])
        }
    }

    override fun getItemCount(): Int {
        return mSnackList.count()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setNewData(snackList: List<SnackPaymentVO>) {
        mSnackList = snackList
        notifyDataSetChanged()
    }
}