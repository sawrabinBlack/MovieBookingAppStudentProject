package com.example.sawrabin.moviebookingapp.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.sawrabin.moviebookingapp.R
import com.example.sawrabin.moviebookingapp.adapter.LoginViewPagerAdapter
import com.example.sawrabin.moviebookingapp.data.models.MovieBookingModel
import com.example.sawrabin.moviebookingapp.data.models.MovieBookingModelImpl
import com.example.sawrabin.moviebookingapp.delegate.LoginMethodsDelegate
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_user_login.*


class UserLoginActivity : AppCompatActivity(), LoginMethodsDelegate {

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, UserLoginActivity::class.java)

        }

    }


    private val mMovieBookingModel: MovieBookingModel = MovieBookingModelImpl
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_login)
        setupViewPager()
        setupTabLayout()


    }


    private fun setupTabLayout() {
        TabLayoutMediator(tabLayoutLogin, viewPagerLogin) { tab, position ->

            when (position) {
                0 -> {
                    tab.text = getString(R.string.lbl_log_in)
                }
                1 -> {
                    tab.text = getString(R.string.lbl_Sign_up)
                }
            }
        }.attach()
    }

    private fun setupViewPager() {
        val loginViewPagerAdapter = LoginViewPagerAdapter(this)
        viewPagerLogin.adapter = loginViewPagerAdapter
    }

    override fun onClickConfirmSignUp(
        email: String,
        password: String,
        phone: String,
        name: String
    ) {
        mMovieBookingModel.registerUser(
            name = name,
            email = email,
            phone = phone,
            password = password,
            onSuccess = {
                startActivity(HomeActivity.newIntent(this))
            },
            onFailure = {
                Snackbar.make(window.decorView, it, Snackbar.LENGTH_LONG).show()
            }

        )
    }

    override fun onCLickConfirmLoginIn(email: String, password: String) {
        mMovieBookingModel.emailLoginUser(
            email = email,
            password = password,
            onSuccess = {
                startActivity(HomeActivity.newIntent(this))
            },
            onFailure = {
                Snackbar.make(window.decorView, it, Snackbar.LENGTH_LONG).show()
            }

        )
    }


}