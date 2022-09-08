package com.example.sawrabin.moviebookingapp.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sawrabin.moviebookingapp.R
import com.example.sawrabin.moviebookingapp.adapter.PaymentAdapter
import com.example.sawrabin.moviebookingapp.adapter.SnackAdapter
import com.example.sawrabin.moviebookingapp.data.models.MovieBookingModel
import com.example.sawrabin.moviebookingapp.data.models.MovieBookingModelImpl
import com.example.sawrabin.moviebookingapp.data.vos.CarrierVO
import com.example.sawrabin.moviebookingapp.data.vos.PaymentVO
import com.example.sawrabin.moviebookingapp.data.vos.SnackVO
import com.example.sawrabin.moviebookingapp.delegate.PaymentMethodDelegate
import com.example.sawrabin.moviebookingapp.delegate.SnackDelegate
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_snack.*

class SnackActivity : AppCompatActivity(), SnackDelegate, PaymentMethodDelegate {
    private var mMovieBookingModel: MovieBookingModel = MovieBookingModelImpl
    private var mSnackList: List<SnackVO> = listOf()
    private var mPaymentList: List<PaymentVO> = listOf()
    var mSubTotal: Int = 0
    var mCarrierData: CarrierVO? = null
    var mCarrierJson: String = ""

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, SnackActivity::class.java)
        }
    }

    lateinit var mSnackAdapter: SnackAdapter
    lateinit var mPaymentAdapter: PaymentAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_snack)
        mCarrierData = mMovieBookingModel.getBookingData()
        mCarrierData?.let {
            mSubTotal = it.totalPrice ?: 0
            subTotalUpdate(it.totalPrice ?: 0)
        }

        setUpSnackRecyclerView()
        setUpPaymentMethodRecyclerView()
        requestData()
        setUpOnClickListener()
    }

    private fun subTotalUpdate(subTotal: Int) {
        "Sub total: $subTotal $".also { tvSubTotal.text = it }
        "Pay  $subTotal $ ".also { tvPaySnack.text = it }
    }

    private fun requestData() {
        mMovieBookingModel.getSnackList(
            onSuccess = {
                mSnackAdapter.setNewData(it)
                mSnackList = it

            }, onFailure = {
                Snackbar.make(window.decorView, it, Snackbar.LENGTH_SHORT).show()
            }
        )

        mMovieBookingModel.getPaymentMethods(
            onSuccess = {
                mPaymentAdapter.setNewData(it)
                mPaymentList = it

            }, onFailure = {
                Snackbar.make(window.decorView, it, Snackbar.LENGTH_SHORT).show()
            }
        )
    }


    private fun setUpOnClickListener() {
        ivBtnBackSnack.setOnClickListener {
            startActivity(MovieSeatActivity.newIntent(this))
        }

        tvPaySnack.setOnClickListener {
            val mSelectedSnackList = mSnackList.filter { it.quantity != 0 }
            mMovieBookingModel.storeSnackData(mSelectedSnackList,mSubTotal)
            startActivity(PaymentAccountActivity.newIntent(this))
        }
    }

    private fun setUpSnackRecyclerView() {
        mSnackAdapter = SnackAdapter(this)
        rvSnackList.adapter = mSnackAdapter
        rvSnackList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }

    private fun setUpPaymentMethodRecyclerView() {
        mPaymentAdapter = PaymentAdapter(this)
        rvPaymentMethod.adapter = mPaymentAdapter
        rvPaymentMethod.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }

    override fun onTapPlus(id: Int) {
        for (snack in mSnackList) {
            if (snack.id == id) {
                snackOnPlusUpdate(snack)
            }
        }


    }

    override fun onTapMinus(id: Int) {
        for (snack in mSnackList) {
            if (snack.id == id) {
                if (snack.quantity != 0) {
                    snackOnMinusUpdate(snack)
                }
            }
        }
        mSnackAdapter.setNewData(mSnackList)
    }
    private fun snackOnPlusUpdate(snack: SnackVO) {
        snack.quantity++
        mSubTotal += snack.price ?: 0
        mSnackAdapter.setNewData(mSnackList)
        subTotalUpdate(mSubTotal)
    }
    private fun snackOnMinusUpdate(snack: SnackVO) {
        snack.quantity--
        mSubTotal -= snack.price ?: 0
        subTotalUpdate(mSubTotal)
    }

    override fun onTapItem(id: Int) {
        for (payment in mPaymentList) {
            payment.isSelected = payment.id == id
        }
        mPaymentAdapter.setNewData(mPaymentList)
    }
}