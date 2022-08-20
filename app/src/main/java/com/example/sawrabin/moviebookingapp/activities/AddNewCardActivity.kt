package com.example.sawrabin.moviebookingapp.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.AppCompatEditText
import com.example.sawrabin.moviebookingapp.R
import com.example.sawrabin.moviebookingapp.data.models.MovieBookingModel
import com.example.sawrabin.moviebookingapp.data.models.MovieBookingModelImpl
import com.example.sawrabin.moviebookingapp.data.vos.CardVO
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_add_new_card.*

class AddNewCardActivity : AppCompatActivity() {
    private var mMovieBookingModel: MovieBookingModel = MovieBookingModelImpl
    lateinit var cardNo: AppCompatEditText
    lateinit var cardHolderName: AppCompatEditText
    lateinit var cardExpDate: AppCompatEditText
    lateinit var cardCCV: AppCompatEditText

    companion object {

        const val IE_DATA_TO_RETURN = "DATA TO RETURN"
        fun newIntent(context: Context): Intent {
            return Intent(context, AddNewCardActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_card)
        cardNo = etCardNo
        cardHolderName = etCardHolder
        cardExpDate = etExpDate
        cardCCV = etCCV
        setUpOnClickListener()


    }

    private fun setUpOnClickListener() {
        ivBackAddNewCard.setOnClickListener {
            super.onBackPressed()
        }

        tvAddNewCardConfirmBtn.setOnClickListener {
            val intent = Intent()
            intent.putExtra(IE_DATA_TO_RETURN, "ADD CARD SUCCESSFULLY")
            createNewCard()
        }
    }

    private fun createNewCard() {
        when {
            (cardNo.length() != 16) -> {
                cardNo.error = "Please Type Valid Card No"
            }
            (cardHolderName.length() == 0) -> {
                cardHolderName.error = "Name Cannot Be Empty"
            }
            (cardExpDate.length() == 0) -> {
                cardExpDate.error = "Exp Date Cannot Be Empty"
            }
            (cardCCV.length() != 3) -> {
                cardCCV.error = "Invalid"
            }
            else -> requestCreateCard()
        }

    }

    private fun requestCreateCard() {
        mMovieBookingModel.createNewCard(
            cardNumber = cardNo.text.toString(),
            cardHolder = cardHolderName.text.toString(),
            expDate = cardExpDate.text.toString(),
            cvc = cardCCV.text.toString(),
            onSuccess = {
                setResult(RESULT_OK, intent)
                finish()
            },
            onFailure = { Snackbar.make(window.decorView, it, Snackbar.LENGTH_LONG).show() }
        )
    }
}

