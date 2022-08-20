package com.example.sawrabin.moviebookingapp.adapter

import android.app.Activity
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.sawrabin.moviebookingapp.activities.HomeActivity
import com.example.sawrabin.moviebookingapp.delegate.LoginMethodsDelegate
import com.example.sawrabin.moviebookingapp.fragments.LoginFragment
import com.example.sawrabin.moviebookingapp.fragments.SignInFragment
import kotlinx.android.synthetic.main.view_pod_login_methods.view.*

class LoginViewPagerAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {


    override fun getItemCount(): Int {
        return 2
    }




    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                LoginFragment()
            }
            else -> SignInFragment()
        }
    }
}