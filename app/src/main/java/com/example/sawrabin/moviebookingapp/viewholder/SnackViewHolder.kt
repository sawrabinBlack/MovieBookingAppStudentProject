package com.example.sawrabin.moviebookingapp.viewholder

import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.sawrabin.moviebookingapp.data.vos.SnackPaymentVO
import com.example.sawrabin.moviebookingapp.delegate.SnackDelegate
import kotlinx.android.synthetic.main.view_holder_snack.view.*

class SnackViewHolder(itemView: View, private var mDelegate: SnackDelegate) :
    RecyclerView.ViewHolder(itemView) {

    private var mSnack: SnackPaymentVO? = null

    fun bindData(snack: SnackPaymentVO) {
        mSnack = snack
        itemView.tvSnackName.text = snack.name ?: ""
        itemView.tvSnackContent.text = snack.description
        itemView.tvSnackPrice.text = "${snack.price} $"
        Log.println(Log.INFO,"quantity",snack.toString())
        itemView.tvMinus.setOnClickListener {
            mDelegate.onTapMinus(snack.id?:0)
        }

        itemView.tvPlus.setOnClickListener {
            mDelegate.onTapPlus(snack.id?:0)
        }

        itemView.tvAmount.text=snack.quantity.toString()
    }
}