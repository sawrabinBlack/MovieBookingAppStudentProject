package com.example.sawrabin.moviebookingapp.viewpod

import android.content.Context
import android.util.AttributeSet
import android.widget.RelativeLayout
import com.example.sawrabin.moviebookingapp.delegate.DrawerDelegate
import kotlinx.android.synthetic.main.view_pod_drawer.view.*

class DrawerViewPod @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null,
) : RelativeLayout(context, attrs) {
    lateinit var mDrawerDelegate: DrawerDelegate
    override fun onFinishInflate() {

        super.onFinishInflate()
    }

    fun setUpDrawerViewPod(mDelegate: DrawerDelegate) {
        this.mDrawerDelegate = mDelegate
        tvEdit.setOnClickListener {
            mDelegate.onTapEdit()
        }

        tvLogOut.setOnClickListener {
            mDelegate.onTapLogOut()
        }
    }
}