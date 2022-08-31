package com.example.sawrabin.moviebookingapp.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sawrabin.moviebookingapp.R
import com.example.sawrabin.moviebookingapp.adapter.PaymentAdapter
import com.example.sawrabin.moviebookingapp.adapter.SnackAdapter
import com.example.sawrabin.moviebookingapp.data.models.MovieBookingModel
import com.example.sawrabin.moviebookingapp.data.models.MovieBookingModelImpl
import com.example.sawrabin.moviebookingapp.data.vos.CarrierVO
import com.example.sawrabin.moviebookingapp.data.vos.SnackPaymentVO
import com.example.sawrabin.moviebookingapp.delegate.PaymentMethodDelegate
import com.example.sawrabin.moviebookingapp.delegate.SnackDelegate
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_snack.*

class SnackActivity : AppCompatActivity(), SnackDelegate, PaymentMethodDelegate {
    private var mMovieBookingModel: MovieBookingModel = MovieBookingModelImpl
    private var mSnackList: List<SnackPaymentVO> = listOf()
    private var mPaymentList: List<SnackPaymentVO> = listOf()
    var mSubTotal: Int = 0
    var mCarrierData: CarrierVO? = null
    var mCarrierJson: String = ""

    companion object {
        const val EXTRA_CARRIER_DATA = "EXTRA_CARRIER_DATA"
        fun newIntent(context: Context, data: String): Intent {

            val intent = Intent(context, SnackActivity::class.java)
            intent.putExtra(EXTRA_CARRIER_DATA, data)
            return intent
        }
    }

    lateinit var mSnackAdapter: SnackAdapter
    lateinit var mPaymentAdapter: PaymentAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_snack)
        val mData = intent.getStringExtra(EXTRA_CARRIER_DATA)
        mCarrierData = Gson().fromJson(mData, CarrierVO::class.java)
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
//        ivBtnBackSnack.setOnClickListener {
//            startActivity(MovieSeatActivity.newIntent(this))
//        }

        tvPaySnack.setOnClickListener {
            mSnackList = mSnackList.filter { it.quantity != 0 }
            mCarrierData?.let {
                it.snack=mSnackList
                it.totalPrice=mSubTotal
            }
            mCarrierJson = Gson().toJson(mCarrierData, CarrierVO::class.java)
            startActivity(PaymentAccountActivity.newIntent(this, mCarrierJson))
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
    private fun snackOnPlusUpdate(snack: SnackPaymentVO) {
        snack.quantity++
        mSubTotal += snack.price ?: 0
        mSnackAdapter.setNewData(mSnackList)
        subTotalUpdate(mSubTotal)
    }
    private fun snackOnMinusUpdate(snack: SnackPaymentVO) {
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