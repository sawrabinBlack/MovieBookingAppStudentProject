package com.example.sawrabin.moviebookingapp.fragments

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatEditText
import com.example.sawrabin.moviebookingapp.R
import com.example.sawrabin.moviebookingapp.activities.HomeActivity
import com.example.sawrabin.moviebookingapp.delegate.LoginMethodViewPodDelegate
import com.example.sawrabin.moviebookingapp.delegate.LoginMethodsDelegate
import com.example.sawrabin.moviebookingapp.delegate.MovieViewHolderDelegate
import com.example.sawrabin.moviebookingapp.viewpod.LoginMethodsViewPod
import kotlinx.android.synthetic.main.activity_snack.view.*
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_login.view.*
import kotlinx.android.synthetic.main.fragment_sign_in.view.*
import kotlinx.android.synthetic.main.view_pod_login_methods.*
import kotlinx.android.synthetic.main.view_pod_login_methods.view.*
import kotlinx.android.synthetic.main.view_pod_login_methods.view.flConfirm


class LoginFragment : Fragment(), LoginMethodViewPodDelegate {
    lateinit var mDelegate: LoginMethodsDelegate
    lateinit var mLoginMethodsViewPod: LoginMethodsViewPod
    lateinit var email: AppCompatEditText
    lateinit var password: AppCompatEditText

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mDelegate = context as LoginMethodsDelegate

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mLoginMethodsViewPod = vpLogin as LoginMethodsViewPod
        password = view.etPasswordLogin
        email = view.etEmailLogin
        mLoginMethodsViewPod.setUpViewPod(this)


    }

    override fun onTapConfirm() {
        when {
            (email.length() == 0) -> {
                email.error = "Email Cannot be Empty"

            }
            (!(android.util.Patterns.EMAIL_ADDRESS.matcher(email.text.toString())
                .matches())) -> {
                email.error = "Please Enter Valid Email"

            }
            (password.length() == 0) -> {
                password.error = "Please Enter Password"

            }
            (password.length() < 6) -> {
                password.error = "Password must be 6 character min"

            }
            else -> mDelegate.onCLickConfirmLoginIn(
                email = email.text.toString(),
                password = password.text.toString()
            )
        }


    }


}














