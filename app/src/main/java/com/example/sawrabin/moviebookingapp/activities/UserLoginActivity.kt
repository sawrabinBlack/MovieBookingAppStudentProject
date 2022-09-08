package com.example.sawrabin.moviebookingapp.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.sawrabin.moviebookingapp.R
import com.example.sawrabin.moviebookingapp.adapter.LoginViewPagerAdapter
import com.example.sawrabin.moviebookingapp.data.models.MovieBookingModel
import com.example.sawrabin.moviebookingapp.data.models.MovieBookingModelImpl
import com.example.sawrabin.moviebookingapp.delegate.LoginMethodsDelegate
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_user_login.*
import kotlinx.android.synthetic.main.view_pod_login_methods.*


class UserLoginActivity : AppCompatActivity(), LoginMethodsDelegate {

    companion object {
        private const val RC_GET_TOKEN = 9002
        private const val RC_GET_TOKEN_SIGN_UP=9000
        fun newIntent(context: Context): Intent {
            return Intent(context, UserLoginActivity::class.java)

        }

    }


    private val mMovieBookingModel: MovieBookingModel = MovieBookingModelImpl
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private  var mGoogleSignUpId : String = ""
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_login)
        setupViewPager()
        setupTabLayout()
        setUpGoogleLogin()


    }

    private fun setUpGoogleLogin() {
        val gso: GoogleSignInOptions =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.server_client_id))
                .requestEmail().requestId().build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        mGoogleSignInClient.revokeAccess()
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
            googleAccessToken = mGoogleSignUpId,
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

    override fun onTapSignInWithGoogle() {
        Log.println(Log.INFO, "login", "login1111")
        val signInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(
            signInIntent, RC_GET_TOKEN
        )
    }

    override fun onTapSignUpWithGoogle() {
        val signInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(
            signInIntent, RC_GET_TOKEN_SIGN_UP
        )
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_GET_TOKEN) {
            try {
                val account =authenicateWithGoogle(data)
                mMovieBookingModel.loginWithGoogle(
                    account.id.toString(),
                    onSuccess = {
                        startActivity(HomeActivity.newIntent(this))
                    }, onFailure = {})
            } catch (e: ApiException) {

            }


        }

        if (requestCode == RC_GET_TOKEN_SIGN_UP) {
            try {
                mGoogleSignUpId=authenicateWithGoogle(data).id.toString()
            } catch (e: ApiException) {

            }


        }
    }

    private fun authenicateWithGoogle(data: Intent?) : GoogleSignInAccount{
        val task = GoogleSignIn.getSignedInAccountFromIntent(data)
        val account = task.getResult(ApiException::class.java)
        return account
    }


    private fun showError(error: String) {
        Snackbar.make(window.decorView, error, Snackbar.LENGTH_LONG).show()
    }

}