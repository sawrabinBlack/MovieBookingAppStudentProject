package com.example.sawrabin.moviebookingapp

import android.app.Application
import com.example.sawrabin.moviebookingapp.data.models.MovieBookingModelImpl

class MovieBookingApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        MovieBookingModelImpl.initDatabase(applicationContext)
    }
}