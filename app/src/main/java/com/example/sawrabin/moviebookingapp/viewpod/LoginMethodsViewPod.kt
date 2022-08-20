package com.example.sawrabin.moviebookingapp.viewpod

import android.content.Context
import android.util.AttributeSet
import android.widget.RelativeLayout
import androidx.core.content.ContextCompat.startActivity
import com.example.sawrabin.moviebookingapp.activities.HomeActivity
import com.example.sawrabin.moviebookingapp.delegate.LoginMethodViewPodDelegate
import com.example.sawrabin.moviebookingapp.delegate.LoginMethodsDelegate
import com.example.sawrabin.moviebookingapp.delegate.MovieViewHolderDelegate
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.view_pod_login_methods.view.*
import kotlinx.android.synthetic.main.view_pod_movie_list.view.*

class LoginMethodsViewPod @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : RelativeLayout(context, attrs) {

    //    lateinit var mDelegate: LoginMethodsDelegate
    lateinit var mDelegate: LoginMethodViewPodDelegate

    init {

    }

    override fun onFinishInflate() {
        super.onFinishInflate()
    }

    //    fun setUpViewPod(mMethodsDelegate: LoginMethodsDelegate, email: String, password: String) {
//        mDelegate = mMethodsDelegate
//        mDelegate.onCLickConfirmLoginIn(email, password)
//
//
//    }
//
//    fun setUpViewPodSignIn(
//        mMethodsDelegate: LoginMethodsDelegate,
//        email: String,
//        password: String,
//        phone: String,
//        name: String,
//    ) {
//        mDelegate = mMethodsDelegate
//            mDelegate.onClickConfirmSignUp(
//                name = name,
//                phone = phone,
//                email = email,
//                password = password
//            )
    fun setUpViewPod(
        mLoginMethodsViewPodDelegate: LoginMethodViewPodDelegate
    ) {
        mDelegate = mLoginMethodsViewPodDelegate
        flConfirm.setOnClickListener {
            mDelegate.onTapConfirm()
        }

    }

}


