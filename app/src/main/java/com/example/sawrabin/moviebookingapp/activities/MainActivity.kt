package com.example.sawrabin.moviebookingapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.sawrabin.moviebookingapp.R
import com.example.sawrabin.moviebookingapp.data.models.MovieBookingModel
import com.example.sawrabin.moviebookingapp.data.models.MovieBookingModelImpl
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    val mMovieBookingModel : MovieBookingModel= MovieBookingModelImpl
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpListener()

    }

    private fun setUpListener() {
        ivGetStartedBtn.setOnClickListener {
            if(MovieBookingModelImpl.isLogin()){
                startActivity(HomeActivity.newIntent(this))
            }
            else startActivity(UserLoginActivity.newIntent(this))


        }

    }
}