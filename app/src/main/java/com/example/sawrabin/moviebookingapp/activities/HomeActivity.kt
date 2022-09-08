package com.example.sawrabin.moviebookingapp.activities

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.core.view.GravityCompat
import com.example.sawrabin.moviebookingapp.R
import com.example.sawrabin.moviebookingapp.data.models.MovieBookingModel
import com.example.sawrabin.moviebookingapp.data.models.MovieBookingModelImpl
import com.example.sawrabin.moviebookingapp.delegate.DrawerDelegate
import com.example.sawrabin.moviebookingapp.delegate.MovieViewHolderDelegate
import com.example.sawrabin.moviebookingapp.viewpod.DrawerViewPod
import com.example.sawrabin.moviebookingapp.viewpod.MovieListViewPod
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.view_pod_drawer.*


class HomeActivity : AppCompatActivity(), MovieViewHolderDelegate, DrawerDelegate {
    companion object {

        fun newIntent(context: Context): Intent {
            val intent = Intent(context, HomeActivity::class.java)

            return intent
        }
    }

    private lateinit var mComingSoonViewPod: MovieListViewPod
    private lateinit var mNowShowingViewPod: MovieListViewPod
    private lateinit var mDrawerViewPod: DrawerViewPod
    private var actionBarDrawerToggle: ActionBarDrawerToggle? = null
    private var mMovieBookingModel: MovieBookingModel = MovieBookingModelImpl

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setUpToolBar()
        setUpViewPod()
        requestData()


    }

    private fun requestData() {
        mMovieBookingModel.getProfile(
            onSuccess = {
                it.name?.let { name ->
                    tvHiUser.text = "Hi $name"
                    tvUserNameDrawer.text = name
                }
                tvEmailDrawer.text = it.email ?: ""

            }, onFailure = {
                showError(it)
            }
        )
        mMovieBookingModel.getUpcoming(onSuccess = {
            mComingSoonViewPod.setData(it)
        }, onFailure = {
            showError(it)
        })
        mMovieBookingModel.getNowShowing(onSuccess = {
            mNowShowingViewPod.setData(it)
        }, onFailure = {
            showError(it)
        })


    }

    private fun setUpToolBar() {
        setSupportActionBar(toolbar)
        actionBarDrawerToggle =
            ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_open, R.string.drawer_close)
        actionBarDrawerToggle?.let {
            drawerLayout.addDrawerListener(it)
            it.syncState()
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_menu)
        val colorDrawable = ColorDrawable(Color.parseColor("#FFFFFF"))
        supportActionBar?.setBackgroundDrawable(colorDrawable)
    }

    private fun setUpViewPod() {
        mNowShowingViewPod = viewPodNowShowing as MovieListViewPod
        mNowShowingViewPod.setUpMovieListViewPods(this, getString(R.string.lbl_Now_Showing))
        mComingSoonViewPod = viewPodComingSoon as MovieListViewPod
        mComingSoonViewPod.setUpMovieListViewPods(this, getString(R.string.lbl_Coming_soon))
        mDrawerViewPod = vpDrawer as DrawerViewPod
        mDrawerViewPod.setUpDrawerViewPod(this)


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar, menu)
        return true
    }

    override fun onTapMovie(movieId: Int) {
        mMovieBookingModel.insertMovieId(movieId)
        startActivity(MovieDetailsActivity.newIntent(this))
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (actionBarDrawerToggle?.onOptionsItemSelected(item) == true) {
            true
        } else super.onOptionsItemSelected(item)
    }

    override fun onTapEdit() {
    }

    override fun onTapLogOut() {
        logOut()


    }

    private fun logOut() {
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle("Log out")
        alertDialogBuilder.setMessage("Do You Want To Log Out? ")
        alertDialogBuilder.setPositiveButton("Yes") { _, _ ->
            mMovieBookingModel.userLogOut(
                onSuccess = { startActivity(UserLoginActivity.newIntent(this)) },
                onFailure = { showError(it) }
            )
        }
        alertDialogBuilder.setNegativeButton("No") { dialog, _ ->
            dialog.dismiss()
        }
        alertDialogBuilder.create().show()
    }

    override fun onBackPressed() {
        return if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else logOut()
    }

    private fun showError(error: String) {
        Snackbar.make(window.decorView, error, Snackbar.LENGTH_LONG).show()
    }
}