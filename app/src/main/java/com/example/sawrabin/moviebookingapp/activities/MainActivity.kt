package com.example.sawrabin.moviebookingapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.sawrabin.moviebookingapp.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpListener()
    }

    private fun setUpListener() {
        ivGetStartedBtn.setOnClickListener {
            startActivity(UserLoginActivity.newIntent(this))
        }
    }
}