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
import com.example.sawrabin.moviebookingapp.network.CheckOutRequest
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_payment_account.*


class PaymentAccountActivity : AppCompatActivity() {

    private var mMovieBookingModel: MovieBookingModel = MovieBookingModelImpl
    var mCarrierData: CarrierVO? = null
    var mCardList: List<CardVO> = listOf()
    var mSelectedCardNumber: Int = 0
    lateinit var mCreditCardAdapter: CreditCardAdapter

    companion object {
        val IE_DATA_TO_Return = "Data to Return"
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
        val mData = intent.getStringExtra(SnackActivity.EXTRA_CARRIER_DATA)
        mCarrierData = Gson().fromJson(mData, CarrierVO::class.java)
        mCarrierData?.let {
            tvPaymentAmount.text = "$ ${it.totalPrice}"
        }
    }

    private fun requestData() {
        mMovieBookingModel.getProfile(
            onSuccess = {
                it.cards?.let { cardlist ->
                    mCardList = cardlist.reversed()
                }
                mSelectedCardNumber = mCardList.firstOrNull()?.id ?: 0
                Log.println(Log.INFO, "selected", mSelectedCardNumber.toString())
                mCardList.firstOrNull()?.isSelected = true
                mCreditCardAdapter.setNewData(mCardList)
            }, onFailure = {
                Snackbar.make(window.decorView, it, Snackbar.LENGTH_LONG).show()
            }
        )
    }

    private fun setUpOnClickListener() {

        tvPaymentAccountConfirm.setOnClickListener {

//            val mCheckOutRequest = CheckOutRequest(
//                row = mCarrierData?.row,
//                seatNumber = mCarrierData?.seatNumber,
//                snacks = mCarrierData?.snack ?: listOf(),
//                bookingDate = mCarrierData?.bookDate,
//                movieId = mCarrierData?.movie_id,
//                cinemaDayTimeslotId = mCarrierData?.timeslot ?: 0,
//                cardId = mSelectedCardNumber,
//                cinemaId = mCarrierData?.cinemaId
//            )

            if (mCardList.isNotEmpty()) {
                requestCheckOut()
            } else showError("Please Add A Card To Continue")
        }

        tvAddNewCard.setOnClickListener {
            startActivityForResult(AddNewCardActivity.newIntent(this), 100)
        }

//        ivBtnBackPaymentAccount.setOnClickListener {
//            startActivity(SnackActivity.newIntent(this))
//        }
    }

    private fun requestCheckOut() {
        mMovieBookingModel.checkOut(
            row = mCarrierData?.row ?: "",
            seatNumber = mCarrierData?.seatNumber ?: "",
            snacks = mCarrierData?.snack ?: listOf(),
            bookingDate = mCarrierData?.bookDate ?: "",
            movieId = mCarrierData?.movie_id ?: 0,
            cinemaDayTimeslotId = mCarrierData?.timeslot ?: 0,
            cardId = mSelectedCardNumber,
            cinemaId = mCarrierData?.cinemaId ?: 0,
            onSuccess = {
                mCarrierData?.bookingNo = it.bookingNo
                val carrierJson = Gson().toJson(mCarrierData, CarrierVO::class.java)
                startActivity(ReceiptActivity.newIntent(this, carrierJson))
            }, onFailure = {
                showError(it)
            })
    }

    private fun setUpCarousel() {
        mCreditCardAdapter = CreditCardAdapter()
        val carousel = Carousel(this, carouselRecyclerview, mCreditCardAdapter)
        carousel.setOrientation(CarouselView.HORIZONTAL, false)
        carousel.scaleView(true)
        mCreditCardAdapter.setNewData(mCardList)
        carousel.addCarouselListener(object : CarouselListener {
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
        }
    }

    private fun showError(error: String) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
    }

}