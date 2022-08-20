package com.example.sawrabin.moviebookingapp.viewholder

import android.graphics.Color
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.sawrabin.moviebookingapp.R
import com.example.sawrabin.moviebookingapp.data.vos.SnackPaymentVO
import com.example.sawrabin.moviebookingapp.delegate.PaymentMethodDelegate
import kotlinx.android.synthetic.main.view_holder_payment_method.view.*

class PaymentViewHolder(itemView: View, private var mDelegate: PaymentMethodDelegate) :
    RecyclerView.ViewHolder(itemView) {
    private var mPayment: SnackPaymentVO? = null

    fun bindData(payment: SnackPaymentVO) {
        mPayment = payment
        itemView.tvPaymentType.text = payment.name ?: ""
        itemView.tvPaymentChannel.text = payment.description ?: ""
        itemView.setOnClickListener {
            mDelegate.onTapItem(payment.id ?: 0)
        }
        if (payment.isSelected == true) {
            setUpUI(R.color.colorAccent)
        } else
            setUpUI(R.color.color_payment_unselected)
    }

    private fun setUpUI(Color: Int) {
        itemView.ivPaymentIcon.setColorFilter(ContextCompat.getColor(itemView.context, Color))
        itemView.tvPaymentType.setTextColor(ContextCompat.getColor(itemView.context, Color))
        itemView.tvPaymentChannel.setTextColor(ContextCompat.getColor(itemView.context, Color))
    }
}