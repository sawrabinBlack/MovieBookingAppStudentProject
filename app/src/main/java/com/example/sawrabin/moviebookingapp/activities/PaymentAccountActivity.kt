package com.example.sawrabin.moviebookingapp.activities


import alirezat775.lib.carouselview.Carousel
import alirezat775.lib.carouselview.CarouselListener
import alirezat775.lib.carouselview.CarouselView
import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.sawrabin.moviebookingapp.R
import com.example.sawrabin.moviebookingapp.adapter.CreditCardAdapter
import com.example.sawrabin.moviebookingapp.data.models.MovieBookingModel
import com.example.sawrabin.moviebookingapp.data.models.MovieBookingModelImpl
import com.example.sawrabin.moviebookingapp.data.vos.CardVO
import com.example.sawrabin.moviebookingapp.data.vos.CarrierVO
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_payment_account.*


class PaymentAccountActivity : AppCompatActivity() {

    private var mMovieBookingModel: MovieBookingModel = MovieBookingModelImpl
    var mCarrierData: CarrierVO? = null
    var mCardList: List<CardVO> = listOf()
    var mSelectedCardNumber: Int = 0
    var mData: String? = ""
    lateinit var mCreditCardAdapter: CreditCardAdapter
    lateinit var mCarousel: Carousel

    companion object {
        private const val EXTRA_CARRIER_DATA = "EXTRA_CARRIER_DATA"
        fun newIntent(context: Context, data: String): Intent {
            val intent = Intent(context, PaymentAccountActivity::class.java)
            intent.putExtra(EXTRA_CARRIER_DATA, data)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_account)
        setUpCarousel()
        setUpInitialDataAndUI()
        setUpOnClickListener()
        requestData()


    }

    private fun setUpInitialDataAndUI() {
        mCarrierData = MovieBookingModelImpl.getBookingData()
        mCarrierData?.let {
            tvPaymentAmount.text = "$ ${it.totalPrice}"
        }
    }

    private fun requestData() {
        mMovieBookingModel.getProfile(
            onSuccess = {
                it.cards?.let { cardList ->
                    mCardList = cardList.reversed()
                }
                mSelectedCardNumber = mCardList.firstOrNull()?.id ?: 0
                mCardList.firstOrNull()?.isSelected = true
                mCreditCardAdapter.setNewData(mCardList)
            }, onFailure = {
                showError(it)
            }
        )
    }

    private fun setUpOnClickListener() {
        tvPaymentAccountConfirm.setOnClickListener {
            if (mCardList.isNotEmpty()) {
                requestCheckOut()
            } else showError(getString(R.string.warning_please_add_a_card))
        }
        tvAddNewCard.setOnClickListener {
            startActivityForResult(AddNewCardActivity.newIntent(this), 100)
        }

        ivBtnBackPaymentAccount.setOnClickListener {
            startActivity(SnackActivity.newIntent(this, mData ?: ""))
        }
    }

    private fun requestCheckOut() {
        mMovieBookingModel.checkOut(
            cardId = mSelectedCardNumber,
            onSuccess = {
                MovieBookingModelImpl.storeBookingNo(it.bookingNo?:"")
                mCarrierData?.bookingNo = it.bookingNo
                val carrierJson = Gson().toJson(mCarrierData, CarrierVO::class.java)
                startActivity(ReceiptActivity.newIntent(this, carrierJson))
            }, onFailure = {
                showError(it)
            })
    }

    private fun setUpCarousel() {
        mCreditCardAdapter = CreditCardAdapter()
        mCarousel = Carousel(this, carouselRecyclerview, mCreditCardAdapter)
        mCarousel.setOrientation(CarouselView.HORIZONTAL, false)
        mCarousel.scaleView(true)
        mCreditCardAdapter.setNewData(mCardList)

        mCarousel.addCarouselListener(object : CarouselListener {
            override fun onPositionChange(position: Int) {
                mSelectedCardNumber = mCardList[position].id ?: 0
                mCardList[position].isSelected = true
                mCardList.forEach {
                    if (it.id != mSelectedCardNumber) {
                        it.isSelected = false
                    }
                }
                mCreditCardAdapter.setNewData(mCardList)
                Log.println(Log.INFO, "selected", mSelectedCardNumber.toString())
            }

            override fun onScroll(dx: Int, dy: Int) {
            }

        })
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && data != null && resultCode == Activity.RESULT_OK) {
            requestData()
            if (mCardList.isNotEmpty()) {
                mCarousel.setCurrentPosition(0)
            }

        }
    }

    private fun showError(error: String) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
    }

}