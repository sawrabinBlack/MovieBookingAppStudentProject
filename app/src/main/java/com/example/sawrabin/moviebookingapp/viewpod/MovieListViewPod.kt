package com.example.sawrabin.moviebookingapp.viewpod

import android.content.Context
import android.util.AttributeSet
import android.widget.RelativeLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sawrabin.moviebookingapp.adapter.MovieAdapter
import com.example.sawrabin.moviebookingapp.data.vos.MovieVO
import com.example.sawrabin.moviebookingapp.delegate.MovieViewHolderDelegate
import kotlinx.android.synthetic.main.view_pod_movie_list.view.*

class MovieListViewPod @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : RelativeLayout(context, attrs) {
    lateinit var mMovieAdapter: MovieAdapter
    lateinit var mMovieViewHolderDelegate: MovieViewHolderDelegate

    override fun onFinishInflate() {
        super.onFinishInflate()
    }

    fun setUpMovieListViewPods(delegate: MovieViewHolderDelegate, titleText: String) {
        setDelegate(delegate)
        setUpRecyclerView()
        tvNowShowing.text = titleText

    }

    fun setData(movieList: List<MovieVO>) {
        mMovieAdapter.setNewData(movieList)
    }

    private fun setDelegate(delegate: MovieViewHolderDelegate) {
        this.mMovieViewHolderDelegate = delegate
    }

    private fun setUpRecyclerView() {
        mMovieAdapter = MovieAdapter(mMovieViewHolderDelegate)
        rvMovieList.adapter = mMovieAdapter
        rvMovieList.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    }
}