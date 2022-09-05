package com.example.sawrabin.moviebookingapp.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sawrabin.moviebookingapp.R
import com.example.sawrabin.moviebookingapp.data.vos.PaymentVO
import com.example.sawrabin.moviebookingapp.data.vos.SnackVO
import com.example.sawrabin.moviebookingapp.delegate.PaymentMethodDelegate
import com.example.sawrabin.moviebookingapp.viewholder.PaymentViewHolder

class PaymentAdapter(private var mDelegate:PaymentMethodDelegate) : RecyclerView.Adapter<PaymentViewHolder>() {
    private var mPaymentList: List<PaymentVO> = listOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaymentViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_holder_payment_method, parent, false)
        return PaymentViewHolder(view,mDelegate)
    }

    override fun onBindViewHolder(holder: PaymentViewHolder, position: Int) {
        if (mPaymentList.isNotEmpty()) {
            holder.bindData(mPaymentList[position])
        }
    }

    override fun getItemCount(): Int {
        return mPaymentList.count()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setNewData(payment: List<PaymentVO>) {
        mPaymentList = payment
        notifyDataSetChanged()
    }
}