package com.example.sawrabin.moviebookingapp.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatEditText
import com.example.sawrabin.moviebookingapp.R
import com.example.sawrabin.moviebookingapp.delegate.LoginMethodViewPodDelegate
import com.example.sawrabin.moviebookingapp.delegate.LoginMethodsDelegate
import com.example.sawrabin.moviebookingapp.viewpod.LoginMethodsViewPod
import kotlinx.android.synthetic.main.fragment_sign_in.*
import kotlinx.android.synthetic.main.fragment_sign_in.view.*
import kotlinx.android.synthetic.main.view_pod_login_methods.view.*


class SignInFragment : Fragment(), LoginMethodViewPodDelegate {
    lateinit var mDelegate: LoginMethodsDelegate
    lateinit var mLoginMethodsViewPod: LoginMethodsViewPod
    lateinit var name: AppCompatEditText
    lateinit var email: AppCompatEditText
    lateinit var phone: AppCompatEditText
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
        return inflater.inflate(R.layout.fragment_sign_in, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mLoginMethodsViewPod = vpSignUp as LoginMethodsViewPod
        name = view.etName
        email = view.etEmailSignUp
        phone = view.etPhone
        password = view.etPassword
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
            (password.length() < 6) -> {
                password.error = "Password must be 6 character min"

            }
            (name.length() == 0) -> {
                name.error = "name Cannot Be Empty"

            }
            (phone.length() == 0 || phone.length() > 13) -> {
                phone.error = "Please Enter Valid Phone No"

            }
            ((phone.text.toString().slice(0..2) != "959")) -> {
                phone.error = "Phone Number must start with 959"

            }
            else -> {
                mDelegate.onClickConfirmSignUp(
                    name = name.text.toString(),
                    password = password.text.toString(),
                    phone = phone.text.toString(),
                    email = email.text.toString(),
                )
            }
        }
    }
}