package com.example.sawrabin.moviebookingapp.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.sawrabin.moviebookingapp.R
import com.example.sawrabin.moviebookingapp.data.models.MovieBookingModelImpl
import com.example.sawrabin.moviebookingapp.data.vos.CarrierVO
import com.example.sawrabin.moviebookingapp.utils.BASE_IMAGE_URL
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeEncoder
import kotlinx.android.synthetic.main.activity_receipt.*

class ReceiptActivity : AppCompatActivity() {
    private var mCarrierData: CarrierVO? = null

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, ReceiptActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receipt)
        mCarrierData = MovieBookingModelImpl.getBookingData()
        setUpUI()
        setUpOnClickListener()
    }

    private fun setUpUI() {
        //BarCode
        val barcodeEncoder = BarcodeEncoder()
        val bitmap =
            barcodeEncoder.encodeBitmap(mCarrierData?.bookingNo, BarcodeFormat.CODE_128, 140, 50)
        ivBarCode.setImageBitmap(bitmap)
        //UI Update With DataS
        mCarrierData?.let {
            tvBooking.text = it.bookingNo ?: ""
            tvMovieNameReceipt.text = it.name ?: ""
            tvShowTime.text = " ${it.timeslotTime} - ${it.formatDate()}"
            tvTheater.text = it.cinemaName
            tvRow.text = it.row
            tvPrice.text = "$ ${it.totalPrice}"
            tvSeat.text = it.seatNumber
            tvMovieGenreReceipt.text = it.runtime
            Glide.with(this)
                .load("$BASE_IMAGE_URL${it.posterPath}")
                .into(SivMovie)
        }

    }

    private fun setUpOnClickListener() {
        ivBtnCloseReceipt.setOnClickListener {
            startActivity(HomeActivity.newIntent(this))
        }
    }

    override fun onBackPressed() {
        startActivity(HomeActivity.newIntent(this))
    }
}